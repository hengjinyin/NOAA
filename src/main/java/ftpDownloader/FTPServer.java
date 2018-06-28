package ftpDownloader;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.*;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FTPServer {
    private FTPClient     ftpClient;
    private final String  strIp;
    private final int     intPort;
    private final String  user;
    private final String  password;

    public FTPServer(String strIp, int intPort, String user, String Password) {
        this.strIp = strIp;
        this.intPort = intPort;
        this.user = user;
        this.password = Password;
        this.ftpClient = new FTPClient();

        System.out.println(this.strIp + " " + this.intPort + "  " +this.user + "  " + this.password);
    }

    public boolean downloadFile(String remoteFileName, String localDires, String remoteDownLoadPath) {
        String strFilePath = localDires + remoteFileName;
        BufferedOutputStream outStream = null;
        boolean success = false;
        try {
            InetAddress inetAddress =InetAddress.getByName(strIp);
            String ipAddress = inetAddress.getHostAddress();
            this.ftpClient.connect(ipAddress,intPort);
            if(user == "" ){
                this.ftpClient.login("anonymous", "");
            }else{
                this.ftpClient.login(user, password);
            }
            int reply = this.ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                this.ftpClient.disconnect();
                return false;
            }
            this.ftpClient.enterLocalPassiveMode();
            this.ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            this.ftpClient.changeWorkingDirectory(remoteDownLoadPath);
            reply = this.ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                this.ftpClient.disconnect();
                return false;
            }
            outStream = new BufferedOutputStream(new FileOutputStream(strFilePath), 1024 * 200);
            this.ftpClient.setBufferSize(1024 * 200);
            success = this.ftpClient.retrieveFile( remoteFileName, outStream);
            if (success == true) {
                return success;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != outStream) {
                try {
                    outStream.flush();
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return success;
    }

}

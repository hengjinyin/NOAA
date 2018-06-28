package UnzipFile;

import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.core.ZipFile;
//This file is useless because gz is not a archive format.
public class UnzipFile {
    public static void unzip(String source, String destination, String password){
        try {
            ZipFile zipFile = new ZipFile(source);
            if (zipFile.isEncrypted()) {
                zipFile.setPassword(password);
            }
            zipFile.extractAll(destination);
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }
}

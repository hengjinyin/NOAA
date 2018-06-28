package com.NOAA.Controllers;

import DecompressGZFile.DecompressGZFile;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import entity.NoaaDataDto;
import ftpDownloader.FTPServer;
import FileReaderUtility.FileReaderUtility;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class SearchController {
    private int port = 21; // ftp uses default port Number 21
    private static Map<String, NoaaDataDto> noaaDataDtoMap = new HashMap<String, NoaaDataDto>();


    @RequestMapping(value = "/DownLoadFile", method = RequestMethod.PUT)
    public String downLoadFile(@RequestBody String ftpURL) throws JsonProcessingException {
        String[] strs = ftpURL.split("/");
        String ftpServerAddress = strs[0];
        String fileName = strs[strs.length-1];
        String remoteFilePath = ftpURL.substring(ftpServerAddress.length(),ftpURL.length() - fileName.length());
        //hard code value 3 here to assume file name is ending with ".gz"
        String localFileName = fileName.substring(0,fileName.length()-3);
        FTPServer ftpServer = new FTPServer(ftpServerAddress,port,"","");
        String home = System.getProperty("user.home");
        boolean downloadSuccess = ftpServer.downloadFile(fileName,home+"/Downloads/",remoteFilePath);
        if(downloadSuccess){
            DecompressGZFile.unGunzipFile(home+"/Downloads/"+fileName, home+"/Downloads/"+localFileName);
        }
        String response = downloadSuccess ? home+"/Downloads/"+localFileName : "";
        return ConvertToJson(response);
    }

    @RequestMapping(value = "/FindAll", method = RequestMethod.POST)
    public String findAllNoaaData(@RequestBody String localFileDirectory) throws JsonProcessingException {
        if(noaaDataDtoMap.size() != 0){
            return ConvertToJson(noaaDataDtoMap);
        }
        String home = System.getProperty("user.home");
        noaaDataDtoMap = FileReaderUtility.ReadCvsFile(localFileDirectory);
        return ConvertToJson(noaaDataDtoMap);
    }

    @RequestMapping(value = "/SearchById", method = RequestMethod.POST)
    public String searchNoaaDataById(@RequestBody String searchId) throws JsonProcessingException {
        searchId = searchId.toUpperCase();
        if(noaaDataDtoMap.containsKey(searchId)){
            Map<String, NoaaDataDto> noaatemp = new HashMap<String, NoaaDataDto>();
            noaatemp.put(searchId,noaaDataDtoMap.get(searchId));
            return ConvertToJson(noaatemp);
        }
        return "";
    }

    private String ConvertToJson(Map<String, NoaaDataDto> noaaDataDtoMap) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return mapper.writeValueAsString(noaaDataDtoMap);
    }

    private String ConvertToJson(String response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return mapper.writeValueAsString(response);
    }

}

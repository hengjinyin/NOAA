package FileReaderUtility;

import entity.NoaaDataDto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileReaderUtility{
    public static Map<String, NoaaDataDto> ReadCvsFile(String fileLocation) {

        BufferedReader crunchifyBuffer = null;
        Map<String, NoaaDataDto> noaaDataDtoMap = new HashMap<String, NoaaDataDto>();

        try {
            String crunchifyLine;
            crunchifyBuffer = new BufferedReader(new FileReader(fileLocation));

            // How to read file in java line by line?
            while ((crunchifyLine = crunchifyBuffer.readLine()) != null) {
                String[] buffer = crunchifyLine.split(",");
                NoaaDataDto noaaData = new NoaaDataDto(buffer[0],buffer[1],buffer[2]);
                noaaDataDtoMap.put(buffer[0].toUpperCase(),noaaData);
            }
            return noaaDataDtoMap;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (crunchifyBuffer != null) crunchifyBuffer.close();
            } catch (IOException crunchifyException) {
                crunchifyException.printStackTrace();
            }
        }

        // Utility which converts CSV to ArrayList using Split Operation
//        public static ArrayList<String> crunchifyCSVtoArrayList(String crunchifyCSV) {
//            ArrayList<String> crunchifyResult = new ArrayList<String>();
//
//            if (crunchifyCSV != null) {
//                String[] splitData = crunchifyCSV.split("\\s*,\\s*");
//                for (int i = 0; i < splitData.length; i++) {
//                    if (!(splitData[i] == null) || !(splitData[i].length() == 0)) {
//                        crunchifyResult.add(splitData[i].trim());
//                    }
//                }
//            }
//
//            return crunchifyResult;
        return null;
        }
}

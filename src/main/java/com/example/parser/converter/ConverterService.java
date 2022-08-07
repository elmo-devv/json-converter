package com.example.parser.converter;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public final class ConverterService {

    @Value("${asIsFileDir}")
    private String asIsFileDir;
    @Value("${toBeFileDir}")
    private String toBeFileDir;
    @Value("${asIsFileName}")
    private String asIsFileName;

    public int readFile() {
        JSONParser jsonParser = new JSONParser();
        int totalSize = 0;

        try {
            File directory = new File(toBeFileDir);
            FileUtils.deleteDirectory(directory);
            directory.mkdirs();
            Reader reader = new FileReader(asIsFileDir + File.separator + asIsFileName);
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            jsonObject.keySet().forEach(walletName -> {
                Object keystore = jsonObject.get(walletName);
                try {
                    generateFile(keystore);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            totalSize = jsonObject.size();
        } catch (IOException | ParseException e) {
            System.out.println("ERROR" + e);
        }

        return totalSize;
    }

    private void generateFile(Object keystore) throws IOException {
        JSONObject jsonObject = (JSONObject) keystore;
        String path = toBeFileDir;
        String fileName = jsonObject.get("address").toString();
        String insertStr = jsonObject.toJSONString();

        File directory = new File(path);
        FileWriter writer = null;
        try {
            writer = new FileWriter(directory + File.separator + fileName, false);
            writer.write(insertStr);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

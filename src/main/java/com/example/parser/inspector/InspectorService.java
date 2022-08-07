package com.example.parser.inspector;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.management.BadAttributeValueExpException;
import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class InspectorService {
    @Value("${asIsFileDir}")
    private String asIsFileDir;
    @Value("${toBeFileDir}")
    private String toBeFileDir;
    @Value("${asIsFileName}")
    private String asIsFileName;

    public void inspectFile() {
        JSONParser jsonParser = new JSONParser();
        AtomicInteger inspectCount = new AtomicInteger();

        try {
            Reader reader = new FileReader(asIsFileDir + File.separator + asIsFileName);
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            jsonObject.keySet().forEach(walletName -> {
                Object keystore = jsonObject.get(walletName);
                try {
                    inspect(walletName.toString(), keystore);
                    inspectCount.getAndIncrement();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            System.out.println("총 " + jsonObject.size() + "키스토어 중 " + inspectCount.get() + "개가 성공적으로 검수되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void inspect(String walletName, Object keystore) throws IOException {
        JSONObject jsonObject = (JSONObject) keystore;
        String fileName = jsonObject.get("address").toString();

        Reader reader = new FileReader(toBeFileDir + File.separator + fileName);
        JSONParser jsonParser = new JSONParser();

        try {
            JSONObject targetKeyStore = (JSONObject) jsonParser.parse(reader);

            if (!targetKeyStore.get("address").equals(jsonObject.get("address"))) {
                throw new BadAttributeValueExpException(walletName + "의 address 정보가 일치하지 않습니다.");
            }

            if (!targetKeyStore.get("pub_key").equals(jsonObject.get("pub_key"))) {
                throw new BadAttributeValueExpException(walletName + "의 address 정보가 일치하지 않습니다.");
            }

            if (!targetKeyStore.get("priv_key").equals(jsonObject.get("priv_key"))) {
                throw new BadAttributeValueExpException(walletName + "의 address 정보가 일치하지 않습니다.");
            }

            System.out.println(walletName + "정상적으로 파일이 생성되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

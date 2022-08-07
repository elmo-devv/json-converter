package com.example.parser;

import com.example.parser.converter.ConverterService;
import com.example.parser.inspector.InspectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class ParserApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ParserApplication.class, args);
    }

    @Autowired
    ConverterService converterService;

    @Autowired
    InspectorService inspectorService;

    @Override
    public void run(String... args) {
        try {
            System.out.println("########### Keystore File Migration Converter START !!! ###########");
            Scanner in = new Scanner(System.in);

            while (true) {
                System.out.println("Please select a service");
                System.out.println("[1] converter [2] tester");
                String service = in.next();
                switch (service) {
                    case "1":
                    case "converter":
                        int totalSize = converterService.readFile();
                        System.out.println("총 " + totalSize + "개의 키스토어 파일을 생성하였습니다.");
                        break;
                    case "2":
                    case "tester":
                        inspectorService.inspectFile();
                        System.out.println("모든 키스토어 파일이 정상적으로 검증되었습니다.");
                        break;
                    default:
                        System.out.println("Invalid input !!");
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
    }
}

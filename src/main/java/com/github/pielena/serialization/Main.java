package com.github.pielena.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pielena.serialization.dataprocessor.FileHandlerProcessor;
import com.github.pielena.serialization.dataprocessor.OutputDataPreparer;
import com.github.pielena.serialization.dataprocessor.impl.CsvProcessor;
import com.github.pielena.serialization.dataprocessor.impl.JsonProcessor;
import com.github.pielena.serialization.dataprocessor.impl.XmlProcessor;
import com.github.pielena.serialization.dataprocessor.impl.YmlProcessor;
import com.github.pielena.serialization.dto.ResponseDto;
import com.github.pielena.serialization.model.MessagesReport;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        String fileName = "output";
        URL inputFile = ClassLoader.getSystemResource("input/sms-256866-480df9.json");

        ObjectMapper objectMapper = new ObjectMapper();
        MessagesReport messagesReport = objectMapper.readValue(inputFile, MessagesReport.class);
        List<ResponseDto> outputData = OutputDataPreparer.prepareData(messagesReport);

        System.out.println("Read .json file: ");
        FileHandlerProcessor jsonProcessor = new JsonProcessor();
        jsonProcessor.writeToFile(fileName, outputData);
        System.out.println(jsonProcessor.readFromFile(fileName));

        System.out.println("Read .xml file: ");
        FileHandlerProcessor xmlProcessor = new XmlProcessor();
        xmlProcessor.writeToFile(fileName, outputData);
        System.out.println(xmlProcessor.readFromFile(fileName));

        System.out.println("Read .csv file: ");
        FileHandlerProcessor csvProcessor = new CsvProcessor();
        csvProcessor.writeToFile(fileName, outputData);
        System.out.println(csvProcessor.readFromFile(fileName));

        System.out.println("Read .yml file: ");
        FileHandlerProcessor ymlProcessor = new YmlProcessor();
        ymlProcessor.writeToFile(fileName, outputData);
        System.out.println(ymlProcessor.readFromFile(fileName));
    }
}

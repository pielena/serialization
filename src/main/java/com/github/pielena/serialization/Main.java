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

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        URL inputFile = ClassLoader.getSystemResource("input/sms-256866-480df9.json");

        ObjectMapper objectMapper = new ObjectMapper();
        MessagesReport messagesReport = objectMapper.readValue(inputFile, MessagesReport.class);
        List<ResponseDto> outputData = OutputDataPreparer.prepareData(messagesReport);

        handleJsonFile("output", "output.json", outputData);
        handleXmlFile("output", "output.xml", outputData);
        handleCsvFile("output", "output.csv", outputData);
        handleYmlFile("output", "output.yml", outputData);
    }

    private static void handleJsonFile(String directory, String fileName, List<ResponseDto> outputData) {
        File file = createFileVariable(directory, fileName);

        System.out.println("Read .json file: ");
        FileHandlerProcessor jsonProcessor = new JsonProcessor();
        jsonProcessor.writeToFile(file, outputData);
        System.out.println(jsonProcessor.readFromFile(file));
    }

    private static void handleXmlFile(String directory, String fileName, List<ResponseDto> outputData) {
        File file = createFileVariable(directory, fileName);

        System.out.println("Read .xml file: ");
        FileHandlerProcessor xmlProcessor = new XmlProcessor();
        xmlProcessor.writeToFile(file, outputData);
        System.out.println(xmlProcessor.readFromFile(file));
    }

    private static void handleCsvFile(String directory, String fileName, List<ResponseDto> outputData) {
        File file = createFileVariable(directory, fileName);

        System.out.println("Read .csv file: ");
        FileHandlerProcessor csvProcessor = new CsvProcessor();
        csvProcessor.writeToFile(file, outputData);
        System.out.println(csvProcessor.readFromFile(file));
    }

    private static void handleYmlFile(String directory, String fileName, List<ResponseDto> outputData) {
        File file = createFileVariable(directory, fileName);

        System.out.println("Read .yml file: ");
        FileHandlerProcessor ymlProcessor = new YmlProcessor();
        ymlProcessor.writeToFile(file, outputData);
        System.out.println(ymlProcessor.readFromFile(file));
    }

    private static File createFileVariable(String directory, String fileName) {
        File file = null;
        try {
            file = Path.of(ClassLoader.getSystemResource(directory).toURI()).resolve(Path.of(fileName)).toFile();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return file;
    }
}

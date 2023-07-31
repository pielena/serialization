package com.github.pielena.serialization.dataprocessor.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.pielena.serialization.dataprocessor.FileHandlerProcessor;
import com.github.pielena.serialization.dto.ResponseDto;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.github.pielena.serialization.util.FilePathStringCreator.createPathString;

public class XmlProcessor implements FileHandlerProcessor {

    @Override
    public void writeToFile(String fileName, List<ResponseDto> outputData) {
        File file = new File(createPathString(fileName + ".xml"));
        XmlMapper xmlMapper = new XmlMapper();
        try {
            xmlMapper.writerWithDefaultPrettyPrinter().writeValue(file, outputData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ResponseDto> readFromFile(String fileName) {
        File file = new File(createPathString(fileName + ".xml"));
        XmlMapper xmlMapper = new XmlMapper();
        List<ResponseDto> responseDtoList = null;
        try {
            responseDtoList = xmlMapper.readValue(file, new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseDtoList;
    }
}

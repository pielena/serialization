package com.github.pielena.serialization.dataprocessor.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pielena.serialization.dataprocessor.FileHandlerProcessor;
import com.github.pielena.serialization.dto.ResponseDto;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonProcessor implements FileHandlerProcessor {

    @Override
    public void writeToFile(File file, List<ResponseDto> outputData) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, outputData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ResponseDto> readFromFile(File file) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ResponseDto> responseDtoList = null;
        try {
            responseDtoList = objectMapper.readValue(file, new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseDtoList;
    }
}

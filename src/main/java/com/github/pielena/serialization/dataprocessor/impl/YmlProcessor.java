package com.github.pielena.serialization.dataprocessor.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.github.pielena.serialization.dataprocessor.FileHandlerProcessor;
import com.github.pielena.serialization.dto.ResponseDto;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.github.pielena.serialization.util.FilePathStringCreator.createPathString;

public class YmlProcessor implements FileHandlerProcessor {

    @Override
    public void writeToFile(String fileName, List<ResponseDto> outputData) {
        File file = new File(createPathString(fileName + ".yml"));
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
        try {
            mapper.writeValue(file, outputData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ResponseDto> readFromFile(String fileName) {
        File file = new File(createPathString(fileName + ".yml"));
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        List<ResponseDto> responseDtoList = null;
        try {
            responseDtoList = mapper.readValue(file, new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseDtoList;
    }
}

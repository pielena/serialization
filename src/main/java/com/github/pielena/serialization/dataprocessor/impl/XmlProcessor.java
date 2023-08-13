package com.github.pielena.serialization.dataprocessor.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.pielena.serialization.dataprocessor.FileHandlerProcessor;
import com.github.pielena.serialization.dto.ResponseDto;
import com.github.pielena.serialization.dto.XmlResponseDto;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class XmlProcessor implements FileHandlerProcessor {

    @Override
    public void writeToFile(File file, List<ResponseDto> outputData) {
        XmlResponseDto xmlResponseDto = new XmlResponseDto(outputData);
        XmlMapper xmlMapper = new XmlMapper();
        try {
            xmlMapper.writerWithDefaultPrettyPrinter().writeValue(file, xmlResponseDto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ResponseDto> readFromFile(File file) {
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

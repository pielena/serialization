package com.github.pielena.serialization.dataprocessor;

import com.github.pielena.serialization.dto.ResponseDto;

import java.util.List;

public interface FileHandlerProcessor {

    void writeToFile(String fileName, List<ResponseDto> outputData);

    List<ResponseDto> readFromFile(String fileName);

}

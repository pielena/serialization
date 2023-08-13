package com.github.pielena.serialization.dataprocessor;

import com.github.pielena.serialization.dto.ResponseDto;

import java.io.File;
import java.util.List;

public interface FileHandlerProcessor {

    void writeToFile(File file, List<ResponseDto> outputData);

    List<ResponseDto> readFromFile(File file);

}

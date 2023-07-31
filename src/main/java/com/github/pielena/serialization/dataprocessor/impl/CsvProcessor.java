package com.github.pielena.serialization.dataprocessor.impl;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.github.pielena.serialization.dataprocessor.FileHandlerProcessor;
import com.github.pielena.serialization.dto.CsvResponseDto;
import com.github.pielena.serialization.dto.ResponseDto;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.github.pielena.serialization.util.FilePathStringCreator.createPathString;
import static com.github.pielena.serialization.mapper.ResponseCsvMapper.fromCsvDtoList;
import static com.github.pielena.serialization.mapper.ResponseCsvMapper.toCsvDtoList;

public class CsvProcessor implements FileHandlerProcessor {

    @Override
    public void writeToFile(String fileName, List<ResponseDto> outputData) {
        File file = new File(createPathString(fileName + ".csv"));
        List<CsvResponseDto> csvResponseDtoList = toCsvDtoList(outputData);

        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(CsvResponseDto.class)
                .withColumnSeparator(',')
                .withoutQuoteChar()
                .withHeader();
        ObjectWriter writer = mapper.writer(schema);
        try {
            writer.writeValue(new FileWriter(file, StandardCharsets.UTF_8), csvResponseDtoList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ResponseDto> readFromFile(String fileName) {
        File file = new File(createPathString(fileName + ".csv"));
        List<ResponseDto> responseDtoList = new ArrayList<>();

        CsvMapper mapper = new CsvMapper();
        CsvSchema csvSchema = mapper.schemaFor(CsvResponseDto.class)
                .withColumnSeparator(',')
                .withoutQuoteChar()
                .withHeader();
        try {
            MappingIterator<CsvResponseDto> csvDtoIter = mapper
                    .readerWithTypedSchemaFor(CsvResponseDto.class)
                    .with(csvSchema)
                    .readValues(file);

            List<CsvResponseDto> csvDtoList = csvDtoIter.readAll();
            responseDtoList = fromCsvDtoList(csvDtoList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseDtoList;
    }
}

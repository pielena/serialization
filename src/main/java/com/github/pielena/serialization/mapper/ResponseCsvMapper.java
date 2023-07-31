package com.github.pielena.serialization.mapper;

import com.github.pielena.serialization.dto.CsvResponseDto;
import com.github.pielena.serialization.dto.NestedInfoDto;
import com.github.pielena.serialization.dto.ResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ResponseCsvMapper {

    public static List<CsvResponseDto> toCsvDtoList(List<ResponseDto> responseDtoList) {

        if (responseDtoList == null) {
            throw new IllegalArgumentException("ResponseDtoList doesn't exist");
        }

        List<CsvResponseDto> csvResponseDtoList = new ArrayList<>();
        for (ResponseDto responseDto : responseDtoList) {
            for (NestedInfoDto nestedInfo : responseDto.getNestedInfo()) {
                CsvResponseDto csvResponseDto = CsvResponseDto.builder()
                        .belongNumber(responseDto.getBelongNumber())
                        .chatIdentifier(nestedInfo.getChatIdentifier())
                        .last(nestedInfo.getLast())
                        .sendDate(nestedInfo.getSendDate())
                        .text(nestedInfo.getText())
                        .build();
                csvResponseDtoList.add(csvResponseDto);
            }
        }

        return csvResponseDtoList;
    }

    public static List<ResponseDto> fromCsvDtoList(List<CsvResponseDto> csvResponseDtoList) {

        if (csvResponseDtoList == null) {
            throw new IllegalArgumentException("CsvResponseDtoList doesn't exist");
        }

        Map<String, List<CsvResponseDto>> groupedByNumberInput = csvResponseDtoList.stream()
                .collect(Collectors.groupingBy(CsvResponseDto::getBelongNumber));

        List<ResponseDto> responseDtoList = new ArrayList<>();

        for (Map.Entry<String, List<CsvResponseDto>> entry : groupedByNumberInput.entrySet()) {
            List<NestedInfoDto> nestedInfoList = new ArrayList<>();
            entry.getValue().stream()
                    .map(ResponseCsvMapper::toNestedInfoDto)
                    .forEach(nestedInfoList::add);

            ResponseDto responseDto = ResponseDto.builder()
                    .belongNumber(entry.getKey())
                    .nestedInfo(nestedInfoList)
                    .build();
            responseDtoList.add(responseDto);
        }

        return responseDtoList;
    }

    private static NestedInfoDto toNestedInfoDto(CsvResponseDto csvResponseDto) {
        return NestedInfoDto.builder()
                .chatIdentifier(csvResponseDto.getChatIdentifier())
                .last(csvResponseDto.getLast())
                .sendDate(csvResponseDto.getSendDate())
                .text(csvResponseDto.getText())
                .build();
    }

}

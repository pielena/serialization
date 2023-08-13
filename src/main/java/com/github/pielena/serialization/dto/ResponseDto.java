package com.github.pielena.serialization.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
@ToString
public class ResponseDto {

    @JsonProperty("belong_number")
    private String belongNumber;

    @JsonProperty("info")
    private List<NestedInfoDto> nestedInfo;
}

package com.github.pielena.serialization.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@JacksonXmlRootElement(localName = "reports")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class XmlResponseDto {

    @JacksonXmlElementWrapper(useWrapping=false)
    @JsonProperty("report")
    private List<ResponseDto> responseDtoList;
}

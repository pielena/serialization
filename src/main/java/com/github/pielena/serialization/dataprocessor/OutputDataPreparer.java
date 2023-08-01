package com.github.pielena.serialization.dataprocessor;

import com.github.pielena.serialization.dto.NestedInfoDto;
import com.github.pielena.serialization.dto.ResponseDto;
import com.github.pielena.serialization.model.ChatSession;
import com.github.pielena.serialization.model.Member;
import com.github.pielena.serialization.model.Message;
import com.github.pielena.serialization.model.MessagesReport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OutputDataPreparer {

    public static List<ResponseDto> prepareData(MessagesReport messagesReport) {

        Map<String, List<NestedInfoDto>> groupedData = getGroupedData(messagesReport);

        List<ResponseDto> result = groupedData.entrySet().stream()
                .map(entry -> new ResponseDto(entry.getKey(), entry.getValue()))
                .toList();
        result.forEach(responseDto -> sortNestedInfoByDate(responseDto.getNestedInfo()));

        return result;
    }

    private static void sortNestedInfoByDate(List<NestedInfoDto> nestedInfos) {
        nestedInfos.sort(Comparator.comparing(NestedInfoDto::getSendDate));
    }

    private static String findLastMember(ChatSession chatSession, Message message) {
        return chatSession.getMembers().stream()
                .filter(member -> member.getHandleId() == message.getHandleId())
                .map(Member::getLast)
                .findFirst().orElse(null);
    }

    private static Map<String, List<NestedInfoDto>> getGroupedData(MessagesReport messagesReport) {
        Map<String, List<NestedInfoDto>> groupedData = new HashMap<>();

        for (ChatSession chatSession : messagesReport.getChatSessions()) {
            for (Message message : chatSession.getMessages()) {
                String belongNumber = message.getBelongNumber();

                NestedInfoDto nestedInfoDto = NestedInfoDto.builder()
                        .chatIdentifier(chatSession.getChatIdentifier())
                        .last(findLastMember(chatSession, message))
                        .sendDate(message.getSendDate())
                        .text(message.getText())
                        .build();

                groupedData.merge(belongNumber, new ArrayList<>(Arrays.asList(nestedInfoDto)), (l1, l2) -> {
                    List<NestedInfoDto> resultList = new ArrayList<>(l1);
                    resultList.addAll(l2);
                    return resultList;
                });
            }
        }

        return groupedData;
    }
}

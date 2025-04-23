package com.school.vaccination.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class IntegerListToStringConverter implements AttributeConverter<List<Integer>, String> {

    @Override
    public String convertToDatabaseColumn(List<Integer> list) {
        return list != null ? list.stream().map(String::valueOf).collect(Collectors.joining(",")) : "";
    }

    @Override
    public List<Integer> convertToEntityAttribute(String data) {
        return data != null && !data.isEmpty()
                ? Arrays.stream(data.split(",")).map(Integer::parseInt).collect(Collectors.toList())
                : new ArrayList<>();
    }
}

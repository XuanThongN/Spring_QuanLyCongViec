package com.xuanthongn.spring_quanlycongviec.common;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xuanthongn.spring_quanlycongviec.entities.SubTask;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.util.Collection;

@Converter(autoApply = true)
public class CollectionConverter implements AttributeConverter<Collection<SubTask>, String> {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Collection<SubTask> subTask) {

        String subTaskJson = null;
        try {
            subTaskJson = objectMapper.writeValueAsString(subTask);
        } catch (final JsonProcessingException e) {
            System.out.println(e);
        }

        return subTaskJson;
    }

    @Override
    public Collection<SubTask> convertToEntityAttribute(String subTaskJSON) {

        if (subTaskJSON == null) {
            return null; // or handle the null value appropriately
        }
        Collection<SubTask> subTasks = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

            // Use TypeReference to specify the target collection type
            subTasks = objectMapper.readValue(subTaskJSON, new TypeReference<Collection<SubTask>>() {});
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonParseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return subTasks;
    }
}

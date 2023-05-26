package com.openclassroom.project5.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.openclassroom.project5.model.FireStationDTO;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadDataFile {

    //TODO: Mettre un Logger
    static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> List<T> loadDataFromFile(String entity, Class<T> elementClass) throws IOException {
        List<T> objects = null;
        File file = new File(ReadDataFile.class.getClassLoader().getResource("data.json").getFile());

        try {
            JsonNode jsonNode = objectMapper.readTree(file);

            JsonNode entityNode = jsonNode.get(entity);
            if (entityNode != null) {
                CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, elementClass);
                objects = objectMapper.readValue(entityNode.traverse(), listType);
            }
        } catch (FileNotFoundException exception) {
            System.err.println("File not Found, make sur path is correct or file is not missing");
        }

        return objects;
    }
}

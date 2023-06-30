package com.openclassroom.project5.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.openclassroom.project5.model.FireStationDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadDataFile {

    //TODO: Mettre un Logger
    private static final Logger logger = LogManager.getLogger(ReadDataFile.class);
    static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> List<T> loadDataFromFile(String entity, Class<T> elementClass) throws IOException {
        logger.info("Extract entiy: " + entity);
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
            logger.error("File not Found, make sur path is correct or file is not missing");
        }

        return objects;
    }
}

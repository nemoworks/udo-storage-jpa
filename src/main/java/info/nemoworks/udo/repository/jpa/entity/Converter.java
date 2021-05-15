package info.nemoworks.udo.repository.jpa.entity;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@javax.persistence.Converter
public class Converter implements
        AttributeConverter<Map<String, Object>, String> {

    private String convertWithStream(Map<String, Object> map) {
        return map.keySet().stream()
                .map(key -> key + "=" + map.get(key))
                .collect(Collectors.joining(", ", "{", "}"));
    }

    public Map<String, Object> convertWithStream(String mapAsString) {
        Map<String, Object> map = Arrays.stream(mapAsString.split(","))
                .map(entry -> entry.split("="))
                .collect(Collectors.toMap(entry -> entry[0].replace("{", ""), entry -> entry[1].replace("}", "")));
        return map;
    }

    @Override
    public String convertToDatabaseColumn(Map<String, Object> map) {
        return convertWithStream(map);
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String s) {
        return convertWithStream(s);
    }
}

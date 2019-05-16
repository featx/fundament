package org.featx.fundament.sort;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class StringCharsTimes implements StringCharsTime {
    @Override
    public Map<Character, Integer> timesOf(String string) {
        final Map<Character, Integer> map = new HashMap<>();
        string.chars().forEach(c -> {
            Integer o = Optional.ofNullable(map.get((char) c)).orElse(0);
            map.put((char) c, o + 1);
        });
        final Map<Character, Integer> result = new LinkedHashMap<>();
        map.entrySet().stream()
                .sorted((e1, e2) ->
                        e1.getValue().equals(e2.getValue()) ? e1.getKey() - e2.getKey() : e1.getValue() - e2.getValue())
                .forEach(e -> result.put(e.getKey(), e.getValue()));
        return result;
    }
}

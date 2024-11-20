package backend.academy.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Специальный класс для подсчета наибольших значений какого-либо типа
 *
 * @param <T> value в map-е
 */
public class FrequencyMap<T> {
    private final Map<T, Long> map = new HashMap<>();

    public void put(T key) {
        if (map.containsKey(key)) {
            map.put(key, map.get(key) + 1);
        } else {
            map.put(key, 1L);
        }
    }

    public T getMostCommon() {
        // need to compute most common key
        T mostCommon = null;
        Long counterCommon = -1L;

        for (var entry : map.entrySet()) {
            if (entry.getValue() > counterCommon) {
                mostCommon = entry.getKey();
                counterCommon = entry.getValue();
            }
        }

        return mostCommon;
    }

    public Map<T, Long> getSortedMap() {
        var list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Map<T, Long> result = new LinkedHashMap<>();

        for (var entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}

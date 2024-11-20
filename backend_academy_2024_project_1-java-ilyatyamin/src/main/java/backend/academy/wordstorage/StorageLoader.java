package backend.academy.wordstorage;

import backend.academy.enums.Difficult;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class StorageLoader {
    private StorageLoader() {
    }

    /**
     * Loads WordStorage from InputStream (JSON with arrays of WordInfo)
     * @param is InputStream, where method needs to read data
     * @return WordStorage object
     * @throws FileNotFoundException throws when pointed stream (file, string...) doesn't exist
     */
    public static WordStorage loadFromStream(InputStream is) throws FileNotFoundException {
        Word[] words = deserializeWordInfoFromJson(is);
        Map<Difficult, List<Word>> map = createMapFromWordInfoList(words);
        return new WordStorage(map);
    }

    /**
     * Deserialize JSON with WordInfo schema from InputStream
     * @param is InputStream, where method needs to read data
     * @return Array of WordInfo objects
     */
    private static Word[] deserializeWordInfoFromJson(InputStream is) {
        ObjectMapper jsonCreator = new ObjectMapper();
        Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
        try {
            return jsonCreator.readValue(reader, Word[].class);
        } catch (IOException e) {
            throw new RuntimeException("Incorrect file got, please check your file" + e.getMessage());
        }
    }

    /**
     * Creates Map from WordInfo array,
     * where key is the Difficult enum and values is ArrayList of Word with this difficlt
     * @param words Array of WordInfo objects
     * @return Map object, key = Difficult, value = ArrayList
     */
    private static Map<Difficult, List<Word>> createMapFromWordInfoList(Word[] words) {
        Map<Difficult, List<Word>> map = new HashMap<>();
        for (Difficult diff : Difficult.values()) {
            map.put(diff, new ArrayList<>());
        }

        for (Word info : words) {
            map.get(info.difficult()).add(info);
        }
        return map;
    }
}

package keywords;

import java.util.ArrayList;
import java.util.List;

/***
 * Copyright (C) RandomeStudios. All rights reserved.
 *
 * @author Sachin Gavali
 * <p>
 * =+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
 * Class        : KeyWord
 * Package      : keywords
 * <p>
 * <p>
 * This class represents an AST for langauge specific keyword
 *
 * <p>
 * =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
 */
public abstract class Keyword {
    private String mName;

    public Keyword(String name) {
        mName = name;
    }

    public static List<String> getWords(String currentLine, String seperator) {
        String[] words = currentLine.split(seperator);
        List<String> wordsList = new ArrayList<>();
        for (String word : words) {
            word = word.trim();
            if (!word.equals("")) {
                wordsList.add(word);
            }
        }
        return wordsList;
    }

    public String getName() {
        return mName;
    }


}

package keywords;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rando on 6/27/2016.
 */
public class Keyword {
    private String mName;

    public Keyword(String name){
        mName = name;
    }

    public String getName(){
        return mName;
    }

    public static List<String> getWords(String currentLine,String seperator) {
        String [] words = currentLine.split(seperator);
        List<String> wordsList = new ArrayList<>();
        for(String word : words){
            if(!word.equals("")){
                wordsList.add(word);
            }
        }
        return wordsList;
    }
}

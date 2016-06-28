package keywords;

import java.util.List;

/**
 * Created by Rando on 6/27/2016.
 */
public class Namespace extends Keyword{

    public Namespace(String keyword) {
        super(keyword);
    }

    public static Namespace read(String currentLine) {
        List<String> words = Keyword.getWords(currentLine," ");
        if(words.size() > 2){
            String name = words.get(1);
            Namespace namespace = new Namespace(name);
            return namespace;
        }else {
            return null;
        }


    }
}

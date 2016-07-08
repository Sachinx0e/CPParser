package keywords;

import java.util.List;

/**
 * Created by Rando on 6/28/2016.
 */
public class ImportK extends Keyword {

    public ImportK(String name) {
        super(name);
    }

    public static ImportK read(String currentLine){
        List<String> words = Keyword.getWords(currentLine.replace("<","").replace(">","")," ");
        if(words.size() >= 2){
            String importFileName = words.get(1);
            ImportK importk = new ImportK(importFileName);
            return importk;
        }else {
            return null;
        }
    }


}

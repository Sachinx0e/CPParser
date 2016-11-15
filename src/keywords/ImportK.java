package keywords;

import java.util.List;

/***
 * Copyright (C) RandomeStudios. All rights reserved.
 *
 * @author Sachin Gavali
 * <p>
 * =+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
 * Class        : Import
 * Package      : keywords
 * <p>
 * <p>
 * This class represents an AST for the import statements
 *
 * <p>
 * =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
 */
public class ImportK extends Keyword {

    public ImportK(String name) {
        super(name);
    }

    public static ImportK read(String currentLine) {
        List<String> words = Keyword.getWords(currentLine.replace("<", "").replace(">", ""), " ");
        if (words.size() >= 2) {
            String importFileName = words.get(1);
            ImportK importk = new ImportK(importFileName);
            return importk;
        } else {
            return null;
        }
    }


}

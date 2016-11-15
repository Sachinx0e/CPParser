package keywords;

import com.cpp.GeneratorType;

import java.util.List;

/***
 * Copyright (C) RandomeStudios. All rights reserved.
 *
 * @author Sachin Gavali
 * <p>
 * =+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
 * Class        : Namespace
 * Package      : keywords
 * <p>
 * <p>
 * This class represents an AST for namespace
 *
 * <p>
 * =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
 */

public class Namespace extends Keyword {

    private Namespace mChildNamespace;

    public Namespace(String keyword) {
        super(keyword);
    }

    public static Namespace read(String currentLine) {
        String line = currentLine.replace("{", "");
        List<String> words = Keyword.getWords(line, " ");
        if (words.size() > 1) {
            String name = words.get(1);
            Namespace namespace = new Namespace(name);
            return namespace;
        } else {
            return null;
        }


    }

    public void setChild(Namespace namespace) {
        mChildNamespace = namespace;
    }

    public Namespace getNamespace() {
        return mChildNamespace;
    }

    public String getQualifiedName() {
        if (mChildNamespace != null) {
            return getName() + "::" + mChildNamespace.getQualifiedName();
        } else {
            return getName();
        }
    }

    public String generate(GeneratorType generatorType) {
        if (generatorType == GeneratorType.CXX) {
            String name = getQualifiedName();
            StringBuilder stringBuilder = new StringBuilder();
            return "namespace" + " " + name + "{";
        } else {
            return "";
        }
    }

}

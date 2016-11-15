package keywords;

import java.util.ArrayList;
import java.util.List;


/***
 * Copyright (C) RandomeStudios. All rights reserved.
 *
 * @author Sachin Gavali
 * <p>
 * =+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
 * Class        : AST
 * Package      : keywords
 * <p>
 * <p>
 * This class represents an AST for Class keyword
 *
 * <p>
 * =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
 */

public class ClassK extends Keyword {

    private ArrayList<Constructor> mConstructors = new ArrayList<>();
    private ArrayList<Variable> mVariables = new ArrayList<>();
    private ArrayList<Function> mFunctions = new ArrayList<>();

    public ClassK(String keyword) {
        super(keyword);
    }

    public static ClassK read(String currentLine) {
        String line = currentLine.replace("{", "");
        List<String> words = Keyword.getWords(line, " ");
        if (words.size() >= 1) {
            String className = words.get(1);
            ClassK classK = new ClassK(className);
            return classK;
        } else {
            return null;
        }
    }

    public static List<String> readTemplateParams(String currentLine) {
        int brackStart = currentLine.indexOf("<");
        int bracketEnd = currentLine.lastIndexOf(">");
        String templateStr = currentLine.substring(brackStart + 1, bracketEnd);
        List<String> words = Keyword.getWords(templateStr, ",");
        return words;
    }

    public void addVariable(Variable variable) {
        mVariables.add(variable);
    }

    public void addFunctions(Function function) {
        if (!mFunctions.contains(function)) {
            mFunctions.add(function);
        }
    }

    public List<Variable> getVariables() {
        return mVariables;
    }

    public List<Function> getFunctions() {
        return mFunctions;
    }

    public void addConstructors(Constructor constructor) {
        mConstructors.add(constructor);
    }

    public List<Constructor> getConstructors() {
        return mConstructors;
    }

    public String getQualifiedName(AST ast) {
        String qualifiedName = ast.getNamespace().getQualifiedName() + "::" + getName();
        return qualifiedName;
    }

    public String getWrappedMemberName() {
        return "m" + getName();
    }
}

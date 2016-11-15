package keywords;

import com.cpp.CppKeywordNames;

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
 * This class represents an abstract syntax tree that is generated in memory by reading the c++ header file and
 * the interface defination file
 * <p>
 * =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
 */

public class AST {

    ArrayList<ImportK> mImports = new ArrayList<>();
    Namespace mNamespace = null;
    private ClassK mClass = null;
    private boolean mHasReachedStop;
    private ClassK mParentClassK = null;
    private List<String> mTemplateParamsChild;
    private List<String> mTemplateParamsParent;

    public Namespace getNamespace() {
        return mNamespace;
    }

    public void setNamespace(Namespace namespace) {
        mNamespace = namespace;
    }

    public void setClass(ClassK classK) {
        mClass = classK;
    }

    public ClassK getClassK() {
        return mClass;
    }

    public String getString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(CppKeywordNames.NAMESPACE).append(" ").append(mNamespace.getName()).append("\n");
        stringBuilder.append(" ").append(CppKeywordNames.CLASS).append(" ").append(mClass.getName()).append("\n");
        List<Variable> variables = mClass.getVariables();
        for (Variable variable : variables) {
            stringBuilder.append("  ").append(variable.toString()).append("\n");
        }

        List<Function> functions = mClass.getFunctions();
        for (Function function : functions) {
            stringBuilder.append("  ").append(function.toString()).append("\n");
        }

        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public void addImport(ImportK importK) {
        mImports.add(importK);
    }

    public boolean getHasReachedStop() {
        return mHasReachedStop;
    }

    public void setHasReachedStop(boolean hasReachedStop) {
        mHasReachedStop = hasReachedStop;
    }

    public void setParentClass(ClassK classK) {
        mParentClassK = classK;
    }

    public ClassK getParentClassK() {
        return mParentClassK;
    }

    public List<String> getTemplateParamsChild() {
        return mTemplateParamsChild;
    }

    public void setTemplateParamsChild(List<String> templateParams) {
        mTemplateParamsChild = templateParams;
    }

    public List<String> getTemplateParamsParent() {
        return mTemplateParamsParent;
    }

    public void setTemplateParamsParent(List<String> templateParams) {
        mTemplateParamsParent = templateParams;
    }

}

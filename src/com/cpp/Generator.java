/***
 * Copyright (C) RandomeStudios. All rights reserved.
 *
 * @author Sachin Gavali
 * <p>
 * =+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
 * Class        : Generator
 * Package      : com.cpp
 * <p>
 * <p>
 * This class is used for generating the wrapper header as well as source file
 * <p>
 * =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
 */

package com.cpp;

import interfaces.Interface;
import keywords.AST;
import keywords.Constructor;
import keywords.Function;
import keywords.Variable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Generator {

    private final AST mAST;
    private final String mNamespace;
    private final GeneratorType mGeneratorType;
    private final Interface mInterfaceK;
    private final File mOutPutDir;
    private final File mHeaderOutPutDir;
    private final File mSourceOutPutDir;

    public Generator(AST ast, String namespace, Interface interfaceK, GeneratorType generatorType, File outPutDir) {
        mAST = ast;
        mNamespace = namespace;
        mGeneratorType = generatorType;
        mInterfaceK = interfaceK;
        mOutPutDir = outPutDir;
        mHeaderOutPutDir = new File(outPutDir, "headers");
        mHeaderOutPutDir.mkdir();
        mSourceOutPutDir = new File(outPutDir, "sources");
        mSourceOutPutDir.mkdir();
    }

    public void generateHeader() throws IOException {

        StringBuilder stringBuilder = new StringBuilder();

        //pragma once
        stringBuilder.append("#pragma once").append("\n\n");

        //import statement for wrapped class header
        stringBuilder.append(CXXTemplates.IMPORT.replace("%", mInterfaceK.getFullHeaderName())).append("\n");
        List<String> headers = mInterfaceK.getImportFilesHeader();
        for (int i = 0; i < headers.size(); i++) {
            stringBuilder.append(CXXTemplates.IMPORT.replace("%", headers.get(i))).append("\n");
        }
        stringBuilder.append(CXXTemplates.IMPORT.replace("%", "Pointer.h"));
        stringBuilder.append("\n\n");

        stringBuilder.append("\n");

        //namespace for wrappped class
        String namespaceStr = CXXTemplates.NAMESPACE.replace("%", mNamespace);
        stringBuilder.append(namespaceStr).append("\n");

        //class name for wrapper class
        String classStr = CXXTemplates.CLASS.replace("%", mAST.getClassK().getName());
        stringBuilder.append(CXXTemplates.SPACING_1).append(classStr).append("\n");

        //public scope
        stringBuilder.append(CXXTemplates.SPACING_2).append(CppKeywordNames.PUBLIC).append("\n");

        //VARIABLES
        List<Variable> variables = mAST.getClassK().getVariables();
        for (int i = 0; i < variables.size(); i++) {
            Variable variable = variables.get(i);
            String variableStr = variable.generateDeclaration(mGeneratorType);
            stringBuilder.append(variableStr).append("\n\n");
        }

        //Pointer constructor
        stringBuilder.append(CXXTemplates.SPACING_3).append(CXXTemplates.POINTER_CONSTRUCTOR_DECLARATION.replace("%class_name", mAST.getClassK().getName())).append("\n\n");

        //start adding constructors
        List<Constructor> constructors = mAST.getClassK().getConstructors();
        for (int i = 0; i < constructors.size(); i++) {
            Constructor constructor = constructors.get(i);
            String constructorStr = CXXTemplates.SPACING_3 + constructor.generateDeclaration(mGeneratorType) + "\n";
            stringBuilder.append(constructorStr);
            stringBuilder.append("\n");
        }

        //start adding functions
        List<Function> functionList = mAST.getClassK().getFunctions();
        for (int i = 0; i < functionList.size(); i++) {
            Function function = functionList.get(i);
            String functionStr = CXXTemplates.SPACING_3 + function.generateDeclarations(mInterfaceK, mGeneratorType) + "\n";
            stringBuilder.append(functionStr);
            stringBuilder.append("\n");
        }

        //getPointer()
        stringBuilder.append(CXXTemplates.SPACING_3).append("Pointer^ getPointer();\n\n");

        //set MemOwn
        stringBuilder.append(CXXTemplates.SPACING_3).append(CXXTemplates.SET_MEM_OWN_DECLARATION).append("\n\n");

        //delete
        stringBuilder.append(CXXTemplates.SPACING_3).append(CXXTemplates.DELETE_ITEM_DECLARATION).append("\n\n");

        //isNull
        stringBuilder.append(CXXTemplates.SPACING_3).append(CXXTemplates.IS_NULL_DECLARATION).append("\n\n");

        //private member
        stringBuilder.append(CXXTemplates.SPACING_2).append(CppKeywordNames.PRIVATE).append("\n");

        //wrapped class member variable
        stringBuilder.append(CXXTemplates.SPACING_3).append(CXXTemplates.WRAPPED_POINTER.replace("%qualified_name", mAST.getClassK().getQualifiedName(mAST)).replace("%member_name", mAST.getClassK().getWrappedMemberName())).append("\n\n");

        //mem own flag
        stringBuilder.append(CXXTemplates.SPACING_3).append("bool mMemOwn = true;").append("\n\n");

        //destructor
        stringBuilder.append(CXXTemplates.SPACING_3).append(CXXTemplates.DESTRUCTOR_DECLATATION.replace("%class_name", mAST.getClassK().getName())).append("\n");

        //close class
        stringBuilder.append("   };\n");

        //close namespace
        stringBuilder.append("}\n");

        FileWriter fileWriter = new FileWriter(new File(mHeaderOutPutDir, mInterfaceK.getTranslationUnitHeaderName()));
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(stringBuilder.toString());
        bufferedWriter.flush();

        System.out.println("Generated header for class " + mAST.getClassK().getQualifiedName(mAST));

    }

    public void generateSource() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        //import statement for wrapped class header
        stringBuilder.append("#include").append(" ").append("<").append("headers\\").append(mInterfaceK.getTranslationUnitHeaderName()).append(">");
        stringBuilder.append("\n");

        //import for source files
        List<String> headers = mInterfaceK.getImportFilesSource();
        for (int i = 0; i < headers.size(); i++) {
            stringBuilder.append(CXXTemplates.IMPORT.replace("%", headers.get(i))).append("\n");
        }
        stringBuilder.append("\n\n");

        //Pointer constructor
        stringBuilder.append(CXXTemplates.POINTER_CONSTRUCTOR_DEFINATION.replace("%class_name", mAST.getClassK().getName()).replace("%qualified_name", mAST.getClassK().getQualifiedName(mAST)));

        //pointer constructor body end
        stringBuilder.append("\n\n");

        //variables
        List<Variable> variables = mAST.getClassK().getVariables();
        for (int i = 0; i < variables.size(); i++) {
            Variable variable = variables.get(i);
            String variablesStr = variable.generateDefination(mGeneratorType, mAST);
            stringBuilder.append(variablesStr).append("\n\n");
        }

        //start adding constructors
        List<Constructor> constructors = mAST.getClassK().getConstructors();
        for (int i = 0; i < constructors.size(); i++) {
            Constructor constructor = constructors.get(i);
            String constructorStr = constructor.generateDefination(mAST, mNamespace, mGeneratorType) + "\n";
            stringBuilder.append(constructorStr);
            stringBuilder.append("\n");
        }

        //start adding functions
        List<Function> functions = mAST.getClassK().getFunctions();
        for (int i = 0; i < functions.size(); i++) {
            Function function = functions.get(i);
            String functionStr = function.generateDefination(mAST, mNamespace, mGeneratorType, mInterfaceK) + "\n";
            stringBuilder.append(functionStr);
            stringBuilder.append("\n");
        }

        //getPointer()
        stringBuilder.append(CXXTemplates.GET_POINTER_DEFINATION_HEAD.replace("%class_name", mAST.getClassK().getName())).append("\n");

        //getPointer() body
        stringBuilder.append(CXXTemplates.SPACING_1).append(CXXTemplates.GET_POINTER_BODY.replace("%class_name", mAST.getClassK().getName())).append("\n");

        //close getPointer()
        stringBuilder.append("}\n\n");

        //setMemOwn body
        stringBuilder.append(CXXTemplates.SET_MEM_OWN_DEFINATION.replace("%class_name", mAST.getClassK().getName())).append("\n\n");

        //delete Item body
        stringBuilder.append(CXXTemplates.DELETE_ITEM_DEFINATION.replace("%class_name", mAST.getClassK().getName())).append("\n\n");

        //destructor body
        stringBuilder.append(CXXTemplates.DESTRUCTOR_DEFINATION.replace("%class_name", mAST.getClassK().getName())).append("\n\n");

        //is null body
        stringBuilder.append(CXXTemplates.IS_NULL_BODY.replace("%class_name", mAST.getClassK().getName()));

        FileWriter fileWriter = new FileWriter(new File(mSourceOutPutDir, mInterfaceK.getTranslationUnitSourceName()));
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(stringBuilder.toString());
        bufferedWriter.flush();

        System.out.println("Generated source for class " + mAST.getClassK().getQualifiedName(mAST) + "\n");

    }

}

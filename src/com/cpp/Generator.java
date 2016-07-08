package com.cpp;

import interfaces.Interface;
import keywords.AST;
import keywords.Constructor;
import keywords.Function;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by Rando on 7/8/2016.
 */
public class Generator {

    private final AST mAST;
    private final String mNamespace;
    private final GeneratorType mGeneratorType;
    private final Interface mInterfaceK;
    private final File mOutPutDir;
    private final File mHeaderOutPutDir;
    private final File mSourceOutPutDir;

    public Generator(AST ast, String namespace, Interface interfaceK, GeneratorType generatorType, File outPutDir){
        mAST = ast;
        mNamespace = namespace;
        mGeneratorType = generatorType;
        mInterfaceK = interfaceK;
        mOutPutDir = outPutDir;
        mHeaderOutPutDir = new File(outPutDir,"headers");
        mHeaderOutPutDir.mkdir();
        mSourceOutPutDir = new File(outPutDir,"source");
        mSourceOutPutDir.mkdir();
    }

    public void generateHeader() throws IOException {

        StringBuilder stringBuilder = new StringBuilder();

        //pragma once
        stringBuilder.append("#pragma once").append("\n\n");

        //import statement for wrapped class header
        stringBuilder.append(CXXTemplates.IMPORT.replace("%",mInterfaceK.HEADER_NAME)).append("\n");
        List<String> headers = mInterfaceK.getImportFiles();
        for(int i = 0;i<headers.size();i++){
            stringBuilder.append(CXXTemplates.IMPORT.replace("%",headers.get(i))).append("\n");
        }
        stringBuilder.append("\n");

        //namespace for wrappped class
        String namespaceStr = CXXTemplates.NAMESPACE.replace("%",mNamespace);
        stringBuilder.append(namespaceStr).append("\n");

        //class name for wrapper class
        String classStr = CXXTemplates.CLASS.replace("%",mAST.getClassK().getName());
        stringBuilder.append(CXXTemplates.SPACING_1).append(classStr).append("\n");

        //public scope
        stringBuilder.append(CXXTemplates.SPACING_2).append(CppKeywordNames.PUBLIC).append("\n");

        //start adding constructors
        List<Constructor> constructors = mAST.getClassK().getConstructors();
        for(int i = 0;i<constructors.size();i++){
            Constructor constructor = constructors.get(i);
            String constructorStr = CXXTemplates.SPACING_3 + constructor.generateDeclaration(mGeneratorType) + "\n";
            stringBuilder.append(constructorStr);
            stringBuilder.append("\n");
        }

        //start adding functions
        List<Function> functionList = mAST.getClassK().getFunctions();
        for(int i = 0;i<functionList.size();i++){
            Function function = functionList.get(i);
            if(!function.getName().contains("operator")){
                String functionStr = CXXTemplates.SPACING_3 + function.generateDeclarations(mGeneratorType) + "\n";
                stringBuilder.append(functionStr);
                stringBuilder.append("\n");
            }
        }

        //private member
        stringBuilder.append(CXXTemplates.SPACING_2).append(CppKeywordNames.PRIVATE).append("\n");

        //wrapped class member variable
        String classAsMemStr = mAST.getClassK().getQualifiedName(mAST) + " " + "m" + mAST.getClassK().getName() + ";";
        stringBuilder.append(CXXTemplates.SPACING_3).append(classAsMemStr).append("\n\n");

        //close class
        stringBuilder.append("   };\n");

        //close namespace
        stringBuilder.append("}\n");

        System.out.print("  ");
        System.out.print(stringBuilder.toString());
        System.out.print("  ");

        FileWriter fileWriter = new FileWriter(new File(mHeaderOutPutDir,mInterfaceK.getTranslationUnitHeaderName()));
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(stringBuilder.toString());
        bufferedWriter.flush();
    }

    public void generateSource() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        //import statement for wrapped class header
        stringBuilder.append("#include").append(" ").append("<").append("headers\\").append(mInterfaceK.getTranslationUnitHeaderName()).append(">");
        stringBuilder.append("\n\n");

        //start adding constructors
        List<Constructor> constructors = mAST.getClassK().getConstructors();
        for(int i = 0;i<constructors.size();i++){
            Constructor constructor = constructors.get(i);
            String constructorStr = constructor.generateDefination(mAST,mNamespace,mGeneratorType) + "\n";
            stringBuilder.append(constructorStr);
            stringBuilder.append("\n");
        }

        System.out.print("  ");
        System.out.print(stringBuilder.toString());
        System.out.print("  ");

        FileWriter fileWriter = new FileWriter(new File(mSourceOutPutDir,mInterfaceK.getTranslationUnitSourceName()));
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(stringBuilder.toString());
        bufferedWriter.flush();
    }

}
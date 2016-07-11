package com.cpp;

import interfaces.Interface;
import interfaces.InterfaceParser;
import keywords.*;
import misc.HeaderStore;

import java.io.*;
import java.util.List;

/**
 * Created by Rando on 7/8/2016.
 */
public class Worker {
    private final File mInterfaceFile;
    private final String mNamespace;
    private final File mOutPutDir;

    public Worker(File interfaceFile,String namespace,File outPutDir){
        mInterfaceFile = interfaceFile;
        mNamespace = namespace;
        mOutPutDir = outPutDir;
    }

    public void work() throws IOException {

        InterfaceParser interfaceParser = new InterfaceParser(mInterfaceFile);
        Interface interfaceK = interfaceParser.parse();

        //read the file
        AST ast = new AST();
        parseNormalHeader(ast,interfaceK);
        if(interfaceK.getIsParentTemplated()){
            parseParentHeaderTemplated(ast,interfaceK);
        }else {
            parseParentHeader(ast,interfaceK);
        }


        System.out.print(ast.getString());

        Generator cxxGenerator = new Generator(ast,mNamespace,interfaceK,GeneratorType.CXX,mOutPutDir);
        cxxGenerator.generateHeader();
        cxxGenerator.generateSource();
    }

    private void parseNormalHeader(AST ast, Interface interfaceK) throws IOException {
        File headerFile = HeaderStore.findHeader(interfaceK.getHeaderName(),interfaceK.getHeaderDirName());
        if(headerFile == null){
            throw new FileNotFoundException("Could not find " + interfaceK.getFullHeaderName());
        }
        BufferedReader bufferedReader = new BufferedReader(new FileReader(headerFile));
        String currentLine = null;

        char previousChar = 'j';

        while((currentLine = bufferedReader.readLine()) != null){

            currentLine = currentLine.trim();
            if(!ast.getHasReachedPrivate() && currentLine.length() != 0){
                LanguageContruct contruct = CppParser.getConstruct(currentLine,ast);
                switch (contruct){
                    case IMPORTS:
                        ImportK importK = ImportK.read(currentLine);
                        ast.addImport(importK);
                        break;
                    case NAMESPACE:
                        Namespace namespace = Namespace.read(currentLine);
                        if(ast.getNamespace() != null){
                            ast.getNamespace().setChild(namespace);
                        }else{
                            ast.setNamespace(namespace);
                        }
                        break;
                    case CLASS:
                        ClassK classK = ClassK.read(currentLine);
                        ast.setClass(classK);
                        if(interfaceK.getIsParentTemplated()){
                            List<String> templateParams = ClassK.readTemplateParams(currentLine);
                            ast.setTemplateParamsChild(templateParams);
                        }
                        break;
                    case VARIABLE:
                        Variable variable = Variable.read(currentLine);
                        if(ast.getClassK() != null){
                            ast.getClassK().addVariable(variable);
                        }else{
                            throw new RuntimeException("Class not found for variable : " + variable.getName());
                        }
                        break;
                    case CONSTRUCTOR:
                        boolean isIgnoredConstructor = interfaceK.isConstructorIgnored(currentLine);
                        if(!isIgnoredConstructor){
                            Constructor constructor = Constructor.read(currentLine,ast);
                            classK = ast.getClassK();
                            if(classK != null){
                                classK.addConstructors(constructor);
                            }else{
                                throw new RuntimeException("Class not found for constructor : " + constructor.getName());
                            }
                        }
                        break;
                    case DESTRUCTOR:
                        break;
                    case FUNCTION:
                        boolean isIgnoredFunction = interfaceK.isFunctionIgnored(currentLine);
                        if(!isIgnoredFunction){
                            Function function = Function.read(currentLine,ast);
                            if(ast.getClassK() != null){
                                ast.getClassK().addFunctions(function);
                            }else {
                                throw new RuntimeException("Class not found for constructor : " + function.getName());
                            }
                        }
                        break;
                    case STATEMENT:
                        break;
                    case UNKNOWN:
                        break;
                    case PRIVATE:
                        ast.setHasReachedPrivate(true);
                }
            }
        }
    }

    private void parseParentHeader(AST ast, Interface interfaceK) throws IOException {
        if(!interfaceK.getParentHeaderFileName().isEmpty()){
            File headerFile = HeaderStore.findHeader(interfaceK.getParentHeaderFileName(),interfaceK.getParentHeaderDirName());
            if(headerFile == null){
                throw new FileNotFoundException("Could not find " + interfaceK.getFullHeaderName());
            }

            BufferedReader bufferedReader = new BufferedReader(new FileReader(headerFile));
            String currentLine = null;

            ast.setHasReachedPrivate(false);

            while((currentLine = bufferedReader.readLine()) != null){

                currentLine = currentLine.trim();
                if(!ast.getHasReachedPrivate() && currentLine.length() != 0){
                    LanguageContruct contruct = CppParser.getConstruct(currentLine,ast);
                    switch (contruct){
                        case CLASS:
                            ClassK classK = ClassK.read(currentLine);
                            ast.setParentClass(classK);
                            break;
                        case VARIABLE:
                            Variable variable = Variable.read(currentLine);
                            if(ast.getClassK() != null){
                                ast.getClassK().addVariable(variable);
                            }else{
                                throw new RuntimeException("Class not found for variable : " + variable.getName());
                            }
                            break;
                        case FUNCTION:
                            boolean isIgnoredFunction = interfaceK.isFunctionIgnored(currentLine);
                            if(!isIgnoredFunction){
                                Function function = Function.read(currentLine,ast);
                                if(ast.getClassK() != null){
                                    ast.getClassK().addFunctions(function);
                                }else {
                                    throw new RuntimeException("Class not found for constructor : " + function.getName());
                                }
                            }
                            break;
                        case PRIVATE:
                            ast.setHasReachedPrivate(true);
                    }
                }
            }
        }
    }

    private void parseParentHeaderTemplated(AST ast, Interface interfaceK) throws IOException {
        if(!interfaceK.getParentHeaderFileName().isEmpty()){
            File headerFile = HeaderStore.findHeader(interfaceK.getParentHeaderFileName(),interfaceK.getParentHeaderDirName());
            if(headerFile == null){
                throw new FileNotFoundException("Could not find " + interfaceK.getFullHeaderName());
            }

            BufferedReader bufferedReader = new BufferedReader(new FileReader(headerFile));
            String currentLine = null;

            ast.setHasReachedPrivate(false);

            while((currentLine = bufferedReader.readLine()) != null){

                currentLine = currentLine.trim();
                if(!ast.getHasReachedPrivate() && currentLine.length() != 0){
                    LanguageContruct contruct = CppParser.getConstructForTemplated(currentLine,ast);
                    switch (contruct){
                        case TEMPLATE:
                            String line = currentLine.replace(CppKeywordNames.TEMPLATE,"").replace("typename","").replace(">","");
                            List<String> words = Keyword.getWords(line,",");
                            ast.setTemplateParamsParent(words);
                            break;
                        case FUNCTION:
                            line = currentLine.replace(CppKeywordNames.FUNCTION_TAG,"").trim();

                            List<String> templateParamsChild = ast.getTemplateParamsChild();
                            List<String> templateParamsParent = ast.getTemplateParamsParent();

                            int size = templateParamsParent.size();
                            for(int i = 0;i < size;i++){
                                String paramChild = templateParamsChild.get(i);
                                String paramParent = templateParamsParent.get(i);
                                line = line.replace(paramParent,paramChild);
                            }

                            boolean isIgnoredFunction = interfaceK.isFunctionIgnored(line);
                            if(!isIgnoredFunction){
                                Function function = Function.read(line,ast);
                                if(ast.getClassK() != null){
                                    ast.getClassK().addFunctions(function);
                                }else {
                                    throw new RuntimeException("Class not found for constructor : " + function.getName());
                                }
                            }
                            break;
                        case PRIVATE:
                            ast.setHasReachedPrivate(true);
                    }
                }
            }
        }
    }

}

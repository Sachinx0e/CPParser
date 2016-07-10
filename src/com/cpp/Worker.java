package com.cpp;

import interfaces.Interface;
import interfaces.InterfaceParser;
import keywords.*;
import misc.HeaderStore;

import java.io.*;

/**
 * Created by Rando on 7/8/2016.
 */
public class Worker {
    private final File mInterfaceFile;
    private final String mNamespace;
    private final File mOutPutDir;
    private String currentLine;

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
        File headerFile = HeaderStore.findHeader(interfaceK.getHeaderName(),interfaceK.getHeaderDirName());
        if(headerFile == null){
            throw new FileNotFoundException("Could not find " + interfaceK.getFullHeaderName());
        }
        BufferedReader bufferedReader = new BufferedReader(new FileReader(headerFile));
        currentLine = null;

        char previousChar = 'j';

        while((currentLine = bufferedReader.readLine()) != null){

            currentLine = currentLine.trim();
            if(!ast.getHasReachedPrivate() && currentLine.length() != 0){
                LanguageContruct contruct = CppParser.getConstruct(currentLine,ast,true);
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
                        boolean isIgnoredConstructor = interfaceK.isFunctionIgnored(currentLine);
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

        System.out.print(ast.getString());

        Generator cxxGenerator = new Generator(ast,mNamespace,interfaceK,GeneratorType.CXX,mOutPutDir);
        cxxGenerator.generateHeader();
        cxxGenerator.generateSource();
    }
}

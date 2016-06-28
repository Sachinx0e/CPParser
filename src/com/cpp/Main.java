package com.cpp;

import keywords.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    private static String currentLine;

    public static void main(String[] args) throws IOException {

        ParseExceptionHandler exceptionHandler = new ParseExceptionHandler();

        Thread.currentThread().setUncaughtExceptionHandler(exceptionHandler);

	    //read the file
        AST ast = new AST();
        BufferedReader bufferedReader = new BufferedReader(new FileReader("LocalDate.h"));
        currentLine = null;

        char previousChar = 'j';

        while((currentLine = bufferedReader.readLine()) != null){

            currentLine = currentLine.trim();
            if(currentLine.length() != 0){
                LanguageContruct contruct = CppParser.getConstruct(currentLine);
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
                        break;
                    case CONSTRUCTOR:
                        Constructor constructor = Constructor.read(currentLine);
                        classK = ast.getClassK();
                        if(classK != null){
                            classK.addConstructors(constructor);
                        }else{
                            throw new RuntimeException("Class not found for constructor " + constructor.getName());
                        }
                        break;
                    case DESTRUCTOR:
                        break;
                    case FUNCTION:
                        break;
                    case STATEMENT:
                        break;
                    case UNKNOWN:
                        break;
                }
            }
        }

        System.out.print(ast.getString());

    }

    private static class ParseExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.err.println("Error parsing line : " + currentLine);
            e.printStackTrace();
        }
    }
}

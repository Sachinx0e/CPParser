package com.company;

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
        BufferedReader bufferedReader = new BufferedReader(new FileReader("ColorHelper.h"));
        currentLine = null;

        char previousChar = 'j';

        while((currentLine = bufferedReader.readLine()) != null){

            currentLine = currentLine.trim();
            if(currentLine.length() != 0){

                //get namespace
                if(ast.getNamespace() == null){
                    Namespace namespace = Namespace.read(currentLine);
                    ast.setNamespace(namespace);
                    continue;
                }

                //get class
                if(ast.getNamespace() != null && ast.getClassK() == null){
                    ClassK classK = ClassK.read(currentLine);
                    if(classK != null){
                        ast.setClass(classK);
                    }
                    continue;
                }

                //get variables and methods
                if(ast.getNamespace() != null && ast.getClassK() != null){
                    char lastLetter = currentLine.charAt(currentLine.length() - 1);
                    if(previousChar != '{' && lastLetter == ';' & !(previousChar == '}' && lastLetter == ';')){
                        Variable variable = Variable.read(currentLine);
                        if(variable != null){
                            ClassK classK = ast.getClassK();
                            if(classK != null){
                                ast.getClassK().addVariable(variable);
                            }
                        }
                    }else if(lastLetter == '{'){
                        Function function = Function.read(currentLine);
                        ast.getClassK().addFunctions(function);
                    }
                    previousChar = lastLetter;
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

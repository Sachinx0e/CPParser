package com.cpp;

import keywords.AST;
import keywords.Keyword;

import java.util.List;

/**
 * Created by Rando on 6/28/2016.
 */
public class CppParser {
    public static LanguageContruct getConstruct(String currentLine, AST ast,boolean isHeader) {
        List<String> words = Keyword.getWords(currentLine," ");
        try{
            if(words.size() > 0){

                //import
                if(words.get(0).equals(CppKeywordNames.IMPORT)){
                    return LanguageContruct.IMPORTS;
                }

                //namespace
                else if(words.get(0).equals(CppKeywordNames.NAMESPACE)){
                    return LanguageContruct.NAMESPACE;
                }

                //class
                else if(words.get(0).equals(CppKeywordNames.CLASS) && words.get(words.size() - 1).equals("{")){
                    return LanguageContruct.CLASS;
                }

                //constructor
                else if(ast.getClassK() != null &&
                        !words.get(0).contains("~") &&
                        words.get(0).contains(ast.getClassK().getName() + "(")){
                    return LanguageContruct.CONSTRUCTOR;
                }

                //is function in header only
                else if(isHeader && !currentLine.contains("~") && currentLine.contains("(") && currentLine.contains(")") && currentLine.contains(";")){
                    return LanguageContruct.FUNCTION;
                }

                //is function in header only
                else if(!isHeader && !currentLine.contains("~") && currentLine.contains("(") && currentLine.contains(")") && currentLine.contains("{")){
                    return LanguageContruct.FUNCTION;
                }

                //variable
                else if(currentLine.contains(";") && !currentLine.contains("}")){
                    return LanguageContruct.VARIABLE;
                }

                //unknown
                else {
                    return LanguageContruct.UNKNOWN;
                }
            }else {
                return LanguageContruct.UNKNOWN;
            }
        }catch (Exception ex){
            System.out.println(currentLine);
            ex.printStackTrace();
            return LanguageContruct.UNKNOWN;
        }

    }
}

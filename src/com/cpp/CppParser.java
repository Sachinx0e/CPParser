package com.cpp;

import keywords.AST;
import keywords.Keyword;

import java.util.List;

/**
 * Created by Rando on 6/28/2016.
 */
public class CppParser {
    public static LanguageContruct getConstruct(String currentLine, AST ast) {
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

                //ignore using namespace
                else if(currentLine.contains("using namespace")){
                    return LanguageContruct.UNKNOWN;
                }

                //class
                else if(words.get(0).equals(CppKeywordNames.CLASS) && words.get(words.size() - 1).equals("{") && !currentLine.contains(";")){
                    return LanguageContruct.CLASS;
                }

                //private:
                else if(words.get(0).equals(CppKeywordNames.PRIVATE)){
                    return LanguageContruct.PRIVATE;
                }

                //constructor
                else if((ast.getClassK() != null &&
                        !words.get(0).contains("~") &&
                        words.get(0).contains(ast.getClassK().getName() + "(")) ||
                        (ast.getParentClassK() != null &&
                                !words.get(0).contains("~") &&
                                words.get(0).contains(ast.getParentClassK().getName() + "("))
                        ){
                    return LanguageContruct.CONSTRUCTOR;
                }

                //is function
                else if(!currentLine.contains("~") &&
                        currentLine.contains("(") &&
                        currentLine.contains(")") &&
                        currentLine.contains(";") &&
                        !currentLine.contains("=")){

                    if(words.get(0).equals(CppKeywordNames.CONST) || words.get(words.size() - 1).equals(CppKeywordNames.CONST + ";")){
                         return LanguageContruct.UNKNOWN;
                    }else {
                        return LanguageContruct.FUNCTION;
                    }

                }

                //variable
                else if(!currentLine.contains("~") && currentLine.contains(";") && !currentLine.contains("}") && !currentLine.contains(CppKeywordNames.CLASS)){
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

    public static LanguageContruct getConstructForTemplated(String currentLine, AST ast) {
        List<String> words = Keyword.getWords(currentLine," ");
        try{
            if(words.size() > 0){

                //template
                if(currentLine.contains(CppKeywordNames.TEMPLATE)){
                    return LanguageContruct.TEMPLATE;
                }

                //function
                else if(currentLine.contains(CppKeywordNames.FUNCTION_TAG)){
                    if(words.get(0).equals(CppKeywordNames.CONST) || words.get(words.size() - 1).equals(CppKeywordNames.CONST + ";")){
                        return LanguageContruct.UNKNOWN;
                    }else {
                        return LanguageContruct.FUNCTION;
                    }
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

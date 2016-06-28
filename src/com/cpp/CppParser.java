package com.cpp;

import keywords.Keyword;

import java.util.List;

/**
 * Created by Rando on 6/28/2016.
 */
public class CppParser {
    public static LanguageContruct getConstruct(String currentLine) {
        List<String> words = Keyword.getWords(currentLine," ");
        try{
            if(words.size() > 0){
                if(words.get(0).equals(CppKeywordNames.IMPORT)){
                    return LanguageContruct.IMPORTS;
                }
                else if(words.get(0).equals(CppKeywordNames.NAMESPACE)){
                    return LanguageContruct.NAMESPACE;
                }else if(words.get(0).equals(CppKeywordNames.CLASS) && words.get(words.size() - 1).equals("{")){
                    return LanguageContruct.CLASS;
                } else {
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

package com.cpp;

import java.util.HashMap;

/**
 * Created by Rando on 7/8/2016.
 */
public class TypeMappings {
    private static HashMap<String,String> MAPPINGS = new HashMap<>();

    static {
        MAPPINGS.put("std::string","Platform::String^");
        MAPPINGS.put("int","int32");
        MAPPINGS.put("long","int64");
        MAPPINGS.put("long long","int64");
        MAPPINGS.put("bool","bool");
        MAPPINGS.put("short","int16");
    }

    public static String getMapping(String type){
        if(type.equals(CppKeywordNames.VOID)){
            return type;
        }else {
            String mapping = MAPPINGS.get(type);
            if(mapping == null){
                mapping = type + "^";
            }
            return mapping;
        }
    }

}

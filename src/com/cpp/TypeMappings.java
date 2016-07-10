package com.cpp;

import java.util.HashMap;

/**
 * Created by Rando on 7/8/2016.
 */
public class TypeMappings {
    private static HashMap<String,String> MAPPINGS = new HashMap<>();

    static {
        MAPPINGS.put("std::string","Platform::String^");
        MAPPINGS.put("string","Platform::String^");
        MAPPINGS.put("int","int32");
        MAPPINGS.put("long","int64");
        MAPPINGS.put("long long","int64");
        MAPPINGS.put("bool","bool");
        MAPPINGS.put("short","int16");
        MAPPINGS.put("float","float32");
        MAPPINGS.put("double","float64");
        MAPPINGS.put("std::vector<int>","Windows::Foundation::Collections:: IVector<int>^");
        MAPPINGS.put("vector<int>","Windows::Foundation::Collections:: IVector<int>^");
        MAPPINGS.put("std::vector<std::string>","Windows::Foundation::Collections::IVector<Platform::String^>^");
        MAPPINGS.put("vector<std::string>","Windows::Foundation::Collections::IVector<Platform::String^>^");
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

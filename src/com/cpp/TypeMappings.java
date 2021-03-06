package com.cpp;

import java.util.HashMap;


/***
 * Copyright (C) RandomeStudios. All rights reserved.
 *
 * @author Sachin Gavali
 * <p>
 * =+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
 * Class        : TypeMappings
 * Package      : com.cpp
 * <p>
 * <p>
 * This class holds the mapping from c++ to managed types
 * <p>
 * =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
 */

public class TypeMappings {
    public static final String LIB_NAMESPACE = "RewireRuntimeComponent";
    private static HashMap<String, String> MAPPINGS = new HashMap<>();

    static {
        MAPPINGS.put("std::string", "Platform::String^");
        MAPPINGS.put("string", "Platform::String^");
        MAPPINGS.put("int", "int32");
        MAPPINGS.put("long", "int64");
        MAPPINGS.put("long long", "int64");
        MAPPINGS.put("bool", "bool");
        MAPPINGS.put("short", "int16");
        MAPPINGS.put("float", "float32");
        MAPPINGS.put("double", "float64");
        MAPPINGS.put("int64_t", "int64");
        MAPPINGS.put("std::vector<int>", "Windows::Foundation::Collections:: IVector<int>^");
        MAPPINGS.put("vector<int>", "Windows::Foundation::Collections:: IVector<int>^");
        MAPPINGS.put("std::vector<std::string>", "Windows::Foundation::Collections::IVector<Platform::String^>^");
        MAPPINGS.put("vector<std::string>", "Windows::Foundation::Collections::IVector<Platform::String^>^");
    }

    public static String getMapping(String type) {
        if (type.equals(CppKeywordNames.VOID)) {
            return type;
        } else {
            String mapping = MAPPINGS.get(type);
            if (mapping == null) {
                mapping = type + "^";
            }
            return mapping;
        }
    }

    public static String getMappingQualified(String type) {
        if (type.equals(CppKeywordNames.VOID)) {
            return type;
        } else {
            String mapping = MAPPINGS.get(type);
            if (mapping == null) {
                mapping = LIB_NAMESPACE + "::" + type + "^";
            }
            return mapping;
        }
    }


}

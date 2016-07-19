package com.cpp;

import java.util.Hashtable;

/**
 * Created by Rando on 7/8/2016.
 */
public class CXXTemplates {
    public static final String SPACING_1 = "   ";
    public static final String SPACING_2 = "      ";
    public static final String SPACING_3 = "         ";
    public static final String OBJECT_CONVERSION_BODY = "m%memberName = %paramName;";
    public static final String POINTER_CONSTRUCTOR_DECLARATION = "%class_name(Pointer^ pointer,bool memOwn);";

    public static final String POINTER_CONSTRUCTOR_DEFINATION = "RewireRuntimeComponent::%class_name::%class_name(Pointer^ pointer,bool memOwn){\n" +
                                                                    "\tm%class_name = (%qualified_name*)pointer->getAddress();\n" +
                                                                    "\tmMemOwn = memOwn;\n" +
                                                                 "}";

    public static final String POINTER_TO_NATIVE_ASSIGNMENT_EXPRESSION = "m%class_name = (%qualified_name*)pointer->getAddress();";
    public static final String DESTRUCTOR_DECLATATION = "~%class_name();";

    public static final String DESTRUCTOR_DEFINATION = "RewireRuntimeComponent::%class_name::~%class_name() {\n" +
                                                            "\tif (mMemOwn) {\n" +
                                                            "\t\tdelete m%class_name;\n" +
                                                            "\t}\n" +
                                                        "}";
    public static final String POINTER_CAST = "(%qualified_name*)";
    public static final String SET_MEM_OWN_DECLARATION = "void setMemOwn(bool memOwn);";
    public static final String SET_MEM_OWN_DEFINATION = "void RewireRuntimeComponent::%class_name::setMemOwn(bool memOwn) {\n" +
                                                               "\tmMemOwn = memOwn;\n" +
                                                         "}";


    public static final String DELETE_ITEM_DECLARATION = "void deleteItem();";
    public static final String DELETE_ITEM_DEFINATION = "void RewireRuntimeComponent::%class_name::deleteItem() {\n" +
                                                                "\tif (!mMemOwn) {\n" +
                                                                "\t\tdelete m%class_name;\n" +
                                                                "\t}\n" +
                                                         "}";



    public static String POINTER_TO_NATIVE_CAST = "*((%qualified_name*)%variable->getPointer()->getAddress())";
    public static final String NATIVE_OBJ_TO_POINTER_ASSIGNMENT_EXPRESSION = "Pointer^ pointer = ref new Pointer((__int64)new %qualified_name(%value));";
    public static final String NATIVE_POINTER_TO_POINTER_ASSIGNMENT_EXPRESSION = "Pointer^ pointer = ref new Pointer((__int64)%value);";
    public static final String GET_POINTER_DEFINATION_HEAD = "RewireRuntimeComponent::Pointer^ RewireRuntimeComponent::%class_name::getPointer(){";
    public static final String GET_POINTER_BODY = "return ref new Pointer((__int64)m%class_name);";
    public static String NAMESPACE = "namespace % {";
    public static String CLASS = "public ref class % sealed {";
    public static String IMPORT = "#include <%>";
    public static String STRING_CONVERSION_EXPRESSION = "Platform::String^ %to_name = string_utils::to_platform_string(%from_name);";
    public static String STRING_CONV_FUNC_PLATFORM_TO_STD = "string_utils::to_std_string(%from_name)";
    public static String STRING_CONV_FUNC_STD_TO_PLATFORM = "string_utils::to_platform_string(%from_name);";
    public static String OBJECT_CONVERSION_EXPRESSION = "RewireRuntimeComponent::%to_type^ %to_name = ref new RewireRuntimeComponent::%to_type(%from_name,%mem_own);";
    public static final String OBJECT_CONVERSION_CONSTRUCTOR_DEFINATION = "%to_type(%from_type %from_name);";
    public static final String WRAPPED_OBJECT = "(%dereference((%qualified_name*)%param_name->getPointer()->getAddress()))";
    public static final String WRAPPED_POINTER = "%qualified_name* %member_name = NULL;";

    public static final String NATIVE__STRING_LIST_TO_PLATFORM__STRING_LIST = "Platform::Collections::Vector<Platform::String^>^ %to_value = string_utils::to_platform_list(%from_value);";
    public static final String STRING_LIST_CONV_PLATFORM_TO_STD = "string_utils::to_native_list(%from_name)";

    public static final String NATIVE_INT_LIST_TO_PLATFORM_INT_LIST = "Platform::Collections::Vector<int>^ %to_value = int_utils::to_platform_list(%from_value);";
    public static final String INT_LIST_CONV_PLATFORM_TO_STD = "int_utils::to_native_list(%from_name)";

    public static final String PROPERTY_DECLARATION_TEMPLATE = "property %type %name {\n" +
                                                               "\t\t\t  %type get();\n" +
                                                                "\t\t }";

    public static final String PROPERTY_DEFINATION_TEMPLATE = "%type RewireRuntimeComponent::%class_name::%name::get() {\n" +
                                                                "\t%body;\n" +
                                                               "}";

    public static final String NATIVE_VARIABLE_TO_PLATFORM_CONV_BODY = "Pointer^ pointer = ref new Pointer((__int64)&%qualified_name);\n"+
                                                                "\treturn ref new RewireRuntimeComponent::%type_name(pointer,false);";

}

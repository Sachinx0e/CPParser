package com.cpp;

/**
 * Created by Rando on 7/8/2016.
 */
public class CXXTemplates {
    public static final String SPACING_1 = "   ";
    public static final String SPACING_2 = "      ";
    public static final String SPACING_3 = "         ";
    public static final String OBJECT_CONVERSION_BODY = "m%memberName = %paramName;";
    public static final String POINTER_CONSTRUCTOR = "%class_name(Pointer^ pointer);";
    public static final String POINTER_TO_NATIVE_ASSIGNMENT_EXPRESSION = "m%class_name = (%qualified_name*)pointer->getAddress();";
    public static String POINTER_TO_NATIVE_CAST = "*((%qualified_name*)%variable->getPointer())";
    public static final String NATIVE_OBJ_TO_POINTER_ASSIGNMENT_EXPRESSION = "Pointer^ pointer = ref new Pointer((__int64)new %qualified_name(%value));";
    public static final String NATIVE_POINTER_TO_POINTER_ASSIGNMENT_EXPRESSION = "Pointer^ pointer = ref new Pointer((__int64)%value);";
    public static final String GET_POINTER_DEFINATION_HEAD = "RewireRuntimeComponent::Pointer^ RewireRuntimeComponent::%class_name::getPointer(){";
    public static final String GET_POINTER_BODY = "return ref new Pointer((__int64)m%class_name);";
    public static String NAMESPACE = "namespace % {";
    public static String CLASS = "public ref class % sealed {";
    public static String IMPORT = "#include <%>";
    public static String STRING_CONVERSION_EXPRESSION = "Platform::String^ %to_name = string_utils::to_platform_string(%from_name);";
    public static String STRING_CONV_FUNC_PLATFORM_TO_STD = "string_utils::to_std_string(%from_name)";
    public static String OBJECT_CONVERSION_EXPRESSION = "RewireRuntimeComponent::%to_type^ %to_name = ref new RewireRuntimeComponent::%to_type(%from_name);";
    public static final String OBJECT_CONVERSION_CONSTRUCTOR_DEFINATION = "%to_type(%from_type %from_name);";
    public static final String WRAPPED_OBJECT = "%param_name->m%class_name";
    public static final String WRAPPED_POINTER = "%qualified_name* %member_name = NULL;";

}

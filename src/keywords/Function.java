package keywords;

import com.cpp.CXXTemplates;
import com.cpp.CppKeywordNames;
import com.cpp.GeneratorType;
import com.cpp.TypeMappings;
import interfaces.Interface;

import java.util.List;

/***
 * Copyright (C) RandomeStudios. All rights reserved.
 *
 * @author Sachin Gavali
 * <p>
 * =+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
 * Class        :
 * Package      : keywords
 * <p>
 * <p>
 * This class represents an AST for Destructor
 *
 * <p>
 * =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
 */
public class Function extends Keyword {

    private final boolean mIsVirtual;
    private List<Parameter> mParamaters;
    private boolean mIsStatic;
    private ReturnType mReturnType;
    private String mReadLine;


    public Function(boolean isVirtual, boolean isStatic, ReturnType returnType, String functionName, List<Parameter> parameters, String readLine) {
        super(functionName);
        mIsStatic = isStatic;
        mReturnType = returnType;
        mParamaters = parameters;
        mIsVirtual = isVirtual;
        mReadLine = readLine;
    }

    public static Function read(String currentLine, AST ast) {
        String line = currentLine.replace(CppKeywordNames.VIRTUAL, "").trim();
        List<String> words = Keyword.getWords(line.replace("(", " ").replace(")", " "), " ");
        if (words.size() > 0) {

            //get words list for function return types
            String returnTypesSignature = line.substring(0, line.indexOf("("));
            List<String> returnWords = Keyword.getWords(returnTypesSignature, " ");

            boolean isVirtual = returnWords.contains(CppKeywordNames.VIRTUAL);
            boolean isStatic = returnWords.contains(CppKeywordNames.STATIC);
            boolean isConst = returnWords.contains(CppKeywordNames.CONST);

            ReturnType returnType = null;
            String functionName = null;
            int returnTypeIndex = 0;
            int functionNameIndex = 1;

            if (isVirtual) {
                returnTypeIndex = 1;
                functionNameIndex = 2;
            }

            if (isStatic) {
                returnTypeIndex = returnTypeIndex + 1;
                functionNameIndex = functionNameIndex + 1;
            }

            if (isConst) {
                returnTypeIndex = returnTypeIndex + 1;
                functionNameIndex = functionNameIndex + 1;
            }

            returnType = ReturnType.read(words.get(returnTypeIndex), ast);
            functionName = words.get(functionNameIndex);

            List<Parameter> parameters = Parameter.read(line, ast);

            Function function = new Function(isVirtual, isStatic, returnType, functionName, parameters, line);
            return function;

        } else {
            return null;
        }

    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        if (mIsVirtual) {
            stringBuilder.append(CppKeywordNames.VIRTUAL).append(" ");
        }

        if (mIsStatic) {
            stringBuilder.append(CppKeywordNames.STATIC).append(" ");
        }

        stringBuilder.append(mReturnType).append(" ").append(getName());

        //build parameters
        stringBuilder.append("(");
        int count = mParamaters.size();
        for (int i = 0; i < count; i++) {
            Parameter parameter = mParamaters.get(i);
            stringBuilder.append(parameter.toString());
            if (i != count - 1) {
                stringBuilder.append(",");
            }
        }
        stringBuilder.append(")");

        return stringBuilder.toString();
    }

    public String generateDeclarations(Interface interfaceK, GeneratorType generatorType) {
        if (generatorType == GeneratorType.CXX) {
            StringBuilder stringBuilder = new StringBuilder();

            if (mIsStatic) {
                stringBuilder.append(CppKeywordNames.STATIC).append(" ");
            }

            stringBuilder.append(TypeMappings.getMapping(mReturnType.getName())).append(" ").append(interfaceK.getFunctionRenameMapping(getName()));

            //build parameters
            stringBuilder.append("(");
            int count = mParamaters.size();
            for (int i = 0; i < count; i++) {
                Parameter parameter = mParamaters.get(i);
                stringBuilder.append(parameter.generate(generatorType));
                if (i != count - 1) {
                    stringBuilder.append(",");
                }
            }
            stringBuilder.append(");");

            return stringBuilder.toString();

        } else {
            return null;
        }
    }

    public String generateDefination(AST ast, String namespace, GeneratorType generatorType, Interface interfaceK) {
        if (generatorType == GeneratorType.CXX) {
            StringBuilder stringBuilder = new StringBuilder();

            //return type
            if (mReturnType.getIsObject()) {
                stringBuilder.append(namespace).append("::");
            }
            stringBuilder.append(TypeMappings.getMapping(mReturnType.getName()));

            //space
            stringBuilder.append(" ");

            //fully qualified function names
            stringBuilder.append(namespace).append("::").append(ast.getClassK().getName());
            stringBuilder.append("::").append(interfaceK.getFunctionRenameMapping(getName()));

            //build parameters
            stringBuilder.append("(");
            int count = mParamaters.size();
            for (int i = 0; i < count; i++) {
                Parameter parameter = mParamaters.get(i);
                stringBuilder.append(parameter.generate(generatorType));
                if (i != count - 1) {
                    stringBuilder.append(",");
                }
            }
            stringBuilder.append(")");

            //build body
            stringBuilder.append("{\n");

            StringBuilder functionCallStrBuilder = new StringBuilder();

            if (mReturnType.isPointer()) {
                functionCallStrBuilder.append(CXXTemplates.POINTER_CAST.replace("%qualified_name", mReturnType.getQualifedName()));
            }

            //function call operator
            if (mIsStatic) {
                functionCallStrBuilder.append(ast.getClassK().getQualifiedName(ast));
                functionCallStrBuilder.append("::");
            } else {
                //init member variable
                String mVariableName = "m" + ast.getClassK().getName();
                functionCallStrBuilder.append(mVariableName);
                functionCallStrBuilder.append("->");
            }

            //functionName
            functionCallStrBuilder.append(getName());

            //pass params to function
            functionCallStrBuilder.append("(");
            for (int i = 0; i < count; i++) {
                Parameter parameter = mParamaters.get(i);
                if (parameter.getIsObject()) {
                    String dereference;
                    if (parameter.getIsPointer()) {
                        dereference = "";
                    } else {
                        dereference = "*";
                    }

                    functionCallStrBuilder.append(CXXTemplates.WRAPPED_OBJECT.
                            replace("%dereference", dereference).
                            replace("%param_name", parameter.getName()).
                            replace("%qualified_name", parameter.getQualifiedName()));
                } else if (parameter.getIsString()) {
                    functionCallStrBuilder.append(CXXTemplates.STRING_CONV_FUNC_PLATFORM_TO_STD.replace("%from_name", parameter.getName()));
                } else if (parameter.getIsListString()) {
                    functionCallStrBuilder.append(CXXTemplates.STRING_LIST_CONV_PLATFORM_TO_STD.replace("%from_name", parameter.getName()));
                } else if (parameter.getIsListInt()) {
                    functionCallStrBuilder.append(CXXTemplates.INT_LIST_CONV_PLATFORM_TO_STD.replace("%from_name", parameter.getName()));
                } else {
                    functionCallStrBuilder.append(parameter.getName());
                }
                if (i != count - 1) {
                    functionCallStrBuilder.append(",");
                }
            }

            functionCallStrBuilder.append(");\n");

            //check if return type is void
            if (!mReturnType.getName().equals(CppKeywordNames.VOID)) {
                stringBuilder.append(CXXTemplates.SPACING_1).append(mReturnType.getQualifedName());
                if (mReturnType.isPointer()) {
                    stringBuilder.append("*");
                }
                stringBuilder.append(" ").append("returnValue").append(" = ");
                stringBuilder.append(functionCallStrBuilder.toString());
                String conversionStr;
                if (mReturnType.getIsObject()) {
                    if (!mReturnType.isPointer()) {
                        stringBuilder.append(CXXTemplates.SPACING_1).append(CXXTemplates.NATIVE_OBJ_TO_POINTER_ASSIGNMENT_EXPRESSION.replace("%qualified_name", mReturnType.getQualifedName())
                                .replace("%value", "returnValue"));
                        stringBuilder.append("\n");
                    } else {
                        stringBuilder.append(CXXTemplates.SPACING_1).append(CXXTemplates.NATIVE_POINTER_TO_POINTER_ASSIGNMENT_EXPRESSION.replace("%value", "returnValue"));
                        stringBuilder.append("\n");
                    }

                    //convert return value
                    String memOwn = interfaceK.getMemOwnStr(getReadLine());
                    conversionStr = mReturnType.getConversionString("pointer", "convertedValue", memOwn);
                } else {
                    conversionStr = mReturnType.getConversionString("returnValue", "convertedValue", null) + ";";
                }

                stringBuilder.append(CXXTemplates.SPACING_1).append(conversionStr).append("\n");


                stringBuilder.append(CXXTemplates.SPACING_1).append("return").append(" ").append("convertedValue").append(";");
                stringBuilder.append("\n");
            } else {
                stringBuilder.append(CXXTemplates.SPACING_1);
                stringBuilder.append(functionCallStrBuilder.toString());
            }


            //close the body
            stringBuilder.append("}");

            return stringBuilder.toString();

        } else {
            return null;
        }
    }

    private String getReadLine() {
        return mReadLine;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Function) {
            Function rFunction = (Function) obj;
            if (mIsStatic == rFunction.mIsStatic &&
                    mReturnType != null &&
                    mReturnType.equals(rFunction.mReturnType) &&
                    getName().equals(rFunction.getName()) &&
                    mParamaters.size() == rFunction.mParamaters.size()) {
                int size = mParamaters.size();
                for (int i = 0; i < size; i++) {
                    Parameter lParameter = mParamaters.get(i);
                    Parameter rParamater = rFunction.mParamaters.get(i);
                    if (!lParameter.equals(rParamater)) {
                        return false;
                    }
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}

package keywords;

import com.cpp.CXXTemplates;
import com.cpp.CppKeywordNames;
import com.cpp.GeneratorType;
import com.cpp.TypeMappings;
import interfaces.Interface;

import java.util.List;

/**
 * Created by Rando on 6/27/2016.
 */
public class Function extends Keyword {

    private final boolean mIsVirtual;
    private List<Parameter> mParamaters;
    private boolean mIsStatic;
    private ReturnType mReturnType;


    public Function(boolean isVirtual, boolean isStatic, ReturnType returnType, String functionName, List<Parameter> parameters) {
        super(functionName);
        mIsStatic = isStatic;
        mReturnType = returnType;
        mParamaters = parameters;
        mIsVirtual = isVirtual;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        if(mIsVirtual){
            stringBuilder.append(CppKeywordNames.VIRTUAL).append(" ");
        }

        if(mIsStatic){
            stringBuilder.append(CppKeywordNames.STATIC).append(" ");
        }

        stringBuilder.append(mReturnType).append(" ").append(getName());

        //build parameters
        stringBuilder.append("(");
        int count = mParamaters.size();
        for(int i = 0;i<count;i++){
            Parameter parameter = mParamaters.get(i);
            stringBuilder.append(parameter.toString());
            if(i != count -1){
                stringBuilder.append(",");
            }
        }
        stringBuilder.append(")");

        return stringBuilder.toString();
    }

    public static Function read(String currentLine,AST ast) {

        List<String> words = Keyword.getWords(currentLine.replace("("," ").replace(")"," ")," ");
        if(words.size() > 0){

            boolean isVirtual = false;
            if(words.get(0).equals(CppKeywordNames.VIRTUAL)){
                isVirtual = true;
            }

            boolean isStatic = false;
            if(isVirtual){
                if(words.get(1).equals(CppKeywordNames.STATIC)){
                    isStatic = true;
                }
            }else {
                if(words.get(0).equals(CppKeywordNames.STATIC)){
                    isStatic = true;
                }
            }


            ReturnType returnType = null;
            String functionName = null;
            int returnTypeIndex = 0;
            int functionNameIndex = 1;


            if((isVirtual && !isStatic) || (!isVirtual && isStatic)){
                returnTypeIndex = 1;
                functionNameIndex = 2;
            }else if(isVirtual && isStatic){
                returnTypeIndex = 2;
                functionNameIndex = 3;
            }

            returnType = ReturnType.read(words.get(returnTypeIndex),ast);
            functionName = words.get(functionNameIndex);

            List<Parameter> parameters = Parameter.read(currentLine,ast);

            Function function = new Function(isVirtual,isStatic,returnType,functionName,parameters);
            return function;

        }else {
            return null;
        }

    }

    public String generateDeclarations(Interface interfaceK,GeneratorType generatorType){
        if(generatorType == GeneratorType.CXX){
            StringBuilder stringBuilder = new StringBuilder();

            if(mIsVirtual){
                stringBuilder.append(CppKeywordNames.VIRTUAL).append(" ");
            }

            if(mIsStatic){
                stringBuilder.append(CppKeywordNames.STATIC).append(" ");
            }

            stringBuilder.append(TypeMappings.getMapping(mReturnType.getName())).append(" ").append(interfaceK.getFunctionRenameMapping(getName()));

            //build parameters
            stringBuilder.append("(");
            int count = mParamaters.size();
            for(int i = 0;i<count;i++){
                Parameter parameter = mParamaters.get(i);
                stringBuilder.append(parameter.generate(generatorType));
                if(i != count -1){
                    stringBuilder.append(",");
                }
            }
            stringBuilder.append(");");

            return stringBuilder.toString();

        }else {
            return null;
        }
    }

    public String generateDefination(AST ast, String namespace, GeneratorType generatorType,Interface interfaceK) {
        if(generatorType == GeneratorType.CXX){
            StringBuilder stringBuilder = new StringBuilder();

            //return type
            if(mReturnType.getIsObject()){
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
            for(int i = 0;i<count;i++){
                Parameter parameter = mParamaters.get(i);
                stringBuilder.append(parameter.generate(generatorType));
                if(i != count -1){
                    stringBuilder.append(",");
                }
            }
            stringBuilder.append(")");

            //build body
            stringBuilder.append("{\n");

            StringBuilder functionCallStrBuilder = new StringBuilder();



            //function call operator
            if(mIsStatic){
                functionCallStrBuilder.append(ast.getClassK().getQualifiedName(ast));
                functionCallStrBuilder.append("::");
            }else {
                //init member variable
                String mVariableName = "m" + ast.getClassK().getName();
                functionCallStrBuilder.append(mVariableName);
                functionCallStrBuilder.append("->");
            }

            //functionName
            functionCallStrBuilder.append(getName());

            //pass params to function
            functionCallStrBuilder.append("(");
            for(int i = 0;i<count;i++){
                Parameter parameter = mParamaters.get(i);
                if(parameter.getIsObject()){
                    String dereference;
                    if(parameter.getIsPointer()){
                        dereference = "";
                    }else {
                        dereference = "*";
                    }
                    functionCallStrBuilder.append(dereference).append(CXXTemplates.WRAPPED_OBJECT.replace("%param_name",parameter.getName()).replace("%class_name",parameter.getType()));
                }else if(parameter.getIsString()){

                }
                else{
                    functionCallStrBuilder.append(parameter.getName());
                }
                if(i != count -1){
                    functionCallStrBuilder.append(",");
                }
            }

            functionCallStrBuilder.append(");\n");

            //check if return type is void
            if(!mReturnType.getName().equals(CppKeywordNames.VOID)){
                stringBuilder.append(CXXTemplates.SPACING_1).append(mReturnType.getQualifedName()).append(" ").append("returnValue").append(" = ");
                stringBuilder.append(functionCallStrBuilder.toString());
                String conversionStr;
                if(mReturnType.getIsObject()){
                    if(!mReturnType.isPointer()){
                        stringBuilder.append(CXXTemplates.SPACING_1).append(CXXTemplates.NATIVE_OBJ_TO_POINTER_ASSIGNMENT_EXPRESSION.replace("%qualified_name",mReturnType.getQualifedName())
                                .replace("%value","returnValue"));
                        stringBuilder.append("\n");
                    }else {
                        stringBuilder.append(CXXTemplates.SPACING_1).append(CXXTemplates.NATIVE_POINTER_TO_POINTER_ASSIGNMENT_EXPRESSION.replace("%value","returnValue"));
                    }

                    //convert return value
                    conversionStr = mReturnType.getConversionString("pointer","convertedValue");
                }else {
                    conversionStr = mReturnType.getConversionString("returnValue","convertedValue") + ";";
                }

                stringBuilder.append(CXXTemplates.SPACING_1).append(conversionStr).append("\n");


                stringBuilder.append(CXXTemplates.SPACING_1).append("return").append(" ").append("convertedValue").append(";");
                stringBuilder.append("\n");
            }else {
                stringBuilder.append(CXXTemplates.SPACING_1);
                stringBuilder.append(functionCallStrBuilder.toString());
            }


            //close the body
            stringBuilder.append("}");

            return stringBuilder.toString();

        }else {
            return null;
        }
    }
}

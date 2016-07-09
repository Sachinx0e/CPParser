package keywords;

import com.cpp.CppKeywordNames;
import com.cpp.GeneratorType;
import com.cpp.TypeMappings;

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

    public String generateDeclarations(GeneratorType generatorType){
        if(generatorType == GeneratorType.CXX){
            StringBuilder stringBuilder = new StringBuilder();

            if(mIsVirtual){
                stringBuilder.append(CppKeywordNames.VIRTUAL).append(" ");
            }

            if(mIsStatic){
                stringBuilder.append(CppKeywordNames.STATIC).append(" ");
            }

            stringBuilder.append(TypeMappings.getMapping(mReturnType.getName())).append(" ").append(getName());

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

    public static Function read(String currentLine) {

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

            returnType = ReturnType.read(words.get(returnTypeIndex));
            functionName = words.get(functionNameIndex);

            List<Parameter> parameters = Parameter.read(currentLine);

            Function function = new Function(isVirtual,isStatic,returnType,functionName,parameters);
            return function;

        }else {
            return null;
        }

    }
}

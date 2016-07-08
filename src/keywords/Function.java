package keywords;

import com.cpp.CppKeywordNames;
import com.cpp.GeneratorType;
import com.cpp.TypeMappings;

import java.util.List;

/**
 * Created by Rando on 6/27/2016.
 */
public class Function extends Keyword {

    private List<Parameter> mParamaters;
    private boolean mIsStatic;
    private ReturnType mReturnType;


    public Function(boolean isStatic, ReturnType returnType, String functionName, List<Parameter> parameters) {
        super(functionName);
        mIsStatic = isStatic;
        mReturnType = returnType;
        mParamaters = parameters;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
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

    public String generate(GeneratorType generatorType){
        if(generatorType == GeneratorType.CXX){
            StringBuilder stringBuilder = new StringBuilder();
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

            boolean isStatic = false;
            if(words.get(0).equals(CppKeywordNames.STATIC)){
                 isStatic = true;
            }

            ReturnType returnType = null;
            String functionName = null;
            if(isStatic){
                returnType = ReturnType.read(words.get(1));
                functionName = words.get(2);
            }else {
                returnType = ReturnType.read(words.get(0));
                functionName = words.get(1);
            }

            List<Parameter> parameters = Parameter.read(currentLine);

            Function function = new Function(isStatic,returnType,functionName,parameters);
            return function;

        }else {
            return null;
        }

    }
}

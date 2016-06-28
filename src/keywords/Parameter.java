package keywords;

import com.cpp.CppKeywordNames;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rando on 6/28/2016.
 */
public class Parameter extends Keyword {

    private final String mType;
    private final boolean mIsConst;
    private final boolean mIsReference;
    private final boolean mIsPointer;

    public Parameter(boolean isConst, String type, String name,boolean isReference,boolean isPointer) {
        super(name);
        mType = type;
        mIsConst = isConst;
        mIsReference = isReference;
        mIsPointer = isPointer;
    }

    public String getType() {
        return mType;
    }

    public boolean getIsConst(){
        return mIsConst;
    }

    public boolean getIsReference(){
        return mIsReference;
    }

    public static List<Parameter> read(String currentLine) {
        List<Parameter> parameterList = new ArrayList<>();
        int index_bracket_open = currentLine.lastIndexOf("(");
        int index_bracket_close = currentLine.lastIndexOf(")");
        if(index_bracket_open != -1 && index_bracket_close != -1 && index_bracket_open != index_bracket_close){
            String parametersStr = currentLine.substring(index_bracket_open + 1,index_bracket_close);
            List<String> paramsArray = Keyword.getWords(parametersStr,",");
            for(String param : paramsArray){
                List<String> singleParam = Keyword.getWords(param," ");

                //ex -> int variableName&
                if(singleParam.size() == 2){
                    boolean isConst = false;

                    //type and ref
                    String type;
                    boolean isRef;
                    boolean isPointer;
                    if(singleParam.get(0).contains("&")){
                        type = singleParam.get(0).replace("&","");
                        isRef = true;
                        isPointer = false;
                    }else if(singleParam.get(0).contains("*")){
                        type = singleParam.get(0).replace("*","");
                        isRef = true;
                        isPointer = false;
                    }else {
                        type = singleParam.get(0);
                        isRef = false;
                        isPointer = false;
                    }

                    // name
                    String variableName =singleParam.get(1);

                    Parameter parameter = new Parameter(isConst,type,variableName,isRef,isPointer);
                    parameterList.add(parameter);
                }else if(singleParam.size() == 3){

                    //const
                    boolean isConst = false;
                    if(singleParam.get(0).equals(CppKeywordNames.CONST)){
                        isConst = true;
                    }

                    //type and ref
                    int typeIndex;
                    if(isConst){
                        typeIndex = 1;
                    }else {
                        typeIndex = 0;
                    }

                    String type;
                    boolean isRef;
                    boolean isPointer;
                    if(singleParam.get(typeIndex).contains("&")){
                        type = singleParam.get(typeIndex).replace("&","");
                        isRef = true;
                        isPointer = false;
                    }else if(singleParam.get(typeIndex).contains("*")){
                        type = singleParam.get(typeIndex).replace("*","");
                        isRef = true;
                        isPointer = false;
                    }else {
                        type = singleParam.get(typeIndex);
                        isRef = false;
                        isPointer = false;
                    }

                    //name
                    int nameIndex;
                    if(isConst){
                        nameIndex = 2;
                    }else {
                        nameIndex = 1;
                    }
                    String variableName = singleParam.get(nameIndex);

                    Parameter parameter = new Parameter(isConst,type,variableName,isRef,isPointer);
                    parameterList.add(parameter);
                } else if(singleParam.size() == 4){
                    boolean isConst = true;
                    boolean isRef;
                    boolean isPointer;

                    //type and ref
                    String type;
                    if(singleParam.get(2).contains("&")){
                        type = singleParam.get(1) + " " + singleParam.get(2).replace("&","");
                        isRef = true;
                        isPointer = false;
                    }else if(singleParam.get(2).contains("*")){
                        type = singleParam.get(1) + " " + singleParam.get(2).replace("*","");
                        isRef = true;
                        isPointer = false;
                    }else {
                        type = singleParam.get(1) + " " + singleParam.get(2);
                        isRef = false;
                        isPointer = false;
                    }



                    //name
                    String variableName = singleParam.get(3);

                    Parameter parameter = new Parameter(isConst,type,variableName,isRef,isPointer);
                    parameterList.add(parameter);

                }

            }
        }

        return parameterList;

    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(mType).append(" ").append(getName());
        return stringBuilder.toString();
    }
}

package keywords;

import com.cpp.CppKeywordNames;
import com.cpp.GeneratorType;
import com.cpp.TypeMappings;

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
    private final String mNamespace;

    public Parameter(boolean isConst,String type,String namespace, String name, boolean isReference, boolean isPointer) {
        super(name);
        mType = type;
        mIsConst = isConst;
        mIsReference = isReference;
        mIsPointer = isPointer;
        mNamespace = namespace;
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

    public boolean getIsPointer(){
        return mIsPointer;
    }

    public static List<Parameter> read(String currentLine,AST ast) {
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
                    String [] words;
                    String type;
                    String namespace;
                    boolean isRef;
                    boolean isPointer;
                    if(singleParam.get(0).contains("&")){
                        words = getNamespace(singleParam.get(0).replace("&",""),ast);
                        isRef = true;
                        isPointer = false;
                    }else if(singleParam.get(0).contains("*")){
                        words = getNamespace(singleParam.get(0).replace("*",""),ast);
                        isRef = false;
                        isPointer = true;
                    }else {
                        words = getNamespace(singleParam.get(0),ast);
                        isRef = false;
                        isPointer = false;
                    }

                    type = words[0];
                    namespace = words[1];

                    // name
                    String variableName =singleParam.get(1);

                    Parameter parameter = new Parameter(isConst,type,namespace,variableName,isRef,isPointer);
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

                    String [] words;
                    String type;
                    String namespace;
                    String paramStr;
                    boolean isRef;
                    boolean isPointer;
                    if(singleParam.get(typeIndex).contains("&")){
                        paramStr = singleParam.get(typeIndex).replace("&","");
                        isRef = true;
                        isPointer = false;
                    }else if(singleParam.get(typeIndex).contains("*")){
                        paramStr = singleParam.get(typeIndex).replace("*","");
                        isRef = false;
                        isPointer = true;
                    }else {
                        paramStr = singleParam.get(typeIndex);
                        isRef = false;
                        isPointer = false;
                    }

                    words = getNamespace(paramStr,ast);
                    type = words[0];
                    namespace = words[1];

                    //name
                    int nameIndex;
                    if(isConst){
                        nameIndex = 2;
                    }else {
                        nameIndex = 1;
                    }
                    String variableName = singleParam.get(nameIndex);

                    Parameter parameter = new Parameter(isConst, type,namespace,variableName,isRef,isPointer);
                    parameterList.add(parameter);
                } else if(singleParam.size() == 4){
                    boolean isConst = true;
                    boolean isRef;
                    boolean isPointer;

                    //type and ref
                    String [] words;
                    String type;
                    String namespace;
                    String paramStr;

                    if(singleParam.get(2).contains("&")){
                        paramStr = singleParam.get(1) + " " + singleParam.get(2).replace("&","");
                        isRef = true;
                        isPointer = false;
                    }else if(singleParam.get(2).contains("*")){
                        paramStr = singleParam.get(1) + " " + singleParam.get(2).replace("*","");
                        isRef = false;
                        isPointer = true;
                    }else {
                        paramStr = singleParam.get(1) + " " + singleParam.get(2);
                        isRef = false;
                        isPointer = false;
                    }

                    words = getNamespace(paramStr,ast);
                    type = words[0];
                    namespace = words[1];

                    //name
                    String variableName = singleParam.get(3);

                    Parameter parameter = new Parameter(isConst, type,namespace,variableName,isRef,isPointer);
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

    public String generate(GeneratorType generatorType) {
        if(generatorType == GeneratorType.CXX){
            return TypeMappings.getMapping(mType) + " " + getName();
        }else {
            return null;
        }
    }

    public boolean getIsObject() {
        if(Character.isUpperCase(getType().charAt(0))){
            return true;
        }else {
            return false;
        }
    }


    public boolean getIsString() {
        if(getType().equals("std::string") || getType().equals("string")){
            return true;
        }else {
            return false;
        }
    }

    public boolean getIsListString() {
        if(getQualifiedName().equals("std::vector<std::string>") || getQualifiedName().equals("std::vector<string>")){
            return true;
        }else {
            return false;
        }
    }

    public boolean getIsListInt() {
        if(getQualifiedName().equals("std::vector<int>")){
            return true;
        }else {
            return false;
        }
    }

    public static String[] getNamespace(String paramStr,AST ast){
        String[] words = new String[2];
        String namespace = null;
        String name;
        List<String> namespaceWords = Keyword.getWords(paramStr,"::");
        if(namespaceWords.size() > 1){
            for(int i = 0;i<namespaceWords.size()-1;i++){
                if(i == 0){
                    namespace = namespaceWords.get(i);
                }
                else{
                    namespace = namespace + "::" + namespaceWords.get(i);
                }
            }
            name = namespaceWords.get(namespaceWords.size() - 1);
        }else {
            name = paramStr;
            namespace = ast.getNamespace().getQualifiedName();
        }
        words[0] = name;
        words[1] = namespace;
        return words;
    }

    public CharSequence getQualifiedName() {
        return mNamespace + "::" + getType();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Parameter){
            Parameter rValue = (Parameter) obj;
            if(mType.equals(rValue.mType) &&
                    mIsConst == rValue.mIsConst &&
                    mIsPointer == rValue.mIsPointer &&
                    mIsReference == rValue.mIsReference &&
                    mNamespace != null && mNamespace.equals(rValue.mNamespace)
                    ){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }
}

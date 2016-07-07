package keywords;

import com.cpp.CppKeywordNames;

import java.util.List;

/**
 * Created by Rando on 6/27/2016.
 */
public class Variable extends Keyword {

    private final String mType;
    private final boolean mIsConst;
    private final boolean mIsStatic;
    private final boolean mIsRef;
    private final boolean mIsPointer;

    public Variable(boolean isStatic,boolean isConst,String type,boolean isRef,boolean isPointer,String keyword) {
        super(keyword);
        mType = type;
        mIsStatic = isStatic;
        mIsConst = isConst;
        mIsRef = isRef;
        mIsPointer = isPointer;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if(mIsStatic){
            stringBuilder.append(CppKeywordNames.STATIC).append(" ");
        }

        if(mIsConst){
            stringBuilder.append(CppKeywordNames.CONST).append(" ");
        }

        stringBuilder.append(mType).append(" ").append(getName());

        if(mIsRef){
            stringBuilder.append("&");
        }

        if(mIsPointer){
            stringBuilder.append("*");
        }


        return stringBuilder.toString();
    }

    public static Variable read(String currentLine){
        List<String> words = Keyword.getWords(currentLine.replace(";","")," ");
        if(words.size() > 0){
            boolean isStatic = false;
            boolean isConst = false;

            if(words.get(0).equals(CppKeywordNames.STATIC)){
                isStatic = true;
                if(words.get(1).equals(CppKeywordNames.CONST)){
                    isConst = true;
                }else {
                    isConst = false;
                }
            }else if(words.get(0).equals(CppKeywordNames.CONST)){
                isStatic = false;
                isConst = true;
            }

            String type;
            String name;
            int typeIndex;
            int nameIndex;
            if(isStatic && isConst){
                typeIndex = 2;
                nameIndex = 3;
            }else if(isStatic || isConst){
                typeIndex = 1;
                nameIndex = 2;
            }
            else {
                typeIndex = 0;
                nameIndex = 1;
            }


            boolean isRef;
            boolean isPointer;
            if(words.get(typeIndex).contains("&")){
                isRef = true;
                isPointer = false;
                type = words.get(typeIndex).replace("&","");
            }else if(words.get(typeIndex).contains("*")){
                isRef = false;
                isPointer = true;
                type = words.get(typeIndex).replace("*","");
            }else {
                isRef = false;
                isPointer = false;
                type = words.get(typeIndex);
            }

            name = words.get(nameIndex);

            Variable variable = new Variable(isStatic,isConst,type,isRef,isPointer,name);
            return variable;

        }
        return null;
    }


}

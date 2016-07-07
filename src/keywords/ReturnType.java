package keywords;

import java.util.List;

/**
 * Created by Rando on 6/29/2016.
 */
public class ReturnType extends Keyword {

    private final boolean mIsReference;
    private final boolean mIsPointer;

    public ReturnType(String name, boolean isReference, boolean isPointer) {
        super(name);
        mIsReference = isReference;
        mIsPointer = isPointer;
    }


    public boolean isReference() {
        return mIsReference;
    }

    public boolean isPointer() {
        return mIsPointer;
    }

    public static ReturnType read(String currentLine){
        List<String> words = Keyword.getWords(currentLine," ");
        boolean isRef;
        boolean isPointer;
        String name;
        if(words.get(0).contains("&")){
            isRef = true;
            isPointer = false;
            name = words.get(0).replace("&","");
        }else if(words.get(0).contains("*")){
            isRef = false;
            isPointer = true;
            name = words.get(0).replace("*","");
        }else {
            isRef=false;
            isPointer=false;
            name = words.get(0);
        }

        ReturnType returnType = new ReturnType(name,isRef,isPointer);
        return returnType;

    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getName());
        if(isReference()){
            stringBuilder.append("&");
        }else if(isPointer()){
            stringBuilder.append("*");
        }
        return stringBuilder.toString();
    }
}

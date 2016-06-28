package keywords;

import com.cpp.CppKeywordNames;

import java.util.List;

/**
 * Created by Rando on 6/27/2016.
 */
public class Variable extends Keyword {

    private final String mType;
    private boolean mIsStatic;

    public Variable(boolean isStatic,String type,String keyword) {
        super(keyword);
        mType = type;
        setIsStatic(isStatic);
    }

    public void setIsStatic(boolean isStatic){
        mIsStatic = isStatic;
    }

    public String getType(){
        return mType;
    }

    public boolean getIsStatic(){
        return mIsStatic;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Variable){
            Variable rVariable = (Variable) obj;
            Variable lVariable = this;
            if(rVariable.mType.equals(lVariable.getType())
                    && rVariable.getName().equals(lVariable.getName())
                    && rVariable.getIsStatic() == lVariable.getIsStatic()){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if(mIsStatic){
            stringBuilder.append(CppKeywordNames.STATIC).append(" ");
        }
        stringBuilder.append(mType).append(" ").append(getName());
        return stringBuilder.toString();
    }

    public static Variable read(String currentLine){
        List<String> words = Keyword.getWords(currentLine.replace(";","")," ");
        if(words.size() > 0){
            boolean isStatic = false;
            if(words.get(0).equals(CppKeywordNames.STATIC)){
                isStatic = true;
            }

            String type;
            String name;
            if(isStatic){
                type = words.get(1);
                name = words.get(2);
            }else {
                type = words.get(0);
                name = words.get(1);
            }

            Variable variable = new Variable(isStatic,type,name);
            return variable;

        }
        return null;
    }


}

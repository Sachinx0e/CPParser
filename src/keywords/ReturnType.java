package keywords;

import com.cpp.CXXTemplates;

import java.util.List;

/**
 * Created by Rando on 6/29/2016.
 */
public class ReturnType extends Keyword {

    private final boolean mIsReference;
    private final boolean mIsPointer;
    private final String mNamespace;

    public ReturnType(String name, String namespace, boolean isReference, boolean isPointer) {
        super(name);
        mIsReference = isReference;
        mIsPointer = isPointer;
        mNamespace = namespace;
    }


    public boolean isReference() {
        return mIsReference;
    }

    public boolean isPointer() {
        return mIsPointer;
    }

    public String getNamespace(){
        return mNamespace;
    }

    public static ReturnType read(String currentLine,AST ast){
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

        String namespaceExtractionStr;
        if(currentLine.contains("<") && currentLine.contains(">") ){
            List<String> wordsNamespace = Keyword.getWords(currentLine.replace("<"," ").replace(">"," ")," ");
            namespaceExtractionStr = wordsNamespace.get(1);
        }else {
            namespaceExtractionStr = currentLine;
        }

        List<String> namespaceWords = Keyword.getWords(namespaceExtractionStr,"::");
        String namespace = null;
        if(namespaceWords.size() > 1){
            for(int i = 0;i<namespaceWords.size()-1;i++){
                if(i == 0){
                    namespace = namespaceWords.get(i);
                }
                else{
                    namespace = namespace + "::" + namespaceWords.get(i);
                }
            }
            if(currentLine.contains("<") && currentLine.contains(">")){
                name = currentLine.trim();
            }else {
                name = namespaceWords.get(namespaceWords.size() - 1).replace("*","").replace("&","");
            }

        }else {
            namespace = ast.getNamespace().getQualifiedName();
        }

        ReturnType returnType = new ReturnType(name,namespace,isRef,isPointer);
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

    public String getConversionString(String fromName, String toName, String memOwn) {
        if(getName().equals("std::string") || getName().equals("string")){
            String conversionStr = CXXTemplates.STRING_CONVERSION_EXPRESSION.replace("%to_name",toName).replace("%from_name",fromName);
            return conversionStr;
        }else if(getName().equals("std::vector<std::string>") || getName().equals("std::vector<string>")){
            String conversionStr = CXXTemplates.NATIVE__STRING_LIST_TO_PLATFORM__STRING_LIST.replace("%from_value",fromName).replace("%to_value",toName);
            return conversionStr;
        }else if(getName().equals("std::vector<int>")){
            String conversionStr = CXXTemplates.NATIVE_INT_LIST_TO_PLATFORM_INT_LIST.replace("%from_value",fromName).replace("%to_value",toName);
            return conversionStr;
        }
        else if(Character.isUpperCase(getName().charAt(0))) {
            String memoOwnStr = memOwn;
            String conversionStr = CXXTemplates.OBJECT_CONVERSION_EXPRESSION.replace("%to_type",getName()).replace("%to_name",toName).replace("%from_name",fromName).replace("%mem_own",memoOwnStr);
            return conversionStr;
        }else {
            return getName() + " " + toName + " = " + fromName;
        }
    }

    public boolean getIsObject() {
        if(Character.isUpperCase(getName().charAt(0))){
            return true;
        }else {
            return false;
        }
    }

    public String getQualifedName() {
        if(getIsObject()){
            return mNamespace + "::" + getName();
        }else if(getName().equals("string")){
            return "std::string";
        } else {
            return getName();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ReturnType){
            ReturnType rValue = (ReturnType) obj;
            if(mIsPointer == rValue.mIsPointer && mIsReference == rValue.mIsReference && mNamespace != null && mNamespace.equals(rValue.mNamespace) && getName().equals(rValue.getName())){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }
}

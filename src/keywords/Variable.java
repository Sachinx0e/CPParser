package keywords;

import com.cpp.CXXTemplates;
import com.cpp.CppKeywordNames;
import com.cpp.GeneratorType;
import com.cpp.TypeMappings;

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
    private final String mTypeQualified;

    public Variable(boolean isStatic,boolean isConst,String type,boolean isRef,boolean isPointer,String keyword) {
        super(keyword);
        mTypeQualified = type;
        if(type.contains("::")){
            List<String> words = Keyword.getWords(mTypeQualified,"::");
            mType = words.get(words.size() - 1);
        }else {
            mType = mTypeQualified;
        }
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

    public String getQualfiedName(AST ast){
        String qualifiedName;
        if(mIsStatic){
            qualifiedName = ast.getClassK().getQualifiedName(ast) + "::" + getName();
        }else {
            qualifiedName = "m" + ast.getClassK().getName() + "->" + getName();
        }

        return qualifiedName;
    }

    public String generateDeclaration(GeneratorType generatorType) {
        if(generatorType == GeneratorType.CXX){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(CXXTemplates.SPACING_3);
            if(mIsStatic){
                stringBuilder.append("static ");
            }
            String typeMapped = TypeMappings.getMappingQualified(mType);

            stringBuilder.append(CXXTemplates.PROPERTY_DECLARATION_TEMPLATE.replace("%type",typeMapped).replace("%name",getName()));

            return stringBuilder.toString();

        }else{
            return null;
        }
    }

    public String generateDefination(GeneratorType generatorType,AST ast) {
        if(generatorType == GeneratorType.CXX){

            StringBuilder stringBuilderBody = new StringBuilder();


            //if is string
            if(mType.equals("std::string") || mType.equals("string")){
                stringBuilderBody.append(CXXTemplates.SPACING_1).append("return ");
                stringBuilderBody.append(CXXTemplates.STRING_CONV_FUNC_STD_TO_PLATFORM.replace("%from_name",getQualfiedName(ast)));
            }
            //if is object
            else if(Character.isUpperCase(mType.charAt(0))) {
                stringBuilderBody.append(CXXTemplates.NATIVE_VARIABLE_TO_PLATFORM_CONV_BODY.replace("%qualified_name",getQualfiedName(ast)).replace("%type_name",mType));
            }
            //if inbuilt type
            else{
                stringBuilderBody.append(CXXTemplates.SPACING_1).append("return ");
                stringBuilderBody.append(getQualfiedName(ast));
            }



            StringBuilder stringBuilder = new StringBuilder();
            String mappedType = TypeMappings.getMappingQualified(mType);
            stringBuilder.append(CXXTemplates.PROPERTY_DEFINATION_TEMPLATE.replace("%type",mappedType)
                                                                          .replace("%class_name",ast.getClassK().getName())
                                                                          .replace("%name",getName())
                                                                          .replace("%body",stringBuilderBody.toString()));

            return stringBuilder.toString();
        }else {
            return null;
        }
    }

}

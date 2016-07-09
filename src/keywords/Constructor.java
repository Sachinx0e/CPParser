package keywords;

import com.cpp.CXXTemplates;
import com.cpp.GeneratorType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rando on 6/28/2016.
 */
public class Constructor extends Keyword {

    private List<Parameter> mParameters = new ArrayList<>();

    public Constructor(String name) {
        super(name);
    }

    public void addParam(Parameter parameter){
        mParameters.add(parameter);
    }

    public void addParam(List<Parameter> parameters){
        mParameters.addAll(parameters);
    }

    public static Constructor read(String currentLine,AST ast) {
        String currentLineWithSpaces = currentLine.replace("("," ").replace(")"," ");
        List<String> words = Keyword.getWords(currentLineWithSpaces," ");
        if(words.size() > 0){
            String name = words.get(0);
            List<Parameter> parameters = Parameter.read(currentLine,ast);
            Constructor constructor = new Constructor(name);
            constructor.addParam(parameters);
            return constructor;
        }else {
            return null;
        }
    }

    public String generateDeclaration(GeneratorType generatorType) {
        if(generatorType == GeneratorType.CXX){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getName());
            stringBuilder.append("(");
            int count = mParameters.size();
            for(int i = 0;i<count;i++){
                Parameter parameter = mParameters.get(i);
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

    public String generateDefination(AST ast, String namespace, GeneratorType generatorType) {
        if(generatorType == GeneratorType.CXX){

            //fully qualified names
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(namespace).append("::").append(ast.getClassK().getName());
            stringBuilder.append("::").append(getName());

            //paramters
            stringBuilder.append("(");
            int count = mParameters.size();
            for(int i = 0;i<count;i++){
                Parameter parameter = mParameters.get(i);
                stringBuilder.append(parameter.generate(generatorType));
                if(i != count -1){
                    stringBuilder.append(",");
                }
            }
            stringBuilder.append(")");

            //init member variable
            String mVariableName = "m" + ast.getClassK().getName();
            stringBuilder.append(":").append(mVariableName);

            //paramters to the mVariable
            stringBuilder.append("(");

            stringBuilder.append("new " + ast.getClassK().getQualifiedName(ast)).append("(");

            for(int i = 0;i<count;i++){
                Parameter parameter = mParameters.get(i);
                String paramName;
                if(parameter.getIsObject()){
                    //*((Applib::DateTime::LocalDate*)startDate->getPointer())
                    paramName = CXXTemplates.POINTER_TO_NATIVE_CAST.replace("%qualified_name",parameter.getQualifiedName()).replace("%variable",parameter.getName());
                }else if(parameter.getIsString()){
                    paramName = CXXTemplates.STRING_CONV_FUNC_PLATFORM_TO_STD.replace("%from_name",parameter.getName());
                }else {
                    paramName = parameter.getName();
                }
                stringBuilder.append(paramName);
                if(i != count -1){
                    stringBuilder.append(",");
                }
            }
            stringBuilder.append("))");

            //close the constructor
            stringBuilder.append("{").append("\n\n").append("}");
            return stringBuilder.toString();

        }else {
            return null;
        }
    }
}

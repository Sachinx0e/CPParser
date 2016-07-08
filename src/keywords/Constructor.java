package keywords;

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

    public static Constructor read(String currentLine) {
        String currentLineWithSpaces = currentLine.replace("("," ").replace(")"," ");
        List<String> words = Keyword.getWords(currentLineWithSpaces," ");
        if(words.size() > 0){
            String name = words.get(0);
            List<Parameter> parameters = Parameter.read(currentLine);
            Constructor constructor = new Constructor(name);
            constructor.addParam(parameters);
            return constructor;
        }else {
            return null;
        }
    }

    public String generate(GeneratorType generatorType) {
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
}

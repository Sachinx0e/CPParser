package keywords;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rando on 6/28/2016.
 */
public class Parameter extends Keyword {

    private final String mType;

    public Parameter(String type, String name) {
        super(name);
        mType = type;
    }


    public String getType() {
        return mType;
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
                if(singleParam.size() == 2){
                    Parameter parameter = new Parameter(singleParam.get(0),singleParam.get(1));
                    parameterList.add(parameter);
                }else if(singleParam.size() == 3){
                    Parameter parameter = new Parameter(singleParam.get(0) + " " + singleParam.get(1),singleParam.get(2));
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

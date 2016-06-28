package keywords;

import com.company.CppKeywordNames;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rando on 6/27/2016.
 */
public class ClassK extends Keyword {

    private ArrayList<Variable> mVariables = new ArrayList<>();
    private ArrayList<Function> mFunctions = new ArrayList<>();

    public ClassK(String keyword) {
        super(keyword);
    }

    public void addVariable(Variable variable){
        mVariables.add(variable);
    }


    public static ClassK read(String currentLine) {
        List<String> words = Keyword.getWords(currentLine," ");
        if(words.size() >= 2){
            String className = words.get(1);
            ClassK classK = new ClassK(className);
            return classK;
        }else {
            return null;
        }
    }

    public void addFunctions(Function function) {
        mFunctions.add(function);
    }

    public List<Variable> getVariables() {
        return mVariables;
    }

    public List<Function> getFunctions() {
        return mFunctions;
    }
}

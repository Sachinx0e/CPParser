package keywords;

import com.company.CppKeywordNames;

import java.util.List;

/**
 * Created by Rando on 6/27/2016.
 */
public class AST {

    Namespace mNamespace = null;
    private ClassK mClass = null;

    public Namespace getNamespace(){
        return mNamespace;
    }

    public void setNamespace(Namespace namespace){
        mNamespace = namespace;
    }

    public void setClass(ClassK classK){
        mClass = classK;
    }

    public ClassK getClassK(){
        return mClass;
    }

    public String getString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(CppKeywordNames.NAMESPACE).append(" ").append(mNamespace.getName()).append("\n");
        stringBuilder.append(" ").append(CppKeywordNames.CLASS).append(" ").append(mClass.getName()).append("\n");
        List<Variable> variables = mClass.getVariables();
        for(Variable variable : variables){
            stringBuilder.append("  ").append(variable.toString()).append("\n");
        }

        List<Function> functions = mClass.getFunctions();
        for(Function function : functions){
            stringBuilder.append("  ").append(function.toString()).append("\n");
        }

        return stringBuilder.toString();
    }

    @Override
    public String toString() {
       return super.toString();
    }
}

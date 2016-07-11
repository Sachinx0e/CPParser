package keywords;

import com.cpp.CppKeywordNames;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rando on 6/27/2016.
 */
public class AST {

    ArrayList<ImportK> mImports = new ArrayList<>();
    Namespace mNamespace = null;
    private ClassK mClass = null;
    private boolean mHasReachedPrivate;
    private ClassK mParentClassK = null;

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

    public void addImport(ImportK importK) {
        mImports.add(importK);
    }

    public void setHasReachedPrivate(boolean hasReachedPrivate){
        mHasReachedPrivate = hasReachedPrivate;
    }

    public boolean getHasReachedPrivate(){
        return mHasReachedPrivate;
    }

    public void setParentClass(ClassK classK) {
        mParentClassK = classK;
    }

    public ClassK getParentClassK(){
        return mParentClassK;
    }

}

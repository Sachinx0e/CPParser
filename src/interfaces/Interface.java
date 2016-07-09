package interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Rando on 7/8/2016.
 */
public class Interface {
    public String HEADER_NAME;

    private List<String> mFunctionToIgnore = new ArrayList<>();
    private List<String> mImportFiles = new ArrayList<>();
    private HashMap<String,String> functionToRename = new HashMap<>();
    private boolean mGenConvConst = false;

    public void addFunctionToIgnore(String line) {
        mFunctionToIgnore.add(line);
    }

    public List<String> getFunctionToIgnore(){
        return mFunctionToIgnore;
    }

    public void addImportFile(String importFile) {
        mImportFiles.add(importFile);
    }

    public List<String> getImportFiles(){
        return mImportFiles;
    }

    public String getTranslationUnitHeaderName() {
        return HEADER_NAME.replace(".h","") + "Wrapper.h";
    }

    public String getTranslationUnitSourceName() {
        return HEADER_NAME.replace(".h","") + "Wrapper.cpp";
    }

    public boolean isFunctionIgnored(String currentLine) {
        for(String function : mFunctionToIgnore){
            if(currentLine.equals(function)){
                return true;
            }
        }
        return false;
    }

    public void setGenerateConvConst(boolean genconvConstr) {
        mGenConvConst = genconvConstr;
    }

    public boolean getGenerateConvConst(){
        return mGenConvConst;
    }

    public void addFunctionRename(String fromName, String toName) {
        functionToRename.put(fromName,toName);
    }

    public String getFunctionRenameMapping(String funcName){
        String value = functionToRename.getOrDefault(funcName,funcName);
        return value;
    }

}

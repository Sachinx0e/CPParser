package interfaces;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rando on 7/8/2016.
 */
public class Interface {
    public String HEADER_NAME;

    private List<String> mFunctionToIgnore = new ArrayList<>();
    private List<String> mImportFiles = new ArrayList<>();

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
}

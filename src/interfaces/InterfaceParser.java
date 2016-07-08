package interfaces;

import java.io.*;

/**
 * Created by Rando on 7/8/2016.
 */
public class InterfaceParser {

    private final File mInterfaceFile;
    private String currentLine;
    private boolean mInFunctionIgnore = false;
    private boolean mOutFuntionIgnore = false;
    private boolean mInIMPORT = false;
    private boolean mOutImport = false;

    public InterfaceParser(File interfaceFile){
        mInterfaceFile = interfaceFile;
    }

    public Interface parse() throws IOException {
        Interface interfaceK = new Interface();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(mInterfaceFile));
        currentLine = null;

        while((currentLine = bufferedReader.readLine()) != null){
            String line = currentLine.trim();
            InterfaceConstruct interfaceConstruct = getConstruct(line);
            switch (interfaceConstruct){
                case HEADER_FILE:
                    String [] words = line.split(" ");
                    String headerFileName = words[2];
                    interfaceK.HEADER_NAME = headerFileName;
                    break;
                case FUNCTION_IGNORE_START:
                    mInFunctionIgnore = true;
                    mOutFuntionIgnore = false;
                    break;
                case FUNCTION_IGNORE_END:
                    mInFunctionIgnore = false;
                    mOutFuntionIgnore = true;
                    break;
                case FUNCTION_IGNORE:
                    interfaceK.addFunctionToIgnore(line);
                    break;
                case IMPORT_START:
                    mInIMPORT = true;
                    mOutImport = false;
                    break;
                case IMPORT_END:
                    mInIMPORT = false;
                    mOutImport = true;
                    break;
                case IMPORT:
                    interfaceK.addImportFile(line);
                    break;
                default:
                    break;
            }
        }

        return interfaceK;
    }

    private InterfaceConstruct getConstruct(String currentLine){

        //HEADER_FILE
        if(currentLine.contains(InterfaceKeywords.HEADER_FILE)){
            return InterfaceConstruct.HEADER_FILE;
        }

        //FUNCTION_IGNORE_START
        else if(currentLine.contains(InterfaceKeywords.FUNCTION_IGNORE_START)){
            return InterfaceConstruct.FUNCTION_IGNORE_START;
        }

        //FUNCTION_IGNORE_END
        else if(currentLine.contains(InterfaceKeywords.FUNCTION_IGNORE_END)){
            return InterfaceConstruct.FUNCTION_IGNORE_END;
        }

        //FUNCTION IGNORE
        else if(mInFunctionIgnore && !mOutFuntionIgnore && currentLine.length() > 1){
            return InterfaceConstruct.FUNCTION_IGNORE;
        }

        //IMPORT START
        else if(currentLine.contains(InterfaceKeywords.IMPORT_START)){
            return InterfaceConstruct.IMPORT_START;
        }

        //IMPORT END
        else if(currentLine.contains(InterfaceKeywords.IMPORT_END)){
            return InterfaceConstruct.IMPORT_END;
        }

        //IMPORT
        else if(mInIMPORT && !mOutImport && currentLine.length() > 1){
            return InterfaceConstruct.IMPORT;
        }

        //UNKNOWN
        else {
            return InterfaceConstruct.UNKNOWN;
        }
    }

}

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
                    if(headerFileName.contains("\\")){
                        String directoryName = headerFileName.substring(0,headerFileName.lastIndexOf("\\"));
                        String headerName = headerFileName.substring(headerFileName.lastIndexOf("\\") + 1,headerFileName.length());
                        interfaceK.setHeaderFileName(headerName,directoryName);
                    }else {
                        interfaceK.setHeaderFileName(headerFileName,"");
                    }
                    break;
                case CONVERSION_CONSTRUCRTOR:
                    words = line.split(" ");
                    String boolStr = words[2];
                    if(boolStr.equals("true")){
                         interfaceK.setGenerateConvConst(true);
                    }else {
                         interfaceK.setGenerateConvConst(false);
                    }
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
                case RENAME_FUNC:
                    words = line.split(":");
                    String fromName = words[1].trim();
                    String toName = words[2].trim();
                    interfaceK.addFunctionRename(fromName,toName);
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

        //CONVERSION CONSTRUCTOR
        else if(currentLine.contains(InterfaceKeywords.CONVERSION_CONSTRUCRTOR)){
            return InterfaceConstruct.CONVERSION_CONSTRUCRTOR;
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

        //RENAME FUNC
        else if(currentLine.contains(InterfaceKeywords.RENAME_FUNC)){
            return InterfaceConstruct.RENAME_FUNC;
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

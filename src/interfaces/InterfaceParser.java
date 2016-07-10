package interfaces;

import keywords.Keyword;

import java.io.*;
import java.util.List;

/**
 * Created by Rando on 7/8/2016.
 */
public class InterfaceParser {

    private final File mInterfaceFile;
    private String currentLine;

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
                    String [] words = line.split(":=");
                    String headerFileName = words[1].trim();
                    if(headerFileName.contains("\\")){
                        String directoryName = headerFileName.substring(0,headerFileName.lastIndexOf("\\"));
                        String headerName = headerFileName.substring(headerFileName.lastIndexOf("\\") + 1,headerFileName.length());
                        interfaceK.setHeaderFileName(headerName,directoryName);
                    }else {
                        interfaceK.setHeaderFileName(headerFileName,"");
                    }
                    break;
                case CONSTRUCTOR_IGNORE:
                    words = line.split(":=");
                    interfaceK.addConstructorsToIgnore(words[1].trim());
                    break;
                case FUNCTION_IGNORE:
                    words = line.split(":=");
                    interfaceK.addFunctionToIgnore(words[1].trim());
                    break;
                case RENAME_FUNC:
                    words = line.split(":");
                    String fromName = words[1].trim();
                    String toName = words[2].trim();
                    interfaceK.addFunctionRename(fromName,toName);
                    break;
                case IMPORT:
                    words = line.split(":=");
                    interfaceK.addImportFile(words[1].trim());
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
        else if(currentLine.contains(InterfaceKeywords.CONSTRUCTOR_IGNORE)){
            return InterfaceConstruct.CONSTRUCTOR_IGNORE;
        }

        //FUNCTION IGNORE
        else if(currentLine.contains(InterfaceKeywords.FUNCTION_IGNORE)){
            return InterfaceConstruct.FUNCTION_IGNORE;
        }

        //RENAME FUNC
        else if(currentLine.contains(InterfaceKeywords.RENAME_FUNC)){
            return InterfaceConstruct.RENAME_FUNC;
        }

        //IMPORT
        else if(currentLine.contains(InterfaceKeywords.IMPORT)){
            return InterfaceConstruct.IMPORT;
        }

        //UNKNOWN
        else {
            return InterfaceConstruct.UNKNOWN;
        }
    }

}

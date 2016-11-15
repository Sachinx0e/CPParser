import com.cpp.Worker;

import java.io.File;
import java.io.IOException;

/***
 * Copyright (C) RandomeStudios. All rights reserved.
 *
 * @author Sachin Gavali
 * <p>
 * =+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
 * Class        : Main
 * <p>
 * <p>
 * This class is the main entry point of the program
 *
 * <p>
 * =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
 */

public class Main {

    private static String currentLine;
    private static File mInterfaceFile;

    public static final String INTERFACE_FOLDER = "D:\\Projects\\Rewire\\rewire_windows\\rewirelib\\interfaces";
    public static final String OUTPUT_FOLDER = "D:\\Projects\\Rewire\\rewire_windows\\Rewire\\RewireRuntimeComponent";

    public static void main(String[] args) throws IOException {

        ParseExceptionHandler exceptionHandler = new ParseExceptionHandler();

        Thread.currentThread().setUncaughtExceptionHandler(exceptionHandler);

        String namespace = "RewireRuntimeComponent";
        File dir = new File(INTERFACE_FOLDER);
        //File dir = new File("interfaces_test");
        File[] interfaceFiles = dir.listFiles((dir1, name) -> {
            System.out.println(name);
            return name.toLowerCase().endsWith(".i");
        });
        System.out.println();

        File OutPutDir = new File(OUTPUT_FOLDER);
        OutPutDir.mkdir();

        int i = 0;

        for (File interfaceFile : interfaceFiles) {
            mInterfaceFile = interfaceFile;
            Worker worker = new Worker(interfaceFile, namespace, OutPutDir);
            worker.work();
            i++;
        }

        System.out.println("\n" + "Generated " + Integer.toString(i) + " classes");

    }

    private static class ParseExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.err.println("Error parsing line : " + currentLine);
            if (mInterfaceFile != null) {
                System.err.println(mInterfaceFile.toString());
            }
            e.printStackTrace();
        }
    }
}

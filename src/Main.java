import com.cpp.Worker;

import java.io.File;
import java.io.IOException;

public class Main {

    private static String currentLine;
    private static File mInterfaceFile;

    public static void main(String[] args) throws IOException {

        ParseExceptionHandler exceptionHandler = new ParseExceptionHandler();

        Thread.currentThread().setUncaughtExceptionHandler(exceptionHandler);

        String namespace = "RewireRuntimeComponent";
        File dir = new File("D:\\Projects\\Rewire\\rewire_windows\\rewirelib\\interfaces");
        File[] interfaceFiles = dir.listFiles((dir1, name) -> {
            System.out.println(name);
            return name.toLowerCase().endsWith(".i");
        });
        System.out.println();

        File OutPutDir = new File("D:\\Projects\\Rewire\\rewire_windows\\Rewire\\RewireRuntimeComponent");
        OutPutDir.mkdir();

        int i = 0;

        for(File interfaceFile : interfaceFiles){
            mInterfaceFile = interfaceFile;
            Worker worker = new Worker(interfaceFile,namespace,OutPutDir);
            worker.work();
            i++;
        }

        System.out.println("\n" + "Generated " + Integer.toString(i) + " classes");

    }

    private static class ParseExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.err.println("Error parsing line : " + currentLine);
            if(mInterfaceFile != null){
                System.err.println(mInterfaceFile.toString());
            }
            e.printStackTrace();
        }
    }
}
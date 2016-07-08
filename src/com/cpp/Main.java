package com.cpp;

import java.io.File;
import java.io.IOException;

public class Main {

    private static String currentLine;

    public static void main(String[] args) throws IOException {


        ParseExceptionHandler exceptionHandler = new ParseExceptionHandler();

        Thread.currentThread().setUncaughtExceptionHandler(exceptionHandler);

        String namespace = "RewireRuntimeComponent";
        File LocalDateInterface = new File("interfaces/LocalDate.i");

        File OutPutDir = new File("cxx");
        OutPutDir.mkdir();

        Worker worker = new Worker(LocalDateInterface,namespace,OutPutDir);
        worker.work();

    }

    private static class ParseExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.err.println("Error parsing line : " + currentLine);
            e.printStackTrace();
        }
    }
}

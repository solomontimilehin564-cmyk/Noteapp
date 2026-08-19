package com.example.noteapp;
import android.content.Context;
import android.content.Intent;
import java.io.*;
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private final Context context;
    private final Thread.UncaughtExceptionHandler defaultHandler;
    public CrashHandler(Context context) {
        this.context = context;
        this.defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    }
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        try {
            StringWriter sw = new StringWriter();
            throwable.printStackTrace(new PrintWriter(sw));
            String error = sw.toString();
            File file = new File(context.getExternalFilesDir(null), "crash_log.txt");
            FileWriter fw = new FileWriter(file, true);
            fw.write("--- CRASH ---\n" + error + "\n");
            fw.close();
        } catch (Exception e) { e.printStackTrace(); }
        defaultHandler.uncaughtException(thread, throwable);
    }
}

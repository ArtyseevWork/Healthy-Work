package com.mordansoft.healthywork.domain;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.domain.BuildConfig;
/*
Log.e() - ошибки (error)
Log.w() - предупреждения (warning)
Log.i() - информация (info)
Log.d() - отладка (degub)
Log.v() - подробности (verbose)
Log.wtf() - очень серьёзная ошибка! (What a Terrible Failure!, работает начиная с Android 2.2)
 */

public class MordanSoftLogger {
    private static final String myTag = "MordanSoft";
    private static final char msgTypeDefault = 'i';
    private static final boolean fullLog  = true;



    public static void addLog(String bodyText){
        addLog(bodyText,msgTypeDefault,null);
    }

    public static void addLog(String bodyText, char msgType){
        addLog(bodyText,msgType,null);
    }
    public static void addLog(String bodyText, Context context){
        addLog(bodyText,msgTypeDefault,context);
    }

    public static void addLog(boolean quickLog, String bodyText){
        addLog(quickLog, bodyText,msgTypeDefault,null);
    }

    public static void addLog(boolean quickLog, String bodyText, char msgType){
        addLog(quickLog, bodyText,msgType,null);
    }
    public static void addLog(boolean quickLog, String bodyText, Context context){
        addLog(quickLog, bodyText,msgTypeDefault,context);
    }
    public static void addLog(String bodyText, char msgType, Context context){
        addLog(fullLog, bodyText, msgType, context);
    }

    public static void addLog(boolean fullLog, String bodyText, char msgType, Context context){
        if (BuildConfig.DEBUG && fullLog) {
            if (context != null){
                try{
                    bodyText = context.getClass().getSimpleName() + ": " + bodyText;
                } catch (Exception e){
                    Log.e(myTag, "MordanSoftLogs error = " + e);
                }
            }
            switch (msgType){
                case 'e': Log.e(myTag, bodyText);
                          Toast.makeText(context, "bodyText", Toast.LENGTH_SHORT).show();
                          break;
                case 'w': Log.w(myTag, bodyText);
                          break;
                case 'i': Log.i(myTag, bodyText);
                          break;
                case 'd': Log.d(myTag, bodyText);
                          break;
                case 'v': Log.v(myTag, bodyText);
                          break;
                case 'f': Log.wtf(myTag, bodyText);
                          break;
            }
        }
    }
}

package com.android.d0d0.Global;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;

import com.android.d0d0.Service.MediatorService;
import com.android.d0d0.Service.SpeechRecognizerIntentService;
import com.android.d0d0.Service.SpeechRecognizerService;
import com.android.d0d0.Service.edgeIntentService;


public class ApplicationClass extends Application {
	
	public static Context context = null;

    /*

    These are the global variables for this application
    these actions will be passed in intents to be sent to a service or a intent service

    */

    public static final String ACTION_START_VOICE_DETECTOR = "START_VOICE_DETECTOR";
    public static final String ACTION_STOP_VOICE_DETECTOR = "STOP_VOICE_DETECTOR";

    public static final String ACTION_STARTSPEECHRECOGNIZER = "com.android.d0d0.Service.action.STARTSPEECHRECOGNIZER";

    public static final String ACTION_START_RECORDING = "START_RECORDING";
    public static final String ACTION_STOP_RECORDING = "STOP_RECORDING";

    public static final String ACTION_START_JXTA = "START_JXTA";
    public static final String ACTION_STOP_JXTA = "STOP_JXTA";



    @Override
	public void onCreate() {
		
		super.onCreate();
		context = getApplicationContext();

        // Create an intent to start JXTA Edge as an intent sevice
//        Intent startSpeechRecognizerIntent = new Intent(context, SpeechRecognizerService.class);
//        startService(startSpeechRecognizerIntent);



    }


    @Override
    public void onTerminate() {
        super.onTerminate();

//        Intent stopSpeechRecognizerIntent = new Intent(context, SpeechRecognizerIntentService.class);
//        stopService(stopSpeechRecognizerIntent);
    }

    public void onConfigurationChange(Configuration newConfig){
		
		super.onConfigurationChanged(newConfig);
	}
	
	
	
	public ApplicationClass() {
		// TODO Auto-generated constructor stub
		
		
	}
	
	
	public static Context getApplicationClassContext(){
		
		return context;
	}
	
}

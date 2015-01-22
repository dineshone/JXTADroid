package com.android.d0d0.Speech;



import java.util.List;
import java.util.logging.Logger;

import net.jxta.logging.Logging;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

import com.android.d0d0.Global.ApplicationClass;
//import com.android.d0d0.Interface.IMediator;
//import com.android.d0d0.Mediator.Mediator;

/**
 * Created by d0d0 on 1/7/15.
 */

public class hazardSpeechRecognizer implements RecognitionListener {


    public MediaRecorder recorder = new MediaRecorder();
    protected hazardSpeechRecorder hazardRecorder = new hazardSpeechRecorder( recorder);
    protected boolean micON;
    protected Intent recognizerIntent;

    private boolean isStarted = false;
    private SpeechRecognizer recognizer;
    private Context context;

    private final static Logger LOG = Logger.getLogger(hazardSpeechRecognizer.class.getName());


    public hazardSpeechRecognizer() {
        //super();

//        recognizer = SpeechRecognizer.createSpeechRecognizer(context);

        micON = false;
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        context = ApplicationClass.getApplicationClassContext();


           }


    public void startVoiceDetector() {
        // TODO Auto-generated method stub
        Logging.logCheckedInfo(LOG,"Starting startVoiceDetector");
        if(isStarted == false){
            isStarted = true;

            recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

            // accept partial results if they come
            recognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);

            recognizeSpeechDirectly(context, recognizerIntent, this, (SpeechRecognizer) getSpeechRecognizer());

        } else{
            recognizer.startListening(recognizerIntent);
        }

    }

    public void stopVoiceDetector(){

        recognizer.stopListening();
        recognizer.destroy();
    }



    private void recognizeSpeechDirectly(Context context, Intent recognizerIntent, RecognitionListener listener,
                                         SpeechRecognizer recognizer) {
        // TODO Auto-generated method stub
        //need to have a calling package for it to work
        Logging.logCheckedInfo(LOG,"Starting recognizeSpeechDirectly");
        if (!recognizerIntent.hasExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE))
        {
            recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "com.dummy");
        }

        recognizer.setRecognitionListener(listener);
        recognizer.startListening(recognizerIntent);

    }

    private Object getSpeechRecognizer() {
        // TODO Auto-generated method stub

        if (recognizer == null)
        {
            recognizer = SpeechRecognizer.createSpeechRecognizer(context);
        }
        return recognizer;

    }


    /**
     * Recognitionlistener
     * Used for receiving notifications from the SpeechRecognizer when the recognition related events occur
     */

    @Override
    public void onBeginningOfSpeech()
    {
        Logging.logCheckedInfo(LOG,"Starting onBeginningOfSpeech");
        startVoiceDetector();

    }

    @Override
    public void onBufferReceived(byte[] buffer)
    {
        Logging.logCheckedInfo(LOG,"Starting onbufferReceived");
    }

    @Override
    public void onRmsChanged(float rmsdB)
    {
        //		Logging.logCheckedInfo(LOG,"Starting onRMSChanged");
    }

    @Override
    public void onEvent(int eventType, Bundle params)
    {
        Logging.logCheckedInfo(LOG, "Starting onEvent");
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        // TODO Auto-generated method stub
        Logging.logCheckedInfo(LOG,"Starting onReadyForSpeech");
        startVoiceDetector();
    }

    @Override
    public void onEndOfSpeech() {
        // TODO Auto-generated method stub
        Logging.logCheckedInfo(LOG,"Starting onEndOfSpeech");
		startVoiceDetector();

    }

    @Override
    public void onError(int error) {
        // TODO Auto-generated method stub

        Logging.logCheckedInfo(LOG,"Starting onError");
        Logging.logCheckedInfo(LOG,getErrorDescription(error));


    }

    @Override
    public void onResults(Bundle results) {
        // TODO Auto-generated method stub
        Logging.logCheckedInfo(LOG,"Starting onResults");
        checkResults(results);

    }



    @Override
    public void onPartialResults(Bundle partialResults) {
        // TODO Auto-generated method stub
        Logging.logCheckedInfo(LOG,"Starting onPartialResults");
        checkResults(partialResults);

    }


    public String getErrorDescription(int errorCode)
    {
        String message;
        switch (errorCode)
        {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                Logging.logCheckedInfo(LOG,"Speech recognizer restarting after Error");
                startVoiceDetector();
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                Logging.logCheckedInfo(LOG,"Speech recognizer restarting after Error");
                startVoiceDetector();
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                Logging.logCheckedInfo(LOG,"Speech recognizer restarting after Error");
                startVoiceDetector();
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                Logging.logCheckedInfo(LOG,"Speech recognizer restarting after Error");
                startVoiceDetector();
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                Logging.logCheckedInfo(LOG,"Speech recognizer restarting after Error");
                startVoiceDetector();
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                Logging.logCheckedInfo(LOG,"Speech recognizer restarting after Error");
                startVoiceDetector();
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                Logging.logCheckedInfo(LOG,"Speech recognizer restarting after Error");
                startVoiceDetector();
                break;
            default:
                message = "Speech Recognizer threw an ununderstandable Error Code";
                Logging.logCheckedInfo(LOG,"Speech recognizer restarting after Error");
                startVoiceDetector();
                break;
        }
        return message;
    }


    /**
     * @param results
     */
    protected void checkResults(Bundle results) {
        if(results != null) {
            List<String> words = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

            if(words.contains("dodo")){
                Logging.logCheckedInfo(LOG,"The word 'dodo' has been uttered");


                if(micON == true){
                    // Swtitch Off mic
                    Logging.logCheckedInfo(LOG,"Switching OFF Mic");
                    micON = false;
                    hazardRecorder.stopRecording(recorder);


                }else {
                    // Switch ON mic and start recording
                    Logging.logCheckedInfo(LOG,"Switching ON Mic");
                    micON = true;
                    // if the audioMsgFile exists, delete it so

                    hazardRecorder.startRecording(recorder);
                }

                Logging.logCheckedInfo(LOG, "Switching OFF Speechrecognizer");
                stopVoiceDetector();
                //startVoiceDetector();

            }
        }
    }


}


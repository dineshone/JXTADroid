package com.android.d0d0.Speech;


import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import net.jxta.document.FileDocument;
import net.jxta.document.MimeMediaType;
import net.jxta.logging.Logging;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;

import com.android.d0d0.Global.ApplicationClass;
import com.android.d0d0.Service.edgeIntentService;
import com.android.d0d0.droidEdge;
//import com.android.d0d0.FileObject.FileObject;
//import com.android.d0d0.Interface.IMediator;
//import com.android.d0d0.Mediator.Mediator;


/**
 * Created by d0d0 on 1/7/15.
 */
public class hazardSpeechRecorder {


    public static Context context = ApplicationClass.getApplicationClassContext();

    protected MediaRecorder recorder;
    protected MediaPlayer mplayer = new MediaPlayer();


    //    protected IMediator mediator;
//    protected FileObject audioMsgFileObject;

    public String audioMsgFilePath = context.getFilesDir().toString()+ "audioMsg.3gp";
    public File audioMsgFile = new File(audioMsgFilePath);

    private final static Logger LOG = Logger.getLogger(hazardSpeechRecorder.class.getName());
    private final static String TAG = "hazardSpeechRecorder";
    private final int speakTimeMS = 10000;
    private LocalBroadcastManager myLocalBroadcastmanager = LocalBroadcastManager.getInstance(context);

    public  hazardSpeechRecorder( MediaRecorder recorder) {
        // TODO Auto-generated constructor stub

        this.recorder = recorder;


    }


    public void stopRecording(MediaRecorder recorder) {


        Logging.logCheckedInfo(LOG, "stopping recorder");
        recorder.stop();
        //			 recorder.reset();
        //			 recorder.release();
        //
        //        FileDocument audioFileDoc = mediator.createFileDocument(audioMsgFileObject.FileObject, MimeMediaType.AOS);
        //        audioMsgFileObject.FileContentShareAdvertisement = mediator.getShareAdvForAudioFile(audioFileDoc);
        //        mediator.putFileObjectIntoFileServer(audioMsgFileObject);

    }

    /**
     *
     */
    public void startRecording(MediaRecorder recorder) {

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOnErrorListener(recorderErrorListener);
        recorder.setOnInfoListener(recorderInfoListener);
        recorder.setMaxDuration( speakTimeMS);

        Logging.logCheckedInfo(LOG, "starting recording");

        // audioMsgFileObject = mediator.getFileObjectFromFilePool();
        // audioMsgFilePath = audioMsgFileObject.FileObject.getAbsolutePath();

        Logging.logCheckedInfo(LOG, Environment.getDataDirectory().getAbsolutePath().toString());
        Logging.logCheckedInfo(LOG, Environment.getDataDirectory().exists());
        Logging.logCheckedInfo(LOG, Environment.getDownloadCacheDirectory().getAbsolutePath().toString());
        Logging.logCheckedInfo(LOG, Environment.getDownloadCacheDirectory().exists());
        Logging.logCheckedInfo(LOG, Environment.getExternalStorageDirectory().getAbsolutePath().toString());
        Logging.logCheckedInfo(LOG, Environment.getExternalStorageDirectory().exists());

        recorder.setOutputFile(audioMsgFilePath);


        try {
            recorder.prepare();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {

            recorder.start();
            Thread.sleep(speakTimeMS, 0);

        } catch (RuntimeException e){
            Logging.logCheckedInfo(LOG, TAG + " RuntimeException while starting the recorder, change the audio source");
            e.printStackTrace();
        } catch(InterruptedException e) {
            Logging.logCheckedInfo(LOG, TAG + " Thread Sleep after recorder.start, interrupted");
            e.printStackTrace();
        }



        stopRecording(recorder);


            Intent intent = new Intent("newSpeechRecorded");
            intent.setAction("JXTA_SEND_FILE");
            intent.putExtra("command", "sendFile");
            intent.putExtra("fileName", audioMsgFilePath);
            myLocalBroadcastmanager.sendBroadcastSync(intent);






//        Create an intent to start JXTA Edge as an intent sevice
//        Intent startJxtaIntent = new Intent(context, edgeIntentService.class);
//        startJxtaIntent.setAction(ApplicationClass.ACTION_STOP_JXTA);
//        context.startService(startJxtaIntent);
//

        try {
            mplayer.reset();
            mplayer.setDataSource(audioMsgFilePath);
            mplayer.prepare();
        } catch (IOException e){
            Logging.logCheckedInfo(LOG, TAG + " unable to play recorded speech File");
            e.printStackTrace();
        }

        mplayer.start();
        mplayer.setOnCompletionListener( new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.stop();
                mp.release();
            }
        }  );



    }


    /**
     * @return
     */


    private MediaRecorder.OnErrorListener recorderErrorListener = new MediaRecorder.OnErrorListener() {
        @Override
        public void onError(MediaRecorder mr, int what, int extra) {
            Logging.logCheckedInfo(LOG,"Error: " + what + ", " + extra);
        }
    };


    private MediaRecorder.OnInfoListener recorderInfoListener = new MediaRecorder.OnInfoListener() {
        @Override
        public void onInfo(MediaRecorder mr, int what, int extra) {
            Logging.logCheckedInfo(LOG,"Warning: " + what + ", " + extra);
        }
    };


}


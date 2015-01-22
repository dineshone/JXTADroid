package com.android.d0d0.Service;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;

import com.android.d0d0.Speech.hazardSpeechRecognizer;

import net.jxta.logging.Logging;

import java.util.logging.Logger;

public class SpeechRecognizerService extends Service {

    private hazardSpeechRecognizer ServiceSpeechRecognizer = new hazardSpeechRecognizer();
    private final static Logger LOG = Logger.getLogger(SpeechRecognizerService.class.getName());
    private final static String TAG = "SpeechRecognizerService";

    public SpeechRecognizerService() {
    }

    /**
     * Called by the system when the service is first created.  Do not call this method directly.
     */
    @Override
    public void onCreate() {

        Logging.logCheckedInfo(LOG, TAG + " onCreate");
        super.onCreate();
    }

    /**
     * Called by the system every time a client explicitly starts the service by calling
     * {@link android.content.Context#startService}, providing the arguments it supplied and a
     * unique integer token representing the start request.  Do not call this method directly.
     * <p/>
     * <p class="caution">Note that the system calls this on your
     * service's main thread.  A service's main thread is the same
     * thread where UI operations take place for Activities running in the
     * same process.  You should always avoid stalling the main
     * thread's event loop.  When doing long-running operations,
     * network calls, or heavy disk I/O, you should kick off a new
     * thread, or use {@link android.os.AsyncTask}.</p>
     *
     * @param intent  The Intent supplied to {@link android.content.Context#startService},
     *                as given.  This may be null if the service is being restarted after
     *                its process has gone away, and it had previously returned anything
     *                except {@link #START_STICKY_COMPATIBILITY}.
     * @param flags   Additional data about this start request.  Currently either
     *                0, {@link #START_FLAG_REDELIVERY}, or {@link #START_FLAG_RETRY}.
     * @param startId A unique integer representing this specific request to
     *                start.  Use with {@link #stopSelfResult(int)}.
     * @return The return value indicates what semantics the system should
     * use for the service's current started state.  It may be one of the
     * constants associated with the {@link #START_CONTINUATION_MASK} bits.
     * @see #stopSelfResult(int)
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Logging.logCheckedInfo(LOG, TAG + " onStartCommand");
        ServiceSpeechRecognizer.startVoiceDetector();

        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * Called by the system to notify a Service that it is no longer used and is being removed.  The
     * service should clean up any resources it holds (threads, registered
     * receivers, etc) at this point.  Upon return, there will be no more calls
     * in to this Service object and it is effectively dead.  Do not call this method directly.
     */
    @Override
    public void onDestroy() {


        Logging.logCheckedInfo(LOG, TAG + " onDestroy");
        ServiceSpeechRecognizer.stopVoiceDetector();
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        Logging.logCheckedInfo(LOG, TAG + " onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {

        Logging.logCheckedInfo(LOG, TAG + " onLowMemory");
        ServiceSpeechRecognizer.stopVoiceDetector();
        super.onLowMemory();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

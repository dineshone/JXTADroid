package com.android.d0d0.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import com.android.d0d0.Global.ApplicationClass;

public class MediatorService extends Service {

    public static Context context = ApplicationClass
            .getApplicationClassContext();

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.android.d0d0.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.android.d0d0.extra.PARAM2";


    public MediatorService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null && !intent.getAction().equals(null)) {
            final String action = intent.getAction().toString();
            if (ApplicationClass.ACTION_START_VOICE_DETECTOR.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionStartVoiceDetector(param1, param2);

            } else if (ApplicationClass.ACTION_STOP_VOICE_DETECTOR.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionStopVoiceDetector(param1, param2);
            } else if (ApplicationClass.ACTION_START_JXTA.equals(action)) {
                // Create an intent to start JXTA Edge as an intent sevice
                Intent startJxtaEdgeIntent = new Intent(context, edgeIntentService.class);
                startJxtaEdgeIntent.setAction(ApplicationClass.ACTION_START_JXTA.toString());
                startService(startJxtaEdgeIntent);

            } else if (ApplicationClass.ACTION_STOP_JXTA.equals(action)) {

                Intent stopJxtaEdgeIntent = new Intent(context, edgeIntentService.class);
                stopJxtaEdgeIntent.setAction("ACTION_START_EDGE");
                startService(stopJxtaEdgeIntent);

            }
        }
        // return super.onStartCommand(intent, flags, startId);
        return START_REDELIVER_INTENT;
    }

        @Override
        public void onDestroy () {
            super.onDestroy();
        }

        void handleActionStartVoiceDetector(String param1, String param2) {
            //TODO will add behavior in the future
            throw new UnsupportedOperationException("Not yet implemented");

        }


        void handleActionStopVoiceDetector(String param1, String param2) {
            //TODO will add behavior in the future
            throw new UnsupportedOperationException("Not yet implemented");

        }


}

package com.android.d0d0.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.android.d0d0.droidEdge;

public class edgeService extends Service {

    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();

    public edgeService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.



        //throw new UnsupportedOperationException("Not yet implemented");

        return mBinder;
    }



    public class LocalBinder extends Binder {

        public LocalBinder() {
            // Return this instance of LocalService so clients can call public methods
            startEdge();

        }
    }

        /**
     * Handle action start edge in the provided background thread with the provided
     * parameters.
     * Starts JXTA Edge
     *
     */
    private void startEdge() {

        droidEdge.startJXTA();

    }
}

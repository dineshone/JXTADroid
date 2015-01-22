package com.android.d0d0.Service;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.android.d0d0.Global.ApplicationClass;
import com.android.d0d0.droidEdge;

import net.jxta.logging.Logging;

import java.util.logging.Logger;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class edgeIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS

    public static Context context = ApplicationClass.getApplicationClassContext();

    private static final String ACTION_START_EDGE = "ACTION_START_EDGE";
    private static final String ACTION_BAZ = "com.android.d0d0.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.android.d0d0.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.android.d0d0.extra.PARAM2";

    private final static Logger LOG = Logger.getLogger(droidEdge.class
            .getName());

    private LocalBroadcastManager myLocalBroadcastManager= LocalBroadcastManager.getInstance(context);

    private BroadcastReceiver jxtaIntentReceiver;
/*

    */


    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */

    // TODO: Customize helper method
    public static void startActionStartEdge(Context context, String param1, String param2) {
        Intent intent = new Intent(context, edgeIntentService.class);
        intent.setAction(ACTION_START_EDGE);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }


    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     *//*

    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, edgeIntentService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

*/
    public edgeIntentService() {
        super("edgeIntentService");
    }




    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        return super.registerReceiver(receiver, filter);
    }

    @Override
    public void onCreate() {

        Logging.logCheckedInfo(LOG, "starting onCreate of edgeIntentService");
        super.onCreate();

        jxtaIntentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String cmd = intent.getStringExtra("command");

                if (cmd == "sendFile" ){
                    String contentFileName = intent.getStringExtra("fileName");
                    shareContent(contentFileName);
                }
            }
        };

        IntentFilter commandIntentFilter = new IntentFilter();
        {
            commandIntentFilter.addAction("JXTA_SEND_FILE");
        }

        myLocalBroadcastManager.registerReceiver(jxtaIntentReceiver, commandIntentFilter);



    }

    @Override
    public void onDestroy() {


        super.onDestroy();

        myLocalBroadcastManager.unregisterReceiver(jxtaIntentReceiver);

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ApplicationClass.ACTION_START_JXTA.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionStartEdge(param1, param2);
            } else if (ApplicationClass.ACTION_STOP_JXTA.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionStopEdge(param1, param2);
            }
        }
    }

    /**
     * Handle action start edge in the provided background thread with the provided
     * parameters.
     * Starts JXTA Edge
     *
     */
    private void handleActionStartEdge(String param1, String param2) {

        droidEdge.startJXTA();

    }

    private void shareContent(String contentFileName){

        droidEdge.shareContent(contentFileName);
    }


    private void handleActionStopEdge(String param1, String param2) {

        droidEdge.stopEdge();

    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }


}

package com.android.d0d0.Service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.android.d0d0.Speech.hazardSpeechRecognizer;

import net.jxta.logging.Logging;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class SpeechRecognizerIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.android.d0d0.Service.action.FOO";
    private static final String ACTION_BAZ = "com.android.d0d0.Service.action.BAZ";

    private static final String ACTION_STARTSPEECHRECOGNIZER = "com.android.d0d0.Service.action.STARTSPEECHRECOGNIZER";
    private hazardSpeechRecognizer intentServiceSpeechRecognizer = new hazardSpeechRecognizer();

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.android.d0d0.Service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.android.d0d0.Service.extra.PARAM2";

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, SpeechRecognizerIntentService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, SpeechRecognizerIntentService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public SpeechRecognizerIntentService() {
        super("SpeechRecognizerIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();

            if (ACTION_STARTSPEECHRECOGNIZER.equals(action)) {
//                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
//                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionSpeechRecognizer();

            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }



    /**
     * Handle action Start Speech recognizer in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSpeechRecognizer() {
        //start the speech Recognizer


        intentServiceSpeechRecognizer.startVoiceDetector();

    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
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

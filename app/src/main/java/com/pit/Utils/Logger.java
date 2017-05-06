package com.pit.Utils;

import android.util.Log;

/**
 * Created by Yanay on 04/05/2017.
 */

public class Logger {

    private static final boolean DEBUG_MODE = true;
    private static final String LOG_TAG = "Yanay";

    public static void v(final String msg) {

        if (DEBUG_MODE) {
            
            Log.v(LOG_TAG, msg);
        }
    }
}

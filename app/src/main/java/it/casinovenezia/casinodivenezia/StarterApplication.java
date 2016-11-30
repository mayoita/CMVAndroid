package it.casinovenezia.casinodivenezia;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.helpshift.Helpshift;
import java.util.Locale;
import java.util.concurrent.Executor;


/**
 * Created by massimomoro on 02/09/15.
 */
public class StarterApplication extends Application{
public static Locale currentLocale;
    private final String HELPSHIFT_API_KEY = "75b10c6c105e8bebefc95729c56e33ae";
    private final String HELPSHIFT_DOMAIN = "casinovenezia.helpshift.com";
    private final String HELPSHIFT_APP_ID = "casinovenezia_platform_20131218091253899-f3f796e2d4b9e99";
    private Tracker mTracker;


    private final static String LOG_TAG = Application.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        //initializeApplication();
        Log.d(LOG_TAG, "StarterApplication.onCreate - Application initialized OK");
        currentLocale=getResources().getConfiguration().locale;
        FacebookSdk.sdkInitialize(this);


        if (!FirebaseApp.getApps(this).isEmpty()) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }

        Helpshift.install(this, // "this" should be the application object
                HELPSHIFT_API_KEY,
                HELPSHIFT_DOMAIN,
                HELPSHIFT_APP_ID);


    }
    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker

    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }
*/

}

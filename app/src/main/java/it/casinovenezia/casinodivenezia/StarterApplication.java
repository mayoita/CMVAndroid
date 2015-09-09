package it.casinovenezia.casinodivenezia;

import android.app.Application;
import android.os.Build;

import com.facebook.FacebookSdk;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;
import com.helpshift.Helpshift;
import java.util.Locale;

/**
 * Created by massimomoro on 02/09/15.
 */
public class StarterApplication extends Application{
public static Locale currentLocale;
    private final String HELPSHIFT_API_KEY = "75b10c6c105e8bebefc95729c56e33ae";
    private final String HELPSHIFT_DOMAIN = "casinovenezia.helpshift.com";
    private final String HELPSHIFT_APP_ID = "casinovenezia_platform_20131218091253899-f3f796e2d4b9e99";
    @Override
    public void onCreate() {
        super.onCreate();
        currentLocale=getResources().getConfiguration().locale;
        FacebookSdk.sdkInitialize(this);
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "yO3MBzW9liNCaiAfXWGb3NtZJ3VhXyy4Zh8rR5ck", "KImYuYCrJ9j3IbDI3W2KtDXCXwmfqsRDCn5Em6A9");
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParseFacebookUtils.initialize(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ParseTwitterUtils.initialize("iG8JhxkUYQS0liIzwtYQ", "DCT2PL3MbHCN0RV9cx5K7iTlSdKfimaEUB8cOBELOTc");
        }

        //ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        // Optionally enable public read access.
        // defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
        Helpshift.install(this, // "this" should be the application object
                HELPSHIFT_API_KEY,
                HELPSHIFT_DOMAIN,
                HELPSHIFT_APP_ID);
    }
}

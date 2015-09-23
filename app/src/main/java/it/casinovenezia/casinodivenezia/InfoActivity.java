package it.casinovenezia.casinodivenezia;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by massimomoro on 01/06/15.
 */
public class InfoActivity extends AppCompatActivity {
    private Tracker mTracker;
    @Override
    public void onResume() {
        super.onResume();
        mTracker.setScreenName("InfoMapVE");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StarterApplication application = (StarterApplication) getApplication();
        mTracker = application.getDefaultTracker();
        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {

        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        setContentView(R.layout.activity_info);


        TextView diciotto = (TextView) findViewById(R.id.diciottopiu);
        diciotto.setMovementMethod(LinkMovementMethod.getInstance());
        TextView info_text = (TextView)findViewById(R.id.textView30);
        TextView info_title = (TextView)findViewById(R.id.textViewInfotitle);

        Typeface XLight = Typeface.createFromAsset(getAssets(), "fonts/GothamXLight.otf");
        //Typeface Thin = Typeface.createFromAsset(getAssets(), "fonts/Giorgio-Thin.ttf");
        info_text.setTypeface(XLight);

        Display display = getWindowManager().getDefaultDisplay();






        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            // If the screen is now in landscape mode, we can show the
            // dialog in-line with the list so we don't need this activity.
            finish();
            return;
        }

        if (savedInstanceState == null) {
            // During initial setup, plug in the details fragment.

            // EventDetails details = new EventDetails();
            // details.setArguments(getIntent().getExtras());
            //  getSupportFragmentManager().beginTransaction().add(R.id.event_details, details).commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

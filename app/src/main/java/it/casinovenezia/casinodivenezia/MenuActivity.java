package it.casinovenezia.casinodivenezia;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by massimomoro on 26/05/15.
 */
public class MenuActivity extends ActionBarActivity {

    private MenuAdapter mAdapter;
    private ListView listView;

    private int convertDpToPx(int dp, DisplayMetrics displayMetrics) {
        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
        return Math.round(pixels);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {

        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        setContentView(R.layout.pop_menu_restaurant);


        TextView diciotto = (TextView) findViewById(R.id.diciottopiu);
        diciotto.setMovementMethod(LinkMovementMethod.getInstance());
        Typeface XLight = Typeface.createFromAsset(getAssets(), "fonts/GothamXLight.otf");
        Typeface Thin = Typeface.createFromAsset(getAssets(), "fonts/Giorgio-Thin.ttf");

        Display display = getWindowManager().getDefaultDisplay();
        ImageView imageView = (ImageView) findViewById(R.id.imageView9);
        ImageView imageView2 = (ImageView) findViewById(R.id.imageView10);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int width = display.getWidth();
        int height = (int) (width * 0.66); // 0.75 if image aspect ration is 4:3, change accordingly

        RelativeLayout.LayoutParams fp = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height + convertDpToPx(6,dm));
        RelativeLayout.LayoutParams sp = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height);

        fp.setMargins(convertDpToPx(3, dm), convertDpToPx(0, dm), convertDpToPx(3, dm), 0);
        sp.setMargins(convertDpToPx(5, dm), convertDpToPx(3, dm), convertDpToPx(5, dm), 0);
        imageView.setLayoutParams(sp);
        imageView.setLayoutParams(fp);
        Resources res = getResources();
        mAdapter = new MenuAdapter(this);
        listView = (ListView)findViewById(R.id.listViewMenu);
        for (int i = 0; i < 16; i++) {

            if (i % 4 == 0) {
                switch(i) {
                    case 0:

                        mAdapter.addSectionHeaderItem(res.getString(R.string.starters));
                        break;
                    case 4:
                        mAdapter.addSectionHeaderItem(res.getString(R.string.first));
                        break;
                    case 8:
                        mAdapter.addSectionHeaderItem(res.getString(R.string.second));
                        break;
                    case 12:
                        mAdapter.addSectionHeaderItem(res.getString(R.string.dessert));
                        break;
                    default:
                        mAdapter.addSectionHeaderItem(res.getString(R.string.starters));
                }

            } else {
                mAdapter.addItem("Row Item #" + i);
            }

        }

        listView.setAdapter(mAdapter);


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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_details, menu);
        return true;
    }
}

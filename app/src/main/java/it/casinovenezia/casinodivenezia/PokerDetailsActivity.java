package it.casinovenezia.casinodivenezia;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;

import org.lucasr.twowayview.TwoWayView;

/**
 * Created by massimomoro on 08/05/15.
 */
public class PokerDetailsActivity extends ActionBarActivity implements BaseSliderView.OnSliderClickListener{

    private PokerDayAdapter mAdapter;
    private ListView myListView;
    private PokerCellAdapter mCellAdapter;

    Context context = PokerDetailsActivity.this;


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
        setContentView(R.layout.activity_poker_details);

        myListView = (ListView)findViewById(R.id.listViewPoker);
        TextView diciotto = (TextView) findViewById(R.id.diciottopiu);
        diciotto.setMovementMethod(LinkMovementMethod.getInstance());
        Typeface XLight = Typeface.createFromAsset(getAssets(), "fonts/GothamXLight.otf");


        Display display = getWindowManager().getDefaultDisplay();

        DisplayMetrics dm = getResources().getDisplayMetrics();
        TextView titolo = (TextView)findViewById(R.id.textViewPoker);
        TextView titoloR = (TextView)findViewById(R.id.textViewPokerRule);
        titolo.setTypeface(XLight);
        titoloR.setTypeface(XLight);

        int width = display.getWidth();
        int height = (int) (width * 0.66); // 0.75 if image aspect ration is 4:3, change accordingly

        RelativeLayout.LayoutParams fp = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height + convertDpToPx(6,dm));
        RelativeLayout.LayoutParams sp = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height);

        fp.setMargins(convertDpToPx(7, dm), convertDpToPx(37, dm), convertDpToPx(7, dm), 0);
        sp.setMargins(convertDpToPx(10, dm), convertDpToPx(40, dm), convertDpToPx(10, dm), 0);

        mAdapter = new PokerDayAdapter(context, width);
        mAdapter.addItem("Item 1");
        mAdapter.addItem("Item 2");
        mAdapter.addItem("Item 3");
        mAdapter.addItem("Item 4");
        mAdapter.addItem("Item 5");
        mAdapter.addItem("Item 6");

        mCellAdapter = new PokerCellAdapter(context, width);

        mCellAdapter.addItem("Item1");
        mCellAdapter.addItem("Item2");
        mCellAdapter.addItem("Item3");
        mCellAdapter.addItem("Item4");
        mCellAdapter.addItem("Item5");

        myListView.setAdapter(mCellAdapter);



        TwoWayView lvTest = (TwoWayView) findViewById(R.id.lvItemsPoker);
        lvTest.setAdapter(mAdapter);


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

    @Override
    public void onSliderClick(BaseSliderView baseSliderView) {
        Toast.makeText(this, baseSliderView.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();

    }
}
package it.casinovenezia.casinodivenezia;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
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

import org.json.JSONException;
import org.lucasr.twowayview.TwoWayView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by massimomoro on 08/05/15.
 */
public class PokerDetailsActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener{

    private PokerDayAdapter mAdapter;
    private ListView myListView;
    private PokerCellAdapter mCellAdapter;
    private int currentVisibleItemCount;
    private int currentScrollState;
    private TwoWayView lvTest;
    ArrayList pokerArray;
    Context context = PokerDetailsActivity.this;
    int width;


    private int convertDpToPx(int dp, DisplayMetrics displayMetrics) {
        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
        return Math.round(pixels);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
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
        titoloR.setText(Html.fromHtml(createRules(i.getStringArrayListExtra("TournamentsRules"))));
        titolo.setText((i.getStringExtra("TournamentName")));
        titolo.setTypeface(XLight);
        titoloR.setTypeface(XLight);

        width = display.getWidth();
        int height = (int) (width * 0.66); // 0.75 if image aspect ration is 4:3, change accordingly

        RelativeLayout.LayoutParams fp = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height + convertDpToPx(6,dm));
        RelativeLayout.LayoutParams sp = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height);

        fp.setMargins(convertDpToPx(7, dm), convertDpToPx(37, dm), convertDpToPx(7, dm), 0);
        sp.setMargins(convertDpToPx(10, dm), convertDpToPx(40, dm), convertDpToPx(10, dm), 0);

        mAdapter = new PokerDayAdapter(context, width);
        mCellAdapter = new PokerCellAdapter(context, width);
        pokerArray = i.getStringArrayListExtra("PokerData");
        int primo=0;
        for (int k = 0; k < pokerArray.size(); k++) {
            ArrayList f = (ArrayList) pokerArray.get(k);
            String ff = (String) f.get(1);
            if (!ff.equals("")) {
                mAdapter.addItem((String) f.get(1), k);
                primo = primo + 1;
            }
            if (primo == 1) {
                mCellAdapter.addItem((ArrayList) pokerArray.get(k));
            }

        }
        myListView.setAdapter(mCellAdapter);

        lvTest = (TwoWayView) findViewById(R.id.lvItemsPoker);
        lvTest.setAdapter(mAdapter);
        lvTest.setOnScrollListener(new TwoWayView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(TwoWayView view, int scrollState) {
                String stateName = "Undefined";
                currentScrollState = scrollState;
                isScrollCompleted();
                switch (scrollState) {
                    case SCROLL_STATE_IDLE:
                        stateName = "Idle";
                        break;
                    case SCROLL_STATE_TOUCH_SCROLL:
                        stateName = "Dragging";
                        break;
                    case SCROLL_STATE_FLING:
                        stateName = "Flinging";
                        break;
                }



            }

            @Override
            public void onScroll(TwoWayView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {



                currentVisibleItemCount = visibleItemCount;
            }
            private void isScrollCompleted() {

                if (currentVisibleItemCount > 0 && currentScrollState == 0) {


                    mCellAdapter.mData.clear();
                    for (int k = mAdapter.getIndex(lvTest.getFirstVisiblePosition()); k < mAdapter.getIndex(lvTest.getFirstVisiblePosition() + 1); k++) {
                            mCellAdapter.addItem((ArrayList) pokerArray.get(k));
                    }
                    myListView.setAdapter(mCellAdapter);
                }
            }

        });




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

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSliderClick(BaseSliderView baseSliderView) {
        Toast.makeText(this, baseSliderView.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();

    }
    public String createRules (ArrayList theList) {
        String theRules ="";

        for (int i = 0; i < theList.size(); i++) {

            ArrayList a = (ArrayList) theList.get(i);
            theRules = theRules + "<font color=#cc0029>" + a.get(0) + "</font><BR>"  + a.get(1) + "<BR><BR>";
        }

        return theRules;
    }

}
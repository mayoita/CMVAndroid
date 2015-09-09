package it.casinovenezia.casinodivenezia;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by massimomoro on 09/09/15.
 */
public class TimeTableActivity extends AppCompatActivity {
    private static final String TAG = "BUS";
    private TimetablesCellAdapter mCellAdapter;
    int fragmentWidth;
    private View myView;
    private List<Object> arrayFestivity= new ArrayList<>();
    private Boolean VPS2 = false;
    private ParseQuery<ParseObject> queryForFestivity;
    private List<ParseObject> arrayTimetables= new ArrayList<>();
    private ListView myListView;
    TextView  left;
    TextView  right;
    TextView titolo;
    TextView subtitleTime;
    TextView boatText;
    String currentVenue = "VE";
    ImageView imageBG;
    ImageView imageBGFirst;
    ImageView imageBGSec;
    TextView secondPart;
    TextView thirdPart;
    TextView fourthdPart;
    TextView fifthPart;
    TextView sisthPart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {

        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        Typeface XLight = Typeface.createFromAsset(getAssets(), "fonts/GothamXLight.otf");
        setContentView(R.layout.fragment_timetable);
        myView = findViewById(R.id.timetable_rootview);
        fragmentWidth = myView.getWidth();
        myListView = (ListView)myView.findViewById(R.id.listViewTimetables);

        left = (TextView)myView.findViewById(R.id.textView33);
        left.setTypeface(XLight);
        right = (TextView)myView.findViewById(R.id.textView34);
        right.setTypeface(XLight);
        titolo = (TextView)myView.findViewById(R.id.textViewTitolo);
        titolo.setTypeface(XLight);
        subtitleTime = (TextView)myView.findViewById(R.id.textViewSubtitle);
        subtitleTime.setTypeface(XLight);
        imageBG = (ImageView)myView.findViewById(R.id.imageView8);
        imageBGFirst = (ImageView)myView.findViewById(R.id.imageViewFirstBG);
        imageBGSec = (ImageView)myView.findViewById(R.id.imageViewSecondBG);
        boatText = (TextView)myView.findViewById(R.id.textViewBoat);
        boatText.setTypeface(XLight);
        secondPart = (TextView)myView.findViewById(R.id.textViewsecond);
        secondPart.setTypeface(XLight);
        thirdPart = (TextView)myView.findViewById(R.id.textViewTre);
        thirdPart.setTypeface(XLight);
        fourthdPart = (TextView)myView.findViewById(R.id.textViewQuattro);
        fourthdPart.setTypeface(XLight);
        fifthPart = (TextView)myView.findViewById(R.id.textViewcinque);
        fifthPart.setTypeface(XLight);
        sisthPart = (TextView)myView.findViewById(R.id.textViewSei);
        sisthPart.setTypeface(XLight);

        boatText.setText(R.string.text_for_free_boat);
        secondPart.setText(R.string.secondPart);
        thirdPart.setText(R.string.thirdPart);
        fourthdPart.setText(R.string.fourthPart);
        fifthPart.setText(R.string.fifthPart);
        sisthPart.setText(R.string.sixthPart);
        boatText.setVisibility(View.GONE);
        imageBGFirst.setVisibility(View.GONE);
        imageBGSec.setVisibility(View.GONE);
        secondPart.setVisibility(View.GONE);
        thirdPart.setVisibility(View.GONE);
        sisthPart.setVisibility(View.GONE);
        fourthdPart.setVisibility(View.GONE);
        fifthPart.setVisibility(View.GONE);
        titolo.setText(R.string.titoloServices);
        right.setText(R.string.fromCaNoghera);
        if (Venue.currentVenue == 1) {
            currentVenue = "ME";
            left.setText(R.string.fromMestre);
            subtitleTime.setText(R.string.subTimetablesME);
        } else {
            currentVenue = "VE";
            left.setText(R.string.fromVenezia);
            subtitleTime.setText(R.string.subTimetablesVE);
        }
        loadStorageFestivity();

        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            // If the screen is now in landscape mode, we can show the
            // dialog in-line with the list so we don't need this activity.
            finish();
            return;
        }

        if (savedInstanceState == null) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timetables, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_bus:
                currentVenue = "ME";
                left.setVisibility(View.VISIBLE);
                right.setVisibility(View.VISIBLE);
                myListView.setVisibility(View.VISIBLE);
                imageBG.setVisibility(View.VISIBLE);
                subtitleTime.setVisibility(View.VISIBLE);
                boatText.setVisibility(View.GONE);
                imageBGFirst.setVisibility(View.GONE);
                imageBGSec.setVisibility(View.GONE);
                secondPart.setVisibility(View.GONE);
                secondPart.setVisibility(View.GONE);
                thirdPart.setVisibility(View.GONE);
                fourthdPart.setVisibility(View.GONE);
                fifthPart.setVisibility(View.GONE);
                sisthPart.setVisibility(View.GONE);
                left.setText(R.string.fromMestre);
                subtitleTime.setText(R.string.subTimetablesME);
                titolo.setText(R.string.titoloServices);
                loadFestivity();
                return true;
            case R.id.action_busV:
                currentVenue = "VE";
                left.setVisibility(View.VISIBLE);
                right.setVisibility(View.VISIBLE);
                myListView.setVisibility(View.VISIBLE);
                imageBG.setVisibility(View.VISIBLE);
                subtitleTime.setVisibility(View.VISIBLE);
                boatText.setVisibility(View.GONE);
                imageBGFirst.setVisibility(View.GONE);
                imageBGSec.setVisibility(View.GONE);
                secondPart.setVisibility(View.GONE);
                thirdPart.setVisibility(View.GONE);
                fourthdPart.setVisibility(View.GONE);
                fifthPart.setVisibility(View.GONE);
                sisthPart.setVisibility(View.GONE);
                left.setText(R.string.fromVenezia);
                subtitleTime.setText(R.string.subTimetablesVE);
                titolo.setText(R.string.titoloServices);
                loadFestivity();
                return true;
            case R.id.action_boat:
                left.setVisibility(View.GONE);
                right.setVisibility(View.GONE);
                myListView.setVisibility(View.GONE);
                imageBG.setVisibility(View.GONE);
                subtitleTime.setVisibility(View.GONE);
                boatText.setVisibility(View.VISIBLE);
                imageBGFirst.setVisibility(View.VISIBLE);
                imageBGSec.setVisibility(View.VISIBLE);
                secondPart.setVisibility(View.VISIBLE);
                thirdPart.setVisibility(View.VISIBLE);
                fourthdPart.setVisibility(View.VISIBLE);
                fifthPart.setVisibility(View.VISIBLE);
                sisthPart.setVisibility(View.VISIBLE);
                titolo.setText(R.string.titoloBoat);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    public void loadStorageFestivity () {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Festivity");
        query.getInBackground("7VTo3n7rum", new GetCallback<ParseObject>() {
                    public void done(ParseObject object, ParseException e) {
                        if (e == null) {
                            // object will be your game score

                            arrayFestivity = object.getList("festivity");
                            loadFestivity();
                        } else {
                            // something went wrong

                        }
                    }
                }
        );

    }
    public void loadFestivity() {

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH); //zero-based

        for (int i=0; i< arrayFestivity.size(); i++) {
            List<Object> myArray = (List<Object>) arrayFestivity.get(i);

            if ((day == (Integer) myArray.get(0)) && (month == (Integer) myArray.get(1) + 1)) {
                VPS2=true;
            }
        }

        checkWeekDay();
    }

    public void checkWeekDay( ) {

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);//zero-based

        if ((month == 11) && ((day == 24) || (day == 25))) {

            queryForFestivity = ParseQuery.getQuery("ServicesVSP");
        } else {
            if ((weekDay == 7) || VPS2) {
                queryForFestivity = ParseQuery.getQuery("Services");
            } else {
                queryForFestivity = ParseQuery.getQuery("Services");
            }
        }

        queryForFestivity.orderByAscending("Order");
        queryForFestivity.whereEqualTo("MEVE", currentVenue);
        queryForFestivity.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> services, ParseException e) {
                if (e == null) {
                    arrayTimetables = services;
                    mCellAdapter = new TimetablesCellAdapter(TimeTableActivity.this, fragmentWidth, arrayTimetables);
                    myListView.setAdapter(mCellAdapter);

                } else {
                    Log.d("Services", "Error: " + e.getMessage());
                }
            }
        });

    }
}

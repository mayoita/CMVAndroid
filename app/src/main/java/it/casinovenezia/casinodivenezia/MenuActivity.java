package it.casinovenezia.casinodivenezia;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
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

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by massimomoro on 26/05/15.
 */
public class MenuActivity extends AppCompatActivity {

    private static final String CA_NOGHERA = "k5xjqXyLe7";
    private static final String VENEZIA = "25WSnGlEDW";

    private MenuAdapter mAdapter;
    private ListView listView;
    String menuId;
    private List<Object> arrayStarters= new ArrayList<>();
    private List<Object> arrayFirstCourse= new ArrayList<>();
    private List<Object> arraySecondCourse= new ArrayList<>();
    private List<Object> arrayDessert= new ArrayList<>();
    Resources res;
    private ParseImageView imageView;
    TextView date;
    TextView name;


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

         date = (TextView)findViewById(R.id.textView23);
         name = (TextView)findViewById(R.id.textView25);
        TextView diciotto = (TextView) findViewById(R.id.diciottopiu);
        diciotto.setMovementMethod(LinkMovementMethod.getInstance());
        Typeface XLight = Typeface.createFromAsset(getAssets(), "fonts/GothamXLight.otf");
        Typeface Thin = Typeface.createFromAsset(getAssets(), "fonts/Giorgio-Thin.ttf");

        Display display = getWindowManager().getDefaultDisplay();
        imageView = (ParseImageView) findViewById(R.id.imageView9);
        ImageView imageViewWhite = (ImageView) findViewById(R.id.imageView10);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int width = display.getWidth();
        int height = (int) (width * 0.66); // 0.75 if image aspect ration is 4:3, change accordingly

        RelativeLayout.LayoutParams fp = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height + convertDpToPx(6,dm));
        RelativeLayout.LayoutParams sp = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height);

        fp.setMargins(convertDpToPx(3, dm), convertDpToPx(0, dm), convertDpToPx(3, dm), 0);
        sp.setMargins(convertDpToPx(5, dm), convertDpToPx(3, dm), convertDpToPx(5, dm), 0);
        imageView.setLayoutParams(sp);
        imageViewWhite.setLayoutParams(fp);
        res = getResources();
        mAdapter = new MenuAdapter(this);
        listView = (ListView)findViewById(R.id.listViewMenu);
        loadMenu();

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

    public void loadMenu () {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Menu");
        if (Venue.currentVenue == 1) {

            menuId = CA_NOGHERA;
        } else {
            menuId = VENEZIA;
        }
        query.getInBackground(menuId, new GetCallback<ParseObject>() {
                    public void done(ParseObject object, ParseException e) {
                        if (e == null) {
                            date.setText(formatMyDate(object.getDate("StartDate")) + " - " + formatMyDate(object.getDate("EndDate")));
                            name.setText(object.getString("Chief"));
                            arrayStarters = object.getList("Starters");
                            arrayFirstCourse = object.getList("FirstCourse");
                            arraySecondCourse = object.getList("SecondCourse");
                            arrayDessert = object.getList("Dessert");
                            ParseFile image = (ParseFile) object.get("ImageChief");
                            imageView.setParseFile(image);
                            imageView.loadInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, com.parse.ParseException e) {

                                }
                            });
                            for (int i = 0; i < 4; i++) {
                                switch (i) {
                                    case 0:

                                        mAdapter.addSectionHeaderItem(res.getString(R.string.starters));
                                        for (int y = 0; y < arrayStarters.size(); y += 2) {
                                                mAdapter.addItem((String) arrayStarters.get(y), (String) arrayStarters.get(y + 1));
                                        }
                                        break;
                                    case 1:
                                        mAdapter.addSectionHeaderItem(res.getString(R.string.first));
                                        for (int y = 0; y < arrayFirstCourse.size(); y += 2) {
                                            mAdapter.addItem((String) arrayFirstCourse.get(y), (String) arrayFirstCourse.get(y + 1));
                                        }
                                        break;
                                    case 2:
                                        mAdapter.addSectionHeaderItem(res.getString(R.string.second));
                                        for (int y = 0; y < arraySecondCourse.size(); y += 2) {
                                            mAdapter.addItem((String) arraySecondCourse.get(y), (String) arraySecondCourse.get(y + 1));
                                        }
                                        break;
                                    case 3:
                                        mAdapter.addSectionHeaderItem(res.getString(R.string.dessert));
                                        for (int y = 0; y < arrayDessert.size(); y += 2) {
                                            mAdapter.addItem((String) arrayDessert.get(y), (String) arrayDessert.get(y + 1));
                                        }
                                        break;
                                    default:
                                        mAdapter.addSectionHeaderItem(res.getString(R.string.starters));
                                }


                            }

                            listView.setAdapter(mAdapter);
                        } else {
                            // something went wrong

                        }
                    }
                }
        );
    }

    private String formatMyDate(Date myDate) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd LLLL", getResources().getConfiguration().locale);

        return sdf.format(myDate);
    }
}

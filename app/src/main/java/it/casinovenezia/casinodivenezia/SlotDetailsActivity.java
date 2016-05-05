package it.casinovenezia.casinodivenezia;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;


import org.json.JSONArray;
import org.json.JSONException;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

/**
 * Created by massimomoro on 05/05/15.
 */
public class SlotDetailsActivity extends AppCompatActivity {
    private String[] slot_array;
    private String[] slot_array_title;
    private SliderLayout mySlider;
    private FeatureCoverFlow mCoverFlow;
    private CoverFlowAdapter mAdapter;
    DisplayMetrics dm;
    private ArrayList<GameEntity> mData = new ArrayList<>(0);
    private TextSwitcher mTitle;
    private Tracker mTracker;
  //  List<ParseObject> ob;
    private List<EventItem> eventitemlist = null;
    TextView theText;
    private int[][][] arraySlot = {
            {
                    {R.drawable.slotvenezia, R.drawable.slotvenezia2, R.drawable.slotvenezia3, R.drawable.slotvenezia4},
                    {R.drawable.slotvenezia, R.drawable.slotvenezia2, R.drawable.slotvenezia3, R.drawable.slotvenezia4},
                    {R.drawable.slotvenezia, R.drawable.slotvenezia2, R.drawable.slotvenezia3, R.drawable.slotvenezia4},
                    {R.drawable.slotvenezia, R.drawable.slotvenezia2, R.drawable.slotvenezia3, R.drawable.slotvenezia4}
            },
            {
                    {R.drawable.slotcn1, R.drawable.slotcn2, R.drawable.slotcn3, R.drawable.slotcn4,R.drawable.slotcn9},
                    {R.drawable.slotcn8, R.drawable.slotcn4, R.drawable.slotcn1, R.drawable.slotcn5,R.drawable.slotcn9},
                    {R.drawable.slotcn6, R.drawable.slotcn4, R.drawable.slotcn1, R.drawable.slotcn2, R.drawable.slotcn9},
                    {R.drawable.slotcn5, R.drawable.slotcn6, R.drawable.slotcn8, R.drawable.slotcn2,R.drawable.slotcn9}
            }


    };


    private int convertDpToPx(int dp, DisplayMetrics displayMetrics) {
        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
        return Math.round(pixels);
    }
    @Override
    public void onResume() {
        super.onResume();
        if (Venue.currentVenue == 1) {
            mTracker.setScreenName("SlotsDetailsCN");
        } else {
            mTracker.setScreenName("SlotsDetailsVE");
        }
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        slot_array = getResources().getStringArray(R.array.slot_array);
        slot_array_title = getResources().getStringArray(R.array.slot_array_title);
        final Typeface XLight = Typeface.createFromAsset(getAssets(), "fonts/GothamXLight.otf");
        Intent intent = getIntent();
        String jsonArray = intent.getStringExtra("jsonArray");
        int theIndex = intent.getIntExtra("index", 0);
        StarterApplication application = (StarterApplication) getApplication();
        mTracker = application.getDefaultTracker();
        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {

        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        dm = getResources().getDisplayMetrics();
        switch (theIndex) {
            case 1:
                setContentView(R.layout.activity_slot_details_carousel);

                mTitle = (TextSwitcher) findViewById(R.id.title);
                mTitle.setFactory(new ViewSwitcher.ViewFactory() {
                    @Override
                    public View makeView() {
                        LayoutInflater inflater = LayoutInflater.from(SlotDetailsActivity.this);
                        TextView textView = (TextView) inflater.inflate(R.layout.item_title, null);
                        textView.setTypeface(XLight);
                        return textView;
                    }
                });
                Animation in = AnimationUtils.loadAnimation(this, R.anim.slide_in_top);
                Animation out = AnimationUtils.loadAnimation(this, R.anim.slide_out_bottom);
                mTitle.setInAnimation(in);
                mTitle.setOutAnimation(out);

                new RemoteDataTask().execute();
                break;
            case 2:
                setContentView(R.layout.activity_slot_details);
                ImageView imageView = (ImageView) findViewById(R.id.imageViewSlotDetails);
                mySlider = (SliderLayout)findViewById(R.id.slider);
                Display display = getWindowManager().getDefaultDisplay();

                int width = display.getWidth();
                int height = (int) (width * 0.66); // 0.75 if image aspect ration is 4:3, change accordingly

                RelativeLayout.LayoutParams fp = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height + convertDpToPx(6,dm));
                RelativeLayout.LayoutParams sp = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height);

                fp.setMargins(convertDpToPx(7,dm) ,convertDpToPx(10,dm), convertDpToPx(7,dm), 0);
                sp.setMargins(convertDpToPx(10,dm) ,convertDpToPx(13,dm), convertDpToPx(10,dm), 0);
                imageView.setLayoutParams(fp);
                mySlider.setLayoutParams(sp);


                HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
                for(int i = 0; i < arraySlot[Venue.currentVenue][2].length; i++) {
                    file_maps.put("Slot"+ i,arraySlot[Venue.currentVenue][theIndex][i]);
                }

                for(String name : file_maps.keySet()){
                    DefaultSliderView textSliderView = new DefaultSliderView(this);
                    // initialize a SliderLayout
                    textSliderView
                            .description(name)
                            .image(file_maps.get(name))
                            .setScaleType(BaseSliderView.ScaleType.Fit);
                    //.setOnSliderClickListener(this);

                    //add your extra information
                    textSliderView.getBundle()
                            .putString("extra", name);

                    mySlider.addSlider(textSliderView);
                }
                mySlider.setPresetTransformer(SliderLayout.Transformer.Fade);
                mySlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                mySlider.setCustomAnimation(new DescriptionAnimation());
                mySlider.setDuration(4000);
                new RemoteDataTaskForJackpots().execute();
                break;
            default:
                setContentView(R.layout.activity_slot_details);
                ImageView imageViewD = (ImageView) findViewById(R.id.imageViewSlotDetails);
                mySlider = (SliderLayout)findViewById(R.id.slider);
                Display displayD = getWindowManager().getDefaultDisplay();

                int widthD = displayD.getWidth();
                int heightD = (int) (widthD * 0.66); // 0.75 if image aspect ration is 4:3, change accordingly

                RelativeLayout.LayoutParams fpD = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, heightD + convertDpToPx(6,dm));
                RelativeLayout.LayoutParams spD = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, heightD);

                fpD.setMargins(convertDpToPx(7,dm) ,convertDpToPx(10,dm), convertDpToPx(7,dm), 0);
                spD.setMargins(convertDpToPx(10,dm) ,convertDpToPx(13,dm), convertDpToPx(10,dm), 0);
                imageViewD.setLayoutParams(fpD);
                mySlider.setLayoutParams(spD);


                HashMap<String,Integer> file_mapsD = new HashMap<String, Integer>();
                for(int i = 0; i < arraySlot[Venue.currentVenue][2].length; i++) {
                    file_mapsD.put("Slot"+ i,arraySlot[Venue.currentVenue][theIndex][i]);
                }

                for(String name : file_mapsD.keySet()){
                    DefaultSliderView textSliderView = new DefaultSliderView(this);
                    // initialize a SliderLayout
                    textSliderView
                            .description(name)
                            .image(file_mapsD.get(name))
                            .setScaleType(BaseSliderView.ScaleType.Fit);
                    //.setOnSliderClickListener(this);

                    //add your extra information
                    textSliderView.getBundle()
                            .putString("extra", name);

                    mySlider.addSlider(textSliderView);
                }
                mySlider.setPresetTransformer(SliderLayout.Transformer.Fade);
                mySlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                mySlider.setCustomAnimation(new DescriptionAnimation());
                mySlider.setDuration(4000);
                break;

        }

        TextView diciotto = (TextView) findViewById(R.id.diciottopiu);
        diciotto.setMovementMethod(LinkMovementMethod.getInstance());

        theText = (TextView)findViewById(R.id.textViewSlotDetails);
        TextView theTitle = (TextView)findViewById(R.id.textViewTitleGame);
        theText.setTypeface(XLight);
        theTitle.setTypeface(XLight);

        theText.setText(slot_array[theIndex]);
        theTitle.setText(slot_array_title[theIndex]);



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
    protected void onPause() {
        freeMemory();
        super.onPause();
    }

    private void freeMemory() {
        mAdapter = null;
        mCoverFlow = null;

    }

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create the array
//            eventitemlist = new ArrayList<EventItem>();
//            try {
//                // Locate the class table named "Country" in Parse.com
//                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
//                        "Events");
//                // Locate the column named "ranknum" in Parse.com and order list
//                // by ascending
//                query.orderByDescending("StartDate");
//                query.whereEqualTo("eventType", "E");
//                query.whereEqualTo("isSlotsEvents",true);
//                ob = query.find();
//                for (ParseObject event : ob) {
//                    // Locate images in flag column
//                    ParseFile image = (ParseFile) event.get("ImageName");
//
//                    EventItem map = new EventItem();
//                   // map.setImageMain(image);
//                    map.setOffice((String) event.get("office"));
//                    map.setMyId((String)event.getObjectId());
//                    map.setNameIT((String) event.get("NameIT"));
//                    map.setDescriptionIT((String) event.get("DescriptionIT"));
//                    map.setStartDate(formatMyDate(event.getDate("StartDate")));
//                    map.setEndDate(event.getDate("EndDate"));
//
//                    eventitemlist.add(map);
//                }
//            } catch (ParseException e) {
//                Log.e("Error", e.getMessage());
//                e.printStackTrace();
//            }
            return null;
        }
        private String formatMyDate(Date myDate) {

            SimpleDateFormat sdf = new SimpleDateFormat("LLLL yyyy", getResources().getConfiguration().locale);

            return sdf.format(myDate);
        }

        @Override
        protected void onPostExecute(Void result) {
            Context context = SlotDetailsActivity.this;


            RelativeLayout layout = (RelativeLayout)findViewById(R.id.carouselLayout);

            FeatureCoverFlow coverFlow = new FeatureCoverFlow(SlotDetailsActivity.this);
            coverFlow.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
            coverFlow.setCoverHeight(convertDpToPx(180,dm));
            coverFlow.setCoverWidth(convertDpToPx(120,dm));
            coverFlow.setMaxScaleFactor((float) 1.5);
            coverFlow.setReflectionGap(0);
            coverFlow.setRotationTreshold((float) 1.5);
            coverFlow.setScalingThreshold((float) 0.5);
            coverFlow.setSpacing((float) 0.6);
            layout.addView(coverFlow);


            mAdapter = new CoverFlowAdapter(SlotDetailsActivity.this);
            mAdapter.setData((ArrayList<EventItem>) eventitemlist);

            mCoverFlow = coverFlow;
            mCoverFlow.setAdapter(mAdapter);

            mCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                     //mTitle.setText(getResources().getString(mData.get(position).titleResId));
                }
            });

            mCoverFlow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener() {
                @Override
                public void onScrolledToPosition(int position) {

                    mTitle.setText(eventitemlist.get(position).getStartDate() + " - " + eventitemlist.get(position).getNameIT());
                }

                @Override
                public void onScrolling() {

                    mTitle.setText("");
                }
            });


        }
    }

    private class RemoteDataTaskForJackpots extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create the array

            // Locate the class table named "Country" in Parse.com
//            ParseQuery<ParseObject> query = ParseQuery.getQuery("Jackpot");
//            query.whereEqualTo("objectId", "pclLmCGRbH");
//            query.getFirstInBackground(new GetCallback<ParseObject>() {
//                public void done(ParseObject object, ParseException e) {
//                    if (object == null) {
//
//                        Log.d("jackpot", "The getFirst request failed.");
//                    } else {
//
//                        switch (Locale.getDefault().getLanguage()) {
//                            case "it":
//                                theText.setText((String) object.get("ourJackpotIT"));
//                                break;
//                            case "es":
//                                theText.setText((String) object.get("ourJackpotES"));
//                                break;
//                            case "fr":
//                                theText.setText((String) object.get("ourJackpotFR"));
//                                break;
//                            case "de":
//                                theText.setText((String) object.get("ourJackpotDE"));
//                                break;
//                            case "ru":
//                                theText.setText((String) object.get("ourJackpotRU"));
//                                break;
//                            case "ch":
//                                theText.setText((String) object.get("ourJackpotCH"));
//                                break;
//                            default:
//                                theText.setText((String) object.get("ourJackpots"));
//                                break;
//                        }
//
//                    }
//                }
//            });

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {

        }
    }

}

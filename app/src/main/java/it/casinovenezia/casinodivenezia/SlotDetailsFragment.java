package it.casinovenezia.casinodivenezia;


import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
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
public class SlotDetailsFragment extends Fragment {

    private static final String MY_ARRAY = "theDataArray";
    private static final String MY_INDEX = "theIndex";

    private String[] slot_array;
    private String[] slot_array_title;
    private SliderLayout mySlider;
    private CoverFlowAdapter mAdapter;
    private FeatureCoverFlow mCoverFlow;
    int fragmentWidth;
    private Tracker mTracker;
    DisplayMetrics dm;
    int theIndex;
    Typeface XLight;
    View rootView;
    private TextSwitcher mTitle;
 //   List<ParseObject> ob;
    private List<EventItem> eventitemlist = null;
    View myView;
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
    TextView theText;

    private OnEventsInteractionListener mListener;

    private int convertDpToPx(int dp, DisplayMetrics displayMetrics) {
        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
        return Math.round(pixels);
    }

    public static SlotDetailsFragment newInstance(String myArray, int index) {
        SlotDetailsFragment fragment = new SlotDetailsFragment();
        Bundle args = new Bundle();
        args.putString(MY_ARRAY, myArray);
        args.putInt(MY_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    public String getShownIndex() {
        return getArguments().getString(MY_ARRAY);
    }

    public SlotDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            theIndex = getArguments().getInt(MY_INDEX);
        }
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        fragmentWidth= display.getWidth();
        StarterApplication application = (StarterApplication) getActivity().getApplication();
        // mTracker = application.getDefaultTracker();

    }
    @Override
    public void onResume() {
        super.onResume();
        if (Venue.currentVenue == 1) {
            // mTracker.setScreenName("SlotsDetailsCN");
        } else {
            // mTracker.setScreenName("SlotsDetailsVE");
        }
        // mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
    @Override
    public void onStop() {
        if (mySlider != null) {
            mySlider.stopAutoCycle();
        }

        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            // We have different layouts, and in one of them this
            // fragment's containing frame doesn't exist.  The fragment
            // may still be created from its saved state, but there is
            // no reason to try to create its view hierarchy because it
            // won't be displayed.  Note this is not needed -- we could
            // just run the code below, where we would create and return
            // the view hierarchy; it would just never be used.
            return null;
        }

        switch (theIndex) {
            case 1:
                rootView = inflater.inflate(R.layout.activity_slot_details_carousel, container, false);
                break;
            case 2:
                rootView = inflater.inflate(R.layout.activity_slot_details, container, false);
                break;
            default:
                rootView = inflater.inflate(R.layout.activity_slot_details, container, false);
                break;
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        slot_array = getResources().getStringArray(R.array.slot_array);
        slot_array_title = getResources().getStringArray(R.array.slot_array_title);
        myView = getView();

        XLight = Typeface.createFromAsset(getActivity().getAssets(), "fonts/GothamXLight.otf");
        dm = getResources().getDisplayMetrics();

        if (myView != null) {

            switch (theIndex) {
                case 1:
                    mTitle = (TextSwitcher) myView.findViewById(R.id.title);
                    mTitle.setFactory(new ViewSwitcher.ViewFactory() {
                        @Override
                        public View makeView() {
                            LayoutInflater inflater = LayoutInflater.from(getActivity());
                            TextView textView = (TextView) inflater.inflate(R.layout.item_title, null);
                            textView.setTypeface(XLight);
                            return textView;
                        }
                    });
                    Animation in = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_top);
                    Animation out = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_bottom);
                    mTitle.setInAnimation(in);
                    mTitle.setOutAnimation(out);
                    theText = (TextView)myView.findViewById(R.id.textViewSlotDetails);
                    if (theText != null) {
                        theText.setTypeface(XLight);
                    }
                    new RemoteDataTask().execute();

                    break;
                case 2:
                    mySlider = (SliderLayout)myView.findViewById(R.id.slider);
                    HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
                    for(int i = 0; i < arraySlot[Venue.currentVenue][2].length; i++) {
                        file_maps.put("Slot"+ i,arraySlot[Venue.currentVenue][theIndex][i]);
                    }

                    for(String name : file_maps.keySet()){
                        TextSliderView textSliderView = new TextSliderView(getActivity());
                        // initialize a SliderLayout
                        textSliderView
                                .description(name)
                                .image(file_maps.get(name));
                        //   .setScaleType(BaseSliderView.ScaleType.Fit);

                        mySlider.addSlider(textSliderView);
                    }
                    mySlider.setPresetTransformer(SliderLayout.Transformer.Fade);
                    mySlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                    mySlider.setCustomAnimation(new DescriptionAnimation());
                    mySlider.setDuration(4000);


                    TextView diciotto = (TextView) myView.findViewById(R.id.diciottopiu);
                    diciotto.setMovementMethod(LinkMovementMethod.getInstance());
                    theText = (TextView)myView.findViewById(R.id.textViewSlotDetails);
                    TextView theTitle = (TextView)myView.findViewById(R.id.textViewTitleGame);
                    theText.setTypeface(XLight);
                    theTitle.setTypeface(XLight);

                    theText.setText(slot_array[theIndex]);
                    theTitle.setText(slot_array_title[theIndex]);

                    ImageView imageView = (ImageView) myView.findViewById(R.id.imageViewSlotDetails);
                    DisplayMetrics dm = getResources().getDisplayMetrics();
                    int height = (int) (fragmentWidth * 0.44); // 0.75 if image aspect ration is 4:3, change accordingly

                    RelativeLayout.LayoutParams fp = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height + convertDpToPx(6,dm));
                    RelativeLayout.LayoutParams sp = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height);

                    fp.setMargins(convertDpToPx(7, dm), convertDpToPx(37, dm), convertDpToPx(7, dm), 0);
                    sp.setMargins(convertDpToPx(10, dm), convertDpToPx(40, dm), convertDpToPx(10, dm), 0);

                    imageView.setLayoutParams(fp);
                    mySlider.setLayoutParams(sp);
                    new RemoteDataTaskForJackpots().execute();
                    break;
                default:
                    mySlider = (SliderLayout)myView.findViewById(R.id.slider);
                    HashMap<String,Integer> file_mapsD = new HashMap<String, Integer>();
                    for(int i = 0; i < arraySlot[Venue.currentVenue][2].length; i++) {
                        file_mapsD.put("Slot"+ i,arraySlot[Venue.currentVenue][theIndex][i]);
                    }

                    for(String name : file_mapsD.keySet()){
                        DefaultSliderView textSliderView = new DefaultSliderView(getActivity());
                        // initialize a SliderLayout
                        textSliderView
                                .description(name)
                                .image(file_mapsD.get(name));
                             //   .setScaleType(BaseSliderView.ScaleType.Fit);

                        mySlider.addSlider(textSliderView);
                    }
                    mySlider.setPresetTransformer(SliderLayout.Transformer.Fade);
                    mySlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                    mySlider.setCustomAnimation(new DescriptionAnimation());
                    mySlider.setDuration(4000);


                    TextView diciottoD = (TextView) myView.findViewById(R.id.diciottopiu);
                    diciottoD.setMovementMethod(LinkMovementMethod.getInstance());
                    theText = (TextView)myView.findViewById(R.id.textViewSlotDetails);
                    TextView theTitleD = (TextView)myView.findViewById(R.id.textViewTitleGame);
                    theText.setTypeface(XLight);
                    theTitleD.setTypeface(XLight);

                    theText.setText(slot_array[theIndex]);
                    theTitleD.setText(slot_array_title[theIndex]);


                    ImageView imageViewD = (ImageView) myView.findViewById(R.id.imageViewSlotDetails);
                    DisplayMetrics dmD = getResources().getDisplayMetrics();
                    int heightD = (int) (fragmentWidth * 0.44); // 0.75 if image aspect ration is 4:3, change accordingly

                    RelativeLayout.LayoutParams fpD = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, heightD + convertDpToPx(6,dmD));
                    RelativeLayout.LayoutParams spD = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, heightD);

                    fpD.setMargins(convertDpToPx(7, dmD), convertDpToPx(37, dmD), convertDpToPx(7, dmD), 0);
                    spD.setMargins(convertDpToPx(10, dmD), convertDpToPx(40, dmD), convertDpToPx(10, dmD), 0);

                    imageViewD.setLayoutParams(fpD);
                    mySlider.setLayoutParams(spD);
                    break;
            }

        }

    }

    public interface OnEventsInteractionListener {
        void onListItemClick(ListView l, View v, int position, long id);

        public void onFragmentInteraction(Uri uri);
    }
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {
//            // Create the array
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
            Context context = getActivity();


            RelativeLayout layout = (RelativeLayout)myView.findViewById(R.id.carouselLayout);

            FeatureCoverFlow coverFlow = new FeatureCoverFlow(getActivity());
            coverFlow.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
            coverFlow.setCoverHeight(convertDpToPx(180,dm));
            coverFlow.setCoverWidth(convertDpToPx(120,dm));
            coverFlow.setMaxScaleFactor((float) 1.5);
            coverFlow.setReflectionGap(0);
            coverFlow.setRotationTreshold((float) 1.5);
            coverFlow.setScalingThreshold((float) 0.5);
            coverFlow.setSpacing((float) 0.6);
            layout.addView(coverFlow);


            mAdapter = new CoverFlowAdapter(getActivity());
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
//
//            // Locate the class table named "Country" in Parse.com
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
//
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
//
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

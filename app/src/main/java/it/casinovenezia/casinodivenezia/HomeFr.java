package it.casinovenezia.casinodivenezia;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringConfigRegistry;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;
import com.facebook.rebound.ui.SpringConfiguratorView;
import com.facebook.rebound.ui.Util;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.helpshift.Helpshift;





import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import android.os.Handler;

import com.amazonaws.mobile.AWSMobileClient;

/**
 * Created by massimomoro on 25/03/15.
 */
public class HomeFr extends Fragment {

    //DelegateListener mListener;
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    ImageButton helpshiftButton;
    TextView jackpotamount;
    TextView apertoDalle;
    public TextView venue;
    private View rootView;
    private List<Object> arrayFestivity= new ArrayList<>();
    private Boolean VPS2 = false;
    private int DURATION = 1000;
    private Tracker mTracker;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference festivity = mRootRef.child("Festivity");

    /** The DynamoDB object mapper for accessing DynamoDB. */
    private final DynamoDBMapper mapper;

    public HomeFr() {
        mapper = AWSMobileClient.defaultMobileClient().getDynamoDBMapper();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        StarterApplication application = (StarterApplication) getActivity().getApplication();
        // mTracker = application.getDefaultTracker();
        //StrictMode
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }

    public static final HomeFr newInstance(String message) {
        HomeFr instance = new HomeFr();
        Bundle bdl = new Bundle();
        bdl.putString(EXTRA_MESSAGE, message);
        instance.setArguments(bdl);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.home_fragment, container, false);

        venue = (TextView) rootView.findViewById(R.id.cavendramin);
        Typeface XLight = Typeface.createFromAsset(getActivity().getAssets(), "fonts/GothamXLight.otf");
        Typeface Thin = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Giorgio-Thin.ttf");
        venue.setTypeface(XLight);
        TextView contactustext = (TextView)rootView.findViewById(R.id.contactustext);
        ImageView contactus = (ImageView)rootView.findViewById(R.id.contactUs);
        ImageView fb_back = (ImageView) rootView.findViewById(R.id.imageView3);
        ImageView arrow = (ImageView)rootView.findViewById(R.id.arrow);

        if(!HomeActivity.hasBeenSeen) {
            contactus.setVisibility(View.VISIBLE);
            contactustext.setVisibility(View.VISIBLE);
            fb_back.setVisibility(View.VISIBLE);
            arrow.setVisibility(View.VISIBLE);
            Animation animation2 = new AlphaAnimation(1.0f, 0.0f);
            animation2.setDuration(1000);
            animation2.setStartOffset(2000);
            animation2.setFillAfter(true);
            contactus.setAnimation(animation2);
            contactustext.setAnimation(animation2);
            HomeActivity.hasBeenSeen = true;

            YoYo.with(Techniques.Shake)
                    .duration(DURATION)
                    .playOn(rootView.findViewById(R.id.arrow));
            fb_back.setAnimation(animation2);
            arrow.setAnimation(animation2);
        }

        apertoDalle = (TextView) rootView.findViewById(R.id.apertodalle);
        apertoDalle.setTypeface(XLight);

        jackpotamount = (TextView) rootView.findViewById(R.id.jackpotamount);
        jackpotamount.setTypeface(Thin);

        TextView jackpotLabel = (TextView) rootView.findViewById(R.id.jackpotLabel);
        jackpotLabel.setTypeface(XLight);

        TextView diciotto = (TextView) rootView.findViewById(R.id.diciottopiu);
        diciotto.setMovementMethod(LinkMovementMethod.getInstance());

        helpshiftButton = (ImageButton) rootView.findViewById(R.id.helpshift);
        helpshiftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helpshift.showConversation(getActivity());
            }
        });


        Shader textShader=new LinearGradient(0, 0, 0, jackpotLabel.getPaint().getTextSize(),
                new int[]{Color.rgb(253, 255, 26), Color.rgb(250, 100, 22)},
                new float[]{0, 1}, Shader.TileMode.CLAMP);
        jackpotLabel.getPaint().setShader(textShader);
        setOffice();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Venue.currentVenue == 1) {
            // mTracker.setScreenName("HomeCN");
        } else {
            // mTracker.setScreenName("HomeVE");
        }
        // mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        android.support.v7.app.ActionBar action_bar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        action_bar.setDisplayShowCustomEnabled(true);
        action_bar.setDisplayShowTitleEnabled(false);
        Resources res = getResources();
        loadStorageFestivity();
        loadFestivity(res.getString(R.string.todayOpen), res.getString(R.string.todayOpenVenice));
        if (HomeActivity.jackpot == null) {

            JackpotDO selectedJackpot = mapper.load(JackpotDO.class, "1");
            double d = Double.parseDouble(selectedJackpot.getJackpot());
            jackpotamount.setText(DecimalFormat.getCurrencyInstance(Locale.getDefault()).format(d));
            HomeActivity.jackpot = DecimalFormat.getCurrencyInstance(Locale.getDefault()).format(d);

        } else {
            jackpotamount.setText(HomeActivity.jackpot);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
    }

    public void loadStorageFestivity () {

        if(HomeActivity.arrayFestivity.size() == 0) {

            festivity.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("EventAdapter", "Failed to read value.", error.toException());
                }
            });

            FestivityDO selectedFestivity = mapper.load(FestivityDO.class, "1");
            arrayFestivity = selectedFestivity.getFestivityConv();
            HomeActivity.arrayFestivity = arrayFestivity;

        } else {
            arrayFestivity = HomeActivity.arrayFestivity;
        }
    }

    public void loadFestivity(String todayOpen, String andVSP) {

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH); //zero-based

        for (int i=0; i< arrayFestivity.size(); i++) {

            List<Object> myArray = (List<Object>) arrayFestivity.get(i);

            if ((day == (int)(long) myArray.get(0)) && (month == (int)(long) myArray.get(1) + 1)) {
                VPS2=true;
            }
        }

        checkWeekDay(todayOpen, andVSP);
    }

    public void checkWeekDay(String todayOpen, String andVSP) {
        Resources res = getResources();
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
         int weekDay = calendar.get(Calendar.DAY_OF_WEEK);//zero-based

        if ((month == 11) && ((day == 24) || (day == 25))) {
            apertoDalle.setText(res.getString(R.string.todayIsClosed));
        } else {
            if ((weekDay == 7) || VPS2) {
                apertoDalle.setText(todayOpen);
            } else {
                apertoDalle.setText(andVSP);
            }
        }

    }

    public void setOffice() {
        ImageView myBack = (ImageView) rootView.findViewById(R.id.imageView);

        if (Venue.currentVenue == 1) {
            venue.setText("CA' NOGHERA");
            myBack.setImageResource(R.drawable.backcn);
            loadFestivity(getResources().getText(R.string.canogheratime1).toString(), getResources().getText(R.string.canogheratime2).toString());
        } else {
            loadFestivity(getResources().getText(R.string.veneziatime1).toString(), getResources().getText(R.string.veneziatime2).toString());
            venue.setText("CA' VENDRAMIN CALERGI");
            myBack.setImageResource(R.drawable.backve);
        }
    }
}

package it.casinovenezia.casinodivenezia;


import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import com.parse.ParseException;

import org.json.JSONArray;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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


    //Container Activity must implement this interface
//    public interface DelegateListener {
//        public void onItemSelected(Uri articleUri);
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    //TODO:implementa il controllo sulle interfacce
//   @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            mListener = (DelegateListener) activity;
//        } catch (ClassCastException e) {
//
//            throw new ClassCastException(activity.getClass().getName() + " must implement onItemSelected");
//        }
//    }

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
            public void onClick(View v) {
                Toast.makeText(getActivity(),
                        "Help is clicked !", Toast.LENGTH_SHORT).show();
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Resources res = getResources();
        loadStorageFestivity();
        loadFestivity(res.getString(R.string.todayOpen),res.getString(R.string.todayOpenVenice));
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Jackpot");
        query.getInBackground("ykIRbhqKUn", new GetCallback<ParseObject>() {
                     public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    // object will be your game score

                    double d = Double.parseDouble(object.getString("jackpot"));
                    jackpotamount.setText(DecimalFormat.getCurrencyInstance(Locale.GERMANY).format(d));

                } else {
                    // something went wrong

                }
            }
        }
        );

    }

    public void loadStorageFestivity () {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Festivity");
        query.getInBackground("7VTo3n7rum", new GetCallback<ParseObject>() {
                    public void done(ParseObject object, ParseException e) {
                        if (e == null) {
                            // object will be your game score

                            arrayFestivity = object.getList("festivity");

                        } else {
                            // something went wrong

                        }
                    }
                }
        );

    }

    public void loadFestivity(String todayOpen, String andVSP) {

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH); //zero-based

        for (int i=0; i< arrayFestivity.size(); i++) {
            List<Object> myArray = (List<Object>) arrayFestivity.get(i);

            if ((day == (Integer) myArray.get(0)) && (month == (Integer) myArray.get(1) + 1)) {
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

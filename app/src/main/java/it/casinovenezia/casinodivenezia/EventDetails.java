package it.casinovenezia.casinodivenezia;

import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;


import java.util.HashMap;



public class EventDetails extends Fragment implements BaseSliderView.OnSliderClickListener{

    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String DATE = "date";
    private static final String OBJECTID = "objectId";
    private SliderLayout mySlider;
    int fragmentWidth;
    private String image1;
    private String image2;
    private String image3;


    private int convertDpToPx(int dp, DisplayMetrics displayMetrics) {
        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
        return Math.round(pixels);
    }

    public static EventDetails newInstance(String name, String description, String date, String objecyId) {
        EventDetails fragment = new EventDetails();
        Bundle args = new Bundle();
        args.putString(NAME, name);
        args.putString(DESCRIPTION, description);
        args.putString(DATE, date);
        args.putString(OBJECTID, objecyId);

        fragment.setArguments(args);
        return fragment;
    }

    public String getShownIndex() {
        return getArguments().getString(NAME);
    }

    public EventDetails() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {


        }


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

        View rootView = inflater.inflate(R.layout.activity_event_details, container, false);
        mySlider = (SliderLayout)getActivity().findViewById(R.id.slider);


        Typeface XLight = Typeface.createFromAsset(getActivity().getAssets(), "fonts/GothamXLight.otf");
        Typeface Thin = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Giorgio-Thin.ttf");

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final View myView = getView();
        if (myView != null) {
            myView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    fragmentWidth = getView().getWidth();
                    loadImage(getArguments().getString(OBJECTID));
                    ImageView imageView = (ImageView) myView.findViewById(R.id.imageView7);
                    TextView name = (TextView) myView.findViewById(R.id.textView8);
                    TextView date = (TextView) myView.findViewById(R.id.textView9);
                    TextView description = (TextView) myView.findViewById(R.id.textView10);
                    name.setText(getArguments().getString(NAME));
                    date.setText(getArguments().getString(DATE));
                    description.setText(getArguments().getString(DESCRIPTION));
                    DisplayMetrics dm = getResources().getDisplayMetrics();
                    int height = (int) (fragmentWidth * 0.66); // 0.75 if image aspect ration is 4:3, change accordingly

                    RelativeLayout.LayoutParams fp = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height + convertDpToPx(6,dm));
                    RelativeLayout.LayoutParams sp = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height);

                    fp.setMargins(convertDpToPx(7, dm),convertDpToPx(37,dm), convertDpToPx(7, dm), 0);
                    sp.setMargins(convertDpToPx(10, dm),convertDpToPx(40,dm), convertDpToPx(10,dm), 0);
                    imageView.setLayoutParams(fp);
                    mySlider.setLayoutParams(sp);


                    if (fragmentWidth > 0) {
                         getView().getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                }
            });

            mySlider = (SliderLayout)myView.findViewById(R.id.slider);
            TextView diciotto = (TextView) myView.findViewById(R.id.diciottopiu);
            diciotto.setMovementMethod(LinkMovementMethod.getInstance());

        }

    }

    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onSliderClick(BaseSliderView baseSliderView) {

    }



    public void loadImage(String myId) {
//        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
//                "Events");
//
//        query.getInBackground(myId,
//                new GetCallback<ParseObject>() {
//
//                    public void done(ParseObject object,
//                                     ParseException e) {
//
//                        ParseFile image1F = (ParseFile) object
//                                .get("ImageEvent1");
//                        ParseFile image2F = (ParseFile) object
//                                .get("ImageEvent2");
//                        ParseFile image3F = (ParseFile) object
//                                .get("ImageEvent3");
//                        ParseFile imageDefault = (ParseFile) object
//                                .get("ImageName");
//                        if (image1F != null){
//                            image1 = image1F.getUrl();
//                        } else {
//                            image1 = imageDefault.getUrl();
//                        }
//
//                        if (image2F != null){
//                            image2 = image2F.getUrl();
//                        }
//                        if (image3F != null){
//                            image3 = image3F.getUrl();
//                        }
//                        createSlider();
//
//
//                    }
//                });
    }
    public void createSlider (){
        HashMap<String, String> file_maps = new HashMap<String, String>();
        if (image1 != null) {
            file_maps.put("image1", image1);
        }
        if (image2 != null) {
            file_maps.put("image2", image2);
        }
        if (image3 != null) {
            file_maps.put("image3", image3);
        }

        for(String nameF : file_maps.keySet()){
            DefaultSliderView textSliderView = new DefaultSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .description(nameF)
                    .image(file_maps.get(nameF))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.getBundle()
                    .putString("extra",nameF);

            mySlider.addSlider(textSliderView);
        }
        mySlider.setPresetTransformer(SliderLayout.Transformer.Fade);
        mySlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mySlider.setCustomAnimation(new DescriptionAnimation());
        mySlider.setDuration(2000);
    }

}

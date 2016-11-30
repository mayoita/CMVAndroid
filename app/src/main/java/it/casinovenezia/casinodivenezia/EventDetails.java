package it.casinovenezia.casinodivenezia;

import android.content.Context;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.util.HashMap;



public class EventDetails extends Fragment implements BaseSliderView.OnSliderClickListener{

    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String DATE = "date";
    private static final String OBJECTID = "objectId";
    private static final String IMAGE1 = "image1";
    private static final String IMAGE2 = "image2";
    private static final String IMAGE3 = "image3";
    private static final String IMAGEMAIN = "imageMain";
    private SliderLayout mySlider;
    int fragmentWidth;
    private  static Context myContext;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://cmv-gioco.appspot.com/Events");


    private int convertDpToPx(int dp, DisplayMetrics displayMetrics) {
        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
        return Math.round(pixels);
    }

    public static EventDetails newInstance(String name,
                                           String description,
                                           String date,
                                           String objecyId,
                                           String image1,
                                           String image2,
                                           String image3,
                                           Context context,
                                           String imageMain) {
        EventDetails fragment = new EventDetails();
        //DA controllare de context non crea problemi
        myContext = context;
        Bundle args = new Bundle();
        args.putString(NAME, name);
        args.putString(DESCRIPTION, description);
        args.putString(DATE, date);
        args.putString(OBJECTID, objecyId);
        args.putString(IMAGE1, image1);
        args.putString(IMAGE2, image2);
        args.putString(IMAGE3, image3);
        args.putString(IMAGEMAIN, imageMain);

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

                       createSlider();
    }
    public void createSlider (){
        final HashMap<String, String> file_maps = new HashMap<String, String>();
        if (getArguments().getString(IMAGE1) != null) {
            storageRef.child(getArguments().getString(IMAGE1)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    DefaultSliderView textSliderView = new DefaultSliderView(myContext);
                    textSliderView
                            .description("Image")
                            .image(uri.toString())
                            .setScaleType(BaseSliderView.ScaleType.Fit);
                    //.setOnSliderClickListener(this);
                    mySlider.addSlider(textSliderView);

                }
            });

        } else {
            storageRef.child(getArguments().getString(IMAGEMAIN)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    DefaultSliderView textSliderView = new DefaultSliderView(myContext);
                    textSliderView
                            .description("Image")
                            .image(uri.toString())
                            .setScaleType(BaseSliderView.ScaleType.Fit);
                    //.setOnSliderClickListener(this);
                    mySlider.addSlider(textSliderView);

                }
            });
        }
        if (getArguments().getString(IMAGE2) != null) {
            storageRef.child(getArguments().getString(IMAGE2)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    DefaultSliderView textSliderView = new DefaultSliderView(myContext);
                    textSliderView
                            .description("Image")
                            .image(uri.toString())
                            .setScaleType(BaseSliderView.ScaleType.Fit);
                    // .setOnSliderClickListener(this);
                    mySlider.addSlider(textSliderView);

                }
            });
        }
        if (getArguments().getString(IMAGE3) != null) {
            storageRef.child(getArguments().getString(IMAGE3)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    DefaultSliderView textSliderView = new DefaultSliderView(myContext);
                    textSliderView
                            .description("Image")
                            .image(uri.toString())
                            .setScaleType(BaseSliderView.ScaleType.Fit);
                    // .setOnSliderClickListener(this);
                    mySlider.addSlider(textSliderView);

                }
            });
        }
        mySlider.setPresetTransformer(SliderLayout.Transformer.Fade);
        mySlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mySlider.setCustomAnimation(new DescriptionAnimation());
        mySlider.setDuration(2000);
    }

}

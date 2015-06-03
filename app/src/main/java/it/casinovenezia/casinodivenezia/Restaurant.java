package it.casinovenezia.casinodivenezia;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Restaurant.OnRestaurantInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Restaurant#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Restaurant extends Fragment implements View.OnClickListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "title";

    private String mTitle;
    private SliderLayout mySlider;
    private LayoutInflater myInflater;
    private int heightDisplay;
    private int OverMarginForDeepRule = 80;
    private int OverMarginForDeepRuleTopBottom = 200;
    private TextView myTexttitle;
    private DisplayMetrics dm;
    private ImageView myAnchorFrame;
    private MenuAdapter mAdapter;
    private ListView listView;

    int fragmentWidth;

    private OnRestaurantInteractionListener mListener;


    private int convertDpToPx(int dp, DisplayMetrics displayMetrics) {
        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
        return Math.round(pixels);
    }

    public static Restaurant newInstance(String param1) {
        Restaurant fragment = new Restaurant();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    public Restaurant() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_PARAM1);

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
        myInflater =inflater;
        View rootView = myInflater.inflate(R.layout.fragment_restaurant, container, false);
        ImageButton infoButton = (ImageButton)rootView.findViewById(R.id.imageButtonInfo);
        ImageButton menuButton = (ImageButton)rootView.findViewById(R.id.imageButtonMenu);
        infoButton.setOnClickListener(this);
        menuButton.setOnClickListener(this);


        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnRestaurantInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final View myView = getView();
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        dm = getResources().getDisplayMetrics();
        heightDisplay = display.getHeight();
        myTexttitle = (TextView)getActivity().findViewById(R.id.textTestaurantTitle);
        myAnchorFrame = (ImageView)getActivity().findViewById(R.id.imageView7);
        if (myView != null) {
            myView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    fragmentWidth = getView().getWidth();
                    ImageView imageView = (ImageView) myView.findViewById(R.id.imageView7);

                    DisplayMetrics dm = getResources().getDisplayMetrics();
                    int height = (int) (fragmentWidth * 0.66); // 0.75 if image aspect ration is 4:3, change accordingly

                    RelativeLayout.LayoutParams fp = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height + convertDpToPx(6,dm));
                    RelativeLayout.LayoutParams sp = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height);
                    Typeface XLight = Typeface.createFromAsset(getActivity().getAssets(), "fonts/GothamXLight.otf");
                    TextView title = (TextView)myView.findViewById(R.id.textTestaurantTitle);
                    title.setTypeface(XLight);

                    fp.setMargins(convertDpToPx(7,dm) ,convertDpToPx(5,dm), convertDpToPx(7,dm), 0);
                    sp.setMargins(convertDpToPx(10,dm) ,convertDpToPx(8,dm), convertDpToPx(10,dm), 0);
                    imageView.setLayoutParams(fp);
                    mySlider.setLayoutParams(sp);

                    HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
                    file_maps.put("Hannibal",R.drawable.hannibal);
                    file_maps.put("Big Bang Theory",R.drawable.bigbang);
                    file_maps.put("House of Cards",R.drawable.house);
                    file_maps.put("Game of Thrones", R.drawable.game_of_thrones);

                    for(String name : file_maps.keySet()){
                        DefaultSliderView textSliderView = new DefaultSliderView(getActivity());
                        // initialize a SliderLayout
                        textSliderView
                                .description(name)
                                .image(file_maps.get(name))
                                .setScaleType(BaseSliderView.ScaleType.Fit);


                        //add your extra information
                        textSliderView.getBundle()
                                .putString("extra",name);

                        mySlider.addSlider(textSliderView);
                    }
                    mySlider.setPresetTransformer(SliderLayout.Transformer.Fade);
                    mySlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                    mySlider.setCustomAnimation(new DescriptionAnimation());
                    mySlider.setDuration(4000);
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

    public void openPopInfo(View v) {


        View popupView = myInflater.inflate(R.layout.pop_info_restaurant, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                myTexttitle.getWidth()  + convertDpToPx(OverMarginForDeepRule,dm),
                heightDisplay - convertDpToPx(OverMarginForDeepRuleTopBottom, dm));



        Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);
        btnDismiss.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();
            }});

        popupWindow.showAsDropDown(myTexttitle, -convertDpToPx(OverMarginForDeepRule/2,dm), 0);
    }

    public void openPopMenu(View v) {


        Intent intent = new Intent();
        intent.setClass(getActivity(), MenuActivity.class);
       // intent.putExtra("param1", demoData[index]);
        startActivity(intent);

//        View popupView = myInflater.inflate(R.layout.pop_menu_restaurant, null);
//        ScrollView myScroll = (ScrollView)popupView.findViewById(R.id.scrollView);
//        LinearLayout myLay = (LinearLayout)popupView.findViewById(R.id.myLay);
//        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(myAnchorFrame.getWidth(),convertDpToPx(500, dm));
//        myLay.setLayoutParams(parms);
//
//        mAdapter = new MenuAdapter(getActivity());
//        listView = (ListView) popupView.findViewById(R.id.listViewMenu);
//        for (int i = 0; i < 11; i++) {
//
//            if (i % 4 == 0) {
//                mAdapter.addSectionHeaderItem("Section #" + i);
//            }
//            mAdapter.addItem("Row Item #" + i);
//        }
//
//        listView.setAdapter(mAdapter);
//
//        final PopupWindow popupWindow = new PopupWindow(
//                popupView,
//                myAnchorFrame.getWidth()  ,
//                heightDisplay - convertDpToPx(OverMarginForDeepRuleTopBottom, dm));
//
//
//
//        Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);
//        btnDismiss.setOnClickListener(new Button.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                popupWindow.dismiss();
//            }});
//
//        popupWindow.showAsDropDown(myAnchorFrame, 0, -myAnchorFrame.getHeight());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButtonInfo:
                openPopInfo(v);
                break;
            case R.id.imageButtonMenu:
                openPopMenu(v);
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnRestaurantInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}

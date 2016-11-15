package it.casinovenezia.casinodivenezia;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.lucasr.twowayview.TwoWayView;

/**
 * Created by massimomoro on 06/05/15.
 */
public class GameDetailsFragment extends Fragment {

    private static final String MY_ARRAY = "theDataArray";
    private Tracker mTracker;
    private String mParam1;
    private TextView myTexttitle;
    private DisplayMetrics dm;
    private GameRuleAdapter mAdapter;
    private int heightDisplay;
    JSONArray myData;
    int fragmentWidth;
    private TwoWayView lvTest;
    private int[] arrayGames = {
            R.drawable.fairroulette_972,
            R.drawable.blackjack458,
            R.drawable.carribean_458,
            R.drawable.roulettefrancese_458,
            R.drawable.chemindefer_458,
            R.drawable.trentaquaranta_458,
            R.drawable.default458

    };
    private int currentVisibleItemCount;
    private int currentScrollState;
    TextView myText;
    private int OverMarginForDeepRuleTopBottom = 200;
    private int OverMarginForDeepRule = 80;



    private OnEventsInteractionListener mListener;

    private int convertDpToPx(int dp, DisplayMetrics displayMetrics) {
        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
        return Math.round(pixels);
    }

    public static GameDetailsFragment newInstance(String myArray) {
        GameDetailsFragment fragment = new GameDetailsFragment();
        Bundle args = new Bundle();
        args.putString(MY_ARRAY, myArray);

        fragment.setArguments(args);
        return fragment;
    }

    public String getShownIndex() {
        return getArguments().getString(MY_ARRAY);
    }

    public GameDetailsFragment() {
        // Required empty public constructor
    }
    @Override
    public void onResume() {
        super.onResume();
        if (Venue.currentVenue == 1) {
           // mTracker.setScreenName("TableGameDetailsCN");
        } else {
          //  mTracker.setScreenName("TableGameDetailsVE");
        }
       // mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(MY_ARRAY);

        }
        StarterApplication application = (StarterApplication) getActivity().getApplication();
        //mTracker = application.getDefaultTracker();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

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
        View rootView = inflater.inflate(R.layout.activity_game_details, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final View myView = getView();
        String jsonArray = mParam1;

        try {
            myData = new JSONArray(jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (myView != null) {
            myView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    fragmentWidth = getView().getWidth();
                    Typeface XLight = Typeface.createFromAsset(getActivity().getAssets(), "fonts/GothamXLight.otf");

                    ImageView imageViewWhite = (ImageView) myView.findViewById(R.id.imageViewWhite);
                    ImageView imageViewGame = (ImageView) myView.findViewById(R.id.imageViewGame);
                    myText = (TextView)myView.findViewById(R.id.textViewGame);
                    myTexttitle = (TextView)myView.findViewById(R.id.textViewTitleGame);

                    myText.setTypeface(XLight);
                    myTexttitle.setTypeface(XLight);

                    int myNum = 6;

                    try {
                        myNum = Integer.parseInt(myData.getJSONArray(2).getString(0));
                    } catch(NumberFormatException nfe) {
                        System.out.println("Could not parse " + nfe);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    imageViewGame.setImageResource(arrayGames[myNum]);

                    try {
                        myTexttitle.setText(myData.getString(0));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        myText.setText(myData.getJSONArray(2).getJSONArray(3).getJSONArray(0).getString(1));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    dm = getResources().getDisplayMetrics();
                    int width = getView().getWidth();
                    heightDisplay = getView().getHeight();
                    int height = (int) (width * 0.47);

                    RelativeLayout.LayoutParams fp = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height + convertDpToPx(6,dm));
                    RelativeLayout.LayoutParams sp = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height);

                    fp.setMargins(convertDpToPx(7,dm) ,convertDpToPx(10,dm), convertDpToPx(7,dm), 0);
                    sp.setMargins(convertDpToPx(10, dm), convertDpToPx(13, dm), convertDpToPx(10, dm), 0);
                    imageViewWhite.setLayoutParams(fp);
                    imageViewGame.setLayoutParams(sp);


                    mAdapter = new GameRuleAdapter(getActivity(), width);
                    JSONArray values = new JSONArray();
                    try {
                        values = myData.getJSONArray(2).getJSONArray(3);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    for(int i = 0 ; i < values.length(); i++) {
                        try {
                            mAdapter.addItem(values.getJSONArray(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }



                    lvTest = (TwoWayView) getActivity().findViewById(R.id.lvItems);
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
                                try {
                                    myText.setText(myData.getJSONArray(2).getJSONArray(3).getJSONArray(lvTest.getFirstVisiblePosition()).getString(1));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                /*** In this way I detect if there's been a scroll which has completed ***/
                                /*** do the work! ***/
                            }
                        }

                    });

                    if (fragmentWidth > 0) {
                        getView().getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                }
            });


            TextView diciotto = (TextView) myView.findViewById(R.id.diciottopiu);
            diciotto.setMovementMethod(LinkMovementMethod.getInstance());

        }

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
    public interface OnEventsInteractionListener {
        void onListItemClick(ListView l, View v, int position, long id);

        public void onFragmentInteraction(Uri uri);
    }
    public void openPopWindow(View v) {

        LayoutInflater layoutInflater
                = LayoutInflater.from(getActivity());
        View popupView = layoutInflater.inflate(R.layout.popupgamerule, null);
        TextView theTitle = (TextView) popupView.findViewById(R.id.popUpTitle);
        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                WindowManager.LayoutParams.MATCH_PARENT,
                heightDisplay - convertDpToPx(OverMarginForDeepRuleTopBottom, dm));

        ListView theList = (ListView)popupView.findViewById(R.id.therule);

        //Adapter for GameRule
        GameDeepRuleAdapter theRuleAdapter= new GameDeepRuleAdapter(v.getContext());

        JSONArray values = new JSONArray();
        try {
            theTitle.setText(myData.getJSONArray(2).getJSONArray(3).getJSONArray(lvTest.getFirstVisiblePosition()).getString(2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            values = myData.getJSONArray(2).getJSONArray(3).getJSONArray(lvTest.getFirstVisiblePosition());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (values.length() > 3 ) {
            for (int i = 3; i < values.length(); i++) {
                try {
                    theRuleAdapter.addItem(values.getJSONArray(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        theList.setAdapter(theRuleAdapter);

        Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);
        btnDismiss.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }});

        popupWindow.showAsDropDown(myTexttitle, -convertDpToPx(OverMarginForDeepRule / 2, dm), 0);
    }
}

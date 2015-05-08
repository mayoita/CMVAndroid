package it.casinovenezia.casinodivenezia;

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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.lucasr.twowayview.TwoWayView;

/**
 * Created by massimomoro on 06/05/15.
 */
public class GameDetailsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private TextView myTexttitle;
    private DisplayMetrics dm;
    private GameRuleAdapter mAdapter;
    private int heightDisplay;

    int fragmentWidth;




    private OnEventsInteractionListener mListener;

    private int convertDpToPx(int dp, DisplayMetrics displayMetrics) {
        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
        return Math.round(pixels);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EventDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static GameDetailsFragment newInstance(String index) {
        GameDetailsFragment fragment = new GameDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, index);

        fragment.setArguments(args);
        return fragment;
    }

    public String getShownIndex() {
        return getArguments().getString(ARG_PARAM1);
    }

    public GameDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);

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
        View rootView = inflater.inflate(R.layout.activity_game_details, container, false);

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
                    Typeface XLight = Typeface.createFromAsset(getActivity().getAssets(), "fonts/GothamXLight.otf");

                    ImageView imageViewWhite = (ImageView) myView.findViewById(R.id.imageViewWhite);
                    ImageView imageViewGame = (ImageView) myView.findViewById(R.id.imageViewGame);
                    TextView myText = (TextView)myView.findViewById(R.id.textViewGame);
                    myTexttitle = (TextView)myView.findViewById(R.id.textViewTitleGame);

                    myText.setTypeface(XLight);
                    myTexttitle.setTypeface(XLight);

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
                    mAdapter.addItem("Item 1");
                    mAdapter.addItem("Item 2");
                    mAdapter.addItem("Item 3");
                    mAdapter.addItem("Item 4");
                    mAdapter.addItem("Item 5");
                    mAdapter.addItem("Item 6");


                    TwoWayView lvTest = (TwoWayView) myView.findViewById(R.id.lvItems);
                    lvTest.setAdapter(mAdapter);

                    if (fragmentWidth > 0) {
                        getView().getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                }
            });


            TextView diciotto = (TextView) myView.findViewById(R.id.diciottopiu);
            diciotto.setMovementMethod(LinkMovementMethod.getInstance());

        }

    }

    // TODO: Rename method, update argument and hook method into UI event
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

        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
}

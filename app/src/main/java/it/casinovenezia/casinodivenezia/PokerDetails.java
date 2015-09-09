package it.casinovenezia.casinodivenezia;

import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
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
import android.widget.Toast;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;

/**
 * Created by massimomoro on 08/05/15.
 */
public class PokerDetails extends Fragment implements BaseSliderView.OnSliderClickListener{
// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TOURNAMENT_DESCRIPTION = "TournamentDescription";
    private static final String TOURNAMENT_DATE = "TournamentDate";
    private static final String TOURNAMENT_NAME = "TournamentName";
    private static final String TOURNAMENT_URL = "TournamentURL";
    private static final String START_DATE = "StartDate";
    private static final String TOURNAMENTS_RULES = "TournamentsRules";
    private static final String POKER_DATA = "PokerData";

    private PokerDayAdapter mAdapter;
    private PokerCellAdapter mCellAdapter;
    private ListView myListView;
    ArrayList pokerArray;
    private TwoWayView lvTest;
    private int currentVisibleItemCount;
    private int currentScrollState;
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
public static PokerDetails newInstance(String tournamentDescription, String tournamentDate, String tournamentName, String tournamentUrl, String startDate, ArrayList tournamtsrules, ArrayList pokerData) {
        PokerDetails fragment = new PokerDetails();
        Bundle args = new Bundle();
        args.putString(TOURNAMENT_DESCRIPTION, tournamentDescription);
        args.putString(TOURNAMENT_DATE, tournamentDate);
        args.putString(TOURNAMENT_NAME, tournamentName);
        args.putString(TOURNAMENT_URL, tournamentUrl);
        args.putString(START_DATE, startDate);
        args.putStringArrayList(TOURNAMENTS_RULES, tournamtsrules);
        args.putStringArrayList(POKER_DATA, pokerData);


        fragment.setArguments(args);
        return fragment;
        }

public String getShownIndex() {
        return getArguments().getString(TOURNAMENT_NAME);
        }

public PokerDetails() {
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
        View rootView = inflater.inflate(R.layout.activity_poker_details, container, false);

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

                    mAdapter = new PokerDayAdapter(getActivity(), fragmentWidth);
                    mCellAdapter = new PokerCellAdapter(getActivity(), fragmentWidth);
                    pokerArray = getArguments().getStringArrayList("PokerData");

                    int primo=0;
                    for (int k = 0; k < pokerArray.size(); k++) {
                        ArrayList f = (ArrayList) pokerArray.get(k);
                        String ff = (String) f.get(1);
                        if (!ff.equals("")) {
                            mAdapter.addItem((String) f.get(1), k);
                            primo = primo + 1;
                        }
                        if (primo == 1) {
                            mCellAdapter.addItem((ArrayList) pokerArray.get(k));
                        }
                    }
                    myListView.setAdapter(mCellAdapter);

                    lvTest = (TwoWayView) myView.findViewById(R.id.lvItemsPoker);
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


                                mCellAdapter.mData.clear();
                                for (int k = mAdapter.getIndex(lvTest.getFirstVisiblePosition()); k < mAdapter.getIndex(lvTest.getFirstVisiblePosition() + 1); k++) {
                                    mCellAdapter.addItem((ArrayList) pokerArray.get(k));
                                }
                                myListView.setAdapter(mCellAdapter);
                            }
                        }

                    });

                    if (fragmentWidth > 0) {
                        getView().getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                }
            });
            myListView = (ListView)myView.findViewById(R.id.listViewPoker);
            TextView diciotto = (TextView) myView.findViewById(R.id.diciottopiu);
            diciotto.setMovementMethod(LinkMovementMethod.getInstance());
            Typeface XLight = Typeface.createFromAsset(getActivity().getAssets(), "fonts/GothamXLight.otf");
            TextView titolo = (TextView)myView.findViewById(R.id.textViewPoker);
            TextView titoloR = (TextView)myView.findViewById(R.id.textViewPokerRule);
            titolo.setTypeface(XLight);
            titoloR.setTypeface(XLight);
            titoloR.setText(Html.fromHtml(createRules(getArguments().getStringArrayList("TournamentsRules"))));
            titolo.setText((getArguments().getString("TournamentName")));
        }

}

// TODO: Rename method, update argument and hook method into UI event
public void onButtonPressed(Uri uri) {
        if (mListener != null) {
        mListener.onFragmentInteraction(uri);
        }
        }

@Override
public void onSliderClick(BaseSliderView baseSliderView) {
        Toast.makeText(getActivity(), baseSliderView.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
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
    public String createRules (ArrayList theList) {
        String theRules ="";

        for (int i = 0; i < theList.size(); i++) {

            ArrayList a = (ArrayList) theList.get(i);
            theRules = theRules + "<font color=#cc0029>" + a.get(0) + "</font><BR>"  + a.get(1) + "<BR><BR>";
        }

        return theRules;
    }

}
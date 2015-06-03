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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;

import org.lucasr.twowayview.TwoWayView;

/**
 * Created by massimomoro on 08/05/15.
 */
public class PokerDetails extends Fragment implements BaseSliderView.OnSliderClickListener{
// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private static final String ARG_PARAM1 = "param1";
private static final String ARG_PARAM2 = "param2";
        private PokerDayAdapter mAdapter;
        private PokerCellAdapter mCellAdapter;
        private ListView myListView;

// TODO: Rename and change types of parameters
private String mParam1;

        int fragmentWidth;
        int fragmentHeight;



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
public static PokerDetails newInstance(String index) {
        PokerDetails fragment = new PokerDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, index);

        fragment.setArguments(args);
        return fragment;
        }

public String getShownIndex() {
        return getArguments().getString(ARG_PARAM1);
        }

public PokerDetails() {
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

                        DisplayMetrics dm = getResources().getDisplayMetrics();
                        int height = (int) (fragmentWidth * 0.66); // 0.75 if image aspect ration is 4:3, change accordingly

                        RelativeLayout.LayoutParams fp = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height + convertDpToPx(6, dm));
                        RelativeLayout.LayoutParams sp = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height);

                        fp.setMargins(convertDpToPx(7, dm), convertDpToPx(37, dm), convertDpToPx(7, dm), 0);
                        sp.setMargins(convertDpToPx(10, dm), convertDpToPx(40, dm), convertDpToPx(10, dm), 0);

                        mAdapter = new PokerDayAdapter(getActivity(), fragmentWidth);
                        mAdapter.addItem("Item 1");
                        mAdapter.addItem("Item 2");
                        mAdapter.addItem("Item 3");
                        mAdapter.addItem("Item 4");
                        mAdapter.addItem("Item 5");
                        mAdapter.addItem("Item 6");


                        TwoWayView lvTest = (TwoWayView) myView.findViewById(R.id.lvItemsPoker);
                        lvTest.setAdapter(mAdapter);
                        myListView = (ListView)myView.findViewById(R.id.listViewPoker);

                        mCellAdapter = new PokerCellAdapter(getActivity(), fragmentWidth);

                        mCellAdapter.addItem("Item1");
                        mCellAdapter.addItem("Item2");
                        mCellAdapter.addItem("Item3");
                        mCellAdapter.addItem("Item4");
                        mCellAdapter.addItem("Item5");

                        myListView.setAdapter(mCellAdapter);


                        if (fragmentWidth > 0) {
                                getView().getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        }
                }
        });


        TextView diciotto = (TextView) myView.findViewById(R.id.diciottopiu);
        diciotto.setMovementMethod(LinkMovementMethod.getInstance());
                Typeface XLight = Typeface.createFromAsset(getActivity().getAssets(), "fonts/GothamXLight.otf");
                TextView titolo = (TextView)myView.findViewById(R.id.textViewPoker);
                TextView titoloR = (TextView)myView.findViewById(R.id.textViewPokerRule);
                titolo.setTypeface(XLight);
                titoloR.setTypeface(XLight);



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

}
package it.casinovenezia.casinodivenezia;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Timetable.OnTimetableInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Timetable#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Timetable extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "BUS";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnTimetableInteractionListener mListener;
    private TimetablesCellAdapter mCellAdapter;
    int fragmentWidth;
    private View myView;
    private List<Object> arrayFestivity= new ArrayList<>();
    private Boolean VPS2 = false;
    private ParseQuery<ParseObject> queryForFestivity;
    private List<ParseObject> arrayTimetables= new ArrayList<>();
    private ListView myListView;
    TextView  left;
    TextView  right;
    TextView titolo;
    TextView subtitleTime;
    TextView boatText;
    String currentVenue = "VE";
    ImageView imageBG;
    ImageView imageBGFirst;
    ImageView imageBGSec;
    TextView secondPart;
    TextView thirdPart;
    TextView fourthdPart;
    TextView fifthPart;
    TextView sisthPart;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Timetable.
     */
    // TODO: Rename and change types and number of parameters
    public static Timetable newInstance(String param1, String param2) {
        Timetable fragment = new Timetable();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Timetable() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timetable, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        getActivity().getMenuInflater().inflate(R.menu.menu_timetables, menu);
        //super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_bus:
                currentVenue = "ME";
                left.setVisibility(View.VISIBLE);
                right.setVisibility(View.VISIBLE);
                myListView.setVisibility(View.VISIBLE);
                imageBG.setVisibility(View.VISIBLE);
                subtitleTime.setVisibility(View.VISIBLE);
                boatText.setVisibility(View.GONE);
                imageBGFirst.setVisibility(View.GONE);
                imageBGSec.setVisibility(View.GONE);
                secondPart.setVisibility(View.GONE);
                secondPart.setVisibility(View.GONE);
                thirdPart.setVisibility(View.GONE);
                fourthdPart.setVisibility(View.GONE);
                fifthPart.setVisibility(View.GONE);
                sisthPart.setVisibility(View.GONE);
                left.setText(R.string.fromMestre);
                subtitleTime.setText(R.string.subTimetablesME);
                titolo.setText(R.string.titoloServices);
                loadFestivity();
                return true;
            case R.id.action_busV:
                currentVenue = "VE";
                left.setVisibility(View.VISIBLE);
                right.setVisibility(View.VISIBLE);
                myListView.setVisibility(View.VISIBLE);
                imageBG.setVisibility(View.VISIBLE);
                subtitleTime.setVisibility(View.VISIBLE);
                boatText.setVisibility(View.GONE);
                imageBGFirst.setVisibility(View.GONE);
                imageBGSec.setVisibility(View.GONE);
                secondPart.setVisibility(View.GONE);
                thirdPart.setVisibility(View.GONE);
                fourthdPart.setVisibility(View.GONE);
                fifthPart.setVisibility(View.GONE);
                sisthPart.setVisibility(View.GONE);
                left.setText(R.string.fromVenezia);
                subtitleTime.setText(R.string.subTimetablesVE);
                titolo.setText(R.string.titoloServices);
                loadFestivity();
                return true;
            case R.id.action_boat:
                left.setVisibility(View.GONE);
                right.setVisibility(View.GONE);
                myListView.setVisibility(View.GONE);
                imageBG.setVisibility(View.GONE);
                subtitleTime.setVisibility(View.GONE);
                boatText.setVisibility(View.VISIBLE);
                imageBGFirst.setVisibility(View.VISIBLE);
                imageBGSec.setVisibility(View.VISIBLE);
                secondPart.setVisibility(View.VISIBLE);
                thirdPart.setVisibility(View.VISIBLE);
                fourthdPart.setVisibility(View.VISIBLE);
                fifthPart.setVisibility(View.VISIBLE);
                sisthPart.setVisibility(View.VISIBLE);
                titolo.setText(R.string.titoloBoat);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Typeface XLight = Typeface.createFromAsset(getActivity().getAssets(), "fonts/GothamXLight.otf");
        myView = getView();
        fragmentWidth = getView().getWidth();
        myListView = (ListView)myView.findViewById(R.id.listViewTimetables);

        left = (TextView)myView.findViewById(R.id.textView33);
        left.setTypeface(XLight);
        right = (TextView)myView.findViewById(R.id.textView34);
        right.setTypeface(XLight);
        titolo = (TextView)myView.findViewById(R.id.textViewTitolo);
        titolo.setTypeface(XLight);
        subtitleTime = (TextView)myView.findViewById(R.id.textViewSubtitle);
        subtitleTime.setTypeface(XLight);
        imageBG = (ImageView)myView.findViewById(R.id.imageView8);
        imageBGFirst = (ImageView)myView.findViewById(R.id.imageViewFirstBG);
        imageBGSec = (ImageView)myView.findViewById(R.id.imageViewSecondBG);
        boatText = (TextView)myView.findViewById(R.id.textViewBoat);
        boatText.setTypeface(XLight);
        secondPart = (TextView)myView.findViewById(R.id.textViewsecond);
        secondPart.setTypeface(XLight);
        thirdPart = (TextView)myView.findViewById(R.id.textViewTre);
        thirdPart.setTypeface(XLight);
        fourthdPart = (TextView)myView.findViewById(R.id.textViewQuattro);
        fourthdPart.setTypeface(XLight);
        fifthPart = (TextView)myView.findViewById(R.id.textViewcinque);
        fifthPart.setTypeface(XLight);
        sisthPart = (TextView)myView.findViewById(R.id.textViewSei);
        sisthPart.setTypeface(XLight);

        boatText.setText(R.string.text_for_free_boat);
        secondPart.setText(R.string.secondPart);
        thirdPart.setText(R.string.thirdPart);
        fourthdPart.setText(R.string.fourthPart);
        fifthPart.setText(R.string.fifthPart);
        sisthPart.setText(R.string.sixthPart);
        boatText.setVisibility(View.GONE);
        imageBGFirst.setVisibility(View.GONE);
        imageBGSec.setVisibility(View.GONE);
        secondPart.setVisibility(View.GONE);
        thirdPart.setVisibility(View.GONE);
        sisthPart.setVisibility(View.GONE);
        fourthdPart.setVisibility(View.GONE);
        fifthPart.setVisibility(View.GONE);
        titolo.setText(R.string.titoloServices);
        right.setText(R.string.fromCaNoghera);
        if (Venue.currentVenue == 1) {
            currentVenue = "ME";
            left.setText(R.string.fromMestre);
            subtitleTime.setText(R.string.subTimetablesME);
        } else {
            currentVenue = "VE";
            left.setText(R.string.fromVenezia);
            subtitleTime.setText(R.string.subTimetablesVE);
        }
        loadStorageFestivity();



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
            mListener = (OnTimetableInteractionListener) activity;
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
    public interface OnTimetableInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }


    public void loadStorageFestivity () {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Festivity");
        query.getInBackground("7VTo3n7rum", new GetCallback<ParseObject>() {
                    public void done(ParseObject object, ParseException e) {
                        if (e == null) {
                            // object will be your game score

                            arrayFestivity = object.getList("festivity");
                            loadFestivity();
                        } else {
                            // something went wrong

                        }
                    }
                }
        );

    }
    public void loadFestivity() {

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH); //zero-based

        for (int i=0; i< arrayFestivity.size(); i++) {
            List<Object> myArray = (List<Object>) arrayFestivity.get(i);

            if ((day == (Integer) myArray.get(0)) && (month == (Integer) myArray.get(1) + 1)) {
                VPS2=true;
            }
        }

        checkWeekDay();
    }

    public void checkWeekDay( ) {
        Resources res = getResources();
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);//zero-based

        if ((month == 11) && ((day == 24) || (day == 25))) {

            queryForFestivity = ParseQuery.getQuery("ServicesVSP");
        } else {
            if ((weekDay == 7) || VPS2) {
                queryForFestivity = ParseQuery.getQuery("Services");
            } else {
                queryForFestivity = ParseQuery.getQuery("Services");
            }
        }

        queryForFestivity.orderByAscending("Order");
        queryForFestivity.whereEqualTo("MEVE", currentVenue);
        queryForFestivity.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> services, ParseException e) {
                if (e == null) {
                    arrayTimetables = services;
                    mCellAdapter = new TimetablesCellAdapter(getActivity(), fragmentWidth, arrayTimetables);
                    myListView.setAdapter(mCellAdapter);

                } else {
                    Log.d("Services", "Error: " + e.getMessage());
                }
            }
        });

    }
    public void setOffice() {


        if (Venue.currentVenue == 1) {

            loadFestivity();
        } else {
            loadFestivity();

        }
    }

}

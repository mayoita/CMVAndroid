package it.casinovenezia.casinodivenezia;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by massimomoro on 08/05/15.
 */
public class PokerFr extends Fragment {

    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    private ListView listView;


    private PokerAdapter mAdapter;
    private List<PokerItem> pokeritemlist = null;
    private List<PokerItem> myEventitemlist = null;
    List<ParseObject> ob;
    boolean mDualPane;
    int mCurCheckPosition = 0;

    ListEventItemClickListener ifaceItemClickListener;


    /** An interface for defining the callback method */
    public interface ListEventItemClickListener {
        /** This method will be invoked when an item in the ListFragment is clicked */
        void onListFragmentItemClick(int position);
    }
    /** A callback function, executed when this fragment is attached to an activity */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
    }
    public void setOffice () {
        if (Venue.currentVenue == 1) {

            myEventitemlist = inOffice("CN");
            if (getActivity() != null) {
                mAdapter = new PokerAdapter(getActivity(),
                        myEventitemlist);
                listView.setAdapter(mAdapter);
            }

        } else {

            myEventitemlist = inOffice("VE");
            if (getActivity() != null) {
                mAdapter = new PokerAdapter(getActivity(),
                        myEventitemlist);
                listView.setAdapter(mAdapter);
            }
        }
    }
    public ArrayList<PokerItem> inOffice(String office)    {
        ArrayList<PokerItem> helper = new ArrayList<PokerItem>();
        if (pokeritemlist != null) {
            for (int i = 0; i < pokeritemlist.size(); i++) {
                PokerItem myArray = (PokerItem) pokeritemlist.get(i);

                if (myArray.getOffice().equals(office)) {
                    helper.add(myArray);
                    helper.add(myArray);
                }
            }
        }
        return(helper);
    }
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PokerFr() {
    }
    //
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.poker_fragment, container, false);
        listView = (ListView) rootView.findViewById(R.id.list_events);





        if (savedInstanceState != null) {
            // Restore last state for checked position.
            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }

        if (rootView.findViewById(R.id.text_for_general_poker) != null) {
            mDualPane = true;
            // Its a tablet, so create a new detail fragment if one does not already exist
            // In dual-pane mode, the list view highlights the selected item.
            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            // Make sure our UI is in the correct state.
            //showDetails(mCurCheckPosition);


        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                showDetails(position);
            }
        });

        return rootView;
    }




    public static final PokerFr newInstance(String message) {
        PokerFr instance = new PokerFr();
        Bundle bdl = new Bundle();
        bdl.putString(EXTRA_MESSAGE, message);
        instance.setArguments(bdl);
        return instance;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadPoker();
        if (savedInstanceState != null) {
            // Restore last state for checked position.
            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }

    }

    /**
     * Helper function to show the details of a selected item, either by
     * displaying a fragment in-place in the current UI, or starting a
     * whole new activity in which it is displayed.
     */
    void showDetails(int index) {
        mCurCheckPosition = index;

        if (mDualPane) {
            // We can display everything in-place with fragments, so update
            // the list to highlight the selected item and show the data.
            listView.setItemChecked(index, true);

            // Check what fragment is currently shown, replace if needed.
            PokerDetails details = (PokerDetails)
                    getFragmentManager().findFragmentById(R.id.containerInLandPoker);
            if (details == null || details.getShownIndex() != myEventitemlist.get(index).getTournamentsName()) {
                // Make new fragment to show this selection.
                details = PokerDetails.newInstance(myEventitemlist.get(index).getTournamentDescription(),
                        myEventitemlist.get(index).getTournamentDate(),
                        myEventitemlist.get(index).getTournamentsName(),
                        myEventitemlist.get(index).getTournamentUrl(),
                        myEventitemlist.get(index).getStartDate(),
                        myEventitemlist.get(index).getTournamentsRules(),
                        myEventitemlist.get(index).getPokerData());

                // Execute a transaction, replacing any existing fragment
                // with this one inside the frame.
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                //  if (index == 0) {
                ft.replace(R.id.containerInLandPoker, details);
                // } else {
                // ft.replace(R.id.a_item, details);
                //}
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();

            }

        } else {
            // Otherwise we need to launch a new activity to display
            // the dialog fragment with selected text.
            Intent intent = new Intent();
            intent.setClass(getActivity(), PokerDetailsActivity.class);
            intent.putExtra("TournamentDescription", myEventitemlist.get(index).getTournamentDescription());
            intent.putExtra("TournamentDate", myEventitemlist.get(index).getTournamentDate());
            intent.putExtra("TournamentName", myEventitemlist.get(index).getTournamentsName());
            intent.putExtra("TournamentURL", myEventitemlist.get(index).getTournamentUrl());
            intent.putExtra("StartDate", myEventitemlist.get(index).getStartDate());
            intent.putStringArrayListExtra("TournamentsRules", myEventitemlist.get(index).getTournamentsRules());
            intent.putStringArrayListExtra("PokerData", myEventitemlist.get(index).getPokerData());
            startActivity(intent);
        }

    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }

    private String formatMyDate(Date myDate) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd LLLL yyyy", StarterApplication.currentLocale);

        return sdf.format(myDate);
    }
    public void loadPoker() {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                "Poker");

        query.orderByDescending("StartDate");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> eventList, ParseException e) {
                if (e == null) {
                    pokeritemlist = new ArrayList<PokerItem>();
                    for (ParseObject event : eventList) {
                        PokerItem map = new PokerItem();

                        map.setOffice((String) event.get("office"));
                        map.setMyId((String) event.getObjectId());
                        map.setTournamentDescription((String) event.get("TournamentDescription"));
                        map.setTournamentDate((String) event.get("TournamentDate"));
                        map.setTournamentsName((String) event.get("TournamentName"));
                        map.setTournamentsRules((ArrayList) event.get("TournamentsRules"));
                        map.setTournamentUrl((String) event.get("TournamentURL"));
                        map.setPokerData((ArrayList) event.get("PokerData"));
                        map.setStartDate(formatMyDate(event.getDate("StartDate")));
                        map.setEndDate(formatMyDate(event.getDate("EndDate")));

                        pokeritemlist.add(map);

                    }
                    setOffice();
                } else {
                    Log.d("events", "Error: " + e.getMessage());
                }
            }
        });
    }

}

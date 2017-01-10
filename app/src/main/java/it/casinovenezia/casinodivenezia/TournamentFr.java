package it.casinovenezia.casinodivenezia;

import android.app.Activity;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by massimomoro on 13/05/15.
 */
public class TournamentFr extends Fragment {

    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    private ListView listView;


    private TournamentAdapter mAdapter;
    private List<TournamentItem> pokeritemlist = null;
    private List<TournamentItem> myEventitemlist = null;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference tournaments = mRootRef.child("Tournaments");

    boolean mDualPane;
    int mCurCheckPosition = 0;

    String [] demoData = {"a", "b", "c", "d", "e","f", "g", "h", "i", "l"};

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
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TournamentFr() {
    }
    //
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tournament_fragment, container, false);
        listView = (ListView) rootView.findViewById(R.id.list_tournament);



        if (savedInstanceState != null) {
            // Restore last state for checked position.
            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }

        if (rootView.findViewById(R.id.text_for_general_tournament) != null) {
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




    public static final TournamentFr newInstance(String message) {
        TournamentFr instance = new TournamentFr();
        Bundle bdl = new Bundle();
        bdl.putString(EXTRA_MESSAGE, message);
        instance.setArguments(bdl);
        return instance;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadTournament();
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
            TournamentDetails details = (TournamentDetails)
                    getFragmentManager().findFragmentById(R.id.containerInLandTournament);
            if (details == null || details.getShownIndex() != myEventitemlist.get(index).getTournamentsName()) {
                // Make new fragment to show this selection.
                details = TournamentDetails.newInstance(
                        myEventitemlist.get(index).getTournamentDescription(),
                        myEventitemlist.get(index).getTournamentsName(),
                        myEventitemlist.get(index).getTournamentUrl(),
                        myEventitemlist.get(index).getStartDate(),
                        myEventitemlist.get(index).getTournamentsRules(),
                        myEventitemlist.get(index).getTournamentEvent(),
                        myEventitemlist.get(index).getType());
                // Execute a transaction, replacing any existing fragment
                // with this one inside the frame.
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                //  if (index == 0) {
                ft.replace(R.id.containerInLandTournament, details);
                ft.addToBackStack(null);
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
            intent.setClass(getActivity(), TournamentDetailsActivity.class);
            intent.putExtra("TournamentDescription", myEventitemlist.get(index).getTournamentDescription());
            intent.putExtra("TournamentName", myEventitemlist.get(index).getTournamentsName());
            intent.putExtra("TournamentURL", myEventitemlist.get(index).getTournamentUrl());
            intent.putExtra("StartDate", myEventitemlist.get(index).getStartDate());
            intent.putStringArrayListExtra("TournamentRules", myEventitemlist.get(index).getTournamentsRules());
            intent.putStringArrayListExtra("TournamentEvent", myEventitemlist.get(index).getTournamentEvent());
            intent.putExtra("Type", myEventitemlist.get(index).getType());
            startActivity(intent);
        }

    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }

    public void setOffice () {
        if (Venue.currentVenue == 1) {

            myEventitemlist = inOffice("CN");
            if (getActivity() != null) {
                mAdapter = new TournamentAdapter(getActivity(),
                        myEventitemlist);
                listView.setAdapter(mAdapter);
            }

        } else {

            myEventitemlist = inOffice("VE");
            if (getActivity() != null) {
                mAdapter = new TournamentAdapter(getActivity(),
                        myEventitemlist);
                listView.setAdapter(mAdapter);
            }
        }
    }
    public ArrayList<TournamentItem> inOffice(String office)    {
        ArrayList<TournamentItem> helper = new ArrayList<TournamentItem>();
        if(pokeritemlist != null) {
            for (int i = 0; i < pokeritemlist.size(); i++) {
                TournamentItem myArray = (TournamentItem) pokeritemlist.get(i);

                if (myArray.getOffice().equals(office)) {
                    helper.add(myArray);
                    helper.add(myArray);
                }
            }
        }
        return(helper);
    }


    public void loadTournament() {

        if (HomeActivity.tournamentlistitem == null) {

            tournaments.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    pokeritemlist = new ArrayList<TournamentItem>();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        TournamentItem map = new TournamentItem();

                        map.setOffice(child.child("office").getValue(String.class));

                        map.setTournamentDescription(child.child("TournamentDescription").getValue(String.class));
                        map.setTournamentsName(child.child("TournamentName").getValue(String.class));
                        map.setTournamentsRules((ArrayList) child.child("TournamentsRules").getValue());
                        map.setTournamentUrl(child.child("TournamentURL").getValue(String.class));
                        map.setType(child.child("Type").getValue(String.class));
                        map.setTournamentEvent((ArrayList) child.child("TournamentEvent").getValue());
                        map.setStartDate(child.child("StartDate").getValue(String.class));
                        map.setEndDate(child.child("EndDate").getValue(String.class));
                        map.setImageTournament(child.child("ImageTournament").getValue(String.class));
                        pokeritemlist.add(map);
                    }
                    Collections.sort(pokeritemlist, new Comparator<Object>() {
                        @Override
                        public int compare(Object lhs, Object rhs) {
                            TournamentItem a = (TournamentItem) lhs;
                            TournamentItem b = (TournamentItem) rhs;
                            DateFormat format =new SimpleDateFormat("dd/MM/yy");

                            Date dateA = null;
                            try {
                                dateA = format.parse(a.getStartDate());
                            } catch (java.text.ParseException e) {
                                e.printStackTrace();
                            }
                            ;
                            Date dateB = null;
                            try {
                                dateB = format.parse(b.getStartDate());
                            } catch (java.text.ParseException e) {
                                e.printStackTrace();
                            }


                            if (dateA.after(dateB)) {
                                return -1;
                            } else {
                                return 1;
                            }

                        }
                    });
                    HomeActivity.tournamentlistitem=pokeritemlist;
                    setOffice();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        } else {
            pokeritemlist=HomeActivity.tournamentlistitem;
            setOffice();
        }
    }

}

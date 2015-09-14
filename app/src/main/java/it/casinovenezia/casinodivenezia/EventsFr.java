package it.casinovenezia.casinodivenezia;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by massimomoro on 25/03/15.
 */
public class EventsFr extends Fragment {

    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    private ListView listView;
    List<ParseObject> ob;
    private EventsAdapter mAdapter;
    private List<EventItem> eventitemlist = null;
    private List<EventItem> myEventitemlist = null;

    ProgressDialog mProgressDialog;

    boolean mDualPane;
    int mCurCheckPosition = 0;


    /** An interface for defining the callback method */
    public interface ListEventItemClickListener {

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
    public EventsFr() {
    }
//
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.events_fragment, container, false);
        listView = (ListView) rootView.findViewById(R.id.list_events);




        if (savedInstanceState != null) {
            // Restore last state for checked position.
            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }

        if (rootView.findViewById(R.id.containerInLand) != null) {
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
                //final String titoloriga = (String)parent.getItemAtPosition(position);
                //Log.d("list", "Ho cliccato sull'elemento con il titolo " + titoloriga);
                showDetails(position);
            }
        });

        return rootView;
    }




    public static final EventsFr newInstance(String message) {
        EventsFr instance = new EventsFr();
        Bundle bdl = new Bundle();
        bdl.putString(EXTRA_MESSAGE, message);
        instance.setArguments(bdl);
        return instance;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadEvent();
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

            EventDetails details = (EventDetails)
                    getFragmentManager().findFragmentById(R.id.containerInLand);
            if (details == null || details.getShownIndex() != myEventitemlist.get(index).getName()) {
                // Make new fragment to show this selection.
                details = EventDetails.newInstance(myEventitemlist.get(index).getName(), myEventitemlist.get(index).getDescription(),myEventitemlist.get(index).getStartDate(),myEventitemlist.get(index).getMyId());

                // Execute a transaction, replacing any existing fragment
                // with this one inside the frame.
                FragmentTransaction ft = getFragmentManager().beginTransaction();
              //  if (index == 0) {
                    ft.replace(R.id.containerInLand, details);
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
            intent.setClass(getActivity(), EventDetailsActivity.class);
            intent.putExtra("name", myEventitemlist.get(index).getName());
            intent.putExtra("description", myEventitemlist.get(index).getDescription());
            intent.putExtra("date", myEventitemlist.get(index).getStartDate());
            intent.putExtra("objectId", myEventitemlist.get(index).getMyId());
            startActivity(intent);
        }

    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }


//    @Override
//    public void onListItemClick(ListView l, View v, int position, long id) {
//        // Do something when a list item is clicked
//    }



    public void setOffice () {
        if (EventsAdapter.engine != null) {
            EventsAdapter.engine.stop();
        }
        if (Venue.currentVenue == 1) {

            myEventitemlist = inOffice("CN");
            mAdapter = new EventsAdapter(getActivity(),
                    myEventitemlist);

            mAdapter.notifyDataSetChanged();
            listView.setAdapter(mAdapter);

        } else {

            myEventitemlist = inOffice("VE");
            mAdapter = new EventsAdapter(getActivity(),
                    myEventitemlist);
            //inOffice("VE");
            mAdapter.notifyDataSetChanged();
            listView.setAdapter(mAdapter);
        }
    }

    public ArrayList<EventItem> inOffice(String office)    {
        ArrayList<EventItem> helper = new ArrayList<EventItem>();
        if (eventitemlist != null) {
            for (int i = 0; i < eventitemlist.size(); i++) {
                EventItem myArray = (EventItem) eventitemlist.get(i);

                if (myArray.getOffice().equals(office)) {
                    helper.add(myArray);
                    helper.add(myArray);
                    //mAdapter.addItem(myArray);
                }
            }
        }
        return(helper);
    }


    private String formatMyDate(Date myDate) {

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd LLLL", StarterApplication.currentLocale);

        return sdf.format(myDate);
    }

    public void loadEvent () {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                "Events");
        query.orderByDescending("StartDate");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> eventList, ParseException e) {
                if (e == null) {
                    eventitemlist = new ArrayList<EventItem>();
                    for (ParseObject event : eventList) {
                        // Locate images in flag column
                        ParseFile image = (ParseFile) event.get("ImageName");

                        EventItem map = new EventItem();
                        map.setImageMain(image);
                        map.setOffice((String) event.get("office"));
                        map.setMyId((String) event.getObjectId());

                        switch (Locale.getDefault().getLanguage()) {
                            case "it":
                                map.setDescription((String) event.get("DescriptionIT"));
                                map.setName((String) event.get("NameIT"));
                                map.setMemo((String)event.get("memoIT"));
                                break;
                            case "es":
                                map.setDescription((String) event.get("DescriptionES"));
                                map.setName((String) event.get("NameES"));
                                map.setMemo((String) event.get("memoES"));
                                break;
                            case "fr":
                                map.setDescription((String) event.get("DescriptionFR"));
                                map.setName((String) event.get("NameFR"));
                                map.setMemo((String) event.get("memoFR"));
                                break;
                            case "de":
                                map.setDescription((String) event.get("DescriptionDE"));
                                map.setName((String) event.get("NameDE"));
                                map.setMemo((String) event.get("memoDE"));
                                break;
                            case "ru":
                                map.setDescription((String) event.get("DescriptionRU"));
                                map.setName((String) event.get("NameRU"));
                                map.setMemo((String) event.get("memoRU"));
                                break;
                            case "ch":
                                map.setDescription((String) event.get("DescriptionZH"));
                                map.setName((String) event.get("NameZH"));
                                map.setMemo((String) event.get("memoZH"));
                                break;
                            default:
                                map.setDescription((String) event.get("Description"));
                                map.setName((String) event.get("Name"));
                                map.setMemo((String) event.get("memo"));
                                break;
                        }

                        map.setStartDate(formatMyDate(event.getDate("StartDate")));
                        map.setEndDate(event.getDate("EndDate"));

                        eventitemlist.add(map);

                        if(isAdded()) {
                            setOffice();
                        } else {
                            Log.e("isAdded", "EventsFr not added");
                        }
                    }
                } else {
                    Log.d("events", "Error: " + e.getMessage());
                }
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        EventsAdapter.engine.shutdown();
    }
}

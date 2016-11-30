package it.casinovenezia.casinodivenezia;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
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
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;


import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by massimomoro on 25/03/15.
 */
public class EventsFr extends Fragment implements TextToSpeech.OnInitListener{

    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    private ListView listView;

    private EventFBAdapter mAdapterFB;
    private List<EventItem> eventitemlist = null;
    private List<EventItem> myEventitemlist = null;
    public static TextToSpeech engine;
    private Tracker mTracker;

    boolean mDualPane;
    int mCurCheckPosition = 0;


    /** The current relative path within the ContentManager. */
    private String currentPath = "";

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference eventsChild = mRootRef.child("Events");



    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onInit(int status) {
        Log.d("Speech", "OnInit - Status [" + status + "]");

        if (status == TextToSpeech.SUCCESS) {
            Log.d("Speech", "Success!");
            engine.setLanguage(StarterApplication.currentLocale);
        }
    }


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
// Obtain the shared Tracker instance.
        StarterApplication application = (StarterApplication) getActivity().getApplication();
        // mTracker = application.getDefaultTracker();
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

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {

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

                details = EventDetails.newInstance(myEventitemlist.get(index).getName(),
                        myEventitemlist.get(index).getDescription(),
                        myEventitemlist.get(index).getStartDate(),
                        myEventitemlist.get(index).getMyId(),
                        myEventitemlist.get(index).getImage1(),
                        myEventitemlist.get(index).getImage2(),
                        myEventitemlist.get(index).getImage3(),
                        this.getContext(),
                        myEventitemlist.get(index).getImageMain()
                );

                // Execute a transaction, replacing any existing fragment
                // with this one inside the frame.
                FragmentTransaction ft = getFragmentManager().beginTransaction();
              //  if (index == 0) {
                    ft.replace(R.id.containerInLand, details);
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
            intent.setClass(getActivity(), EventDetailsActivity.class);
            intent.putExtra("name", myEventitemlist.get(index).getName());
            intent.putExtra("description", myEventitemlist.get(index).getDescription());
            intent.putExtra("date", myEventitemlist.get(index).getStartDate());
            intent.putExtra("objectId", myEventitemlist.get(index).getMyId());
            intent.putExtra("image1", myEventitemlist.get(index).getImage1());
            intent.putExtra("image2", myEventitemlist.get(index).getImage2());
            intent.putExtra("image3", myEventitemlist.get(index).getImage3());
            intent.putExtra("imageMain", myEventitemlist.get(index).getImageMain());
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
        if (EventsFr.engine != null) {
            EventsFr.engine.stop();
        }
        if (Venue.currentVenue == 1) {

            myEventitemlist = inOffice("CN");
            mAdapterFB = new EventFBAdapter(getActivity(), R.layout.events_fragment, myEventitemlist);
         //   mAdapter = new EventsAdapter(getActivity(), this.contentManager,
     //               myEventitemlist);

   //         mAdapter.notifyDataSetChanged();
            listView.setAdapter(mAdapterFB);

        } else {

            myEventitemlist = inOffice("VE");
            mAdapterFB = new EventFBAdapter(getActivity(), R.layout.events_fragment, myEventitemlist);
      //      mAdapter = new EventsAdapter(getActivity(), this.contentManager,
      //              myEventitemlist);
            //inOffice("VE");
//            mAdapter.notifyDataSetChanged();
            listView.setAdapter(mAdapterFB);
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


    private String formatMyDate(String myDate) {
        DateFormat format = new SimpleDateFormat("dd/MM/yy");
        Date date = new Date();
        try {
             date = format.parse(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd LLLL", StarterApplication.currentLocale);

        return sdf.format(date);
    }

    public void loadEvent () {
        if(HomeActivity.eventitemlist == null) {
            eventitemlist = new ArrayList<EventItem>();


            eventsChild.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.


                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        EventItem map = new EventItem();
                        map.setImageMain(child.child("ImageName").getValue(String.class));
                        map.setImage1(child.child("ImageEvent1").getValue(String.class));
                        map.setImage2(child.child("ImageEvent2").getValue(String.class));
                        map.setImage3(child.child("ImageEvent3").getValue(String.class));
                        map.setOffice(child.child("office").getValue(String.class));
                        map.setIsSlotEvent(child.child("isSlotEvents").getValue(Boolean.class));
                        //Need for SlotDetailsActivity
                        map.setDescriptionIT(child.child("DescriptionIT").getValue(String.class));
                        map.setNameIT(child.child("NameIT").getValue(String.class));
                        //    map.setMyId((String) event.getObjectId());

                        switch (Locale.getDefault().getLanguage()) {
                            case "it":
                                map.setDescription(child.child("DescriptionIT").getValue(String.class));
                                map.setName(child.child("NameIT").getValue(String.class));
                                map.setMemo(child.child("memoIT").getValue(String.class));
                                break;
                            case "es":
                                map.setDescription(child.child("DescriptionES").getValue(String.class));
                                map.setName(child.child("NameES").getValue(String.class));
                                map.setMemo(child.child("memoES").getValue(String.class));
                                break;
                            case "fr":
                                map.setDescription(child.child("DescriptionFR").getValue(String.class));
                                map.setName(child.child("NameFR").getValue(String.class));
                                map.setMemo(child.child("memoFR").getValue(String.class));
                                break;
                            case "de":
                                map.setDescription(child.child("DescriptionDE").getValue(String.class));
                                map.setName(child.child("NameDE").getValue(String.class));
                                map.setMemo(child.child("memoDE").getValue(String.class));
                                break;
                            case "ru":
                                map.setDescription(child.child("DescriptionRU").getValue(String.class));
                                map.setName(child.child("NameRU").getValue(String.class));
                                map.setMemo(child.child("memoRU").getValue(String.class));
                                break;
                            case "zh":
                                map.setDescription(child.child("DescriptionZH").getValue(String.class));
                                map.setName(child.child("NameZH").getValue(String.class));
                                map.setMemo(child.child("memoZH").getValue(String.class));
                                break;
                            default:
                                map.setDescription(child.child("Description").getValue(String.class));
                                map.setName(child.child("Name").getValue(String.class));
                                map.setMemo(child.child("memo").getValue(String.class));
                                break;
                        }
                        map.setStartDate(formatMyDate(child.child("StartDate").getValue(String.class)));
                        map.setEndDate(child.child("EndDate").getValue(String.class));

                        eventitemlist.add(map);
                    }
                    Collections.sort(eventitemlist, new Comparator<Object>() {
                        @Override
                        public int compare(Object lhs, Object rhs) {
                            EventItem a = (EventItem) lhs;
                            EventItem b = (EventItem) rhs;
                            DateFormat format = new SimpleDateFormat("EEEE MM LLLL");

                            Date dateA = null;
                            try {
                                dateA = format.parse(a.getStartDate());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            ;
                            Date dateB = null;
                            try {
                                dateB = format.parse(b.getStartDate());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


                            if (dateA.after(dateB)) {
                                return -1;
                            } else {
                                return 1;
                            }

                        }
                    });
                    HomeActivity.eventitemlist = eventitemlist;
                    if (isAdded()) {
                        setOffice();
                    } else {
                        Log.e("isAdded", "EventsFr not added");
                    }

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("EventAdapter", "Failed to read value.", error.toException());
                }
            });

        } else {
            eventitemlist = HomeActivity.eventitemlist;
            if (isAdded()) {
                setOffice();
            } else {
                Log.e("isAdded", "EventsFr not added");

            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
      engine.shutdown();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Venue.currentVenue == 1) {
            // mTracker.setScreenName("EventDetailsCN");
        } else {
            // mTracker.setScreenName("EventDetailsVE");
        }
        // mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        if (mAdapterFB != null)
        mAdapterFB.notifyDataSetChanged();
        engine = new TextToSpeech(getContext(), this);
        engine.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {

            }

            @Override
            public void onDone(String utteranceId) {
//                if (isSpeaking != null) {
//                    isSpeaking.speak.setImageResource(R.drawable.speak);
//                }
            }

            @Override
            public void onError(String utteranceId) {

            }
        });
    }

}

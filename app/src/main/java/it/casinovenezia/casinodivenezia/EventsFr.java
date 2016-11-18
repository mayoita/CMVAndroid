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

import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobile.content.ContentItem;
import com.amazonaws.mobile.content.ContentListHandler;
import com.amazonaws.mobile.content.ContentListItem;
import com.amazonaws.mobile.content.ContentManager;
import com.amazonaws.mobile.content.ContentState;
import com.amazonaws.mobile.util.StringFormatUtils;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedScanList;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by massimomoro on 25/03/15.
 */
public class EventsFr extends Fragment implements TextToSpeech.OnInitListener{

    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    private ListView listView;
  //  List<ParseObject> ob;
    private EventsAdapter mAdapter;
    private EventFBAdapter mAdapterFB;
    private List<EventItem> eventitemlist = null;
    private List<EventItem> myEventitemlist = null;
    public static TextToSpeech engine;
    private Tracker mTracker;

    boolean mDualPane;
    int mCurCheckPosition = 0;
    private final DynamoDBMapper mapper;
    /** Content Manager that manages the content for this demo. */
    private ContentManager contentManager;
    /** Handles the main content list. */
    private EventsAdapter contentListItems;
    /** The current relative path within the ContentManager. */
    private String currentPath = "";

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference eventsChild = mRootRef.child("Events");


    private void createContentList(final View fragmentView, final ContentManager contentManager) {
        listView = (ListView) fragmentView.findViewById(R.id.list_events);
        contentListItems = new EventsAdapter(fragmentView.getContext(), contentManager,
                new EventsAdapter.ContentListPathProvider() {
                    @Override
                    public String getCurrentPath() {
                        return currentPath;
                    }
                },
                new EventsAdapter.ContentListCacheObserver() {
                    @Override
                    public void onCacheChanged() {
                        //refreshCacheSummary();
                    }
                },
                R.layout.events_fragment);

        listView.setAdapter(contentListItems);
      //  listView.setOnItemClickListener(this);
        listView.setOnCreateContextMenuListener(this);


    }

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
        mapper = AWSMobileClient.defaultMobileClient().getDynamoDBMapper();
    }
//
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.events_fragment, container, false);
        listView = (ListView) rootView.findViewById(R.id.list_events);

        this.contentManager = StarterApplication.contentManager;


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

//        AWSMobileClient.defaultMobileClient().
//                createDefaultContentManager(new ContentManager.BuilderResultHandler() {
//
//                    @Override
//                    public void onComplete(final ContentManager contentManager) {
//                        if (!isAdded()) {
//                            contentManager.destroy();
//                            return;
//                        }
//
//                        final View fragmentView = getView();
//                        EventsFr.this.contentManager = contentManager;
//                        createContentList(fragmentView, contentManager);
//                        contentManager.setContentRemovedListener(contentListItems);
//                       // dialog.dismiss();
//                        refreshContent(currentPath);
//                    }
//                });


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

                    ArrayList<EventFB> eventListFB = new ArrayList<EventFB>();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        EventItem map = new EventItem();
                        map.setImageMain(child.child("ImageName").getValue(String.class));
                        map.setImage1(child.child("ImageEvent1").getValue(String.class));
                        map.setImage2(child.child("ImageEvent2").getValue(String.class));
                        map.setImage3(child.child("ImageEvent3").getValue(String.class));
                        map.setOffice(child.child("office").getValue(String.class));
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
                            case "ch":
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
//            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
//            PaginatedScanList<EventsDO> result = mapper.scan(EventsDO.class, scanExpression);
//            for (EventsDO item : result) {
//                EventItem map = new EventItem();
//                            map.setImageMain(item.get_ImageName());
//                            map.setOffice(item.get_office());
//                        //    map.setMyId((String) event.getObjectId());
//
//                switch (Locale.getDefault().getLanguage()) {
//                                case "it":
//                                    map.setDescription(item.get_DescriptionIT());
//                                    map.setName(item.get_NameIT());
//                                    map.setMemo(item.get_memoIT());
//                                    break;
//                            case "es":
//                                map.setDescription(item.get_DescriptionES());
//                                map.setName(item.get_NameES());
//                                map.setMemo(item.get_memoES());
//                                break;
//                            case "fr":
//                                map.setDescription(item.get_DescriptionFR());
//                                map.setName(item.get_NameFR());
//                                map.setMemo(item.get_memoFR());
//                                break;
//                            case "de":
//                                map.setDescription(item.get_DescriptionDE());
//                                map.setName(item.get_NameDE());
//                                map.setMemo(item.get_memoDE());
//                                break;
//                            case "ru":
//                                map.setDescription(item.get_DescriptionRU());
//                                map.setName(item.get_NameRU());
//                                map.setMemo(item.get_memoRU());
//                                break;
//                            case "ch":
//                                map.setDescription(item.get_DescriptionZH());
//                                map.setName(item.get_NameZH());
//                                map.setMemo(item.get_memoZH());
//                                break;
//                                default:
//                                    map.setDescription(item.get_Description());
//                                    map.setName(item.getName());
//                                    map.setMemo(item.get_memo());
//                                    break;
//                }
//                map.setStartDate(formatMyDate(item.getStartDate()));
//                            map.setEndDate(item.get_EndDate());
//
//                            eventitemlist.add(map);
//            }
//            HomeActivity.eventitemlist = eventitemlist;
//                        if (isAdded()) {
//                            setOffice();
//                        } else {
//                            Log.e("isAdded", "EventsFr not added");
//                        }

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
        if (mAdapter != null)
        mAdapter.notifyDataSetChanged();
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
    public void refreshContent(final String path) {
   //     if (!listingContentInProgress) {
    //        listingContentInProgress = true;

          //  refreshCacheSummary();

            // Remove all progress listeners.
            contentManager.clearProgressListeners();

            // Clear old content.
            contentListItems.clear();
            contentListItems.notifyDataSetChanged();
            currentPath = path;
           // updatePath();

        //    final ProgressDialog dialog = getProgressDialog(
           //         R.string.content_progress_dialog_message_load_content);

            contentManager.listAvailableContent(path, new ContentListHandler() {
                @Override
                public boolean onContentReceived(final int startIndex,
                                                 final List<ContentItem> partialResults,
                                                 final boolean hasMoreResults) {
                    // if the activity is no longer alive, we can stop immediately.
                    if (getActivity() == null) {
                    //    listingContentInProgress = false;
                        return false;
                    }
                    if (startIndex == 0) {
                  //      dialog.dismiss();
                    }

                    for (final ContentItem contentItem : partialResults) {
                        // Add the item to the list.
                        contentListItems.add(new ContentListItem(contentItem));

                        // If the content is transferring, ensure the progress listener is set.
                        final ContentState contentState = contentItem.getContentState();
                        if (ContentState.isTransferringOrWaitingToTransfer(contentState)) {
                            contentManager.setProgressListener(contentItem.getFilePath(),
                                    contentListItems);
                        }
                    }
                    // sort items added.
                    contentListItems.sort(ContentListItem.contentAlphebeticalComparator);

                    if (!hasMoreResults) {
                  //      listingContentInProgress = false;
                    }
                    // Return true to continue listing.
                    return true;
                }

                @Override
                public void onError(final Exception ex) {
             //       dialog.dismiss();
             //       listingContentInProgress = false;
                    final Activity activity = getActivity();
                    if (activity != null) {
                        final AlertDialog.Builder errorDialogBuilder = new AlertDialog.Builder(activity);
                  //      errorDialogBuilder.setTitle(activity.getString(R.string.content_list_failure_text));
                        errorDialogBuilder.setMessage(ex.getMessage());
                        errorDialogBuilder.setNegativeButton(
                                activity.getString(R.string.content_dialog_ok), null);
                        errorDialogBuilder.show();
                    }
                }
            });
        }
   // }
}

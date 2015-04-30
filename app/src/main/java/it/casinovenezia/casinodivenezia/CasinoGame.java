package it.casinovenezia.casinodivenezia;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CasinoGame.OnGameInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CasinoGame#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CasinoGame extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "title";
    private ListView listView;
    private GamesAdapter mAdapter;

    private int[] arrayGames = {
        R.drawable.Fair,
            R.drawable.BlackJ,
            R.drawable.Texas,
            R.drawable.Banco,
            R.drawable.Carribean,
            R.drawable.French,
            R.drawable.Chemin,
            R.drawable.Trente
    };

    private int[] arraySlots = {
            R.drawable.SlotOffer,
            R.drawable.SlotNew,
            R.drawable.SlotRoom,
            R.drawable.OurJack

    };
    private int[] arrayGamesTitle = {
            R.string.fair,
            R.string.black,
            R.string.texas,
            R.string.punto,
            R.string.carribean,
            R.string.francese,
            R.string.chemin,
            R.string.trente
    };
    private int[] arraySlotsTitle = {
            R.string.what,
            R.string.whatnew,
            R.string.rooms,
            R.string.jackpot_slot
    };

    boolean mDualPane;
    int mCurCheckPosition = 0;


    private String mTitle;


    private OnGameInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * Test 2
     * @param param1 Parameter 1.

     * @return A new instance of fragment CasinoGame.
     */



    public static CasinoGame newInstance(String param1) {
        CasinoGame fragment = new CasinoGame();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    public CasinoGame() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_casino_game, container, false);
        listView = (ListView) rootView.findViewById(R.id.list_games);


        Display display = getActivity().getWindowManager().getDefaultDisplay();

        int width = display.getWidth();
        int height = (int) (width * 0.45);



        mAdapter = new GamesAdapter(getActivity(), height);
        listView.setAdapter(mAdapter);

        if (savedInstanceState != null) {
            // Restore last state for checked position.
            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }

        if (rootView.findViewById(R.id.event_details) != null) {
            mDualPane = true;
            // Its a tablet, so create a new detail fragment if one does not already exist
            // In dual-pane mode, the list view highlights the selected item.
            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            // Make sure our UI is in the correct state.
            showDetails(mCurCheckPosition);


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
            mListener = (OnGameInteractionListener) activity;
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
    public interface OnGameInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    void showDetails(int index) {
        mCurCheckPosition = index;

        if (mDualPane) {
            // We can display everything in-place with fragments, so update
            // the list to highlight the selected item and show the data.
            listView.setItemChecked(index, true);

            // Check what fragment is currently shown, replace if needed.
//            EventDetails details = (EventDetails)
//                    getFragmentManager().findFragmentById(R.id.containerInLand);
//            if (details == null || details.getShownIndex() != demoData[index]) {
//                // Make new fragment to show this selection.
//                details = EventDetails.newInstance(demoData[index]);
//
//                // Execute a transaction, replacing any existing fragment
//                // with this one inside the frame.
//                FragmentTransaction ft = getFragmentManager().beginTransaction();
//                //  if (index == 0) {
//                ft.replace(R.id.containerInLand, details);
//                // } else {
//                // ft.replace(R.id.a_item, details);
//                //}
//                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//                ft.commit();

//            }

        } else {
            // Otherwise we need to launch a new activity to display
            // the dialog fragment with selected text.
//            Intent intent = new Intent();
//            intent.setClass(getActivity(), EventDetailsActivity.class);
//            intent.putExtra("param1", demoData[index]);
//            startActivity(intent);
        }

    }
}

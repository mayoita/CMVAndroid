package it.casinovenezia.casinodivenezia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Config;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CasinoGame.OnGameInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CasinoGame#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CasinoGame extends Fragment implements OnBackPressedListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "title";
    private ListView listView;
    private GamesAdapter mAdapter;
    private int level = 0;
    private int levelTwo = 0;
    private CasinoGame me;
    private static final String TAG = "GAME_LIST";
    int height;
    GameDetailsFragment detailsG;

    private JSONObject gameList;
    public int currentGamePosition;
    public int currentGamePositionLevel2;
    JSONArray myjArr;


    String [] demoData = {"a", "b", "c", "d", "e","f", "g", "h", "i", "l"};

    private int[] textureArray = {
            R.drawable.tabletableview,
            R.drawable.slotstableview,
            R.drawable.onlinetableview
    };
    private int[] titleArray = {
            R.string.table_games,
            R.string.slot_games,
            R.string.online_games
    };

    private int[] arrayGames = {
            R.drawable.fair,
            R.drawable.blackj,
            R.drawable.texas,
            R.drawable.banco,
            R.drawable.carribean,
            R.drawable.french,
            R.drawable.chemin,
            R.drawable.trente
    };
    private int[] arrayGamesCN = {
            R.drawable.fair,
            R.drawable.blackj,
            R.drawable.texas,
            R.drawable.banco,
            R.drawable.carribean,
            R.drawable.french,
            R.drawable.chemin
    };

    private int[] arraySlots = {
            R.drawable.slotoffer,
            R.drawable.slotnew,
            R.drawable.ourjack,
            R.drawable.slotroom


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
    private int[] arrayGamesTitleCN = {
            R.string.fair,
            R.string.black,
            R.string.texas,
            R.string.punto,
            R.string.carribean,
            R.string.francese,
            R.string.chemin
    };
    private int[] arraySlotsTitle = {
            R.string.what,
            R.string.whatnew,
            R.string.jackpot_slot,
            R.string.rooms

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

        me = this;
        try {
            gameList = new JSONObject(loadJSONFromAsset());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        View rootView = inflater.inflate(R.layout.fragment_casino_game, container, false);
        listView = (ListView) rootView.findViewById(R.id.list_games);


        Display display = getActivity().getWindowManager().getDefaultDisplay();

        int width = display.getWidth();
        height = (int) (width * 0.45);

        mAdapter = new GamesAdapter(getActivity(), height, textureArray, titleArray);
        listView.setAdapter(mAdapter);
        level=0;


        if (savedInstanceState != null) {
            // Restore last state for checked position.
            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }

        if (rootView.findViewById(R.id.text_for_general) != null) {
            mDualPane = true;
            // Its a tablet, so create a new detail fragment if one does not already exist
            // In dual-pane mode, the list view highlights the selected item.
            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            // Make sure our UI is in the correct state.
            showDetails(mCurCheckPosition,2);


        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //final String titoloriga = (String)parent.getItemAtPosition(position);
                //Log.d("list", "Ho cliccato sull'elemento con il titolo " + titoloriga);
                currentGamePosition = position;
                if (level == 0) {
                    FragmentActivity activity = getActivity();
                    ((HomeActivity) activity).setOnBackPressedListener(me);
                    switch (position) {
                        case 0:
                            level++;
                            currentGamePositionLevel2 = 0;
                            try {
                                setOffice();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 1:
                            level++;
                            currentGamePositionLevel2 = 1;
                            try {
                                setOffice();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 2:
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.clickandplay.it"));
                            startActivity(browserIntent);
                            break;
                        default:
                            break;
                    }


                } else if (level == 1) {
                    switch (currentGamePositionLevel2) {
                        case 0:
                            showDetails(position, 0);

                            break;
                        case 1:
                            showDetails(position, 1);

                            break;
                        default:
                            break;
                    }

                }
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

    @Override
    public void doBack() {
        //
        if (level == 1) {
            mAdapter.textureArray = textureArray;
            mAdapter.titleArray = titleArray;
            mAdapter.notifyDataSetChanged();
            level--;
        }
        if (levelTwo == 1) {
            levelTwo = 0;
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
    public interface OnGameInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
public void openPop(View v) {
    detailsG.openPopWindow(v);
}
    void showDetails(int index, int levelGame) {
        mCurCheckPosition = index;

        if (mDualPane) {
            // We can display everything in-place with fragments, so update
            // the list to highlight the selected item and show the data.
            listView.setItemChecked(index, true);

            switch (levelGame) {
                case 0:
                     detailsG = null;
                    if (!(getFragmentManager().findFragmentById(R.id.containerInLandGame) instanceof SlotDetailsFragment)) {
                        detailsG = (GameDetailsFragment)
                                getFragmentManager().findFragmentById(R.id.containerInLandGame);
                    }

                    if (detailsG == null || detailsG.getShownIndex() != demoData[index]) {
                        // Make new fragment to show this selection.
                        try {
                            detailsG = GameDetailsFragment.newInstance(myjArr.getJSONArray(index).toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // Execute a transaction, replacing any existing fragment
                        // with this one inside the frame.
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        //  if (index == 0) {
                        ft.replace(R.id.containerInLandGame, detailsG);
                        // } else {
                        // ft.replace(R.id.a_item, details);
                        //}
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft.commit();
                    }
                    break;
                case 1:

                    SlotDetailsFragment details = null;
                    if (!(getFragmentManager().findFragmentById(R.id.containerInLandGame) instanceof GameDetailsFragment)) {
                        details = (SlotDetailsFragment)
                                getFragmentManager().findFragmentById(R.id.containerInLandGame);
                    }

                    if (details == null || details.getShownIndex() != demoData[index]) {
                        // Make new fragment to show this selection.
                        try {
                            details = SlotDetailsFragment.newInstance(myjArr.getJSONArray(index).toString(), index);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // Execute a transaction, replacing any existing fragment
                        // with this one inside the frame.
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        //  if (index == 0) {
                        ft.replace(R.id.containerInLandGame, details);
                        // } else {
                        // ft.replace(R.id.a_item, details);
                        //}
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft.commit();
                    }
                    break;
            }


        } else {
            // Otherwise we need to launch a new activity to display
            // the dialog fragment with selected text.
                switch (levelGame) {
                    case 0:
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), CasinoGame_Item_Activity.class);
                        try {
                            intent.putExtra("jsonArray", myjArr.getJSONArray(index).toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intentSlot = new Intent();
                        intentSlot.setClass(getActivity(), SlotDetailsActivity.class);
                        try {
                            intentSlot.putExtra("jsonArray", myjArr.getJSONArray(index).toString());
                            intentSlot.putExtra("index", index);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        startActivity(intentSlot);
                        break;
                    default:
                        break;
                }


        }

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getActivity().getAssets().open("base/GameDBase.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
    public void setOffice () throws JSONException {
    if (level == 1) {
            if (Venue.currentVenue == 0) {
                switch (currentGamePositionLevel2) {
                    case 0:
                        mAdapter.textureArray = arrayGames;
                        mAdapter.titleArray = arrayGamesTitle;
                        mAdapter.notifyDataSetChanged();

                        try {
                            myjArr = gameList.getJSONArray("Tavoli").getJSONArray(0).getJSONArray(2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 1:
                        mAdapter.textureArray = arraySlots;
                        mAdapter.titleArray = arraySlotsTitle;
                        mAdapter.notifyDataSetChanged();

                        try {
                            myjArr = gameList.getJSONArray("Slot").getJSONArray(0).getJSONArray(Venue.currentVenue + 2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }



            } else {
                switch (currentGamePositionLevel2) {
                    case 0:
                        mAdapter.textureArray = arrayGamesCN;
                        mAdapter.titleArray = arrayGamesTitleCN;
                        mAdapter.notifyDataSetChanged();

                        try {
                            myjArr = gameList.getJSONArray("Tavoli").getJSONArray(1).getJSONArray(2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 1:
                        mAdapter.textureArray = arraySlots;
                        mAdapter.titleArray = arraySlotsTitle;
                        mAdapter.notifyDataSetChanged();

                        try {
                            myjArr = gameList.getJSONArray("Slot").getJSONArray(0).getJSONArray(Venue.currentVenue + 2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }

            }
        }
    }



}

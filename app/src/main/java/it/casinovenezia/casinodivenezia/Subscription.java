package it.casinovenezia.casinodivenezia;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.SaveCallback;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Subscription.OnSubscriptionInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Subscription#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Subscription extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView mySubscription;
    private TextView news;
    private TextView slot;
    private TextView poker;
    private ToggleButton newsT;
    private ToggleButton slotT;
    private ToggleButton pokerT;
    public static final String PREFS_NAME = "MySubscription";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnSubscriptionInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Subscription.
     */
    // TODO: Rename and change types and number of parameters
    public static Subscription newInstance(String param1, String param2) {
        Subscription fragment = new Subscription();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Subscription() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        final SharedPreferences.Editor editor = settings.edit();
        if (newsT.isChecked()) {

            ParsePush.subscribeInBackground("Events", new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Log.d("com.parse.push", "successfully subscribed to the Events channel.");
                        editor.putBoolean("news", true);
                        editor.commit();
                    } else {
                        Log.e("com.parse.push", "failed to subscribe for push", e);
                    }
                }
            });
        } else {

            ParsePush.unsubscribeInBackground("Events", new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Log.d("com.parse.push", "successfully unsubscribed to the Events channel.");
                        editor.putBoolean("news", false);
                        editor.commit();
                    } else {
                        Log.e("com.parse.push", "failed to subscribe for push", e);
                    }
                }
            });
        }
        if (slotT.isChecked()) {
            ParsePush.subscribeInBackground("Slots", new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Log.d("com.parse.push", "successfully subscribed to the Slots channel.");
                        editor.putBoolean("slot", true);
                        editor.commit();
                    } else {
                        Log.e("com.parse.push", "failed to subscribe for push", e);
                    }
                }
            });
        } else {
            ParsePush.unsubscribeInBackground("Slots", new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Log.d("com.parse.push", "successfully unsubscribed to the Slots channel.");
                        editor.putBoolean("slot", false);
                        editor.commit();
                    } else {
                        Log.e("com.parse.push", "failed to subscribe for push", e);
                    }
                }
            });
        }
        if (pokerT.isChecked()) {
            ParsePush.subscribeInBackground("Poker", new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Log.d("com.parse.push", "successfully subscribed to the Poker channel.");
                        editor.putBoolean("poker", true);
                        editor.commit();
                    } else {
                        Log.e("com.parse.push", "failed to subscribe for push", e);
                    }
                }
            });
        } else {
            ParsePush.unsubscribeInBackground("Poker", new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Log.d("com.parse.push", "successfully unsubscribed to the Poker channel.");
                        editor.putBoolean("poker", false);
                        editor.commit();
                    } else {
                        Log.e("com.parse.push", "failed to subscribe for push", e);
                    }
                }
            });
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);

        View rootView = inflater.inflate(R.layout.fragment_subscription, container, false);
        Typeface XLight = Typeface.createFromAsset(getActivity().getAssets(), "fonts/GothamXLight.otf");
        mySubscription = (CustomMenuTitleView) rootView.findViewById(R.id.mysubscription);
        news = (TextView) rootView.findViewById(R.id.textView5);
        slot = (TextView) rootView.findViewById(R.id.textView6);
        poker = (TextView) rootView.findViewById(R.id.textView7);
        newsT = (ToggleButton)rootView.findViewById(R.id.toggleButton);
        slotT = (ToggleButton)rootView.findViewById(R.id.toggleButton2);
        pokerT = (ToggleButton)rootView.findViewById(R.id.toggleButton3);
        TextView diciotto = (TextView) rootView.findViewById(R.id.diciottopiu);
        diciotto.setMovementMethod(LinkMovementMethod.getInstance());


            if (settings.getBoolean("news", false)) {
                newsT.setChecked(true);
            } else {
                newsT.setChecked(false);
            }

            if (settings.getBoolean("slot", false)) {
                slotT.setChecked(true);
            } else {
                slotT.setChecked(false);
            }

            if (settings.getBoolean("poker", false)) {
                pokerT.setChecked(true);
            } else {
                pokerT.setChecked(false);
            }

        Shader textShader=new LinearGradient(0, 0, 0, mySubscription.getPaint().getTextSize(),
                new int[]{Color.rgb(253, 255, 26), Color.rgb(250, 100, 22)},
                new float[]{0, 1}, Shader.TileMode.CLAMP);
        mySubscription.getPaint().setShader(textShader);

        news.setTypeface(XLight);
        slot.setTypeface(XLight);
        poker.setTypeface(XLight);
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
            mListener = (OnSubscriptionInteractionListener) activity;
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
    public interface OnSubscriptionInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}

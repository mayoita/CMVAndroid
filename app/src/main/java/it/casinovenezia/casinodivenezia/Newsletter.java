package it.casinovenezia.casinodivenezia;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;


public class Newsletter extends Fragment {
    private Tracker mTracker;
    private CustomMenuTitleView newsletterTitle;
    private Button unsubscribe;
    private Button subscribe;

    // TODO:implementare codice
    public static Newsletter newInstance(String param1, String param2) {
        Newsletter fragment = new Newsletter();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public Newsletter() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StarterApplication application = (StarterApplication) getActivity().getApplication();
        // mTracker = application.getDefaultTracker();
    }
    @Override
    public void onResume() {
        super.onResume();
        // mTracker.setScreenName("Newsletter");
        // mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_newsletter, container, false);
        Typeface XLight = Typeface.createFromAsset(getActivity().getAssets(), "fonts/GothamXLight.otf");
        TextView diciotto = (TextView) rootView.findViewById(R.id.diciottopiu);
        diciotto.setMovementMethod(LinkMovementMethod.getInstance());
        newsletterTitle = (CustomMenuTitleView) rootView.findViewById(R.id.newsletter);
        unsubscribe = (Button) rootView.findViewById(R.id.unsubscribe);
        subscribe = (Button) rootView.findViewById(R.id.subscribe);

        Shader textShader=new LinearGradient(0, 0, 0, newsletterTitle.getPaint().getTextSize(),
                new int[]{Color.rgb(253, 255, 26), Color.rgb(250, 100, 22)},
                new float[]{0, 1}, Shader.TileMode.CLAMP);
        newsletterTitle.getPaint().setShader(textShader);
        unsubscribe.getPaint().setShader(textShader);
        unsubscribe.setTypeface(XLight);

        subscribe.getPaint().setShader(textShader);
        subscribe.setTypeface(XLight);

        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        android.support.v7.app.ActionBar action_bar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        action_bar.setDisplayShowCustomEnabled(false);
        action_bar.setDisplayShowTitleEnabled(true);
    }


}

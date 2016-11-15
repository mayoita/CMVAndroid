package it.casinovenezia.casinodivenezia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.share.widget.LikeView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class Facebook extends Fragment {

    private Button myButton;
    private CallbackManager mCallbackManager;
    private Tracker mTracker;
    LikeView likeView;
    int likeState;
    int[] currentState;

    public static Facebook newInstance(String param1, String param2) {
        Facebook fragment = new Facebook();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public Facebook() {
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
        // mTracker.setScreenName("FREE_ENTRY_FACEBOOK");
         // mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mCallbackManager = CallbackManager.Factory.create();
        Typeface XLight = Typeface.createFromAsset(getActivity().getAssets(), "fonts/GothamXLight.otf");
        View rootView = inflater.inflate(R.layout.fragment_facebook, container, false);
        myButton = (Button) rootView.findViewById(R.id.facebook);

        myButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkBGColorOfLikeView();
                if (currentState.length == 4) {
                    Context context = getActivity().getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, R.string.like_us, duration);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                } else {
                    Intent intent = new Intent(getActivity(), FreeEntrance.class);
                    startActivity(intent);
                    // mTracker.send(new HitBuilders.EventBuilder()
                    //        .setCategory("FREE_ENTRY")
                   //         .setAction("press")
                    //        .build());
                }
            }
        });

        Shader textShader=new LinearGradient(0, 0, 0, myButton.getPaint().getTextSize(),
                new int[]{Color.rgb(253, 255, 26), Color.rgb(250, 100, 22)},
                new float[]{0, 1}, Shader.TileMode.CLAMP);
        myButton.getPaint().setShader(textShader);
        myButton.setTypeface(XLight);
        TextView diciotto = (TextView) rootView.findViewById(R.id.diciottopiu);
        diciotto.setMovementMethod(LinkMovementMethod.getInstance());
        likeView = (LikeView) rootView.findViewById(R.id.like_view);
        likeView.setLikeViewStyle(LikeView.Style.BOX_COUNT);
        likeView.setAuxiliaryViewPosition(LikeView.AuxiliaryViewPosition.INLINE);
        likeView.setObjectIdAndType(
                "https://www.facebook.com/casinovenezia",
                LikeView.ObjectType.PAGE);
        likeView.setFragment(this);
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
       // checkBGColorOfLikeView();

        // in futuro può tornare utile nel caso cambino le API
//        StateListDrawable stateListDrawable = (StateListDrawable)child2.getBackground();
//        Method getStateDrawableIndex = null;
//        try {
//            getStateDrawableIndex = StateListDrawable.class.getMethod("getStateDrawableIndex", int[].class);
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//        Method getStateDrawable = null;
//        try {
//            getStateDrawable = StateListDrawable.class.getMethod("getStateDrawable", int.class);
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//        int index = 0;
//        try {
//            index = (int) getStateDrawableIndex.invoke(stateListDrawable,currentState);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//        try {
//            Drawable drawable = (Drawable) getStateDrawable.invoke(stateListDrawable,index);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }

    }

    private void checkBGColorOfLikeView (){
        LinearLayout child = (LinearLayout) likeView.getChildAt(0);
        View child2 =  child.getChildAt(0);

        StateListDrawable background = (StateListDrawable) child2.getBackground();
        currentState = background.getState();
        likeState = currentState[0];
    }

    public void onButtonPressed(Uri uri) {

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

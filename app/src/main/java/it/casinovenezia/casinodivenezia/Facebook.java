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
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.share.widget.LikeView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Facebook.OnFacebookInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Facebook#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Facebook extends Fragment {

    private Button myButton;
    private CallbackManager mCallbackManager;
    LikeView likeView;
    int likeState;
    int[] currentState;
    private OnFacebookInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Facebook.
     */
    // TODO: Rename and change types and number of parameters
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
        if (getArguments() != null) {

        }

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
                    CharSequence text = "Please like us on Facebook!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                } else {

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
            mListener = (OnFacebookInteractionListener) activity;
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
    public interface OnFacebookInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }


}

package it.casinovenezia.casinodivenezia;


import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by massimomoro on 25/03/15.
 */
public class HomeFr extends Fragment {

    //DelegateListener mListener;
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    ImageButton helpshiftButton;

    //Container Activity must implement this interface
//    public interface DelegateListener {
//        public void onItemSelected(Uri articleUri);
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    //TODO:implementa il controllo sulle interfacce
//   @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            mListener = (DelegateListener) activity;
//        } catch (ClassCastException e) {
//
//            throw new ClassCastException(activity.getClass().getName() + " must implement onItemSelected");
//        }
//    }

    public static final HomeFr newInstance(String message) {
        HomeFr instance = new HomeFr();
        Bundle bdl = new Bundle();
        bdl.putString(EXTRA_MESSAGE, message);
        instance.setArguments(bdl);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.home_fragment, container, false);

        TextView txt = (TextView) rootView.findViewById(R.id.cavendramin);
        Typeface XLight = Typeface.createFromAsset(getActivity().getAssets(), "fonts/GothamXLight.otf");
        Typeface Thin = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Giorgio-Thin.ttf");
        txt.setTypeface(XLight);

        TextView apertoDalle = (TextView) rootView.findViewById(R.id.apertodalle);
        apertoDalle.setTypeface(XLight);

        TextView jackpotamount = (TextView) rootView.findViewById(R.id.jackpotamount);
        jackpotamount.setTypeface(Thin);



        TextView jackpotLabel = (TextView) rootView.findViewById(R.id.jackpotLabel);
        jackpotLabel.setTypeface(XLight);

        TextView diciotto = (TextView) rootView.findViewById(R.id.diciottopiu);
        diciotto.setMovementMethod(LinkMovementMethod.getInstance());

        helpshiftButton = (ImageButton) rootView.findViewById(R.id.helpshift);
        helpshiftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),
                        "Help is clicked !", Toast.LENGTH_SHORT).show();
            }
        });


        Shader textShader=new LinearGradient(0, 0, 0, jackpotLabel.getPaint().getTextSize(),
                new int[]{Color.rgb(253, 255, 26), Color.rgb(250, 100, 22)},
                new float[]{0, 1}, Shader.TileMode.CLAMP);
        jackpotLabel.getPaint().setShader(textShader);

        return rootView;
    }


}

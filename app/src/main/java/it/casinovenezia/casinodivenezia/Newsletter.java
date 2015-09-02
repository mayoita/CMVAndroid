package it.casinovenezia.casinodivenezia;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Newsletter.OnNewsletterInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Newsletter#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Newsletter extends Fragment {

    private CustomMenuTitleView newsletterTitle;
    private Button unsubscribe;
    private Button subscribe;

    // TODO: Rename and change types and number of parameters
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
    public interface OnNewsletterInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}

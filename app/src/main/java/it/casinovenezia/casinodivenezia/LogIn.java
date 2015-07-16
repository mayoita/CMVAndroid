package it.casinovenezia.casinodivenezia;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphResponse;
import com.facebook.GraphRequest;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;
import com.parse.twitter.Twitter;
import com.parse.ui.ParseLoginBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LogIn.OnLogInInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LogIn#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogIn extends Fragment {
    private static final int LOGIN_REQUEST = 0;

    private TextView titleTextView;
    private TextView emailTextView;
    private TextView nameTextView;
    private Button loginOrLogoutButton;
    private ImageView pic;



    private ParseUser currentUser;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnLogInInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LogIn.
     */
    // TODO: Rename and change types and number of parameters
    public static LogIn newInstance(String param1, String param2) {
        LogIn fragment = new LogIn();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public LogIn() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Typeface XLight = Typeface.createFromAsset(getActivity().getAssets(), "fonts/GothamXLight.otf");
        View rootView = inflater.inflate(R.layout.fragment_log_in, container, false);
        TextView diciotto = (TextView) rootView.findViewById(R.id.diciottopiu);
        diciotto.setMovementMethod(LinkMovementMethod.getInstance());
        emailTextView = (TextView)rootView.findViewById(R.id.textEmail);
        pic = (ImageView)rootView.findViewById(R.id.circular_image_view);

        nameTextView = (TextView) rootView.findViewById(R.id.please);
        loginOrLogoutButton = (Button) rootView.findViewById(R.id.parse_login_button);
        loginOrLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser != null) {
                    // User clicked to log out.
                    ParseUser.logOut();
                    currentUser = null;

                    showProfileLoggedOut();
                } else {
                    // User clicked to log in.
                    ParseLoginBuilder loginBuilder = new ParseLoginBuilder(
                            getActivity());

                    startActivityForResult(loginBuilder.build(), LOGIN_REQUEST);
                }
            }
        });

        Shader textShader=new LinearGradient(0, 0, 0, loginOrLogoutButton.getPaint().getTextSize(),
                new int[]{Color.rgb(253, 255, 26), Color.rgb(250, 100, 22)},
                new float[]{0, 1}, Shader.TileMode.CLAMP);
        loginOrLogoutButton.getPaint().setShader(textShader);
        loginOrLogoutButton.setTypeface(XLight);
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
            mListener = (OnLogInInteractionListener) activity;
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
    public interface OnLogInInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onStart() {
        super.onStart();

        currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            if (ParseFacebookUtils.isLinked(currentUser)) {
                showProfileLoggedIn();
                return;
            }
            if (ParseTwitterUtils.isLinked(currentUser)) {
                Twitter twitterUser = ParseTwitterUtils.getTwitter();
                if (twitterUser.getScreenName().length() > 0) {
                    nameTextView.setText(twitterUser.getScreenName());

                }
                loginOrLogoutButton.setText(R.string.profile_logout_button_label);
                return;
            }
            String myName = getResources().getString(R.string.welcome) + "\n" + currentUser.getString("name");
            emailTextView.setText((CharSequence) currentUser.getEmail());
            nameTextView.setText(myName);
            loginOrLogoutButton.setText(R.string.profile_logout_button_label);
        } else {
            showProfileLoggedOut();
        }
    }
    /**
     * Shows the profile of the given user.
     */
    private void showProfileLoggedIn() {

        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        URL imageURL = null;
                        Bitmap bitmap = null;
                        try {
                            String myName = getResources().getString(R.string.welcome) + "\n" + object.getString("first_name") + " " + object.getString("last_name");
                            String id = object.optString("id");
                            emailTextView.setText(object.getString("email"));
                            nameTextView.setText(myName);
                            downloadAvatar(id);
                            //imageURL = new URL("https://graph.facebook.com/" + id + "/picture");
                            //bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
                           // pic.setImageBitmap(bitmap);


                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                        // Application code
                    }
                });

        request.executeAsync();

        //titleTextView.setText(R.string.profile_title_logged_in);
        emailTextView.setText(currentUser.getEmail());
        String fullName = currentUser.getString("name");
        if (fullName != null) {
            nameTextView.setText(fullName);
        }
        loginOrLogoutButton.setText(R.string.profile_logout_button_label);
    }
    private synchronized void downloadAvatar(final String myId) {
        AsyncTask<Void, Void, Bitmap> task = new AsyncTask<Void, Void, Bitmap>() {

            @Override
            public Bitmap doInBackground(Void... params) {
                URL fbAvatarUrl = null;
                Bitmap fbAvatarBitmap = null;
                try {

                    URL imageURL = new URL("https://graph.facebook.com/" + myId + "/picture?type=large");
                    fbAvatarBitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return fbAvatarBitmap;
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                pic.setImageBitmap(result);

            }

        };
        task.execute();
    }

    /**
     * Show a message asking the user to log in, toggle login/logout button text.
     */
    private void showProfileLoggedOut() {
        //titleTextView.setText(R.string.profile_title_logged_out);
        emailTextView.setText("");
        nameTextView.setText(R.string.please);
        loginOrLogoutButton.setText(R.string.profile_login_button_label);

        Drawable res = getResources().getDrawable(R.drawable.loginimage);
        pic.setImageDrawable(res);
    }

//    private void onLoginButtonClicked() {
//
//        List<String> permissions = Arrays.asList("public_profile", "user_about_me", "user_relationships", "user_birthday", "user_location", "email");
//        ParseFacebookUtils.logInInBackground(permissions, this, new LogInCallback() {
//            @Override
//            public void done(ParseUser pUser, ParseException err) {
//
//                if (email != null) {
//                    pUser.put("email", email);
//                }
//                if (fName != null) {
//                    pUser.put("firstName", fName);
//                }
//                if (lName != null) {
//                    pUser.put("lastName", lName);
//                }
//                if (birthday != null) {
//                    pUser.put("birthday", birthday);
//                }
//                if (relationship != null) {
//                    pUser.put("relationship", relationship);
//                }
//
//                pUser.saveInBackground();
//
//
//                if (pUser == null) {
//                    Log.d("The Bar App", "Uh oh. The user cancelled the Facebook login.");
//                } else if (pUser.isNew()) {
//                    Log.d("The Bar App", "User signed up and logged in through Facebook!");
//                    showNextActivity();
//                } else {
//                    Log.d("The Bar App", "User logged in through Facebook!");
//                    showNextActivity();
//                }
//            }
//        });
//
//        com.facebook.Request.executeMeRequestAsync(ParseFacebookUtils.getSession(), new com.facebook.Request.GraphUserCallback() {
//
//            @Override
//            public void onCompleted(GraphUser user, Response response) {
//                email = user.getProperty("email").toString();
//                fName = user.getFirstName();
//                lName = user.getLastName();
//
//
//
//            }
//        });
//    }
}

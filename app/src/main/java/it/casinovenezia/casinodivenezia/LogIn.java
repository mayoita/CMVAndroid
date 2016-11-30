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
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphResponse;
import com.facebook.GraphRequest;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class LogIn extends Fragment {
    private static final int LOGIN_REQUEST = 0;
    private Tracker mTracker;
    private TextView titleTextView;
    private TextView emailTextView;
    private TextView nameTextView;
    private Button loginOrLogoutButton;
    private ImageView pic;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "LogInAcitivityAuth";


//    private ParseUser currentUser;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    public static LogIn newInstance(String param1, String param2) {
        LogIn fragment = new LogIn();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public LogIn() {
        // Required empty public constructor
    }
    @Override
    public void onResume() {
        super.onResume();
        // mTracker.setScreenName("LogIn");
        // mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StarterApplication application = (StarterApplication) getActivity().getApplication();
        // mTracker = application.getDefaultTracker();
        setHasOptionsMenu(true);

        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        // [START auth_state_listener]
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // [START_EXCLUDE]
                updateUI(user);
                // [END_EXCLUDE]
            }
        };
    }
    private void updateUI(FirebaseUser user) {


      //  TextView idView = (TextView) findViewById(R.id.anonymous_status_id);
     //   TextView emailView = (TextView) findViewById(R.id.anonymous_status_email);
        boolean isSignedIn = (user != null);

        // Status text
        if (isSignedIn) {
      //      idView.setText(getString(R.string.id_fmt, user.getUid()));
     //       emailView.setText(getString(R.string.email_fmt, user.getEmail()));
        } else {
       //     idView.setText(R.string.signed_out);
      //      emailView.setText(null);
        }

        // Button visibility
      //  findViewById(R.id.button_anonymous_sign_in).setEnabled(!isSignedIn);
      //  findViewById(R.id.button_anonymous_sign_out).setEnabled(isSignedIn);
      //  findViewById(R.id.button_link_account).setEnabled(isSignedIn);
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
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    if (user.isAnonymous()) {
                        Intent logInSignIn = new Intent(getActivity(), SignUp.class);
                        startActivity(logInSignIn);
                    }
                }
//                if (currentUser != null) {
//                    // User clicked to log out.
//                    ParseUser.logOut();
//                    currentUser = null;
//                    mTracker.send(new HitBuilders.EventBuilder()
//                            .setCategory("LOGGING")
//                            .setAction("press")
//                            .build());
//                    showProfileLoggedOut();
//                } else {
//                    // User clicked to log in.
//                    ParseLoginBuilder loginBuilder = new ParseLoginBuilder(
//                            getActivity());
//
//                    startActivityForResult(loginBuilder.build(), LOGIN_REQUEST);
//                }
            }
        });

        Shader textShader=new LinearGradient(0, 0, 0, loginOrLogoutButton.getPaint().getTextSize(),
                new int[]{Color.rgb(253, 255, 26), Color.rgb(250, 100, 22)},
                new float[]{0, 1}, Shader.TileMode.CLAMP);
        loginOrLogoutButton.getPaint().setShader(textShader);
        loginOrLogoutButton.setTypeface(XLight);
        return rootView;

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
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        FirebaseUser user = mAuth.getCurrentUser();

        if (user.isAnonymous()) {
            return;
        }
        if (user != null) {
            if (user.getProviderId().equals("password")) {
                nameTextView.setText("prova");
                emailTextView.setText("due");
            }
            String providerId = "";
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                providerId = profile.getProviderId();

                // UID specific to the provider
                String uid = profile.getUid();

                // Name, email address, and profile photo Url
                String name = profile.getDisplayName();
                String email = profile.getEmail();
                Uri photoUrl = profile.getPhotoUrl();
            };
            if (providerId.equals("password")) {
                nameTextView.setText("prova");
                emailTextView.setText("due");
            }

        } else {
            // User is signed out
            Log.d(TAG, "onAuthStateChanged:signed_out");
        }
//        currentUser = ParseUser.getCurrentUser();
//        if (currentUser != null) {
//            if (ParseFacebookUtils.isLinked(currentUser)) {
//                showProfileLoggedIn();
//                return;
//            }
//            if (ParseTwitterUtils.isLinked(currentUser)) {
//                Twitter twitterUser = ParseTwitterUtils.getTwitter();
//                if (twitterUser.getScreenName().length() > 0) {
//                    nameTextView.setText(twitterUser.getScreenName());
//
//                }
//                loginOrLogoutButton.setText(R.string.profile_logout_button_label);
//                return;
//            }
//            String myName = getResources().getString(R.string.welcome) + "\n" + currentUser.getString("name");
//            emailTextView.setText((CharSequence) currentUser.getEmail());
//            nameTextView.setText(myName);
//            loginOrLogoutButton.setText(R.string.profile_logout_button_label);
//        } else {
//            showProfileLoggedOut();
//        }
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


                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,first_name, last_name");
        request.setParameters(parameters);

        request.executeAsync();

        //titleTextView.setText(R.string.profile_title_logged_in);
     //   emailTextView.setText(currentUser.getEmail());
       // String fullName = currentUser.getString("name");
    //    if (fullName != null) {
       //     nameTextView.setText(fullName);
     //   }
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
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        android.support.v7.app.ActionBar action_bar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        action_bar.setDisplayShowCustomEnabled(false);
        action_bar.setDisplayShowTitleEnabled(true);
    }
}

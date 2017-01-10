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
import android.support.annotation.Nullable;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


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
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference().child("users");


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
    private void updateUI(final FirebaseUser user) {

        if (user.isAnonymous()) {
            showProfileLoggedOut();
            return;
        }
        if (user != null) {

            String providerId = "";
            Uri photoUrl = user.getPhotoUrl();
            String name =user.getDisplayName();
            String email = user.getEmail();
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                providerId = profile.getProviderId();

                // UID specific to the provider
                String uid = profile.getUid();

                // Name, email address, and profile photo Url
                 name = profile.getDisplayName();
                 email = profile.getEmail();
                 photoUrl = profile.getPhotoUrl();
            }

            if (providerId.equals("password")) {
                DatabaseReference mUser = mRootRef.child(user.getUid());
                mUser.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User a = dataSnapshot.getValue(User.class);
                        nameTextView.setText(a.name);
                        emailTextView.setText(user.getEmail());
                        loginOrLogoutButton.setText(R.string.profile_logout_button_label);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            } else {
                nameTextView.setText(name);
                emailTextView.setText(email);
                Picasso.with(getContext()).load(photoUrl).into(pic);
                loginOrLogoutButton.setText(R.string.profile_logout_button_label);


            }

        } else {
            // User is signed out
            Log.d(TAG, "onAuthStateChanged:signed_out");
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
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    if (user.isAnonymous() || user.getProviderData().size() == 1) {
                        Intent logInSignIn = new Intent(getActivity(), SignUp.class);
                        startActivity(logInSignIn);
                    } else {
                            //FirebaseAuth.getInstance().signOut();
                        for (UserInfo profile : user.getProviderData()) {
                            // Id of the provider (ex: google.com)
                            String providerId = profile.getProviderId();

                            // UID specific to the provider
                            String uid = profile.getUid();

                            // Name, email address, and profile photo Url
                            String name = profile.getDisplayName();
                            String email = profile.getEmail();
                            Uri photoUrl = profile.getPhotoUrl();
                            if (providerId.equals("password") || providerId.equals("facebook.com")) {
                                user.unlink(providerId).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (!task.isSuccessful()) {
                                            Log.d("unlink",task.getException().getLocalizedMessage());

                                            // Auth provider u)nlinked from account
                                        } else {
                                            Log.d("unlink",task.getResult().toString());
                                        }
                                    }
                                });
                            }
                        };
                        showProfileLoggedOut();

                    }
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

package it.casinovenezia.casinodivenezia;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;


public class EventDetailsActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener{

    private SliderLayout mySlider;
    private String image1;
    private String image2;
    private String image3;
    private String imageMain;
    private Context myContext;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://cmv-gioco.appspot.com/Events");

    private int convertDpToPx(int dp, DisplayMetrics displayMetrics) {
        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
        return Math.round(pixels);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        myContext = this;
        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {

        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        setContentView(R.layout.activity_event_details);

        mySlider = (SliderLayout)findViewById(R.id.slider);
        TextView name = (TextView) findViewById(R.id.textView8);
        TextView date = (TextView) findViewById(R.id.textView9);
        TextView description = (TextView) findViewById(R.id.textView10);
        name.setText(i.getStringExtra("name"));
        date.setText(i.getStringExtra("date"));
        description.setText(i.getStringExtra("description"));
        TextView diciotto = (TextView) findViewById(R.id.diciottopiu);
        diciotto.setMovementMethod(LinkMovementMethod.getInstance());
        Typeface XLight = Typeface.createFromAsset(getAssets(), "fonts/GothamXLight.otf");
        name.setTypeface(XLight);
        date.setTypeface(XLight);
        description.setTypeface(XLight);

        image1 = (i.getStringExtra("image1"));
        image2 = (i.getStringExtra("image2"));
        image3 = (i.getStringExtra("image3"));
        imageMain = (i.getStringExtra("imageMain"));
        loadImage(i.getStringExtra("objectId"));
        Display display = getWindowManager().getDefaultDisplay();
        ImageView imageView = (ImageView) findViewById(R.id.imageView7);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int width = display.getWidth();
        int height = (int) (width * 0.66); // 0.75 if image aspect ration is 4:3, change accordingly

        RelativeLayout.LayoutParams fp = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height + convertDpToPx(6,dm));
        RelativeLayout.LayoutParams sp = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height);

        fp.setMargins(convertDpToPx(7,dm) ,convertDpToPx(37,dm), convertDpToPx(7,dm), 0);
        sp.setMargins(convertDpToPx(10, dm), convertDpToPx(40, dm), convertDpToPx(10, dm), 0);
        imageView.setLayoutParams(fp);
        mySlider.setLayoutParams(sp);


        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            // If the screen is now in landscape mode, we can show the
            // dialog in-line with the list so we don't need this activity.
            finish();
            return;
        }

        if (savedInstanceState == null) {
            // During initial setup, plug in the details fragment.

           // EventDetails details = new EventDetails();
           // details.setArguments(getIntent().getExtras());
          //  getSupportFragmentManager().beginTransaction().add(R.id.event_details, details).commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSliderClick(BaseSliderView baseSliderView) {


    }
    public void loadImage(String myId) {
                       createSlider();
    }

    public void createSlider (){
        final HashMap<String, String> file_maps = new HashMap<String, String>();
        if (image1 != null) {
            storageRef.child(image1).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    DefaultSliderView textSliderView = new DefaultSliderView(myContext);
                    textSliderView
                            .description("Image")
                            .image(uri.toString())
                            .setScaleType(BaseSliderView.ScaleType.Fit);
                            //.setOnSliderClickListener(this);
                    mySlider.addSlider(textSliderView);

                }
            });

        } else {
            storageRef.child(imageMain).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    DefaultSliderView textSliderView = new DefaultSliderView(myContext);
                    textSliderView
                            .description("Image")
                            .image(uri.toString())
                            .setScaleType(BaseSliderView.ScaleType.Fit);
                    //.setOnSliderClickListener(this);
                    mySlider.addSlider(textSliderView);

                }
            });
        }
        if (image2 != null) {
            storageRef.child(image2).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    DefaultSliderView textSliderView = new DefaultSliderView(myContext);
                    textSliderView
                            .description("Image")
                            .image(uri.toString())
                            .setScaleType(BaseSliderView.ScaleType.Fit);
                    // .setOnSliderClickListener(this);
                    mySlider.addSlider(textSliderView);

                }
            });
        }
        if (image3 != null) {
            storageRef.child(image3).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    DefaultSliderView textSliderView = new DefaultSliderView(myContext);
                    textSliderView
                            .description("Image")
                            .image(uri.toString())
                            .setScaleType(BaseSliderView.ScaleType.Fit);
                    // .setOnSliderClickListener(this);
                    mySlider.addSlider(textSliderView);

                }
            });
        }
        mySlider.setPresetTransformer(SliderLayout.Transformer.Fade);
        mySlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mySlider.setCustomAnimation(new DescriptionAnimation());
        mySlider.setDuration(2000);
    }

}

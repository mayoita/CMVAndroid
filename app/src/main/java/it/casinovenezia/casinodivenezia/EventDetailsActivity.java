package it.casinovenezia.casinodivenezia;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
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

    private int convertDpToPx(int dp, DisplayMetrics displayMetrics) {
        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
        return Math.round(pixels);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();

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
//        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
//                "Events");
//
//        query.getInBackground(myId,
//                new GetCallback<ParseObject>() {
//
//                    public void done(ParseObject object,
//                                     ParseException e) {
//
//                        ParseFile image1F = (ParseFile) object
//                                .get("ImageEvent1");
//                        ParseFile image2F = (ParseFile) object
//                                .get("ImageEvent2");
//                        ParseFile image3F = (ParseFile) object
//                                .get("ImageEvent3");
//                        ParseFile imageDefault = (ParseFile) object
//                                .get("ImageName");
//                        if (image1F != null){
//                            image1 = image1F.getUrl();
//                        } else {
//                            image1 = imageDefault.getUrl();
//                        }
//
//                        if (image2F != null){
//                            image2 = image2F.getUrl();
//                        }
//                        if (image3F != null){
//                            image3 = image3F.getUrl();
//                        }
//                       createSlider();
//
//
//                    }
//                });
    }

    public void createSlider (){
        HashMap<String, String> file_maps = new HashMap<String, String>();
        if (image1 != null) {
            file_maps.put("image1", image1);
        }
        if (image2 != null) {
            file_maps.put("image2", image2);
        }
        if (image3 != null) {
            file_maps.put("image3", image3);
        }

        for(String nameF : file_maps.keySet()){
            DefaultSliderView textSliderView = new DefaultSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(nameF)
                    .image(file_maps.get(nameF))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.getBundle()
                    .putString("extra",nameF);

            mySlider.addSlider(textSliderView);
        }
        mySlider.setPresetTransformer(SliderLayout.Transformer.Fade);
        mySlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mySlider.setCustomAnimation(new DescriptionAnimation());
        mySlider.setDuration(2000);
    }
}

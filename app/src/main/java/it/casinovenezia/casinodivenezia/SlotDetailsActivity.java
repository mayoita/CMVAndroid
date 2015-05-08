package it.casinovenezia.casinodivenezia;

import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import java.util.HashMap;

/**
 * Created by massimomoro on 05/05/15.
 */
public class SlotDetailsActivity extends ActionBarActivity {

    private SliderLayout mySlider;

    private int convertDpToPx(int dp, DisplayMetrics displayMetrics) {
        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
        return Math.round(pixels);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_slot_details);

        mySlider = (SliderLayout)findViewById(R.id.slider);
        TextView diciotto = (TextView) findViewById(R.id.diciottopiu);
        diciotto.setMovementMethod(LinkMovementMethod.getInstance());
        Typeface XLight = Typeface.createFromAsset(getAssets(), "fonts/GothamXLight.otf");

        Display display = getWindowManager().getDefaultDisplay();
        ImageView imageView = (ImageView) findViewById(R.id.imageViewSlotDetails);
        TextView theText = (TextView)findViewById(R.id.textViewSlotDetails);
        theText.setTypeface(XLight);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int width = display.getWidth();
        int height = (int) (width * 0.66); // 0.75 if image aspect ration is 4:3, change accordingly

        RelativeLayout.LayoutParams fp = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height + convertDpToPx(6,dm));
        RelativeLayout.LayoutParams sp = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height);

        fp.setMargins(convertDpToPx(7,dm) ,convertDpToPx(10,dm), convertDpToPx(7,dm), 0);
        sp.setMargins(convertDpToPx(10,dm) ,convertDpToPx(13,dm), convertDpToPx(10,dm), 0);
        imageView.setLayoutParams(fp);
        mySlider.setLayoutParams(sp);


        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Hannibal",R.drawable.hannibal);
        file_maps.put("Big Bang Theory",R.drawable.bigbang);
        file_maps.put("House of Cards",R.drawable.house);
        file_maps.put("Game of Thrones", R.drawable.game_of_thrones);

        for(String name : file_maps.keySet()){
            DefaultSliderView textSliderView = new DefaultSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
                    //.setOnSliderClickListener(this);

            //add your extra information
            textSliderView.getBundle()
                    .putString("extra",name);

            mySlider.addSlider(textSliderView);
        }
        mySlider.setPresetTransformer(SliderLayout.Transformer.Fade);
        mySlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mySlider.setCustomAnimation(new DescriptionAnimation());
        mySlider.setDuration(4000);

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

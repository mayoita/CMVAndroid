package it.casinovenezia.casinodivenezia;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.lucasr.twowayview.TwoWayView;

/**
 * Created by massimomoro on 04/05/15.
 */
public class CasinoGame_Item_Activity extends ActionBarActivity {
    private GameRuleAdapter mAdapter;

    private TextView myTexttitle;
    private DisplayMetrics dm;
    private int OverMarginForDeepRule = 80;
    private int OverMarginForDeepRuleTopBottom = 200;
    private int heightDisplay;
    JSONArray myData;
    Context context = CasinoGame_Item_Activity.this;
    private int[] arrayGames = {
            R.drawable.fair,
            R.drawable.blackj,
            R.drawable.texas,
            R.drawable.banco,
            R.drawable.carribean,
            R.drawable.french,
            R.drawable.chemin,
            R.drawable.trente
    };



    private int convertDpToPx(int dp, DisplayMetrics displayMetrics) {
        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
        return Math.round(pixels);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String jsonArray = intent.getStringExtra("jsonArray");

        try {
            myData = new JSONArray(jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {

        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        setContentView(R.layout.game_single);

        Typeface XLight = Typeface.createFromAsset(getAssets(), "fonts/GothamXLight.otf");
        TextView diciotto = (TextView) findViewById(R.id.diciottopiu);
        diciotto.setMovementMethod(LinkMovementMethod.getInstance());

        Display display = getWindowManager().getDefaultDisplay();
        ImageView imageViewWhite = (ImageView) findViewById(R.id.imageViewWhite);
        ImageView imageViewGame = (ImageView) findViewById(R.id.imageViewGame);
        TextView myText = (TextView)findViewById(R.id.textViewGame);
        myTexttitle = (TextView)findViewById(R.id.textViewTitleGame);

        myText.setTypeface(XLight);
        myTexttitle.setTypeface(XLight);
        imageViewGame.setImageResource(arrayGames[position]);
        try {
            myTexttitle.setText(myData.getString(0));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        dm = getResources().getDisplayMetrics();
        int width = display.getWidth();
        heightDisplay = display.getHeight();
        int height = (int) (width * 0.47);

        RelativeLayout.LayoutParams fp = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height + convertDpToPx(6,dm));
        RelativeLayout.LayoutParams sp = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height);

        fp.setMargins(convertDpToPx(7,dm) ,convertDpToPx(10,dm), convertDpToPx(7,dm), 0);
        sp.setMargins(convertDpToPx(10, dm), convertDpToPx(13, dm), convertDpToPx(10, dm), 0);
        imageViewWhite.setLayoutParams(fp);
        imageViewGame.setLayoutParams(sp);


        mAdapter = new GameRuleAdapter(context, width);
        mAdapter.addItem("Item 1");
        mAdapter.addItem("Item 2");
        mAdapter.addItem("Item 3");
        mAdapter.addItem("Item 4");
        mAdapter.addItem("Item 5");
        mAdapter.addItem("Item 6");


        TwoWayView lvTest = (TwoWayView) findViewById(R.id.lvItems);
        lvTest.setAdapter(mAdapter);



    }

    public void openPopWindow(View v) {
       int a = heightDisplay;
        LayoutInflater layoutInflater
                = (LayoutInflater)getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popupgamerule, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                myTexttitle.getWidth()  + convertDpToPx(OverMarginForDeepRule,dm),
                heightDisplay - convertDpToPx(OverMarginForDeepRuleTopBottom, dm));

        ListView theList = (ListView)popupView.findViewById(R.id.therule);

        //Adapter for GameRule
        GameDeepRuleAdapter theRuleAdapter= new GameDeepRuleAdapter(v.getContext());
        theRuleAdapter.addItem("Item 1");
        theRuleAdapter.addItem("Item 2");
        theRuleAdapter.addItem("Item 3");
        theRuleAdapter.addItem("Item 4");
        theRuleAdapter.addItem("Item 5");
        theRuleAdapter.addItem("Item 6");
        theRuleAdapter.addItem("Item 1");
        theRuleAdapter.addItem("Item 2");
        theRuleAdapter.addItem("Item 3");
        theRuleAdapter.addItem("Item 4");
        theRuleAdapter.addItem("Item 5");
        theRuleAdapter.addItem("Item 6");
        theRuleAdapter.addItem("Item 1");
        theRuleAdapter.addItem("Item 2");
        theRuleAdapter.addItem("Item 3");
        theRuleAdapter.addItem("Item 4");
        theRuleAdapter.addItem("Item 5");

        theList.setAdapter(theRuleAdapter);

        Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);
        btnDismiss.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();
            }});

        popupWindow.showAsDropDown(myTexttitle, -convertDpToPx(OverMarginForDeepRule/2,dm), 0);
    }

}

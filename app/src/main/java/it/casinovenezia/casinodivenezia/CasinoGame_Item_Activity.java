package it.casinovenezia.casinodivenezia;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
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
public class CasinoGame_Item_Activity extends ActionBarActivity  {
    private GameRuleAdapter mAdapter;

    private TextView myTexttitle;
    private DisplayMetrics dm;
    private int OverMarginForDeepRule = 80;
    private int OverMarginForDeepRuleTopBottom = 200;
    private int heightDisplay;
    TextView myText;
    JSONArray myData;
    Context context = CasinoGame_Item_Activity.this;
    private int[] arrayGames = {
            R.drawable.fairroulette_972,
            R.drawable.blackjack458,
            R.drawable.carribean_458,
            R.drawable.roulettefrancese_458,
            R.drawable.chemindefer_458,
            R.drawable.trentaquaranta_458,
            R.drawable.default458

    };

    private int currentVisibleItemCount;
    private int currentScrollState;
    private TwoWayView lvTest;




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


        myText = (TextView)findViewById(R.id.textViewGame);
        myTexttitle = (TextView)findViewById(R.id.textViewTitleGame);

        myText.setTypeface(XLight);
        myTexttitle.setTypeface(XLight);
        int myNum = 6;

        try {
            myNum = Integer.parseInt(myData.getJSONArray(2).getString(0));
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        } catch (JSONException e) {
            e.printStackTrace();
        }

            imageViewGame.setImageResource(arrayGames[myNum]);

        try {
            myTexttitle.setText(myData.getString(0));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            myText.setText(myData.getJSONArray(2).getJSONArray(3).getJSONArray(0).getString(1));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        dm = getResources().getDisplayMetrics();
        int width = display.getWidth();
        heightDisplay = display.getHeight();
        int height = (int) (width * 0.45);

        RelativeLayout.LayoutParams fp = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height + convertDpToPx(6,dm));
        RelativeLayout.LayoutParams sp = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height);

        fp.setMargins(convertDpToPx(7,dm) ,convertDpToPx(10,dm), convertDpToPx(7,dm), 0);
        sp.setMargins(convertDpToPx(10, dm), convertDpToPx(13, dm), convertDpToPx(10, dm), 0);
        imageViewWhite.setLayoutParams(fp);
        imageViewGame.setLayoutParams(sp);


        mAdapter = new GameRuleAdapter(context, width);
        JSONArray values = new JSONArray();
        try {
             values = myData.getJSONArray(2).getJSONArray(3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for(int i = 0 ; i < values.length(); i++) {
            try {
                mAdapter.addItem(values.getJSONArray(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



        lvTest = (TwoWayView) findViewById(R.id.lvItems);
        lvTest.setAdapter(mAdapter);
        lvTest.setOnScrollListener(new TwoWayView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(TwoWayView view, int scrollState) {
                String stateName = "Undefined";
                currentScrollState = scrollState;
                isScrollCompleted();
                switch (scrollState) {
                    case SCROLL_STATE_IDLE:
                        stateName = "Idle";

                        break;

                    case SCROLL_STATE_TOUCH_SCROLL:
                        stateName = "Dragging";
                        break;

                    case SCROLL_STATE_FLING:
                        stateName = "Flinging";
                        break;
                }



            }

            @Override
            public void onScroll(TwoWayView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {



                currentVisibleItemCount = visibleItemCount;
            }
            private void isScrollCompleted() {

                if (currentVisibleItemCount > 0 && currentScrollState == 0) {
                    try {
                        myText.setText(myData.getJSONArray(2).getJSONArray(3).getJSONArray(lvTest.getFirstVisiblePosition() ).getString(1));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    /*** In this way I detect if there's been a scroll which has completed ***/
                    /*** do the work! ***/
                }
            }

        });


    }



    public void openPopWindow(View v) {
       int a = heightDisplay;
        LayoutInflater layoutInflater
                = (LayoutInflater)getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popupgamerule, null);
        TextView theTitle = (TextView) popupView.findViewById(R.id.popUpTitle);
        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                WindowManager.LayoutParams.MATCH_PARENT,
                heightDisplay - convertDpToPx(OverMarginForDeepRuleTopBottom, dm));

        ListView theList = (ListView)popupView.findViewById(R.id.therule);

        //Adapter for GameRule
        GameDeepRuleAdapter theRuleAdapter= new GameDeepRuleAdapter(v.getContext());

        JSONArray values = new JSONArray();
        try {
            theTitle.setText(myData.getJSONArray(2).getJSONArray(3).getJSONArray(lvTest.getFirstVisiblePosition()).getString(2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            values = myData.getJSONArray(2).getJSONArray(3).getJSONArray(lvTest.getFirstVisiblePosition());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (values.length() > 3 ) {
            for (int i = 3; i < values.length(); i++) {
                try {
                    theRuleAdapter.addItem(values.getJSONArray(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        theList.setAdapter(theRuleAdapter);

        Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);
        btnDismiss.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();
            }});

        popupWindow.showAsDropDown(myTexttitle, -convertDpToPx(OverMarginForDeepRule / 2, dm), 0);
    }




}

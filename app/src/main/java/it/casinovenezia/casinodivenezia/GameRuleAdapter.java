package it.casinovenezia.casinodivenezia;

import android.content.Context;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;

/**
 * Created by massimomoro on 04/05/15.
 */
public class GameRuleAdapter extends BaseAdapter {
    private final Context context;
    private int theWidth;
    private LayoutInflater mInflater;
    private Typeface myTypeFace;
    private ArrayList<String> mData = new ArrayList<String>();
    private final int sumOfMarginLeftAndRight = 60;
    private DisplayMetrics dm;

    class ViewHolder {
        public TextView text;
        public TextView text2;
        ImageButton openPopWindow;
    }
    private int convertDpToPx(int dp, DisplayMetrics displayMetrics) {
        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
        return Math.round(pixels);
    }

    public GameRuleAdapter(Context context, int theWidth) {

        this.context = context;
        this.theWidth = theWidth;
        dm = context.getResources().getDisplayMetrics();
        myTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/GothamXLight.otf");
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public void addItem(String item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder mViewHolder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.game_rule, parent, false);
            //configure view holder
            mViewHolder = new ViewHolder();
            mViewHolder.text = (TextView) convertView.findViewById(R.id.textGameRule);
            mViewHolder.text.setTypeface(myTypeFace);
            mViewHolder.text2 = (TextView) convertView.findViewById(R.id.textGameRuleDeep);
            mViewHolder.text2.setTypeface(myTypeFace);
            mViewHolder.openPopWindow = (ImageButton)convertView.findViewById(R.id.buttonPopWindow);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder)convertView.getTag();
        }
        mViewHolder.text.setText("COMBINAZIONI E PAGAMENTI");
        mViewHolder.text2.setText("Chances multiple");
        final TwoWayView.LayoutParams params = (TwoWayView.LayoutParams) convertView.getLayoutParams();

        if (params != null) {
            params.width = theWidth - convertDpToPx(sumOfMarginLeftAndRight,dm);
        }

        return convertView;
    }
}

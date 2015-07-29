package it.casinovenezia.casinodivenezia;

import android.content.Context;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by massimomoro on 05/05/15.
 */
public class GameDeepRuleAdapter extends BaseAdapter {
    private final Context context;

    private LayoutInflater mInflater;
    private Typeface myTypeFace;
    private JSONArray mData = new JSONArray();

    private DisplayMetrics dm;


    private int convertDpToPx(int dp, DisplayMetrics displayMetrics) {
        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
        return Math.round(pixels);
    }

    class ViewHolder {
        public TextView text;
        public TextView text2;

    }

    public GameDeepRuleAdapter(Context context) {

        this.context = context;

        myTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/GothamXLight.otf");
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(JSONArray item) {
        mData.put(item);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.length();
    }

    @Override
    public Object getItem(int position) {
        try {
            return mData.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder mViewHolder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.deep_rule_list_item, parent, false);
            //configure view holder
            mViewHolder = new ViewHolder();
            mViewHolder.text = (TextView) convertView.findViewById(R.id.deeprule1);
            mViewHolder.text.setTypeface(myTypeFace);
            mViewHolder.text2 = (TextView) convertView.findViewById(R.id.deeprule2);
            mViewHolder.text2.setTypeface(myTypeFace);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder)convertView.getTag();
        }
        try {
            mViewHolder.text.setText(mData.getJSONArray(position).getString(0));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            mViewHolder.text2.setText(mData.getJSONArray(position).getString(1));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}

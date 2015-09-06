package it.casinovenezia.casinodivenezia;

import android.content.Context;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by massimomoro on 17/08/15.
 */
public class TimetablesCellAdapter extends BaseAdapter {
    private final Context context;
    private int theWidth;
    private LayoutInflater mInflater;
    private Typeface myTypeFace;
    public List<ParseObject> mData = new ArrayList();

    private final int sumOfMarginLeftAndRight = 60;
    private DisplayMetrics dm;

    class ViewHolder {
        public TextView oraS;
        public TextView oraD;

    }


    public TimetablesCellAdapter(Context context, int theWidth, List<ParseObject> theData) {

        this.context = context;
        this.theWidth = theWidth;
        mData = theData;

        myTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/GothamXLight.otf");
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        ParseObject itemarray =  mData.get(position);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.fragment_timetable_cell, parent, false);
            //configure view holder
            mViewHolder = new ViewHolder();
            mViewHolder.oraS = (TextView) convertView.findViewById(R.id.textOraS);
            mViewHolder.oraD = (TextView) convertView.findViewById(R.id.textOraD);



            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder)convertView.getTag();
        }
        mViewHolder.oraS.setText(itemarray.getString("LeftColumn"));
        mViewHolder.oraD.setText((String)itemarray.getString("RightColumn"));


//        final TwoWayView.LayoutParams params = (TwoWayView.LayoutParams) convertView.getLayoutParams();
//
//        if (params != null) {
//            params.width = theWidth - convertDpToPx(sumOfMarginLeftAndRight,dm);
//        }

        return convertView;
    }
}
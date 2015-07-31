package it.casinovenezia;

import android.content.Context;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.lucasr.twowayview.TwoWayView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import it.casinovenezia.casinodivenezia.R;

/**
 * Created by massimomoro on 13/05/15.
 */
public class TournamentDayAdapter  extends BaseAdapter {
    private final Context context;
    private int theWidth;
    private LayoutInflater mInflater;
    private Typeface myTypeFace;
    private ArrayList<String> mData = new ArrayList<String>();
    private final int sumOfMarginLeftAndRight = 60;
    private DisplayMetrics dm;
    DateFormat formatter;
    SimpleDateFormat sdf;
    public  ArrayList parentIndex = new ArrayList();

    class ViewHolder {
        public TextView text;

    }
    private int convertDpToPx(int dp, DisplayMetrics displayMetrics) {
        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
        return Math.round(pixels);
    }

    public TournamentDayAdapter(Context context, int theWidth) {

        this.context = context;
        this.theWidth = theWidth;
        dm = context.getResources().getDisplayMetrics();
        myTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/GothamXLight.otf");
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        formatter = new SimpleDateFormat("dd/MM/yy");
        sdf = new SimpleDateFormat("EEEE dd LLLL", context.getResources().getConfiguration().locale);
    }
    public void addItem(String item,int index) {
        mData.add(item);
        parentIndex.add(index);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }
    public int getIndex(int position) {return (int)parentIndex.get(position);}

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder mViewHolder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.tournament_day, parent, false);
            //configure view holder
            mViewHolder = new ViewHolder();
            mViewHolder.text = (TextView) convertView.findViewById(R.id.tournament_day);
            mViewHolder.text.setTypeface(myTypeFace);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder)convertView.getTag();
        }
        mViewHolder.text.setText("MERCOLEDì 1 maggio");

        final TwoWayView.LayoutParams params = (TwoWayView.LayoutParams) convertView.getLayoutParams();

        if (params != null) {
            params.width = theWidth - convertDpToPx(sumOfMarginLeftAndRight,dm);
        }

        return convertView;
    }
}

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

import java.util.ArrayList;

/**
 * Created by massimomoro on 13/05/15.
 */
public class TournamentCellAdapter extends BaseAdapter {
    private final Context context;
    private int theWidth;
    private LayoutInflater mInflater;
    private Typeface myTypeFace;
    private ArrayList<String> mData = new ArrayList<String>();
    private final int sumOfMarginLeftAndRight = 60;
    private DisplayMetrics dm;

    class ViewHolder {
        public TextView nomeTorneo;
        public TextView oraTorneo;
        public TextView nomeTorneoBis;
        public TextView buy;

        public TextView note;

    }
    private int convertDpToPx(int dp, DisplayMetrics displayMetrics) {
        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
        return Math.round(pixels);
    }

    public TournamentCellAdapter(Context context, int theWidth) {

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
            convertView = mInflater.inflate(R.layout.tournament_cell, parent, false);
            //configure view holder
            mViewHolder = new ViewHolder();
            mViewHolder.nomeTorneo = (TextView) convertView.findViewById(R.id.textNameTorneoTorneo);
            mViewHolder.oraTorneo = (TextView) convertView.findViewById(R.id.textOraTorneo);
            mViewHolder.nomeTorneoBis = (TextView) convertView.findViewById(R.id.textOraTorneoV);
            mViewHolder.buy = (TextView) convertView.findViewById(R.id.textBuyTorneoV);
            mViewHolder.note = (TextView) convertView.findViewById(R.id.textNoteTorneoV);


            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder)convertView.getTag();
        }
        mViewHolder.nomeTorneo.setText("POKER HOUR ON LINE");
        mViewHolder.oraTorneo.setText("18:45");
        mViewHolder.nomeTorneoBis.setText("FREE BONUS SUPER APRIL CLICKANDPLAY.IT");
        mViewHolder.buy.setText("freeroll");
        mViewHolder.note.setText("1 bonus freeroll da 2 euro per POKER GAMES in CLICKANDPLAY.IT");

//        final TwoWayView.LayoutParams params = (TwoWayView.LayoutParams) convertView.getLayoutParams();
//
//        if (params != null) {
//            params.width = theWidth - convertDpToPx(sumOfMarginLeftAndRight,dm);
//        }

        return convertView;
    }
}

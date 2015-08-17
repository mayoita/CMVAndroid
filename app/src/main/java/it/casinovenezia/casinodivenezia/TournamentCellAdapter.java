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
    public ArrayList mData = new ArrayList();
    private final int sumOfMarginLeftAndRight = 60;
    private DisplayMetrics dm;

    class ViewHolder {
        public TextView nomeTorneo;
        public TextView oraTorneo;
        public TextView nomeTorneoBis;
        public TextView buy;

        public TextView note;

    }


    public TournamentCellAdapter(Context context, int theWidth) {

        this.context = context;
        this.theWidth = theWidth;
        dm = context.getResources().getDisplayMetrics();
        myTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/GothamXLight.otf");
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public void addItem(ArrayList item) {
        mData.add(item);

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
        ArrayList itemarray = (ArrayList) mData.get(position);
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
        mViewHolder.oraTorneo.setText((String)itemarray.get(1));
        mViewHolder.nomeTorneoBis.setText((String)itemarray.get(2));
        mViewHolder.buy.setText((String)itemarray.get(3));
        mViewHolder.note.setText((String)itemarray.get(4));

//        final TwoWayView.LayoutParams params = (TwoWayView.LayoutParams) convertView.getLayoutParams();
//
//        if (params != null) {
//            params.width = theWidth - convertDpToPx(sumOfMarginLeftAndRight,dm);
//        }

        return convertView;
    }
}

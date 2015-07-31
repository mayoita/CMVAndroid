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
public class PokerCellAdapter extends BaseAdapter {
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
        public TextView stack;
        public TextView blinds;
        public TextView late;
        public TextView cap;
        public TextView note;

    }


    public PokerCellAdapter(Context context, int theWidth) {

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
            convertView = mInflater.inflate(R.layout.poker_cell, parent, false);
            //configure view holder
            mViewHolder = new ViewHolder();
            mViewHolder.nomeTorneo = (TextView) convertView.findViewById(R.id.textNameTorneo);
            mViewHolder.oraTorneo = (TextView) convertView.findViewById(R.id.textOra);
            mViewHolder.nomeTorneoBis = (TextView) convertView.findViewById(R.id.textOraV);
            mViewHolder.buy = (TextView) convertView.findViewById(R.id.textBuyV);
            mViewHolder.stack = (TextView) convertView.findViewById(R.id.textStackV);
            mViewHolder.blinds = (TextView) convertView.findViewById(R.id.textBlindV);
            mViewHolder.late = (TextView) convertView.findViewById(R.id.textLateV);
            mViewHolder.cap = (TextView) convertView.findViewById(R.id.textCapV);
            mViewHolder.note = (TextView) convertView.findViewById(R.id.textNoteV);


            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder)convertView.getTag();
        }
        mViewHolder.nomeTorneo.setText((String)itemarray.get(0));
        mViewHolder.oraTorneo.setText((String)itemarray.get(2));
        mViewHolder.nomeTorneoBis.setText((String)itemarray.get(3));
        mViewHolder.buy.setText((String)itemarray.get(4));
        mViewHolder.stack.setText((String)itemarray.get(5).toString());
        mViewHolder.blinds.setText((String)itemarray.get(6).toString());
        mViewHolder.late.setText((String)itemarray.get(7).toString());
        mViewHolder.cap.setText((String)itemarray.get(8).toString());
        mViewHolder.note.setText((String)itemarray.get(9));

//        final TwoWayView.LayoutParams params = (TwoWayView.LayoutParams) convertView.getLayoutParams();
//
//        if (params != null) {
//            params.width = theWidth - convertDpToPx(sumOfMarginLeftAndRight,dm);
//        }

        return convertView;
    }
}

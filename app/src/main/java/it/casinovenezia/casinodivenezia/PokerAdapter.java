package it.casinovenezia.casinodivenezia;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by massimomoro on 08/05/15.
 */
public class PokerAdapter extends BaseAdapter {
    private final Context context;
    private LayoutInflater mInflater;

    private static final int TYPE_ITEM = 1;
    private static final int TYPE_SEPARATOR = 0;
    private String formattedCurrentDate;
    private List<PokerItem> eventitemList = null;
    private ArrayList<PokerItem> arraylist;
    Typeface Aachen;
    enum RowType {
        LIST_ITEM, HEADER_ITEM
    }

    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();


    class ViewHolder {

        private TextView text;
        private TextView date;
    }
    class ViewHolderHeader {

        private TextView text;

    }

    public PokerAdapter(Context context,List<PokerItem> eventitemlist) {
        this.context = context;

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.eventitemList = eventitemlist;
        this.arraylist = new ArrayList<PokerItem>();
        this.arraylist.addAll(eventitemlist);
        Aachen = Typeface.createFromAsset(context.getAssets(), "fonts/Aachen_Bold_Plain.ttf");
    }

    public void addItem(PokerItem item) {
        eventitemList.add(item);
        notifyDataSetChanged();
    }
    public void addSectionHeaderItem(final PokerItem item) {
        eventitemList.add(item);
        sectionHeader.add(eventitemList.size() - 1);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return eventitemList.size();
    }

    @Override
    public int getViewTypeCount() {
        return RowType.values().length;
    }


    @Override
    public Object getItem(int position) {

        return eventitemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public int getItemViewType(int position) {

        //int b= sectionHeader.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
        return position % 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        int rowType = getItemViewType(position);

        if (rowView == null) {
            switch (rowType) {
                case TYPE_ITEM:
                    rowView = mInflater.inflate(R.layout.poker_items, parent, false);
                    //configure view holder
                    ViewHolder viewHolder = new ViewHolder();
                    //viewHolder.text = (TextView) rowView.findViewById(R.id.textView1);
                    viewHolder.text = (TextView) rowView.findViewById(R.id.editText4);
                    viewHolder.text.setTypeface(Aachen);
                    viewHolder.date = (TextView) rowView.findViewById(R.id.editText5);
                    viewHolder.date.setTypeface(Aachen);
                    rowView.setTag(viewHolder);
                    break;
                case TYPE_SEPARATOR:
                    rowView = mInflater.inflate(R.layout.events_header, parent, false);
                    ViewHolderHeader viewHolderHeader = new ViewHolderHeader();
                    //convertView = mInflater.inflate(R.layout.events_header, null);
                    viewHolderHeader.text = (TextView) rowView.findViewById(R.id.textSeparator);

                    // viewHolderHeader.text.setText(formattedCurrentDate);
                    rowView.setTag(viewHolderHeader);
                    break;
            }


        }
        switch (rowType) {
            case TYPE_ITEM:
                ViewHolder holder = (ViewHolder)rowView.getTag();
                //holder.textView.setText(mData.get(position));
                //download image
                // holder.image
                break;
            case TYPE_SEPARATOR:
                ViewHolderHeader holderHeader = (ViewHolderHeader)rowView.getTag();

                holderHeader.text.setText(arraylist.get(position).getStartDate());
                break;
        }

        return rowView;
    }
}

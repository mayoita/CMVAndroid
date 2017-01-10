package it.casinovenezia.casinodivenezia;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
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
    private List<PokerItem> eventitemList = null;
    private ArrayList<PokerItem> arraylist;

    Typeface Aachen;
    enum RowType {
        LIST_ITEM, HEADER_ITEM
    }

    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();


    class ViewHolder {
        private ImageView image;
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

        //notifyDataSetChanged();
    }
    public void addSectionHeaderItem(final PokerItem item) {
        eventitemList.add(item);
        sectionHeader.add(eventitemList.size() - 1);
        //notifyDataSetChanged();
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
                    viewHolder.image = (ImageView) rowView.findViewById(R.id.image_event);
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

                holder.text.setText(arraylist.get(position).getTournamentsName());
                holder.date.setText(formatMyDate(arraylist.get(position).getStartDate()) + " - " + formatMyDate(arraylist.get(position).getEndDate()));
                holder.image.setImageResource(R.drawable.poker_cell_background);
                holder.text.setVisibility(View.VISIBLE);
                holder.date.setVisibility(View.VISIBLE);
                break;
            case TYPE_SEPARATOR:
                ViewHolderHeader holderHeader = (ViewHolderHeader)rowView.getTag();

                holderHeader.text.setText(formatMyDate(arraylist.get(position).getStartDate()));
                break;
        }

        return rowView;
    }
    private String formatMyDate(String myDate)  {

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        try {
            date = format.parse(myDate);
            System.out.println(date);

        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd LLLL", StarterApplication.currentLocale);

        return sdf.format(date);
    }
}

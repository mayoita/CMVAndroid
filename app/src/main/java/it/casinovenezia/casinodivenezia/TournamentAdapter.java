package it.casinovenezia.casinodivenezia;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseImageView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by massimomoro on 13/05/15.
 */
public class TournamentAdapter extends BaseAdapter {
    private final Context context;
    private LayoutInflater mInflater;

    private static final int TYPE_ITEM = 1;
    private static final int TYPE_SEPARATOR = 0;
    private List<TournamentItem> eventitemList = null;
    private ArrayList<TournamentItem> arraylist;

    Typeface Aachen;
    enum RowType {
        LIST_ITEM, HEADER_ITEM
    }


    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();


    class ViewHolder {

        public ParseImageView image;
        private TextView text;
        private TextView date;
    }
    class ViewHolderHeader {

        private TextView text;
    }

    public TournamentAdapter(Context context,List<TournamentItem> eventitemlist) {
        this.context = context;

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.eventitemList = eventitemlist;
        this.arraylist = new ArrayList<TournamentItem>();
        this.arraylist.addAll(eventitemlist);
        Aachen = Typeface.createFromAsset(context.getAssets(), "fonts/Aachen_Bold_Plain.ttf");

    }

    public void addItem(TournamentItem item) {
        eventitemList.add(item);

    }
    public void addSectionHeaderItem(final TournamentItem item) {
        eventitemList.add(item);
        sectionHeader.add(eventitemList.size() - 1);

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
                    rowView = mInflater.inflate(R.layout.event_items, parent, false);
                    //configure view holder
                    ViewHolder viewHolder = new ViewHolder();
                    //viewHolder.text = (TextView) rowView.findViewById(R.id.textView1);
                    viewHolder.image = (ParseImageView) rowView.findViewById(R.id.image_event);
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
                if (arraylist.get(position).getType().equals("C")) {
                    holder.image.setParseFile(arraylist.get(position).getImageTournament());
                    holder.image.loadInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] data, com.parse.ParseException e) {

                        }
                    });
                    holder.text.setVisibility(View.GONE);
                    holder.date.setVisibility(View.GONE);
                } else {
                    holder.image.setImageResource(R.drawable.tournament_cell_background);

                    holder.text.setVisibility(View.VISIBLE);
                    holder.date.setVisibility(View.VISIBLE);
                }
                holder.text.setText(arraylist.get(position).getTournamentsName());
                holder.date.setText(arraylist.get(position).getStartDate() + " - " + arraylist.get(position).getEndDate());
                break;
            case TYPE_SEPARATOR:
                ViewHolderHeader holderHeader = (ViewHolderHeader)rowView.getTag();

                holderHeader.text.setText(arraylist.get(position).getStartDate());
                break;
        }

        return rowView;
    }

}
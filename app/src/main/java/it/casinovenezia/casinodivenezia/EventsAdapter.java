package it.casinovenezia.casinodivenezia;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by massimomoro on 14/04/15.
 */
public class EventsAdapter extends BaseAdapter {
    private final Context context;
    private LayoutInflater mInflater;
    private ProgressDialog progressDialog;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_SEPARATOR = 0;
    private String formattedCurrentDate;
    private List<EventItem> eventitemList = null;
    private ArrayList<EventItem> arraylist;

    enum RowType {
        LIST_ITEM, HEADER_ITEM
    }


    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();


    class ViewHolder {

        public ImageView image;
        public TextView text;
        public TextView date;
    }
    class ViewHolderHeader {

        private TextView text;
    }

    public EventsAdapter(Context context, List<EventItem> eventitemlist) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.eventitemList = eventitemlist;
        this.arraylist = new ArrayList<EventItem>();
        this.arraylist.addAll(eventitemlist);

    }

    public void addItem(EventItem item) {
        eventitemList.add(item);
        notifyDataSetChanged();
    }
    public void addSectionHeaderItem(final EventItem item) {
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
        return position;
    }
    @Override
    public int getItemViewType(int position) {

        //int b= sectionHeader.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
        return position % 2; //RowType.values()[position % 2].ordinal();
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
                    viewHolder.text = (TextView) rowView.findViewById(R.id.editText5);
                    viewHolder.date = (TextView) rowView.findViewById(R.id.editText4);
                    viewHolder.image = (ImageView) rowView.findViewById(R.id.image_event);
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
                //download image
                loadImage(arraylist.get(position).getImageMain(), holder.image);
                holder.text.setVisibility(View.GONE);
                holder.date.setVisibility(View.GONE);
                break;
            case TYPE_SEPARATOR:
                ViewHolderHeader holderHeader = (ViewHolderHeader)rowView.getTag();

                holderHeader.text.setText(arraylist.get(position).getStartDate());
                break;
        }

        return rowView;
    }
    public void loadImage(ParseFile file, final ImageView myImage) {
        file
                .getDataInBackground(new GetDataCallback() {

                    public void done(byte[] data,
                                     ParseException e) {
                        if (e == null) {
                            Log.d("test",
                                    "We've got data in data.");
                            // Decode the Byte[] into
                            // Bitmap
                            Bitmap bmp = BitmapFactory
                                    .decodeByteArray(
                                            data, 0,
                                            data.length);

                            myImage.setImageBitmap(bmp);


                        } else {
                            Log.d("test",
                                    "There was a problem downloading the data.");
                        }
                    }
                });
    }
}

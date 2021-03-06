package it.casinovenezia.casinodivenezia;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeSet;

/**
 * Created by massimomoro on 25/05/15.
 */
public class MenuAdapter  extends BaseAdapter {
    private final Context context;
    private LayoutInflater mInflater;
    private Typeface Venice;
    private Typeface VeniceB;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;


    enum RowType {
        LIST_ITEM, HEADER_ITEM
    }


    ArrayList<ArrayList<String>> outer = new ArrayList<ArrayList<String>>();


    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();


    class ViewHolder {

        public TextView text;
        public TextView textE;
    }
    class ViewHolderHeader {

        private TextView text;
    }

    public MenuAdapter(Context context) {
        this.context = context;
        Venice = Typeface.createFromAsset(context.getAssets(), "fonts/VeniceCasinoItalic.otf");
        VeniceB = Typeface.createFromAsset(context.getAssets(), "fonts/VeniceCasinoBoldItalic.otf");
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(String item, String itemEuro) {
        ArrayList<String> inner = new ArrayList<String>();
        inner.add(item);
        inner.add(itemEuro);
        outer.add(inner);

    }
    public void addSectionHeaderItem(String item) {
        ArrayList<String> inner = new ArrayList<String>();
        inner.add(item);
        inner.add("");
        outer.add(inner);
        sectionHeader.add(outer.size() - 1);

    }

    @Override
    public int getCount() {

        return outer.size();
    }

    @Override
    public int getViewTypeCount() {

        return RowType.values().length;
    }


    @Override
    public Object getItem(int position) {

        return outer.get(position);
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }
    @Override
    public int getItemViewType(int position) {

        return sectionHeader.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        int rowType = getItemViewType(position);

        if (rowView == null) {
            switch (rowType) {
                case TYPE_ITEM:
                    rowView = mInflater.inflate(R.layout.menu_items, parent, false);
                    //configure view holder
                    ViewHolder viewHolder = new ViewHolder();
                    viewHolder.text = (TextView) rowView.findViewById(R.id.textView27);
                    viewHolder.text.setTypeface(Venice);
                    viewHolder.textE = (TextView) rowView.findViewById(R.id.textView28);
                    viewHolder.textE.setTypeface(Venice);

                    rowView.setTag(viewHolder);
                    break;
                case TYPE_SEPARATOR:
                    rowView = mInflater.inflate(R.layout.menu_header, parent, false);
                    ViewHolderHeader viewHolderHeader = new ViewHolderHeader();
                    //convertView = mInflater.inflate(R.layout.events_header, null);
                    viewHolderHeader.text = (TextView) rowView.findViewById(R.id.textSeparator);
                    viewHolderHeader.text.setTypeface(VeniceB);
                    // viewHolderHeader.text.setText(formattedCurrentDate);
                    rowView.setTag(viewHolderHeader);
                    break;
            }


        }
        switch (rowType) {
            case TYPE_ITEM:
                ViewHolder holder = (ViewHolder)rowView.getTag();
                holder.text.setText( outer.get(position).get(0));
                holder.textE.setText( outer.get(position).get(1));
                //download image
                // holder.image
                break;
            case TYPE_SEPARATOR:
                ViewHolderHeader holderHeader = (ViewHolderHeader)rowView.getTag();
                String a = outer.get(position).get(0);
                holderHeader.text.setText( outer.get(position).get(0));

                break;
        }

        return rowView;
    }

}

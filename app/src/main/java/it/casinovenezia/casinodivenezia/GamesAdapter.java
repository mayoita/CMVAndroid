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

/**
 * Created by massimomoro on 29/04/15.
 */
public class GamesAdapter extends BaseAdapter {

    private final Context context;
    private ArrayList<String> mData = new ArrayList<String>();
    private LayoutInflater mInflater;
    private Typeface myType;
    private  int rowHeight;
    //Array to manage data
    public int[] textureArray ;
    public int[] titleArray ;

    class ViewHolder {

        public ImageView image;
        public TextView textTitle;
    }
    public GamesAdapter(Context context, int myHeight, int[] textureArray, int[] titleArray) {
        this.context = context;
        this.rowHeight = myHeight;
        this.textureArray = textureArray;
        this.titleArray = titleArray;
        myType = Typeface.createFromAsset(context.getAssets(), "fonts/GothamXLight.otf");
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return textureArray.length;
    }

    @Override
    public Object getItem(int position) {
        return textureArray[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        ViewHolder viewHolder = new ViewHolder();

        if (rowView == null) {
            rowView = mInflater.inflate(R.layout.game_items, parent, false);
            viewHolder.image = (ImageView) rowView.findViewById(R.id.image_game);
            viewHolder.textTitle = (TextView)rowView.findViewById(R.id.textView11);
            viewHolder.textTitle.setTypeface(myType);
            rowView.setTag(viewHolder);

        }
            viewHolder = (ViewHolder)rowView.getTag();
            viewHolder.image.setImageResource(textureArray[position]);
            viewHolder.textTitle.setText(titleArray[position]);
//            final AbsListView.LayoutParams params = (AbsListView.LayoutParams) rowView.getLayoutParams();
//
//            if (params != null) {
//                params.height = rowHeight;
//            }
        return rowView;
    }
}

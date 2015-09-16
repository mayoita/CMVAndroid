package it.casinovenezia.casinodivenezia;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;

import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

import java.util.TreeSet;


/**
 * Created by massimomoro on 14/04/15.
 */
public class EventsAdapter extends BaseAdapter  {
    private final Context context;
    private LayoutInflater mInflater;
    private ProgressDialog progressDialog;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_SEPARATOR = 0;
    private String formattedCurrentDate;
    private List<EventItem> eventitemList = null;
    private ArrayList<EventItem> arraylist;

    private Spring mSpring1;
    private SpringRunnable runnable1;
    private static final SpringConfig SPRING_CONFIG = SpringConfig.fromOrigamiTensionAndFriction(10, 3);
    private static final int MAX = 300;
    private static final int MIN = 500;


    private ViewHolder isSpeaking;

    private void speakOut(String text, ViewHolder view) {


        String utteranceId=this.hashCode() + "";
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, utteranceId);
        if (isSpeaking != null){
            if (view != isSpeaking) {
                if (EventsFr.engine.isSpeaking()) {
                    EventsFr.engine.stop();
                    isSpeaking.speak.setImageResource(R.drawable.speak);
                    EventsFr.engine.speak(text, TextToSpeech.QUEUE_FLUSH, map);
                    isSpeaking = view;
                    view.speak.setImageResource(R.drawable.speak_f);
                } else{
                    EventsFr.engine.speak(text, TextToSpeech.QUEUE_FLUSH, map);
                    view.speak.setImageResource(R.drawable.speak_f);
                    isSpeaking = view;
                }
            }else{
                if (EventsFr.engine.isSpeaking()) {
                    EventsFr.engine.stop();
                    view.speak.setImageResource(R.drawable.speak);
                } else{
                    EventsFr.engine.speak(text, TextToSpeech.QUEUE_FLUSH, map);;
                    view.speak.setImageResource(R.drawable.speak_f);
                    isSpeaking = view;
                }
            }
        } else {
            if (EventsFr.engine.isSpeaking()) {
                EventsFr.engine.stop();
                view.speak.setImageResource(R.drawable.speak);
                isSpeaking= view;
            } else{
                EventsFr.engine.speak(text, TextToSpeech.QUEUE_FLUSH, map);
                view.speak.setImageResource(R.drawable.speak_f);
                isSpeaking = view;
            }
        }

    }



    enum RowType {
        LIST_ITEM, HEADER_ITEM
    }


    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();


    class ViewHolder {

        public ImageView image;
        public TextView text;
        public TextView date;
        public ImageView speak;

    }
    class ViewHolderHeader {

        private TextView text;
    }

    public EventsAdapter(Context context, List<EventItem> eventitemlist) {
        if (context != null) {

            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.context = context;
            this.eventitemList = eventitemlist;
            this.arraylist = new ArrayList<EventItem>();
            this.arraylist.addAll(eventitemlist);
        } else {
            this.context = null;
            Log.e("Event", "Context is null");
        }

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
    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        int rowType = getItemViewType(position);

        if (rowView == null) {
            switch (rowType) {
                case TYPE_ITEM:
                    rowView = mInflater.inflate(R.layout.event_items, parent, false);
                    //configure view holder
                    final ViewHolder viewHolder = new ViewHolder();
                    viewHolder.text = (TextView) rowView.findViewById(R.id.editText5);
                    viewHolder.date = (TextView) rowView.findViewById(R.id.editText4);
                    viewHolder.image = (ImageView) rowView.findViewById(R.id.image_event);
                    viewHolder.speak = (ImageView) rowView.findViewById(R.id.speak);
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
                final ViewHolder holder = (ViewHolder)rowView.getTag();

                final String textToSpeach = arraylist.get(position).getMemo();
                if (textToSpeach != null) {
                    holder.speak.setVisibility(View.VISIBLE);
                }
                holder.speak.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {

                        if (textToSpeach != null)
                        speakOut(textToSpeach,holder);

                    }
                });
                holder.image.setImageResource(R.drawable.default_event);
               // if(holder.)

                holder.speak.setImageResource(R.drawable.speak);
                //download image
                loadImage(arraylist.get(position).getImageMain(), holder.image);
                holder.text.setVisibility(View.GONE);
                holder.date.setVisibility(View.GONE);
                SpringSystem mSpringSystem = SpringSystem.create();
                mSpring1 = mSpringSystem.createSpring();
                mSpring1.setSpringConfig(SPRING_CONFIG);
                mSpring1.addListener(new ViewSpringListener(holder.speak));
                runnable1 = new SpringRunnable(mSpring1, holder.speak);
                Handler handler = new Handler();
                handler.postDelayed(runnable1, MIN + (int) (Math.random() * ((MAX - MIN) + 1)));
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
    public class SpringRunnable implements Runnable{
        Spring _spring;
        View _view;
        SpringRunnable(Spring spring, View view){
            this._spring = spring;
            this._view = view;
        }
        @Override
        public void run() {
            _spring.setEndValue(1);

        }
    }
    public class ViewSpringListener implements SpringListener {

        View _view;

        ViewSpringListener(View view){
            this._view = view;
        }

        @Override
        public void onSpringUpdate(Spring spring) {
            render(_view,spring);
        }

        @Override
        public void onSpringAtRest(Spring spring) {
        }

        @Override
        public void onSpringActivate(Spring spring) {
        }

        @Override
        public void onSpringEndStateChange(Spring spring) {
        }
    }
    private void render(View v, Spring spring) {

        float value = (float)spring.getCurrentValue();
        float scale = 1f - (value * 0.2f);
        v.setScaleX(scale);
        v.setScaleY(scale);
    }

}

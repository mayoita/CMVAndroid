package it.casinovenezia.casinodivenezia;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import static it.casinovenezia.casinodivenezia.HomeActivity.eventitemlist;

/**
 * Created by mayoita on 17/11/2016.
 */

public class EventFBAdapter extends ArrayAdapter<EventItem> {

    private List<EventItem> eventitemList = null;
    private final Context context;

    private Spring mSpring1;
    private SpringRunnable runnable1;
    private static final SpringConfig SPRING_CONFIG = SpringConfig.fromOrigamiTensionAndFriction(10, 3);
    private static final int MAX = 300;
    private static final int MIN = 500;

    private LayoutInflater mInflater;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_SEPARATOR = 0;

    private ViewHolder isSpeaking;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://cmv-gioco.appspot.com/Events");


    enum RowType {
        LIST_ITEM, HEADER_ITEM
    }

    public EventFBAdapter(Context context, final int resource, List<EventItem> objects) {
        super(context,resource);



        if (context != null) {
            this.eventitemList = objects;
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.context = context;
        } else {
            this.context = null;
        }
    }

    class ViewHolder {

        public ImageView image;
        public TextView text;
        public TextView date;
        public ImageView speak;

    }
    class ViewHolderHeader {

        private TextView text;
    }
    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();

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
    public int getItemViewType(int position) {
        return position % 2;
    }

    @Nullable
    @Override
    public EventItem getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getViewTypeCount() {
        return RowType.values().length;
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

                final String textToSpeach = eventitemList.get(position).getMemo();
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
                StorageReference imagesRef = storageRef.child(eventitemList.get(position).getImageMain());
                imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.with(context).load(uri.toString()).placeholder(R.drawable.default_event).into(holder.image);
                    }
                });
                holder.speak.setImageResource(R.drawable.speak);
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

                holderHeader.text.setText(eventitemList.get(position).getStartDate());
                break;
        }

        return rowView;
    }
}

package it.casinovenezia.casinodivenezia;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobile.content.ContentDownloadPolicy;
import com.amazonaws.mobile.content.ContentItem;
import com.amazonaws.mobile.content.ContentListItem;
import com.amazonaws.mobile.content.ContentManager;
import com.amazonaws.mobile.content.ContentProgressListener;
import com.amazonaws.mobile.content.ContentRemovedListener;
import com.amazonaws.mobile.user.IdentityManager;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;

import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;


import java.io.File;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

import java.util.TreeSet;


/**
 * Created by massimomoro on 14/04/15.
 */
public class EventsAdapter extends ArrayAdapter<ContentListItem> implements ContentProgressListener,ContentRemovedListener {
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
    private final ContentListPathProvider pathProvider;
    private final ContentListCacheObserver cacheObserver;

    private ViewHolder isSpeaking;
    /** Content Manager that manages the content for this demo. */
    private final ContentManager contentManager ;
    /** Map from file name to content list item. */
    HashMap<String, ContentListItem> contentListItemMap = new HashMap<>();

    public interface ContentListPathProvider {
        String getCurrentPath();
    }
    public interface ContentListCacheObserver {
        void onCacheChanged();
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
    public void onSuccess(ContentItem contentItem) {
        String b= contentItem.getFilePath();
        File c= contentItem.getFile();
        File d= contentItem.getFile();
        if (contentItem == null) {
            Log.w("tag", String.format("Warning: item '%s' completed," +
                    " but is not in the content list.", contentItem.getFilePath()));
            return;
        }
        cacheObserver.onCacheChanged();
    }

    @Override
    public void onProgressUpdate(String filePath, boolean isWaiting, long bytesCurrent, long bytesTotal) {
        if (filePath == null) {
            Log.w("tag", String.format("Warning: item '%s' completed," +
                    " but is not in the content list.", filePath));
            return;
        }
    }

    @Override
    public void onError(String filePath, Exception ex) {
        if (filePath == null) {
            Log.w("tag", String.format("Warning: item '%s' completed," +
                    " but is not in the content list.", filePath));
            return;
        }
    }

    @Override
    public void onFileRemoved(File removedItem) {
        if (removedItem == null) {
            Log.w("tag", String.format("Warning: item '%s' completed," +
                    " but is not in the content list.", removedItem));
            return;
        }
    }

    @Override
    public void onRemoveError(File file) {
        if (file == null) {
            Log.w("tag", String.format("Warning: item '%s' completed," +
                    " but is not in the content list.", file));
            return;
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

    public EventsAdapter(Context context,
                         final ContentManager contentManager,
                         final ContentListPathProvider pathProvider,
                         final ContentListCacheObserver cacheObserver,
                         final int resource) {
        super(context, resource);
        this.contentManager = contentManager;
        this.pathProvider = pathProvider;
        this.cacheObserver = cacheObserver;
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
    @Override
    public void add(ContentListItem item) {
        if (item.getContentItem() != null) {
            contentListItemMap.put(item.getContentItem().getFilePath(), item);
        }
        super.add(item);
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

//    @Override
//    public int getCount() {
//
//        return eventitemList.size();
//    }

    @Override
    public int getViewTypeCount() {

        return RowType.values().length;
    }


//    @Override
//    public Object getItem(int position) {
//
//        return eventitemList.get(position);
//    }

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

              //  loadImage(arraylist.get(position).getImageMain(), holder.image);
                // get the item state from the server.
                contentManager.getContent("1.png", 0, ContentDownloadPolicy.DOWNLOAD_ALWAYS, false,
                        new ContentProgressListener() {
                            @Override
                            public void onSuccess(final ContentItem contentItem) {
                                // holder.image.setContentItem(contentItem);
                                // ContentListViewAdapter.this.sort(ContentListItem.contentAlphebeticalComparator);
                                //Bitmap myBitmap = BitmapFactory.decodeFile(contentItem.getFilePath());
                                File r = contentItem.getFile();
//                                final Uri contentUri = FileProvider.getUriForFile(
//                                        context,
//                                        context.getString(R.string.content_file_provider_authority),
//                                        contentItem.getFile());
//String a = contentItem.getFilePath();



                               // holder.image.setImageBitmap(myBitmap);
                            }

                            @Override
                            public void onProgressUpdate(String fileName, boolean isWaiting,
                                                         long bytesCurrent, long bytesTotal) {
                                // Nothing to do here.
                            }

                            @Override
                            public void onError(String fileName, Exception ex) {
                                // Remove the item since we can't determine if it exists anymore.
                                // ContentListViewAdapter.this.remove(item);
                            }
                        });
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

//    public void loadImage(ParseFile file, final ImageView myImage) {
//        file
//                .getDataInBackground(new GetDataCallback() {
//
//                    public void done(byte[] data,
//                                     ParseException e) {
//                        if (e == null) {
//                            Log.d("test",
//                                    "We've got data in data.");
//                            // Decode the Byte[] into
//                            // Bitmap
//                            Bitmap bmp = BitmapFactory
//                                    .decodeByteArray(
//                                            data, 0,
//                                            data.length);
//
//                            myImage.setImageBitmap(bmp);
//
//
//                        } else {
//                            Log.d("test",
//                                    "There was a problem downloading the data.");
//                        }
//                    }
//                });
//    }
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

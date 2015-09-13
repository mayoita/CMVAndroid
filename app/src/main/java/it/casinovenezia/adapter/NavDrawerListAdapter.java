package it.casinovenezia.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;
import com.facebook.rebound.ui.Util;

import java.util.ArrayList;

import it.casinovenezia.casinodivenezia.CustomMenuTitleView;
import it.casinovenezia.casinodivenezia.HomeFr;
import it.casinovenezia.casinodivenezia.R;
import it.casinovenezia.it.casinovenezia.model.NavDrawerItem;

/**
 * Created by massimomoro on 27/03/15.
 */
public class NavDrawerListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private Spring mSpring1;
    private SpringRunnable runnable1;
    private static final SpringConfig SPRING_CONFIG = SpringConfig.fromOrigamiTensionAndFriction(40, 3);
    private float mOrigX = 36.0f;
    private static final long DELAY_TIME = 100;

    public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems){
        this.context = context;
        this.navDrawerItems = navDrawerItems;
    }

    @Override
    public int getCount() {
        return navDrawerItems.size();
    }

    @Override
    public Object getItem(int position) {
        return navDrawerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public  class ViewHolder{
        ImageView icon;
        CustomMenuTitleView text;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
            holder = new ViewHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            //holder.icon.setVisibility(View.INVISIBLE);
            holder.text = (CustomMenuTitleView) convertView.findViewById(R.id.text1);
            convertView.setTag (holder);
        } else {
            holder = (ViewHolder) convertView.getTag ();
        }

      //  imgIcon.setX(-60.0f);
        //holder.icon.setVisibility(View.VISIBLE);
        holder.icon.setImageResource(navDrawerItems.get(position).getIcon());
        holder.text.setText(navDrawerItems.get(position).getTitle());

            SpringSystem mSpringSystem = SpringSystem.create();
            mSpring1 = mSpringSystem.createSpring();
            mSpring1.setSpringConfig(SPRING_CONFIG);
            mSpring1.addListener(new ViewSpringListener(holder.icon));
            runnable1 = new SpringRunnable(mSpring1, holder.icon);
            Handler handler = new Handler();
            handler.postDelayed(runnable1, DELAY_TIME * position);


        return convertView;
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
            _spring.setEndValue(mOrigX);

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

        v.setX(value);
    }
}

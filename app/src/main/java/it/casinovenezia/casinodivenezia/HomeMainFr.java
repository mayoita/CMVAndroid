package it.casinovenezia.casinodivenezia;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by massimomoro on 27/03/15.
 */
public class HomeMainFr extends Fragment {
    public static android.support.v4.view.PagerTitleStrip title_strip;
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    MyPageAdapter pageAdapter;
    ViewPager mPager;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static final HomeMainFr newInstance(String message) {
        HomeMainFr instance = new HomeMainFr();
        Bundle bdl = new Bundle();
        bdl.putString(EXTRA_MESSAGE, message);
        instance.setArguments(bdl);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.home, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Fragment> fragments = HomeActivity.fragments;


        title_strip = (android.support.v4.view.PagerTitleStrip)getView().findViewById(R.id.pager_title_strip);
        Typeface font = Typeface.createFromAsset(getActivity().getBaseContext().getAssets(), "fonts/GothamXLight.otf");
        for (int counter = 0 ; counter<title_strip.getChildCount(); counter++) {

            if (title_strip.getChildAt(counter) instanceof TextView) {

                ((TextView)title_strip.getChildAt(counter)).setTypeface(font);
                if (Venue.currentVenue == 0) {
                    ((TextView) title_strip.getChildAt(counter)).setTextColor(getResources().getColor(R.color.red_brand));
                } else {
                    ((TextView) title_strip.getChildAt(counter)).setTextColor(getResources().getColor(R.color.green));
                }
            }
        }

        pageAdapter = new MyPageAdapter(getChildFragmentManager(), fragments);
        mPager = (ViewPager) getView().findViewById(R.id.viewpager);
        mPager.setAdapter(pageAdapter);

    }

    public class MyPageAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> fragments;
        SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

        public MyPageAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {

            return this.fragments.get(position);
        }

        @Override
        public int getCount() {
            return this.fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return getString(R.string.home_label);

                case 1:
                    return getString(R.string.events_label);

                case 2:
                    return getString(R.string.casino_label);

                case 3:
                    return getString(R.string.poker_label);

                case 4:
                    return getString(R.string.tournament_label);

                case 5:
                    return getString(R.string.restaurant_label);

                case 6:
                    return getString(R.string.map_label);
                default:
                    return "item " + position;
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }
    }

    public List<Fragment> getFragments() {
        List<Fragment> fList = new ArrayList<Fragment>();

        fList.add(HomeFr.newInstance("@string/home_title"));
        fList.add(EventsFr.newInstance("@string/events_title"));
        fList.add(CasinoGame.newInstance("@string/casino_title"));
        fList.add(PokerFr.newInstance("@string/poker_title"));
        fList.add(TournamentFr.newInstance("@string/tournament_title"));
        fList.add(Restaurant.newInstance("@string/restaurant_title"));
        fList.add(Map.newInstance("@string/map_title"));

        return fList;
    }

}

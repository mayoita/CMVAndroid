package it.casinovenezia.casinodivenezia;

import android.app.Activity;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class Map extends Fragment implements OnMapReadyCallback {

    static final LatLng CaNoghera = new LatLng(45.520532, 12.358032);
    static final LatLng CaVendramin = new LatLng(45.44284, 12.32988);
    private static final String ARG_PARAM1 = "title";
    private SupportMapFragment map;
    private LatLngBounds.Builder builder;
    private Tracker mTracker;

    public static Map newInstance(String param1) {
        Map fragment = new Map();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    public Map() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        // mTracker.setScreenName("MapVE");
        // mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        StarterApplication application = (StarterApplication) getActivity().getApplication();
        // mTracker = application.getDefaultTracker();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map, null, false);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        map = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
        map.getMapAsync(this);;
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        android.support.v7.app.ActionBar action_bar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        action_bar.setDisplayShowCustomEnabled(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            googleMap.setMyLocationEnabled(true);
            Marker noghera = googleMap.addMarker(new MarkerOptions()
                    .position(CaNoghera)
                    .snippet("Ca'Noghera")
                    .title("Ca'Noghera")
                    .rotation((float) 180.0)

                    .icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.canogherathumbnail2)));
            Marker vendramin = googleMap.addMarker(new MarkerOptions()
                    .position(CaVendramin)
                    .title("Ca' Vendramin Calergi")
                    .snippet("Ca'Vendramin Calergi")
                    .icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.veneziathumbnail2)));
            builder = new LatLngBounds.Builder();
            builder.include(noghera.getPosition());
            builder.include(vendramin.getPosition());
           //googleMap.setOnCameraMoveListener();
            googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {

                @Override
                public void onCameraIdle() {
                    // Move camera.
                 //   googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 50));
                    // Remove listener to prevent position reset on camera move.
                  //  googleMap.setOnCameraChangeListener(null);
                }

               // @Override
             //   public void onCameraChange(CameraPosition arg0) {
                    // Move camera.
               //     googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 50));
                    // Remove listener to prevent position reset on camera move.
               //     googleMap.setOnCameraChangeListener(null);
               // }
            });
            return;
        }

    }
}

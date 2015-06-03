package it.casinovenezia.casinodivenezia;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Map.OnMapInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Map#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Map extends Fragment implements OnMapReadyCallback {

    static final LatLng CaNoghera = new LatLng(45.520532, 12.358032);
    static final LatLng CaVendramin = new LatLng(45.44284, 12.32988);
    private static final String ARG_PARAM1 = "title";
    private GoogleMap map;
    private LatLngBounds.Builder builder;



    private String mTitle;

    private OnMapInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.

     * @return A new instance of fragment Map.
     */

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_PARAM1);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map, null, false);
        map = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();


        Marker noghera = map.addMarker(new MarkerOptions()
                .position(CaNoghera)
                .snippet("Ca'Noghera")
                .title("Ca'Noghera")
                .rotation((float) 180.0)

                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.canogherathumbnail2)));
        Marker vendramin = map.addMarker(new MarkerOptions()
                .position(CaVendramin)
                .title("Ca' Vendramin Calergi")
                .snippet("Ca'Vendramin Calergi")
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.veneziathumbnail2)));
        builder = new LatLngBounds.Builder();
        builder.include(noghera.getPosition());
        builder.include(vendramin.getPosition());
        map.setMyLocationEnabled(true);
       // LatLngBounds bounds = builder.build();
       // int padding = 10; // offset from edges of the map in pixels
        //CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        //map.moveCamera(cu);
         //Move the camera instantly to hamburg with a zoom of 15.
        //map.moveCamera(CameraUpdateFactory.newLatLngZoom(CaVendramin, 15));
        //map.setMyLocationEnabled(true);
        // Zoom in, animating the camera.
        //map.animateCamera(CameraUpdateFactory.zoomTo(12), 2000, null);
        map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {

            @Override
            public void onCameraChange(CameraPosition arg0) {
                // Move camera.
                map.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 50));
                // Remove listener to prevent position reset on camera move.
                map.setOnCameraChangeListener(null);
            }
        });
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnMapInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnMapInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
    @Override
    public void onMapReady(GoogleMap map) {
//        map.addMarker(new MarkerOptions()
//                .position(new LatLng(0, 0))
//                .title("Marker"));
    }


}

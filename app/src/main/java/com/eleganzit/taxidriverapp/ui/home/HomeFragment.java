package com.eleganzit.taxidriverapp.ui.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.eleganzit.taxidriverapp.AcceptRejectRideActivity;
import com.eleganzit.taxidriverapp.NewRequestActivity;
import com.eleganzit.taxidriverapp.R;
import com.eleganzit.taxidriverapp.adapter.CardsDataAdapter;
import com.eleganzit.taxidriverapp.utils.CardStack;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import static androidx.core.content.ContextCompat.checkSelfPermission;
import static com.eleganzit.taxidriverapp.HomeScreen.sw_button;
import static com.eleganzit.taxidriverapp.HomeScreen.toolbar_title;

public class HomeFragment extends Fragment implements OnMapReadyCallback {
    MapView mapView;
    GoogleMap googleMap;
    LocationManager locationManager;

    private HomeViewModel homeViewModel;
    private CardStack mCardStack;
    private CardsDataAdapter mCardAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
      /*  mCardStack = (CardStack) root.findViewById(R.id.container);

        mCardStack.setContentResource(R.layout.card_content);
//        mCardStack.setStackMargin(20);

        mCardAdapter = new CardsDataAdapter(getActivity());
        mCardAdapter.add("Ritu Rajan");
        mCardAdapter.add("Priyal Panicker");
        mCardAdapter.add("Shreyance Panicker");


        mCardStack.setAdapter(mCardAdapter);
mCardStack.setCanSwipe(false);


        mCardStack.setListener(new CardStack.CardEventListener() {
            @Override
            public boolean swipeEnd(int section, float distance) {
                return false;
            }

            @Override
            public boolean swipeStart(int section, float distance) {
                return false;
            }

            @Override
            public boolean swipeContinue(int section, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void discarded(int mIndex, int direction) {


            }

            @Override
            public void topCardTapped() {




            }
        });
        if (mCardStack.getAdapter() != null) {
            Log.i("MyActivity", "Card Stack size: " + mCardStack.getAdapter().getCount());
        }*/
        mapView = (MapView) root.findViewById(R.id.map);

        mapView.getMapAsync(this);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
        toolbar_title.setText("Home");


        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

   /* @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.home_screen, menu);
        RelativeLayout badgeLayout = (RelativeLayout) menu.findItem(R.id.myswitch).getActionView();
       Switch sw_button = badgeLayout.findViewById(R.id.switchForActionBar);
        sw_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    startActivity(new Intent(getActivity(), AcceptRejectRideActivity.class));
                }
            }
        });
    }*/

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        RelativeLayout badgeLayout = (RelativeLayout) menu.findItem(R.id.myswitch).getActionView();
        SwitchCompat sw_button = badgeLayout.findViewById(R.id.switchForActionBar);
        sw_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    startActivity(new Intent(getActivity(), AcceptRejectRideActivity.class));
                }
            }
        });
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        MapsInitializer.initialize(getActivity());
        boolean success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        getActivity(), R.raw.silvermode));

        if (!success) {
            Log.e("MainAct", "Style parsing failed.");
        }
        Log.e("ddddddd", "Style parsing failed.");

        this.googleMap = googleMap;
        googleMap.getUiSettings().setAllGesturesEnabled(false);
        if (checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(getActivity(),Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.pickuppin);
                Bitmap b=bitmapdraw.getBitmap();
                Bitmap firstMarker = Bitmap.createScaledBitmap(b, 70   , 70, false);
                googleMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitude())).icon(BitmapDescriptorFactory.fromBitmap(firstMarker)));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude())));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(16f));

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        }, null);
    }
}
package com.eleganzit.taxidriverapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.eleganzit.taxidriverapp.NewRequestActivity;
import com.eleganzit.taxidriverapp.R;
import com.eleganzit.taxidriverapp.adapter.CardsDataAdapter;
import com.eleganzit.taxidriverapp.utils.CardStack;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MapStyleOptions;

import static com.eleganzit.taxidriverapp.HomeScreen.toolbar_title;

public class HomeFragment extends Fragment implements OnMapReadyCallback {
    MapView mapView;
    GoogleMap googleMap;

    private HomeViewModel homeViewModel;
    private CardStack mCardStack;
    private CardsDataAdapter mCardAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mCardStack = (CardStack) root.findViewById(R.id.container);

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
        }
        mapView = (MapView) root.findViewById(R.id.map);

        mapView.getMapAsync(this);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
        toolbar_title.setText("Online");


        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getActivity());
        boolean success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        getActivity(), R.raw.silvermode));

        if (!success) {
            Log.e("MainAct", "Style parsing failed.");
        }
        Log.e("ddddddd", "Style parsing failed.");

        this.googleMap=googleMap;
        googleMap.getUiSettings().setAllGesturesEnabled(false);
    }
}
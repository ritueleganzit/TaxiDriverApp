package com.eleganzit.taxidriverapp.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.eleganzit.taxidriverapp.R;
import com.eleganzit.taxidriverapp.adapter.HistoryAdapter;
import com.eleganzit.taxidriverapp.datepicker.DatePickerTimeline;
import com.eleganzit.taxidriverapp.datepicker.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.eleganzit.taxidriverapp.HomeScreen.sw_button;
import static com.eleganzit.taxidriverapp.HomeScreen.toolbar_title;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    RecyclerView fav;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        toolbar_title.setText("History");

        fav=root.findViewById(R.id.fav);
        fav.setAdapter(new HistoryAdapter(fav,getActivity()));

        DatePickerTimeline datePickerTimeline = root.findViewById(R.id.dateTimeline);
        final Calendar calendar = Calendar.getInstance();
        final Date date = calendar.getTime();
        int day = Integer.parseInt(new SimpleDateFormat("dd").format(date));    // always 2 digits
        int month = Integer.parseInt(new SimpleDateFormat("MM").format(date));  // always 2 digits
        int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
// Set a Start date (Default, 1 Jan 1970)
        datePickerTimeline.setInitialDate(year, month-1, day);
// Set a date Selected Listener
        datePickerTimeline.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int dayOfWeek) {
                //Do Something
            }

            @Override
            public void onDisabledDateSelected(int year, int month, int day, int dayOfWeek, boolean isDisabled) {

            }
        });

        return root;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }
}
package com.eleganzit.taxidriverapp.ui.slideshow;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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
    Button btn_complete,btn_cancel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        toolbar_title.setText("History");

        fav=root.findViewById(R.id.fav);
        btn_complete=root.findViewById(R.id.btn_complete);
        btn_cancel=root.findViewById(R.id.btn_cancel);
        fav.setAdapter(new HistoryAdapter(fav,getActivity()));

        DatePickerTimeline datePickerTimeline = root.findViewById(R.id.dateTimeline);
        final Calendar calendar = Calendar.getInstance();
        final Date date = calendar.getTime();
        int day = Integer.parseInt(new SimpleDateFormat("dd").format(date));    // always 2 digits
        int month = Integer.parseInt(new SimpleDateFormat("MM").format(date));  // always 2 digits
        int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
// Set a Start date (Default, 1 Jan 1970)
        datePickerTimeline.setActiveDate(calendar);
        datePickerTimeline.setInitialDate(1970, 0, 1);
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


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* AnimatorSet reducer = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(),R.animator.reduce_size);
                reducer.setTarget(btn_complete);

                reducer.start();

                AnimatorSet regainer = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(),R.animator.regain_size);
                regainer.setTarget(btn_cancel);
                regainer.start();
                btn_cancel.setEnabled(false);
                btn_complete.setEnabled(true);*/
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                params.weight = 0.7f;
                params.leftMargin = 10;
                params.topMargin = 10;
                params.rightMargin=10;
                params.bottomMargin = 10;
                btn_cancel.setLayoutParams(params);

                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                params2.weight = 0.3f;
                params2.rightMargin = 10;
                params2.leftMargin=10;
                params2.topMargin = 10;
                params2.bottomMargin = 10;
                btn_complete.setLayoutParams(params2);
                btn_cancel.setEnabled(false);
                btn_complete.setEnabled(true);

            }
        });
        btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_cancel.setEnabled(true);
                btn_complete.setEnabled(false);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                params.weight = 0.7f;
                params.leftMargin = 10;
                params.topMargin = 10;
                params.rightMargin=10;
                params.bottomMargin = 10;
                btn_complete.setLayoutParams(params);

                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                params2.weight = 0.3f;
                params2.rightMargin = 10;
                params2.leftMargin=10;
                params2.topMargin = 10;
                params2.bottomMargin = 10;
                btn_cancel.setLayoutParams(params2);

               /* AnimatorSet reducer = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(),R.animator.reduce_size);
                reducer.setTarget(btn_cancel);
                reducer.start();
                AnimatorSet regainer = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(),R.animator.regain_size);
                regainer.setTarget(btn_complete);
                regainer.start();
                btn_cancel.setEnabled(true);
                btn_complete.setEnabled(false);*/
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
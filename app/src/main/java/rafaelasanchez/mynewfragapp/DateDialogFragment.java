package rafaelasanchez.mynewfragapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by rafa on 2/5/16.
 */
public class DateDialogFragment extends DialogFragment {

    private int year;
    private int month;
    private int day;


    public static final String SELECTED_YEAR = "rafaantosanchez.investorUtility.SELECTED_YEAR";
    public static final String SELECTED_MONTH = "rafaantosanchez.investorUtility.SELECTED_MONTH";
    public static final String SELECTED_DAY = "rafaantosanchez.investorUtility.SELECTED_DAY";
    public static final String THE_TITLE = "rafaantosanchez.investorUtility.THE_TITLE";

    public static final String DAY_MIN = "rafaantosanchez.investorUtility.DAY_MIN";
    public static final String MONTH_MIN = "rafaantosanchez.investorUtility.MONTH_MIN";
    public static final String YEAR_MIN = "rafaantosanchez.investorUtility.YEAR_MIN";
    public static final String DAY_MAX = "rafaantosanchez.investorUtility.DAY_MAX";
    public static final String MONTH_MAX = "rafaantosanchez.investorUtility.MONTH_MAX";
    public static final String YEAR_MAX = "rafaantosanchez.investorUtility.YEAR_MAX";


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String theTitle;
        int yearMin;
        int monthMin;
        int dayMin;
        int yearMax;
        int monthMax;
        int dayMax;

        year = (int) getArguments().getSerializable(SELECTED_YEAR);
        month = (int) getArguments().getSerializable(SELECTED_MONTH);
        day = (int) getArguments().getSerializable(SELECTED_DAY);
        theTitle = (String) getArguments().getSerializable(THE_TITLE);
        dayMin = (int) getArguments().getSerializable(DAY_MIN);
        monthMin = (int) getArguments().getSerializable(MONTH_MIN);
        yearMin = (int) getArguments().getSerializable(YEAR_MIN);
        dayMax = (int) getArguments().getSerializable(DAY_MAX);
        monthMax = (int) getArguments().getSerializable(MONTH_MAX);
        yearMax = (int) getArguments().getSerializable(YEAR_MAX);

        final View theView = getActivity().getLayoutInflater()
                .inflate(R.layout.date_picker_fragment_layout, null);

        DatePicker datePicker = (DatePicker) theView.findViewById(R.id.date_picker);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year_, int month_, int day_) {

                day=day_;
                month=month_;
                year=year_;
                getArguments().putSerializable(SELECTED_DAY,day);
                getArguments().putSerializable(SELECTED_MONTH,month);
                getArguments().putSerializable(SELECTED_YEAR,year);
            }
        });
        if(yearMin!=-1){
            Calendar minCalendar = Calendar.getInstance();
            minCalendar.set(yearMin, monthMin, dayMin);
            datePicker.setMinDate(minCalendar.getTimeInMillis());
        }
        if(yearMax!=-1){
            Calendar minCalendar = Calendar.getInstance();
            minCalendar.set(yearMax,monthMax,dayMax);
            datePicker.setMaxDate(minCalendar.getTimeInMillis());
        }

        return new AlertDialog.Builder(getActivity())
                .setView(theView)
                .setTitle(theTitle)
                .setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK);
                    }
                })
                .create();
    }


    public static DateDialogFragment newInstance(int year_, int month_, int day_, String theTitle_,
                                                 int yearMin_, int monthMin_, int dayMin_,
                                                 int yearMax_, int monthMax_, int dayMax_){
        Bundle dataPassed = new Bundle();
        if (year_==-1) {
            Calendar calendar = Calendar.getInstance();
            day_ = calendar.get(Calendar.DAY_OF_MONTH);
            month_ = calendar.get(Calendar.MONTH);
            year_ = calendar.get(Calendar.YEAR);
        }

        dataPassed.putSerializable(SELECTED_YEAR, year_);
        dataPassed.putSerializable(SELECTED_MONTH, month_);
        dataPassed.putSerializable(SELECTED_DAY, day_);
        dataPassed.putSerializable(THE_TITLE, theTitle_);
        dataPassed.putSerializable(DAY_MIN, dayMin_);
        dataPassed.putSerializable(MONTH_MIN, monthMin_);
        dataPassed.putSerializable(YEAR_MIN, yearMin_);
        dataPassed.putSerializable(DAY_MAX, dayMax_);
        dataPassed.putSerializable(MONTH_MAX, monthMax_);
        dataPassed.putSerializable(YEAR_MAX, yearMax_);

        DateDialogFragment dateDialogFragment = new DateDialogFragment();
        dateDialogFragment.setArguments(dataPassed);
        return dateDialogFragment;
    }

    private void sendResult(int resultCode){
        if(getTargetFragment()==null) return;
        Intent intent =new Intent();
        intent.putExtra(SELECTED_DAY,day);
        intent.putExtra(SELECTED_MONTH,month);
        intent.putExtra(SELECTED_YEAR,year);
        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,intent);
    }

}

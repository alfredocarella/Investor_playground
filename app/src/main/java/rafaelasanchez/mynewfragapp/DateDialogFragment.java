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

import java.util.Calendar;

/**
 * Created by rafa on 2/5/16.
 */
public class DateDialogFragment extends DialogFragment {

    private int year;
    private int month;
    private int day;
    private String theTitle;

    public static final String SELECTED_YEAR = "rafaantosanchez.investorUtility.SELECTED_YEAR";
    public static final String SELECTED_MONTH = "rafaantosanchez.investorUtility.SELECTED_MONTH";
    public static final String SELECTED_DAY = "rafaantosanchez.investorUtility.SELECTED_DAY";
    public static final String THE_TITLE = "rafaantosanchez.investorUtility.THE_TITLE";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        year = (int) getArguments().getSerializable(SELECTED_YEAR);
        month = (int) getArguments().getSerializable(SELECTED_MONTH);
        day = (int) getArguments().getSerializable(SELECTED_DAY);
        theTitle = (String) getArguments().getSerializable(THE_TITLE);

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


    public static DateDialogFragment newInstance(int year_, int month_, int day_, String theTitle_){
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

package rafaelasanchez.mynewfragapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import java.util.Calendar;
import java.util.Date;

public class FirstFragment extends Fragment {

    private  String theString;
    private  String theEndDateString;
    private  String theCurrentCompany;
    private static final int REQUEST_START_DATE=0;
    private static final int REQUEST_END_DATE=1;
    private static final int REQUEST_COMPANY=2;
    private static final String SELECT_DATE="Select date";
    private static final String SELECT_COMPANY="Select company";
    private int startingDay;
    private int startingMonth;
    private int startingYear;
    private int endingDay;
    private int endingMonth;
    private int endingYear;
    private String[] theCompaniesArray={
            "OSEBX",
            "AVANCE","BAKKA","BWLPG","DETNOR","DNB",
            "DNO","FRO","GJF","MHG","NAS",
            "NHY","NOD","OPERA","ORK","PGS",
            "REC","RCL","SCHA","SCHB","SDRL",
            "STL","STB","SUBC","TEL","TGS",
            "YAR"};

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.e("FF.onCreate", "Run");

        theString =(String) getArguments().getSerializable("theString");
        theEndDateString =(String) getArguments().getSerializable("theEndDateString");
        theCurrentCompany = (String) getArguments().getSerializable("theCurrentCompany");
        startingDay = (int) getArguments().getSerializable("startingDay");
        startingMonth = (int) getArguments().getSerializable("startingMonth");
        startingYear = (int) getArguments().getSerializable("startingYear");
        endingDay = (int) getArguments().getSerializable("endingDay");
        endingMonth = (int) getArguments().getSerializable("endingMonth");
        endingYear = (int) getArguments().getSerializable("endingYear");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("FF.onCreateView", "Run");
        return inflater.inflate(R.layout.first_frag_layout, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("FF.onStart", "Run");

        TextView startDateTextView = (TextView) getActivity().findViewById(R.id.first_frag_start_date_text_view);
        TextView endDateTextView = (TextView) getActivity().findViewById(R.id.first_frag_end_date_text_view);
        TextView companyTextView = (TextView) getActivity().findViewById(R.id.first_frag_company_text_view);
        startDateTextView.setText(theString);
        endDateTextView.setText(theEndDateString);
        companyTextView.setText(theCurrentCompany);
        ((MainActivity)getActivity()).onParametersUpdated();

        startDateTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.e("FF.onStart", "startDateTextView.setOnClickListener.onClick");

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                DateDialogFragment dateDialogFragment = DateDialogFragment
                        .newInstance(startingYear,startingMonth,startingDay,"Enter start date");
                dateDialogFragment.setTargetFragment(FirstFragment.this, REQUEST_START_DATE);
                dateDialogFragment.show(fragmentManager, SELECT_DATE);
            }
        });

        endDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("FF.onStart", "endDateTextView.setOnClickListener.onClick");
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                DateDialogFragment dateDialogFragment = DateDialogFragment
                        .newInstance(endingYear,endingMonth,endingDay,"Enter end date");
                dateDialogFragment.setTargetFragment(FirstFragment.this, REQUEST_END_DATE);
                dateDialogFragment.show(fragmentManager, SELECT_DATE);
            }
        });


        companyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
                ListOfSmthFrag listOfSmthFrag =
                        ListOfSmthFrag.newInstance(theCompaniesArray);
                listOfSmthFrag.setTargetFragment(FirstFragment.this, REQUEST_COMPANY);
                listOfSmthFrag.show(fragmentManager,SELECT_COMPANY);

            }
        });

        Button button = (Button) getActivity().findViewById(R.id.return_rate_1st_frag_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                ReturnRateDialogFragment returnRateDialogFragment = ReturnRateDialogFragment.newInstance();
                returnRateDialogFragment.setTargetFragment(FirstFragment.this,0);
                returnRateDialogFragment.show(fragmentManager,"");
            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK) return;
        if(requestCode==REQUEST_START_DATE){
            Log.e("FF.onActivityResult", "requestCode==REQUEST_START_DATE");
            startingDay = (int)
                    data.getSerializableExtra(DateDialogFragment.SELECTED_DAY);
            startingMonth = (int)
                    data.getSerializableExtra(DateDialogFragment.SELECTED_MONTH);
            startingYear = (int)
                    data.getSerializableExtra(DateDialogFragment.SELECTED_YEAR);

            ((MainActivity) getActivity()).setStartingDay(startingDay);
            ((MainActivity) getActivity()).setStartingMonth(startingMonth);
            ((MainActivity) getActivity()).setStartingYear(startingYear);

            int startingMonth_=startingMonth+1;
            String dateString = startingDay+"/"+startingMonth_+"/"+startingYear;
            Log.e("Start dateString",dateString);

            ((MainActivity)getActivity()).setTheString(dateString);
            TextView startDateTextView = (TextView) getActivity().findViewById(R.id.first_frag_start_date_text_view);
            startDateTextView.setText(dateString);
        }
        if (requestCode==REQUEST_END_DATE){
            Log.e("FF.onActivityResult", "requestCode==REQUEST_END_DATE");
            endingDay = (int)
                    data.getSerializableExtra(DateDialogFragment.SELECTED_DAY);
            endingMonth = (int)
                    data.getSerializableExtra(DateDialogFragment.SELECTED_MONTH);
            endingYear = (int)
                    data.getSerializableExtra(DateDialogFragment.SELECTED_YEAR);

            ((MainActivity) getActivity()).setEndingDay(endingDay);
            ((MainActivity) getActivity()).setEndingMonth(endingMonth);
            ((MainActivity) getActivity()).setEndingYear(endingYear);

            int endingMonth_=endingMonth+1;
            String dateString = endingDay+"/"+endingMonth_+"/"+endingYear;
            Log.e("End dateString",dateString);

            ((MainActivity)getActivity()).setTheEndDateString(dateString);
            TextView endDateTextView = (TextView) getActivity().findViewById(R.id.first_frag_end_date_text_view);
            endDateTextView.setText(dateString);

        }
        if (requestCode==REQUEST_COMPANY){
            Log.e("FF.onActivityResult", "requestCode==REQUEST_COMPANY");
            String selectedCompany = (String)
                    data.getSerializableExtra(ListOfSmthFrag.SELECTED_COMPANY);

            ((MainActivity)getActivity()).setTheCurrentCompany(selectedCompany);
            TextView companyTextView = (TextView) getActivity().findViewById(R.id.first_frag_company_text_view);
            companyTextView.setText(selectedCompany);

        }
    }

}

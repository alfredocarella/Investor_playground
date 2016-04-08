package rafaelasanchez.mynewfragapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class FirstFragment extends Fragment {

    private  String theString;
    private  String theEndDateString;
    private  String theCurrentCompany;
    private  String theCurrentBenchmark;
    private static final int REQUEST_START_DATE=0;
    private static final int REQUEST_END_DATE=1;
    private static final int REQUEST_COMPANY=2;
    private static final int REQUEST_BENCHMARK=3;
    private static final String SELECT_START_DATE="Select start date";
    private static final String SELECT_END_DATE="Select end date";
    private static final String SELECT_COMPANY="Select company";
    private static final String SELECT_BENCHMARK="Select benchmark";
    private int startingDay;
    private int startingMonth;
    private int startingYear;
    private int endingDay;
    private int endingMonth;
    private int endingYear;

    private String[] theCompaniesArray={
            "",
            "OSEBX",
            "AVANCE","BAKKA","BWLPG","DETNOR","DNB",
            "DNO","FRO","GJF","MHG","NAS",
            "NHY","NOD","OPERA","ORK","PGS",
            "REC","RCL","SCHA","SCHB","SDRL",
            "STL","STB","SUBC","TEL","TGS",
            "YAR"};

    private String[] theCompanyNamesArray={
            "",
            "Oslo Stock Exchange Benchmark Index",
            "Avance Gas Holding",
            "Bakkafrost",
            "BW LPG",
            "Det norske oljeselskap",
            "DNB",
            "DNO",
            "Frontline",
            "Gjensidige Forsikring",
            "Marine Harvest",
            "Norwegian Air Shuttle",
            "Norsk Hydro",
            "Nordic Semiconductor",
            "Opera Software",
            "Orkla",
            "Petroleum Geo-Services",
            "REC Silicon",
            "Royal Caribbean Cruises",
            "Schibsted ser. A",
            "Schibsted ser. B",
            "Seadrill",
            "Statoil",
            "Storebrand",
            "Subsea 7",
            "Telenor",
            "TGS-NOPEC Geophysical Company",
            "Yara International"};

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.e("FF.onCreate", "Run");

        theString =(String) getArguments().getSerializable("theString");
        theEndDateString =(String) getArguments().getSerializable("theEndDateString");
        theCurrentCompany = (String) getArguments().getSerializable("theCurrentCompany");
        theCurrentBenchmark = (String) getArguments().getSerializable("theCurrentBenchmark");
        startingDay = (int) getArguments().getSerializable("startingDay");
        startingMonth = (int) getArguments().getSerializable("startingMonth");
        startingYear = (int) getArguments().getSerializable("startingYear");
        endingDay = (int) getArguments().getSerializable("endingDay");
        endingMonth = (int) getArguments().getSerializable("endingMonth");
        endingYear = (int) getArguments().getSerializable("endingYear");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.first_frag_layout, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        TextView startDateTextView = (TextView) getActivity().findViewById(R.id.first_frag_start_date_text_view);
        TextView endDateTextView = (TextView) getActivity().findViewById(R.id.first_frag_end_date_text_view);
        TextView companyTextView = (TextView) getActivity().findViewById(R.id.first_frag_company_text_view);
        TextView benchmarkTextView = (TextView) getActivity().findViewById(R.id.first_frag_benchmark_text_view);
        startDateTextView.setText(theString);
        endDateTextView.setText(theEndDateString);
        companyTextView.setText(theCurrentCompany);
        benchmarkTextView.setText(theCurrentBenchmark);


        ((MainActivity)getActivity()).onNewGraph();

        startDateTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                DateDialogFragment dateDialogFragment = DateDialogFragment
                        .newInstance(startingYear, startingMonth, startingDay, "Enter start date");
                dateDialogFragment.setTargetFragment(FirstFragment.this, REQUEST_START_DATE);
                dateDialogFragment.show(fragmentManager, SELECT_START_DATE);
            }
        });

        endDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                DateDialogFragment dateDialogFragment = DateDialogFragment
                        .newInstance(endingYear, endingMonth, endingDay, "Enter end date");
                dateDialogFragment.setTargetFragment(FirstFragment.this, REQUEST_END_DATE);
                dateDialogFragment.show(fragmentManager, SELECT_END_DATE);
            }
        });


        companyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
                String popUpTitle="Select a financial instrument";
                ListOfSmthFrag listOfSmthFrag =
                        ListOfSmthFrag.newInstance(theCompaniesArray, theCompanyNamesArray,popUpTitle);
                listOfSmthFrag.setTargetFragment(FirstFragment.this, REQUEST_COMPANY);
                listOfSmthFrag.show(fragmentManager,SELECT_COMPANY);

            }
        });


        benchmarkTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                String popUpTitle="Select a benchmark";
                ListOfSmthFrag listOfSmthFrag= ListOfSmthFrag.newInstance(theCompaniesArray,theCompanyNamesArray,popUpTitle);
                listOfSmthFrag.setTargetFragment(FirstFragment.this, REQUEST_BENCHMARK);
                listOfSmthFrag.show(fragmentManager,SELECT_COMPANY);
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK) return;
        if(requestCode==REQUEST_START_DATE){
            startingDay = (int)
                    data.getSerializableExtra(DateDialogFragment.SELECTED_DAY);
            startingMonth = (int)
                    data.getSerializableExtra(DateDialogFragment.SELECTED_MONTH);
            startingYear = (int)
                    data.getSerializableExtra(DateDialogFragment.SELECTED_YEAR);

            ArrayList<Integer> startingDate = new ArrayList<Integer>();
            startingDate.add(0,startingDay);
            startingDate.add(1,startingMonth);
            startingDate.add(2,startingYear);
            ((MainActivity) getActivity()).setStartingDate(startingDate);

            int startingMonth_=startingMonth+1;
            String dateString = startingDay+"/"+startingMonth_+"/"+startingYear;
            Log.e("Start dateString",dateString);

            ((MainActivity)getActivity()).setTheString(dateString);
            TextView startDateTextView = (TextView) getActivity().findViewById(R.id.first_frag_start_date_text_view);
            startDateTextView.setText(dateString);
        }
        if (requestCode==REQUEST_END_DATE){
            endingDay = (int)
                    data.getSerializableExtra(DateDialogFragment.SELECTED_DAY);
            endingMonth = (int)
                    data.getSerializableExtra(DateDialogFragment.SELECTED_MONTH);
            endingYear = (int)
                    data.getSerializableExtra(DateDialogFragment.SELECTED_YEAR);

            ArrayList<Integer> endingDate = new ArrayList<Integer>();
            endingDate.add(0,endingDay);
            endingDate.add(1,endingMonth);
            endingDate.add(2,endingYear);
            ((MainActivity) getActivity()).setEndingDate(endingDate);

            int endingMonth_=endingMonth+1;
            String dateString = endingDay+"/"+endingMonth_+"/"+endingYear;
            Log.e("End dateString",dateString);

            ((MainActivity)getActivity()).setTheEndDateString(dateString);
            TextView endDateTextView = (TextView) getActivity().findViewById(R.id.first_frag_end_date_text_view);
            endDateTextView.setText(dateString);

        }
        if (requestCode==REQUEST_COMPANY){
            String selectedCompany = (String)
                    data.getSerializableExtra(ListOfSmthFrag.SELECTED_FINANCIAL_INSTRUMENT);

            ((MainActivity)getActivity()).setTheCurrentCompany(selectedCompany);
            TextView companyTextView = (TextView) getActivity().findViewById(R.id.first_frag_company_text_view);
            companyTextView.setText(selectedCompany);

        }
        if (requestCode==REQUEST_BENCHMARK){
            String selectedBenchmark = (String)
                    data.getSerializableExtra(ListOfSmthFrag.SELECTED_FINANCIAL_INSTRUMENT);

            ((MainActivity)getActivity()).setTheCurrentBenchmark(selectedBenchmark);
            TextView benchmarkTextView = (TextView) getActivity().findViewById(R.id.first_frag_benchmark_text_view);
            benchmarkTextView.setText(selectedBenchmark);

        }
    }

}

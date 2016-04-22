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
import java.util.Calendar;
import java.util.Date;


public class FirstFragment extends Fragment {

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
    private ArrayList<Integer> dateInfimum;
    private SimpleUserValues values;


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

        values = (SimpleUserValues) getArguments().getSerializable("values");

        theCurrentCompany = values.getTheCurrentCompany();
        theCurrentBenchmark = values.getTheCurrentBenchmark();
        startingDay = values.getStartingDate().get(0);
        startingMonth = values.getStartingDate().get(1);
        startingYear = values.getStartingDate().get(2);
        endingDay = values.getEndingDate().get(0);
        endingMonth = values.getEndingDate().get(1);
        endingYear = values.getEndingDate().get(2);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.first_frag_layout, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();

        TextView startDateTextView = (TextView) getActivity().findViewById(R.id.first_frag_start_date_text_view);
        final TextView endDateTextView = (TextView) getActivity().findViewById(R.id.first_frag_end_date_text_view);
        TextView companyTextView = (TextView) getActivity().findViewById(R.id.first_frag_company_text_view);
        TextView benchmarkTextView = (TextView) getActivity().findViewById(R.id.first_frag_benchmark_text_view);
        startDateTextView.setText(values.getTheStartDateString());
        endDateTextView.setText(values.getTheEndDateString());
        companyTextView.setText(theCurrentCompany);
        benchmarkTextView.setText(theCurrentBenchmark);


        final Calendar today = Calendar.getInstance();
        today.setTime(new Date());

        ((MainActivity) getActivity()).onNewGraph();

        startDateTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Calendar endingDate=Calendar.getInstance();
                endingDate.set(endingYear,endingMonth,endingDay);
                endingDate.add(Calendar.MONTH, -1);

                dateInfimum= ((MainActivity) getActivity()).getStartingDateInfimum();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                DateDialogFragment dateDialogFragment = DateDialogFragment
                        .newInstance(startingYear, startingMonth, startingDay, "Enter start date",
                                dateInfimum.get(2),dateInfimum.get(1),dateInfimum.get(0),
                                endingDate.get(Calendar.YEAR),endingDate.get(Calendar.MONTH),
                                endingDate.get(Calendar.DAY_OF_MONTH));
                dateDialogFragment.setTargetFragment(FirstFragment.this, REQUEST_START_DATE);
                dateDialogFragment.show(fragmentManager, SELECT_START_DATE);
            }
        });

        endDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar startDate = Calendar.getInstance();
                startDate.set(startingYear,startingMonth,startingDay);
                startDate.add(Calendar.MONTH,1);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                DateDialogFragment dateDialogFragment = DateDialogFragment
                        .newInstance(endingYear, endingMonth, endingDay, "Enter end date",
                                startDate.get(Calendar.YEAR),startDate.get(Calendar.MONTH),
                                startDate.get(Calendar.DAY_OF_MONTH),
                                today.get(Calendar.YEAR),today.get(Calendar.MONTH),today.get(Calendar.DAY_OF_MONTH));
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

            ((MainActivity) getActivity()).setStartingDate(
                    startingDay, startingMonth, startingYear,true);

        }
        if (requestCode==REQUEST_END_DATE){
            endingDay = (int)
                    data.getSerializableExtra(DateDialogFragment.SELECTED_DAY);
            endingMonth = (int)
                    data.getSerializableExtra(DateDialogFragment.SELECTED_MONTH);
            endingYear = (int)
                    data.getSerializableExtra(DateDialogFragment.SELECTED_YEAR);

            ((MainActivity) getActivity()).setEndingDate(
                    endingDay, endingMonth, endingYear,true);

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


    public void setDateInfimum(ArrayList<Integer> dateInfimum) {
        this.dateInfimum = dateInfimum;
    }



}

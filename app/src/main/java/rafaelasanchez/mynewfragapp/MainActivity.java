package rafaelasanchez.mynewfragapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    public SimpleUserValues values;

    FirstFragment firstFragment;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.bar_calc) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            ReturnRateDialogFragment returnRateDialogFragment = ReturnRateDialogFragment.newInstance();
            returnRateDialogFragment.show(fragmentManager, "");

            return true;
        }
        else if(item.getItemId()==R.id.bar_config) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            ConfigDialogFragment configDialogFragment = ConfigDialogFragment.newInstance();
            configDialogFragment.show(fragmentManager, "");



            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bar, menu);
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.activity_main);

        restoreSavedValues();

        //Register the Broadcast receiver
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(FileService.TRANSACTION_DONE);
        registerReceiver(downloadReceiver, intentFilter);

        //Decide which fragment to load
        putRightFragment();

    }








    @Override
    public void onBackPressed() {
        ChangeFragment();
    }


    // Methods for loading fragments and updating the different views

    private void ChangeFragment(){
        if(values.getTheCurrentFragment()==1){
            values.setTheCurrentFragment(2);
        }else{
            values.setTheCurrentFragment(1);
        }
        putRightFragment();
    }

    private void putRightFragment(){
        if(values.getTheCurrentFragment()==1){
            put1stFrag();
        }else{
            put2ndFrag();
        }
    }

    private void put1stFrag(){
        Bundle bundle = new Bundle();
        bundle.putSerializable("values",values);
        firstFragment = new FirstFragment();
        firstFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, firstFragment)
                .addToBackStack(null)
                .commit();
    }

    private void put2ndFrag(){
        SecondFragment secondFragment = new SecondFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, secondFragment)
                .addToBackStack(null)
                .commit();
    }



    private void onPriceGraphClicked() {
        if(values.getTheCurrentFragment()==1) {
            values.setTheCurrentFragment(2);
            put2ndFrag();
        }else{
            values.setTheCurrentFragment(1);
            put1stFrag();
        }
    }


    public void onNewGraph(){
        dataIntoArrays();
    }


    private void onParametersUpdated(boolean companyChanged,
                                     boolean benchmarkChanged,
                                     boolean datesChanged,
                                     String callingMethod){


        Log.e("onParametersUpdated", "companySet " + String.valueOf(values.getCompanySet()) +  ",  startDateSet " + String.valueOf(values.getStartDateSet()) + ";  endDateSet " + String.valueOf(values.getEndDateSet()) + "  ; callingMethod: " + callingMethod);

        if(values.getCompanySet()&&(companyChanged||datesChanged)&&!benchmarkChanged){
            // if only the company has been selected, add some random dates to speed up the user experience
            if(!values.getStartDateSet()&&!values.getEndDateSet()){
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());

                setEndingDate(calendar.get(Calendar.DAY_OF_MONTH),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.YEAR),
                        false);

                calendar.add(Calendar.YEAR, -1);
                setStartingDate(calendar.get(Calendar.DAY_OF_MONTH),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.YEAR),
                        false);
            }
            if(values.getCompanySet()&&values.getStartDateSet()&&values.getEndDateSet()){
                values.setRequestCompany(true);
                values.setRequestIndex(values.getBenchmarkSet());
                startFileService("onParametersUpdated");
            }

        }else if(benchmarkChanged&&values.getCompanySet()&&values.getStartDateSet()&&values.getEndDateSet()){
            values.setRequestCompany(false);
            values.setRequestIndex(true);
            startFileService("onParametersUpdated");
        }


    }



    // Methods for downloading price data and putting it into arrays
    private void startFileService(String callingMethod) {

        Log.e("startFileService","callingMethod: " + String.valueOf(callingMethod));

        if(isInternetAvailable()) {

            String theDay = Integer.toString(values.getStartingDate().get(0));
            String theMonth = Integer.toString(values.getStartingDate().get(1));
            String theYear = Integer.toString(values.getStartingDate().get(2));
            String theEndDay = Integer.toString(values.getEndingDate().get(0));
            String theEndMonth = Integer.toString(values.getEndingDate().get(1));
            String theEndYear = Integer.toString(values.getEndingDate().get(2));


            Intent intent = new Intent(this, FileService.class);
        /*      http://ichart.yahoo.com/table.csv?s=REC.OL&a=0&b=1&c=2000&d=0&e=31&f=2010
                a month0; b day0; c year0;  similarly for the end dates
                for dividends and splits replace date with &g=v     */

            String theURL1stPart = "http://ichart.yahoo.com/table.csv?s=";
            String ticker="";
            if (values.getRequestIndex()) {
                ticker = values.getTheCurrentBenchmark();
            } else if(values.getRequestCompany()) {
                ticker = values.getTheCurrentCompany();
            }
            if(!ticker.equals("")) {
                String theURLcomplete = theURL1stPart + ticker
                        + ".OL&a=" + theMonth + "&b=" + theDay + "&c=" + theYear
                        + "&d=" + theEndMonth + "&e=" + theEndDay + "&f=" + theEndYear;

                Log.e("theURLcomplete", theURLcomplete);
                intent.putExtra("url", theURLcomplete);
                this.startService(intent);
            }
        }else{
            CharSequence text = "Check your internet connection";
            Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
            toast.show();
        }
    }


    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("FileService","Service Received");
            getFileContents();
        }
    };


    private void getFileContents(){
        StringBuilder sb;
        try {
            FileInputStream fis = this.openFileInput("myFile");
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            sb = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append('\n');
            }

            if (values.getRequestIndex()) {
                values.setTheDownloadedIndexData(sb.toString());
                values.setRequestIndex(false);
                if(values.getRequestCompany()) {
                    startFileService("getFileContents");
                }else{
                    dataIntoArrays();
                }
            } else if(values.getRequestCompany()) {
                values.setTheDownloadedData(sb.toString());
                values.setRequestCompany(false);
                dataIntoArrays();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private void dataIntoArrays(){
        if (!values.getTheDownloadedIndexData().equals("")) {
            DataReducer dataReducerIndex = new DataReducer(values.getTheDownloadedIndexData());
            values.setArrayListIndex(dataReducerIndex.getTheArray());
            values.setTheDatesBenchmark(dataReducerIndex.getTheDates());
        }
        if(!values.getTheDownloadedData().equals("")) {
            DataReducer dataReducer= new DataReducer(values.getTheDownloadedData());
            values.setArrayList(dataReducer.getTheArray());
            values.setTheDates(dataReducer.getTheDates());

            int nItems=values.getTheDates().get(0).size()-1;
            int infDay =values.getTheDates().get(0).get(nItems);
            int infMonth =values.getTheDates().get(1).get(nItems);
            int infYear =values.getTheDates().get(2).get(nItems);

            Calendar infimumDate = Calendar.getInstance();
            infimumDate.set(infYear, infMonth-1, infDay);

            Calendar currentMinDate=Calendar.getInstance();
            currentMinDate.set( values.getStartingDate().get(2),
                    values.getStartingDate().get(1),
                    values.getStartingDate().get(0));

            long diff = currentMinDate.getTimeInMillis()-infimumDate.getTimeInMillis();

            int diffDays = (int) TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS);

            if(diffDays<-14){
                setStartingDate(
                        infimumDate.get(Calendar.DAY_OF_MONTH),
                        infimumDate.get(Calendar.MONTH),
                        infimumDate.get(Calendar.YEAR),
                        false);

                values.setStartingDateInfimum(values.getStartingDate());

            }
        }

        plotStuff();
    }



    // Plot the price data

    public void plotStuff(){
        if(values.getArrayList()!=null) {

            if(values.getTheCurrentFragment()==2) {

                FrameLayout graphContainer2= (FrameLayout) findViewById(R.id.the_2nd_frag);
                Graph graph = new Graph(this);
                graph.newInstance(values);
                View theGraph = graph.getTheGraph();
                graphContainer2.addView(theGraph);
                graphContainer2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onPriceGraphClicked();
                    }
                    });

            }else{

                FrameLayout graphContainer = (FrameLayout) findViewById(R.id.prices_1st_frag_the_outer_container);

                values.setTheCurrentGraph(1);
                Graph graph = new Graph(this);
                graph.newInstance(values);
                View theGraph = graph.getTheGraph();
                graphContainer.addView(theGraph);
                graphContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        values.setTheCurrentGraph(1);
                        onPriceGraphClicked();
                    }
                });

                showStrategy();
                showStats();

            }

        }

    }


    //strategy
    private void showStrategy(){

        Strategy strategy = new Strategy(values);
        values.setStrategyResult(strategy.getTheResult());
        values.setYourReturn(strategy.getYourReturn());

        FrameLayout strategyContainer =
                (FrameLayout) findViewById(R.id.strategy_1st_frag_the_outer_container);

        values.setTheCurrentGraph(2);
        Graph graph = new Graph(this);
        graph.newInstance(values);
        View theGraph = graph.getTheGraph();
        strategyContainer.addView(theGraph);

        strategyContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                values.setTheCurrentGraph(2);
                onPriceGraphClicked();
            }
        });
    }


    //Show juicy stats into the screen

    private void showStats(){

        View outer_container = findViewById(R.id.the_stats);

        if (values.getCompanySet()&&values.getArrayList()!=null) {
            outer_container.setVisibility(View.VISIBLE);

            DataCruncher dataCruncher = new DataCruncher(
                    values.getArrayList(),
                    values.getArrayListIndex(),
                    values.getTheDates(),
                    values.getTheDatesBenchmark());


            DecimalFormat thePrecision = new DecimalFormat("0.00");

            TextView theReturnTextView = (TextView) findViewById(R.id.the_return);
            Double theReturn = dataCruncher.getTheReturn();
            theReturnTextView.setText(thePrecision.format(theReturn));

            Double theAnnualizedReturn = dataCruncher.getTheAnnualizedReturn();
            TextView theAReturnTextView = (TextView) findViewById(R.id.the_a_return);
            theAReturnTextView.setText(thePrecision.format(theAnnualizedReturn));


            TextView yourReturnTextView = (TextView) findViewById(R.id.your_return);
            Float yourReturn = values.getYourReturn();
            yourReturnTextView.setText(thePrecision.format(yourReturn));

            Double yourAnnualizedReturn = dataCruncher.getTheAnnualizedReturn();
            TextView yourAReturnTextView = (TextView) findViewById(R.id.the_a_return);
            yourAReturnTextView.setText(thePrecision.format(yourAnnualizedReturn));




            LinearLayout theBenchmarkStats = (LinearLayout) findViewById(R.id.the_benchmark_stats);

            if (values.getBenchmarkSet() && values.getArrayListIndex()!=null) {

                theBenchmarkStats.setVisibility(View.VISIBLE);

                TextView theCorrTextView = (TextView) findViewById(R.id.the_correlation);
                Double theCorrelation = dataCruncher.getTheCorrelation();
                theCorrTextView.setText(thePrecision.format(theCorrelation));

                TextView theBetaTextView = (TextView) findViewById(R.id.the_beta);
                Double theBeta = dataCruncher.getTheBeta();
                theBetaTextView.setText(thePrecision.format(theBeta));

                TextView theSharpeTextView = (TextView) findViewById(R.id.the_sharpe);
                Double theSharpe = dataCruncher.getTheSharpe();
                theSharpeTextView.setText(thePrecision.format(theSharpe));
            } else{
                theBenchmarkStats.setVisibility(View.GONE);
            }

        }else{
            outer_container.setVisibility(View.GONE);
        }

    }







    // setters and getters





    public void setStartingDate(int day_, int month_, int year_, boolean callOnParametersUpdated) {

        ArrayList<Integer> startingDate_ = new ArrayList<Integer>();
        startingDate_.add(0,day_);
        startingDate_.add(1,month_);
        startingDate_.add(2,year_);

        if(values.getStartingDate()!=startingDate_){
            values.setStartingDate(startingDate_);
            values.setStartDateSet(true);

            int startingMonth_=values.getStartingDate().get(1)+1;
            String dateString = values.getStartingDate().get(0)+"/"+startingMonth_+"/"+values.getStartingDate().get(2);
            values.setTheStartDateString(dateString);
            TextView startDateTextView = (TextView) findViewById(R.id.first_frag_start_date_text_view);
            startDateTextView.setText(dateString);

            if(callOnParametersUpdated) {
                onParametersUpdated(false, false, true, "setStartingDate");
            }
        }

    }

    public void setEndingDate(int day_, int month_, int year_, boolean callOnParametersUpdated) {

        ArrayList<Integer> endingDate_ = new ArrayList<Integer>();
        endingDate_.add(0,day_);
        endingDate_.add(1,month_);
        endingDate_.add(2, year_);

        if(values.getEndingDate()!=endingDate_){
            values.setEndingDate(endingDate_);
            values.setEndDateSet(true);

            int endingMonth_=values.getEndingDate().get(1)+1;
            String dateString = values.getEndingDate().get(0)+"/"+endingMonth_+"/"+values.getEndingDate().get(2);
            values.setTheEndDateString(dateString);
            TextView endDateTextView = (TextView) findViewById(R.id.first_frag_end_date_text_view);
            endDateTextView.setText(dateString);

            if(callOnParametersUpdated){
                onParametersUpdated(false,false,true,"setEndingDate");
                //boolean companyChanged, boolean benchmarkChanged,boolean datesChanged, String callingMethod
            }
        }

    }

    public void setTheCurrentCompany(String theCurrentCompany) {
        values.setTheCurrentCompany(theCurrentCompany);
        values.setStartingDateInfimum(null);

        if(values.getTheCurrentCompany().equals("")) {
            values.setTheDownloadedData("");
            values.setArrayList(null);
            values.setCompanySet(false);
            values.setRequestCompany(false);
            FrameLayout thePriceGraph = (FrameLayout) findViewById(R.id.prices_1st_frag_the_outer_container);
            thePriceGraph.setVisibility(View.GONE);

            FrameLayout theStrategyGraph = (FrameLayout) findViewById(R.id.strategy_1st_frag_the_outer_container);
            theStrategyGraph.setVisibility(View.GONE);

            View outer_container = findViewById(R.id.the_stats);
            outer_container.setVisibility(View.GONE);

        }else{
            values.setCompanySet(true);
            values.setRequestCompany(true);
            onParametersUpdated(true, false, false, "setTheCurrentCompany");
            //boolean companyChanged, boolean benchmarkChanged,boolean datesChanged, String callingMethod
        }
    }

    public void setTheCurrentBenchmark(String theCurrentBenchmark) {
        values.setTheCurrentBenchmark(theCurrentBenchmark);
        if(values.getTheCurrentBenchmark().equals("")) {
            values.setBenchmarkSet(false);
            values.setRequestIndex(false);
            values.setTheDownloadedIndexData("");
            values.setArrayListIndex(null);
            LinearLayout theBenchmarkStats = (LinearLayout) findViewById(R.id.the_benchmark_stats);
            theBenchmarkStats.setVisibility(View.GONE);
        }else{
            values.setBenchmarkSet(true);
            values.setRequestIndex(true);
            onParametersUpdated(false,true,false,"setTheCurrentBenchmark");
            //boolean companyChanged, boolean benchmarkChanged,boolean datesChanged, String callingMethod
        }
    }



    // Method to restore the last values, called from onCreate
    private void restoreSavedValues(){
        
        if(getSharedPreferences(SimpleUserValues.getMyAppKey(), MODE_PRIVATE).contains("JSON")) {
            Gson gson = new Gson();
            Type classType = new TypeToken<SimpleUserValues>() {}.getType();
            values = gson.fromJson(
                    getSharedPreferences(SimpleUserValues.getMyAppKey(), MODE_PRIVATE)
                            .getString("JSON", "")
                    , classType
            );
        }else{
            values=SimpleUserValues.newInstance();
        }

    }


    //Check internet availability, called from startFileService
    public boolean isInternetAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    // Kill the BroadcastReceiver to avoid seeing an error message
    @Override
    protected void onStop() {
        //if(downloadReceiver)
        try{
            unregisterReceiver(downloadReceiver);
        }catch(IllegalArgumentException e){
            Log.e("MainActivity.onStop ", "IllegalArgumentException: Receiver not registered");
        }
        super.onStop();
    }


    //Save the key variables before dying
    @Override
    public void onSaveInstanceState(Bundle outState) {

        try {
            Gson gson = new Gson();
            Type classType = new TypeToken<SimpleUserValues>() {
            }.getType();

            String JSONString = gson.toJson(values, classType);
            getSharedPreferences(SimpleUserValues.getMyAppKey(), MODE_PRIVATE).edit()
                    .putString("JSON", JSONString).commit();
        }catch(RuntimeException e){
            Log.e("RuntimeException","");
        }
        super.onSaveInstanceState(outState);
    }



}

package rafaelasanchez.mynewfragapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {


    private int theCurrentFragment;
    private ArrayList<Integer> startingDate = new ArrayList<Integer>();
    private ArrayList<Integer> endingDate = new ArrayList<Integer>();
    private ArrayList<Integer> startingDateInfimum=new ArrayList<Integer>();

    private boolean requestIndex=false;
    private boolean requestCompany=false;
    private boolean startDateSet=false;
    private boolean endDateSet=false;
    private boolean companySet=false;
    private boolean benchmarkSet=false;

    private String theStartDateString;
    private String theEndDateString;
    private String theCurrentCompany;
    private String theCurrentBenchmark;
    private String theDownloadedData;
    private String theDownloadedIndexData;

    private ArrayList<Float> arrayList = new ArrayList<Float>();
    private ArrayList<Float> arrayListIndex = new ArrayList<Float>();
    private ArrayList<ArrayList<Integer>> theDates=new ArrayList<ArrayList<Integer>>();
    private ArrayList<ArrayList<Integer>> theDatesBenchmark=new ArrayList<ArrayList<Integer>>();

    FirstFragment firstFragment;

    public static final String myAppKey = "Investor Playground";
    public static final String THECURRENTFRAGMENT = "theCurrentFragment";
    public static final String THESTARTDATESTRING = "THESTARTDATESTRING";
    public static final String THEENDDATESTRING = "THEENDDATESTRING";
    public static final String THECURRENTCOMPANY = "THECURRENTCOMPANY";
    public static final String THECURRENTBENCHMARK = "THECURRENTBENCHMARK";
    public static final String STARTINGDAY = "startingDay";
    public static final String STARTINGMONTH = "startingMonth";
    public static final String STARTINGYEAR = "startingYear";
    public static final String THEDOWNLOADEDDATA = "theDownloadedData";
    public static final String THEDOWNLOADEDINDEXDATA = "theDownloadedIndexData";
    public static final String ENDINGDAY = "endingDay";
    public static final String ENDINGMONTH = "endingMonth";
    public static final String ENDINGYEAR = "endingYear";

    public static final String INFIMUM_0 = "INFIMUM_0";
    public static final String INFIMUM_1 = "INFIMUM_1";
    public static final String INFIMUM_2 = "INFIMUM_2";

    public UserValues userValues;

    private FrameLayout graphContainer;
    private FrameLayout graphContainer2;
    private boolean request2Frag=false;

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

        //Decide which fragment to load
        putRightFragment();

        //Set the downloading stuff
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(FileService.TRANSACTION_DONE);
        registerReceiver(downloadReceiver, intentFilter);
    }

    @Override
    public void onBackPressed() {
        ChangeFragment();
    }


    // Methods for loading fragments and updating the different views

    private void ChangeFragment(){
        if(theCurrentFragment==1){
            theCurrentFragment=2;
        }else{
            theCurrentFragment=1;
        }
        userValues.setTheCurrentFragment(theCurrentFragment);
        putRightFragment();
    }

    private void putRightFragment(){
        if(theCurrentFragment==1){
            put1stFrag();
        }else{
            put2ndFrag();
        }
    }

    private void put1stFrag(){
        firstFragment = new FirstFragment();
        firstFragment.setArguments(bundlelizer());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, firstFragment)
                .addToBackStack(null)
                .commit();
    }

    private void put2ndFrag(){
        SecondFragment secondFragment = new SecondFragment();
        secondFragment.setArguments(bundlelizer());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, secondFragment)
                .addToBackStack(null)
                .commit();
    }

    private Bundle bundlelizer() {
        //This method puts all the important stuff in a bundle and returns it
        Bundle theBundle = new Bundle();
        theBundle.putSerializable("theStartDateString", theStartDateString);
        theBundle.putSerializable("theEndDateString", theEndDateString);
        theBundle.putSerializable("theCurrentFragment", theCurrentFragment);
        theBundle.putSerializable("theCurrentCompany", theCurrentCompany);
        theBundle.putSerializable("theCurrentBenchmark", theCurrentBenchmark);
        theBundle.putSerializable("startingDay", startingDate.get(0));
        theBundle.putSerializable("startingMonth",startingDate.get(1));
        theBundle.putSerializable("startingYear",startingDate.get(2));
        theBundle.putSerializable("endingDay",endingDate.get(0));
        theBundle.putSerializable("endingMonth", endingDate.get(1));
        theBundle.putSerializable("endingYear", endingDate.get(2));
        theBundle.putSerializable("userValues",userValues);
        return theBundle;
    }

    private void onPriceGraphClicked() {
        if(theCurrentFragment==1) {
            theCurrentFragment = 2;
            userValues.setTheCurrentFragment(theCurrentFragment);
            put2ndFrag();
        }else{
            theCurrentFragment = 1;
            userValues.setTheCurrentFragment(theCurrentFragment);
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


        Log.e("onParametersUpdated", "companySet " + String.valueOf(companySet) +  ",  startDateSet " + String.valueOf(startDateSet) + ";  endDateSet " + String.valueOf(endDateSet) + "  ; callingMethod: " + callingMethod);

        if(companySet&&(companyChanged||datesChanged)&&!benchmarkChanged){
            // if only the company has been selected, add some random dates to speed up the user experience
            if(!startDateSet&&!endDateSet){
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
            if(companySet&&startDateSet&&endDateSet){
                requestCompany=true;
                requestIndex=benchmarkSet;
                startFileService("onParametersUpdated");
            }

        }else if(benchmarkChanged&&companySet&&startDateSet&&endDateSet){
            requestCompany=false;
            requestIndex=true;
            startFileService("onParametersUpdated");
        }


    }



    // Methods for downloading price data and putting it into arrays
    private void startFileService(String callingMethod) {

        Log.e("startFileService","callingMethod: " + String.valueOf(callingMethod));

        if(isInternetAvailable()) {

            String theDay = Integer.toString(startingDate.get(0));
            String theMonth = Integer.toString(startingDate.get(1));
            String theYear = Integer.toString(startingDate.get(2));
            String theEndDay = Integer.toString(endingDate.get(0));
            String theEndMonth = Integer.toString(endingDate.get(1));
            String theEndYear = Integer.toString(endingDate.get(2));

            Intent intent = new Intent(this, FileService.class);
        /*      http://ichart.yahoo.com/table.csv?s=REC.OL&a=0&b=1&c=2000&d=0&e=31&f=2010
                a month0; b day0; c year0;  similarly for the end dates
                for dividends and splits replace date with &g=v     */

            String theURL1stPart = "http://ichart.yahoo.com/table.csv?s=";
            String ticker="";
            if (requestIndex) {
                ticker = theCurrentBenchmark;
            } else if(requestCompany) {
                ticker = theCurrentCompany;
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

            if (requestIndex) {
                theDownloadedIndexData = sb.toString();
                requestIndex = false;
                if(requestCompany) {
                    startFileService("getFileContents");
                }else{
                    dataIntoArrays();
                }
            } else if(requestCompany) {
                theDownloadedData = sb.toString();
                requestCompany=false;
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
        if (!theDownloadedIndexData.equals("")) {
            DataReducer dataReducerIndex = new DataReducer(theDownloadedIndexData);
            arrayListIndex = dataReducerIndex.getTheArray();
            theDatesBenchmark = dataReducerIndex.getTheDates();
        }
        if(!theDownloadedData.equals("")) {
            DataReducer dataReducer= new DataReducer(theDownloadedData);
            arrayList = dataReducer.getTheArray();
            theDates = dataReducer.getTheDates();

            userValues.setArrayList(arrayList);
            userValues.setTheDates(theDates);

            int nItems=theDates.get(0).size()-1;
            int infDay =theDates.get(0).get(nItems);
            int infMonth =theDates.get(1).get(nItems);
            int infYear =theDates.get(2).get(nItems);

            Calendar infimumDate = Calendar.getInstance();
            infimumDate.set(infYear, infMonth-1, infDay);

            Calendar currentMinDate=Calendar.getInstance();
            currentMinDate.set( startingDate.get(2),
                    startingDate.get(1),
                    startingDate.get(0));

            long diff = currentMinDate.getTimeInMillis()-infimumDate.getTimeInMillis();

            int diffDays = (int) TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS);

            if(diffDays<-14){
                setStartingDate(
                        infimumDate.get(Calendar.DAY_OF_MONTH),
                        infimumDate.get(Calendar.MONTH),
                        infimumDate.get(Calendar.YEAR),
                        false);

                startingDateInfimum.set(0,startingDate.get(0));
                startingDateInfimum.set(1,startingDate.get(1));
                startingDateInfimum.set(2,startingDate.get(2));

                firstFragment.setDateInfimum(startingDateInfimum);
            }
        }

        plotStuff();
    }



    // Plot the price data

    private void plotStuff(){
        if(!theDownloadedData.equals("")) {

//            Log.e("plotStuff", "theDownloadedData.length()= " + String.valueOf(theDownloadedData.length()));




            if(theCurrentFragment==2) {

                graphContainer2= (FrameLayout) findViewById(R.id.the_2nd_frag);

                if(!request2Frag) {
                    userValues.setTheCurrentGraph(1);
                }
                Graph graph = new Graph(this);
                graph.newInstance(userValues);
                View theGraph = graph.getTheGraph();
                graphContainer2.addView(theGraph);
                graphContainer2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        request2Frag=false;
                        onPriceGraphClicked();
                    }
                });

            }else{

                graphContainer = (FrameLayout) findViewById(R.id.prices_1st_frag_the_outer_container);

                if(!request2Frag) {
                    userValues.setTheCurrentGraph(1);
                }
                Graph graph = new Graph(this);
                graph.newInstance(userValues);
                View theGraph = graph.getTheGraph();
                graphContainer.addView(theGraph);
                graphContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        request2Frag=true;
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

        userValues.setArrayList(arrayList);

        Strategy strategy = new Strategy(userValues);
        ArrayList<Float> strategyResult=strategy.getTheResult();
        userValues.setStrategyResult(strategyResult);


        FrameLayout strategyContainer =
                (FrameLayout) findViewById(R.id.strategy_1st_frag_the_outer_container);

        userValues.setTheCurrentGraph(2);
        Graph graph = new Graph(this);
        graph.newInstance(userValues);
        View theGraph = graph.getTheGraph();
        strategyContainer.addView(theGraph);

        strategyContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request2Frag=true;
                onPriceGraphClicked();
            }
        });
    }


    //Show juicy stats into the screen

    private void showStats(){

        View outer_container = findViewById(R.id.the_stats);

        if (companySet&&!theDownloadedData.equals("")) {
            outer_container.setVisibility(View.VISIBLE);

            DataCruncher dataCruncher = new DataCruncher(
                    arrayList,
                    arrayListIndex,
                    theDates,
                    theDatesBenchmark);


            DecimalFormat thePrecision = new DecimalFormat("0.00");

            TextView theReturnTextView = (TextView) findViewById(R.id.the_return);
            Double theReturn = dataCruncher.getTheReturn();
            theReturnTextView.setText(thePrecision.format(theReturn));

            Double theAnnualizedReturn = dataCruncher.getTheAnnualizedReturn();
            TextView theAReturnTextView = (TextView) findViewById(R.id.the_a_return);
            theAReturnTextView.setText(thePrecision.format(theAnnualizedReturn));

            LinearLayout theBenchmarkStats = (LinearLayout) findViewById(R.id.the_benchmark_stats);

            if (benchmarkSet && !theDownloadedIndexData.equals("")) {

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
    public void setTheStartDateString(String theStartDateString) {
        this.theStartDateString = theStartDateString;
    }

    public void setTheEndDateString(String theEndDateString) {
        this.theEndDateString = theEndDateString;
    }




    public void setStartingDate(int day_, int month_, int year_, boolean callOnParametersUpdated) {

        ArrayList<Integer> startingDate_ = new ArrayList<Integer>();
        startingDate_.add(0,day_);
        startingDate_.add(1,month_);
        startingDate_.add(2,year_);

        if(startingDate!=startingDate_){
            startingDate = startingDate_;
            startDateSet=true;

            int startingMonth_=startingDate.get(1)+1;
            String dateString = startingDate.get(0)+"/"+startingMonth_+"/"+startingDate.get(2);
            setTheStartDateString(dateString);
            TextView startDateTextView = (TextView) findViewById(R.id.first_frag_start_date_text_view);
            startDateTextView.setText(dateString);

            firstFragment.setStartingDay(startingDate.get(0));
            firstFragment.setStartingMonth(startingDate.get(1));
            firstFragment.setStartingYear(startingDate.get(2));

            if(callOnParametersUpdated) {
                onParametersUpdated(false, false, true, "setStartingDate");
                //boolean companyChanged, boolean benchmarkChanged,boolean datesChanged, String callingMethod
            }
        }

    }

    public void setEndingDate(int day_, int month_, int year_, boolean callOnParametersUpdated) {

        ArrayList<Integer> endingDate_ = new ArrayList<Integer>();
        endingDate_.add(0,day_);
        endingDate_.add(1,month_);
        endingDate_.add(2,year_);

        if(endingDate!=endingDate_){
            endingDate = endingDate_;
            endDateSet=true;

            int endingMonth_=endingDate.get(1)+1;
            String dateString = endingDate.get(0)+"/"+endingMonth_+"/"+endingDate.get(2);
            setTheEndDateString(dateString);
            TextView endDateTextView = (TextView) findViewById(R.id.first_frag_end_date_text_view);
            endDateTextView.setText(dateString);

            if(callOnParametersUpdated){
                onParametersUpdated(false,false,true,"setEndingDate");
                //boolean companyChanged, boolean benchmarkChanged,boolean datesChanged, String callingMethod
            }
        }

    }

    public void setTheCurrentCompany(String theCurrentCompany) {
        this.theCurrentCompany = theCurrentCompany;

        startingDateInfimum.add(0,-1);
        startingDateInfimum.add(1,-1);
        startingDateInfimum.add(2,-1);

        if(theCurrentCompany.equals("")) {
            theDownloadedData="";
            companySet = false;
            requestCompany=false;
            FrameLayout thePriceGraph = (FrameLayout) findViewById(R.id.prices_1st_frag_the_outer_container);
            thePriceGraph.setVisibility(View.GONE);

            FrameLayout theStrategyGraph = (FrameLayout) findViewById(R.id.strategy_1st_frag_the_outer_container);
            theStrategyGraph.setVisibility(View.GONE);

            View outer_container = findViewById(R.id.the_stats);
            outer_container.setVisibility(View.GONE);

/*            startDateSet=false;
            TextView startDate = (TextView) findViewById(R.id.first_frag_start_date_text_view);
            setTheStartDateString("");
            startDate.setText(theStartDateString);
            endDateSet=false;
            TextView endDate = (TextView) findViewById(R.id.first_frag_end_date_text_view);
            setTheEndDateString("");
            endDate.setText(theEndDateString);
            benchmarkSet=false;
            TextView theBenchmark = (TextView) findViewById(R.id.first_frag_benchmark_text_view);
            setTheCurrentBenchmark("");
            theBenchmark.setText(theCurrentBenchmark);
*/
        }else{
            companySet = true;
            requestCompany=true;
            onParametersUpdated(true, false, false, "setTheCurrentCompany");
            //boolean companyChanged, boolean benchmarkChanged,boolean datesChanged, String callingMethod
        }
    }

    public void setTheCurrentBenchmark(String theCurrentBenchmark) {
        this.theCurrentBenchmark = theCurrentBenchmark;
        if(theCurrentBenchmark.equals("")) {
            benchmarkSet = false;
            requestIndex = false;
            theDownloadedIndexData="";
            LinearLayout theBenchmarkStats = (LinearLayout) findViewById(R.id.the_benchmark_stats);
            theBenchmarkStats.setVisibility(View.GONE);
        }else{
            benchmarkSet = true;
            requestIndex = true;
            onParametersUpdated(false,true,false,"setTheCurrentBenchmark");
            //boolean companyChanged, boolean benchmarkChanged,boolean datesChanged, String callingMethod
        }
    }

    public ArrayList<Integer> getStartingDateInfimum() {
        return startingDateInfimum;
    }


    // Method to restore the last values, called from onCreate
    private void restoreSavedValues(){


        userValues = new UserValues(this);

        if(getSharedPreferences(myAppKey,MODE_PRIVATE).getInt(THECURRENTFRAGMENT,0)!=0){
            theCurrentFragment=getSharedPreferences(myAppKey,MODE_PRIVATE).getInt(THECURRENTFRAGMENT, 0);
        }else{
            theCurrentFragment=1;
        }
        userValues.setTheCurrentFragment(theCurrentFragment);



        if(getSharedPreferences(myAppKey,MODE_PRIVATE).getInt("theCurrentGraph",0)!=0){
            userValues.setTheCurrentGraph(getSharedPreferences(myAppKey,MODE_PRIVATE).getInt("theCurrentGraph", 0));
        }else{
            userValues.setTheCurrentGraph(1);
        }

        if(getSharedPreferences(myAppKey,MODE_PRIVATE).contains("request2Frag")){
            request2Frag=getSharedPreferences(myAppKey,MODE_PRIVATE).getBoolean("request2Frag", false);
        }else{
            request2Frag=false;
        }






        if(!getSharedPreferences(myAppKey,MODE_PRIVATE).getString(THESTARTDATESTRING,"").equals("")){
            theStartDateString=getSharedPreferences(myAppKey,MODE_PRIVATE).getString(THESTARTDATESTRING, "");
        }else{
            theStartDateString="";
        }

        if(!getSharedPreferences(myAppKey,MODE_PRIVATE).getString(THEENDDATESTRING,"").equals("")){
            theEndDateString=getSharedPreferences(myAppKey,MODE_PRIVATE).getString(THEENDDATESTRING,"");
        }else{
            theEndDateString="";
        }

        if(!getSharedPreferences(myAppKey,MODE_PRIVATE).getString(THECURRENTCOMPANY,"").equals("")){
            theCurrentCompany=getSharedPreferences(myAppKey,MODE_PRIVATE).getString(THECURRENTCOMPANY, "");
            companySet=true;
        }else{
            theCurrentCompany="";
            companySet=false;
        }

        if(!getSharedPreferences(myAppKey,MODE_PRIVATE).getString(THECURRENTBENCHMARK,"").equals("")){
            theCurrentBenchmark=getSharedPreferences(myAppKey,MODE_PRIVATE).getString(THECURRENTBENCHMARK, "");
            benchmarkSet=true;
        }else{
            theCurrentBenchmark="";
            benchmarkSet=false;
        }

        if(!getSharedPreferences(myAppKey,MODE_PRIVATE).getString(THEDOWNLOADEDDATA,"").equals("")){
            theDownloadedData=getSharedPreferences(myAppKey,MODE_PRIVATE).getString(THEDOWNLOADEDDATA, "");
        }else{
            theDownloadedData="";
        }

        if(!getSharedPreferences(myAppKey,MODE_PRIVATE).getString(THEDOWNLOADEDINDEXDATA,"").equals("")){
            theDownloadedIndexData=getSharedPreferences(myAppKey,MODE_PRIVATE).getString(THEDOWNLOADEDINDEXDATA, "");
        }else{
            theDownloadedIndexData="";
        }






        if(getSharedPreferences(myAppKey,MODE_PRIVATE).contains(STARTINGDAY)){
            startingDate.add(0,getSharedPreferences(myAppKey,MODE_PRIVATE).getInt(STARTINGDAY, -1));
        }else{
            startingDate.add(0,-1);//calendar.get(Calendar.DAY_OF_MONTH);
        }
        if(getSharedPreferences(myAppKey,MODE_PRIVATE).contains(STARTINGMONTH)){
            startingDate.add(1,getSharedPreferences(myAppKey,MODE_PRIVATE).getInt(STARTINGMONTH, -1));
        }else{
            startingDate.add(1,-1);//calendar.get(Calendar.MONTH);
        }
        if(getSharedPreferences(myAppKey,MODE_PRIVATE).contains(STARTINGYEAR)){
            startingDate.add(2,getSharedPreferences(myAppKey,MODE_PRIVATE).getInt(STARTINGYEAR, -1));
            startDateSet=true;
        }else{
            startingDate.add(2,-1);//calendar.get(Calendar.YEAR);
            startDateSet=false;
        }

        if(getSharedPreferences(myAppKey,MODE_PRIVATE).contains(INFIMUM_0)){
            startingDateInfimum.add(0,getSharedPreferences(myAppKey,MODE_PRIVATE).getInt(INFIMUM_0, -1));
        }else{
            startingDateInfimum.add(0,-1);
        }
        if(getSharedPreferences(myAppKey,MODE_PRIVATE).contains(INFIMUM_1)){
            startingDateInfimum.add(1,getSharedPreferences(myAppKey,MODE_PRIVATE).getInt(INFIMUM_1, -1));
        }else{
            startingDateInfimum.add(1,-1);
        }
        if(getSharedPreferences(myAppKey,MODE_PRIVATE).contains(INFIMUM_2)){
            startingDateInfimum.add(2,getSharedPreferences(myAppKey,MODE_PRIVATE).getInt(INFIMUM_2, -1));
        }else{
            startingDateInfimum.add(2,-1);
        }



        if(getSharedPreferences(myAppKey,MODE_PRIVATE).contains(ENDINGDAY)){
            endingDate.add(0,getSharedPreferences(myAppKey,MODE_PRIVATE).getInt(ENDINGDAY, -1));
        }else{
            endingDate.add(0,-1);//calendar.get(Calendar.DAY_OF_MONTH);
        }
        if(getSharedPreferences(myAppKey,MODE_PRIVATE).contains(ENDINGMONTH)){
            endingDate.add(1,getSharedPreferences(myAppKey,MODE_PRIVATE).getInt(ENDINGMONTH, -1));
        }else{
            endingDate.add(1,-1);//calendar.get(Calendar.MONTH);
        }
        if(getSharedPreferences(myAppKey,MODE_PRIVATE).contains(ENDINGYEAR)){
            endingDate.add(2,getSharedPreferences(myAppKey,MODE_PRIVATE).getInt(ENDINGYEAR, -1));
            endDateSet=true;
        }else{
            endingDate.add(2,-1);//calendar.get(Calendar.YEAR);
            endDateSet=false;
        }



    }


    //Check internet availability, called from startFileService
    public boolean isInternetAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    //Save the key variables before dying
    @Override
    public void onSaveInstanceState(Bundle outState) {

        getSharedPreferences(myAppKey, MODE_PRIVATE).edit().putInt(STARTINGDAY, startingDate.get(0)).commit();
        getSharedPreferences(myAppKey, MODE_PRIVATE).edit().putInt(STARTINGMONTH, startingDate.get(1)).commit();
        getSharedPreferences(myAppKey, MODE_PRIVATE).edit().putInt(STARTINGYEAR, startingDate.get(2)).commit();
        getSharedPreferences(myAppKey, MODE_PRIVATE).edit().putInt(ENDINGDAY, endingDate.get(0)).commit();
        getSharedPreferences(myAppKey, MODE_PRIVATE).edit().putInt(ENDINGMONTH, endingDate.get(1)).commit();
        getSharedPreferences(myAppKey, MODE_PRIVATE).edit().putInt(ENDINGYEAR, endingDate.get(2)).commit();

        getSharedPreferences(myAppKey, MODE_PRIVATE).edit().putInt(INFIMUM_0, startingDateInfimum.get(0)).commit();
        getSharedPreferences(myAppKey, MODE_PRIVATE).edit().putInt(INFIMUM_1, startingDateInfimum.get(1)).commit();
        getSharedPreferences(myAppKey, MODE_PRIVATE).edit().putInt(INFIMUM_2, startingDateInfimum.get(2)).commit();

        getSharedPreferences(myAppKey, MODE_PRIVATE).edit().putInt(THECURRENTFRAGMENT, theCurrentFragment).commit();
        getSharedPreferences(myAppKey, MODE_PRIVATE).edit().putString(THESTARTDATESTRING, theStartDateString).commit();
        getSharedPreferences(myAppKey, MODE_PRIVATE).edit().putString(THEENDDATESTRING, theEndDateString).commit();

        getSharedPreferences(myAppKey, MODE_PRIVATE).edit().putString(THECURRENTCOMPANY, theCurrentCompany).commit();
        getSharedPreferences(myAppKey, MODE_PRIVATE).edit().putString(THECURRENTBENCHMARK, theCurrentBenchmark).commit();

        getSharedPreferences(myAppKey, MODE_PRIVATE).edit().putString(THEDOWNLOADEDDATA, theDownloadedData).commit();
        getSharedPreferences(myAppKey, MODE_PRIVATE).edit().putString(THEDOWNLOADEDINDEXDATA, theDownloadedIndexData).commit();


        getSharedPreferences(myAppKey, MODE_PRIVATE).edit().putBoolean("request2Frag", request2Frag).commit();
        getSharedPreferences(myAppKey, MODE_PRIVATE).edit().putInt("theCurrentGraph", userValues.getTheCurrentGraph()).commit();


        super.onSaveInstanceState(outState);
    }



}

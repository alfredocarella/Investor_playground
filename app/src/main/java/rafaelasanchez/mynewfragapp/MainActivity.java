package rafaelasanchez.mynewfragapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.NoSuchElementException;


public class MainActivity extends AppCompatActivity {


    private int theCurrentFragment;
    private int startingDay;
    private int startingMonth;
    private int startingYear;
    private int endingDay;
    private int endingMonth;
    private int endingYear;

    private boolean requestIndex=false;
    private boolean requestCompany=false;
    private boolean startDateSet=false;
    private boolean endDateSet=false;
    private boolean companySet=false;
    private boolean benchmarkSet=false;

    private String theString;
    private String theEndDateString;
    private String theCurrentCompany;
    private String theCurrentBenchmark;
    private String theDownloadedData;
    private String theDownloadedIndexData;

    private ArrayList<Float> arrayList = new ArrayList<Float>();
    private ArrayList<Float> arrayListIndex = new ArrayList<Float>();
    private ArrayList<ArrayList<Integer>> theDates=new ArrayList<ArrayList<Integer>>();
    private ArrayList<ArrayList<Integer>> theDatesBenchmark=new ArrayList<ArrayList<Integer>>();



    public static final String RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND = "Investor Playground";
    public static final String THECURRENTFRAGMENT = "theCurrentFragment";
    public static final String THE_STRING = "THE_STRING";
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

    private FrameLayout graphContainer;
    private FrameLayout graphContainer2;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bar_calc:
                FragmentManager fragmentManager = getSupportFragmentManager();
                ReturnRateDialogFragment returnRateDialogFragment = ReturnRateDialogFragment.newInstance();
                returnRateDialogFragment.show(fragmentManager,"");
                return true;
            default:
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
        FirstFragment firstFragment = new FirstFragment();
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
        theBundle.putSerializable("theString", theString);
        theBundle.putSerializable("theEndDateString", theEndDateString);
        theBundle.putSerializable("theCurrentFragment", theCurrentFragment);
        theBundle.putSerializable("theCurrentCompany", theCurrentCompany);
        theBundle.putSerializable("theCurrentBenchmark", theCurrentBenchmark);
        theBundle.putSerializable("startingDay", startingDay);
        theBundle.putSerializable("startingMonth",startingMonth);
        theBundle.putSerializable("startingYear",startingYear);
        theBundle.putSerializable("endingDay",endingDay);
        theBundle.putSerializable("endingMonth", endingMonth);
        theBundle.putSerializable("endingYear", endingYear);
        return theBundle;
    }

    private void onPriceGraphClicked() {
        if(theCurrentFragment==1) {
            theCurrentFragment = 2;
            put2ndFrag();
        }else{
            theCurrentFragment = 1;
            put1stFrag();
        }
    }


    public void onNewGraph(){

        dataIntoArrays();

    }


    private void onParametersUpdated(boolean companyChanged, boolean benchmarkChanged,
                                     boolean datesChanged){

   /*     Log.e("onParametersUpdated","startDateSet "+ String.valueOf(startDateSet));
        Log.e("onParametersUpdated","endDateSet "+ String.valueOf(endDateSet));
        Log.e("onParametersUpdated","companySet "+ String.valueOf(companySet));
        Log.e("onParametersUpdated", "benchmarkSet " + String.valueOf(benchmarkSet));
        Log.e("onParametersUpdated", "requestIndex " + String.valueOf(requestIndex));
        Log.e("onParametersUpdated", "requestCompany " + String.valueOf(requestCompany));
*/
        if((datesChanged&&companySet&&startDateSet&&endDateSet)
                ||(companyChanged&&startDateSet&&endDateSet)){
            requestCompany=true;
            requestIndex=benchmarkSet;
            startFileService("onParametersUpdated");
        }else if(benchmarkChanged&&companySet&&startDateSet&&endDateSet){
            Log.e("onParametersUpdated","benchmarkChanged");
            requestCompany=false;
            requestIndex=true;
            startFileService("onParametersUpdated");
        }

    }



    // Methods for downloading price data and putting it into arrays
    private void startFileService(String callingMethod) {

        Log.e("startFileService","callingMethod: " + String.valueOf(callingMethod));

        if(isInternetAvailable()) {

            String theDay = Integer.toString(startingDay);
            String theMonth = Integer.toString(startingMonth);
            String theYear = Integer.toString(startingYear);
            String theEndDay = Integer.toString(endingDay);
            String theEndMonth = Integer.toString(endingMonth);
            String theEndYear = Integer.toString(endingYear);

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
        }
        plotStuff();
    }



    // Plot the price data

    private void plotStuff(){
        if(!theDownloadedData.equals("")) {

            Log.e("plotStuff", "theDownloadedData.length()= " + String.valueOf(theDownloadedData.length()));

            double minDouble=-1;
            double maxDouble=0;
            try{
                minDouble= Collections.min(arrayList).doubleValue();
                maxDouble= Collections.max(arrayList).doubleValue();
            }catch(NoSuchElementException e) {
                e.printStackTrace();
            }

            DecimalFormat precision = new DecimalFormat("0.00E0");

            TextView startingTV;
            TextView endingTV;
            TextView minTV;
            TextView maxTV;
            String endingTVtext = theDates.get(0).get(0).toString() + "/" + theDates.get(1).get(0).toString() + "/" + theDates.get(2).get(0).toString();
            String startingTVtext = theDates.get(0).get(theDates.get(0).size() - 1).toString() + "/" + theDates.get(1).get(theDates.get(1).size() - 1).toString() + "/" + theDates.get(2).get(theDates.get(2).size() - 1).toString();



            if(theCurrentFragment==2) {

                startingTV = (TextView) findViewById(R.id.axis_start_date);
                endingTV = (TextView) findViewById(R.id.axis_end_date);
                minTV = (TextView) findViewById(R.id.axis_min);
                maxTV = (TextView) findViewById(R.id.axis_max);

                endingTV.setText(endingTVtext);
                startingTV.setText(startingTVtext);

                minTV.setText(precision.format(minDouble));
                maxTV.setText(precision.format(maxDouble));

                graphContainer2= (FrameLayout) findViewById(R.id.graficaContainer);
                graphContainer2.setVisibility(View.VISIBLE);
                graphContainer2.removeAllViews();

                ViewTreeObserver vto2 = graphContainer2.getViewTreeObserver();
                vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                            graphContainer2.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        } else {
                            graphContainer2.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        }

                        int dxplot = graphContainer2.getWidth();
                        int dyplot = graphContainer2.getHeight();


                        LinePlot linePlot = new LinePlot(getApplicationContext());

                        linePlot.addPoints(dxplot, dyplot, arrayList);
                        graphContainer2.addView(linePlot);
                        graphContainer2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onPriceGraphClicked();
                            }
                        });

                    }
                });

            }else{
                LinearLayout theOuterContainer = (LinearLayout) findViewById(R.id.the_outer_container);
                theOuterContainer.setVisibility(View.VISIBLE);

                startingTV = (TextView) findViewById(R.id.axis_start_date_1stFrag);
                endingTV = (TextView) findViewById(R.id.axis_end_date_1stFrag);
                minTV = (TextView) findViewById(R.id.axis_min_1stFrag);
                maxTV = (TextView) findViewById(R.id.axis_max_1stFrag);

                endingTV.setText(endingTVtext);
                startingTV.setText(startingTVtext);

                minTV.setText(precision.format(minDouble));
                maxTV.setText(precision.format(maxDouble));

                graphContainer = (FrameLayout) findViewById(R.id.graficaContainer_1stFrag);
                graphContainer.setVisibility(View.VISIBLE);
                graphContainer.removeAllViews();

                ViewTreeObserver vto = graphContainer.getViewTreeObserver();
                vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                            graphContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        } else {
                            graphContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        }

                        int dxplot = graphContainer.getWidth();

                        ViewGroup.LayoutParams params = graphContainer.getLayoutParams();
                        DisplayMetrics metrics = new DisplayMetrics();
                        getWindowManager().getDefaultDisplay().getMetrics(metrics);
                        params.height = Math.round(Math.min((float) 0.35 * metrics.heightPixels, (float) 0.5 * metrics.widthPixels));
                        graphContainer.setLayoutParams(params);

                        LinePlot linePlot = new LinePlot(getApplicationContext());
                        int dyplot = params.height;

                        linePlot.addPoints(dxplot, dyplot, arrayList);
                        graphContainer.addView(linePlot);
                        graphContainer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onPriceGraphClicked();
                            }
                        });

                        showStats();

                    }
                });

            }

        }
    }


    //Show juicy stats into the screen

    private void showStats(){

        Log.e("showStats", "method run");
        View outer_container = findViewById(R.id.the_stats);

        if (companySet&&!theDownloadedData.equals("")) {
            outer_container.setVisibility(View.VISIBLE);
            Log.e("showStats", "companySet true");

            DataCruncher dataCruncher = new DataCruncher(
                    arrayList,
                    arrayListIndex,
                    theDates,
                    theDatesBenchmark);

            Log.e("data cruncher printout"," "+String.valueOf(dataCruncher));

            DecimalFormat thePrecision = new DecimalFormat("0.00");

            TextView theReturnTextView = (TextView) findViewById(R.id.the_return);
            Double theReturn = dataCruncher.getTheReturn();
            theReturnTextView.setText(thePrecision.format(theReturn));

            Double theAnnualizedReturn = dataCruncher.getTheAnnualizedReturn();
            TextView theAReturnTextView = (TextView) findViewById(R.id.the_a_return);
            theAReturnTextView.setText(thePrecision.format(theAnnualizedReturn));

            LinearLayout theBenchmarkStats = (LinearLayout) findViewById(R.id.the_benchmark_stats);

            if (benchmarkSet && !theDownloadedIndexData.equals("")) {
                Log.e("showStats", "benchmarkSet && !requestIndex = true");

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
    public void setTheString(String theString) {
        this.theString = theString;
    }

    public void setTheEndDateString(String theEndDateString) {
        this.theEndDateString = theEndDateString;
    }




    public void setStartingDate(int startingDay_,int startingMonth_,int startingYear_) {

        if(startingDay!=startingDay_||startingMonth!=startingMonth_||startingYear!=startingYear_){
            startingDay = startingDay_;
            startingMonth = startingMonth_;
            startingYear = startingYear_;
            startDateSet=true;
            requestCompany = true;
            requestIndex=true;
            onParametersUpdated(false,false,true);
            //boolean companyChanged, boolean benchmarkChanged,boolean datesChanged
        }

    }

    public void setEndingDate(int endingDay_, int endingMonth_, int endingYear_) {

        if(endingDay!=endingDay_||endingMonth!=endingMonth_||endingYear!=endingYear_){
            endingDay = endingDay_;
            endingMonth = endingMonth_;
            endingYear = endingYear_;
            endDateSet=true;
            requestCompany = true;
            requestIndex=true;
            onParametersUpdated(false,false,true);
            //boolean companyChanged, boolean benchmarkChanged,boolean datesChanged
        }

    }


    public void setTheCurrentCompany(String theCurrentCompany) {
        this.theCurrentCompany = theCurrentCompany;
        if(theCurrentCompany.equals("")) {
            theDownloadedData="";
            companySet = false;
            requestCompany=false;
            LinearLayout thePriceGraph = (LinearLayout) findViewById(R.id.the_outer_container);
            thePriceGraph.setVisibility(View.GONE);
            View outer_container = findViewById(R.id.the_stats);
            outer_container.setVisibility(View.GONE);
        }else{
            companySet = true;
            requestCompany=true;
            onParametersUpdated(true,false,false);
            //boolean companyChanged, boolean benchmarkChanged,boolean datesChanged
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
            onParametersUpdated(false,true,false);
            //boolean companyChanged, boolean benchmarkChanged,boolean datesChanged
        }
    }




    // Method to restore the last values, called from onCreate
    private void restoreSavedValues(){


        if(getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).getInt(THECURRENTFRAGMENT,0)!=0){
            theCurrentFragment=getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).getInt(THECURRENTFRAGMENT, 0);
        }else{
            theCurrentFragment=1;
        }

        if(!getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).getString(THE_STRING,"").equals("")){
            theString=getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).getString(THE_STRING, "");
        }else{
            theString="";
        }

        if(!getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).getString(THEENDDATESTRING,"").equals("")){
            theEndDateString=getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).getString(THEENDDATESTRING,"");
        }else{
            theEndDateString="";
        }

        if(!getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).getString(THECURRENTCOMPANY,"").equals("")){
            theCurrentCompany=getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).getString(THECURRENTCOMPANY, "");
            companySet=true;
        }else{
            theCurrentCompany="";
            companySet=false;
        }

        if(!getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).getString(THECURRENTBENCHMARK,"").equals("")){
            theCurrentBenchmark=getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).getString(THECURRENTBENCHMARK, "");
            benchmarkSet=true;
        }else{
            theCurrentBenchmark="";
            benchmarkSet=false;
        }

        if(!getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).getString(THEDOWNLOADEDDATA,"").equals("")){
            theDownloadedData=getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).getString(THEDOWNLOADEDDATA, "");
        }else{
            theDownloadedData="";
        }

        if(!getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).getString(THEDOWNLOADEDINDEXDATA,"").equals("")){
            theDownloadedIndexData=getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).getString(THEDOWNLOADEDINDEXDATA, "");
        }else{
            theDownloadedIndexData="";
        }






        if(getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).contains(STARTINGDAY)){
            startingDay=getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).getInt(STARTINGDAY, -1);
        }else{
            startingDay=-1;//calendar.get(Calendar.DAY_OF_MONTH);
        }
        if(getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).contains(STARTINGMONTH)){
            startingMonth=getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).getInt(STARTINGMONTH, -1);
        }else{
            startingMonth=-1;//calendar.get(Calendar.MONTH);
        }
        if(getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).contains(STARTINGYEAR)){
            startingYear=getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).getInt(STARTINGYEAR, -1);
            startDateSet=true;
        }else{
            startingYear=-1;//calendar.get(Calendar.YEAR);
            startDateSet=false;
        }

        if(getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).contains(ENDINGDAY)){
            endingDay=getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).getInt(ENDINGDAY, -1);
        }else{
            endingDay=-1;//calendar.get(Calendar.DAY_OF_MONTH);
        }
        if(getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).contains(ENDINGMONTH)){
            endingMonth=getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).getInt(ENDINGMONTH, -1);
        }else{
            endingMonth=-1;//calendar.get(Calendar.MONTH);
        }
        if(getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).contains(ENDINGYEAR)){
            endingYear=getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).getInt(ENDINGYEAR, -1);
            endDateSet=true;
        }else{
            endingYear=-1;//calendar.get(Calendar.YEAR);
            endDateSet=false;
        }

    }


    //Check internet availability, called from startFileService
    public boolean isInternetAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        Log.e("Connected to internet?", String.valueOf(netInfo != null && netInfo.isConnectedOrConnecting()));
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    //Save the key variables before dying
    @Override
    public void onSaveInstanceState(Bundle outState) {

        getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND, MODE_PRIVATE).edit().putInt(STARTINGDAY, startingDay).commit();
        getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND, MODE_PRIVATE).edit().putInt(STARTINGMONTH, startingMonth).commit();
        getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND, MODE_PRIVATE).edit().putInt(STARTINGYEAR, startingYear).commit();
        getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND, MODE_PRIVATE).edit().putInt(ENDINGDAY, endingDay).commit();
        getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND, MODE_PRIVATE).edit().putInt(ENDINGMONTH, endingMonth).commit();
        getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND, MODE_PRIVATE).edit().putInt(ENDINGYEAR, endingYear).commit();

        getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND, MODE_PRIVATE).edit().putInt(THECURRENTFRAGMENT, theCurrentFragment).commit();
        getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND, MODE_PRIVATE).edit().putString(THE_STRING, theString).commit();
        getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND, MODE_PRIVATE).edit().putString(THEENDDATESTRING, theEndDateString).commit();

        getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND, MODE_PRIVATE).edit().putString(THECURRENTCOMPANY, theCurrentCompany).commit();
        getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND, MODE_PRIVATE).edit().putString(THECURRENTBENCHMARK, theCurrentBenchmark).commit();

        getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND, MODE_PRIVATE).edit().putString(THEDOWNLOADEDDATA, theDownloadedData).commit();
        getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND, MODE_PRIVATE).edit().putString(THEDOWNLOADEDINDEXDATA, theDownloadedIndexData).commit();

        super.onSaveInstanceState(outState);
    }



}

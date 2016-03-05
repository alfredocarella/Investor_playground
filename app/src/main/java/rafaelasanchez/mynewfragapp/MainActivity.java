package rafaelasanchez.mynewfragapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {


    private String theString;
    private String theEndDateString;
    private String theCurrentCompany;
    int theCurrentFragment;
    private int startingDay;
    private int startingMonth;
    private int startingYear;
    private int endingDay;
    private int endingMonth;
    private int endingYear;
    private Float mmin;
    private Float mmax;

    private String theDownloadedData;
    private boolean startDateSet=false;
    private boolean endDateSet=false;
    private boolean companySet=false;
    private ArrayList<Float> arrayList = new ArrayList<Float>();
    private ArrayList<Integer> theDays = new ArrayList<Integer>();
    private ArrayList<Integer> theMonths = new ArrayList<Integer>();
    private ArrayList<Integer> theYears = new ArrayList<Integer>();

    public static final String RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND = "Investor Playground";
    public static final String THECURRENTFRAGMENT = "theCurrentFragment";
    public static final String THE_STRING = "THE_STRING";
    public static final String THEENDDATESTRING = "THEENDDATESTRING";
    public static final String THECURRENTCOMPANY = "THECURRENTCOMPANY";
    public static final String STARTINGDAY = "startingDay";
    public static final String STARTINGMONTH = "startingMonth";
    public static final String STARTINGYEAR = "startingYear";
    public static final String THEDOWNLOADEDDATA = "theDownloadedData";
    public static final String ENDINGDAY = "endingDay";
    public static final String ENDINGMONTH = "endingMonth";
    public static final String ENDINGYEAR = "endingYear";


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
        inflater.inflate(R.menu.menu_bar,menu);
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.activity_main);
        Log.e("MA.onCreate", "Run");

        restoreSavedValues(savedInstanceState);

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
        theBundle.putSerializable("theDownloadedData", theDownloadedData);
        theBundle.putSerializable("startingDay", startingDay);
        theBundle.putSerializable("startingMonth",startingMonth);
        theBundle.putSerializable("startingYear",startingYear);
        theBundle.putSerializable("endingDay",endingDay);
        theBundle.putSerializable("endingMonth", endingMonth);
        theBundle.putSerializable("endingYear", endingYear);
        return theBundle;
    }


    public void onPriceGraphClicked() {

        if(theCurrentFragment==1) {
            theCurrentFragment = 2;
            put2ndFrag();
        }else{
            theCurrentFragment = 1;
            put1stFrag();
        }

    }



    public void onParametersUpdated(){

        if(theCurrentFragment==1&&startDateSet&&endDateSet&&companySet){
            startFileService();
        }

    }


    public void onSecondFragmentCreated(){

            startFileService();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.e("MA.onSaveInstanceState", "Run");

        outState.putInt("theCurrentFragment", theCurrentFragment);
        getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND, MODE_PRIVATE).edit().putInt(THECURRENTFRAGMENT, theCurrentFragment).commit();

        getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND, MODE_PRIVATE).edit().putInt(STARTINGDAY, startingDay).commit();
        getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND, MODE_PRIVATE).edit().putInt(STARTINGMONTH, startingMonth).commit();
        getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND, MODE_PRIVATE).edit().putInt(STARTINGYEAR, startingYear).commit();
        getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND, MODE_PRIVATE).edit().putInt(ENDINGDAY, endingDay).commit();
        getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND, MODE_PRIVATE).edit().putInt(ENDINGMONTH, endingMonth).commit();
        getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND, MODE_PRIVATE).edit().putInt(ENDINGYEAR, endingYear).commit();

        getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND, MODE_PRIVATE).edit().putString(THE_STRING, theString).commit();
        getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND, MODE_PRIVATE).edit().putString(THEENDDATESTRING, theEndDateString).commit();
        getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND, MODE_PRIVATE).edit().putString(THECURRENTCOMPANY, theCurrentCompany).commit();
        getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND, MODE_PRIVATE).edit().putString(THEDOWNLOADEDDATA, theDownloadedData).commit();


        super.onSaveInstanceState(outState);
    }



    // Methods for downloading stuff

    public void startFileService() {

        String theDay = Integer.toString(startingDay);
        String theMonth = Integer.toString(startingMonth);
        String theYear = Integer.toString(startingYear);
        String theEndDay = Integer.toString(endingDay);
        String theEndMonth = Integer.toString(endingMonth);
        String theEndYear = Integer.toString(endingYear);

        Intent intent = new Intent(this,FileService.class);
        /*      http://ichart.yahoo.com/table.csv?s=REC.OL&a=0&b=1&c=2000&d=0&e=31&f=2010
                a month0; b day0; c year0; rest the same but final
                dividends splits replace date with &g=v     */
        String theURL1stPart = "http://ichart.yahoo.com/table.csv?s=";
        String theURLcomplete = theURL1stPart + theCurrentCompany
                + ".OL&a=" + theMonth + "&b=" + theDay + "&c=" + theYear
                +"&d=" + theEndMonth + "&e=" + theEndDay + "&f=" + theEndYear;
        Log.e("theURLcomplete", theURLcomplete);
        intent.putExtra("url", theURLcomplete);
        this.startService(intent);
    }

    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("FileService","Service Received");
            showFileContents();
        }
    };

    public void showFileContents(){
        StringBuilder sb;
        try {
            FileInputStream fis = this.openFileInput("myFile");
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            sb = new StringBuilder();
            String line;

            while ((line=bufferedReader.readLine()) !=null){
                sb.append(line).append('\n');
            }

            theDownloadedData=sb.toString();

            plotStuff(theDownloadedData);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // setters and getters


    public void setTheEndDateString(String theEndDateString) {
        this.theEndDateString = theEndDateString;
    }

    public void setEndingDay(int endingDay) {
        this.endingDay = endingDay;
    }

    public void setEndingMonth(int endingMonth) {
        this.endingMonth = endingMonth;
    }

    public void setEndingYear(int endingYear) {
        this.endingYear = endingYear;
        endDateSet=true;
        onParametersUpdated();
    }

    public void setStartingDay(int startingDay) {
        this.startingDay = startingDay;
    }

    public void setStartingMonth(int startingMonth) {
        this.startingMonth = startingMonth;
    }

    public void setStartingYear(int startingYear) {
        this.startingYear = startingYear;
        startDateSet=true;
        onParametersUpdated();
    }

    public void setTheString(String theString) {
        this.theString = theString;
    }

    public void setTheCurrentCompany(String theCurrentCompany) {
        this.theCurrentCompany = theCurrentCompany;
        companySet=true;
        onParametersUpdated();
    }






    // Method to restore old values, called from onCreate
    private void restoreSavedValues(Bundle savedInstanceState){


        if(getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).getInt(THECURRENTFRAGMENT,0)!=0){
            theCurrentFragment=getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).getInt(THECURRENTFRAGMENT, 0);
        }else{
            theCurrentFragment=1;
        }

        if(!getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).getString(THE_STRING,"").equals("")){
            theString=getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).getString(THE_STRING,"");
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

        if(!getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).getString(THEDOWNLOADEDDATA,"").equals("")){
            theDownloadedData=getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).getString(THEDOWNLOADEDDATA, "");
        }else{
            theDownloadedData="";
        }


        Calendar calendar = Calendar.getInstance();

        if(getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).contains(STARTINGDAY)){
            startingDay=getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).getInt(STARTINGDAY, -1);
        }else{
            startingDay=calendar.get(Calendar.DAY_OF_MONTH);
        }
        if(getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).contains(STARTINGMONTH)){
            startingMonth=getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).getInt(STARTINGMONTH, -1);
        }else{
            startingMonth=calendar.get(Calendar.MONTH);
        }
        if(getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).contains(STARTINGYEAR)){
            startingYear=getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).getInt(STARTINGYEAR, -1);
            startDateSet=true;
        }else{
            startingYear=calendar.get(Calendar.YEAR);
            startDateSet=false;
        }

        if(getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).contains(ENDINGDAY)){
            endingDay=getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).getInt(ENDINGDAY, -1);
        }else{
            endingDay=calendar.get(Calendar.DAY_OF_MONTH);
        }
        if(getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).contains(ENDINGMONTH)){
            endingMonth=getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).getInt(ENDINGMONTH, -1);
        }else{
            endingMonth=calendar.get(Calendar.MONTH);
        }
        if(getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).contains(ENDINGYEAR)){
            endingYear=getSharedPreferences(RAFAANTOSANCHEZ_INVESTOR_PLAYGROUND,MODE_PRIVATE).getInt(ENDINGYEAR, -1);
            endDateSet=true;
        }else{
            endingYear=calendar.get(Calendar.YEAR);
            endDateSet=false;
        }


    }


    private void plotStuff(String theDownloadedData){
        if(!theDownloadedData.equals("")) {
            DataReducer dataReducer = new DataReducer(theDownloadedData);
            arrayList = dataReducer.getTheArray();
            theDays = dataReducer.getTheDays();
            theMonths = dataReducer.getTheMonths();
            theYears = dataReducer.getTheYears();
            mmin = dataReducer.getMmin();
            mmax = dataReducer.getMmax();

            Log.e("mmin",mmin.toString());
            Log.e("mmax", mmax.toString());







            TextView startingTV;
            TextView endingTV;
            TextView minTV;
            TextView maxTV;

            if(theCurrentFragment==2) {
                startingTV = (TextView) findViewById(R.id.axis_start_date);
                endingTV = (TextView) findViewById(R.id.axis_end_date);
                minTV = (TextView) findViewById(R.id.axis_min);
                maxTV = (TextView) findViewById(R.id.axis_max);
            }else{
                startingTV = (TextView) findViewById(R.id.axis_start_date_1stFrag);
                endingTV = (TextView) findViewById(R.id.axis_end_date_1stFrag);
                minTV = (TextView) findViewById(R.id.axis_min_1stFrag);
                maxTV = (TextView) findViewById(R.id.axis_max_1stFrag);
            }

            double minDouble= mmin.doubleValue();
            double maxDouble= mmax.doubleValue();
            DecimalFormat precision = new DecimalFormat("0.00E0");
            minTV.setText(precision.format(minDouble));
            maxTV.setText(precision.format(maxDouble));

            String endingTVtext = theDays.get(0).toString() + "/" + theMonths.get(0).toString() + "/" + theYears.get(0).toString();
            endingTV.setText(endingTVtext);
            String startingTVtext = theDays.get(theDays.size() - 1).toString() + "/" + theMonths.get(theMonths.size() - 1).toString() + "/" + theYears.get(theYears.size() - 1).toString();
            startingTV.setText(startingTVtext);




            FrameLayout graphContainer;
            if(theCurrentFragment==2) {
                graphContainer = (FrameLayout) findViewById(R.id.graficaContainer);
            }else{
                graphContainer = (FrameLayout) findViewById(R.id.graficaContainer_1stFrag);
            }
            graphContainer.setBackgroundResource(R.drawable.back);

            graphContainer.removeAllViews();

            int dxplot = graphContainer.getWidth();
            Log.e("MA.plotStuff.Width()", String.valueOf(dxplot));

            int dyplot = graphContainer.getHeight();
            Log.e("MA.plotStuff.Height()", String.valueOf(dyplot));
            LinePlot linePlot = new LinePlot(this, dxplot, dyplot, arrayList);

            graphContainer.addView(linePlot);

            graphContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onPriceGraphClicked();
                }
            });


            // Add the stats stuff



        }


    }

}

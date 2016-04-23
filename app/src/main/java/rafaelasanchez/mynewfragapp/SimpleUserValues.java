package rafaelasanchez.mynewfragapp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by R on 21/04/2016.
 */
public class SimpleUserValues implements Serializable {

    private Boolean benchmarkSet=null;
    private Boolean companySet=null;
    private Boolean endDateSet=null;
    private Boolean request2Frag=null;
    private Boolean requestCompany=null;
    private Boolean requestIndex=null;
    private Boolean startDateSet=null;
    private Integer theCurrentFragment=null;
    private Integer theCurrentGraph=null;
    private String theCurrentBenchmark="";
    private String theCurrentCompany="";
    private String theDownloadedData="";
    private String theDownloadedIndexData="";
    private String theEndDateString="";
    private String theStartDateString="";
    public static final String myAppKey = "Investor Playground";
    private ArrayList<Boolean> theBooleans=null;
    private ArrayList<Integer> theIntegers=null;
    private ArrayList<Integer> endingDate=null;
    private ArrayList<Integer> startingDate=null;
    private ArrayList<Integer> startingDateInfimum=null;
    private ArrayList<Float> arrayList=null;
    private ArrayList<Float> arrayListIndex = null;
    private ArrayList<Float> strategyResult=null;
    private ArrayList<ArrayList<Integer>> theDates=null;
    private ArrayList<ArrayList<Integer>> theDatesBenchmark=null;


    public SimpleUserValues() {
    }

    public static SimpleUserValues newInstance(){
        return new SimpleUserValues();
    }





    //All getters & setters

    //Boolean

    public Boolean getBenchmarkSet() {
        if(benchmarkSet==null){
            benchmarkSet=false;
        }
        return benchmarkSet;
    }

    public void setBenchmarkSet(Boolean benchmarkSet) {
        this.benchmarkSet = benchmarkSet;
    }

    public Boolean getCompanySet() {
        if(companySet==null){
            companySet=false;
        }
        return companySet;
    }

    public void setCompanySet(Boolean companySet) {
        this.companySet = companySet;
    }

    public Boolean getEndDateSet() {
        if(endDateSet==null){
            endDateSet=false;
        }
        return endDateSet;
    }

    public void setEndDateSet(Boolean endDateSet) {
        this.endDateSet = endDateSet;
    }

    public Boolean getRequest2Frag() {
        if(request2Frag==null){
            request2Frag=false;
        }
        return request2Frag;
    }

    public void setRequest2Frag(Boolean request2Frag) {
        this.request2Frag = request2Frag;
    }

    public Boolean getRequestCompany() {
        if(requestCompany==null){
            requestCompany=false;
        }
        return requestCompany;
    }

    public void setRequestCompany(Boolean requestCompany) {
        this.requestCompany = requestCompany;
    }

    public Boolean getRequestIndex() {
        if(requestIndex==null){
            requestIndex=false;
        }
        return requestIndex;
    }

    public void setRequestIndex(Boolean requestIndex) {
        this.requestIndex = requestIndex;
    }

    public Boolean getStartDateSet() {
        if(startDateSet==null){
            startDateSet=false;
        }
        return startDateSet;
    }

    public void setStartDateSet(Boolean startDateSet) {
        this.startDateSet = startDateSet;
    }



    // Integer
    public Integer getTheCurrentFragment() {
        if(theCurrentFragment==null) {
            theCurrentFragment= 1;
        }
        return theCurrentFragment;
    }

    public void setTheCurrentFragment(Integer theCurrentFragment) {
        this.theCurrentFragment = theCurrentFragment;
    }

    public Integer getTheCurrentGraph() {
        if(theCurrentGraph==null) {
            theCurrentGraph= 1;
        }
        return theCurrentGraph;
    }

    public void setTheCurrentGraph(Integer theCurrentGraph) {
        this.theCurrentGraph = theCurrentGraph;
    }



    // String
    public String getTheCurrentBenchmark() {
        return theCurrentBenchmark;
    }

    public void setTheCurrentBenchmark(String theCurrentBenchmark) {
        if(theCurrentBenchmark.equals("")){
            benchmarkSet=false;
        }else{
            benchmarkSet=true;
        }
        this.theCurrentBenchmark = theCurrentBenchmark;
    }

    public String getTheCurrentCompany() {
        return theCurrentCompany;
    }

    public void setTheCurrentCompany(String theCurrentCompany) {
        if(theCurrentCompany.equals("")){
            companySet=false;
        }else{
            companySet=true;
        }
        this.theCurrentCompany = theCurrentCompany;
    }

    public String getTheDownloadedData() {
        return theDownloadedData;
    }

    public void setTheDownloadedData(String theDownloadedData) {
        this.theDownloadedData = theDownloadedData;
    }

    public String getTheDownloadedIndexData() {
        return theDownloadedIndexData;
    }

    public void setTheDownloadedIndexData(String theDownloadedIndexData) {
        this.theDownloadedIndexData = theDownloadedIndexData;
    }

    public String getTheEndDateString() {
        return theEndDateString;
    }

    public void setTheEndDateString(String theEndDateString) {
        this.theEndDateString = theEndDateString;
    }

    public String getTheStartDateString() {
        return theStartDateString;
    }

    public void setTheStartDateString(String theStartDateString) {
        this.theStartDateString = theStartDateString;
    }

    public static String getMyAppKey() {
        return myAppKey;
    }



    // ArrayList<Boolean>
    public ArrayList<Boolean> getTheBooleans() {
        return theBooleans;
    }

    public void setTheBooleans(ArrayList<Boolean> theBooleans) {
        this.theBooleans = theBooleans;
    }



    // ArrayList<Integer>
    public ArrayList<Integer> getEndingDate() {
        if(endingDate==null){
            endingDate=new ArrayList<Integer>();
            endingDate.add(0,-1);
            endingDate.add(1,-1);
            endingDate.add(2,-1);
        }
        return endingDate;
    }

    public void setEndingDate(ArrayList<Integer> endingDate) {
        this.endingDate = endingDate;
    }

    public ArrayList<Integer> getStartingDate() {
        if(startingDate==null) {
            startingDate=new ArrayList<Integer>();
            startingDate.add(0,-1);
            startingDate.add(1,-1);
            startingDate.add(2,-1);
        }
        return startingDate;
    }

    public void setStartingDate(ArrayList<Integer> startingDate) {
        this.startingDate = startingDate;
    }

    public ArrayList<Integer> getStartingDateInfimum() {
        if(startingDateInfimum==null){
            startingDateInfimum=new ArrayList<Integer>();
            startingDateInfimum.add(0,-1);
            startingDateInfimum.add(1,-1);
            startingDateInfimum.add(2,-1);
        }
        return startingDateInfimum;
    }

    public void setStartingDateInfimum(ArrayList<Integer> startingDateInfimum) {
        this.startingDateInfimum = startingDateInfimum;
    }

    public ArrayList<Integer> getTheIntegers() {
        return theIntegers;
    }

    public void setTheIntegers(ArrayList<Integer> theIntegers) {
        this.theIntegers = theIntegers;
    }


    // ArrayList<Float>
    public ArrayList<Float> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Float> arrayList) {
        this.arrayList = arrayList;
    }

    public ArrayList<Float> getArrayListIndex() {
        return arrayListIndex;
    }

    public void setArrayListIndex(ArrayList<Float> arrayListIndex) {
        this.arrayListIndex = arrayListIndex;
    }

    public ArrayList<Float> getStrategyResult() {
        return strategyResult;
    }

    public void setStrategyResult(ArrayList<Float> strategyResult) {
        this.strategyResult = strategyResult;
    }



    // ArrayList<ArrayList<Integer>>
    public ArrayList<ArrayList<Integer>> getTheDates() {
        return theDates;
    }

    public void setTheDates(ArrayList<ArrayList<Integer>> theDates) {
        this.theDates = theDates;
    }

    public ArrayList<ArrayList<Integer>> getTheDatesBenchmark() {
        return theDatesBenchmark;
    }

    public void setTheDatesBenchmark(ArrayList<ArrayList<Integer>> theDatesBenchmark) {
        this.theDatesBenchmark = theDatesBenchmark;
    }
}

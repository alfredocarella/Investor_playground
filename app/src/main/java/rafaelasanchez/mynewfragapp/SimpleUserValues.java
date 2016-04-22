package rafaelasanchez.mynewfragapp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by R on 21/04/2016.
 */
public class SimpleUserValues implements Serializable {

    private Boolean startDateSet=null;
    private Boolean endDateSet=null;
    private Integer theCurrentFragment=null;
    private Integer theCurrentGraph=null;
    private String theCurrentCompany="";
    private String theCurrentBenchmark="";
    private String theEndDateString="";
    private String theStartDateString="";
    private ArrayList<Boolean> theBooleans;
    private ArrayList<Integer> theIntegers;
    private ArrayList<Integer> endingDate;
    private ArrayList<Integer> startingDate;
    private ArrayList<Integer> startingDateInfimum;
    private ArrayList<Float> arrayList;
    private ArrayList<Float> strategyResult;
    private ArrayList<ArrayList<Integer>> theDates;


    public SimpleUserValues() {
    }

    public static SimpleUserValues newInstance(){
        return new SimpleUserValues();
    }





    //All getters & setters

    //Boolean
    public Boolean getEndDateSet() {
        if(endDateSet==null){
            endDateSet=false;
        }
        return endDateSet;
    }

    public void setEndDateSet(Boolean endDateSet) {
        this.endDateSet = endDateSet;
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
        this.theCurrentBenchmark = theCurrentBenchmark;
    }

    public String getTheCurrentCompany() {
        return theCurrentCompany;
    }

    public void setTheCurrentCompany(String theCurrentCompany) {
        this.theCurrentCompany = theCurrentCompany;
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



    // ArrayList<Boolean>
    public ArrayList<Boolean> getTheBooleans() {
        return theBooleans;
    }

    public void setTheBooleans(ArrayList<Boolean> theBooleans) {
        this.theBooleans = theBooleans;
    }



    // ArrayList<Integer>
    public ArrayList<Integer> getEndingDate() {
        if(endingDate.isEmpty()){
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
        if(startingDate.isEmpty()) {
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
        if(startingDateInfimum.isEmpty()){
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










}

package rafaelasanchez.mynewfragapp;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by R on 21/04/2016.
 */
public class SimpleUserValues {


    private Integer theCurrentFragment=null;
    private Integer theCurrentGraph=null;
    private ArrayList<Boolean> theBooleans;
    private ArrayList<Integer> theIntegers;
    private ArrayList<Integer> endingDate = new ArrayList<Integer>();
    private ArrayList<Integer> startingDate = new ArrayList<Integer>();
    private ArrayList<Integer> startingDateInfimum=new ArrayList<Integer>();
    private ArrayList<Float> arrayList;
    private ArrayList<Float> strategyResult;
    private ArrayList<ArrayList<Integer>> theDates;


    public SimpleUserValues() {
    }

    public static SimpleUserValues newInstance(){
        return new SimpleUserValues();
    }





    //All getters & setters

    // int
    public Integer getTheCurrentFragment() {
        if(theCurrentFragment!=null) {
            return theCurrentFragment;
        }else {
            return 1;
        }
    }

    public void setTheCurrentFragment(Integer theCurrentFragment) {
        this.theCurrentFragment = theCurrentFragment;
    }

    public Integer getTheCurrentGraph() {
        if(theCurrentGraph!=null) {
            return theCurrentGraph;
        }else{
            return 1;
        }
    }

    public void setTheCurrentGraph(Integer theCurrentGraph) {
        this.theCurrentGraph = theCurrentGraph;
    }



    // ArrayList<Boolean>
    public ArrayList<Boolean> getTheBooleans() {
        return theBooleans;
    }

    public void setTheBooleans(ArrayList<Boolean> theBooleans) {
        this.theBooleans = theBooleans;
    }



    // ArrayList<Integer>
    public ArrayList<Integer> getTheIntegers() {
        return theIntegers;
    }

    public void setTheIntegers(ArrayList<Integer> theIntegers) {
        this.theIntegers = theIntegers;
    }

    public ArrayList<Integer> getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(ArrayList<Integer> endingDate) {
        this.endingDate = endingDate;
    }

    public ArrayList<Integer> getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(ArrayList<Integer> startingDate) {
        this.startingDate = startingDate;
    }

    public ArrayList<Integer> getStartingDateInfimum() {
        return startingDateInfimum;
    }

    public void setStartingDateInfimum(ArrayList<Integer> startingDateInfimum) {
        this.startingDateInfimum = startingDateInfimum;
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

package rafaelasanchez.mynewfragapp;

import android.content.Context;
import android.util.Log;


import java.util.ArrayList;

/**
 * Created by R on 18/04/2016.
 */
public class UserValues {

    private Integer period;
    private Float fee;
    private ArrayList<Boolean> theBooleans = new ArrayList<>();
    private ArrayList<Integer> theIntegers = new ArrayList<>();
    private static final ArrayList<String> theIntegerKeys = new ArrayList<>();
    private static final ArrayList<String> theBooleanKeys = new ArrayList<>();
    public static final String myAppKey = "Investor Playground";
    private static final ArrayList<Integer> theIntDefValues = new ArrayList<>();
    private ArrayList<Float> arrayList;
    private ArrayList<ArrayList<Integer>> theDates;
    private ArrayList<Float> strategyResult;
    private Integer theCurrentFragment;

    private Integer theCurrentGraph;

    Context context;


    public UserValues() {
    }

    public void newInstance(Context context_){
        context=context_;







        // Get all the other stuff set into arrays
        for (int pos=0; pos<theBooleanKeys.size(); pos++ ) {

            // the boolean value associated with each entry:
            if (context.getSharedPreferences(myAppKey, Context.MODE_PRIVATE)
                    .contains(theBooleanKeys.get(pos))){
                theBooleans
                        .add(pos, context
                                .getSharedPreferences(myAppKey, Context.MODE_PRIVATE)
                                .getBoolean(theBooleanKeys.get(pos), true));
            } else {
                theBooleans.add(pos, true);
            }

            //the values themselves:
            if (context.getSharedPreferences(myAppKey, Context.MODE_PRIVATE)
                    .contains(theIntegerKeys.get(pos))){
                    theIntegers
                            .add(pos, context
                                    .getSharedPreferences(myAppKey, Context.MODE_PRIVATE)
                                    .getInt(theIntegerKeys.get(pos), -1));
            } else {
                theIntegers.add(pos, theIntDefValues.get(pos));
            }

        }

    }




    public ArrayList<Boolean> getTheBooleans() {
        return theBooleans;
    }

    public ArrayList<Integer> getTheIntegers() {
        return theIntegers;
    }

    public static ArrayList<Integer> getTheIntDefValues() {
        if(theIntDefValues.isEmpty()){
            loadConstants();
        }
        return theIntDefValues;
    }

    public static ArrayList<String> getTheBooleanKeys() {
        if(theBooleanKeys.isEmpty()){
            loadConstants();
        }
        return theBooleanKeys;
    }

    public static ArrayList<String> getTheIntegerKeys() {
        if(theIntegerKeys.isEmpty()){
            loadConstants();
        }
        return theIntegerKeys;
    }

    public Float getFee() {
        return fee;
    }

    public void setFee(Float fee_) {
        this.fee = fee_;
        context.getSharedPreferences(myAppKey, Context.MODE_PRIVATE)
                .edit()
                .putFloat("config_fees_edit_text", fee)
                .commit();

    }

    public Integer getPeriod(){
        return period;
    }

    public void setPeriod(Integer period_) {
        this.period = period_;
        context.getSharedPreferences(myAppKey, Context.MODE_PRIVATE)
                .edit()
                .putInt("config_period_edit_text", period)
                .commit();
    }




    public void setArrayList(ArrayList<Float> arrayList) {
        this.arrayList = arrayList;
    }

    public ArrayList<Float> getArrayList() {
        return arrayList;
    }


    public ArrayList<ArrayList<Integer>> getTheDates() {
        return theDates;
    }

    public void setTheDates(ArrayList<ArrayList<Integer>> theDates) {
        this.theDates = theDates;
    }

    public Integer getTheCurrentFragment() {
        return theCurrentFragment;
    }

    public void setTheCurrentFragment(Integer theCurrentFragment) {
        this.theCurrentFragment = theCurrentFragment;
    }


    public ArrayList<Float> getStrategyResult() {
        return strategyResult;
    }

    public void setStrategyResult(ArrayList<Float> strategyResult) {
        this.strategyResult = strategyResult;
    }

    public Integer getTheCurrentGraph() {
        return theCurrentGraph;
    }

    public void setTheCurrentGraph(Integer theCurrentGraph) {
        this.theCurrentGraph = theCurrentGraph;
    }



}

package rafaelasanchez.mynewfragapp;

import android.content.Context;


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
    private int theCurrentFragment;

    private int theCurrentGraph;

    Context context;


    public UserValues() {
    }

    public void newInstance(Context context_){
        context=context_;

        loadConstants();

        //period
        if (context.getSharedPreferences(myAppKey, Context.MODE_PRIVATE)
                .contains("config_period_edit_text")){
            period=context.getSharedPreferences(myAppKey, Context.MODE_PRIVATE)
                    .getInt("config_period_edit_text", -1);
        } else {
            period=14;
        }

        //fee
        if (context.getSharedPreferences(myAppKey, Context.MODE_PRIVATE)
                .contains("config_fees_edit_text")){
            fee=context
                    .getSharedPreferences(myAppKey, Context.MODE_PRIVATE)
                    .getFloat("config_fees_edit_text", -1.f);
        } else {
            fee=0.1f;
        }



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


    public void setTheBooleans(Integer p, boolean value) {
        this.theBooleans.set(p,value);
        context.getSharedPreferences(myAppKey, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(theBooleanKeys.get(p), value)
                .commit();
    }

    public void setTheIntegers(Integer p, Integer value) {
        this.theIntegers.set(p,value);
        context.getSharedPreferences(myAppKey, Context.MODE_PRIVATE)
                .edit()
                .putInt(theIntegerKeys.get(p), value)
                .commit();
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

    public static String getMyAppKey() {
        return myAppKey;
    }

    private static void loadConstants(){

        Integer p=0;

        // BUY FIELDS
        // RSI =Relative Strength Index
        theBooleanKeys.add(p,"config_buying_RSI_checkbox");
        theIntegerKeys.add(p, "config_buying_RSI_edit_text");
        theIntDefValues.add(p,30);

        // ADX =Average Directional Index
        p=p+1;
        theBooleanKeys.add(p,"config_buying_ADX_checkbox");
        theIntegerKeys.add(p,"config_buying_ADX_edit_text");
        theIntDefValues.add(p,30);

        // SL=Stop Loss
        p=p+1;
        theBooleanKeys.add(p,"config_buying_stop_loss_checkbox");
        theIntegerKeys.add(p,"config_buying_stop_loss_edit_text");
        theIntDefValues.add(p,15);

        // SELL FIELDS
        // RSI
        p=p+1;
        theBooleanKeys.add(p,"config_selling_RSI_checkbox");
        theIntegerKeys.add(p,"config_selling_RSI_edit_text");
        theIntDefValues.add(p,70);

        // ADX
        p=p+1;
        theBooleanKeys.add(p,"config_selling_ADX_checkbox");
        theIntegerKeys.add(p,"config_selling_ADX_edit_text");
        theIntDefValues.add(p,20);

        // SL
        p=p+1;
        theBooleanKeys.add(p,"config_selling_stop_loss_checkbox");
        theIntegerKeys.add(p,"config_selling_stop_loss_edit_text");
        theIntDefValues.add(p,15);
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

    public int getTheCurrentFragment() {
        return theCurrentFragment;
    }

    public void setTheCurrentFragment(int theCurrentFragment) {
        this.theCurrentFragment = theCurrentFragment;
    }


    public ArrayList<Float> getStrategyResult() {
        return strategyResult;
    }

    public void setStrategyResult(ArrayList<Float> strategyResult) {
        this.strategyResult = strategyResult;
    }

    public int getTheCurrentGraph() {
        return theCurrentGraph;
    }

    public void setTheCurrentGraph(int theCurrentGraph) {
        this.theCurrentGraph = theCurrentGraph;
    }



}

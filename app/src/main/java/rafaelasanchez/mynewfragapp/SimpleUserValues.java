package rafaelasanchez.mynewfragapp;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by R on 21/04/2016.
 */
public class SimpleUserValues {


    private int theCurrentFragment;
    private int theCurrentGraph;
    private ArrayList<Boolean> theBooleans;
    private ArrayList<Integer> theIntegers;



    public SimpleUserValues() {
    }

    public static SimpleUserValues newInstance(){
        return new SimpleUserValues();
    }

    public ArrayList<Boolean> getTheBooleans() {
        return theBooleans;
    }

    public void setTheBooleans(ArrayList<Boolean> theBooleans_) {
        theBooleans = theBooleans_;
    }

    public ArrayList<Integer> getTheIntegers() {
        return theIntegers;
    }

    public void setTheIntegers(ArrayList<Integer> theIntegers_) {
        theIntegers = theIntegers_;
    }

    public int getTheCurrentFragment() {
        return theCurrentFragment;
    }

    public void setTheCurrentFragment(int theCurrentFragment_) {
        theCurrentFragment = theCurrentFragment_;
    }

    public int getTheCurrentGraph() {
        return theCurrentGraph;
    }

    public void setTheCurrentGraph(int theCurrentGraph_) {
        theCurrentGraph = theCurrentGraph_;
    }

}

package rafaelasanchez.mynewfragapp;

import java.util.ArrayList;

/**
 * Created by R on 10/03/2016.
 */
public class MultipleResult {

    private ArrayList<Float> resultA;
    private ArrayList<Float> resultB;

    public MultipleResult(ArrayList<Float> resultA_, ArrayList<Float> resultB_) {
        resultA=resultA_;
        resultB=resultB_;
    }

    public ArrayList<Float> getResultA() {
        return resultA;
    }

    public ArrayList<Float> getResultB() {
        return resultB;
    }
}

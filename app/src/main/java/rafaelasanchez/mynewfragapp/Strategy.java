package rafaelasanchez.mynewfragapp;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by R on 19/04/2016.
 */
public class Strategy {

    private ArrayList<Float> theResult = new ArrayList<>();
    private Integer period;
    private Float fee;
    private ArrayList<Boolean> theBooleans;
    private ArrayList<Integer> theIntegers;
    private ArrayList<Float> arrayList;

    public Strategy(UserValues theInput) {

        period=theInput.getPeriod();
        fee=theInput.getFee();
        theIntegers=theInput.getTheIntegers();
        theBooleans=theInput.getTheBooleans();
        arrayList=theInput.getArrayList();


        int n=arrayList.size();

        ArrayList<Float> change = zeros(n);
        ArrayList<Float> gains = zeros(n);
        ArrayList<Float> losses = zeros(n);
        ArrayList<Float> rsi = zeros(n);


        //Generate RSI
        for(int d=arrayList.size()-2;d>=0;d--){
            Float todayChange = (arrayList.get(d)-arrayList.get(d+1))/arrayList.get(d);
            change.set(d,todayChange);
            if(todayChange>0){
                gains.set(d,todayChange);
            }else {
                losses.set(d,-todayChange);
            }
        }
        for(int d=gains.size()-2-(period-1);d>=0;d--){
            Float avgGains=mean(slice(gains,d,d+(period-1)));
            Float avgLosses=mean(slice(losses,d,d+(period-1)));
            Float todayRS=avgGains/avgLosses;
            rsi.set(d,100.f-100.f/(1+todayRS));
        }

        theResult=rsi;
    }

    public ArrayList<Float> getTheResult() {
        return theResult;
    }

    private ArrayList<Float> zeros(int n){
        //Return an ArrayList of size n filled with zeros

        ArrayList<Float> a= new ArrayList<>();
        for (int i = 0; i <= n - 1; i++) {
            a.add(i, 0.f);
        }
        return a;

    }

    private Float mean(ArrayList<Float> theInput){

        //Arithmetic mean
        int n = theInput.size();
        Float result=0.f;
        for(int i=0;i<=n-1;i++){
            result=result+theInput.get(i);
        }
        return result/n;

    }

    private ArrayList<Float> slice(ArrayList<Float> input,int start, int end){

        //Return a custom slice of the original ArrayList
        int n=end-start+1;
        ArrayList<Float> result = zeros(n);
        for(int i =0; i<=n-1 ; i++){
            result.add(i,input.get(start+i));
        }

        return result;
    }




}

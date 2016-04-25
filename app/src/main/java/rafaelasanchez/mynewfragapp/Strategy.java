package rafaelasanchez.mynewfragapp;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by R on 19/04/2016.
 */
public class Strategy {

    private ArrayList<Float> theResult = new ArrayList<>();
    private ArrayList<Float> rsi = new ArrayList<>();
    private Integer period;
    private Float fee;
    private ArrayList<Boolean> theBooleans;
    private ArrayList<Integer> theIntegers;
    private ArrayList<Float> arrayList;

    private Float yourReturn;
    private Float yourAReturn;


    public Strategy(SimpleUserValues values) {

        period=values.getPeriod();
        fee=values.getFee();
        theBooleans=values.getTheBooleans();
        theIntegers=values.getTheIntegers();
        arrayList=values.getArrayList();


        int n=arrayList.size();

        ArrayList<Float> change = zeros(n);
        ArrayList<Float> gains = zeros(n);
        ArrayList<Float> losses = zeros(n);
        rsi = zeros(n);


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

        runTheStrategy();
        theResult=runTheStrategy();

    }


    public ArrayList<Float> runTheStrategy(){
        int n = arrayList.size();
        ArrayList<Float> capital = zeros(n);
        ArrayList<Boolean> buy =falses(n);
        ArrayList<Boolean> sell =falses(n);
        ArrayList<Boolean> bought=falses(n);

        // Fill the buy & sell arrays
        for(int i=arrayList.size()-1;i>=0;i--) {
            // Apply RSI logic
            if (theBooleans.get(0) && rsi.get(i) <= theIntegers.get(0)) {
                buy.set(i, true);
            }
            if (theBooleans.get(3) && rsi.get(i) >= theIntegers.get(3)) {
                sell.set(i, true);
            }

            // Apply logic for the other indicators
        }


        // Fill the bought array
        // Boolean bought is true if sell is false
        // AND either bought true before or buy true now
        for(int i=arrayList.size()-1;i>=0;i--) {
            if(i==arrayList.size()-1){
                if (buy.get(i)&&!sell.get(i)){
                    bought.set(i,true);
                }
            }else{
                if(!sell.get(i)&&(buy.get(i)||bought.get(i+1))) {
                    bought.set(i,true);
                }
            }
        }

        // Fill the capital array
        // Minimum 2 days in which "bought" is true
        for(int i=arrayList.size()-1;i>=0;i--){
            if(i==arrayList.size()-1){
                capital.set(i, 1.0f);
            }else{
                if(bought.get(i)&&bought.get(i+1)){
                    Float change;
                    // Apply fee if buying or selling
                    if((i+1<arrayList.size()-1 && !bought.get(i+2))||i==arrayList.size()-2) {
                        change = (1-fee/100)*arrayList.get(i) / arrayList.get(i + 1);
                    }else if(i>0 && !bought.get(i-1)){
                        change = (1-fee/100)*arrayList.get(i) / arrayList.get(i + 1);
                    }else{
                        change = arrayList.get(i) / arrayList.get(i + 1);
                    }

                    capital.set(i, capital.get(i+1)*change);
                }else {
                    capital.set(i, capital.get(i+1));
                }
            }
        }

        yourReturn=100*(capital.get(0)-1.f);

        return capital;
    }


    public Float getYourReturn() {
        return yourReturn;
    }

    private ArrayList<Float> booleanToFloat(ArrayList<Boolean> input){

        int n=input.size();
        ArrayList<Float> result=new ArrayList<>();
        for(int i=0;i<n;i++){
            if(input.get(i)){
                result.add(i,1.f);
            }else{
                result.add(i,0.f);
            }
        }
        return result;
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

    private ArrayList<Boolean> falses(Integer n){
        //Return an ArrayList of size n filled with false

        ArrayList<Boolean> a= new ArrayList<>();
        for (int i = 0; i <= n - 1; i++) {
            a.add(i, false);
        }
        return a;

    }


    private Float mean(ArrayList<Float> values){

        //Arithmetic mean
        int n = values.size();
        Float result=0.f;
        for(int i=0;i<=n-1;i++){
            result=result+values.get(i);
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

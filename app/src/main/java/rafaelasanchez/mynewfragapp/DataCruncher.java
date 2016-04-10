package rafaelasanchez.mynewfragapp;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by R on 08/03/2016.
 */
public class DataCruncher {


    private Double theReturn;
    private Double theAnnualizedReturn;
    private Double theBeta;
    private Double theSharpe;
    private Double theCorrelation;



    public DataCruncher(ArrayList<Float> theArray,
                        ArrayList<Float> theIndex,
                        ArrayList<ArrayList<Integer>> theDates,
                        ArrayList<ArrayList<Integer>> theDatesBenchmark) {

        if (theArray.size() > 0) {

            //Returns

            float cf = theArray.get(0);
            float c0 = theArray.get(theArray.size() - 1);

            theReturn = 100 * (-1.0 + cf / c0);
            Calendar d0 = Calendar.getInstance();
            d0.set(theDates.get(2).get(theArray.size() - 1),
                    theDates.get(1).get(theArray.size() - 1),
                    theDates.get(0).get(theArray.size() - 1));
            Calendar df = Calendar.getInstance();
            df.set(theDates.get(2).get(0),
                    theDates.get(1).get(0),
                    theDates.get(0).get(0));

            Calendar dj = d0;

            int j = 0;
            while (df.after(dj)) {
                j = j + 1;
                dj.add(Calendar.DATE, 1);
            }

            Double Dt = j / 365.25; //Here converting the time from days to years
            theAnnualizedReturn = 100 * (-1.0 + Math.exp((1.0 / Dt) * Math.log(cf / c0)));

            if (theIndex.size() > 0) {
                // Beta calculation
                // Calculate the daily changes first:
                MultipleResult dayChanges = intraDayChange(theArray, theDates,
                        theIndex, theDatesBenchmark);
                ArrayList<Float> dayChangesCompany = dayChanges.getResultA();
                ArrayList<Float> dayChangesIndex = dayChanges.getResultB();

                Float theVariance = variance(dayChangesIndex);
                Float theCovariance = covariance(dayChangesCompany, dayChangesIndex);
                theBeta = (double) theCovariance / theVariance;
                theSharpe = 0.0;
                theCorrelation = (double) correlate(dayChangesCompany, dayChangesIndex);
            } else {
                theBeta = 0.0;
                theSharpe = 0.0;
                theCorrelation = 0.0;
            }
        }else{
            theReturn=0.0;
            theAnnualizedReturn =0.0;
            theBeta = 0.0;
            theSharpe = 0.0;
            theCorrelation = 0.0;
        }
    }


    public Double getTheReturn() {
        return theReturn;
    }

    public Double getTheAnnualizedReturn() {
        return theAnnualizedReturn;
    }

    public Double getTheBeta() {
        return theBeta;
    }

    public Double getTheSharpe() {
        return theSharpe;
    }

    public Double getTheCorrelation() {
        return theCorrelation;
    }


    private MultipleResult intraDayChange(ArrayList<Float> theArrayA,
                                          ArrayList<ArrayList<Integer>> theDatesA,
                                          ArrayList<Float> theArrayB,
                                          ArrayList<ArrayList<Integer>> theDatesB){

        ArrayList<Float> theOutputA = new ArrayList<>();
        ArrayList<Float> theOutputB = new ArrayList<>();

        int n= Math.min(theArrayA.size(),theArrayB.size());
        int i=0;
        int counter=0;
        while(i<n-1){
            boolean ready=false;
            int j=0;
            while(!ready) {
                if (equalDates(theDatesA,i,
                                theDatesB,j)&&
                        equalDates(theDatesA,i+1,
                                theDatesB,j+1)&&
                        theArrayA.get(i+1)!=0 &&
                        theArrayB.get(i+1)!=0
                        ){
                    theOutputA.add(counter, (theArrayA.get(i) - theArrayA.get(i + 1)) / theArrayA.get(i + 1));
                    theOutputB.add(counter, (theArrayB.get(j) - theArrayB.get(j + 1)) / theArrayB.get(j + 1));
                    counter = counter + 1;
                    ready = true;
                }
                if (j==n-2){
                    ready=true;
                }
                j= 1 + j;
            }
            i = i + 1;
        }
        return new MultipleResult(theOutputA,theOutputB);
    }



    private boolean equalDates(ArrayList<ArrayList<Integer>> theDatesA, Integer i,
                               ArrayList<ArrayList<Integer>> theDatesB, Integer j){

        return (theDatesA.get(0).get(i).equals(theDatesB.get(0).get(j))    &&
                theDatesA.get(1).get(i).equals(theDatesB.get(1).get(j))    &&
                theDatesA.get(2).get(i).equals(theDatesB.get(2).get(j))    );
    }



    private Float variance(ArrayList<Float> theInput) {

        float theSum=0.00000f;
        int i=0;
        float theMean = mean(theInput);
        while(i<theInput.size()) {
            theSum = theSum + (float) Math.pow(theInput.get(i)-theMean,2);
            i=i+1;
        }
        return theSum/theInput.size();
    }



    private Float covariance(ArrayList<Float> theInputA,
                             ArrayList<Float> theInputB) {

        float theSum=0.00000f;
        float theMeanA=mean(theInputA);
        float theMeanB=mean(theInputB);

        int i=0;
        while(i<theInputA.size()) {
            theSum = theSum + (theInputA.get(i) - theMeanA) * (theInputB.get(i) - theMeanB);
            i=i+1;
        }

        return theSum/theInputA.size();
    }

    private Float correlate(ArrayList<Float> theInputA,
                             ArrayList<Float> theInputB) {

        int theSum=0;
        int counter=0;
        int i=0;
        while(i<theInputA.size()) {
            if(theInputA.get(i)*theInputB.get(i)>0){
                theSum = theSum +1;
                counter=counter+1;
            }else if(theInputA.get(i)*theInputB.get(i)<0){
                counter=counter+1;
            }
            i=i+1;
        }

        if(counter>0) {
            return (float) theSum / counter;
        }else{
            return 0.f;
        }

    }

    private Float mean(ArrayList<Float> theInput) {

        float theSum=0.00000f;
        int i=0;
        while(i<theInput.size()-1) {
            theSum = theSum + theInput.get(i);
            i=i+1;
        }
        return theSum/theInput.size();
    }




}

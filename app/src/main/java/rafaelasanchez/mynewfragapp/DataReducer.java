package rafaelasanchez.mynewfragapp;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;


/**
 * Created by rafa on 2/13/16.
 */
public class DataReducer  {


    private ArrayList<Float> theArray;
    private ArrayList<ArrayList<Integer>> theDates;


    public DataReducer(String theDownloadingData) {
        newInstance(theDownloadingData);
    }




    private void newInstance(String theDownloadingData) {
        theArray =new ArrayList<Float>();
        theDates =new ArrayList<ArrayList<Integer>>();
        theDates.add(0,new ArrayList<Integer>());
        theDates.add(1,new ArrayList<Integer>());
        theDates.add(2,new ArrayList<Integer>());


        // Find out number of lines
        boolean ready=false;
        int lines=0;
        int charPos=0;
        while(!ready){
            int newCharPos= theDownloadingData.indexOf("\n", charPos);
            if (newCharPos!=-1){
                charPos=newCharPos+1;
                lines=lines+1;
            }else{
                ready=true;
            }
        }
        Log.e("count lines",String.valueOf(lines));


        int i=theDownloadingData.indexOf("\n", 0); // Avoid the titles
        theDownloadingData=theDownloadingData.substring(i+1);
        i=0;

        int position =0;
        while (position<lines-1) {

            int yearMonth=theDownloadingData.indexOf("-", i);
            int monthDay=theDownloadingData.indexOf("-", yearMonth + 1);;

            String theYear = theDownloadingData.substring(i,yearMonth);
            String theMonth = theDownloadingData.substring(yearMonth+1,monthDay);
            i=theDownloadingData.indexOf(",", i);
            String theDate = theDownloadingData.substring(monthDay+1, i);


            try{
                theDates.get(0).add(position,Integer.parseInt(theDate));
            }
            catch(NumberFormatException theException){
                theException.printStackTrace();
            }
            catch (IndexOutOfBoundsException theException){
                theException.printStackTrace();
                break;
            }

            try{
                theDates.get(1).add(position,Integer.parseInt(theMonth));
            }
            catch(NumberFormatException theException){
                theException.printStackTrace();
            }
            catch (IndexOutOfBoundsException theException){
                theException.printStackTrace();
                break;
            }

            try{
                theDates.get(2).add(position,Integer.parseInt(theYear));
            }
            catch(NumberFormatException theException){
                theException.printStackTrace();
            }
            catch (IndexOutOfBoundsException theException){
                theException.printStackTrace();
                break;
            }


            i=theDownloadingData.indexOf(",", i+1);
            i=theDownloadingData.indexOf(",", i+1);
            i=theDownloadingData.indexOf(",", i+1);
            i=theDownloadingData.indexOf(",", i+1);
            i=theDownloadingData.indexOf(",", i+1);

            int i2 = theDownloadingData.indexOf("\n", i+1);

            String adjClose = theDownloadingData.substring(i+1,i2);


            try{
                theArray.add(position,Float.parseFloat(adjClose));
            }
            catch(NumberFormatException theException){
                theException.printStackTrace();
            }
            catch (IndexOutOfBoundsException theException){
                theException.printStackTrace();
                break;
            }

            i=i2+1;
            position=position+1;
        }



    }


    public ArrayList<Float> getTheArray() {
        return theArray;
    }


    public ArrayList<ArrayList<Integer>> getTheDates() {
        return theDates;
    }




}

package rafaelasanchez.mynewfragapp;


import android.app.Fragment;
import android.os.Bundle;

public class UserData extends Fragment{

    public static String theString;
    public static int theCurrentFragment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            UserData.theString =String.valueOf(savedInstanceState.getCharSequence("CURRENT_STRING"));
            UserData.theCurrentFragment =savedInstanceState.getInt("CURRENT_FRAGMENT");
        } else {
            UserData.theString = "";
            UserData.theCurrentFragment = 1;
        }

    }

    public static String getTheString() {
        return theString;
    }

    public static void setTheString(String theString) {
        UserData.theString = theString;
    }

    public static int getTheCurrentFragment() {
        return theCurrentFragment;
    }

    public static void setTheCurrentFragment(int theCurrentFragment) {
        UserData.theCurrentFragment = theCurrentFragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence("CURRENT_STRING", UserData.theString);
        outState.putInt("CURRENT_FRAGMENT", UserData.theCurrentFragment);

    }


}

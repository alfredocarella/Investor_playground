package rafaelasanchez.mynewfragapp;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public int current_fragment = 1;
    UserData userData = new UserData();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState==null) {
            FirstFragment firstFragment = new FirstFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, firstFragment).commit();
        } else {
            if (savedInstanceState.getInt("CURRENT_FRAGMENT") == 1) {
                FirstFragment firstFragment = new FirstFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, firstFragment).commit();
            } else if (savedInstanceState.getInt("CURRENT_FRAGMENT") == 2) {
                SecondFragment secondFragment = new SecondFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, secondFragment).commit();
            }
        }

    }


    public void on1stButtonClicked(View view) {

        UserData.setTheCurrentFragment(2);
        Log.e("MA.on1stButtonClicked", "Run");

        EditText editText = (EditText) findViewById(R.id.first_frag_edit_text);
        UserData.setTheString(editText.getText().toString());

        SecondFragment secondFragment = new SecondFragment();
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, secondFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }


    public void on2ndButtonClicked(View view) {

        UserData.setTheCurrentFragment(1);
        Log.e("MA.on2ndButtonClicked", "Run");

        FirstFragment firstFragment = new FirstFragment();
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, firstFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        Log.e("MA.onSaveInstanceState", "Run");
        outState.putInt("CURRENT_FRAGMENT", UserData.theCurrentFragment);
        outState.putCharSequence("CURRENT_STRING", UserData.theString);
        super.onSaveInstanceState(outState);
    }



}

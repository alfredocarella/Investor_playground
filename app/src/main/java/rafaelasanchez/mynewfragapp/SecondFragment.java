package rafaelasanchez.mynewfragapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SecondFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("SF.onCreate", "Run");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("SF.onCreateView", "Run");
        return inflater.inflate(R.layout.second_frag_layout, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("SF.onStart", "Run");

        ((MainActivity) getActivity()).startFileService();
    }



}

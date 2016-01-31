package rafaelasanchez.mynewfragapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SecondFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("SF.onCreateView", "Run");
        return inflater.inflate(R.layout.second_frag_layout, container, false);
    }



    @Override
    public void onStart() {
        super.onStart();

        TextView theTextView = (TextView) getActivity().findViewById(R.id.the_text_view_2nd_frag);
        String string = UserData.getTheString();
        Log.e("SF.onStart", "Run");
        theTextView.setText(string);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("SF.onDestroy", "Run");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("SF.onDestroyView", "Run");
    }

}

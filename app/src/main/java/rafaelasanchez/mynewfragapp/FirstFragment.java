package rafaelasanchez.mynewfragapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class FirstFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("FF.onCreateView", "Run");
        return inflater.inflate(R.layout.first_frag_layout, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("FF.onStart", "Run");

        EditText editText = (EditText) getActivity().findViewById(R.id.first_frag_edit_text);
        editText.setText(UserData.getTheString());


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("FF.onDestroy", "Run");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("FF.onDestroyView", "Run");
    }
}

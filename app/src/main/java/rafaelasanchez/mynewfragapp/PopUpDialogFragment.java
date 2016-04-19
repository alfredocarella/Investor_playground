package rafaelasanchez.mynewfragapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;

/**
 * Created by R on 19/04/2016.
 */
public class PopUpDialogFragment extends DialogFragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        String theString = (String) bundle.getSerializable("theInput");
        View theView = inflater.inflate(R.layout.pop_up_layout,container);
        TextView theText =(TextView) theView.findViewById(R.id.the_popup_textview);
        theText.setText(theString);
        return theView;
    }


    public static PopUpDialogFragment newInstance(String theInput){
        Bundle bundle = new Bundle();
        bundle.putSerializable("theInput",theInput);
        PopUpDialogFragment popUpDialogFragment = new PopUpDialogFragment();
        popUpDialogFragment.setArguments(bundle);
        return popUpDialogFragment;
    }



}

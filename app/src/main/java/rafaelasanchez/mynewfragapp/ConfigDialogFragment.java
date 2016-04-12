package rafaelasanchez.mynewfragapp;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by R on 12/04/2016.
 */
public class ConfigDialogFragment extends DialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View theView = inflater.inflate(R.layout.config_layout,container);
        getDialog().setTitle("Settings");

        return theView;
    }


    public static ConfigDialogFragment newInstance(){
        Bundle bundle = new Bundle();
        ConfigDialogFragment configDialogFragment = new ConfigDialogFragment();
        configDialogFragment.setArguments(bundle);
        return configDialogFragment;
    }








}

package rafaelasanchez.mynewfragapp;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by rafa on 2/10/16.
 */
public class ReturnRateFormulaPopUp extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View theView = inflater.inflate(R.layout.return_rate_formula_popup,container);
        getDialog().setTitle("Formula used");

        return theView;
    }


    public static ReturnRateFormulaPopUp newInstance(){
        Bundle bundle = new Bundle();
        ReturnRateFormulaPopUp returnRateFormulaPopUp = new ReturnRateFormulaPopUp();
        returnRateFormulaPopUp.setArguments(bundle);
        return returnRateFormulaPopUp;
    }

}

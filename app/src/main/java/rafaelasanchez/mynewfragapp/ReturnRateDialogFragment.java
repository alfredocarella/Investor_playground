package rafaelasanchez.mynewfragapp;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by rafa on 2/10/16.
 */
public class ReturnRateDialogFragment extends DialogFragment {

    Double current_c0=1.0;
    Double current_cf=1.0;
    Double current_t=1.0;
    Double current_r=0.0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View theView = inflater.inflate(R.layout.return_rate_layout,container);
        getDialog().setTitle("Estimating returns");

        final EditText c0_ET = (EditText) theView.findViewById(R.id.return_rate_c0);
        final EditText cf_ET = (EditText) theView.findViewById(R.id.return_rate_cf);
        final EditText t_ET = (EditText) theView.findViewById(R.id.return_rate_t);
        final EditText r_ET = (EditText) theView.findViewById(R.id.return_rate_r);

        final CheckBox c0_CB = (CheckBox) theView.findViewById(R.id.c0_checkbox);
        final CheckBox cf_CB = (CheckBox) theView.findViewById(R.id.cf_checkbox);
        final CheckBox t_CB = (CheckBox) theView.findViewById(R.id.t_checkbox);
        final CheckBox r_CB = (CheckBox) theView.findViewById(R.id.r_checkbox);

        final ImageView question_mark = (ImageView) theView.findViewById(R.id.return_rate_question_mark);

        r_CB.setChecked(true);
        r_ET.setEnabled(false);      r_ET.setHint("");

        question_mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                ReturnRateFormulaPopUp returnRateFormulaPopUp = ReturnRateFormulaPopUp.newInstance();
                returnRateFormulaPopUp.setTargetFragment(ReturnRateDialogFragment.this,0);
                returnRateFormulaPopUp.show(fragmentManager,"");
            }
        });


        // make the checkboxes function one at the time

        c0_CB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cf_CB.setChecked(false);
                    t_CB.setChecked(false);
                    r_CB.setChecked(false);

                    c0_ET.setEnabled(false);    c0_ET.setHint("");
                    cf_ET.setEnabled(true);     cf_ET.setHint(R.string.hint_enter_cf);
                    t_ET.setEnabled(true);       t_ET.setHint(R.string.hint_enter_t);
                    r_ET.setEnabled(true);       r_ET.setHint(R.string.hint_enter_r);
                }else{
                    if(!cf_CB.isChecked()&&!t_CB.isChecked()&&!r_CB.isChecked()){
                        c0_CB.setChecked(true);
                    }
                }
            }
        });

        cf_CB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    c0_CB.setChecked(false);
                    t_CB.setChecked(false);
                    r_CB.setChecked(false);

                    c0_ET.setEnabled(true);    c0_ET.setHint(R.string.hint_enter_c0);
                    cf_ET.setEnabled(false);     cf_ET.setHint("");
                    t_ET.setEnabled(true);       t_ET.setHint(R.string.hint_enter_t);
                    r_ET.setEnabled(true);       r_ET.setHint(R.string.hint_enter_r);
                }else{
                    if(!c0_CB.isChecked()&&!t_CB.isChecked()&&!r_CB.isChecked()){
                        cf_CB.setChecked(true);
                    }
                }

            }
        });

        t_CB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    c0_CB.setChecked(false);
                    cf_CB.setChecked(false);
                    r_CB.setChecked(false);

                    c0_ET.setEnabled(true);    c0_ET.setHint(R.string.hint_enter_c0);
                    cf_ET.setEnabled(true);     cf_ET.setHint(R.string.hint_enter_cf);
                    t_ET.setEnabled(false);       t_ET.setHint("");
                    r_ET.setEnabled(true);       r_ET.setHint(R.string.hint_enter_r);
                }else{
                    if(!cf_CB.isChecked()&&!c0_CB.isChecked()&&!r_CB.isChecked()){
                        t_CB.setChecked(true);
                    }
                }
            }
        });

        r_CB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    c0_CB.setChecked(false);
                    t_CB.setChecked(false);
                    cf_CB.setChecked(false);

                    c0_ET.setEnabled(true);    c0_ET.setHint(R.string.hint_enter_c0);
                    cf_ET.setEnabled(true);     cf_ET.setHint(R.string.hint_enter_cf);
                    t_ET.setEnabled(true);       t_ET.setHint(R.string.hint_enter_t);
                    r_ET.setEnabled(false);       r_ET.setHint("");
                }else{
                    if(!cf_CB.isChecked()&&!t_CB.isChecked()&&!c0_CB.isChecked()){
                        r_CB.setChecked(true);
                    }
                }
            }
        });


// put the formulas

        c0_ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (cf_CB.isChecked()) {
                    if(isDouble(c0_ET.getText().toString())) {
                        current_c0 = Double.parseDouble(c0_ET.getText().toString());
                    }
                    if (isDouble(t_ET.getText().toString())) {
                        current_t = Double.parseDouble(t_ET.getText().toString());
                    }
                    if(isDouble(r_ET.getText().toString())){
                        current_r = Double.parseDouble(r_ET.getText().toString())/100;
                    }
                    current_cf = current_c0 * (Math.pow(1.0 + current_r, current_t));
                    cf_ET.setText(String.format("%.2f", current_cf));
                }
                if (t_CB.isChecked()) {
                    if(isDouble(cf_ET.getText().toString())) {
                        current_cf = Double.parseDouble(cf_ET.getText().toString());
                    }
                    if (isDouble(c0_ET.getText().toString())) {
                        current_c0 = Double.parseDouble(c0_ET.getText().toString());
                    }
                    if(isDouble(r_ET.getText().toString())){
                        current_r = Double.parseDouble(r_ET.getText().toString())/100;
                    }
                    current_t = Math.log(current_cf/current_c0)/Math.log(1.0+current_r);
                    t_ET.setText(String.format("%.2f", current_t));
                }
                if (r_CB.isChecked()) {
                    if(isDouble(cf_ET.getText().toString())) {
                        current_cf = Double.parseDouble(cf_ET.getText().toString());
                    }
                    if (isDouble(c0_ET.getText().toString())) {
                        current_c0 = Double.parseDouble(c0_ET.getText().toString());
                    }
                    if(isDouble(t_ET.getText().toString())){
                        current_t = Double.parseDouble(t_ET.getText().toString());
                    }
                    current_r = -1.0+Math.exp((1.0/current_t)*Math.log(current_cf/current_c0));
                    r_ET.setText(String.format("%.2f", current_r * 100));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        cf_ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (c0_CB.isChecked()) {
                    if(isDouble(cf_ET.getText().toString())) {
                        current_cf = Double.parseDouble(cf_ET.getText().toString());
                    }
                    if (isDouble(t_ET.getText().toString())) {
                        current_t = Double.parseDouble(t_ET.getText().toString());
                    }
                    if(isDouble(r_ET.getText().toString())){
                        current_r = Double.parseDouble(r_ET.getText().toString())/100;
                    }
                    current_c0 = current_cf / (Math.pow(1.0 + current_r, current_t));
                    c0_ET.setText(String.format("%.2f",current_c0));
                }
                if (t_CB.isChecked()) {
                    if(isDouble(cf_ET.getText().toString())) {
                        current_cf = Double.parseDouble(cf_ET.getText().toString());
                    }
                    if (isDouble(c0_ET.getText().toString())) {
                        current_c0 = Double.parseDouble(c0_ET.getText().toString());
                    }
                    if(isDouble(r_ET.getText().toString())){
                        current_r = Double.parseDouble(r_ET.getText().toString())/100;
                    }
                    current_t = Math.log(current_cf/current_c0)/Math.log(1.0+current_r);
                    t_ET.setText(String.format("%.2f",current_t));
                }
                if (r_CB.isChecked()) {
                    if(isDouble(cf_ET.getText().toString())) {
                        current_cf = Double.parseDouble(cf_ET.getText().toString());
                    }
                    if (isDouble(c0_ET.getText().toString())) {
                        current_c0 = Double.parseDouble(c0_ET.getText().toString());
                    }
                    if(isDouble(t_ET.getText().toString())){
                        current_t = Double.parseDouble(t_ET.getText().toString());
                    }
                    current_r = -1.0+Math.exp((1.0 / current_t) * Math.log(current_cf / current_c0));
                    r_ET.setText(String.format("%.2f", current_r * 100));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        t_ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (c0_CB.isChecked()) {
                    if(isDouble(cf_ET.getText().toString())) {
                        current_cf = Double.parseDouble(cf_ET.getText().toString());
                    }
                    if (isDouble(t_ET.getText().toString())) {
                        current_t = Double.parseDouble(t_ET.getText().toString());
                    }
                    if(isDouble(r_ET.getText().toString())){
                        current_r = Double.parseDouble(r_ET.getText().toString())/100;
                    }
                    current_c0 = current_cf / (Math.pow(1.0 + current_r, current_t));
                    c0_ET.setText(String.format("%.2f", current_c0));
                }
                if (cf_CB.isChecked()) {
                    if(isDouble(c0_ET.getText().toString())) {
                        current_c0 = Double.parseDouble(c0_ET.getText().toString());
                    }
                    if (isDouble(t_ET.getText().toString())) {
                        current_t = Double.parseDouble(t_ET.getText().toString());
                    }
                    if(isDouble(r_ET.getText().toString())){
                        current_r = Double.parseDouble(r_ET.getText().toString())/100;
                    }
                    current_cf = current_c0 * (Math.pow(1.0 + current_r, current_t));
                    cf_ET.setText(String.format("%.2f", current_cf));
                }
                if (r_CB.isChecked()) {
                    if(isDouble(cf_ET.getText().toString())) {
                        current_cf = Double.parseDouble(cf_ET.getText().toString());
                    }
                    if (isDouble(c0_ET.getText().toString())) {
                        current_c0 = Double.parseDouble(c0_ET.getText().toString());
                    }
                    if(isDouble(t_ET.getText().toString())){
                        current_t = Double.parseDouble(t_ET.getText().toString());
                    }
                    current_r = -1.0+Math.exp((1.0/current_t)*Math.log(current_cf/current_c0));
                    r_ET.setText(String.format("%.2f", current_r * 100));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        r_ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (c0_CB.isChecked()) {
                    if(isDouble(cf_ET.getText().toString())) {
                        current_cf = Double.parseDouble(cf_ET.getText().toString());
                    }
                    if (isDouble(t_ET.getText().toString())) {
                        current_t = Double.parseDouble(t_ET.getText().toString());
                    }
                    if(isDouble(r_ET.getText().toString())){
                        current_r = Double.parseDouble(r_ET.getText().toString())/100;
                    }
                    current_c0 = current_cf / (Math.pow(1.0 + current_r, current_t));
                    c0_ET.setText(String.format("%.2f", current_c0));
                }
                if (cf_CB.isChecked()) {
                    if(isDouble(c0_ET.getText().toString())) {
                        current_c0 = Double.parseDouble(c0_ET.getText().toString());
                    }
                    if (isDouble(t_ET.getText().toString())) {
                        current_t = Double.parseDouble(t_ET.getText().toString());
                    }
                    if(isDouble(r_ET.getText().toString())){
                        current_r = Double.parseDouble(r_ET.getText().toString())/100;
                    }
                    current_cf = current_c0 * (Math.pow(1.0 + current_r, current_t));
                    cf_ET.setText(String.format("%.2f", current_cf));
                }
                if (t_CB.isChecked()) {
                    if(isDouble(cf_ET.getText().toString())) {
                        current_cf = Double.parseDouble(cf_ET.getText().toString());
                    }
                    if (isDouble(c0_ET.getText().toString())) {
                        current_c0 = Double.parseDouble(c0_ET.getText().toString());
                    }
                    if(isDouble(r_ET.getText().toString())){
                        current_r = Double.parseDouble(r_ET.getText().toString())/100;
                    }
                    current_t = Math.log(current_cf/current_c0)/Math.log(1.0+current_r);
                    t_ET.setText(String.format("%.2f", current_t));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return theView;
    }





    public static ReturnRateDialogFragment newInstance(){
        Bundle bundle = new Bundle();
        ReturnRateDialogFragment returnRateDialogFragment = new ReturnRateDialogFragment();
        returnRateDialogFragment.setArguments(bundle);
        return returnRateDialogFragment;
    }


    private boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }




}

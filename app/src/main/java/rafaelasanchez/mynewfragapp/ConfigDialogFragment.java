package rafaelasanchez.mynewfragapp;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import java.util.ArrayList;

/**
 * Created by R on 12/04/2016.
 */
public class ConfigDialogFragment extends DialogFragment {


    Integer period;
    Float fee;

    public static final String myAppKey = "Investor Playground";

    private ArrayList<Boolean> theBooleans = new ArrayList<>();
    private ArrayList<Integer> theIntegers = new ArrayList<>();
    private ArrayList<String> theIntegerKeys = new ArrayList<>();
    private ArrayList<String> theBooleanKeys = new ArrayList<>();
    private ArrayList<Integer> theIntDefValues = new ArrayList<>();
    private ArrayList<CheckBox> theCheckBoxes = new ArrayList<>();
    private ArrayList<EditText> theEditTexts = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View theView = inflater.inflate(R.layout.config_layout,container);
        getDialog().setTitle("Settings");


        // period

        final EditText period_ET  = (EditText) theView.findViewById(R.id.config_period_edit_text);
        period = startInteger("config_period_edit_text",14);
        period_ET.setText(String.valueOf(period));
        period_ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (charToInteger(s) != null) {
                    period = charToInteger(s);
                    getActivity()
                            .getSharedPreferences(myAppKey, Context.MODE_PRIVATE)
                            .edit()
                            .putInt("config_period_edit_text", period)
                            .commit();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        // fee

        final EditText fee_ET = (EditText) theView.findViewById(R.id.config_fees_edit_text);
        fee =startFloat("config_fees_edit_text",0.1f);
        fee_ET.setText(String.valueOf(fee));
        fee_ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (charToFloat(s) != null) {
                    fee = charToFloat(s);
                    getActivity()
                            .getSharedPreferences(myAppKey, Context.MODE_PRIVATE)
                            .edit()
                            .putFloat("config_fees_edit_text", fee)
                            .commit();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });





        // BUY FIELDS

        Integer p=0;

        // RSI =Relative Strength Index
        theBooleanKeys.add(p,"config_buying_RSI_checkbox");
        theIntegerKeys.add(p, "config_buying_RSI_edit_text");
        theIntDefValues.add(p, 30);
        theCheckBoxes.add(p, (CheckBox) theView.findViewById(R.id.config_buying_RSI_checkbox));
        theEditTexts.add(p, (EditText) theView.findViewById(R.id.config_buying_RSI_edit_text));

        // ADX =Average Directional Index
        p=p+1;
        theBooleanKeys.add(p,"config_buying_ADX_checkbox");
        theIntegerKeys.add(p,"config_buying_ADX_edit_text");
        theIntDefValues.add(p,30);
        theCheckBoxes.add(p, (CheckBox) theView.findViewById(R.id.config_buying_ADX_checkbox));
        theEditTexts.add(p,(EditText) theView.findViewById(R.id.config_buying_ADX_edit_text));

        // SL=Stop Loss
        p=p+1;
        theBooleanKeys.add(p,"config_buying_stop_loss_checkbox");
        theIntegerKeys.add(p,"config_buying_stop_loss_edit_text");
        theIntDefValues.add(p,15);
        theCheckBoxes.add(p, (CheckBox) theView.findViewById(R.id.config_buying_stop_loss_checkbox));
        theEditTexts.add(p, (EditText) theView.findViewById(R.id.config_buying_stop_loss_edit_text));



        // SELL FIELDS

        // RSI
        p=p+1;
        theBooleanKeys.add(p,"config_selling_RSI_checkbox");
        theIntegerKeys.add(p,"config_selling_RSI_edit_text");
        theIntDefValues.add(p,70);
        theCheckBoxes.add(p, (CheckBox) theView.findViewById(R.id.config_selling_RSI_checkbox));
        theEditTexts.add(p, (EditText) theView.findViewById(R.id.config_selling_RSI_edit_text));

        // ADX
        p=p+1;
        theBooleanKeys.add(p,"config_selling_ADX_checkbox");
        theIntegerKeys.add(p,"config_selling_ADX_edit_text");
        theIntDefValues.add(p,20);
        theCheckBoxes.add(p, (CheckBox) theView.findViewById(R.id.config_selling_ADX_checkbox));
        theEditTexts.add(p, (EditText) theView.findViewById(R.id.config_selling_ADX_edit_text));

        // SL
        p=p+1;
        theBooleanKeys.add(p,"config_selling_stop_loss_checkbox");
        theIntegerKeys.add(p,"config_selling_stop_loss_edit_text");
        theIntDefValues.add(p,15);
        theCheckBoxes.add(p, (CheckBox) theView.findViewById(R.id.config_selling_stop_loss_checkbox));
        theEditTexts.add(p, (EditText) theView.findViewById(R.id.config_selling_stop_loss_edit_text));


        // Get all the stuff set
        for (int pos=0; pos<theEditTexts.size(); pos++ ) {
            callMethods(pos);
        }

        return theView;
    }


    public static ConfigDialogFragment newInstance(){
        Bundle bundle = new Bundle();
        ConfigDialogFragment configDialogFragment = new ConfigDialogFragment();
        configDialogFragment.setArguments(bundle);
        return configDialogFragment;
    }

    
    private Integer charToInteger(CharSequence s){

        try {
            return Integer.parseInt(s.toString());
        } catch (NumberFormatException e) {
            return null;
        }

    }


    private Float charToFloat(CharSequence s){

        try {
            return Float.parseFloat(s.toString());
        } catch (NumberFormatException e) {
            return null;
        }

    }


    private boolean startBoolean(String theBooleanKey){

        boolean theBoolean;

        if (getActivity().getSharedPreferences(myAppKey, Context.MODE_PRIVATE)
                .contains(theBooleanKey)){
            theBoolean=getActivity().getSharedPreferences(myAppKey, Context.MODE_PRIVATE)
                    .getBoolean(theBooleanKey, true);
        } else {
            theBoolean=true;
        }

        return theBoolean;

    }


    private Integer startInteger(String theValueKey, Integer theDefaultValue){

        Integer theValue;

        if (getActivity().getSharedPreferences(myAppKey, Context.MODE_PRIVATE)
                .contains(theValueKey)){
            theValue=getActivity().getSharedPreferences(myAppKey, Context.MODE_PRIVATE)
                    .getInt(theValueKey, -1);
        } else {
            theValue=theDefaultValue;
        }

        return theValue;

    }


    private Float startFloat(String theValueKey, Float theDefaultValue){

        Float theValue;

        if (getActivity().getSharedPreferences(myAppKey, Context.MODE_PRIVATE)
                .contains(theValueKey)){
            theValue=getActivity().getSharedPreferences(myAppKey, Context.MODE_PRIVATE)
                    .getFloat(theValueKey, -1.f);
        } else {
            theValue=theDefaultValue;
        }

        return theValue;

    }


    private void setEditTextListener(final Integer thePosition, EditText theEditText){

        theEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (charToInteger(s) != null) {
                    theIntegers.set(thePosition, charToInteger(s));
                    getActivity()
                            .getSharedPreferences(myAppKey, Context.MODE_PRIVATE)
                            .edit()
                            .putInt(theIntegerKeys.get(thePosition), theIntegers.get(thePosition))
                            .commit();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }


    private void setCheckBoxListener(final Integer thePosition,
                              CheckBox theCheckBox,
                              final EditText theEditText){


        theCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                theBooleans.set(thePosition, isChecked);
                getActivity()
                        .getSharedPreferences(myAppKey, Context.MODE_PRIVATE)
                        .edit()
                        .putBoolean(theBooleanKeys.get(thePosition), theBooleans.get(thePosition))
                        .commit();
                theEditText.setEnabled(isChecked);
            }
        });

    }


    private void callMethods(Integer thePosition){

        //Start the CheckBox by retrieving the last value or setting the default
        theBooleans.add(thePosition, startBoolean(theBooleanKeys.get(thePosition)));
        theCheckBoxes.get(thePosition).setChecked(theBooleans.get(thePosition));
        //Add a listener to the CheckBox
        setCheckBoxListener(thePosition, theCheckBoxes.get(thePosition), theEditTexts.get(thePosition));
        //Start the value
        theIntegers.add(thePosition, startInteger(theIntegerKeys.get(thePosition), theIntDefValues.get(thePosition)));
        theEditTexts.get(thePosition).setText(String.valueOf(theIntegers.get(thePosition)));
        //Initially, enable or disable the EditText according to the Checkbox
        theEditTexts.get(thePosition).setEnabled(theBooleans.get(thePosition));
        //Add a listener for the ExitText
        setEditTextListener(thePosition, theEditTexts.get(thePosition));

    }




}

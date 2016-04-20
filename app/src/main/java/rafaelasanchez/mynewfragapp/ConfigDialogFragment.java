package rafaelasanchez.mynewfragapp;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
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

import java.util.ArrayList;

/**
 * Created by R on 12/04/2016.
 */
public class ConfigDialogFragment extends DialogFragment {


    private Integer period;
    private Float fee;
    private ArrayList<Boolean> theBooleans = new ArrayList<>();
    private ArrayList<Integer> theIntegers = new ArrayList<>();
    private ArrayList<CheckBox> theCheckBoxes = new ArrayList<>();
    private ArrayList<EditText> theEditTexts = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View theView = inflater.inflate(R.layout.config_layout,container);
        getDialog().setTitle("Settings");

        //Retrieve constants and last/default values from UserValues
        theBooleans=((MainActivity)getActivity()).userValues.getTheBooleans();
        theIntegers=((MainActivity)getActivity()).userValues.getTheIntegers();


        // period field

        final EditText period_ET  = (EditText) theView.findViewById(R.id.config_period_edit_text);
        period = ((MainActivity)getActivity()).userValues.getPeriod();
        period_ET.setText(String.valueOf(period));
        period_ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (charToInteger(s) != null) {
                    period = charToInteger(s);
                    ((MainActivity) getActivity()).userValues.setPeriod(period);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        // fee field

        final EditText fee_ET = (EditText) theView.findViewById(R.id.config_fees_edit_text);
        fee = ((MainActivity)getActivity()).userValues.getFee();
        fee_ET.setText(String.valueOf(fee));
        fee_ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (charToFloat(s) != null) {
                    fee = charToFloat(s);
                    ((MainActivity)getActivity()).userValues.setFee(fee);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        // BUY FIELDS:
        Integer p=0;

        // RSI =Relative Strength Index
        theCheckBoxes.add(p, (CheckBox) theView.findViewById(R.id.config_buying_RSI_checkbox));
        theEditTexts.add(p, (EditText) theView.findViewById(R.id.config_buying_RSI_edit_text));

        // ADX =Average Directional Index
        p=p+1;
        theCheckBoxes.add(p, (CheckBox) theView.findViewById(R.id.config_buying_ADX_checkbox));
        theEditTexts.add(p,(EditText) theView.findViewById(R.id.config_buying_ADX_edit_text));

        // SL=Stop Loss
        p=p+1;
        theCheckBoxes.add(p, (CheckBox) theView.findViewById(R.id.config_buying_stop_loss_checkbox));
        theEditTexts.add(p, (EditText) theView.findViewById(R.id.config_buying_stop_loss_edit_text));

        // SELL FIELDS
        // RSI
        p=p+1;
        theCheckBoxes.add(p, (CheckBox) theView.findViewById(R.id.config_selling_RSI_checkbox));
        theEditTexts.add(p, (EditText) theView.findViewById(R.id.config_selling_RSI_edit_text));

        // ADX
        p=p+1;
        theCheckBoxes.add(p, (CheckBox) theView.findViewById(R.id.config_selling_ADX_checkbox));
        theEditTexts.add(p, (EditText) theView.findViewById(R.id.config_selling_ADX_edit_text));

        // SL
        p=p+1;
        theCheckBoxes.add(p, (CheckBox) theView.findViewById(R.id.config_selling_stop_loss_checkbox));
        theEditTexts.add(p, (EditText) theView.findViewById(R.id.config_selling_stop_loss_edit_text));


        // Get all the fields set
        for (int pos=0; pos<theEditTexts.size(); pos++ ) {
            callMethods(pos);
        }


        addPopUps(theView);


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


    private void setEditTextListener(final Integer thePosition, EditText theEditText){

        theEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (charToInteger(s) != null) {
                    theIntegers.set(thePosition, charToInteger(s));
                    ((MainActivity)getActivity()).userValues
                            .setTheIntegers(thePosition, theIntegers.get(thePosition));
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
                ((MainActivity) getActivity()).userValues
                        .setTheBooleans(thePosition, isChecked);
                theEditText.setEnabled(isChecked);
            }
        });

    }


    private void callMethods(Integer thePosition){

        //Start the CheckBox in the DialogFragment
        theCheckBoxes.get(thePosition).setChecked(theBooleans.get(thePosition));
        //Add a listener to the CheckBox
        setCheckBoxListener(thePosition, theCheckBoxes.get(thePosition), theEditTexts.get(thePosition));
        //Start the EditText box in the DialogFragment
        theEditTexts.get(thePosition).setText(String.valueOf(theIntegers.get(thePosition)));
        //Initially, enable or disable the EditText according to the Checkbox
        theEditTexts.get(thePosition).setEnabled(theBooleans.get(thePosition));
        //Add a listener for the ExitText
        setEditTextListener(thePosition, theEditTexts.get(thePosition));

    }


    private void addPopUps(View theView){

        // Question marks
        final ImageView q_RSI_buy = (ImageView) theView.findViewById(R.id.q_RSI_buy);
        q_RSI_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                PopUpDialogFragment popUpDialogFragment =
                        PopUpDialogFragment
                                .newInstance(getString(R.string.q_RSI_buy_text_view));
                popUpDialogFragment.setTargetFragment(ConfigDialogFragment.this,0);
                popUpDialogFragment.show(fragmentManager,"");
            }
        });

        final ImageView q_ADX_buy = (ImageView) theView.findViewById(R.id.q_ADX_buy);
        q_ADX_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                PopUpDialogFragment popUpDialogFragment =
                        PopUpDialogFragment
                                .newInstance(getString(R.string.q_ADX_buy_text_view));
                popUpDialogFragment.setTargetFragment(ConfigDialogFragment.this,0);
                popUpDialogFragment.show(fragmentManager,"");
            }
        });

        final ImageView q_SL_buy = (ImageView) theView.findViewById(R.id.q_SL_buy);
        q_SL_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                PopUpDialogFragment popUpDialogFragment =
                        PopUpDialogFragment
                                .newInstance(getString(R.string.q_SL_buy_text_view));
                popUpDialogFragment.setTargetFragment(ConfigDialogFragment.this,0);
                popUpDialogFragment.show(fragmentManager,"");
            }
        });

        final ImageView q_RSI_sell = (ImageView) theView.findViewById(R.id.q_RSI_sell);
        q_RSI_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                PopUpDialogFragment popUpDialogFragment =
                        PopUpDialogFragment
                                .newInstance(getString(R.string.q_RSI_sell_text_view));
                popUpDialogFragment.setTargetFragment(ConfigDialogFragment.this,0);
                popUpDialogFragment.show(fragmentManager,"");
            }
        });

        final ImageView q_ADX_sell = (ImageView) theView.findViewById(R.id.q_ADX_sell);
        q_ADX_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                PopUpDialogFragment popUpDialogFragment =
                        PopUpDialogFragment
                                .newInstance(getString(R.string.q_ADX_sell_text_view));
                popUpDialogFragment.setTargetFragment(ConfigDialogFragment.this,0);
                popUpDialogFragment.show(fragmentManager,"");
            }
        });

        final ImageView q_SL_sell = (ImageView) theView.findViewById(R.id.q_SL_sell);
        q_SL_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                PopUpDialogFragment popUpDialogFragment =
                        PopUpDialogFragment
                                .newInstance(getString(R.string.q_SL_sell_text_view));
                popUpDialogFragment.setTargetFragment(ConfigDialogFragment.this,0);
                popUpDialogFragment.show(fragmentManager,"");
            }
        });


    }


}

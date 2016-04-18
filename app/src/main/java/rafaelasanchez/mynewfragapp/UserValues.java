package rafaelasanchez.mynewfragapp;

import android.content.Context;
import android.util.Log;

/**
 * Created by R on 18/04/2016.
 */
public class UserValues {

    private Integer period;
    private Float fee;
    public static final String myAppKey = "Investor Playground";

    Context context;


    public UserValues(Context context_) {
        context=context_;

        //period
        if (context.getSharedPreferences(myAppKey, Context.MODE_PRIVATE)
                .contains("config_period_edit_text")){
            period=context.getSharedPreferences(myAppKey, Context.MODE_PRIVATE)
                    .getInt("config_period_edit_text", -1);
        } else {
            period=14;
        }

        //fee
        if (context.getSharedPreferences(myAppKey, Context.MODE_PRIVATE)
                .contains("config_fees_edit_text")){
            fee=context.getSharedPreferences(myAppKey, Context.MODE_PRIVATE)
                    .getFloat("config_fees_edit_text", -1.f);
        } else {
            fee=0.1f;
        }

    }


    public Float getFee() {
        return fee;
    }


    public void setFee(Float fee_) {
        this.fee = fee_;
        context.getSharedPreferences(myAppKey, Context.MODE_PRIVATE)
                .edit()
                .putFloat("config_fees_edit_text", fee)
                .commit();

    }


    public Integer getPeriod(){
        return period;
    }


    public void setPeriod(Integer period_) {
        this.period = period_;
        context.getSharedPreferences(myAppKey, Context.MODE_PRIVATE)
                .edit()
                .putInt("config_period_edit_text", period)
                .commit();
    }
}

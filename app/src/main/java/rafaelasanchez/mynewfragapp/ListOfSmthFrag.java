package rafaelasanchez.mynewfragapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;


/**
 * Created by rafa on 2/9/16.
 */
public class ListOfSmthFrag extends DialogFragment{

    public static final String SELECTED_FINANCIAL_INSTRUMENT = "rafaelasanchez.mynewfragapp.selected_financial_instrument";
    public static final String THE_COMPANIES = "The companies";
    public static final String THE_NAMES = "The names";
    public static final String THE_TITLE = "The title";
    private String[] theCompaniesArray;
    private String[] theNamesArray;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        theCompaniesArray = (String[]) getArguments().getSerializable(THE_COMPANIES);
        theNamesArray = (String[]) getArguments().getSerializable(THE_NAMES);
        String theTitle= (String) getArguments().getSerializable(THE_TITLE);

        final View theView = getActivity().getLayoutInflater()
                .inflate(R.layout.list_view_layout, null);

        ListAdapter listAdapter =
                new ArrayAdapter<>(getActivity(),android.R.layout.select_dialog_item,theNamesArray);
        final ListView theListView = (ListView) theView.findViewById(R.id.the_list_view);
        theListView.setAdapter(listAdapter);
        theListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String theCompany = theCompaniesArray[position];
                getArguments().putSerializable(SELECTED_FINANCIAL_INSTRUMENT, theCompany);
                Log.e("look at this shit", theCompany);
                if (getTargetFragment() == null) return;
                Intent intent = new Intent();
                intent.putExtra(SELECTED_FINANCIAL_INSTRUMENT, theCompany);
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                dismiss();
            }
        });


        return new AlertDialog.Builder(getActivity())
                .setView(theView)
                .setTitle(theTitle)
/*                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK);
                    }
                })
                */
                .create();
    }

    public static ListOfSmthFrag newInstance(String[] theListofSomethings, String[] theListofNamesOfThoseSomethings, String theTitle) {

        Bundle dataPassed = new Bundle();
        dataPassed.putSerializable(THE_COMPANIES, theListofSomethings);
        dataPassed.putSerializable(THE_NAMES, theListofNamesOfThoseSomethings);
        dataPassed.putSerializable(THE_TITLE, theTitle);
        ListOfSmthFrag listOfSmthFrag = new ListOfSmthFrag();
        listOfSmthFrag.setArguments(dataPassed);
        return listOfSmthFrag;
    }





}

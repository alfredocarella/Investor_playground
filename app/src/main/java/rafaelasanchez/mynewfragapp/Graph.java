package rafaelasanchez.mynewfragapp;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

/**
 * Created by R on 20/04/2016.
 */
public class Graph extends View {

    private View theView;
    private Context context;


    public Graph(Context context_) {
        super(context_);
        context=context_;
    }

    public void newInstance(SimpleUserValues values) {

        final ArrayList<Float> theData;
        Integer theCurrentGraph = values.getTheCurrentGraph();

        if(theCurrentGraph==2){
            theData=values.getStrategyResult();
        }else{
            theData=values.getArrayList();
        }

        final ArrayList<ArrayList<Integer>> theDates=values.getTheDates();
        final Integer theCurrentFragment=values.getTheCurrentFragment();
        final FrameLayout theFrameLayout;
        final FrameLayout theFragmentFrame;

        final Activity activity = (Activity) context;
        theView = activity.getLayoutInflater().inflate(R.layout.graph_layout,null);

        //Link with the objects in the xml layout:
        final TextView xAxisStart = (TextView) theView.findViewById(R.id.graph_x_axis_start_text_view);
        final TextView xAxisEnd = (TextView) theView.findViewById(R.id.graph_x_axis_end_text_view);
        final TextView yAxisMin = (TextView) theView.findViewById(R.id.graph_y_axis_min_text_view);
        final TextView yAxisMax = (TextView) theView.findViewById(R.id.graph_y_axis_max_text_view);
        theFrameLayout = (FrameLayout) theView.findViewById(R.id.graph_plot_container_frame_layout);

        if(theCurrentFragment==1) {
            if(theCurrentGraph==1) {
                theFragmentFrame = (FrameLayout) ((Activity) context)
                        .findViewById(R.id.prices_1st_frag_the_outer_container);
            }else {
                theFragmentFrame = (FrameLayout) ((Activity) context)
                        .findViewById(R.id.strategy_1st_frag_the_outer_container);
            }
            theFragmentFrame.removeAllViews();
            theFragmentFrame.setVisibility(View.VISIBLE);

            ViewGroup.LayoutParams params = theFragmentFrame.getLayoutParams();
            DisplayMetrics metrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            params.height = Math.round(Math.min((float) 0.5 * metrics.heightPixels, (float) 0.6 * metrics.widthPixels));
            theFragmentFrame.setLayoutParams(params);
        }


        ViewTreeObserver vto = theFrameLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                    theFrameLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    theFrameLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }


                //Work out the x&y axes:
                double minDouble = -1;
                double maxDouble = 0;
                try {
                    minDouble = Collections.min(theData).doubleValue();
                    maxDouble = Collections.max(theData).doubleValue();
                } catch (NoSuchElementException e) {
                    e.printStackTrace();
                }

                DecimalFormat precision = new DecimalFormat("0.00E0");

                xAxisEnd.setText(theDates.get(0).get(0).toString() + "/" + theDates.get(1).get(0).toString() + "/" + theDates.get(2).get(0).toString());
                xAxisStart.setText(theDates.get(0).get(theDates.get(0).size() - 1).toString() + "/" + theDates.get(1).get(theDates.get(1).size() - 1).toString() + "/" + theDates.get(2).get(theDates.get(2).size() - 1).toString());
                yAxisMin.setText(precision.format(minDouble));
                yAxisMax.setText(precision.format(maxDouble));

                int dyPlot = theFrameLayout.getHeight();
                int dxPlot = theFrameLayout.getWidth();


                //Add the plot points
                LinePlot linePlot = new LinePlot(activity.getApplicationContext());
                linePlot.addPoints(dxPlot, dyPlot, theData);
                theFrameLayout.addView(linePlot);


            }
        });



    }






    public View getTheGraph() {
        return theView;
    }


}

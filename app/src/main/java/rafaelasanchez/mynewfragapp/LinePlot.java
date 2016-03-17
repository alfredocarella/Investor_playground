package rafaelasanchez.mynewfragapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by rafa on 2/13/16.
 */
public class LinePlot extends View {

    private ArrayList<Float> mPoints;
    private int dx;
    private int dy;

    Paint paint=new Paint();

    public LinePlot(Context context) {
        super(context);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int radius=3;
        // Use Color.parseColor to define HTML colors
        Iterator<Float> it = mPoints.iterator();
        int i = 0;
        int mlength=mPoints.size();
        if(mlength>0) {
            Float mmin= Collections.min(mPoints);
            Float mmax= Collections.max(mPoints);

            Float dxdp = (1.0f * dx) / mlength;
            while (it.hasNext()) {
                Float p = it.next();
//                paint.setColor(Color.parseColor("#CD5C5C") + 0x40 * i);
                paint.setColor(Color.YELLOW);
                canvas.drawCircle(  Math.round(dxdp * (mlength - (i + 1))),
                                    Math.round(dy * (mmax - p) / (mmax - mmin)),
                        radius, paint);
//                Log.e("x,y= ", String.valueOf(dxdp * (mlength - (i + 1))) + ";  " + String.valueOf(dy * (mmax - p) / (mmax - mmin)));
                i++;
            }
            Log.e("LinePlot.OnDraw.dxdp","dxdp= " + String.valueOf(dxdp) + ";  dx= " +String.valueOf(dx) + ";  dy= " + String.valueOf(dy) + ";  min= " + String.valueOf(mmin) + ";  max= " +String.valueOf(mmax) + ";  i= " +String.valueOf(i));
        }
    }


    public void addPoints(int dx_plot, int dy_plot, ArrayList<Float> puntos){

        mPoints=puntos;
        dx=dx_plot;
        dy=dy_plot;
    }




}




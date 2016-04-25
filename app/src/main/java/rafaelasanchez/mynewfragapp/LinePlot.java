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

        Float radius=2.5f;
        // Use Color.parseColor to define HTML colors
        Iterator<Float> it = mPoints.iterator();
        int mLength=mPoints.size();
        if(mLength>0) {
            Float mmin= Collections.min(mPoints);
            Float mmax= Collections.max(mPoints);
            Float dxdp = (1.0f * dx) / mLength;
            Float pPrevious=0.f;
            paint.setColor(Color.YELLOW);
            int i = 0;
            while (it.hasNext()) {
                if(i==0){
                    pPrevious = it.next();
                }else {
                    Float p = it.next();
                    Float startX = dxdp * (mLength - i);
                    Float startY = dy * (mmax - pPrevious) / (mmax - mmin);
                    Float stopX = dxdp * (mLength - (i + 1));
                    Float stopY = dy * (mmax - p) / (mmax - mmin);
                    canvas.drawLine(startX,startY,stopX,stopY,paint);
                    pPrevious=p;
                }
                canvas.drawCircle(dxdp * (mLength - (i + 1)),
                        dy * (mmax - pPrevious) / (mmax - mmin),
                        radius, paint);
                i++;
            }
        }
    }






    public void addPoints(int dx_plot, int dy_plot, ArrayList<Float> puntos){

        mPoints=puntos;
        dx=dx_plot;
        dy=dy_plot;
    }




}




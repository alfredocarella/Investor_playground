package rafaelasanchez.mynewfragapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.View;

import java.util.Iterator;
import java.util.List;

/**
 * Created by rafa on 2/13/16.
 */
public class Grafica extends View {

    private List<Point> mPoints;

    public Grafica(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int x=80;
        int y=80;
        int radius=40;
        Paint paint=new Paint();
        // Use Color.parseColor to define HTML colors
        paint.setColor(Color.parseColor("#CD5C5C"));
        canvas.drawCircle(x, x, radius, paint);

        Iterator<Point> it = mPoints.iterator();
        int i = 1;
        while(it.hasNext()) {
            Point p = it.next();

            paint.setColor(Color.parseColor("#CD5C5C") + 0x40 * i);
            canvas.drawCircle(p.x, p.y, radius, paint);
            i++;
        }
    }

    public void addPoints(List<Point> puntos) {
        mPoints = puntos;
    }


}




/**

Create una clase Grafica

package com.perseum.datepicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.View;

import java.util.Iterator;
import java.util.List;



public class Grafica extends View {

    private List<Point> mPoints;

    public Grafica(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        int x=80;
        int y=80;
        int radius=40;
        Paint paint=new Paint();
        // Use Color.parseColor to define HTML colors
        paint.setColor(Color.parseColor("#CD5C5C"));
        canvas.drawCircle(x, x, radius, paint);

        Iterator<Point> it = mPoints.iterator();
        int i = 1;
        while(it.hasNext()) {
            Point p = it.next();


            paint.setColor(Color.parseColor("#CD5C5C") + 0x40 * i);
            canvas.drawCircle(p.x, p.y, radius, paint);
            i++;
        }
    }

    public void addPoints(List<Point> puntos) {
        mPoints = puntos;
    }
}

Añade un Frame layout "graficaContainer" donde quieras mostrar la grafica y con el tamaño que quieras

<FrameLayout
android:layout_width="200dp"
        android:layout_height="200dp" android:id="@+id/graficaContainer"
        android:layout_below="@+id/lafecha" android:layout_alignParentLeft="true"
        android:layout_alignParentStart="true" android:layout_alignParentRight="true"
        android:layout_alignParentEnd="true">
</FrameLayout>

        En el MainActivity añade la grafica a la jerarquia en el onCreate

        mGrafica = new Grafica(this);

        ArrayList<Point> list = new ArrayList<Point>();
        list.add(new Point(50,50));
        list.add(new Point(100,100));
        list.add(new Point(150,150));

        mGrafica.addPoints(list);

        ViewGroup viewGroup = (ViewGroup)findViewById(R.id.graficaContainer);
        viewGroup.addView(mGrafica);
 */
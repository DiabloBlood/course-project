package edu.scu.shuang1.photonotes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Blood on 2016/6/1.
 */
public class TouchDrawView extends View {
    //The Paint class holds the style and color information about how to draw geometries, text and bitmaps
    Paint paint = new Paint();
    List<Point> points = new ArrayList<>();

    public TouchDrawView(Context context) {
        super(context);
        paint.setColor(Color.RED);
    }

    public TouchDrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.RED);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Point point = new Point();
        point.x = (int)event.getX();
        point.y = (int)event.getY();
        points.add(point);

        invalidate();

        Log.d("jsun", "point: " + point.x + "," + point.y + ", action: " + event.getAction());
        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        for (Point point : points) {
            canvas.drawCircle(point.x, point.y, 10, paint);
        }
    }

    public void clear() {
        points= new ArrayList<>();
        invalidate();
    }
}

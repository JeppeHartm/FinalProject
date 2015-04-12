package pdi.group14.finalproject.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import pdi.group14.finalproject.model.Item;
import pdi.group14.finalproject.model.Utilities;

/**
 * Created by Jeppe on 07-04-2015.
 */
public class ItemView extends View {
    Item item;
    Paint paintBucketText;
    Paint paintBucketBackground;
    Paint paintBucketBorder;
    public ItemView(Context context,AttributeSet attributeSet, Item item) {
        super(context,attributeSet);
        this.item = item;
        //buckets are created once on construction to limit calculations at paint
        Paint[] buckets = Utilities.createPaint(this.item);
        this.paintBucketText = buckets[0];
        this.paintBucketBackground = buckets[1];
        this.paintBucketBorder = buckets[2];
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        Rect bounds = Utilities.getBounds(this.item);
        canvas.drawRect(bounds,paintBucketBackground);
        canvas.drawRect(bounds,paintBucketBorder);
        canvas.drawText(item.getText(),0.5f,0.5f,paintBucketText);
    }

}

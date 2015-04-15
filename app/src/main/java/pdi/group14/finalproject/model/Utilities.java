package pdi.group14.finalproject.model;

import android.graphics.Paint;
import android.graphics.Rect;

import pdi.group14.finalproject.views.ItemView;

/**
 * Created by Jeppe on 07-04-2015.
 */
public class Utilities {
    public static Paint[] createPaint(Item item) {
        //Text
        Paint p1 = new Paint();
        p1.setARGB(0xff,0xff,0xff,0xff);
        //Background
        Paint p2 = new Paint();
        p2.setStyle(Paint.Style.FILL);
        p2.setARGB(0xff,0xff,0x00,0x00);
        //Border
        Paint p3 = new Paint();
        p3.setStyle(Paint.Style.STROKE);
        p3.setARGB(0xff,0x00,0xff,0xff);
        return new Paint[]{p1,p2,p3};
    }

    public static Rect getBounds(Item item) {
        Rect bounds = new Rect(0,0,30,30);
        return bounds;
    }

    public static Item findItem(String query) {
        return new Item("test");
    }

    public static ItemView[] sort(ItemView[] ivs) {
        return ivs;
    }
}

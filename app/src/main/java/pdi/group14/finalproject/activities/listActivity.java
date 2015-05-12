package pdi.group14.finalproject.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import pdi.group14.finalproject.R;
import pdi.group14.finalproject.views.ListItemView;

/**
 * Created by jeppe on 5/6/15.
 */
public class listActivity extends Activity {

    Activity self = this;
    ListItemView current;
    LinearLayout root;
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_screen);
        root = (LinearLayout) findViewById(R.id.root_layout);
        root.setPadding(10, 10, 10, 10);
        root.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);

        addItem();
    }

    public void addItem(){
        ListItemView liv = new ListItemView(this);
        root.addView(liv);
        liv.initiate();
        root.invalidate();
        if (current != null) current.setUnEditable();
        liv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(self, "lol", Toast.LENGTH_SHORT).show();
                if (current != null) current.setUnEditable();
                if (v.getParent() instanceof ListItemView) {
                    current = ((ListItemView) v.getParent());
                } else if (v instanceof ListItemView) {
                    current = (ListItemView) v;
                }
                current.setEditable();
                current.invalidate();
            }
        });
        if(current != null) current.setUnEditable();
        liv.setEditable();
        current = liv;
    }
}

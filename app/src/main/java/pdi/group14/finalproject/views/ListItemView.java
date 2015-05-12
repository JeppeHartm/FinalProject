package pdi.group14.finalproject.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.transition.Visibility;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

import pdi.group14.finalproject.R;
import pdi.group14.finalproject.activities.listActivity;

/**
 * Created by jeppe on 5/6/15.
 */
public class ListItemView extends RelativeLayout {
    public static int IDS = 1337;
    View tv;
    ImageView mainbutton;
    ImageView secondarybutton;
    public ListItemView(Context context) {
        super(context);
        tv = new EditText(getContext());

        mainbutton = new ImageView(getContext());
        secondarybutton = new ImageView(getContext());
//        mainbutton.setBackgroundColor(Color.BLACK);
//        secondarybutton.setBackgroundColor(Color.BLACK);

    }



    public void initiate(){


        secondarybutton.setImageResource(R.mipmap.mag);
        mainbutton.setImageResource(R.mipmap.plus);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)getLayoutParams();
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.setMargins(2,2,2,2);

        setLayoutParams(lp);

        //setBackgroundColor(Color.LTGRAY);
        //this.setBackgroundColor(Color.BLACK);
        this.addView(secondarybutton);
        this.addView(tv);
        this.addView(mainbutton);
        assignLParams(secondarybutton);
        assignLParams(mainbutton);
        assignLParams(tv);

    }
    private void assignLParams(View v) {
        LayoutParams lp = (LayoutParams) v.getLayoutParams();
        if(v == tv){
            lp.addRule(CENTER_HORIZONTAL,TRUE);
            lp.width = 200;


        }else if(v == secondarybutton){
            lp.addRule(ALIGN_PARENT_LEFT,TRUE);
            lp.addRule(CENTER_VERTICAL,TRUE);
            lp.height = 30;
            lp.width = lp.WRAP_CONTENT;
        }else if(v == mainbutton){
            lp.addRule(ALIGN_PARENT_RIGHT,TRUE);
            lp.addRule(CENTER_VERTICAL,TRUE);
            lp.height = 30;
            lp.setMargins(0,0,5,0);
            lp.width = lp.WRAP_CONTENT;
        }
        v.setLayoutParams(lp);
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        /*LayoutParams lp = (LayoutParams) tv.getLayoutParams();
        if(gainFocus){
            String text = (String) ((TextView)tv).getText();
            tv = new EditText(getContext());
            ((EditText)tv).setText(text);
        }else{
            String text = (String) ((EditText)tv).getText().toString();
            tv = new TextView(getContext());
            ((TextView)tv).setText(text);
        }
        tv.setLayoutParams(lp);*/
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setUnEditable() {

        LayoutParams lp = (LayoutParams) tv.getLayoutParams();
        String text = (String) ((TextView)tv).getText().toString();
        if(text.equals("")){
            ((LinearLayout)getParent()).removeView(this);
            return;
        }
        removeView(tv);
        tv = new TextView(getContext());
        ((TextView)tv).setText(text);
        addView(tv);
        ((TextView) tv).setGravity(Gravity.CENTER);
        secondarybutton.setVisibility(INVISIBLE);
        mainbutton.setVisibility(INVISIBLE);
        mainbutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addButton(v);
            }
        });
        tv.setLayoutParams(lp);
    }
    public void setEditable() {
        LayoutParams lp = (LayoutParams) tv.getLayoutParams();
        String text = (String) ((TextView)tv).getText().toString();
        removeView(tv);
        tv = new EditText(getContext());
        ((EditText)tv).setText(text);
        addView(tv);
        secondarybutton.setVisibility(VISIBLE);
        mainbutton.setVisibility(VISIBLE);
        tv.setLayoutParams(lp);
        setEnabled(true);
    }
    public void addButton(View view){
        if(view == mainbutton) {
            setUnEditable();
            ((listActivity) getContext()).addItem();
        }
    }
}


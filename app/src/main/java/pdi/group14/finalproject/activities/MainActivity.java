package pdi.group14.finalproject.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.Random;

import pdi.group14.finalproject.R;
import pdi.group14.finalproject.layouts.PrioritySoup;
import pdi.group14.finalproject.model.Item;
import pdi.group14.finalproject.model.ShoppingList;
import pdi.group14.finalproject.model.Utilities;
import pdi.group14.finalproject.views.ItemView;


public class MainActivity extends ActionBarActivity {
    ShoppingList shoppingList;
    PrioritySoup ps;
    public MainActivity() {
        super();
        shoppingList = new ShoppingList();
        Utilities.setSl(shoppingList);

    }
    public void addItem(String query){
        Item i = shoppingList.addItem(query);
        if(i == null)return;
        ItemView iv = new ItemView(this,null,i);
        ps.addView(iv);

//        RelativeLayout.LayoutParams lp;
//        lp = (RelativeLayout.LayoutParams)findViewById(R.id.defView).getLayoutParams();
//        lp.addRule(RelativeLayout.VISIBLE,RelativeLayout.TRUE);
//        iv.setLayoutParams(lp);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FrameLayout fl = (FrameLayout) findViewById(R.id.MainLayout);
        ps = new PrioritySoup(this);
        fl.addView(ps);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void debug(View view){
        String q = "test";
        for(int i = 0; i< 10; i++){
            addItem(q+i);
        }
    }
    public void openType(View view){
        findViewById(R.id.typing_layout).setVisibility(View.VISIBLE);
    }
}

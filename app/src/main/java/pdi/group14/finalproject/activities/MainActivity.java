package pdi.group14.finalproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Random;

import pdi.group14.finalproject.R;
import pdi.group14.finalproject.model.Item;
import pdi.group14.finalproject.model.ShoppingList;
import pdi.group14.finalproject.model.Utilities;
import pdi.group14.finalproject.views.ItemView;
import pdi.group14.finalproject.views.ListItemView;


public class MainActivity extends Activity {
    ShoppingList shoppingList;
    LinearLayout ll;
    public MainActivity() {
        super();
        shoppingList = new ShoppingList();
        Utilities.setSl(shoppingList);
    }
    public void addItem(String query){
        Item i = shoppingList.addItem(query);
        if(i == null)return;
        ItemView iv = new ItemView(this,null,i);
        ll.addView(iv);

//        RelativeLayout.LayoutParams lp;
//        lp = (RelativeLayout.LayoutParams)findViewById(R.id.defView).getLayoutParams();
//        lp.addRule(RelativeLayout.VISIBLE,RelativeLayout.TRUE);
//        iv.setLayoutParams(lp);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ll = (LinearLayout) findViewById(R.id.ShopItemLayout);

    }



    public void debug(View view){
//        String q = "test";
//        for(int i = 0; i< 10; i++){
//            addItem(q+i);
//        }
        startActivity(new Intent(this,listActivity.class));
        //addItem();
    }
    /*public void openType(View view){
        findViewById(R.id.typing_layout).setVisibility(View.VISIBLE);
    }*/
}

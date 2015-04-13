package pdi.group14.finalproject.model;

import java.util.ArrayList;

/**
 * Created by jeppe on 18-03-15.
 */
public class ShoppingList {
    ArrayList<Item> items;

    public ShoppingList(){
        initialize();
    }

    private void initialize() {
        items = new ArrayList<Item>();
    }

    public Item addItem(String query){
        Item item = Utilities.findItem(query);
        items.add(item);
        return item;
    }


}

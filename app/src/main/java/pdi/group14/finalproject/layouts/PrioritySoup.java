package pdi.group14.finalproject.layouts;

import android.content.Context;
import android.view.ViewGroup;

import java.util.ArrayList;

import pdi.group14.finalproject.model.Utilities;
import pdi.group14.finalproject.views.ItemView;

/**
 * Created by jeppe on 15-04-15.
 */
public class PrioritySoup extends ViewGroup {
    enum EdgeOrient {horizontal,vertical}
    public PrioritySoup(Context context) {
        super(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        ItemView[] ivs = new ItemView[count];
        for( int i = 0; i < count; i++){
            ivs[i] = (ItemView)getChildAt(i);
        }
        ivs = Utilities.sort(ivs);

    }
    class Cluster{
        ArrayList<Edge> freeEdges;
        ArrayList<ItemView> itemViews;
        Point origin;
        public Cluster(ItemView initial,int x, int y){
            freeEdges = new ArrayList<Edge>();
            itemViews = new ArrayList<ItemView>();
            origin = new Point(x,y);
            int height = initial.getHeight();
            int width = initial.getWidth();
            Point ul = new Point(origin.x-(width/2), origin.y-(width/2)); //upper left
            Point ur = new Point(origin.x+(width/2), origin.y-(width/2)); //upper right
            Point lr = new Point(origin.x+(width/2), origin.y+(width/2)); //lower right
            Point ll = new Point(origin.x-(width/2), origin.y+(width/2)); //lower left
            itemViews.add(initial);
            freeEdges.add(new Edge(ul,ur));//top
            freeEdges.add(new Edge(ll,lr));//bot
            freeEdges.add(new Edge(ul,ll));//left
            freeEdges.add(new Edge(ur,lr));//left
        }
        private ArrayList<Edge> closestEdgeThatFits(int width,int height){
            for(int i = 0; i < freeEdges.size(); i++){

            }
            return null;
        }
        class Point{
            public int x,y;
            public Point(int x,int y){
                this.x = x;
                this.y = y;
            }
        }
        class Edge{
            public Edge(Point p1, Point p2){

                this.p1 = p1;
                this.p2 = p2;
            }
            public EdgeOrient getOrientation(){
                return p1.x==p2.x?EdgeOrient.vertical:EdgeOrient.horizontal;
            }
            public Point p1, p2;
        }
    }
}

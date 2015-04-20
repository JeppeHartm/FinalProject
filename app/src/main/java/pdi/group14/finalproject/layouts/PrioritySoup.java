package pdi.group14.finalproject.layouts;

import android.content.Context;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;

import pdi.group14.finalproject.model.Utilities;
import pdi.group14.finalproject.views.ItemView;

/**
 * Created by jeppe on 15-04-15.
 */
public class PrioritySoup extends ViewGroup {
    enum EdgeOrient {horizontal,vertical}
    enum Position {bottom,top,left,right}
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
            freeEdges.add(new Edge(ul,ur,Position.top,initial));//top
            freeEdges.add(new Edge(ll,lr,Position.bottom,initial));//bot
            freeEdges.add(new Edge(ul,ll,Position.left,initial));//left
            freeEdges.add(new Edge(ur,lr,Position.right,initial));//right
        }
        public void addItem(ItemView iv){
            AlignedEdge best = closestEdgeThatFits(iv.getWidth(),iv.getHeight());
            freeEdges.remove(best.E);
            freeEdges.addAll(generateEdges(best.E,iv,best.ALIGNMENT));
            itemViews.add(iv);

        }

        private Collection<? extends Edge> generateEdges(Edge edge, ItemView iv, Point anchor) {
            ArrayList<Edge> output = new ArrayList<Edge>();
            if()
            switch(edge.position){
                case top:
                    break;
                case bottom:
                    break;
                case left:
                    break;
                case right:
                    break;
                default:
            }
        }

        private AlignedEdge closestEdgeThatFits(int width,int height){
            for(int i = 0; i < freeEdges.size(); i++){
                Edge current = freeEdges.get(i);
                EdgeOrient orientation = current.getOrientation();
                int determiningSize = orientation==EdgeOrient.horizontal?width:height;
                if(determiningSize >= current.getSize()){
                    return new AlignedEdge(current,current.p1);
                }else{
                    Point p1 = current.p1;
                    Point p2 = current.p2;
                    boolean p1free = true;
                    boolean p2free = true;
                    for(Edge e : freeEdges){
                        p1free = !intersects(p1,e);
                        p2free = !intersects(p2,e);
                    }
                    if(p1free || p2free) return new AlignedEdge(current,p2free?p1:p2);
                }
            }
            return null;
        }

        private boolean intersects(Point point, Edge e) {
            Point e1, e2;
            e1 = e.p1;
            e2 = e.p2;
            if(e1.y == point.y && e2.y == point.y){
                return Math.min(e1.x,e2.x) <= point.x && Math.max(e1.x,e2.x) >= point.x;
            }else if(e1.x == point.x && e2.x == point.x){
                return Math.min(e1.y,e2.y) <= point.y && Math.max(e1.y,e2.y) >= point.y;
            }else{
                return false;
            }
        }
        class AlignedEdge {
            public final Edge E;
            public final Point ALIGNMENT;
            AlignedEdge(Edge e, Point alignment) {
                E = e;
                ALIGNMENT = alignment;
            }
        }
        class Point{
            public int x,y;
            public Point(int x,int y){
                this.x = x;
                this.y = y;
            }
        }
        class Edge{
            public Position position;
            public ItemView itemView;
            public Point p1, p2;
            public int size;
            public Edge(Point p1, Point p2,Position position,ItemView itemView){
                this.p1 = p1;
                this.p2 = p2;
                this.itemView = itemView;
                this.position = position;
                initSize();
            }
            public EdgeOrient getOrientation(){
                return p1.x==p2.x?EdgeOrient.vertical:EdgeOrient.horizontal;
            }
            public int getSize(){
                return size;
            }
            private void initSize(){
                int dx = p2.x-p1.x;
                int dy = p2.y-p1.y;
                size = (int)Math.sqrt(dx*dx+dy*dy);
            }
        }
    }
}

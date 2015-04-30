package pdi.group14.finalproject.layouts;

import android.app.Application;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import pdi.group14.finalproject.model.Utilities;
import pdi.group14.finalproject.views.ItemView;

/**
 * Created by jeppe on 15-04-15.
 */
public class PrioritySoup extends ViewGroup {
    enum EdgeOrient {horizontal,vertical}
    enum Position {bottom,top,left,right}
    Cluster cluster = null;

    @Override
    public void addView(View child, int index, LayoutParams params) {
        assert child instanceof ItemView;
        super.addView(child, index, params);
        cluster.addItem((ItemView)child);
    }

    @Override
    public void removeView(View view) {
        super.removeView(view);
        cluster.removeItem((ItemView)view);
    }

    @Override
    public void removeViewInLayout(View view) {
        super.removeViewInLayout(view);
        cluster.removeItem((ItemView)view);
    }

    @Override
    public void removeViewsInLayout(int start, int count) {
        for(int i = start+count-1; i >= start; i--){
            cluster.removeItem((ItemView) getChildAt(i));
        }
        super.removeViewsInLayout(start, count);
    }

    @Override
    public void removeViewAt(int index) {
        cluster.removeItem((ItemView)getChildAt(index));
        super.removeViewAt(index);
    }

    @Override
    public void removeViews(int start, int count) {
        for(int i = start+count-1; i >= start; i--){
            cluster.removeItem((ItemView)getChildAt(i));
        }
        super.removeViews(start, count);
    }

    @Override
    public void removeAllViewsInLayout() {
        super.removeAllViewsInLayout();
        cluster = new Cluster();
    }


    public PrioritySoup(Context context) {
        super(context);
        cluster = new Cluster();
    }

    private Rect getSizeAsRect(){
        int minx = 0,maxx = 0,miny = 0,maxy = 0;
        for(Cluster.Edge e: cluster.freeEdges){
            minx = e.p1.x<minx?e.p1.x:minx;
            maxx = e.p2.x>maxx?e.p2.x:maxx;
            miny = e.p1.y<miny?e.p1.y:miny;
            maxy = e.p2.y>maxy?e.p2.y:maxy;
        }
        int width = maxx - minx;
        int height = maxy - miny;
        return new Rect(0,0,width,height);
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Rect lr = getSizeAsRect();

        for(int i = 0; i < getChildCount(); i++){
            ItemView iv = (ItemView)getChildAt(i);
            Cluster.Point tl = cluster.topLefts.get(iv);
            Rect rect = iv.getSizeAsRect();
            iv.layout(tl.x,tl.y,tl.x+rect.width(),tl.y+rect.height());
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Rect rect = getSizeAsRect();
        int width = rect.width();
        int height = rect.height();
        Toast.makeText(this.getContext(),width+" "+height,Toast.LENGTH_SHORT).show();
        setMeasuredDimension(width, height);
    }

    class Cluster{
        private Point topLeft;
        private Point bottomRight;
        ArrayList<Edge> freeEdges;
        ArrayList<ItemView> itemViews;
        public HashMap<ItemView,Point> topLefts;

        public Cluster(){
            topLeft = new Point(0,0);
            bottomRight = new Point(0,0);
            freeEdges = new ArrayList<>();
            itemViews = new ArrayList<>();
            Point o = new Point(0,0);
            freeEdges.add(new Edge(o,o,Position.top,null));
            topLefts = new HashMap<>();
        }

        public void addItem(ItemView iv){
            Rect rect = iv.getSizeAsRect();
            int h = rect.height(), w = rect.width();
            AlignedEdge best = closestEdgeThatFits(w,h);
            freeEdges.remove(best.E);
            ArrayList<Edge> newEdges = generateEdges(best.E,iv,best.ALIGNMENT,best.BACKWARDS_ALIGNED);
            freeEdges.addAll(newEdges);
            itemViews.add(iv);
            topLefts.put(iv,newEdges.get(0).p1);
        }
        public void removeItem(ItemView iv){
            assert iv!=null;
            int index = itemViews.indexOf(iv);
            ArrayList nIVs = (ArrayList) itemViews.subList(0,index);
            nIVs.remove(iv);
            ArrayList dep = (ArrayList) itemViews.subList(index,itemViews.size()-1);
            dep.remove(iv);
            itemViews = nIVs;
            for(int i = 0; i < freeEdges.size(); i++){
                if(freeEdges.get(i).itemView == iv) {
                    freeEdges = (ArrayList)freeEdges.subList(0,i-1);
                    break;
                }
            }
            for( ItemView e: (ArrayList<ItemView>)dep){
                addItem(e);
            }
            topLefts.remove(iv);
        }
        private ArrayList<Edge> generateEdges(Edge edge, ItemView iv, Point anchor, boolean backwardsAligned) {
            ArrayList<Edge> output = new ArrayList<>();
            int edif;
            Rect rect = iv.getSizeAsRect();
            int h = rect.height(), w = rect.width();
            Point tl = null,tr,bl,br;
            switch(edge.position){
                case top:
                    tl = new Point(backwardsAligned?anchor.x-w:anchor.x,anchor.y-h);
                    edif = edge.getSize()-w;
                    if(edif > 0){
                        Point rp1 = new Point(backwardsAligned?anchor.x-edge.getSize():anchor.x+w,anchor.y);
                        Point rp2 = new Point(rp1.x+edif,anchor.y);
                        Edge rest = new Edge(rp1,rp2,edge.position,edge.itemView);
                        output.add(rest);
                    }
                    break;
                case bottom:
                    tl = new Point(backwardsAligned?anchor.x-w:anchor.x,anchor.y);
                    edif = edge.getSize()-w;
                    if(edif > 0){
                        Point rp1 = new Point(backwardsAligned?anchor.x-edge.getSize():anchor.x+w,anchor.y);
                        Point rp2 = new Point(rp1.x+edif,anchor.y);
                        Edge rest = new Edge(rp1,rp2,edge.position,edge.itemView);
                        output.add(rest);
                    }
                    break;
                case left:
                    tl = new Point(anchor.x-w,backwardsAligned?anchor.y-h:anchor.y);
                    edif = edge.getSize()-h;
                    if(edif > 0){
                        Point rp1 = new Point(anchor.x,backwardsAligned?anchor.y:anchor.y+h);
                        Point rp2 = new Point(anchor.x,rp1.y+edif);
                        Edge rest = new Edge(rp1,rp2,edge.position,edge.itemView);
                        output.add(rest);
                    }
                    break;
                case right:
                    tl = new Point(anchor.x,backwardsAligned?anchor.y-h:anchor.y);
                    edif = edge.getSize()-h;
                    if(edif > 0){
                        Point rp1 = new Point(anchor.x,backwardsAligned?anchor.y:anchor.y+h);
                        Point rp2 = new Point(anchor.x,rp1.y+edif);
                        Edge rest = new Edge(rp1,rp2,edge.position,edge.itemView);
                        output.add(rest);
                    }
                    break;
                default:
            }
            tr = new Point(tl.x+w,tl.y);
            bl = new Point(tl.x,tl.y+h);
            br = new Point(tl.x+w,tl.y+h);
            topLeft.y = tl.y<topLeft.y?tl.y:topLeft.y;
            topLeft.x = tl.x<topLeft.x?tl.x:topLeft.x;
            bottomRight.y = br.y>bottomRight.y?br.y:bottomRight.y;
            bottomRight.x = br.x>bottomRight.x?br.x:bottomRight.x;
            output.add(new Edge(tl,tr,Position.top,iv));
            output.add(new Edge(bl,br,Position.bottom,iv));
            output.add(new Edge(tl,bl,Position.left,iv));
            output.add(new Edge(tr,br,Position.right,iv));
            return output;
        }

        private AlignedEdge closestEdgeThatFits(int width,int height){
            for(int i = 0; i < freeEdges.size(); i++){
                Edge current = freeEdges.get(i);
                EdgeOrient orientation = current.getOrientation();
                int determiningSize = orientation==EdgeOrient.horizontal?width:height;
                if(determiningSize >= current.getSize()){
                    return new AlignedEdge(current,current.p1, false);
                }else{
                    Point p1 = current.p1;
                    Point p2 = current.p2;
                    boolean p1free = true;
                    boolean p2free = true;
                    for(Edge e : freeEdges){
                        p1free = !intersects(p1,e);
                        p2free = !intersects(p2,e);
                    }
                    if(p1free || p2free) return new AlignedEdge(current,p2free?p1:p2, !p2free);
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

        private class AlignedEdge {
            public final Edge E;
            public final Point ALIGNMENT;
            public final boolean BACKWARDS_ALIGNED;
            AlignedEdge(Edge e, Point alignment, boolean backwards_aligned) {
                E = e;
                ALIGNMENT = alignment;
                BACKWARDS_ALIGNED = backwards_aligned;
            }
        }
        private class Point{
            public int x,y;
            public Point(int x,int y){
                this.x = x;
                this.y = y;
            }
        }
        private class Edge{
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

package Task04;

import java.awt.*;
import java.util.*;
import java.util.List;

//Klasse zur Verwaltung von Telefonknoten mit (x,y)-Koordinaten und zur Berechnung eines
// minimal aufspannenden Baums mit dem Algorithmus von Kruskal. Kantengewichte sind durch
// den Manhattan-Abstand definiert.
public class TelNet {

    Map<TelKnoten, Integer> telMap;
    int size = 0;
    List<TelVerbindung> minSpanTree;
    int lbg;

    TelNet(int lbg) {
        telMap = new TreeMap<>();
        this.lbg = lbg;
    }

    //Fügt einen neuen Telefonknoten mit Koordinate (x,y) dazu.
    boolean addTelKnoten(int x, int y) {
        for (var map : telMap.entrySet()) if (map.getKey().x == x && map.getKey().y == y) return false;
        telMap.put(new TelKnoten(x, y), size++);
        return true;
    }

    //    Berechnet ein optimales Telefonnetz als minimal aufspannenden Baum mit dem Algorithmus von Kruskal.
    boolean computeOptTelNet() {
        minSpanTree = minimumSpanningTree();
        //System.out.println(minSpanTree);
        return true;
    }

    private List<TelVerbindung> minimumSpanningTree() {
        UnionFind forest = new UnionFind(size); //{{v} / v ∊V};

        PriorityQueue<TelVerbindung> edges = getOptTelNet();
        System.out.printf("size edgelist = %d \n", edges.size());
        System.out.printf("forest size = %d \n", forest.size());

        List<TelVerbindung> minSpanTree = new LinkedList<>();
        while (forest.size() != 1 && !edges.isEmpty()) {
            TelVerbindung x = edges.remove();

            int t1 = forest.find(telMap.get(x.u));
            int t2 = forest.find(telMap.get(x.v));
            if (t1 != t2) {
                forest.union(t1, t2);
                minSpanTree.add(x);
                System.out.printf("removed value: %d forest size = %d \n", x.c, forest.size());
            }
        }
        if (edges.isEmpty() && forest.size() != 1)
            return null; //“es existiert kein aufspannender Baum”;
        else
            return minSpanTree;
    }

    //    Zeichnet das gefundene optimale Telefonnetz mit der Größe xMax*yMax in ein Fenster.
    void drawOptTelNet(int xMax, int yMax) {
        StdDraw.clear();
        StdDraw.setCanvasSize(xMax, yMax);
        PriorityQueue<TelVerbindung> list =  getOptTelNet();
        double pen = 1.0 / xMax;//300
        if (pen > 0.03)
            pen = 1.0 / 300;
        double factorX = 1.0 / xMax;//100
        double factorY = 1.0 / yMax;//100
        for (TelVerbindung v : list) {
            double x1 = (v.u.x) * factorX;
            double y1 = (v.u.y) * factorY;
            double x2 = (v.v.x) * factorX;
            double y2 = (v.v.y) * factorY;



            StdDraw.setPenColor(Color.BLUE);
            double penSquare = pen * 2;
            StdDraw.filledSquare(x1, y1, penSquare);
            StdDraw.filledSquare(x2, y2, penSquare);
            StdDraw.setPenColor(Color.RED);
            StdDraw.setPenRadius(pen);
            StdDraw.line(x1, y1, x2, y1);
            StdDraw.line(x2, y1, x2, y2);
        }
        StdDraw.show();
    }

    //    Fügt n zufällige Telefonknoten zum Netz dazu mit x-Koordinate aus [0,xMax] und y-Koordinate aus [0,yMax].
    void generateRandomTelNet(int n, int xMax, int yMax) {
        for (int i = 0; i < n; i++) {
            int x = (int) (Math.random() * xMax);
            int y = (int) (Math.random() * yMax);
//            System.out.println(i + ": " + x + " | " + y);
            addTelKnoten(x, y);
        }
    }

    TelKnoten getTelKnoten(int idx) {
        for (var map : telMap.entrySet()) {
            if (map.getValue() == idx) return map.getKey();
        }
        return null;
    }

    //    Liefert ein optimales Telefonnetz als Liste von Telefonverbindungen zurück.
    PriorityQueue<TelVerbindung> getOptTelNet() {
        PriorityQueue<TelVerbindung> edges = new PriorityQueue<>(Comparator.comparingInt(a -> a.c));
        for (int i = 0; i < telMap.size(); i++) {

            int endCost = Integer.MAX_VALUE;
            int jmin = -1;
            TelKnoten u = getTelKnoten(i);

            for (int j = 0; j < telMap.size(); j++) {

                TelKnoten v = getTelKnoten(j);
                int secondCost = cost(u.x, u.y, v.x, v.y);

                if (i != j && secondCost < endCost) {
                    jmin = j;
                    endCost = secondCost;
                }
            }

            if (jmin != -1 && endCost <= lbg) {
                TelKnoten v = getTelKnoten(jmin);

                boolean valid = true;
                for (var list : edges)
                    if (list.u.equals(u) && list.v.equals(v) || list.u.equals(v) && list.v.equals(u)) {
                        valid = false;
                        break;
                    }

                if (valid) {
                    edges.add(new TelVerbindung(u, v, endCost));
                    System.out.println(endCost);
                    //System.out.println(endCost);
                }
            }
        }

        return edges;
    }

    int cost(int x1, int y1, int x2, int y2) {

        int a = Math.abs(x1 - x2);
        int b = Math.abs(y1 - y2);
        int result = a + b;
        if (result <= lbg) return result;
        else return Integer.MAX_VALUE;
    }

    //    Liefert die Gesamtkosten eines optimalen Telefonnetzes zurück.
    int getOptTelNetKosten() {
        int sum = 0;
        if (getOptTelNet() != null) for (var minList : getOptTelNet()) sum += minList.c;
        return sum;
    }

    //    Anwendung.
    public static void main(java.lang.String[] args) {
        TelNet telNet = new TelNet(7);

        telNet.addTelKnoten(1, 1);
        telNet.addTelKnoten(3, 1);
        telNet.addTelKnoten(4, 2);
        telNet.addTelKnoten(3, 4);
        telNet.addTelKnoten(2, 6);
        telNet.addTelKnoten(4, 7);
        telNet.addTelKnoten(7, 5);
        telNet.computeOptTelNet();

        //System.out.println("optTelNet = " + telNet.getOptTelNet());
        System.out.println("Size = " + telNet.size);
        System.out.println("optCost = " + telNet.getOptTelNetKosten());

        TelNet telNetRandom = new TelNet(100);
        telNetRandom.generateRandomTelNet(1000,1000,1000);

        telNetRandom.computeOptTelNet();

        //System.out.println("optTelNet = " + telNet.getOptTelNet());
        System.out.println("Size = " + telNetRandom.size);
        System.out.println("optCost = " + telNetRandom.getOptTelNetKosten());

        telNetRandom.drawOptTelNet(700, 700);
    }

    //    Liefert die Anzahl der Knoten des Telefonnetzes zurück.
    int size() {
        return size;
    }

}





package Task04;

import java.util.*;

//Klasse zur Verwaltung von Telefonknoten mit (x,y)-Koordinaten und zur Berechnung eines
// minimal aufspannenden Baums mit dem Algorithmus von Kruskal. Kantengewichte sind durch
// den Manhattan-Abstand definiert.
public class TelNet {

    Map<TelKnoten, Integer> telMap;
    int size = 0;
    List<TelVerbindung> minSpanTree;
    int lbg ;

    TelNet(int lbg) {
        telMap = new TreeMap<>();
        this.lbg = lbg;
    }

    //Fügt einen neuen Telefonknoten mit Koordinate (x,y) dazu.
    boolean addTelKnoten​(int x, int y) {
        telMap.put(new TelKnoten(x, y), size++);
        return true;
    }

    //    Berechnet ein optimales Telefonnetz als minimal aufspannenden Baum mit dem Algorithmus von Kruskal.
    boolean computeOptTelNet() {
        minSpanTree = minimumSpanningTree();
        return true;
    }

    private List<TelVerbindung> minimumSpanningTree() {
        UnionFind forest = new UnionFind(6); //{{v} / v ∊V};

        PriorityQueue<TelVerbindung> edges = new PriorityQueue<>(Comparator.comparingInt(a -> a.c));

        List<TelVerbindung> minSpanTree = new LinkedList<>();
        while (forest.size() != 1 && !edges.isEmpty()) {
            TelVerbindung x = edges.remove();
            int t1 = forest.find(telMap.get(x.u));
            int t2 = forest.find(telMap.get(x.v));
            if (t1 != t2) {
                forest.union(t1, t2);
                minSpanTree.add(x);
            }
        }
        if (edges.isEmpty() && forest.size() != 1)
            return null; //“es existiert kein aufspannender Baum”;
        else
            return minSpanTree;
    }

    //    Zeichnet das gefundene optimale Telefonnetz mit der Größe xMax*yMax in ein Fenster.
    void drawOptTelNet​(int xMax, int yMax) {

    }

    //    Fügt n zufällige Telefonknoten zum Netz dazu mit x-Koordinate aus [0,xMax] und y-Koordinate aus [0,yMax].
    void generateRandomTelNet​(int n, int xMax, int yMax) {

    }

    //    Liefert ein optimales Telefonnetz als Liste von Telefonverbindungen zurück.
    List<TelVerbindung> getOptTelNet() {
        return minSpanTree;
    }

    //    Liefert die Gesamtkosten eines optimalen Telefonnetzes zurück.
    int getOptTelNetKosten() {
        int sum = 0;
        for (var map: telMap.entrySet()) {
//            sum+= map.getKey().---------------------------------------------------------------------------------------------------------------------------
        }
        return 0;
    }

    //    Anwendung.
    static void main​(java.lang.String[] args) {

    }

    //    Liefert die Anzahl der Knoten des Telefonnetzes zurück.
    int size() {
        return size;
    }

}





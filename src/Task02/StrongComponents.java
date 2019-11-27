// O. Bittel;
// 05-09-2018

package Task02;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Klasse für Bestimmung aller strengen Komponenten.
 * Kosaraju-Sharir Algorithmus.
 *
 * @param <V> Knotentyp.
 * @author Oliver Bittel
 * @since 22.02.2017
 */
public class StrongComponents<V> {
    // comp speichert fuer jede Komponente die zughörigen Knoten.
    // Die Komponenten sind numeriert: 0, 1, 2, ...
    // Fuer Beispielgraph in Aufgabenblatt 2, Abb3:
    // Component 0: 5, 6, 7,
    // Component 1: 8,
    // Component 2: 1, 2, 3,
    // Component 3: 4,

    private final Map<Integer, Set<V>> comp = new TreeMap<>();
    private final List<V> rpol = new LinkedList<>();
    private final DirectedGraph<V> myInvertedGraph;
    private int numberOfDFTrees = 0;

    /**
     * Ermittelt alle strengen Komponenten mit
     * dem Kosaraju-Sharir Algorithmus.
     *
     * @param g gerichteter Graph.
     */
    public StrongComponents(DirectedGraph<V> g) {
        DepthFirstOrder<V> dfs = new DepthFirstOrder<>(g);
        rpol.addAll(invertPostOrder(dfs));

        myInvertedGraph = invertGraph(g);
        DepthFirstOrder<V> dfs2 = new DepthFirstOrder<>(myInvertedGraph);
		visitDFrpol();

    }

    void visitDFrpol() {
        Set<V> besucht = new TreeSet<>();
        for (var v : rpol)
            if (!besucht.contains(v)) {
                comp.put(numberOfDFTrees++, visitDFrpol(v, myInvertedGraph, besucht, new TreeSet<>()));
            }
    }

    Set<V> visitDFrpol(V v, DirectedGraph<V> g, Set<V> besucht, Set<V> teilGraph) {
        besucht.add(v);
        teilGraph.add(v);
// Bearbeite v:
//		println(v);
        for (var w : myInvertedGraph.getSuccessorVertexSet(v))
            if (!besucht.contains(w))
                visitDFrpol(w, g, besucht, teilGraph);
        return teilGraph;
    }


    private DirectedGraph<V> invertGraph(DirectedGraph<V> g) {
        return g.invert();
    }

    private List<V> invertPostOrder(DepthFirstOrder<V> dfs) {
        List<V> reversePostOrderList = new LinkedList<>(dfs.postOrder());
        Collections.reverse(reversePostOrderList);
        return reversePostOrderList;
    }

    /**
     * @return Anzahl der strengen Komponeneten.
     */
    public int numberOfComp() {
        return comp.size();
    }


    // Component 0: 5, 6, 7,
    // Component 1: 8,
    // Component 2: 1, 2, 3,
    // Component 3: 4,

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (var e : comp.entrySet()) {
                output.append("Component ").append(e.getKey()).append(": ").append(e.getValue()).append("\n");
        }
        return output.toString();
    }

    /**
     * Liest einen gerichteten Graphen von einer Datei ein.
     *
     * @param fn Dateiname.
     * @return gerichteter Graph.
     * @throws FileNotFoundException
     */
    public static DirectedGraph<Integer> readDirectedGraph(File fn) throws FileNotFoundException {
        DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
        Scanner sc = new Scanner(fn);
        sc.nextInt();
        sc.nextInt();
        while (sc.hasNextInt()) {
            int v = sc.nextInt();
            int w = sc.nextInt();
            g.addEdge(v, w);
        }
        return g;
    }

    private static void test1() {
        DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(2, 1);
        g.addEdge(2, 3);
        g.addEdge(3, 1);

        g.addEdge(1, 4);
        g.addEdge(5, 4);

        g.addEdge(5, 7);
        g.addEdge(6, 5);
        g.addEdge(7, 6);

        g.addEdge(7, 8);
        g.addEdge(8, 2);

        StrongComponents<Integer> sc = new StrongComponents<>(g);

        System.out.println(sc.numberOfComp());  // 4

        System.out.println(sc);
        // Component 0: 5, 6, 7,
        // Component 1: 8,
        // Component 2: 1, 2, 3,
        // Component 3: 4,
    }

    private static void test2() throws FileNotFoundException {
        DirectedGraph<Integer> g = readDirectedGraph(new File("mediumDG.txt"));
        System.out.println(g.getNumberOfVertexes());
        System.out.println(g.getNumberOfEdges());
        System.out.println(g);

        System.out.println("");

        StrongComponents<Integer> sc = new StrongComponents<>(g);
        System.out.println(sc.numberOfComp());  // 10
        System.out.println(sc);

    }

    public static void main(String[] args) throws FileNotFoundException {
        test1();
        test2();
    }
}

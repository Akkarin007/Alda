package Task04;

import Task01.BinaryTreeDictionary;
import Task01.Dictionary;

import java.util.LinkedList;
import java.util.List;

public class UnionFind {
    //Legt eine neue Union-Find-Struktur mit der Partitionierung {{0}, {1}, ..., {n-1}} an.

    int[] p;

    UnionFind(int n) {

        p = new int[n];
        for (int i = 0; i < n; i++) {
            p[i] = -1;
        }
    }

    //Liefert den Repräsentanten der Menge zurück, zu der e gehört.
    int find(int e) {
        while (p[e] >= 0) // e ist keine Wurzel
            e = p[e];
        return e;
    }

    static void main​(java.lang.String[] args) {

    }

    //Liefert die Anzahl der Mengen in der Partitionierung zurück.
    int size() {
        throw new UnsupportedOperationException("not supportet yet");
    }

    //Vereinigt die beiden Menge s1 und s2.
    void union(int s1, int s2) {
        unionByHeight(s1, s2);
    }

    void unionByHeight(int s1, int s2) {
        if (p[s1] >= 0 || p[s2] >= 0)
            return;
        if (s1 == s2)
            return;
        if (-p[s1] < -p[s2]) // Höhe von s1 < Höhe von s2
            p[s1] = s2;
        else {
            if (-p[s1] == -p[s2])
                p[s1]--; // Höhe von s1 erhöht sich um 1
            p[s2] = s1;
        }
    }
}

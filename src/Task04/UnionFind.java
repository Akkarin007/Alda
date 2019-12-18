package Task04;

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

    public static void main(String[] args) {
        UnionFind union = new UnionFind(12);
        union.p[0] = -1;
        union.p[1] = 0;
        union.p[2] = 0;
        union.p[3] = -1;
        union.p[4] = 6;
        union.p[5] = -1;
        union.p[6] = -1;
        union.p[7] = 4;
        union.p[8] = 5;
        union.p[9] = 4;
        union.p[10] = 6;
        union.p[11] = 6;

        System.out.println("should be 0 = " + union.find(1));
        System.out.println("should be 0 = " + union.find(2));
        System.out.println("should be 0 = " + union.find(0));

        System.out.println("------> find(7) should be 6 = " + union.find(7));
        System.out.println("------> find(9) should be 6 = " + union.find(9));
        System.out.println("------> find(4) should be 6 = " + union.find(4));
        System.out.println("------> find(11) should be 6 = " + union.find(11));
        System.out.println("------> find(10) should be 6 = " + union.find(10));
        System.out.println("------> find(6) should be 6 = " + union.find(6));

        System.out.println("------> find(3) should be 3 = " + union.find(3));

        System.out.println("------> find(5) should be 5 = " + union.find(5));
        System.out.println("------> find(8) should be 5 = " + union.find(8));
        System.out.println(">>> size = " + union.size());

        union.union(0, 6);

        System.out.println("------> after union(0,6) should be 6 = " + union.find(0));
        System.out.println(">>> size = " + union.size());
        union.union(6, 3);

        System.out.println("------> after union(6,3) should be 6 = " + union.find(3));
        System.out.println(">>> size = " + union.size());

        union.union(6, 5);

        System.out.println("------> after union(6,5) should be 6 = " + union.find(8));
        System.out.println(">>> size = " + union.size());
        System.out.println(">>> size = " + union.size());

    }

    //Liefert die Anzahl der Mengen in der Partitionierung zurück.
    int size() {
        int size = 0;
        for (int value : p) {
            if (value < 0) size++;
        }
        return size;
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
        if (-p[s1] < -p[s2]) {// Höhe von s1 < Höhe von s2
            p[s1] = s2;
        } else {
            if (-p[s1] == -p[s2])
                p[s1]--; // Höhe von s1 erhöht sich um 1
            p[s2] = s1;
        }
    }
}

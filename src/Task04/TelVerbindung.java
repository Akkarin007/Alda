package Task04;

//Klasse für eine Telefonverbindung.
public class TelVerbindung implements Comparable<TelVerbindung> {

    //Verbindungskosten
    int c;

    //Anfangsknoten
    TelKnoten u;

    //Endknoten
    TelKnoten v;

    //Legt eine neue Telefonverbindung von u nach v mit Verbindungskosten c an.
    TelVerbindung(TelKnoten u, TelKnoten v, int c) {
        this.u = u;
        this.v = v;
        this.c = c;
    }

    public int compareTo(TelVerbindung o) {
        if (c < o.c)
            return -1;
        if (c > o.c)
            return 1;

        return 0;
    }

    @Override
    public String toString() {
        return u + " " +
                v + "Cost " + c + "\n";
    }
}

package Task04;

//Klasse für eine Telefonverbindung.
public class TelVerbindung {

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


    @Override
    public String toString() {
        return "Verbindung from " + u + " " +
                v + "Cost " + c;
    }
}

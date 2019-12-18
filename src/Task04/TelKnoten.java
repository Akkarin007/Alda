package Task04;

public class TelKnoten implements Comparable {
    int	x;
    int	y;

//Legt einen neuen Telefonknoten mit den Koordinaten (x,y) an.
    TelKnoten(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
         return "x: " + x +" -> "+ " y: " + y +" | ";
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof TelKnoten){
           if(this.x == ((TelKnoten) o).x && this.y == ((TelKnoten) o).y ) return 0;
           else if(this.x < ((TelKnoten) o).x ) return -1;
           else if(this.x > ((TelKnoten) o).x ) return 1;
           else if(this.x == ((TelKnoten) o).x &&this.y > ((TelKnoten) o).y ) return -1;
           else if(this.x == ((TelKnoten) o).x &&this.y < ((TelKnoten) o).y ) return 1;
        }
        return 0;
    }
}

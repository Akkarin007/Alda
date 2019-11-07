package Task01;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.InputMismatchException;
import java.util.LinkedList;


public class Controller<K, V> implements ControllerInterface<K, V> {

    Dictionary<String, String> dict;
    LinkedList<String> deutscheList = new LinkedList<>();
    LinkedList<String> engList = new LinkedList<>();

    @Override
    public void read(int n, String path) throws IOException {
        long start = System.nanoTime(); // aktuelle Zeit in nsec
        LineNumberReader in;
        in = new LineNumberReader(new FileReader(path));
        String line;
        int index = 0;
        // Text einlesen und HÃ¤figkeiten aller WÃ¶rter bestimmen:
        while ((line = in.readLine()) != null && (index < n || n == 0)) {
            String[] wf = line.split(" ");
            if (wf[0].length() == 0 || wf[1].length() == 0 || wf.length != 2) continue;
            if (n != 0) ++index;
            dict.insert(wf[0], wf[1]);
            deutscheList.add(wf[0]);
            engList.add(wf[1]);
            //System.out.printf("inserted -> key: %s | value: %s\n", wf[0], wf[1]);
        }

        long end = System.nanoTime();
        double elapsedTime = (double) (end - start) / 1.0e06; // Zeit in msec

        System.out.println("");
        System.out.printf("\nBenötigte Zeit in msec: %f\n", elapsedTime);
    }


    @Override
    public void create(String dict) {
        if (dict == null) {
            this.dict = new SortedArrayDictionary<>();
            System.out.println("SortedArrayDictionary has been created!");
        } else if (dict.equals("HashDictionary")) {
            this.dict = new HashDictionary<>(7);
            System.out.println("HashDictionary has been created!");
        } else if (dict.equals("BinaryTreeDictionary")) {
            this.dict = new BinaryTreeDictionary<>();
            System.out.println("BinaryTreeDictionary has been created!");
        } else System.out.println("Wrong implementation! try again!");


    }


    @Override
    public void print() {
        if (dict instanceof BinaryTreeDictionary){
            ((BinaryTreeDictionary<String, String>) dict).prettyPrint();
        }else if (dict != null) {
            for (Dictionary.Entry<String, String> d : dict) {
                System.out.println(d.getKey() + ": " + d.getValue());
            }
            System.out.println();
        } else {
            System.out.println("no Dictionary has been created! create first and try again!");
        }
    }

    @Override
    public V search(K key) { //C:\Users\ismoz\Documents\00_GitHub\Alda\dtengl.txt
        if (key instanceof String) {
            long start = System.nanoTime(); // aktuelle Zeit in nsec

            String value = dict.search(String.valueOf(key));

            if (value != null){
                for(String s : deutscheList){
                    dict.search(s);
                }
                System.out.println("my Value of Key is: " + value);
            }
            else {

                for(String s : engList){
                    dict.search(s);
                }
                System.out.println("Key not found! please insert first!");
            }

            long end = System.nanoTime();
            double elapsedTime = (double) (end - start) / 1.0e06; // Zeit in msec

            System.out.println("");
            System.out.printf("\nBenötigte Zeit in msec: %f\n", elapsedTime);
            return (V) value;
        } else {
            throw new InputMismatchException("need Strings as parameters");
        }
    }

    @Override
    public void insert(K deutsch, V englisch) {
        if (deutsch instanceof String && englisch instanceof String) {
            System.out.println("i am : " + String.valueOf(deutsch));
            dict.insert(String.valueOf(deutsch), String.valueOf(englisch));
        }
    }

    @Override
    public void remove(K key) {
        if (key instanceof String) {
            String value = dict.remove(String.valueOf(key));
            if (value != null) System.out.println("my removed keys value is: " + value);
            else System.out.println("nothing to be removed! please try again!");
        } else {
            throw new InputMismatchException("need to have String as parameter");
        }
    }
}
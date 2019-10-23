package Task01;

import java.io.IOException;

public interface ControllerInterface<K,V> {
    void read(int n, String path) throws IOException;

    void create(String dict);

    void print();

    V search(K key);

    void insert(K deutsch,V englisch);
    void remove(K key);



}

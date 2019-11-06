package Task01;

import java.util.Iterator;

public class BinaryTreeDictionary<K,V> implements Dictionary<K,V> {

    private Node<K,V> root = null;
    @Override
    public V insert(K key, V value) {
        return null;
    }

    @Override
    public V search(K key) {
        return searchR(key, root);
    }
    private V searchR(K key, Node<K,V> p) {

        if (p == null)
            return null;
        else if (String.valueOf(key).compareTo(String.valueOf(p.key)) < 0)
            return searchR(key, p.left);
        else if (String.valueOf(key).compareTo(String.valueOf(p.key)) > 0)
            return searchR(key, p.right);
        else
            return p.value;
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return null;
    }
    static private class Node<K, V> {
        int height;
        K key;
        V value;
        Node<K, V> left;
        Node<K, V> right;
        private Node(K k, V v) {
            height = 0;
            key = k;
            value = v;
            left = null;
            right = null;
        }
    }
}

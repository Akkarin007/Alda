package Task01;

import java.util.Iterator;

public class BinaryTreeDictionary<K extends Comparable<? super K>, V> implements Dictionary<K, V> {
    private V oldValue;

    private Node<K, V> root = null;

    @Override
    public V insert(K key, V value) {
        root = insertR(key, value, root);
        return oldValue;
    }

    private Node<K, V> insertR(K key, V value, Node<K, V> p) {
        if (p == null) {
            p = new Node(key, value);
            oldValue = null;
        } else if (key.compareTo(p.key) < 0)
            p.left = insertR(key, value, p.left);
        else if (key.compareTo(p.key) > 0)
            p.right = insertR(key, value, p.right);
        else { // Schlüssel bereits vorhanden:
            oldValue = p.value;
            p.value = value;
        }
        return p;
    }

    @Override
    public V search(K key) {
        return searchR(key, root);
    }

    private V searchR(K key, Node<K, V> p) {

        if (p == null)
            return null;
        else if (key.compareTo(p.key) < 0)
            return searchR(key, p.left);
        else if (key.compareTo(p.key) > 0)
            return searchR(key, p.right);
        else
            return p.value;
    }

    @Override
    public V remove(K key) {
        root = removeR(key, root);
        return oldValue;
    }

    private Node<K, V> removeR(K key, Node<K, V> p) {
        if (p == null) {
            oldValue = null;
        } else if (key.compareTo(p.key) < 0)
            p.left = removeR(key, p.left);
        else if (key.compareTo(p.key) > 0)
            p.right = removeR(key, p.right);
        else if (p.left == null || p.right == null) {
// p muss gelöscht werden
// und hat ein oder kein Kind:
            oldValue = p.value;
            p = (p.left != null) ? p.left : p.right;
        } else {
// p muss gelöscht werden und hat zwei Kinder:
            MinEntry<K, V> min = new MinEntry<K, V>();
            p.right = getRemMinR(p.right, min);
            oldValue = p.value;
            p.key = min.key;
            p.value = min.value;
        }
        return p;
    }

    private Node<K, V> getRemMinR(Node<K, V> p, MinEntry<K, V> min) {
        assert p != null;
        if (p.left == null) {
            min.key = p.key;
            min.value = p.value;
            p = p.right;
        } else
            p.left = getRemMinR(p.left, min);
        return p;
    }

    private static class MinEntry<K, V> {
        private K key;
        private V value;
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

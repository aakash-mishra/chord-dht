package src;

import java.util.List;

public interface ChordInterface {
    void join(ChordImpl refNode);
    void lookup(int key);
    void insert(int key, int value);
    void remove(int key);
}

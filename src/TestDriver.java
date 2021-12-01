package src;

public class TestDriver {
    public static void main(String[] args) {

        /***
         *
         * Below is a network with 8 nodes (m = 8)
         */
        ChordImpl n0 = new ChordImpl(0);
        ChordImpl n1 = new ChordImpl(30);
        ChordImpl n2 = new ChordImpl(65);
        ChordImpl n3 = new ChordImpl(110);
        ChordImpl n4 = new ChordImpl(160);
        ChordImpl n5 = new ChordImpl(230);
        n0.join(null);
        n1.join(n0);
        n2.join(n1);
        n3.join(n2);
        n4.join(n3);
        n5.join(n4);

        n0.insert(3, 3);
        n1.insert(200, 50);
        n2.insert(123, 100);
        n3.insert(45,3);
        n4.insert(99, 0);
        n2.insert(60,10);
        n0.insert(50,8);
        n3.insert(99,22);
        n3.insert(100,5);
        n3.insert(101,4);
        n3.insert(102,6);
        n5.insert(240,8);
        n5.insert(250,10);

        ChordImpl n6 = new ChordImpl(100);
        n6.join(n2);

        n0.lookup(3);
        n2.lookup(100);
        n5.lookup(102);
        n3.lookup(10000);
        n2.remove(100);

    }
}

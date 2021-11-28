public class Driver {
    public static void main(String[] args) {
        ChordNode n0 = new ChordNode(0);
        ChordNode n1 = new ChordNode(30);
        ChordNode n2 = new ChordNode(65);
        ChordNode n3 = new ChordNode(110);
        ChordNode n4 = new ChordNode(160);
        ChordNode n5 = new ChordNode(230);
        n0.join(null);
        n1.join(n0);
//        n1.printFingerTable();
//        n2.join(n1);
//        n3.join(n2);
//        n4.join(n3);
//        n5.join(n4);
        n0.printFingerTable();
    }
}

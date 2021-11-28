public class Driver {
    public static void main(String[] args) {
        /*
        ChordNode n0 = new ChordNode(0);
        ChordNode n1 = new ChordNode(1);
        ChordNode n2 = new ChordNode(3);
//        ChordNode n3 = new ChordNode(12);
        n0.join(null);
        n1.join(n0);
        n2.join(n1);
//        n3.join(n2);
        System.out.println("NO FT:");
        n0.printFingerTable();
        System.out.println("N1 FT:");
        n1.printFingerTable();
        System.out.println("N2 FT:");
        n2.printFingerTable();
//        System.out.println("N3 FT:");
//        n3.printFingerTable();
        */

        ChordNode n0 = new ChordNode(0);
        ChordNode n1 = new ChordNode(30);
        ChordNode n2 = new ChordNode(65); //{1,2,4,8,16,32,64,128}
        ChordNode n3 = new ChordNode(110);
        ChordNode n4 = new ChordNode(160);
        ChordNode n5 = new ChordNode(230);
        n0.join(null);
        n1.join(n0);
        n2.join(n1);
        n3.join(n2);
        n4.join(n3);
        n5.join(n4);
        System.out.println("NO FT:");
        n0.printFingerTable();
        System.out.println("N1 FT:");
        n1.printFingerTable();
        System.out.println("N2 FT:");
        n2.printFingerTable();
        System.out.println("N3 FT:");
        n3.printFingerTable();
        System.out.println("N4 FT:");
        n4.printFingerTable();
        System.out.println("N5 FT:");
        n5.printFingerTable();

        n0.insert(3, 3);
        n1.insert(200, 50);
        n2.insert(123, 100);
        n3.insert(45,3);
        n4.insert(99, 0);
        n2.insert(60,10);
        n0.insert(50,8);
        n3.insert(100,5);
        n3.insert(101,4);
        n3.insert(102,6);
        n5.insert(240,8);
        n5.insert(250,10);

        n0.printDataOnNode();
        n1.printDataOnNode();
        n2.printDataOnNode();
        n3.printDataOnNode();
        n4.printDataOnNode();
        n5.printDataOnNode();
//        */

    }
}

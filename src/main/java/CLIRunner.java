package src.main.java;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static src.main.java.ChordImpl.getNodesInNetwork;

public class CLIRunner {
    public static void main(String[] args) {
        boolean shouldContinue = true;
        while(shouldContinue) {
            System.out.println("1. Add a new node in the network (join)");
            System.out.println("2. Insert data (key, value)");
            System.out.println("3. Lookup data (key)");
            System.out.println("4. Remove data (key)");
            System.out.println("5. Exit");

            Scanner scanner = new Scanner(System.in);
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("Enter node identifier (integer between 0 to 255)");
                    int nodeId = Integer.parseInt(scanner.nextLine());
                    List<ChordImpl> nodesInNetwork = getNodesInNetwork();
                    ChordImpl refNode = null;
                    if(!nodesInNetwork.isEmpty()) {
                        refNode = nodesInNetwork.get(new Random().nextInt(nodesInNetwork.size()));
                        System.out.println("Using reference node as: " + refNode.getId());
                    }
                    ChordImpl newNode = new ChordImpl(nodeId);
                    newNode.join(refNode);
                    break;
                case "2":
                    System.out.println("Enter key to be added");
                    int key = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter value to be stored against the key");
                    int value = Integer.parseInt(scanner.nextLine());
                    refNode = getNodesInNetwork().get(new Random().nextInt(getNodesInNetwork().size()));
                    refNode.insert(key, value);
                    break;
                case "3":
                    System.out.println("Enter the key you need to lookup");
                    int keyToFind = Integer.parseInt(scanner.nextLine());
                    refNode = getNodesInNetwork().get(new Random().nextInt(getNodesInNetwork().size()));
                    refNode.lookup(keyToFind);
                    break;
                case "4":
                    System.out.println("Enter the key you need to remove");
                    int keyToRemove = Integer.parseInt(scanner.nextLine());
                    refNode = getNodesInNetwork().get(new Random().nextInt(getNodesInNetwork().size()));
                    refNode.remove(keyToRemove);
                    break;
                case "5":
                    shouldContinue = false;
                    break;
                default:
                    System.out.println("Please enter a valid choice");
            }
        }
        System.out.println("Thank you for using Chord. The app will now exit...");
    }

}

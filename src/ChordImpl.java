package src;

import java.util.*;

import static src.Constants.m;

public class ChordImpl implements ChordInterface{
    static List<ChordImpl> nodesInNetwork;
    int id;
    ChordImpl pred;
    Finger[] finger;
    Map<Integer, Integer> data;

    static {
        nodesInNetwork = new ArrayList<>();
    }

    public ChordImpl(int id) {
        this.id = id;
        this.pred = null;
        this.finger = new Finger[m];
    }
    private void populateStartAndEndIntervals() {
        //populate start interval
        for(int i = 0; i < m; i++) {
            finger[i] = new Finger();
            int start = (this.id + (int)Math.pow(2, i)) % (int)Math.pow(2, m);
            finger[i].setStart(start);
            finger[i].setSuccessor(this);
        }
        //populate end interval
        for(int i = 0; i < m - 1; i++)
            finger[i].setEnd(finger[i + 1].getStart());
        finger[m - 1].setEnd(finger[0].getStart() - 1);
    }

    private int getId() {
        return this.id;
    }

    private void initalizeFingerTableForNewNode(ChordImpl refNode) {

        //initialize finger table for the new joining node
        for(int i = 1; i < m; i++) {
            finger[i].setSuccessor(refNode.find(finger[i].getStart(), null));
        }
    }

    private void adjustFingerTableOfExistingNodes() {
        //adjust finger table of other nodes that are affected by this new node's joining
        for(int i = 0; i < m; i++) {
            int predValue = this.id - (int)Math.pow(2, i);
            if(predValue < 0)
                predValue = (int) Math.pow(2, m) + predValue;
            ChordImpl p = this.findPred(predValue, null);
            //want to make sure that we only update "other" nodes' FT
            if(p != this)
                p.updateFingerTable(this, i);
        }
    }

    private void migrateAffectedKeys() {
        //migrate keys that are affected by this node's joining
        ChordImpl succ = this.finger[0].getSuccessor();
        if(succ.data != null && !succ.data.isEmpty()) {
            succ.data.forEach(
                    (key, value) -> {
                        if(key <= this.id) {
                            System.out.println("Migrate key " + key + " from node " + this.finger[0].getSuccessor().id + " to node " + this.id);
                            this.insert(key, value);//insert data on the current node
                        }
                    }
            );
            succ.data.entrySet().removeIf(x -> x.getKey() <= this.id);
        }
    }

    public void join(ChordImpl refNode) {
        populateStartAndEndIntervals();
        //this is the first node in the network
        if(refNode == null) {
            this.pred = this;
            //successor = finger[0].successor;
        }
        else {
            //updating succ and pred for new node and neighbors
            ChordImpl succ = refNode.find(this.finger[0].getStart(), null);
            finger[0].setSuccessor(succ);
            this.pred = succ.pred;
            succ.pred = this;

            initalizeFingerTableForNewNode(refNode);
            adjustFingerTableOfExistingNodes();
            migrateAffectedKeys();

            nodesInNetwork.add(this);

            System.out.println("\n\nUpdated finger tables after node " + this.id + " joins the network");
            System.out.println("-------------------------------------");
            printFingerTables();
        }
    }
    private void printFingerTables() {
        Collections.sort(nodesInNetwork, Comparator.comparingInt(ChordImpl::getId));

        for(ChordImpl node : nodesInNetwork) {
            System.out.println("Node ID: " + node.id);
            for(int i = 0; i < m; i++) {
                System.out.println("start: " + node.finger[i].getStart() + " end: " + node.finger[i].getEnd() +
                        " successor node id:" + node.finger[i].getSuccessor().id);
            }
        }

    }
    private void updateFingerTable(ChordImpl s, int i) {
        int successorId = this.finger[i].getSuccessor().id;
        if(s.id != this.id) {
            if (isInBetweenCloseOpen(s.id, this.id, successorId)) {
                finger[i].setSuccessor(s);
                this.pred.updateFingerTable(s, i);
            }
        }
    }

    public void printFingerTable() {
       for(int i = 0; i < m; i++) {
           System.out.println("start: " + finger[i].getStart() + " end: " + finger[i].getEnd() +
                                    " successor node id:" + finger[i].getSuccessor().id);
       }
    }

    private boolean isInBetweenCloseOpen(int key, int startId, int endId) {
        if(startId < endId)
            return (key >= startId && key < endId);
        else
            return (key >= startId || key < endId);
    }

    private boolean isInBetweenCloseClose(int key, int startId, int endId) {
        if(startId < endId)
            return (key >= startId && key <= endId);
        else
            return (key >= startId || key <= endId);
    }

    private ChordImpl findPred(int key, List<Integer> path) {
        //find a node n' such that key lies between n'.id and n'successor.id
        ChordImpl nPrime = this;

//        while(!isInBetweenCloseOpen(key, nPrime.id, nPrime.finger[0].getSuccessor().id)) {
//            nPrime = nPrime.closestPred(key);
//            if(path != null) path.add(nPrime.id);
//        }

        while(!isInBetweenCloseClose(key, nPrime.id, nPrime.finger[0].getSuccessor().id)) {
            nPrime = nPrime.closestPred(key);
            if(path != null) path.add(nPrime.id);
        }
        return nPrime;
    }

    private ChordImpl closestPred(int key) {
        for(int i = m - 1; i >= 0; i--) {
            if(finger[i].getSuccessor().id != this.id && isInBetweenCloseClose(finger[i].getSuccessor().id, this.id, key))
                return finger[i].getSuccessor();
        }
        return this;
    }

    public ChordImpl find(int key, List<Integer> path) {
        ChordImpl res = null;
        if(this.id == key) {
            if(path != null) path.add(this.id);
            res = this;
        }
        else {
            ChordImpl nPrime = findPred(key, path);
            res = nPrime.finger[0].getSuccessor();
        }
        return res;
    }

    public void lookup(int key) {
        System.out.println("Finding key: " + key);
        List<Integer> path = new ArrayList<>();
        path.add(this.id);
        ChordImpl location = find(key, path);
        if(location.data != null && !location.data.containsKey(key))
            System.out.println("Given key does not exist in the network");
        else {
            System.out.println("Key found on Node " + location.id + ". Value mapped to key is: " + location.data.get(key));
        }
        System.out.println("Path taken to find the key:");
        String prefix = "";
        StringBuilder pathString = new StringBuilder();
        pathString.append("[");
        for(Integer node : path) {
            pathString.append(prefix);
            pathString.append(node.toString());
            prefix = " -> ";
        }
        pathString.append("]");
        System.out.println(pathString.toString());
    }

    public void insert(int key, int value) {
        ChordImpl location = this.find(key, null);
        if(location.data == null) {
            location.data = new HashMap<>();
        }
        location.data.put(key, value);
    }
}
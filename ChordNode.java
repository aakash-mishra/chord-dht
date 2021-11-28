import java.util.HashMap;
import java.util.Map;

import static constants.Constants.m;

public class ChordNode {
    int id;
    ChordNode pred;
    Finger[] finger;
    Map<Integer, Integer> data;

    public ChordNode(int id) {
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

    public void join(ChordNode refNode) {
        populateStartAndEndIntervals();
        //this is the first node in the network
        if(refNode == null) {
            this.pred = this;
            //successor = finger[0].successor;
        }
        else {
            ChordNode succ = refNode.find(this.finger[0].getStart());
            finger[0].setSuccessor(succ);
            this.pred = succ.pred;
            succ.pred = this;
            for(int i = 1; i < m; i++) {
                finger[i].setSuccessor(refNode.find(finger[i].getStart()));
                //optimization possible
                //TODO
            }

            for(int i = 0; i < m; i++) {
                int predValue = this.id - (int)Math.pow(2, i);
                if(predValue < 0)
                    predValue = (int) Math.pow(2, m) + predValue;
                ChordNode p = this.findPred(predValue);
                //want to make sure that we only update "other" nodes' FT
                if(p != this)
                    p.updateFingerTable(this, i);
            }
        }
    }
    private void updateFingerTable(ChordNode s, int i) {
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

    private ChordNode findPred(int key) {
        //find a node n' such that key lies between n'.id and n'successor.id
        ChordNode nPrime = this;
        while(!isInBetweenCloseOpen(key, nPrime.id, nPrime.finger[0].getSuccessor().id)) {
            nPrime = nPrime.closestPred(key);
        }
        return nPrime;
    }

    private ChordNode closestPred(int key) {
        for(int i = m - 1; i >= 0; i--) {
            if(finger[i].getSuccessor().id != this.id && isInBetweenCloseOpen(finger[i].getSuccessor().id, this.id, key))
                return finger[i].getSuccessor();
        }
        return this;
    }

    public ChordNode find(int key) {
        ChordNode nPrime = findPred(key);
        return nPrime.finger[0].getSuccessor();
    }

    public void printDataOnNode() {
        System.out.println("Data on node: " + this.id);
        System.out.println(this.data.toString());
    }

    public void insert(int key, int value) {
        ChordNode location = this.find(key);
        if(location.data == null) {
            location.data = new HashMap<>();
        }
        location.data.put(key, value);
    }
}
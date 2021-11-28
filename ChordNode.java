
import constants.Constants;

import static constants.Constants.m;

public class ChordNode {
    int id;
    ChordNode pred;
    Finger[] finger;

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
        //this is the first node in the network
        populateStartAndEndIntervals();
        if(refNode == null) {
            this.pred = this;
            //successor = finger[0].successor;
        }
        else {
            ChordNode succ = refNode.find(finger[0].getStart());
            finger[0].setSuccessor(succ);
            this.pred = succ.pred;
            succ.pred = this;
            for(int i = 1; i < m; i++) {
                finger[i].setSuccessor(refNode.find(finger[i].getStart()));
                //optimization possible
                //TODO
            }
            //things are fine till here.

            //step2 - update finger tables of existing nodes
            for(int i = 1; i < m; i++) {
                ChordNode p = findPred(this.id - (int)Math.pow(2, i-1));
                System.out.println("Pred returned in step 2 is " + p.id);
                p.updateFingerTable(this, i);
            }

        }
        System.out.println("Successfully joined node " + this.id + " and pred is " + this.pred.id);
    }
    private void updateFingerTable(ChordNode s, int i) {
        //this.id = 30
        if(isInBetweenCloseOpen(s.id, this.id, finger[i].getSuccessor().id)) {
            finger[i].setSuccessor(s);
            this.pred.updateFingerTable(s, i);
        }
    }

    public void printFingerTable() {
       for(int i = 0; i < m; i++) {
           System.out.println("start: " + finger[i].getStart() + " end: " + finger[i].getEnd() +
                                    " successor node id:" + finger[i].getSuccessor().id);
       }
    }

    private boolean isInBetween2(int key, int startId, int endId) {
        if(startId < endId)
            return (key >= startId && key < endId);
        else
            return (key >= startId || key < endId);
    }

    private boolean isInBetweenOpenClose(int key, int startId, int endId) {
            if(startId < endId)
                return (key > startId && key <= endId);
            else
                return (key > startId || key <= endId);
    }

    private boolean isInBetweenOpenOpen(int key, int startId, int endId) {
        if(startId < endId)
            return (key > startId && key < endId);
        else
            return (key > startId || key < endId);
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
        while(!isInBetweenOpenClose(key, nPrime.id, nPrime.finger[0].getSuccessor().id)) {
            nPrime = closestPred(key);
        }
        return nPrime;
    }

    private ChordNode closestPred(int key) {
        for(int i = m - 1; i >= 0; i--) {
            if(isInBetweenOpenOpen(finger[i].getSuccessor().id, this.id, key))
                return finger[i].getSuccessor();
        }
        return this;
    }

    public ChordNode find(int key) {
        if(key == this.id)
            return this;
        ChordNode nPrime = findPred(key);
        return nPrime.finger[0].getSuccessor();
    }
}
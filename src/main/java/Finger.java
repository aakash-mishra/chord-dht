package src.main.java;

public class Finger {
    private int start;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public ChordImpl getSuccessor() {
        return successor;
    }

    public void setSuccessor(ChordImpl successor) {
        this.successor = successor;
    }

    private int end;
    ChordImpl successor;
}

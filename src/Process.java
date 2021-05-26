public class Process {
    VectorClock vector;
    int totalProcessCount, myID;

    public Process(int processCount, int processID) {
        this.vector = new VectorClock(processCount, processID);
    }

    public VectorClock getVector() { return this.vector; }

    public void send() {


        this.vector.update();
    }

    public void receive() {

        
        this.vector.update();
    }

    public void broadcast() { }
}

import java.util.ArrayList;
import java.util.List;

public class Process {
    VectorClock vector;
    int totalProcessCount, myID;
    public List <Message> sendQueue;

    public class Message {
        public String m;
        public List <Integer> ts;
        public int pid;
        public Message(String m, List<Integer> ts, int pid){
            this.m = m;
            this.ts = ts;
            this.pid = pid;
        }
    }

    public Process(int processCount, int processID) {
        this.vector = new VectorClock(processCount, processID);
        this.sendQueue = new ArrayList<>();
    }

    public VectorClock getVectorClock() { return this.vector; }

    public void send(String m) {
        this.vector.update();
        Message message = new Message(
                m,
                this.getVectorClock().getVector(),
                myID
                );
        this.sendQueue.add(message);
        this.deliver(this.myID);
    }

    public void receive(String m, List <Integer> m_ts, int pid) {
        Message message = new Message(
                m,
                m_ts,
                pid
                );
        this.sendQueue.add(message);
        // this.deliver(this.myID);
    }

    public void deliverAll() {
        boolean deliverable;
        Message message;
        for (int i = 0; i < this.sendQueue.size(); i++) {
            deliverable = true;
            message = this.sendQueue.get(i);
            for (int j = 0; j < this.getVectorClock().getVector().size(); j++) {
                if (j != message.pid) {
                    if (message.ts.get(j) > this.getVectorClock().getCell(j)) {
                        deliverable = false;
                    }
                }
            }

            if (deliverable) {
                this.deliver(i);                
            }
        }
    }

    public void deliver(int mid) {
        System.out.println(this.sendQueue.get(mid).m);

        VectorClock vc = new VectorClock( this.sendQueue.get(mid).pid, this.sendQueue.get(mid).ts );  // [3, 5, 3, 0 , 1]
        this.vector.update(vc);
    }
}




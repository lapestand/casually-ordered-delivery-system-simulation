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
        this.myID = processID;
    }

    public VectorClock getVectorClock() { return this.vector; }  // Return vector clock

    public void send(String m) {
        System.out.println("\n--------------------------------------------------");
        System.out.println("Message sending...");
        // Update vector clock, increase 1 the local timestamp of current process
        this.vector.update();
        
        // Print out info
        System.out.println("Message sent, details:");
        System.out.println("\tMessage content: " + m);
        System.out.println("\tMessage ts: " + this.vector.getVector());
        
        System.out.println("\n--------------------------------------------------\n");


        // Check delivery condition for messages
        this.deliverAll();
    }

    public void receive(String m, List <Integer> m_ts, int pid) {
        // Create message with given parameters
        Message message = new Message(
                m,
                m_ts,
                pid
                );
        // Add this message to the queue
        this.sendQueue.add(message);
        System.out.println("\nA message received, now checking for deliver message...");

        // Check delivery condition for messages
        this.deliverAll();

        System.out.println("\n");
    }

    public void deliverAll() {
        boolean deliverable;
        Message message;

        int i = 0;
        // Check all messages in the queue if they can be deliverable or not
        while (i < this.sendQueue.size()) {
            deliverable = true;
            message = this.sendQueue.get(i);

            // Check delivery condition
            for (int j = 0; j < this.getVectorClock().getVector().size(); j++) {
                if (j != message.pid) {
                    if (message.ts.get(j) > this.getVectorClock().getCell(j)) { deliverable = false; }
                }
            }

            // Deliver message if deliverable, then start deliverAll method again after update the local timestamp
            if (deliverable) { this.deliver(i); this.deliverAll(); break; }
            i++;
        }
    }

    public void deliver(int mid) {
        if (!this.sendQueue.isEmpty()){
            System.out.println("\n--------------------------------------------------");
            System.out.println("\nA Message delivered from process " + this.sendQueue.get(mid).pid);
            System.out.println("\tMessage content: " + this.sendQueue.get(mid).m);
            System.out.println("\tMessage ts: " + this.sendQueue.get(mid).ts + '\n');
            
            VectorClock vc = new VectorClock( this.sendQueue.get(mid).pid, this.sendQueue.get(mid).ts );  // eg. [3, 5, 3, 0 , 1]
            this.vector.update(vc);
            this.sendQueue.remove(mid);
            System.out.println("\n--------------------------------------------------");
        }else{
            System.out.println("Empty queue");
        }
    }

    public void printQueue() {
        if (!this.sendQueue.isEmpty()) {
            System.out.println("\n\n\tQUEUE CONTENT");
            for (int i = 0; i < this.sendQueue.size(); i++) {
                System.out.println("\t" + (i + 1) + ") Message \"" + this.sendQueue.get(i).m + "\" came from process " + this.sendQueue.get(i).pid + " with timestamp " + this.sendQueue.get(i).ts + "\n\n");
            }
        } else {
            System.out.println("\t\tQueue is empty!\n\n");
        }
    }

}




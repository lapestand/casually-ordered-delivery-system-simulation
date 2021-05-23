import java.util.Scanner;


public class Main {

    public final static int SEND_MESSAGE = 1;
    public final static int RECEIVE_MESSAGE = 2;
    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);        
        int processCount;

        do {
            System.out.print("Enter process count: ");
            processCount = myScanner.nextInt();
        } while (processCount <= 0);
        System.out.println("Total processes count: " + processCount);


        int currentProcessID;
        do {
            System.out.print("\nEnter your process ID[0, total process count - 1]: ");
            currentProcessID = myScanner.nextInt();
        } while (currentProcessID >= processCount || currentProcessID < 0);
        System.out.println("Your process ID: " + currentProcessID);
        
        Process process = new Process(processCount, currentProcessID);

        System.out.println(process.vector.toString());

        int option;
        do {
            do {
                System.out.println("1. Send new message m.");
                System.out.println("2. Receive message m from process i.");
                System.out.println("3. Exit.");
                option = myScanner.nextInt();
            } while (option < 1 && option > 3);

            if (option == SEND_MESSAGE) {
                System.out.println("Message sending...");
            } else if (option == RECEIVE_MESSAGE) {
                System.out.println("Message receiving...");
            } else {
                System.out.println("Program closing...");
                break;
            }

        } while (true);

        myScanner.close();
        // VectorClock vectorClock = new VectorClock(5, 2);


        // System.out.println(vectorClock.toString());
    }
}
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class Main {

    public final static int SEND_MESSAGE = 1;
    public final static int RECEIVE_MESSAGE = 2;
    public final static int PRINT_STATUS = 2;
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
        System.out.println("Your process ID: " + currentProcessID + "\n\n");
        
        Process process = new Process(processCount, currentProcessID);
        
        int option, pid;
        String message, m_ts;
        List <Integer> m_ts_as_list;
         
        do {
            System.out.println(process.getVectorClock().toString());
            do {
                System.out.println("1. Send new message m.");
                System.out.println("2. Receive message m from process i.");
                System.out.println("3. Exit.");
                option = myScanner.nextInt(); myScanner.nextLine();  // To avoid not reading nextLine
            } while (option < 1 && option > 3);

            if (option == SEND_MESSAGE) {
                System.out.print("Enter message m: ");
                message = myScanner.nextLine();
                System.out.println("Message sending...");

                process.send(message);
                System.out.println("\nMessage is \"" + process.sendQueue.get(process.sendQueue.size() - 1).m + "\". " +
                                    "Timestamp of message is " + process.sendQueue.get(process.sendQueue.size() - 1).ts +
                                    "\n\n");
                
                System.out.println("Message sent");
            } else if (option == RECEIVE_MESSAGE) {
                System.out.print("Enter message m: ");
                message = myScanner.nextLine();

                System.out.print("Enter PID of the source process: ");
                pid = myScanner.nextInt(); myScanner.nextLine();

                System.out.print("Enter timestamp of the message(eg. [0, 3, 2, 1]): ");
                m_ts = myScanner.nextLine();                
                m_ts = m_ts.substring(1, m_ts.length() - 1);
                m_ts_as_list = Arrays.stream(m_ts.replaceAll("\\s+","").split(",")).map(Integer::parseInt).collect(Collectors.toList());

                process.receive(message, m_ts_as_list, pid);

                // process.simulateReceive(message, pid, m_ts_as_list);
                // process.receive(message, pid, m_ts_as_list);

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



/*
Enter your process ID[0, total process count - 1]: 3
[0, 0, 0, 1, 0, 0, 0, 0]
[0, 0, 0, 2, 0, 0, 0, 0]

1. Send new message m.
2. Receive message m from process i.
3. Exit.

1
Enter message m = merhaba
Message is "merhaba" Timestamp of message is [0, 0, 0, 2, 0, 0, 0, 0]

[0, 0, 0, 2, 0, 0, 0, 0]

1. Send new message m.
2. Receive message m from process i.
3. Exit.

1
Enter message m = merhaba2
Message is "merhaba2" Timestamp of message is [0, 0, 0, 3, 0, 0, 0, 0]

[0, 0, 0, 3, 0, 0, 0, 0]

1. Send new message m.
2. Receive message m from process i.
3. Exit.

2
Enter message m: merhaba3
Enter PID of the source process: 4
Enter timestamp of the message(eg. [0, 3, 2, 1]): [0, 0, 0, 0, 5, 0, 0, 0]

deliverdayık
[0, 0, 0, 2, 0, 0, 0, 0]
[0, 0, 0, 0, 5+1, 0, 0, 0]



*/


/*

send message seçeneğinde;
1- mesajı giriniz: +
yeni mesaj oluştur. sanki herkese gönderilmiş gibi vektor saatini güncelleyeceğiz. +
mesaja timestamp atacağız. +
bu aşamada hoca print olarak şunu görmek istermiş; +
print (xxxxxx mesajı (içeriğiyle beraber) xxxx timestampte tüm processlere gönderilmiştir)  (sanki gönderilmiş gibi ekrana bunu yazdır) +


[1, 1,1 ,1]

2. seçenek seçilirse; 
1 - geldiğini simüle edeceğimiz mesajın içeriğini kullanıcıya sor. +
2 - bu mesajın hangi processten geldiğini sor. pid istenecek. + 
3 - mesajın hangi timestamp ile geldiğini sor. +
4 - mesajı teslim edebilirse şu şu şu mesajlar teslim edilmiştir diyecek.
her mesaj alıp teslim etme içinse şu mesaj şu timestampteki şu queue'ya konmuştur, queue'nun içeriğindeyse şuanda şunlar şunlar vardır diye state belirtecek. alt alta tablo şeklinde yazılabilir.
amaç hocanın sadece konsolu takip ederek queue'yu direk konsoldan görmesi. gidip kodu açıp debugnan uğraştırman beni diyor.

kuyruk içeriği,
teslim edilen mesaj bilgisi,
teslim alınan mesaj bilgisi,
gönderilen mesaj bilgisi.

her adımda bunlardan ilgili olanı görmek isterim. debug'sız işimi kolaylaştırın.

ben olsam process sayısını konsoldan komut satırından alırım ama elle belirlerseniz en az 3 belirleyin. gidipte 100 yapmayın.

*/
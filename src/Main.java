import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class Main {

    public final static int SEND_MESSAGE = 1;
    public final static int RECEIVE_MESSAGE = 2;
    public final static int PRINT_QUEUE = 3;
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
            System.out.print("\nEnter your process ID[0, total process count): ");
            currentProcessID = myScanner.nextInt();
        } while (currentProcessID >= processCount || currentProcessID < 0);
        System.out.println("Your process ID: " + currentProcessID + "\n\n");
        
        Process process = new Process(processCount, currentProcessID);
        
        int option, pid;
        String message, m_ts;
        List <Integer> m_ts_as_list;
         
        do {
            System.out.println("Current local timestamp: " + process.getVectorClock().toString());
            
            // Get selected option
            do {
                System.out.println("1. Send new message m.");
                System.out.println("2. Receive message m from process i.");
                System.out.println("3. Show Queue.");
                System.out.println("4. Exit.");
                System.out.print("Selected option: ");
                option = myScanner.nextInt(); myScanner.nextLine();  // To avoid not reading nextLine

                // Check if selected option is valid
                if (option < 1 || option > 4) { System.out.println("Please enter a valid option number"); }
            } while (option < 1 || option > 4);

            if (option == SEND_MESSAGE) {
                System.out.print("Enter message m: ");
                message = myScanner.nextLine();
                process.send(message);

            } else if (option == RECEIVE_MESSAGE) {
                System.out.print("Enter message m: ");
                message = myScanner.nextLine();
                do {
                    System.out.print("Enter PID of the source process: ");
                    pid = myScanner.nextInt(); myScanner.nextLine();  // To avoid not reading nextLine
                    
                    // Check if given pid is valid
                    if (pid < 0 || pid >= processCount) { System.out.println("PID must be between 0 and " + (processCount - 1)); }
                    else if (pid == process.myID) { System.out.println("You cannot receive a message from yourself."); }

                } while (pid < 0 || pid >= processCount || pid == process.myID);
                

                do {
                    System.out.print("Enter timestamp of the message(eg. [0, 3, 2, 1]. Dont forget to add brackets): ");
                    m_ts = myScanner.nextLine();                
                    m_ts = m_ts.substring(1, m_ts.length() - 1);
                    m_ts_as_list = Arrays.stream(m_ts.replaceAll("\\s+","").split(",")).map(Integer::parseInt).collect(Collectors.toList());

                    if (m_ts_as_list.size() != processCount) {
                        System.out.println("Given array is not match with process count");
                    }
                } while (m_ts_as_list.size() != processCount);

                

                System.out.println("Message receiving...");
                process.receive(message, m_ts_as_list, pid);
            } else if (option == PRINT_QUEUE){
                process.printQueue();

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

deliverday??k
[0, 0, 0, 2, 0, 0, 0, 0]
[0, 0, 0, 0, 5+1, 0, 0, 0]
*/


/*

send message se??ene??inde;
1- mesaj?? giriniz: +
yeni mesaj olu??tur. sanki herkese g??nderilmi?? gibi vektor saatini g??ncelleyece??iz. +
mesaja timestamp ataca????z. +
bu a??amada hoca print olarak ??unu g??rmek istermi??; +
print (xxxxxx mesaj?? (i??eri??iyle beraber) xxxx timestampte t??m processlere g??nderilmi??tir)  (sanki g??nderilmi?? gibi ekrana bunu yazd??r) +


[1, 1,1 ,1]

2. se??enek se??ilirse; 
1 - geldi??ini sim??le edece??imiz mesaj??n i??eri??ini kullan??c??ya sor. +
2 - bu mesaj??n hangi processten geldi??ini sor. pid istenecek. + 
3 - mesaj??n hangi timestamp ile geldi??ini sor. +
4 - mesaj?? teslim edebilirse ??u ??u ??u mesajlar teslim edilmi??tir diyecek.
her mesaj al??p teslim etme i??inse ??u mesaj ??u timestampteki ??u queue'ya konmu??tur, queue'nun i??eri??indeyse ??uanda ??unlar ??unlar vard??r diye state belirtecek. alt alta tablo ??eklinde yaz??labilir.
ama?? hocan??n sadece konsolu takip ederek queue'yu direk konsoldan g??rmesi. gidip kodu a????p debugnan u??ra??t??rman beni diyor.

kuyruk i??eri??i,
teslim edilen mesaj bilgisi,
teslim al??nan mesaj bilgisi,
g??nderilen mesaj bilgisi.

her ad??mda bunlardan ilgili olan?? g??rmek isterim. debug's??z i??imi kolayla??t??r??n.

ben olsam process say??s??n?? konsoldan komut sat??r??ndan al??r??m ama elle belirlerseniz en az 3 belirleyin. gidipte 100 yapmay??n.



1. [1, 0 ,0]
2. [0, 1 ,0]
3. [0, 0 ,1]

process=2
mesaj=ksadfjk
ts=[0 , 4, 23]

1.
{
    p=2,
    mesaj=ksadfjk,
    ts=[0 , 4, 23]
}

message_list.append(
    {
        p=2,
        mesaj=ksadfjk,
        ts=[0 , 4, 23]
    }
)

***********************


for message in message_list:
    if delivirable(message):
        print(messsage)
        message_list.remove(message)


2. PROCESSTEN MESAJ GELD??:
    MESAJ:  TEST MESAJI
    M_TS:     [0, 1, 4]
    Mesajdan ??nceki yerel TS:   [4, 0, 2]
    Mesajdan sonraki yerel TS:  [4, 1, 4]
  

*/
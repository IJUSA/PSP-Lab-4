import java.io.*;//импорт пакета, содержащего классы для ввода/вывода
import java.net.*;//импорт пакета, содержащего классы для работы в сети
import java.util.Objects;

public class
ClientProgram {
    public static void main(String[] arg) {
        try {
            System.out.println("server connecting....");
            Socket clientSocket = new Socket("127.0.0.1",2525);//установление соединения между локальной машиной и указанным портом узла сети
            System.out.println("connection established....");
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));//создание буферизированного символьного потока ввода
            ObjectOutputStream coos = new ObjectOutputStream(clientSocket.getOutputStream());//создание потока вывода
            ObjectInputStream  cois = new ObjectInputStream(clientSocket.getInputStream());//создание потока ввода
            System.out.println("Enter matrix 3x3 to send to server:");
            String[] clientMessages = new String[3];
            String[] serverAnswers = new String[3];
            clientMessages[0] = stdin.readLine();
            clientMessages[1] = stdin.readLine();
            clientMessages[2] = stdin.readLine();
            System.out.println("you've entered:\n"+clientMessages[0]+"\n"+clientMessages[1]+"\n"+clientMessages[2]);
            int flag;
            while(true) {
                coos.writeObject(clientMessages[0]); //потоку вывода присваивается значение строковой переменной (передается серверу)
                coos.writeObject(clientMessages[1]); //потоку вывода присваивается значение строковой переменной (передается серверу)
                coos.writeObject(clientMessages[2]); //потоку вывода присваивается значение строковой переменной (передается серверу)
                serverAnswers[0] = (String)cois.readObject();
                if(Objects.equals(serverAnswers[0], "no inverse matrix")){
                    System.out.println("~server~:");
                    System.out.println("no inverse matrix");
                }else{
                    serverAnswers[1] = (String)cois.readObject();
                    serverAnswers[2] = (String)cois.readObject();
                    System.out.println("~server~:\n"+serverAnswers[0]+"\n"+serverAnswers[1]+"\n"+serverAnswers[2]); //выводится на экран содержимое потока ввода (переданное сервером)
                }
                System.out.println("---------------------------");
                System.out.println("You want to continue? (1 - yes, 0 - no)");
                flag = Integer.parseInt(stdin.readLine());
                if(flag == 0){
                    break;
                }
                System.out.println("Enter matrix 3x3 to send to server:");
                clientMessages[0] = stdin.readLine();
                clientMessages[1] = stdin.readLine();
                clientMessages[2] = stdin.readLine();
                System.out.println("you've entered:\n"+clientMessages[0]+"\n"+clientMessages[1]+"\n"+clientMessages[2]);
            }
            coos.close();//закрытие потока вывода
            cois.close();//закрытие потока ввода
            clientSocket.close();//закрытие сокета
        }catch(Exception e)	{
            e.printStackTrace();//выполнение метода исключения е
        }
    }
}

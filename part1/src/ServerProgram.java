import java.io.*;//импорт пакета, содержащего классы для ввода/вывода
import java.net.*;//импорт пакета, содержащего классы для работы в сети Internet

public class ServerProgram {
    public static void main(String[] arg) {
    ServerSocket serverSocket = null; //объявление объекта класса ServerSocket
    Socket clientAccepted = null;//объявление объекта класса Socket
    ObjectInputStream  sois = null;//объявление байтового потока ввода
    ObjectOutputStream soos = null;//объявление байтового потока вывода
    try {
        System.out.println("server starting....");
        serverSocket = new ServerSocket(2525);//создание сокета сервера для заданного порта
        clientAccepted = serverSocket.accept();//выполнение метода, который обеспечивает реальное подключение сервера к клиенту
        System.out.println("connection established...."); //создание потока ввода soos = new
        sois = new ObjectInputStream(clientAccepted.getInputStream());
        soos = new ObjectOutputStream(clientAccepted.getOutputStream());//создание потока вывода
        String[] clientMessagesRecieved = new String[3];
        clientMessagesRecieved[0] = (String)sois.readObject();//объявление строки и присваивание ей данных потока ввода, представленных в виде строки (передано клиентом)
        clientMessagesRecieved[1] = (String)sois.readObject();//объявление строки и присваивание ей данных потока ввода, представленных в виде строки (передано клиентом)
        clientMessagesRecieved[2] = (String)sois.readObject();//объявление строки и присваивание ей данных потока ввода, представленных в виде строки (передано клиентом)
        double[][] inputMatrix = new double[3][3];
        double[][] inverseMatrix = new double[3][3];
        double det;
        while(true)
        {
            System.out.println("message recieved: '"+clientMessagesRecieved[0]+"'");
            System.out.println("message recieved: '"+clientMessagesRecieved[1]+"'");
            System.out.println("message recieved: '"+clientMessagesRecieved[2]+"'");

            for (int i = 0; i < 3; i++) {
                String[] values = clientMessagesRecieved[i].split(" ");
                for (int j = 0; j < 3; j++) {
                    inputMatrix[i][j] = Double.parseDouble(values[j]);
                }
            }
            det = inputMatrix[0][0] * inputMatrix[1][1] * inputMatrix[2][2]
                    + inputMatrix[0][1] * inputMatrix[1][2] * inputMatrix[2][0]
                    + inputMatrix[0][2] * inputMatrix[1][0] * inputMatrix[2][1]
                    - inputMatrix[0][2] * inputMatrix[1][1] * inputMatrix[2][0]
                    - inputMatrix[0][0] * inputMatrix[1][2] * inputMatrix[2][1]
                    - inputMatrix[0][1] * inputMatrix[1][0] * inputMatrix[2][2];
            if(det==0.0){
                soos.writeObject("no inverse matrix");//потоку вывода присваивается значение строковой переменной (передается клиенту)
            }else{
                inverseMatrix[0][0] = (inputMatrix[1][1] * inputMatrix[2][2] - inputMatrix[1][2] * inputMatrix[2][1]) / det;
                inverseMatrix[0][1] = (inputMatrix[0][2] * inputMatrix[2][1] - inputMatrix[0][1] * inputMatrix[2][2]) / det;
                inverseMatrix[0][2] = (inputMatrix[0][1] * inputMatrix[1][2] - inputMatrix[0][2] * inputMatrix[1][1]) / det;
                inverseMatrix[1][0] = (inputMatrix[1][2] * inputMatrix[2][0] - inputMatrix[1][0] * inputMatrix[2][2]) / det;
                inverseMatrix[1][1] = (inputMatrix[0][0] * inputMatrix[2][2] - inputMatrix[0][2] * inputMatrix[2][0]) / det;
                inverseMatrix[1][2] = (inputMatrix[1][0] * inputMatrix[0][2] - inputMatrix[0][0] * inputMatrix[1][2]) / det;
                inverseMatrix[2][0] = (inputMatrix[1][0] * inputMatrix[2][1] - inputMatrix[2][0] * inputMatrix[1][1]) / det;
                inverseMatrix[2][1] = (inputMatrix[2][0] * inputMatrix[0][1] - inputMatrix[0][0] * inputMatrix[2][1]) / det;
                inverseMatrix[2][2] = (inputMatrix[0][0] * inputMatrix[1][1] - inputMatrix[1][0] * inputMatrix[0][1]) / det;

                soos.writeObject(String.format("%.2f", inverseMatrix[0][0])+" "+String.format("%.2f", inverseMatrix[0][1])+" "+String.format("%.2f", inverseMatrix[0][2]));
                soos.writeObject(String.format("%.2f", inverseMatrix[1][0])+" "+String.format("%.2f", inverseMatrix[1][1])+" "+String.format("%.2f", inverseMatrix[1][2]));
                soos.writeObject(String.format("%.2f", inverseMatrix[2][0])+" "+String.format("%.2f", inverseMatrix[2][1])+" "+String.format("%.2f", inverseMatrix[2][2]));
            }

            clientMessagesRecieved[0] = (String)sois.readObject();//объявление строки и присваивание ей данных потока ввода, представленных в виде строки (передано клиентом)
            clientMessagesRecieved[1] = (String)sois.readObject();//объявление строки и присваивание ей данных потока ввода, представленных в виде строки (передано клиентом)
            clientMessagesRecieved[2] = (String)sois.readObject();//объявление строки и присваивание ей данных потока ввода, представленных в виде строки (передано клиентом)
        }
    } catch(Exception e)	{
    } finally {
        try {
            sois.close();//закрытие потока ввода
            soos.close();//закрытие потока вывода
            clientAccepted.close();//закрытие сокета, выделенного для клиента
            serverSocket.close();//закрытие сокета сервера
        } catch(Exception e) {
            e.printStackTrace();//вызывается метод исключения е
        }
    }
}
}

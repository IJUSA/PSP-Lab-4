import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientProgram {
    public void runClient() throws IOException {
        DatagramSocket clientSocket = null;
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        try {
            byte[] buf = new byte[512];
            clientSocket = new DatagramSocket();
            System.out.println("Enter values");
            System.out.print("x: ");
            String x = stdin.readLine();
            System.out.print("y: ");
            String y = stdin.readLine();
            System.out.print("z: ");
            String z = stdin.readLine();

            byte[] verCmd = {Byte.parseByte(x), Byte.parseByte(y), Byte.parseByte(z)};
            DatagramPacket sendPacket = new DatagramPacket(verCmd, verCmd.length, InetAddress.getByName("127.0.0.1"), 8001);
            clientSocket.send(sendPacket);
            DatagramPacket receivePacket = new DatagramPacket(buf, buf.length);
            clientSocket.receive(receivePacket);
            String serverResponse = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Function value: " + serverResponse);

        }
        finally {
            if (clientSocket != null) {
                clientSocket.close();
            }  }  }
    public static void main(String[] args) {
        try {
            ClientProgram client = new ClientProgram();
            client.runClient();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}


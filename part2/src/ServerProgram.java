import java.net.*;
import java.io.*;

public class ServerProgram {
    public final static int DEFAULT_PORT = 8001;
    public void runServer() throws IOException {
        DatagramSocket serverSocket = null;
        try {
            byte[] buf = new byte[512];
            serverSocket = new DatagramSocket(DEFAULT_PORT);
            System.out.println("UDPServer: Started on " + serverSocket.getLocalAddress() + ":" + serverSocket.getLocalPort());
            while(true) {
                DatagramPacket recvPacket = new DatagramPacket(buf, buf.length);
                serverSocket.receive(recvPacket);
                double x = recvPacket.getData()[0];
                System.out.println("x: " + x);
                double y = recvPacket.getData()[1];
                System.out.println("y: " + y);
                double z = recvPacket.getData()[2];
                System.out.println("z: " + z);
                double f = Math.pow(Math.abs(Math.cos(x)-Math.exp(y)), (1+2*Math.pow(Math.log(y),2)))*(1+z+Math.pow(z,2)/2+Math.pow(z,3)/3);
                String functionValue = String.format("%.2f", f);
                System.out.println("sf: "+functionValue);
                buf = functionValue.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(buf, buf.length, recvPacket.getAddress(), recvPacket.getPort());                                                                  // дейтаграммы для отсылки данных
                serverSocket.send(sendPacket);
                try (FileWriter fileWriter = new FileWriter("server_data.txt", true);
                     BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                     PrintWriter out = new PrintWriter(bufferedWriter)) {
                    out.println("x: " + x);
                    out.println("y: " + y);
                    out.println("z: " + z);
                    out.println("F(x,y,z): " + f);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        finally {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
    }
    public static void main(String[] args) {
        try {
            ServerProgram udpServer = new ServerProgram();
            udpServer.runServer();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}

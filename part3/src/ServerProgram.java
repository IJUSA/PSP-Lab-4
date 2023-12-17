import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

class ServerProgram {
    static int countOfClients = 0;
    static Map<String, Double> daytimeTemperatures = new HashMap<>();
    static Map<String, Double> nightTemperatures = new HashMap<>();

    public static void main(String args[]) throws IOException {
        Random random = new Random();

        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for (String day : daysOfWeek) {
            double temperature = 15 + random.nextDouble() * 10; // Генерация числа между 15 и 25
            daytimeTemperatures.put(day, temperature);
        }
        for (String day : daysOfWeek) {
            double temperature = 0 + random.nextDouble() * 10; // Генерация числа между 0 и 10
            nightTemperatures.put(day, temperature);
        }

        ServerSocket serverSocket = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            serverSocket = new ServerSocket(1024);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                countOfClients++;
                System.out.println("=======================================");
                System.out.println("Client " + countOfClients + " connected");
                inputStream = clientSocket.getInputStream();
                outputStream = clientSocket.getOutputStream();
                byte[] bytes = new byte[1024];
                inputStream.read(bytes);
                String weekDay = new String(bytes, "UTF-8").trim();
                System.out.println(weekDay);
                String weather = String.format("Daytime temperature: %.1f\nNight temperature: %.1f", daytimeTemperatures.get(weekDay), nightTemperatures.get(weekDay));
                System.out.println(weather);
                bytes = weather.getBytes();
                outputStream.write(bytes);
            }
        } catch (Exception e) {
            System.out.println("Error " + e.toString());
        } finally {
            inputStream.close();
            outputStream.close();
            serverSocket.close();
            System.out.println("Client " + countOfClients + " disconnected");
        }
    }
}
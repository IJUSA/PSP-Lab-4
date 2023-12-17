import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

class ClientProgram extends Frame implements ActionListener, WindowListener {
    Choice dayChoice;
    TextField tf, tf1;
    TextArea ta;
    Label la, la1, la2, la3, la4;
    Socket socket = null;
    InputStream is = null;
    OutputStream os = null;
    public static void main(String[] args) {
        ClientProgram client = new ClientProgram();
        client.GUI();
    }
    private void GUI() {
        setTitle("Client Interface");
        tf = new TextField("127.0.0.1");
        tf1 = new TextField("1024");
        dayChoice = new Choice();
        dayChoice.add("Monday");
        dayChoice.add("Tuesday");
        dayChoice.add("Wednesday");
        dayChoice.add("Thursday");
        dayChoice.add("Friday");
        dayChoice.add("Saturday");
        dayChoice.add("Sunday");
        add(dayChoice);
        ta = new TextArea();
        la = new Label("IP ADRESS");
        la1 = new Label("port");
        la2 = new Label("sending date");
        la3 = new Label("result ");
        la4 = new Label(" ");
        Button btn = new Button("connect ");
        Button btn1 = new Button("send ");
        tf.setBounds(200, 50, 70, 25);
        tf1.setBounds(330, 50, 70, 25);
        dayChoice.setBounds(150, 200, 200, 25);
        ta.setBounds(150, 300, 200, 100);
        btn.setBounds(50, 50, 70, 25);
        btn1.setBounds(50, 200, 70, 25);
        la.setBounds(130, 50, 150, 25);
        la1.setBounds(280, 50, 150, 25);
        la2.setBounds(150, 160, 150, 25);
        la3.setBounds(150, 260, 150, 25);
        la4.setBounds(600, 10, 150, 25);
        add(tf);
        add(tf1);
        add(btn);
        add(btn1);
        add(ta);
        add(la);
        add(la1);
        add(la2);
        add(la3);
        add(la4);
        setSize(600, 600);
        setVisible(true);
        addWindowListener(this);
        btn.addActionListener(al);
        btn1.addActionListener(this);
    }
    public void windowClosing(WindowEvent we) {
        if (socket != null && !socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
            }
        }
        this.dispose();
    }
    public void windowActivated(WindowEvent we) {}   ;
    public void windowClosed(WindowEvent we) {};
    public void windowDeactivated(WindowEvent we) {};
    public void windowDeiconified(WindowEvent we) {}   ;
    public void windowIconified(WindowEvent we) {};
    public void windowOpened(WindowEvent we) { } ;
    public void actionPerformed(ActionEvent e) {
        if (socket == null) {
            return;
        }
        try {
            is = socket.getInputStream();
            os = socket.getOutputStream();
            String weekDay = dayChoice.getSelectedItem();
            os.write(weekDay.getBytes());
            byte[] bytes = new byte[1024];
            is.read(bytes);
            String weather = new String(bytes, "UTF-8");
            ta.setText("");
            ta.append(weather);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                os.close();
                is.close();
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    ActionListener al = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            try {
                socket = new Socket(InetAddress.getByName(tf.getText()), Integer.parseInt(tf1.getText()));
            } catch (NumberFormatException e) {
            } catch (UnknownHostException e) {
            } catch (IOException e) {
            }
        }
    };
}
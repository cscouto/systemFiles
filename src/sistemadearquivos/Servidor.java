package sistemadearquivos;

import java.io.*;
import java.net.*;
import java.util.LinkedList;

public class Servidor {

    static final int porta = 8181;

    public static void main(String[] args) {
        File file = new File("");

        System.out.println("");
        try {
            ServerSocket s = new ServerSocket(porta);
            System.out.println("Servidor de cadastro iniciado na porta " + s);
            while (true) {
                Socket socket = s.accept();
                new ServeUmCliente(socket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class ServeUmCliente extends Thread {

    private Socket socket;
    private BufferedReader is;
    private PrintWriter os;

    public ServeUmCliente(Socket s) {
        this.socket = s;
        try {
            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            os = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
            start(); // dispara a Thread

            System.out.println("Nova connexão: " + socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {

            File diretorito = new File(System.getProperty("user.dir"));

            File[] files = diretorito.listFiles();
            LinkedList<String> arquivosNome = new LinkedList<>();

            for (int i = 0; i < files.length; i++) {
                if (!files[i].isDirectory()) {
                    arquivosNome.add(files[i].getName());
                }
            }
            os.println(arquivosNome.size());
            os.flush();

            String test = is.readLine();

            os.println("ok");
            os.flush();

            int i = 0;
            while ((test.equals("vai")) && (i < arquivosNome.size())) {
                os.println(arquivosNome.get(i));
                os.flush();
                test = is.readLine();
                os.println("ok");
                os.flush();
                i++;
            }
            os.println("acabou");
            os.flush();
            
            int cont = 0;
            String str = is.readLine();

            while (!str.toUpperCase().equals("FIM")) {

                InputStream fis = new FileInputStream(str);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                os.println(str);
                os.flush();

                str = is.readLine();

                String s = br.readLine(); // primeira linha

                while ((s != null) && (str.equals("vai"))) {
                    os.println(s);
                    os.flush();
                    s = br.readLine();
                    os.println("ok");
                    os.flush();
                    str = is.readLine();
                }
                os.println("acabou");
                os.flush();

                br.close();
                str = is.readLine();
            }
            is.close();
            os.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

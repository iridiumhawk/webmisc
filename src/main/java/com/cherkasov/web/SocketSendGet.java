package com.cherkasov.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;

/* Отправка GET-запроса через сокет
*/

public class SocketSendGet {
    public static void main(String[] args) throws Exception {
        getSite(new URL("http://mail.ru/index.html"));
    }

    public static void getSite(URL url) {
        try {
            String host = url.getHost();
            int port = url.getDefaultPort();
            String path = url.getPath();

            Socket clientSocket = new Socket(host, port);

            PrintWriter pw = new PrintWriter(clientSocket.getOutputStream());
            pw.println("GET " + path + " HTTP/1.1");
            pw.println("Host: " + host);
            pw.println("User-Agent: Mozilla/5.0");
            pw.println("Accept: text/html");
            pw.println("Connection: close");
            pw.println("");//todo !!!
            pw.flush();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String responseLine;

            while ((responseLine = bufferedReader.readLine()) != null) {
                System.out.println(responseLine);
            }
            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
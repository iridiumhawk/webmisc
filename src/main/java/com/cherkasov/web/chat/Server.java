package com.cherkasov.web.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

    // ключ -имя клиента, а значение - соединение с ним
    private static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();


    /**
     * MAIN
     **/
    public static void main(String[] args) throws IOException {

        ConsoleHelper.writeMessage("Введите порт сервера: ");
        int serverPort = ConsoleHelper.readInt();

        try (ServerSocket serverSocket = new ServerSocket(serverPort)) {

            ConsoleHelper.writeMessage("Сервер запущен");

            while (true) {
                //Слушаем
                Socket socket = serverSocket.accept();
                Handler handler = new Handler(socket);
                //запускаем handler
                handler.start();
            }
        }

    }


    /**
     * отправка сообщения для всех
     **/
    public static void sendBroadcastMessage(Message message) {

        try {

            for (Connection connection : connectionMap.values()) {
                connection.send(message);
            }

        } catch (Exception e) {
            ConsoleHelper.writeMessage("Сообщение не отправлено");
        }

    }


    /**
     * обработчик Handler, в котором будет происходить обмен сообщениями с клиентом
     **/
    private static class Handler extends Thread {

        private Socket socket;

        //Constructor
        public Handler(Socket socket) {

            this.socket = socket;
        }


        /**
         * Handshake
         **/

        private String serverHandshake(Connection connection) throws IOException,
                ClassNotFoundException {
            while (true) {

                connection.send(new Message(MessageType.NAME_REQUEST));
                Message clientMessage = connection.receive();

                if (clientMessage.getType() != MessageType.USER_NAME) continue;
                if (clientMessage.getData() == null || clientMessage.getData().isEmpty()) continue;
                if (connectionMap.containsKey(clientMessage.getData())) continue;
                else {

                    connectionMap.put(clientMessage.getData(), connection);
                    connection.send(new Message(MessageType.NAME_ACCEPTED));
                    return clientMessage.getData();
                }


            }


        }


        /**
         * Отправка списка всех пользователей
         **/
        private void sendListOfUsers(Connection connection, String userName) throws IOException {

            for (String userNameInMap : connectionMap.keySet()) {
                Message message = new Message(MessageType.USER_ADDED, userNameInMap);

                if (!userNameInMap.equals(userName)) {
                    connection.send(message);
                }
            }
        }


        private void serverMainLoop(Connection connection, String userName) throws
                IOException, ClassNotFoundException {

            while (true) {

                Message message = connection.receive();

                if (message.getType() == MessageType.TEXT) {

                    sendBroadcastMessage(new Message(MessageType.TEXT, userName + ": " + message.getData()));
                } else {
                    ConsoleHelper.writeMessage("Ошибка передачи данных.");
                }

            }
        }


        @Override
        public void run() {
            super.run();
            ConsoleHelper.writeMessage("Установлено соединение с " + socket.getRemoteSocketAddress());

//            Connection connect = null;
            String userName = null;

            try (Connection connect = new Connection(socket))
            {


                userName = serverHandshake(connect);
                sendBroadcastMessage(new Message(MessageType.USER_ADDED, userName));
                sendListOfUsers(connect,userName);

                serverMainLoop(connect,userName);

            } catch (Exception e) {
                ConsoleHelper.writeMessage("Ошибка при работе с удаленным адресом");
   /*             try {
                    connect.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }*/
            }

            if (userName != null) {
                connectionMap.remove(userName);
                sendBroadcastMessage(new Message(MessageType.USER_REMOVED, userName));
            }

            ConsoleHelper.writeMessage("Закрыто соединение с " + socket.getRemoteSocketAddress());


        }
    }


}

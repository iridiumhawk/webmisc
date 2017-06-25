package com.cherkasov.web.chat.client;



import com.cherkasov.web.chat.Connection;
import com.cherkasov.web.chat.ConsoleHelper;
import com.cherkasov.web.chat.Message;
import com.cherkasov.web.chat.MessageType;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by hawk on 17.07.2016.
 */
public class Client {
    protected Connection connection;
    private volatile boolean clientConnected = false;


    public class SocketThread extends Thread {

        protected void processIncomingMessage(String message) {
            ConsoleHelper.writeMessage(message);
        }

        protected void informAboutAddingNewUser(String userName) {
            ConsoleHelper.writeMessage("Участник " + userName + " присоединился к чату");
        }

        protected void informAboutDeletingNewUser(String userName) {
            ConsoleHelper.writeMessage("Участник " + userName + " покинул чат");
        }


        protected void notifyConnectionStatusChanged(boolean clientConnected) {
            Client.this.clientConnected = clientConnected;
            synchronized (Client.this) {
                Client.this.notify();
            }

        }


        protected void clientMainLoop() throws IOException,
                ClassNotFoundException {
            while (true) {
                Message message = connection.receive();
                if (message.getType() == MessageType.TEXT) {
                    processIncomingMessage(message.getData());
                } else
                if (message.getType() == MessageType.USER_ADDED) {
                    informAboutAddingNewUser(message.getData());
                } else
                if (message.getType() == MessageType.USER_REMOVED) {
                    informAboutDeletingNewUser(message.getData());
                } else throw new IOException("Unexpected MessageType");
            }
        }

        protected void clientHandshake() throws IOException,
                ClassNotFoundException {
            while (true) {
                Message message = connection.receive();

                if (message.getType() == MessageType.NAME_REQUEST) {
                    String userName = getUserName();
                    connection.send(new Message(MessageType.USER_NAME, userName));
                } else if (message.getType() == MessageType.NAME_ACCEPTED) {
                    notifyConnectionStatusChanged(true);
                    return;
                } else {
                    throw new IOException("Unexpected MessageType");
                }


            }

        }


        @Override
        public void run(){

            try {
                Socket socket = new Socket(getServerAddress(),getServerPort());
                connection = new Connection(socket);
                clientHandshake();
                clientMainLoop();
            } catch (IOException e) {
                notifyConnectionStatusChanged(false);
            } catch (ClassNotFoundException e) {
                notifyConnectionStatusChanged(false);

            }
        }

    }

    protected String getServerAddress() {

        ConsoleHelper.writeMessage("Введите адрес сервера:");
        return ConsoleHelper.readString();
    }

    protected int getServerPort() {
        ConsoleHelper.writeMessage("Введите порт:");
        return ConsoleHelper.readInt();

    }

    protected String getUserName() {
        ConsoleHelper.writeMessage("Введите Имя пользователя:");
        return ConsoleHelper.readString();
    }

    protected boolean shouldSentTextFromConsole() {
        return true;
    }

    protected SocketThread getSocketThread() {
        return new SocketThread();
    }

    protected void sendTextMessage(String text) {

        try {
            connection.send(new Message(MessageType.TEXT, text));
        } catch (IOException e) {
            ConsoleHelper.writeMessage("Ошибка отправки сообщения");

            clientConnected = false;
        }
    }

    public void run() {

        SocketThread socketThread = getSocketThread();
        socketThread.setDaemon(true);
        socketThread.start();

        try {
            synchronized (this) {

                this.wait();
            }
        } catch (InterruptedException e) {
            ConsoleHelper.writeMessage("Error wait");
            return;
        }

        if (clientConnected) {
            ConsoleHelper.writeMessage("Соединение установлено. Для выхода наберите команду 'exit'.");
            while (clientConnected) {

                String consoleRead = ConsoleHelper.readString();
                if (consoleRead.equals("exit")) break;

                if (shouldSentTextFromConsole()) {
                    sendTextMessage(consoleRead);
                }
            }

        } else {
            ConsoleHelper.writeMessage("Произошла ошибка во время работы клиента.");

        }


    }

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }


}

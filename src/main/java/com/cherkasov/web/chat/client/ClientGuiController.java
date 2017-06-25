package com.cherkasov.web.chat.client;

/**
 * Created by hawk on 23.07.2016.
 */
public class ClientGuiController extends Client {
    private ClientGuiModel model ;
    private ClientGuiView view ;

    public ClientGuiController() {
        //21.2.
        model = new ClientGuiModel();
        //21.3.
        view = new ClientGuiView(this);
    }

    public class GuiSocketThread extends SocketThread{
        @Override
        protected void processIncomingMessage(String message) {
            model.setNewMessage(message);
            view.refreshMessages();
        }

        @Override
        protected void informAboutAddingNewUser(String userName) {
            model.addUser(userName);
            view.refreshUsers();
        }

        @Override
        protected void informAboutDeletingNewUser(String userName) {
            model.deleteUser(userName);
            view.refreshUsers();
        }

        @Override
        protected void notifyConnectionStatusChanged(boolean clientConnected) {
            view.notifyConnectionStatusChanged(clientConnected);
        }


    }

    @Override
    protected SocketThread getSocketThread() {
        return new GuiSocketThread();
    }

    @Override
    protected String getServerAddress() {
        return view.getServerAddress();
    }

    @Override
    protected int getServerPort() {
        return view.getServerPort();
    }

    @Override
    protected String getUserName() {
        return view.getUserName();
    }

    @Override
    public void run() {
        SocketThread socketThread = getSocketThread();
//        socketThread.setDaemon(true);
        socketThread.run();
    }

    public ClientGuiModel getModel(){
        return model;
    }

    public static void main(String[] args) {

        ClientGuiController controller = new ClientGuiController();
        controller.run();
    }
}

package com.cherkasov.mvc;


import com.cherkasov.mvc.controller.Controller;
import com.cherkasov.mvc.model.MainModel;
import com.cherkasov.mvc.model.Model;
import com.cherkasov.mvc.view.EditUserView;
import com.cherkasov.mvc.view.UsersView;

public class Main {

//    private static UsersView usersView;

    public static void main(String[] args) {

        Model model = new MainModel();
        UsersView usersView = new UsersView();
        EditUserView editUserView = new EditUserView();
        Controller controller = new Controller();

        usersView.setController(controller);
        editUserView.setController(controller);

        controller.setModel(model);
        controller.setUsersView(usersView);
        controller.setEditUserView(editUserView);

        usersView.fireEventShowAllUsers();
        usersView.fireEventShowDeletedUsers();
        usersView.fireEventOpenUserEditForm(126L);
        editUserView.fireEventUserDeleted(125);
        editUserView.fireEventUserChanged("Borisov", 124 ,3);
    }
}

package com.cherkasov.mvc.view;


import com.cherkasov.mvc.bean.User;
import com.cherkasov.mvc.controller.Controller;
import com.cherkasov.mvc.model.ModelData;

/**
 * Created by hawk on 25.02.2017.
 */
public class UsersView implements View {
    Controller controller;

    @Override
    public void refresh(ModelData modelData) {

        System.out.println(modelData.isDisplayDeletedUserList() ? "All deleted users:" : "All users:");
        for (User user : modelData.getUsers()) {
            System.out.println("\t" + user);
        }
        System.out.println("===================================================");
    }

    @Override
    public void setController(Controller controller) {

        this.controller = controller;
    }

    public void fireEventShowAllUsers() {

        controller.onShowAllUsers();
    }

    public void fireEventShowDeletedUsers() {

        controller.onShowAllDeletedUsers();
    }

    public void fireEventOpenUserEditForm(long id) {
     controller.onOpenUserEditForm(id);
    }

}

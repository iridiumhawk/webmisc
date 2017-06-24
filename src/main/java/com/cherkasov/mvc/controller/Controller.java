package com.cherkasov.mvc.controller;


import com.cherkasov.mvc.model.Model;
import com.cherkasov.mvc.view.EditUserView;
import com.cherkasov.mvc.view.UsersView;

/**
 * Created by hawk on 25.02.2017.
 */
public class Controller {
    private Model model;
    private UsersView usersView;
    private EditUserView editUserView;

    public void setEditUserView(EditUserView editUserView) {

        this.editUserView = editUserView;
    }

    public void setModel(Model model) {

        this.model = model;
    }

    public void onShowAllUsers() {

        model.loadUsers();
        usersView.refresh(model.getModelData());
    }

    public void onShowAllDeletedUsers() {

        model.loadDeletedUsers();
        usersView.refresh(model.getModelData());
    }

    public void setUsersView(UsersView usersView) {

        this.usersView = usersView;
    }

    public void onOpenUserEditForm(long userId) {
    model.loadUserById(userId);
    editUserView.refresh(model.getModelData());
    }

    public void onUserDelete(long id){
        model.deleteUserById(id);
        usersView.refresh(model.getModelData());

    }

    public void onUserChange(String name, long id, int level){
        model.changeUserData(name, id, level);
        usersView.refresh(model.getModelData());

    }
}

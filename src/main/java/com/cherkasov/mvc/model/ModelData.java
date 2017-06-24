package com.cherkasov.mvc.model;


import com.cherkasov.mvc.bean.User;

import java.util.List;

/**
 * Created by hawk on 24.02.2017.
 */
public class ModelData {
    private List<User> users;
    private User activeUser;
    private boolean displayDeletedUserList;

    public boolean isDisplayDeletedUserList() {

        return displayDeletedUserList;
    }

    public void setDisplayDeletedUserList(boolean displayDeletedUserList) {

        this.displayDeletedUserList = displayDeletedUserList;
    }

    public User getActiveUser() {

        return activeUser;
    }

    public void setActiveUser(User activeUser) {

        this.activeUser = activeUser;
    }

    public List<User> getUsers() {

        return users;
    }

    public void setUsers(List<User> users) {

        this.users = users;
    }
}

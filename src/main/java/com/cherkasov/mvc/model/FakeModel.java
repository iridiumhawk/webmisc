package com.cherkasov.mvc.model;


import com.cherkasov.mvc.bean.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hawk on 25.02.2017.
 */
public class FakeModel implements Model {
    private ModelData modelData = new ModelData();

    @Override
    public ModelData getModelData() {
        return modelData;
    }

    @Override
    public void loadDeletedUsers() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void loadUserById(long userId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void loadUsers() {

        List<User> users = new ArrayList<User>();

        users.add(new User("A",1,1));
        users.add(new User("B",2,1));

        modelData.setUsers(users);
    }

    @Override
    public void deleteUserById(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void changeUserData(String name, long id, int level) {
        throw new UnsupportedOperationException();
    }
}

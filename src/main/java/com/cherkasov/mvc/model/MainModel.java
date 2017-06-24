package com.cherkasov.mvc.model;


import com.cherkasov.mvc.bean.User;
import com.cherkasov.mvc.model.service.UserService;
import com.cherkasov.mvc.model.service.UserServiceImpl;

import java.util.List;

/**
 * Created by hawk on 25.02.2017.
 */
public class MainModel implements Model {
    private UserService userService = new UserServiceImpl();
    private ModelData modelData = new ModelData();

    @Override
    public ModelData getModelData() {

        return modelData;
    }

    @Override
    public void loadUsers() {

        modelData.setUsers(getAliveUsers(userService.getUsersBetweenLevels(1, 100)));
        modelData.setDisplayDeletedUserList(false);
    }

    public void loadDeletedUsers() {

        List<User> users = userService.getAllDeletedUsers();
        modelData.setUsers(users);
        modelData.setDisplayDeletedUserList(true);

    }

    public void loadUserById(long userId) {

        User user = userService.getUsersById(userId);
        modelData.setActiveUser(user);
    }

    @Override
    public void deleteUserById(long id) {

        userService.deleteUser(id);
        loadUsers();
    }

    private List<User> getAliveUsers(List<User> users) {

        return userService.filterOnlyActiveUsers(users);

    }

    public void changeUserData(String name, long id, int level){
        userService.createOrUpdateUser(name, id, level);
        loadUsers();
    }
}

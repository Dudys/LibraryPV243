package cz.muni.fi.pv242.rest.impl;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.ejb.Stateless;
import javax.inject.Inject;

import java.util.Properties;

import cz.muni.fi.pv242.rest.model.User;
import cz.muni.fi.pv242.service.UserService;

/**
 * Created by honza on 5/18/16.
 */
@Stateless
public class RestUserServiceImpl implements cz.muni.fi.pv242.rest.UserService {

    @Inject
    UserService userService;

    @Override
    public User addUser(User user) {
        return userService.createUser(user);
    }

    @Override
    public User getUser(long id) {
        return userService.getUserById(id);
    }

    @Override
    public void disableUser(long id) {
        userService.disableUser(id);

    }

    @Override
    public void enableUser(long id) {
        userService.enableUser(id);
    }

    @Override
    public User updateUser(User updatedUser) {
        return userService.updateUser(updatedUser);
    }

    @Override
    public User findUserByEmail(String email) {

        return userService.getUserByEmail(email);
    }

    @Override
    public String runJob() {
        JobOperator jo = BatchRuntime.getJobOperator();
        long jid = jo.start("createDataJob", new Properties());

        return "job started with jobId " + String.valueOf(jid);
    }


}

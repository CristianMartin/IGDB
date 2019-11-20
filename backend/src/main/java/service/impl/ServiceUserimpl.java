package service.impl;

import dao.interf.UserDAO;
import model.User;
import service.interf.ServiceUser;
import static service.TransactionRunner.run;

public class ServiceUserimpl implements ServiceUser {
    private UserDAO userDAO;


    public ServiceUserimpl(UserDAO userDAO){
        this.userDAO = userDAO;
    }


    @Override
    public User searchUser(Long id) {
        return run(()-> {return this.userDAO.recover(id);});
    }

    //Lo guarda en la Base
    @Override
    public void createUser(User user) {
        run(()->{this.userDAO.save(user);});

    }

    @Override
    public User searchByName(String name) {
        return run(()->{return this.userDAO.searchByName(name);});
    }
}

package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {

    @Override
    public User register(User user) {
        for (User user1 : Storage.people) {
            if (user.getLogin().equals(user1.getLogin())) {
                System.out.println("User with such login have existed");
                return null;
            }
        }

        if (user.getAge() < 18) {
            System.out.println("User age have to more or equal 18");
            return null;
        }

        if (user.getPassword().length() < 6) {
            System.out.println("UserPasswodd have to more or equal 6 characters");
            return null;
        }

        StorageDaoImpl storageDao = new StorageDaoImpl();
        storageDao.add(user);
        for (User user1 : Storage.people) {
            System.out.println(user1.getId() + " " + user1.getAge());
        }
        return user;
    }
}

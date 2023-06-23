package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

import java.util.ArrayList;
import java.util.List;

public class RegistrationServiceImpl implements RegistrationService {
    private final static int MIN_LENGTH_LOGIN = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("User's login can't be null!!!");
        }
        if (isSameLogin(Storage.people, user.getLogin())) {
            throw new RegistrationException("User with same login registered!!!");
        }
        if (user.getLogin().length() < MIN_LENGTH_LOGIN) {
            throw new RegistrationException("User's login can't be least 6 characters!!!");
        }
        Storage.people.add(user);
        return user;
    }

    private boolean isSameLogin(List<User> users, String login) {
        for (User user: users) {
            if (user.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        User u1 = new User( "lllllll", "sa", 1);
        User u2 = new User( "llllll", "sa", 1);
        RegistrationService service = new RegistrationServiceImpl();
        service.register(u1);
        service.register(u2);
        System.out.println(Storage.people.get(0));
        System.out.println(Storage.people.get(1));
    }
}

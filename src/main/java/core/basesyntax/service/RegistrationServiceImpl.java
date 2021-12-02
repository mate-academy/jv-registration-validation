package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import java.util.Objects;

public class RegistrationServiceImpl implements RegistrationService {

    @Override
    public User register(User user) {
        if (user.getLogin() == null || user.getAge() == null || user.getPassword() == null) {
            throw new NullPointerException();
        }
        if (user.getLogin().isEmpty() || user.getAge() < 18
                || user.getAge() > 110 || user.getPassword().length() < 6) {
            throw new RuntimeException();
        }
        for (User users : Storage.people) {
            if (Objects.equals(user.getLogin(), users.getLogin())
                    || user.getLogin().equals(users.getLogin())) {
                throw new RuntimeException();
            }
        }
        Storage.people.add(user);
        return user;
    }
}



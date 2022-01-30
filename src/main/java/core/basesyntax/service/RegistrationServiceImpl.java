package core.basesyntax.service;

import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {

    @Override
    public User register(User user) {
        if (user == null || user.getId() == null
                || user.getLogin() == null || user.getAge() == null
                || user.getPassword() == null || user.getAge() < 18
                || user.getAge() <= 0 || user.getLogin().length() < 6
                || user.getPassword().length() < 6 || user.getLogin().contains(user.getLogin())
                || !user.getPassword().contains("-?\\d+(\\.\\d+)?")
                || Character.isLetter(user.getLogin().charAt(0))) {
            throw new RuntimeException("Something went wrong...");
        }

        return user;
    }
}

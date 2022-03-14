package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_POSSIBLE_AGE = 18;
    private static final int MAX_POSSIBLE_AGE = 99;
    private static final int MIN_POSSIBLE_CHARS = 6;
    private static final int MAX_POSSIBLE_CHARS = 12;
    private final StorageDaoImpl storageDaoimpl = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User couldn't be null");
        }
        User current = storageDaoimpl.get(user.getLogin());
        if (current != null
                && user.getLogin().equals(current.getLogin())) {
            throw new RuntimeException("Added user doesn't meet requirements."
                    + " User with this login: "
                    + user.getLogin() + " already exist.");
        }
        if (!Character.isLetter(user.getLogin().charAt(0))) {
            throw new RuntimeException("Added user doesn't meet requirements."
                    + " User login start with: "
                    + user.getLogin().charAt(0) + " but must start with letter.");
        }
        if (user.getAge() < MIN_POSSIBLE_AGE) {
            throw new RuntimeException("Added user doesn't meet requirements."
                    + " User age: "
                    + user.getAge() + " is less than minimal expected age - "
                    + MIN_POSSIBLE_AGE + ".");
        }
        if (user.getAge() > MAX_POSSIBLE_AGE) {
            throw new RuntimeException("Added user doesn't meet requirements."
                    + " User age: "
                    + user.getAge() + " is more than maximum expected age - "
                    + MAX_POSSIBLE_AGE + ".");
        }
        if (user.getPassword().length() < MIN_POSSIBLE_CHARS) {
            throw new RuntimeException("Added user doesn't meet requirements."
                    + " User password length: "
                    + user.getPassword() + " is less than minimal expected password - "
                    + MIN_POSSIBLE_CHARS + " length.");
        }
        if (user.getPassword().length() > MAX_POSSIBLE_CHARS) {
            throw new RuntimeException("Added user doesn't meet requirements."
                    + " User password length: "
                    + user.getPassword() + " is more than maximum expected password - "
                    + MIN_POSSIBLE_CHARS + "length.");
        }
        return storageDaoimpl.add(user);
    }
}

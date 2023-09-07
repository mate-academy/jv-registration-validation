package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import java.util.regex.Pattern;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int DATA_CORRECT_LENGTH = 6;
    private static final int MINIMUM_AGE = 18;
    private static final int MAXIMUM_AGE = 100;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new FailedRegistrationException(getExceptionMessage(null, "User"));
        }
        checkLogin(user);
        checkPassword(user);
        checkAge(user);
        storageDao.add(user);
        return user;
    }

    private void checkAge(User user) {
        if (user.getAge() < MINIMUM_AGE) {
            throw new FailedRegistrationException("Dear user, you're too young. "
                    + "Please, come back in " + (MINIMUM_AGE - user.getAge()));
        }
        if (user.getAge() > MAXIMUM_AGE) {
            throw new FailedRegistrationException("Dear user, we can't believe,"
                    + " that you are so old.");
        }
    }

    private void checkPassword(User user) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z0-9]{6,20}$";
        Pattern pattern = Pattern.compile(regex);
        if (user.getPassword() == null || !pattern.matcher(user.getPassword()).matches()) {
            throw new FailedRegistrationException("Password must have length [6;20],"
                    + " have at least 1 digits and letters in different cases"
                           + " and it can't has invalid symbols");
        }
    }

    private void checkLogin(User user) {
        String regex = "^(?=.*[a-zA-Z])[a-zA-Z0-9]{6,13}$";
        Pattern pattern = Pattern.compile(regex);
        if (user.getLogin() == null || !pattern.matcher(user.getLogin()).matches()) {
            throw new FailedRegistrationException("Login must have length [6;13], have letters"
                            + " and it can't has invalid symbols");
        }
    }

    private String getExceptionMessage(String inputData, String type) {
        return inputData == null ? type + " can't be null."
                : type + " is too short, actual size = "
                + inputData.length() + ", it should be at least " + DATA_CORRECT_LENGTH;
    }
}

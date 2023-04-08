package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
	private static final int MIN_LOGIN_AND_PASSWORD_LENGTH = 6;
	private static final int MIN_USER_AGE = 18;
	private static final int MIN_ID_LENGTH = 8;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws ValidationException {
		if (user.getId() == null
				|| user.getLogin() == null
				|| user.getPassword() == null
				|| user.getAge() == null) {
			throw new ValidationException("Any user value cannot be null");
		}
		
		if (user.getId().toString().length() >= MIN_ID_LENGTH
				&& storageDao.get(user.getLogin()) == null
				&& user.getLogin().length() >= MIN_LOGIN_AND_PASSWORD_LENGTH
				&& user.getPassword().length() >= MIN_LOGIN_AND_PASSWORD_LENGTH
				&& user.getAge() >= MIN_USER_AGE) {
			storageDao.add(user);
		} else {
			return user;
		}
        return null;
    }
}

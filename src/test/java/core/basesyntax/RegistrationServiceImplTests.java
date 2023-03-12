package core.basesyntax;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceException;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTests {
    private static final String LOGIN_DEFAULT = "yurii.storozhuk@gmail.com";
    private static final String PASSWORD_DEFAULT = "12345678";
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int AGE_MIN = 18;
    private static final int AGE_MAX = 122;
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User user;

    @BeforeEach
    void setUpDefaultUser() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        user = new User(LOGIN_DEFAULT, PASSWORD_DEFAULT, AGE_MIN);
    }

    @Test
    void register_userPasswordEqualsNull_Ok() {
        user.setPassword(null);
        Assertions.assertThrows(RegistrationServiceException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userPasswordMinLength_Ok() {
        User actual = storageDao.add(user);
        Assertions.assertEquals(user, actual);
    }

    @Test
    void register_userPasswordEqualsMinLength_Ok() {
        String passwordMinLength = "a".repeat(PASSWORD_MIN_LENGTH);
        user.setPassword(passwordMinLength);
        User actual = storageDao.add(user);
        Assertions.assertEquals(user, actual);
    }

    @Test
    void register_userPasswordMinLength_notOk() {
        user.setPassword("12345");
        Assertions.assertThrows(RegistrationServiceException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userAgeMin_Ok() throws RegistrationServiceException {
        User actual = storageDao.add(user);
        Assertions.assertEquals(user, actual);
    }

    @Test
    void register_userAgeEqualsMin_Ok() throws RegistrationServiceException {
        user.setAge(AGE_MIN);
        User actual = storageDao.add(user);
        Assertions.assertEquals(user, actual);
    }

    @Test
    void register_userAgeEqualsMax_Ok() throws RegistrationServiceException {
        user.setAge(AGE_MAX);
        User actual = storageDao.add(user);
        Assertions.assertEquals(user, actual);
    }

    @Test
    void register_userAgeMin_notOk() throws RegistrationServiceException {
        user.setAge(16);
        Assertions.assertThrows(RegistrationServiceException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userNegativeAgeMin_Ok() throws RegistrationServiceException {
        user.setAge(-16);
        Assertions.assertThrows(RegistrationServiceException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userAgeEqualsZero_Ok() throws RegistrationServiceException {
        user.setAge(0);
        Assertions.assertThrows(RegistrationServiceException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userAgeEqualsNull_Ok() throws RegistrationServiceException {
        user.setAge(null);
        Assertions.assertThrows(RegistrationServiceException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userAgeMoreThanMax_notOk() throws RegistrationServiceException {
        user.setAge(AGE_MAX + 1);
        Assertions.assertThrows(RegistrationServiceException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userNull_Ok() {
        user = null;
        Assertions.assertThrows(NullPointerException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userExist_Ok() throws Exception {
        storageDao.add(user);
        Assertions.assertThrows(RegistrationServiceException.class, () -> {
            registrationService.register(user);
        });
    }

    @AfterEach
    void finishSetUp() {
        Storage.people.clear();
    }
}

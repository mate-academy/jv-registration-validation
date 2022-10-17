package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String USER_PASSWORD = "54321paSs";
    private static final int USER_AGE = 21;
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final StorageDao storageDao = new StorageDaoImpl();

    @Test
    public void register_userWithSameLoginAlreadyExist_notOk() {
        String userLogin = "Ali852";
        User firstUser = createUser(userLogin, USER_PASSWORD, USER_AGE);
        storageDao.add(firstUser);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(firstUser);
        });
    }

    @Test
    public void register_loginIsNull_notOk() {
        User userWithLoginIsNull = createUser(null, USER_PASSWORD, USER_AGE);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userWithLoginIsNull);
        });
    }

    @Test
    public void register_passwordIsNull_notOk() {
        String userLogin = "Lina960";
        User userWithPasswordIsNull = createUser(userLogin, null, USER_AGE);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userWithPasswordIsNull);
        });
    }

    @Test
    public void register_ageIsNull_notOk() {
        String userLogin = "Mik01";
        User userWithAgeIsNull = createUser(userLogin, USER_PASSWORD, null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userWithAgeIsNull);
        });
    }

    @Test
    public void register_userIsYoung_notOk() {
        String userLogin = "Alex985";
        int age = 16;
        User youngUser = createUser(userLogin, USER_PASSWORD, age);
        youngUser.setAge(age);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(youngUser);
        });
    }

    @Test
    public void register_userIsOfSufficientAge_ok() {
        String userLogin = "Ali002";
        int age = 18;
        User olderUser = createUser(userLogin, USER_PASSWORD, age);
        olderUser.setAge(age);
        assertDoesNotThrow(() -> {
            registrationService.register(olderUser);
        });
    }

    @Test
    public void register_passwordsLengthIsTooShort_notOk() {
        String userLogin = "Bee9641";
        String shortPassword = "12345";
        User userWithShortPassword = createUser(userLogin, shortPassword, USER_AGE);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userWithShortPassword);
        });
    }

    @Test
    public void register_passwordsLengthIsNormal_ok() {
        String userLogin = "Nik88852";
        String normalPassword = "123456789";
        User userWithNormalPassword = createUser(userLogin, normalPassword, USER_AGE);
        assertDoesNotThrow(() -> {
            registrationService.register(userWithNormalPassword);
        });
    }

    @Test
    public void register_userWithCorrectData_ok() {
        String uniqueLogin = "Alisa9987";
        User normalUser = createUser(uniqueLogin, USER_PASSWORD, USER_AGE);
        assertEquals(normalUser, registrationService.register(normalUser));
    }

    private User createUser(String login, String password, Integer age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }
}

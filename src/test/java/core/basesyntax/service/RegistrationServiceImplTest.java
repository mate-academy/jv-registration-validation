package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final RegistrationServiceImpl registrationService =
            new RegistrationServiceImpl();
    private static final StorageDao storageDao = new StorageDaoImpl();
    private static final User VALID_USER_ONE = new User();
    private static final User VALID_USER_TWO = new User();
    private static final User VALID_USER_THREE = new User();
    private static final User EMPTY_USER = new User();
    private static final int LOW_AGE = 15;
    private static final int HIGH_AGE = 160;
    private static Integer[] INVALID_AGE_ARRAY = {Integer.valueOf(LOW_AGE),
            Integer.valueOf(HIGH_AGE),
            null};
    private static final String LOGIN_WITH_WRONG_FIRST = "%sdjfh@mail.com";
    private static final String SHORT_LOGIN = "short";
    private static final String EXISTING_LOGIN = "firstuser@mail.com";
    private static final String[] INVALID_LOGIN_ARRAY = {LOGIN_WITH_WRONG_FIRST,
            SHORT_LOGIN,
            EXISTING_LOGIN,
            null};
    private static final String SHORT_PASSWORD = "123";
    private static final String[] INVALID_PASSWORD_ARRAY = {SHORT_PASSWORD,
            null};
    private static final String VALID_PASSWORD = "12345678";
    private static final User CUSTOM_USER = new User();

    @BeforeAll
    @DisplayName("Set all values")
    static void createUsers() {
        VALID_USER_ONE.setId(123L);
        VALID_USER_ONE.setAge(25);
        VALID_USER_ONE.setLogin("firstuser@mail.com");
        VALID_USER_ONE.setPassword(VALID_PASSWORD);

        VALID_USER_TWO.setId(456L);
        VALID_USER_TWO.setAge(21);
        VALID_USER_TWO.setLogin("seconduser@mail.com");
        VALID_USER_TWO.setPassword(VALID_PASSWORD);

        VALID_USER_THREE.setId(789L);
        VALID_USER_THREE.setAge(46);
        VALID_USER_THREE.setLogin("thirduser@mail.com");
        VALID_USER_THREE.setPassword(VALID_PASSWORD);
    }

    @Test
    void registerSingle_ValidData_Ok() {
        registrationService.register(VALID_USER_ONE);
        User actual = storageDao.get(VALID_USER_ONE.getLogin());
        assertEquals(VALID_USER_ONE, actual);
    }

    @Test
    void registerSeveral_ValidData_Ok() {
        registrationService.register(VALID_USER_TWO);
        registrationService.register(VALID_USER_THREE);
        User actualTwo = storageDao.get(VALID_USER_TWO.getLogin());
        User actualThree = storageDao.get(VALID_USER_THREE.getLogin());
        assertEquals(VALID_USER_TWO, actualTwo);
        assertEquals(VALID_USER_THREE, actualThree);
    }

    @Test
    void registerSingle_EmptyUser_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(EMPTY_USER);
        });
    }

    @Test
    void registerSingle_InvalidAge_NotOk() {
        CUSTOM_USER.setId(123L);
        for (Integer age : INVALID_AGE_ARRAY) {
            CUSTOM_USER.setAge(age);
            assertThrows(RuntimeException.class, () -> {
                registrationService.register(CUSTOM_USER);
            });
        }
    }

    @Test
    void registerSingle_InvalidLogin_NotOk() {
        CUSTOM_USER.setId(123L);
        CUSTOM_USER.setAge(25);
        for (String login : INVALID_LOGIN_ARRAY) {
            CUSTOM_USER.setLogin(login);
            assertThrows(RuntimeException.class, () -> {
                registrationService.register(CUSTOM_USER);
            });
        }
    }

    @Test
    void registerSingle_InvalidPassword_NotOk() {
        CUSTOM_USER.setId(123L);
        CUSTOM_USER.setAge(25);
        CUSTOM_USER.setLogin("changeableuser@mail.com");
        for (String password : INVALID_PASSWORD_ARRAY) {
            CUSTOM_USER.setPassword(password);
            assertThrows(RuntimeException.class, () -> {
                registrationService.register(CUSTOM_USER);
            });
        }
    }
}

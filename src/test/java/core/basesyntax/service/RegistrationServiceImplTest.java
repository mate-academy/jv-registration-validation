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
    //Valid users
    private static final User VALID_USER_ONE = new User();
    private static final User VALID_USER_TWO = new User();
    private static final User VALID_USER_THREE = new User();
    //Valid values
    //Valid ID's
    private static final long ID_ONE = 123L;
    private static final long ID_TWO = 456L;
    private static final long ID_THREE = 789L;
    //Valid ages
    private static final int VALID_AGE_ONE = 18;
    private static final int VALID_AGE_TWO = 25;
    private static final int VALID_AGE_THREE = 41;
    //Valid logins
    private static final String VALID_LOGIN_ONE = "firstuser@mail.com";
    private static final String VALID_LOGIN_TWO = "seconduser@mail.com";
    private static final String VALID_LOGIN_THREE = "thirduser@mail.com";
    //Valid passwords
    private static final String VALID_PASSWORD_ONE = "askj435%";
    private static final String VALID_PASSWORD_TWO = "sdfkjgJKH34";
    private static final String VALID_PASSWORD_THREE = "skfjgh$53d";
    //Invalid users
    private static final User EMPTY_USER = new User();
    private static final User CUSTOM_USER = new User();
    //Invalid values
    //Invalid ages
    private static final int LOW_AGE_ONE = 0;
    private static final int LOW_AGE_TWO = 15;
    private static final Integer[] INVALID_AGE_ARRAY = {Integer.valueOf(LOW_AGE_ONE),
            Integer.valueOf(LOW_AGE_TWO),
            null};
    //Invalid logins
    private static final String SHORT_LOGIN_ONE = "";
    private static final String SHORT_LOGIN_TWO = "short";
    private static final String EXISTING_LOGIN = "firstuser@mail.com";
    private static final String[] INVALID_LOGIN_ARRAY = {SHORT_LOGIN_ONE,
            SHORT_LOGIN_TWO,
            EXISTING_LOGIN,
            null};
    //Invalid passwords
    private static final String SHORT_PASSWORD_ONE = "";
    private static final String SHORT_PASSWORD_TWO = "123";
    private static final String SHORT_PASSWORD_THREE = "12345";
    private static final String[] INVALID_PASSWORD_ARRAY = {SHORT_PASSWORD_ONE,
            SHORT_PASSWORD_TWO,
            SHORT_PASSWORD_THREE,
            null};

    @BeforeAll
    @DisplayName("Set all values")
    static void createUsers() {
        VALID_USER_ONE.setId(ID_ONE);
        VALID_USER_ONE.setAge(VALID_AGE_ONE);
        VALID_USER_ONE.setLogin(VALID_LOGIN_ONE);
        VALID_USER_ONE.setPassword(VALID_PASSWORD_ONE);

        VALID_USER_TWO.setId(ID_TWO);
        VALID_USER_TWO.setAge(VALID_AGE_TWO);
        VALID_USER_TWO.setLogin(VALID_LOGIN_TWO);
        VALID_USER_TWO.setPassword(VALID_PASSWORD_TWO);

        VALID_USER_THREE.setId(ID_THREE);
        VALID_USER_THREE.setAge(VALID_AGE_THREE);
        VALID_USER_THREE.setLogin(VALID_LOGIN_THREE);
        VALID_USER_THREE.setPassword(VALID_PASSWORD_THREE);
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
        CUSTOM_USER.setId(ID_ONE);
        for (Integer age : INVALID_AGE_ARRAY) {
            CUSTOM_USER.setAge(age);
            assertThrows(RuntimeException.class, () -> {
                registrationService.register(CUSTOM_USER);
            });
        }
    }

    @Test
    void registerSingle_InvalidLogin_NotOk() {
        CUSTOM_USER.setId(ID_ONE);
        CUSTOM_USER.setAge(VALID_AGE_ONE);
        for (String login : INVALID_LOGIN_ARRAY) {
            CUSTOM_USER.setLogin(login);
            assertThrows(RuntimeException.class, () -> {
                registrationService.register(CUSTOM_USER);
            });
        }
    }

    @Test
    void registerSingle_InvalidPassword_NotOk() {
        CUSTOM_USER.setId(ID_ONE);
        CUSTOM_USER.setAge(VALID_AGE_ONE);
        CUSTOM_USER.setLogin(VALID_LOGIN_ONE);
        for (String password : INVALID_PASSWORD_ARRAY) {
            CUSTOM_USER.setPassword(password);
            assertThrows(RuntimeException.class, () -> {
                registrationService.register(CUSTOM_USER);
            });
        }
    }
}

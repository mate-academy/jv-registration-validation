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
    private static final long ID = 123L;
    private static final int AGE = 18;
    private static final String LOGIN = "firstuser@mail.com";
    private static final String PASSWORD = "askj435%";
    private static final User EMPTY_USER = new User();
    private static final User CUSTOM_USER = new User();
    private static RegistrationServiceImpl registrationService;

    @BeforeAll
    @DisplayName("Set all values")
    static void createUsers() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void registerSingle_ValidData_Ok() {
        StorageDao storageDao = new StorageDaoImpl();
        User validUser = new User(ID, LOGIN, PASSWORD, AGE);
        registrationService.register(validUser);
        User actual = storageDao.get(validUser.getLogin());
        assertEquals(validUser, actual);
    }

    @Test
    void registerSingle_EmptyUser_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(EMPTY_USER);
        });
    }

    @Test
    void registerSingle_InvalidAge_NotOk() {
        int lowAgeOne = 0;
        int lowAgeTwo = 15;
        Integer[] invalidAgeArray = {Integer.valueOf(lowAgeOne),
                Integer.valueOf(lowAgeTwo),
                null};
        for (Integer age : invalidAgeArray) {
            CUSTOM_USER.setAge(age);
            assertThrows(RuntimeException.class, () -> {
                registrationService.register(CUSTOM_USER);
            });
        }
    }

    @Test
    void registerSingle_InvalidLogin_NotOk() {
        String shortLoginOne = "";
        String shortLoginTwo = "short";
        String existingLogin = "firstuser@mail.com";
        String[] invalidLoginArray = {shortLoginOne,
                shortLoginTwo,
                existingLogin,
                null};
        CUSTOM_USER.setAge(AGE);
        for (String login : invalidLoginArray) {
            CUSTOM_USER.setLogin(login);
            assertThrows(RuntimeException.class, () -> {
                registrationService.register(CUSTOM_USER);
            });
        }
    }

    @Test
    void registerSingle_InvalidPassword_NotOk() {
        String shortPasswordOne = "";
        String shortPasswordTwo = "123";
        String shortPasswordThree = "12345";
        String[] invalidPasswordArray = {shortPasswordOne,
                shortPasswordTwo,
                shortPasswordThree,
                null};
        CUSTOM_USER.setAge(AGE);
        CUSTOM_USER.setLogin(LOGIN);
        for (String password : invalidPasswordArray) {
            CUSTOM_USER.setPassword(password);
            assertThrows(RuntimeException.class, () -> {
                registrationService.register(CUSTOM_USER);
            });
        }
    }
}

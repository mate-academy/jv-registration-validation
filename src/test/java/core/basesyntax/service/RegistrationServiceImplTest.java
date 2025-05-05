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
    private static final User CUSTOM_USER = new User(null, null, null, null);
    private static RegistrationServiceImpl registrationService;

    @BeforeAll
    @DisplayName("Set all values")
    static void createUsers() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void registerUsersWithValidData_Ok() {
        StorageDao storageDao = new StorageDaoImpl();
        User expectedUserOne = new User(ID,
                "firstu",
                "askj4%",
                18);
        User expectedUserTwo = new User(ID,
                "seconduser",
                "ashgJDH3$",
                32);
        registrationService.register(expectedUserOne);
        registrationService.register(expectedUserTwo);
        User actualUserOne = storageDao.get(expectedUserOne.getLogin());
        User actualUserTwo = storageDao.get(expectedUserTwo.getLogin());
        assertEquals(expectedUserOne, actualUserOne);
        assertEquals(expectedUserTwo, actualUserTwo);
    }

    @Test
    void registerEmptyUser_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(CUSTOM_USER);
        });
    }

    @Test
    void registerUserWithInvalidAge_NotOk() {
        int negativeAge = -4;
        int lowAgeOne = 0;
        int lowAgeTwo = 5;
        int lowAgeTree = 15;
        Integer[] invalidAgeArray = {Integer.valueOf(negativeAge),
                Integer.valueOf(lowAgeOne),
                Integer.valueOf(lowAgeTwo),
                Integer.valueOf(lowAgeTree),
                null};
        for (Integer age : invalidAgeArray) {
            CUSTOM_USER.setAge(age);
            assertThrows(RuntimeException.class, () -> {
                registrationService.register(CUSTOM_USER);
            });
        }
    }

    @Test
    void registerUserWithInvalidLogin_NotOk() {
        String shortLoginOne = "";
        String shortLoginTwo = "short";
        String existingLogin = "firstuser@mail.com";
        String[] invalidLoginArray = {shortLoginOne,
                shortLoginTwo,
                existingLogin,
                null};
        CUSTOM_USER.setAge(25);
        for (String login : invalidLoginArray) {
            CUSTOM_USER.setLogin(login);
            assertThrows(RuntimeException.class, () -> {
                registrationService.register(CUSTOM_USER);
            });
        }
    }

    @Test
    void registerUserWithInvalidPassword_NotOk() {
        String shortPasswordOne = "";
        String shortPasswordTwo = "123";
        String shortPasswordThree = "12345";
        String[] invalidPasswordArray = {shortPasswordOne,
                shortPasswordTwo,
                shortPasswordThree,
                null};
        CUSTOM_USER.setAge(59);
        CUSTOM_USER.setLogin("customuser");
        for (String password : invalidPasswordArray) {
            CUSTOM_USER.setPassword(password);
            assertThrows(RuntimeException.class, () -> {
                registrationService.register(CUSTOM_USER);
            });
        }
    }
}

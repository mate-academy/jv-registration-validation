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
    void register_ValidData_Ok() {
        StorageDao storageDao = new StorageDaoImpl();
        User validUserOne = new User(ID,
                "firstu",
                "askj4%",
                18);
        User validUserTwo = new User(ID,
                "seconduser",
                "ashgJDH3$",
                32);
        registrationService.register(validUserOne);
        registrationService.register(validUserTwo);
        User actualOne = storageDao.get(validUserOne.getLogin());
        User actualTwo = storageDao.get(validUserTwo.getLogin());
        assertEquals(validUserOne, actualOne);
        assertEquals(validUserTwo, actualTwo);
    }

    @Test
    void registerSingle_EmptyUser_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(CUSTOM_USER);
        });
    }

    @Test
    void registerSingle_InvalidAge_NotOk() {
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
    void registerSingle_InvalidLogin_NotOk() {
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
    void registerSingle_InvalidPassword_NotOk() {
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

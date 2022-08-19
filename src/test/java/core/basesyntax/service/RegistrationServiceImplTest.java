package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_LOGIN = "tequila";
    private static final String DEFAULT_PASSWORD = "withLime";
    private static final int DEFAULT_AGE = 26;
    private static RegistrationService registrationService;
    private static User correctUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        correctUser = new User();
        correctUser.setLogin(DEFAULT_LOGIN);
        correctUser.setPassword(DEFAULT_PASSWORD);
        correctUser.setAge(DEFAULT_AGE);
    }

    @Test
    public void register_userNull_isNotValid() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    public void register_userSameLogin_isNotValid() {
        registrationService.register(correctUser);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(correctUser);
        });
    }

    @Test
    public void register_userLogin_isValid() {
        User actualUser = registrationService.register(correctUser);
        Assertions.assertEquals(correctUser, actualUser);
        Assertions.assertNotNull(actualUser.getId());
        Assertions.assertTrue(Storage.people.contains(actualUser));
    }

    @Test
    public void register_userLoginNull_isNotValid() {
        correctUser.setLogin(null);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(correctUser);
        });
    }

    @Test
    public void register_userLoginIsEmpty_isNotValid() {
        correctUser.setLogin("");
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(correctUser);
        });
    }

    @Test
    public void register_userLoginContainsEmptySpace_isNotValid() {
        correctUser.setLogin("S   ");
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(correctUser);
        });
    }

    @Test
    public void register_userLoginStartsWithCharacter_isNotValid() {
        correctUser.setLogin("1anita");
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(correctUser);
        });
    }

    @Test
    public void register_userLoginStartsWithCharacter_isValid() {
        correctUser.setLogin(DEFAULT_LOGIN);
        User newUser = registrationService.register(correctUser);
        Assertions.assertEquals(correctUser, newUser);
        Assertions.assertNotNull(newUser.getId());
        Assertions.assertTrue(Storage.people.contains(newUser));
    }

    @Test
    public void register_userPasswordNull_isNotValid() {
        correctUser.setPassword(null);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(correctUser);
        });
    }

    @Test
    public void register_userPasswordLength_isNotValid() {
        correctUser.setPassword("12345");
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(correctUser);
        });
    }

    @Test
    public void register_userPasswordLength_isValid() {
        correctUser.setPassword(DEFAULT_PASSWORD);
        User actualUser = registrationService.register(correctUser);
        Assertions.assertEquals(correctUser, actualUser);
        Assertions.assertNotNull(actualUser.getId());
        Assertions.assertTrue(Storage.people.contains(actualUser));
    }

    @Test
    public void register_userAgeNull_isNotValid() {
        correctUser.setAge(null);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(correctUser);
        });
    }

    @Test
    public void register_userAgeLessThanAccepted_isNotValid() {
        correctUser.setAge(15);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(correctUser);
        });
    }

    @Test
    public void register_userAgeIsLessThanZero_isNotValid() {
        correctUser.setAge(-5);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(correctUser);
        });
    }

    @Test
    public void register_userAge_isValid() {
        correctUser.setAge(DEFAULT_AGE);
        User newUser = registrationService.register(correctUser);
        Assertions.assertEquals(correctUser, newUser);
        Assertions.assertNotNull(newUser.getId());
        Assertions.assertTrue(Storage.people.contains(newUser));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}

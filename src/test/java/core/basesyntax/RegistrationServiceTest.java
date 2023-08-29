package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static final String CORRECT_LOGIN = "CorrectUserLogin";
    private static final String CORRECT_PASSWORD = "12345678";
    private static final int CORRECT_AGE = 20;
    private static User defaultUser;
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @BeforeEach
    void setUp() {
        defaultUser = new User();
        defaultUser.setLogin(CORRECT_LOGIN);
        defaultUser.setAge(CORRECT_AGE);
        defaultUser.setPassword(CORRECT_PASSWORD);
    }

    @Test
    void addCorrectUsers_Ok() {
        Assertions.assertEquals(defaultUser, registrationService.register(defaultUser));
        User secondUser = new User();
        secondUser.setLogin("Login1234");
        secondUser.setPassword(CORRECT_PASSWORD);
        secondUser.setAge(CORRECT_AGE);
        Assertions.assertEquals(secondUser, registrationService.register(secondUser));
        int expectedSize = 2;
        Assertions.assertEquals(expectedSize, Storage.people.size());
    }

    @Test
    void addUserWithLoginEdgeLength_Ok() {
        defaultUser.setLogin("Login1");
        Assertions.assertEquals(defaultUser, registrationService.register(defaultUser));
        User userLoginEdgeLength = new User();
        userLoginEdgeLength.setLogin("Login12");
        userLoginEdgeLength.setPassword(CORRECT_PASSWORD);
        userLoginEdgeLength.setAge(CORRECT_AGE);
        Assertions.assertEquals(userLoginEdgeLength,
                registrationService.register(userLoginEdgeLength));
        int expectedSize = 2;
        Assertions.assertEquals(expectedSize, Storage.people.size());
    }

    @Test
    void addUserWithLoginLessSix_NotOk() {
        defaultUser.setLogin("Log");
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(defaultUser));
        defaultUser.setLogin("Login");
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(defaultUser));
        int expectedSize = 0;
        Assertions.assertEquals(expectedSize, Storage.people.size());
    }

    @Test
    void addExistingUser_NotOk() {
        registrationService.register(defaultUser);
        User existingUser = new User();
        existingUser.setId(defaultUser.getId());
        existingUser.setLogin(defaultUser.getLogin());
        existingUser.setPassword(defaultUser.getPassword());
        existingUser.setAge(defaultUser.getAge());
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(existingUser));
        int expectedSize = 1;
        Assertions.assertEquals(expectedSize, Storage.people.size());
    }

    @Test
    void addUserWithPasswordLessSix_NotOk() {
        defaultUser.setPassword("12345");
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(defaultUser));
        defaultUser.setPassword("abc");
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(defaultUser));
        int expectedSize = 0;
        Assertions.assertEquals(expectedSize, Storage.people.size());
    }

    @Test
    void addUserWithPasswordEdgeLength_Ok() {
        defaultUser.setPassword("123456");
        Assertions.assertEquals(defaultUser, registrationService.register(defaultUser));
        int expectedSize = 1;
        Assertions.assertEquals(expectedSize, Storage.people.size());
    }

    @Test
    void addNullUser_NotOk() {
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(null));
        int expectedSize = 0;
        Assertions.assertEquals(expectedSize, Storage.people.size());
    }

    @Test
    void addUserWithNullLogin_NotOk() {
        defaultUser.setLogin(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(defaultUser));
        int expectedSize = 0;
        Assertions.assertEquals(expectedSize, Storage.people.size());
    }

    @Test
    void addUserWithNullPassword_NotOk() {
        defaultUser.setPassword(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(defaultUser));
        int expectedSize = 0;
        Assertions.assertEquals(expectedSize, Storage.people.size());
    }

    @Test
    void addUserWithNullAge_NotOk() {
        defaultUser.setAge(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(defaultUser));
        int expectedSize = 0;
        Assertions.assertEquals(expectedSize, Storage.people.size());
    }

    @Test
    void addUserAgeEdgeValues_Ok() {
        defaultUser.setAge(18);
        Assertions.assertEquals(defaultUser, registrationService.register(defaultUser));
        int expectedSize = 1;
        Assertions.assertEquals(expectedSize, Storage.people.size());
    }

    @Test
    void addUserWithAgeLess_NotOk() {
        defaultUser.setAge(16);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(defaultUser));
        defaultUser.setAge(17);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(defaultUser));
        int expectedSize = 0;
        Assertions.assertEquals(expectedSize, Storage.people.size());
    }
}

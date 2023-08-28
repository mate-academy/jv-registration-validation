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
    private static User user;
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
        user = new User();
        user.setLogin(CORRECT_LOGIN);
        user.setAge(CORRECT_AGE);
        user.setPassword(CORRECT_PASSWORD);
    }

    @Test
    void addCorrectUsers_Ok() {
        Assertions.assertEquals(user, registrationService.register(user));
        user = new User();
        user.setLogin("Login1234");
        user.setPassword(CORRECT_PASSWORD);
        user.setAge(CORRECT_AGE);
        Assertions.assertEquals(user, registrationService.register(user));
        int expectedSize = 2;
        Assertions.assertEquals(expectedSize, Storage.people.size());
    }

    @Test
    void addUserWithLoginEdgeLength_Ok() {
        user.setLogin("Login1");
        Assertions.assertEquals(user, registrationService.register(user));
        user = new User();
        user.setLogin("Login12");
        user.setPassword(CORRECT_PASSWORD);
        user.setAge(CORRECT_AGE);
        Assertions.assertEquals(user, registrationService.register(user));
        int expectedSize = 2;
        Assertions.assertEquals(expectedSize, Storage.people.size());
    }

    @Test
    void addUserWithLoginLessSix_NotOk() {
        user.setLogin("Log");
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        user.setLogin("Login");
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        int expectedSize = 0;
        Assertions.assertEquals(expectedSize, Storage.people.size());
    }

    @Test
    void addExistingUser_NotOk() {
        registrationService.register(user);
        User existingUser = new User();
        existingUser.setId(user.getId());
        existingUser.setLogin(user.getLogin());
        existingUser.setPassword(user.getPassword());
        existingUser.setAge(user.getAge());
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(existingUser));
        int expectedSize = 1;
        Assertions.assertEquals(expectedSize, Storage.people.size());
    }

    @Test
    void addUserWithPasswordLessSix_NotOk() {
        user.setPassword("12345");
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        user.setPassword("abc");
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        int expectedSize = 0;
        Assertions.assertEquals(expectedSize, Storage.people.size());
    }

    @Test
    void addUserWithPasswordEdgeLength_Ok() {
        user.setPassword("123456");
        Assertions.assertEquals(user, registrationService.register(user));
        int expectedSize = 1;
        Assertions.assertEquals(expectedSize, Storage.people.size());
    }

    @Test
    void addNullUser() {
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(null));
        int expectedSize = 0;
        Assertions.assertEquals(expectedSize, Storage.people.size());
    }

    @Test
    void addUserWithNullLogin() {
        user.setLogin(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        int expectedSize = 0;
        Assertions.assertEquals(expectedSize, Storage.people.size());
    }

    @Test
    void addUserWithNullPassword() {
        user.setPassword(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        int expectedSize = 0;
        Assertions.assertEquals(expectedSize, Storage.people.size());
    }

    @Test
    void addUserWithNullAge() {
        user.setAge(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        int expectedSize = 0;
        Assertions.assertEquals(expectedSize, Storage.people.size());
    }

    @Test
    void addUserAgeEdgeValues_Ok() {
        user.setAge(18);
        Assertions.assertEquals(user, registrationService.register(user));
        int expectedSize = 1;
        Assertions.assertEquals(expectedSize, Storage.people.size());
    }

    @Test
    void addUserWithAgeLess_NotOk() {
        user.setAge(16);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        user.setAge(17);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        int expectedSize = 0;
        Assertions.assertEquals(expectedSize, Storage.people.size());
    }
}

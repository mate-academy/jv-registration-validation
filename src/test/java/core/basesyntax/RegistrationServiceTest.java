package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static User defaultUser;
    private static User user;
    private static RegistrationService service;
    private static int counter = 0;
    private static int expectedSize = 0;

    @BeforeAll
    static void beforeAll() {
        defaultUser = new User();
        defaultUser.setId(1L);
        defaultUser.setLogin("CorrectLogin");
        defaultUser.setAge(19);
        defaultUser.setPassword("Password");
        service = new RegistrationServiceImpl();
        service.register(defaultUser);
        expectedSize++;
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1312422L);
        user.setLogin("SetUpLogin" + counter++);
        user.setAge(19);
        user.setPassword("Password");
    }

    @Test
    void addCorrectUser1_Ok() {
        Assertions.assertEquals(user, service.register(user));
        expectedSize++;
        Assertions.assertEquals(expectedSize, Storage.people.size());
    }

    @Test
    void addCorrectUser2_Ok() {
        Assertions.assertEquals(user, service.register(user));
        expectedSize++;
        Assertions.assertEquals(expectedSize, Storage.people.size());
    }

    @Test
    void addUserWithLoginEdgeLength_Ok() {
        user.setLogin("Login1");
        Assertions.assertEquals(user, service.register(user));
        user = new User();
        user.setLogin("Login1223");
        user.setPassword("1234567");
        user.setAge(20);
        Assertions.assertEquals(user, service.register(user));
        expectedSize += 2;
        Assertions.assertEquals(expectedSize, Storage.people.size());
    }

    @Test
    void addUserWithLoginLessSix_NotOk() {
        user.setLogin("Login");
        Assertions.assertThrows(RegistrationException.class, () -> service.register(user));
        user = new User();
        user.setLogin("Log");
        user.setPassword("1234567");
        user.setAge(20);
        Assertions.assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void addExistingUser_NotOk() {
        Assertions.assertThrows(RegistrationException.class, () -> service.register(defaultUser));
        User existingUser = new User();
        existingUser.setId(defaultUser.getId());
        existingUser.setLogin(defaultUser.getLogin());
        existingUser.setPassword(defaultUser.getPassword());
        existingUser.setAge(defaultUser.getAge());
        Assertions.assertThrows(RegistrationException.class, () -> service.register(existingUser));
    }

    @Test
    void addUserWithPasswordLessSix_NotOk() {
        user.setPassword("12345");
        Assertions.assertThrows(RegistrationException.class, () -> service.register(user));
        user = new User();
        user.setLogin("CorrectLogin");
        user.setPassword("abc");
        user.setAge(20);
        Assertions.assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void addUserWithPasswordEdgeLength_Ok() {
        user.setPassword("123456");
        Assertions.assertEquals(user, service.register(user));
        expectedSize++;
        Assertions.assertEquals(expectedSize, Storage.people.size());
    }

    @Test
    void addUserWithNullValues_NotOk() {
        User nullLoginUser = new User();
        nullLoginUser.setPassword("123456");
        nullLoginUser.setAge(20);
        User nullPasswordUser = new User();
        nullPasswordUser.setLogin("123456");
        nullPasswordUser.setAge(20);
        User nullAgeUser = new User();
        nullAgeUser.setLogin("123456");
        nullAgeUser.setPassword("password");
        User nullValuesUser = new User();
        Assertions.assertThrows(RegistrationException.class, () -> service.register(null));
        Assertions.assertThrows(RegistrationException.class,
                () -> service.register(nullValuesUser));
        Assertions.assertThrows(RegistrationException.class,
                () -> service.register(nullLoginUser));
        Assertions.assertThrows(RegistrationException.class,
                () -> service.register(nullPasswordUser));
        Assertions.assertThrows(RegistrationException.class, () -> service.register(nullAgeUser));
    }

    @Test
    void addUserAgeEdgeValues_Ok() {
        user.setAge(18);
        Assertions.assertEquals(user, service.register(user));
        expectedSize++;
        Assertions.assertEquals(expectedSize, Storage.people.size());
    }

    @Test
    void addUserWithAgeLess_NotOk() {
        user.setAge(17);
        Assertions.assertThrows(RegistrationException.class, () -> service.register(user));
        user = new User();
        user.setLogin("LoginLoginLogin");
        user.setLogin("PasswordPassword");
        user.setAge(16);
        Assertions.assertThrows(RegistrationException.class, () -> service.register(user));
    }
}

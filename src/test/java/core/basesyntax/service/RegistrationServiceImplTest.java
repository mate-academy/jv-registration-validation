package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static core.basesyntax.db.Storage.people;
import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();
    private User user = new User();

    private User userWithDuplicateLogin = new User();
    private User userWithEmptyLogin = new User();

    private User userWithLessAge = new User();
    private User userWithNormalAge = new User();
    private User userWithFulAge = new User();
    private User userWithZeroAge = new User();
    private User userWithNegativeAge = new User();
    private User userWithNullAge = new User();

    private User userWithLessPassword = new User();
    private User userWithNullPassword = new User();

    @BeforeEach
    void setUp(){
        Storage.people.clear();

        user.setAge(30);
        user.setLogin("userLogin");
        user.setPassword("userPassword");

        userWithDuplicateLogin.setAge(50);
        userWithDuplicateLogin.setLogin("userLogin");
        userWithDuplicateLogin.setPassword("userWithDuplicateLogin");

        userWithEmptyLogin.setAge(19);
        userWithEmptyLogin.setLogin("");
        userWithEmptyLogin.setPassword("userWithEmptyLogin");

        userWithLessAge.setAge(17);
        userWithLessAge.setLogin("userWithLessAge");
        userWithLessAge.setPassword("userWithLessAge");

        userWithNormalAge.setAge(18);
        userWithNormalAge.setLogin("userWithNormalAge");
        userWithNormalAge.setPassword("userWithNormalAge");

        userWithFulAge.setAge(19);
        userWithFulAge.setLogin("userWithFulAge");
        userWithFulAge.setPassword("userWithFulAge");

        userWithZeroAge.setAge(-5);
        userWithZeroAge.setLogin("userWithZeroAge");
        userWithZeroAge.setPassword("userWithZeroAge");

        userWithNegativeAge.setAge(-5);
        userWithNegativeAge.setLogin("userWithNegativeAge");
        userWithNegativeAge.setPassword("userWithNegativeAge");

        userWithNullAge.setAge(null);
        userWithNullAge.setLogin("userWithNullAge");
        userWithNullAge.setPassword("userWithNullAge");

        userWithLessPassword.setAge(50);
        userWithLessPassword.setLogin("userWithLessPassword");
        userWithLessPassword.setPassword("pass");

        userWithNullPassword.setAge(18);
        userWithNullPassword.setLogin("userWithPasswordNull");
        userWithNullPassword.setPassword(null);
    }

    @Test
    void storageNotEmpty_Ok() {
        registrationService.register(user);
        boolean actual = people.isEmpty();
        assertFalse(actual);
    }

    @Test
    void loginDuplicate_NotOk() {
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(userWithDuplicateLogin),
                "Expected RuntimeException");
    }

    @Test
    void loginEmpty_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(userWithEmptyLogin),
                "Expected RuntimeException");
    }

    @Test
    void ageLessThan18_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(userWithLessAge),
                "Expected RuntimeException");
    }

    @Test
    void ageThan18_Ok() {
        registrationService.register(userWithNormalAge);
        boolean actual = people.contains(userWithNormalAge);
        assertTrue(actual);
    }

    @Test
    void ageOverThan18_Ok() {
        registrationService.register(userWithFulAge);
        boolean actual = people.contains(userWithFulAge);
        assertTrue(actual);
    }

    @Test
    void ageWithZero_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(userWithZeroAge),
                "Expected RuntimeException");
    }

    @Test
    void ageWithNegative_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(userWithNegativeAge),
                "Expected RuntimeException");
    }

    @Test
    void ageWithNull_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(userWithNullAge),
                "Expected RuntimeException");
    }

    @Test
    void passwordLessThan6_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(userWithLessPassword),
                "Expected RuntimeException");
    }

    @Test
    void passwordNull_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(userWithNullPassword),
                "Expected RuntimeException");
    }

    @Test
    void userNull_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null),
                "Expected RuntimeException");
    }
}

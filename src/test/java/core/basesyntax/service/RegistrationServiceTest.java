package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.NotEnoughAgeForUsingStorageException;
import core.basesyntax.exceptions.UserDoesNotExistException;
import core.basesyntax.exceptions.WrongLoginException;
import core.basesyntax.exceptions.WrongPasswordException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static final RegistrationService registrationService = new RegistrationServiceImpl();
    private static final StorageDao storageDao = new StorageDaoImpl();

    private static User validUser;
    private static User userWithInvalidLogin;
    private static User userWithInvalidPassword;
    private static User userWithInvalidAge;
    private static User userDoesNotExist;

    @BeforeAll
    static void beforeAll() {
        validUser = createUser(13652827L, "qazwsxedc82738", 23, "qwdfrw34");
        userDoesNotExist = createUser(1221L, "qazwhhff738", 23, "qwdfrw34");
        userWithInvalidLogin = createUser(0L, "bob", 18, "qwdfrw");
        userWithInvalidPassword = createUser(0L, "bobik34", 32, "wefwf");
        userWithInvalidAge = createUser(0L, "borisik", 17, "qwdqwerty");
    }

    @Test
    void shouldThrowUserDoesNotExistExceptionWhenUserDoesNotExistInStorage() {
        Exception exception = assertThrows(UserDoesNotExistException.class, () ->
                registrationService.register(userDoesNotExist));
        assertEquals("User was not founded", exception.getMessage());
    }

    @Test
    void shouldThrowWrongLoginExceptionWhenLoginLessSixCharacters() {
        assertThrows(WrongLoginException.class,
                () -> registrationService.register(userWithInvalidLogin));
    }

    @Test
    void shouldThrowWrongPasswordExceptionWhenPasswordLessSixCharacters() {
        assertThrows(WrongPasswordException.class,
                () -> registrationService.register(userWithInvalidPassword));
    }

    @Test
    void shouldThrowNotEnoughAgeForUsingStorage() {
        NotEnoughAgeForUsingStorageException thrown = assertThrows(
                NotEnoughAgeForUsingStorageException.class,
                () -> registrationService.register(userWithInvalidAge),
                "Expected at least 18 y.o"
        );

        assertTrue(thrown.getMessage().contains("Expected at least 18 y.o"));
    }

    @Test
    void shouldReturnValidUserWithoutNullFields() {
        User addUser = storageDao.add(validUser);
        User actualUser = registrationService.register(addUser);
        assertNotNull(actualUser.getPassword());
        assertNotNull(actualUser.getLogin());
        assertNotNull(actualUser.getAge());
    }

    private static User createUser(long id, String login, int age, String password) {
        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setAge(age);
        user.setPassword(password);
        return user;
    }
}

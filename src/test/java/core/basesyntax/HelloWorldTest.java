package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import core.basesyntax.service.UserHasNoLoginException;
import core.basesyntax.service.UserNullException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HelloWorldTest {
    private static final String PLUG = "test";
    private static StorageDao storage;
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void initial() {
        storage = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
     void createValidUser() {
        user = new User();
        user.setAge(42);
        user.setLogin("labudabudapta");
    }

    @Test
    void userIsNull_NotOk() {
        User userNull = null;
        assertThrows(UserNullException.class,
                () -> registrationService.register(userNull),
                "user is null\n");
    }

    @Test
    void userIsYoungerThan18_NotOk() {
        user.setAge(12);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user),
                "condition break: user with age up to 18 was added\n");
    }

    @Test
    void userLoginLesserThanSixCharacters_NotOk() {
        user.setLogin("Robin");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user),
                "too short login\n");
    }

    @Test
    void userLoginIsNull_NotOk() {
        user.setLogin(null);
        assertThrows(UserHasNoLoginException.class,
                () -> registrationService.register(user),
                "login might not be null\n");
    }

    @Test
    void userLoginHasInvalidSymbols_NotOk() {
        user.setLogin("caramba#98");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user),
                "login shouldn't have any special symbol\n");
    }

    @Test
    void userWithDuplicateLoginMayBeAddToDatabase_NotOk() {
        String login = user.getLogin();
        User uniqueUser = storage.get(login);
        while (uniqueUser != null) {
            login = login + PLUG;
            user.setLogin(login);
            uniqueUser = storage.get(login);
        }
        assertEquals(user, registrationService.register(user),
                "method register had to add user to the storage, but it didn't\n");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user),
                "you added user to the storage with duplicate login\n");
    }

    @AfterAll
    static void clearingStorageWithTestingObjects() {
        Storage.people.clear();
    }
}

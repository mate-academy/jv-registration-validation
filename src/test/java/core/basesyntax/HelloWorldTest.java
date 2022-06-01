package core.basesyntax;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HelloWorldTest {
    private StorageDao storage;
    private RegistrationService registrationService;
    private User user;

    @BeforeEach
     void setUp() {
        storage = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
        user = new User();
        user.setAge(42);
        user.setLogin("labudabudapta2022");
        user.setPassword("motorama");
    }

    @Test
    void userAtLeast18_Ok() {
        User result = registrationService.register(user);
        assertEquals(result, user);
    }

    @Test
    void userIsYoungerThan18_notOk() {
        user.setAge(12);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userLoginIsValid_Ok() {
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void userLoginLessThanSixCharacters_notOk() {
        user.setLogin("Robin");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userLoginIsNull_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userLoginHasInvalidSymbols_notOk() {
        user.setLogin("caramba#98");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userWithSuchLoginExistsInDatabase_notOk() {
        User current;
        String login = user.getLogin();
        do {
            current = storage.get(login);
            login = login + "test";
            user.setLogin(login);
        } while (current != null);
        login = login.substring(0, login.length() - 4);
        user.setLogin(login);
        assertEquals(user, registrationService.register(user), "method register had to add user to the storage, but it didn't");
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "you added user to the storage with duplicate login \n");
    }
}

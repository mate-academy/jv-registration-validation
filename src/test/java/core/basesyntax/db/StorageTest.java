package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageTest {
    private static RegistrationService registration;
    private User user;

    @BeforeAll
    static void setUpFirst() {
        registration = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUpEach() {
        user = new User("gerasimov", "wevrewvre", 18);
    }

    @Test
    void sizeOfStorage_Ok() {
        assertEquals(0, Storage.people.size());
    }

    @Test
    void get_UserLogin_Ok() {
        registration.register(user);
        assertEquals(user.getLogin(), Storage.people.get(Storage.people.size() - 1).getLogin());
    }

    @Test
    void register_NewUser_Ok() {
        registration.register(user);
        assertEquals(1, Storage.people.size());
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }
}

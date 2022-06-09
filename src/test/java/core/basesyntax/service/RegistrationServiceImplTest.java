package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static StorageDaoImpl storage;
    private static RegistrationServiceImpl service;

    @BeforeAll
    static void beforeAll() {
        storage = new StorageDaoImpl();
        storage.add(new User(1535854439L, "bobTheOne", "iAmHereAlice", 23));
        service = new RegistrationServiceImpl();
    }

    @Test
    void register_normalUser_Ok() {
        User normalUser = new User(1535855239L, "MisterSandman", "BringMeADreams", 20);
        User user = service.register(normalUser);
        assertNotNull(user);
        assertEquals(normalUser, user);
    }

    @Test
    void register_existingUser_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            service.register(new User(1535854439L, "bobTheOne", "iAmHereAlice", 23));
        });
    }

    @Test
    void register_userAgeLessNotValid_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            service.register(new User(15358545039L, "Shazam", "SHAZAM!!!", 17));
        });
    }

    @Test
    void register_shortPassword_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            service.register(new User(1535855239L, "John", "passw", 19));
        });
    }

    @Test
    void register_nullLogin_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            service.register(new User(1542876439L, null, "iHaveADream", 23));
        });
    }

    @Test
    void register_nullPassword_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            service.register(new User(1542854439L, "Batman", null, 23));
        });
    }

    @Test
    void register_nullAge_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            service.register(new User(1535854439L, "Sakura", "iLoveCards", null));
        });
    }

    @AfterEach
    void tearDown() {
        storage = new StorageDaoImpl();
    }
}

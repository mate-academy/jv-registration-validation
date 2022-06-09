package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static StorageDaoImpl storage;
    private static RegistrationServiceImpl service;

    @BeforeEach
    public void setUp() {
        storage = new StorageDaoImpl();
        service = new RegistrationServiceImpl();
    }

    @Test
    public void register_normalUser_Ok() {
        User normalUser = new User("MisterSandman", "BringMeADreams", 20);
        User user = service.register(normalUser);
        assertNotNull(user);
        assertEquals(normalUser, user);
    }

    @Test
    public void register_existingUser_NotOk() {
        User bob = new User("bobTheOne", "iAmHereAlice", 23);
        User theSameBob = new User("bobTheOne", "iAmHereAlice", 23);
        storage.add(bob);
        storage.add(theSameBob);
        assertThrows(RuntimeException.class, () -> {
            service.register(new User("bobTheOne", "iAmHereAlice", 23));
        });
    }

    @Test
    public void register_userAgeLessNotValid_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            service.register(new User("Shazam", "SHAZAM!!!", 17));
        });
    }

    @Test
    public void register_shortPassword_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            service.register(new User("John", "passw", 19));
        });
    }

    @Test
    public void register_nullLogin_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            service.register(new User(null, "iHaveADream", 23));
        });
    }

    @Test
    public void register_nullPassword_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            service.register(new User("Batman", null, 23));
        });
    }

    @Test
    public void register_nullAge_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            service.register(new User("Sakura", "iLoveCards", null));
        });
    }

    @AfterEach
    public void tearDown() {
        Storage.people.clear();
    }
}

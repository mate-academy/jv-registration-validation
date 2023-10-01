package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static StorageDaoImpl storage;
    private static RegistrationServiceImpl service;

    @BeforeAll
    public static void beforeAll() {
        storage = new StorageDaoImpl();
        service = new RegistrationServiceImpl();
    }

    @Test
    public void register_normalUser_ok() {
        User normalUser = new User("MisterSandman", "BringMeADreams", 20);
        User user = service.register(normalUser);
        assertNotNull(user);
        assertEquals(normalUser, user);
    }

    @Test
    public void register_existingUser_notOk() {
        User bob = new User("bobTheOne", "iAmHereAlice", 23);
        storage.add(bob);
        assertThrows(RuntimeException.class, () ->
                service.register(new User("bobTheOne", "iAmHereAlice", 23)));
    }

    @Test
    public void register_userAgeLessNotValid_notOk() {
        assertThrows(RuntimeException.class, () ->
                service.register(new User("Shazam", "SHAZAM!!!", 17)));
    }

    @Test
    public void register_shortPassword_notOk() {
        assertThrows(RuntimeException.class, () ->
                service.register(new User("John", "passw", 19)));
    }

    @Test
    public void register_nullLogin_notOk() {
        assertThrows(RuntimeException.class, () ->
                service.register(new User(null, "iHaveADream", 23)));

    }

    @Test
    public void register_nullPassword_notOk() {
        assertThrows(RuntimeException.class, () ->
                service.register(new User("Batman", null, 23)));

    }

    @Test
    public void register_nullAge_notOk() {
        assertThrows(RuntimeException.class, () ->
                service.register(new User("Sakura", "iLoveCards", null)));

    }

    @AfterEach
    public void tearDown() {
        Storage.people.clear();
    }
}

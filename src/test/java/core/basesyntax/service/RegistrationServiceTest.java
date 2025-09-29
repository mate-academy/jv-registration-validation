package core.basesyntax.service;

import core.basesyntax.Invalid;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertSame;

class RegistrationServiceTest {
    private StorageDaoImpl dao;
    private RegistrationService service;

    @BeforeEach
    void setUp() {
        dao = new StorageDaoImpl();
        service = new RegistrationServiceImpl(dao);
        Storage.clear();
    }

    @Test
    void register_validUser_Ok() {
        User user1 = new User();
        user1.setAge(20);
        user1.setId(111L);
        user1.setLogin("bruhman");
        user1.setPassword("password");

        User result = service.register(user1);

        assertNotNull(result);
        assertEquals("bruhman", result.getLogin());

        User fromDao = dao.get("bruhman");
        assertNotNull(fromDao);
        assertSame(result, fromDao);
    }

    @Test
    void register_nullUser_notOk() {
        User user1 = new User();
        user1.setAge(20);
        user1.setId(111L);
        user1.setLogin(null);
        user1.setPassword("password");

        Storage.people.add(user1);

        User stored = dao.get(user1.getLogin());
        assertNull(stored);
    }

    @Test
    void register_nullLogin_notOk() {
        User user1 = new User();
        user1.setLogin(null);

        assertThrows(Invalid.class, () -> service.register(user1));
    }

    @Test
    void register_shortLogin_notOk() {
        User user1 = new User();
        user1.setLogin("bruh");

        assertThrows(Invalid.class, () -> service.register(user1));
    }

    @Test
    void register_nullPassword_notOk() {
        User user1 = new User();
        user1.setPassword(null);

        assertNull(user1.getPassword());
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User();
        user.setPassword("pass");

        assertThrows(Invalid.class, () -> service.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        User user1 = new User();
        user1.setAge(null);

        assertNull(user1.getAge());
    }

    @Test
    void register_underAge_notOk() {
        User user1 = new User();
        user1.setAge(17);

        assertThrows(Invalid.class, () -> service.register(user1));
    }

    @Test
    void register_existingLogin_notOk() {
        User user = new User();
        user.setLogin("bruhman");
        dao.add(user);
        User user1 = new User();
        user1.setLogin("bruhman");
        assertThrows(Invalid.class, () -> service.register(user1));
    }

    @Test
    void register_negativeAge_notOk() {
        User user = new User();
        user.setAge(-1);
        assertThrows(Invalid.class, () -> service.register(user));
    }

}

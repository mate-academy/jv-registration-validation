package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageDaoImplTest {
    private StorageDaoImpl storage;
    private User user;

    @BeforeEach
    void setUp() {
        storage = new StorageDaoImpl();

        user = new User();
        user.setId(1L);
        // унікальний логін, щоб тести не злипались між собою навіть при статичному сховищі
        user.setLogin("user_" + System.nanoTime());
        user.setPassword("secret1");
        user.setAge(18);
    }

    @Test
    void get_unknownLogin_returnsNull() {
        User fromStorage = storage.get("no_such_login_" + System.nanoTime());
        assertNull(fromStorage);
    }

    @Test
    void get_nullLogin_returnsNull() {
        User fromStorage = storage.get(null);
        assertNull(fromStorage);
    }

    @Test
    void add_validUser_ok() {
        storage.add(user);

        User fromStorage = storage.get(user.getLogin());
        assertNotNull(fromStorage);
        assertEquals(user.getLogin(), fromStorage.getLogin());
        assertEquals(user.getAge(), fromStorage.getAge());
        assertEquals(user.getId(), fromStorage.getId());
    }

    @Test
    void add_nullUser_throwsException() {
        assertThrows(NullPointerException.class, () -> storage.add(null));
    }

    @Test
    void add_thenGet_differentUsers_ok() {
        User u1 = new User();
        u1.setId(11L);
        u1.setLogin("u1_" + System.nanoTime());
        u1.setPassword("pass123");
        u1.setAge(20);

        User u2 = new User();
        u2.setId(22L);
        u2.setLogin("u2_" + System.nanoTime());
        u2.setPassword("pass456");
        u2.setAge(21);

        storage.add(u1);
        storage.add(u2);

        User from1 = storage.get(u1.getLogin());
        User from2 = storage.get(u2.getLogin());

        assertNotNull(from1);
        assertNotNull(from2);
        assertEquals(u1.getLogin(), from1.getLogin());
        assertEquals(u2.getLogin(), from2.getLogin());
    }
}

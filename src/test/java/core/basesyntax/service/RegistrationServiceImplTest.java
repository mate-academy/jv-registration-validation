package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static StorageDao storageDao;
    private static RegistrationService registrationService;
    private User mark;
    private User sasha;
    private User bob;
    private User alice;
    private User denis;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        mark = new User("Mark","123456", 18);
        sasha = new User("Sasha","ValidPassword", 20);
        bob = new User("Bob","short", 21);
        denis = new User("Denis","isValid", 17);
        alice = new User("Alice","PASSWORD", 19);
    }

    @Test
    void setNullUser_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void setNullLogin_NotOk() {
        mark.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(mark);
        });
    }

    @Test
    void setNullAge_NotOk() {
        mark.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(mark);
        });
    }

    @Test
    void setNullPassword_NotOk() {
        mark.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(mark);
        });
    }

    @Test
    void userAlreadyExistWithSuchLogin_NotOk() {
        storageDao.add(mark);
        storageDao.add(sasha);
        storageDao.add(bob);
        storageDao.add(alice);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User("Mark", "654321", 20));
        });
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User("Sasha","qwerty", 22));
        });
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User("Bob", "asdfGH", 19));
        });
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User("Alice", "LARGE_LETTERS", 18));
        });
    }

    @Test
    void userNotExistWithSuchLogin_Ok() {
        User expected = new User("Mark", "asdq123", 20);
        User actual = registrationService.register(mark);
        assertNotEquals(expected, actual);
    }

    @Test
    void setNotValidAge_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(denis);
        });
    }

    @Test
    void setNegativeAge_NotOk() {
        denis.setAge(-18);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(denis);
        });
    }

    @Test
    void setAgeEqualsAllowAge_Ok() {
        denis.setAge(18);
        storageDao.add(denis);
        assertEquals(denis, storageDao.get(denis.getLogin()));
    }

    @Test
    void setAgeBiggerAgeThanAllow_Ok() {
        denis.setAge(20);
        storageDao.add(denis);
        assertEquals(denis, storageDao.get(denis.getLogin()));
    }

    @Test
    void setPasswordWithLengthLessThanAllow_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(bob);
        });
    }

    @Test
    void setPasswordWithLengthMoreThanAllow_Ok() {
        bob.setPassword("tooLong123435132412346");
        assertEquals(bob, registrationService.register(bob));
    }

    @Test
    void setPasswordWithLengthThatEqualsAllow_Ok() {
        bob.setPassword("123456");
        assertEquals(bob, registrationService.register(bob));
    }

    @Test
    void setPasswordWithSymbols_Ok() {
        bob.setPassword("!@#@$#$!&*^*_-");
        assertEquals(bob, registrationService.register(bob));
    }

    @Test
    void setUserWithValidParameters_Ok() {
        storageDao.add(sasha);
        storageDao.add(alice);
        assertEquals(sasha, storageDao.get(sasha.getLogin()));
        assertEquals(alice, storageDao.get(alice.getLogin()));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}

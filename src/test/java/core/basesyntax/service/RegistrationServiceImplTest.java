package core.basesyntax.service;

import static core.basesyntax.db.Storage.people;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static List<User> users = new ArrayList<>();
    private final StorageDao storageDao = new StorageDaoImpl();
    private final RegistrationServiceImpl registrationService = new RegistrationServiceImpl();

    @BeforeAll
    static void beforeAll() {
        users.add(new User("potrap", "qwerty123", 20));
        users.add(new User("maksym", "jen478fs", 19));
        users.add(new User("obama", "123456789", 65));
        users.add(new User("baiden", "12365t89", 75));
        users.add(new User("hellomoto", "hellooo123", 49));
    }

    @BeforeEach
    void beforeEach() {
        people.clear();
    }

    @Test
    void registration_nullAge_notOk() {
        try {
            registrationService.register(new User("potrap", "qwerty123", null));
        } catch (RuntimeException e) {
            return;
        }
        fail("Should be Runtime exception for age = [null]");
    }

    @Test
    void registration_ageLessThanEighteen_notOk() {
        try {
            registrationService.register(new User("potrap", "qwerty123", 12));
        } catch (RuntimeException e) {
            return;
        }
        fail("Should be Runtime exception for age = [12]");
    }

    @Test
    void registration_tooBigAge_notOk() {
        try {
            registrationService.register(new User("potrap", "qwerty123", 150));
        } catch (RuntimeException e) {
            return;
        }
        fail("Should be Runtime exception for age = [150]");
    }

    @Test
    void registration_nullPassword_notOk() {
        try {
            registrationService.register(new User("potrap", null, 20));
        } catch (RuntimeException e) {
            return;
        }
        fail("Should be Runtime exception for password = [null]");
    }

    @Test
    void registration_passwordShorterThanSixChars_notOk() {
        try {
            registrationService.register(new User("potrap", "bob1", 20));
        } catch (RuntimeException e) {
            return;
        }
        fail("Should be Runtime exception for password = [bob1]");
    }

    @Test
    void registration_nullLogin_notOk() {
        try {
            registrationService.register(new User(null, "qwerty123", 20));
        } catch (RuntimeException e) {
            return;
        }
        fail("Should be Runtime exception for login = [null]");
    }

    @Test
    void registration_nullId_notOk() {
        for (User user : users) {
            user.setId(null);
            registrationService.register(user);
        }
        for (User person : people) {
            try {
                person.getId();
            } catch (NullPointerException e) {
                fail("Id can`t be [null], but id is [null]", e);
            }
        }
    }

    @Test
    void registration_idLessThanZero_notOk() {
        for (User user : users) {
            user.setId(null);
            registrationService.register(user);
        }
        for (User person : people) {
            assertTrue(person.getId() > 0);
        }
    }

    @Test
    void registration_userIsNull_notOk() {
        try {
            registrationService.register(null);
        } catch (RuntimeException e) {
            return;
        }
        fail("Should be Runtime exception for user = [null]");
    }

    @Test
    void registration_idIsNotNull_notOk() {
        User user = new User("potrap", "qwerty123", 20);
        user.setId(50L);
        try {
            registrationService.register(user);
        } catch (RuntimeException e) {
            return;
        }
        fail("Should be Runtime exception for user with id = [50]");
    }

    @Test
    void registration_addTwoSameUsers_notOk() {
        registrationService.register(new User("potrap", "qwerty123", 20));
        try {
            registrationService.register(new User("potrap", "qwerty123", 20));
        } catch (RuntimeException e) {
            return;
        }
        fail("Should be Runtime for two same users");
    }

    @Test
    void registration_addSeveralUsers_ok() {
        for (User user : users) {
            user.setId(null);
            registrationService.register(user);
        }
        for (User user : users) {
            assertEquals(storageDao.get(user.getLogin()), user);
        }
    }

    @Test
    void storageDao_getNullLogin_notOk() {
        for (User user : users) {
            user.setId(null);
            registrationService.register(user);
        }
        assertNull(storageDao.get(null));
    }

    @Test
    void storageDao_getNonexistentLogin_notOk() {
        for (User user : users) {
            user.setId(null);
            registrationService.register(user);
        }
        assertNull(storageDao.get("NonexistentLogin"));
    }
}

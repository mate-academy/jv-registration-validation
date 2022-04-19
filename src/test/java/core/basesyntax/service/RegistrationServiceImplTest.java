package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private User user;
    private RegistrationService registration;

    @BeforeEach
    void setup() {
        user = new User("gerasimov", "wevrewvre", 18);
        registration = new RegistrationServiceImpl();
    }

    @Test
    void ageValidation() {
        user.setAge(15);
        assertThrows(RuntimeException.class, () -> {
            registration.register(user);
        });
    }

    @Test
    void passwordValidation() {
        user.setPassword("rehb");
        assertThrows(RuntimeException.class, () -> {
            registration.register(user);
        });
    }

    @Test
    void nullValue_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registration.register(null);
        });
    }

    @Test
    void addDuplicate_NotOk() {
        registration.register(user);
        assertThrows(RuntimeException.class, () -> {
            registration.register(user);
        });
    }

    @Test
    void addValue_Ok() {
        user.setLogin("ivanov");
        registration.register(user);
        assertEquals(1, Storage.people.size());
    }

    @Test
    void sizeOfStorage_Ok() {
        assertEquals(0, Storage.people.size());
    }

    @Test
    void getValidation() {
        registration.register(user);
        assertEquals(user.getLogin(), Storage.people.get(Math.toIntExact(user.getId()) - 1)
                .getLogin());
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }
}

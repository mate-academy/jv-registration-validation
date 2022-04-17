package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private RegistrationService registrationService;
    private User firstUser;
    private User secondUser;
    private User thirdUser;
    private User fourthUser;
    private User fifthUser;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        firstUser = new User(1L, "Bob", "qwe123", 55);
        secondUser = new User(2L, "Alice", "qwe123", 17);
        thirdUser = new User(3L, "Bob", "567123", 18);
        fourthUser = new User(4L, "John", "qwe56", 19);
        fifthUser = new User(5L, "Max", "qwe569", 33);
    }

    @Test
    void add_Ok() {
        Storage.people.clear();
        registrationService.register(firstUser);
        User registerUser = new User(1L, "Bob", "qwe123", 55);
        boolean actual = Objects.equals(registerUser, Storage.people.get(0));
        assertTrue(actual);
    }

    @Test
    void repeatingName_NotOk() {
        Storage.people.clear();
        registrationService.register(firstUser);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(thirdUser);
        });
    }

    @Test
    void largeAge_NotOk() {
        Storage.people.clear();
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(secondUser);
        });
    }

    @Test
    void largeAge_Ok() {
        Storage.people.clear();
        registrationService.register(thirdUser);
        assertTrue(Storage.people.get(0).getAge() >= MIN_AGE);
    }

    @Test
    void validPasswordLength_NotOk() {
        Storage.people.clear();
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(fourthUser);
        });
    }

    @Test
    void validPasswordLength_Ok() {
        Storage.people.clear();
        registrationService.register(firstUser);
        assertTrue(Storage.people.get(0).getPassword().length() >= MIN_PASSWORD_LENGTH);
    }

    @Test
    void validUserCase_ok() {
        Storage.people.clear();
        registrationService.register(firstUser);
        registrationService.register(fifthUser);
        assertEquals(2, Storage.people.size());
    }

    @Test
    void userNull_NotOk() {
        Storage.people.clear();
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(null);
        });
    }
}

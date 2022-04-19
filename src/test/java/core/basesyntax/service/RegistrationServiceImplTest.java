package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import java.util.Objects;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static RegistrationService registrationService;
    private static User firstUser;
    private static User secondUser;
    private static User thirdUser;
    private static User fourthUser;
    private static User fifthUser;
    private static User sixthUser;
    private static User seventhUser;
    private static User eighthUser;
    private static User ninthUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        firstUser = new User("Bob", "qwe123", 55);
        secondUser = new User("Alice", "qwe123", 17);
        thirdUser = new User("Bob", "567123", 18);
        fourthUser = new User("John", "qwe56", 19);
        fifthUser = new User("Max", "qwe569", 33);
        sixthUser = new User(null, "qwe569", 33);
        seventhUser = new User("", "qwe569", 33);
        eighthUser = new User("Ivan", "", 33);
        ninthUser = new User("Alex", null, 33);
    }

    @BeforeEach
    void userRegister_Ok() {
        Storage.people.clear();
        registrationService.register(firstUser);
        User registerUser = new User("Bob", "qwe123", 55);
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
        assertEquals(Storage.people.get(0).getAge(), thirdUser.getAge());
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
    void userRegisterWithNull_NotOk() {
        Storage.people.clear();
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void userNameNullOrEmpty_NotOk() {
        Storage.people.clear();
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(sixthUser);
        });
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(seventhUser);
        });
    }

    @Test
    void userPasswordNullOrEmpty_NotOk() {
        Storage.people.clear();
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(eighthUser);
        });
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(ninthUser);
        });
    }
}

package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
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
    void setup() {
        registrationService.register(firstUser);
        assertEquals(firstUser, Storage.people.get(0));
        Storage.people.clear();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_repeatingName_NotOk() {
        registrationService.register(firstUser);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(thirdUser);
        });
    }

    @Test
    void largeAge_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(secondUser);
        });
    }

    @Test
    void largeAge_Ok() {
        registrationService.register(thirdUser);
        assertTrue(Storage.people.get(0).getAge() >= MIN_AGE);
        assertEquals(Storage.people.get(0).getAge(), thirdUser.getAge());
    }

    @Test
    void validPasswordLength_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(fourthUser);
        });
    }

    @Test
    void validPasswordLength_Ok() {
        registrationService.register(firstUser);
        assertTrue(Storage.people.get(0).getPassword().length() >= MIN_PASSWORD_LENGTH);
        assertEquals(Storage.people.get(0).getPassword(), firstUser.getPassword());
    }

    @Test
    void validUserCase_ok() {
        registrationService.register(firstUser);
        registrationService.register(fifthUser);
        assertEquals(2, Storage.people.size());
    }

    @Test
    void userRegisterWithNull_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void userNameNullOrEmpty_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(sixthUser);
        });
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(seventhUser);
        });
    }

    @Test
    void userPasswordNullOrEmpty_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(eighthUser);
        });
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(ninthUser);
        });
    }
}

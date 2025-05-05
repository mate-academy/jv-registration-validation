package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static User user;
    private static final String LOGIN = "qwerty";
    private static final String PASSWORD = "qwerty12";
    private static final String EMPTY_ENTRY = "";
    private static final int AGE = 18;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User(LOGIN, PASSWORD, AGE);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(user);
        }, " expected " + RegistrationFailedException.class.getName()
                + " to be thrown for null login "
                + user.getLogin()
                + " but it wasn't");
        user.setLogin(EMPTY_ENTRY);
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(user);
        }, " expected " + RegistrationFailedException.class.getName()
                + " to be thrown for empty entry login "
                + user.getLogin()
                + " but it wasn't");
    }

    @Test
    void register_existLogin_notOk() {
        User anotherUser = new User(LOGIN, PASSWORD, AGE);
        Storage.people.add(user);
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(anotherUser);
        }, " expected " + RegistrationFailedException.class.getName()
                + " to be thrown for exist login "
                + anotherUser.getLogin()
                + " but it wasn't");
    }

    @Test
    void register_lessLengthLogin_notOk() {
        user.setLogin("qwer");
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(user);
        }, " expected " + RegistrationFailedException.class.getName()
                + " to be thrown for less length login "
                + user.getLogin()
                + " but it wasn't");
    }

    @Test
    void register_overLengthLogin_notOk() {
        user.setLogin("qwerty12345");
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(user);
        }, " expected " + RegistrationFailedException.class.getName()
                + " to be thrown for over length login "
                + user.getLogin()
                + " but it wasn't");
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(user);
        }, " expected " + RegistrationFailedException.class.getName()
                + " to be thrown for null password "
                + user.getPassword()
                + " but it wasn't");
        user.setPassword(EMPTY_ENTRY);
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(user);
        }, " expected " + RegistrationFailedException.class.getName()
                + " to be thrown for empty entry password "
                + user.getPassword()
                + " but it wasn't");
    }

    @Test
    void register_lessLengthPassword_notOk() {
        user.setPassword("qwer");
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(user);
        }, " expected " + RegistrationFailedException.class.getName()
                + " to be thrown for less length password "
                + user.getPassword()
                + " but it wasn't");
    }

    @Test
    void register_overLengthPassword_notOk() {
        user.setPassword("qwerty123456789012345678901234567890");
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(user);
        }, " expected " + RegistrationFailedException.class.getName()
                + " to be thrown for over password length "
                + user.getPassword()
                + " but it wasn't");
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(user);
        }, " expected " + RegistrationFailedException.class.getName()
                + " to be thrown for null age "
                + user.getAge()
                + " but it wasn't");
    }

    @Test
    void register_lessAge_notOk() {
        user.setAge(17);
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(user);
        }, " expected " + RegistrationFailedException.class.getName()
                + " to be thrown for less age "
                + user.getAge()
                + " but it wasn't");
    }

    @Test
    void register_overAge_notOk() {
        user.setAge(101);
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(user);
        }, " expected " + RegistrationFailedException.class.getName()
                + " to be thrown for over age "
                + user.getAge()
                + " but it wasn't");
    }

    @Test
    void register_successful_ok() {
        registrationService.register(user);
        Storage.people.add(user);
        assertEquals(Storage.people.get(0).getId(), user.getId());
    }
}

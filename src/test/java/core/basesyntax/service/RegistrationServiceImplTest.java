package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD = 6;
    private static RegistrationService registrationService;
    private static final int STORAGE_LIST_SIZE = 2;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(321123L);
        user.setPassword("hawkIsGood");
        user.setLogin("SomeLogin");
        user.setAge(30);
    }

    @Test
    void register_Young_IsNotOk() {
        user.setAge(16);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "Expected exception when user age is less than required");
    }

    @Test
    void register_Old_IsOk() {
        User register = registrationService.register(user);
        assertTrue(register.getAge() > MIN_AGE, "Good! You age perfect for registration");
    }

    @Test
    void register_AgeNull_IsNotOk() {
        user.setAge(null);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "Expected exception when user age is null");
    }

    @Test
    void register_PasswordSize_IsOk() {
        User register = registrationService.register(user);
        assertTrue(register.getPassword().length() > MIN_PASSWORD, "Good! Password have nice size");
    }

    @Test
    void register_PasswordSize_IsNotOk() {
        user.setPassword("abc");
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "Password to small. Try agan");
    }

    @Test
    void register_PasswordNull_IsNotOk() {
        user.setPassword(null);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "Expected exception when user password is null");
    }

    @Test
    void register_UserIdNull_IsNotOk() {
        user.setId(null);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "Expected exception when user ID is null");
    }

    @Test
    void register_UserNull_IsNotOk() {
        user = null;
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "Expected exception when user is null");
    }

    @Test
    void register_LoginNull_IsNotOk() {
        user.setLogin(null);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "Login is null. Give some login for user");
    }

    @Test
    void already_HaveThisUser_IsNotOk() {
        Storage.people.add(user);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void check_SizeOfStorage_IsOk() {
        User anotherUser = new User();
        anotherUser.setPassword("hawkIsGood");
        anotherUser.setLogin("theAnotherLogin");
        anotherUser.setAge(30);
        anotherUser.setId(321123L);
        Storage.people.add(user);
        Storage.people.add(anotherUser);
        assertEquals(STORAGE_LIST_SIZE,Storage.people.size());
    }

    @AfterEach
    public void clear() {
        Storage.people.clear();
    }
}

package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.excpt.NotValidUserData;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static User user;

    @BeforeAll
    public static void setUp() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
    }

    @BeforeEach
    public void before() {
        user.setAge(18);
        user.setPassword("Abracadabra");
        user.setLogin("Abracadabra");
    }

    @AfterEach
    public void after() {
        if (Storage.people.contains(user)) {
            Storage.people.remove(user);
        }
    }

    @Test
    public void register_minCharactersLogin_notOk() {
        user.setLogin("abc");
        assertThrows(NotValidUserData.class, () -> registrationService.register(user));
    }

    @Test
    public void register_minAge_notOk() {
        user.setAge(17);
        assertThrows(NotValidUserData.class, () -> registrationService.register(user));
    }

    @Test
    public void register_minCharactersPassword_notOk() {
        user.setPassword("abc");
        assertThrows(NotValidUserData.class, () -> registrationService.register(user));
    }

    @Test
    public void register_LoginIsNull_notOk() {
        user.setLogin(null);
        assertThrows(NotValidUserData.class, () -> registrationService.register(user));
    }

    @Test
    public void register_ageIsNull_notOk() {
        user.setAge(null);
        assertThrows(NotValidUserData.class, () -> registrationService.register(user));
    }

    @Test
    public void register_passwordIsNull_notOk() {
        user.setPassword(null);
        assertThrows(NotValidUserData.class, () -> registrationService.register(user));
    }

    @Test
    public void register_existingUserAdd_notOk() {
        Storage.people.add(user);
        assertThrows(NotValidUserData.class, () -> registrationService.register(user));
    }

    @Test
    public void register_addUser_Ok() {
        assertEquals(user, registrationService.register(user),
                "Register method should return added user");
    }
}

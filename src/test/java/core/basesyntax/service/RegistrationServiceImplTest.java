package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.excpt.NotValidUserData;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static User user;

    @BeforeAll
    public static void setUp() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
    }

    @BeforeEach
    public void beforeFirst() {
        user.setAge(18);
        user.setPassword("Abracadabra");
        user.setLogin("Abracadabra");
    }

    @AfterEach
    public void afterFirst() {
        if (Storage.people.contains(user)) {
            Storage.people.remove(user);
        }
    }

    @Test
    public void getUser_minCharactersLogin_notOk() {
        user.setLogin("abc");
        assertThrows(NotValidUserData.class, () -> registrationService.register(user));
    }

    @Test
    public void getUser_minAge_notOk() {
        user.setAge(17);
        assertThrows(NotValidUserData.class, () -> registrationService.register(user));
    }

    @Test
    public void getUser_minCharactersPassword_notOk() {
        user.setPassword("abc");
        assertThrows(NotValidUserData.class, () -> registrationService.register(user));
    }

    @Test
    public void getUser_existingUserAdd_notOk() {
        Storage.people.add(user);
        assertThrows(NotValidUserData.class, () -> registrationService.register(user));
    }

    @Test
    public void getUser_addUser_Ok() {
        assertEquals(user, registrationService.register(user),
                "Register method should return added user");
    }
}
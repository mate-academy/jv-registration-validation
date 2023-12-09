package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegisterDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int DEF_PEOPLE_AMOUNT = 3;
    private static final String WRONG_EDGE_PASS = "1eDf5";
    private static final String WRITE_EDGE_PASS = "1e3f56";
    private static final String WRONG_EDGE_LOGIN = "David";
    private static final String WRITE_EDGE_LOGIN = "David6";
    private static final int WRONG_EDGE_AGE = 17;
    private static final int WRITE_EDGE_AGE = 18;
    private static RegistrationService registrationService;
    private User testUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.add(new User("Cristal", "gGfe18gS12", 21));
        Storage.people.add(new User("madPavel", "QWERTY0123", 20));
        Storage.people.add(new User("SimonB", "GH12fge^&3", 45));
        testUser = new User("BobSalimon", "asd$%DF#$234fgg", 28);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_addUserWithCorrectLog_ok() {
        testUser.setLogin(WRITE_EDGE_LOGIN);
        User registerUser = registrationService.register(testUser);
        assertEquals(testUser, registerUser);
        assertEquals(DEF_PEOPLE_AMOUNT + 1, Storage.people.size());

        User newTestUser = new User(WRITE_EDGE_LOGIN + "7", WRITE_EDGE_PASS, WRITE_EDGE_AGE);
        registerUser = registrationService.register(newTestUser);
        assertEquals(newTestUser, registerUser);
        assertEquals(DEF_PEOPLE_AMOUNT + 2, Storage.people.size());
    }

    @Test
    void register_addUserWithCorrectPass_ok() {
        testUser.setLogin(WRITE_EDGE_PASS);
        User registerUser = registrationService.register(testUser);
        assertEquals(testUser, registerUser);
        assertEquals(DEF_PEOPLE_AMOUNT + 1, Storage.people.size());

        User newTestUser = new User(WRITE_EDGE_LOGIN, WRITE_EDGE_PASS + "7", WRITE_EDGE_AGE);
        registerUser = registrationService.register(newTestUser);
        assertEquals(newTestUser, registerUser);
        assertEquals(DEF_PEOPLE_AMOUNT + 2, Storage.people.size());
    }

    @Test
    void register_addUserWithCorrectAge_ok() {
        testUser.setAge(WRITE_EDGE_AGE);
        User registerUser = registrationService.register(testUser);
        assertEquals(testUser, registerUser);
        assertEquals(DEF_PEOPLE_AMOUNT + 1, Storage.people.size());

        User newTestUser = new User(WRITE_EDGE_LOGIN, WRITE_EDGE_PASS, WRITE_EDGE_AGE + 1);
        registerUser = registrationService.register(newTestUser);
        assertEquals(newTestUser, registerUser);
        assertEquals(DEF_PEOPLE_AMOUNT + 2, Storage.people.size());
    }

    @Test
    void register_SameUserLogin_notOk() {
        User sameUser = new User("Cristal", "12gGfe18gS", 25);
        assertThrows(RegisterDataException.class, () -> registrationService.register(sameUser));
    }

    @Test
    void register_invalidPassword_notOK() {
        testUser.setPassword(null);
        assertThrows(RegisterDataException.class, () -> registrationService.register(testUser));

        testUser.setPassword("1234");
        assertThrows(RegisterDataException.class, () -> registrationService.register(testUser));

        testUser.setPassword(WRONG_EDGE_PASS);
        assertThrows(RegisterDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_invalidLogin_notOK() {
        testUser.setLogin(null);
        assertThrows(RegisterDataException.class, () -> registrationService.register(testUser));

        testUser.setLogin("ABBY");
        assertThrows(RegisterDataException.class, () -> registrationService.register(testUser));

        testUser.setLogin(WRONG_EDGE_LOGIN);
        assertThrows(RegisterDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_wrongAge_notOk() {
        testUser.setAge(null);
        assertThrows(RegisterDataException.class, () -> registrationService.register(testUser));

        testUser.setAge(WRONG_EDGE_AGE);
        assertThrows(RegisterDataException.class, () -> registrationService.register(testUser));

        testUser.setAge(-WRONG_EDGE_AGE);
        assertThrows(RegisterDataException.class, () -> registrationService.register(testUser));
    }
}

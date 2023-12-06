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
    private static final String WRONG_PASS = "weDfD123";
    private static final String WRITE_PASS = "weDfD1fgr#4";
    private static final String WRONG_LOGIN = "David";
    private static final String WRITE_LOGIN = "DavidDanis";
    private static final int WRONG_AGE = 17;
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
    void register_lengthAfterRegistration_ok() {
        int additionalUsers = 50;
        for (int i = 0; i < additionalUsers; i++) {
            registrationService.register(new User("BobSalimon" + i, "asd$%DF#$234fgg", 28));
        }
        assertEquals(DEF_PEOPLE_AMOUNT + additionalUsers, Storage.people.size());
    }

    @Test
    void register_addUserWithCorrectLog_ok() {
        testUser.setLogin(WRITE_LOGIN);
        User registerUser = registrationService.register(testUser);
        assertEquals(testUser, registerUser);
        assertEquals(DEF_PEOPLE_AMOUNT + 1, Storage.people.size());
    }

    @Test
    void register_addUserWithCorrectPass_ok() {
        testUser.setLogin(WRITE_PASS);
        User registerUser = registrationService.register(testUser);
        assertEquals(testUser, registerUser);
        assertEquals(DEF_PEOPLE_AMOUNT + 1, Storage.people.size());
    }

    @Test
    void register_SameUserName_notOk() {
        User sameUser = Storage.people.get(DEF_PEOPLE_AMOUNT - 1);
        assertThrows(RegisterDataException.class, () -> registrationService.register(sameUser));
    }

    @Test
    void register_passLessThanSixChars_notOK() {
        testUser.setPassword(WRONG_PASS);
        assertThrows(RegisterDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_loginLessThanSixChars_notOK() {
        testUser.setLogin(WRONG_LOGIN);
        assertThrows(RegisterDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_wrongAge_notOk() {
        testUser.setAge(WRONG_AGE);
        assertThrows(RegisterDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_negativeAge_notOk() {
        testUser.setAge(-WRONG_AGE);
        assertThrows(RegisterDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_nullAge_notOk() {
        testUser.setAge(null);
        assertThrows(RegisterDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_nullLogin_notOk() {
        testUser.setLogin(null);
        assertThrows(RegisterDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_nullPass_notOk() {
        testUser.setPassword(null);
        assertThrows(RegisterDataException.class, () -> registrationService.register(testUser));
    }
}

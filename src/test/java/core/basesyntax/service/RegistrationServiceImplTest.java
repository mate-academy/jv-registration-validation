package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final RegistrationServiceImpl registrationService =
            new RegistrationServiceImpl();
    private static final User firstUser = new User();
    private static final User secondUser = new User();
    private static final User thirdUser = new User();
    private static final User testUser = new User();

    @BeforeEach
    void setUp() {
        firstUser.setLogin("Login123");
        firstUser.setPassword("Passworsd123");
        firstUser.setAge(19);
        secondUser.setLogin("SimpleLogin");
        secondUser.setPassword("@Passworsd@");
        secondUser.setAge(21);
        thirdUser.setLogin("ShortLogin");
        thirdUser.setPassword("NotAPassword");
        thirdUser.setAge(39);
        firstUser.setId((long)1);
        secondUser.setId((long)2);
        thirdUser.setId((long)3);
    }

    @AfterEach
    void reset() {
        Storage.people.clear();
    }

    @Test
    void register_nullAge_notOk() {
        testUser.setLogin("JustALogin");
        testUser.setPassword("JustAPassword");
        testUser.setAge(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser),
                "Missing information: User age");
    }

    @Test
    void register_AgeBelow18_notOk() {
        testUser.setLogin("JustALogin");
        testUser.setPassword("JustAPassword");
        testUser.setAge(12);
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser),
                "You have to be at least 18 years old to register");
    }

    @Test
    void register_loginNull_notOk() {
        testUser.setLogin(null);
        testUser.setPassword("JustAPassword");
        testUser.setAge(21);
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser),
                "Missing information: User login ");
    }

    @Test
    void register_loginToShort_notOk() {
        testUser.setLogin("Login");
        testUser.setPassword("JustAPass");
        testUser.setAge(21);
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser),
                "Login is too short");
    }

    @Test
    void register_passwordNull_notOk() throws InvalidDataException {
        testUser.setLogin("JustALogin");
        testUser.setPassword(null);
        testUser.setAge(21);
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser),
                "Missing information: User Password");
    }

    @Test
    void register_passwordToShort_notOk() {
        testUser.setLogin("JustALogin");
        testUser.setPassword("APass");
        testUser.setAge(21);
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser),
                "Password is too short");
    }

    @Test
    void register_sameUser_notOk() throws InvalidDataException {
        testUser.setLogin(firstUser.getLogin());
        testUser.setPassword("APass");
        testUser.setAge(21);
        registrationService.register(firstUser);
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser),
                "this login already exists");

    }

    @Test
    void register_users_Ok() throws InvalidDataException {
        List<User> expected = new ArrayList<>();
        expected.add(firstUser);
        expected.add(secondUser);
        expected.add(thirdUser);
        registrationService.register(firstUser);
        registrationService.register(secondUser);
        registrationService.register(thirdUser);
        assertEquals(expected, Storage.people);
    }

    @Test
    void userWithSameID_Nok() throws InvalidDataException {
        registrationService.register(firstUser);
        registrationService.register(secondUser);
        registrationService.register(thirdUser);
        assertFalse(firstUser.getId().equals(secondUser.getId()));
        assertFalse(firstUser.getId().equals(thirdUser.getId()));
    }
}

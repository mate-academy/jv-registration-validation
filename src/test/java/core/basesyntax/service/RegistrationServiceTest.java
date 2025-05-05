package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static final int MIN_AGE = 18;
    private static final int BOUND_NUMBER = 10;
    private static StorageDao storageDao;
    private static Random random;
    private static RegistrationService registrationService;

    @BeforeEach
    public void setUp() {
        storageDao = new StorageDaoImpl();
        random = new Random();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void register_existingUser_throwsException() {
        User user1 = new User("Recruit", "password", MIN_AGE);
        storageDao.add(user1);

        User user2 = new User("Recruit", "KingBoss", MIN_AGE);

        assertThrows(InvalidDataException.class, () -> registrationService.register(user2));
    }

    @Test
    public void register_loginLength_lessThan6_throwsException() {
        User user = new User("PON", "MyCode", MIN_AGE);

        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_loginLength_5_registrationFailed() {
        User user = new User("derek", "derekD12", MIN_AGE);

        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_loginLength_6_registrationSuccessful() {
        User user = new User("Peters", "passCodes", MIN_AGE);
        User actual = registrationService.register(user);

        assertEquals(user, actual);
    }

    @Test
    public void register_loginLength_moreThan6_registrationSuccessful() {
        User user = new User("NancyHale", "passwords-1", MIN_AGE);
        User actual = registrationService.register(user);

        assertEquals(user, actual);
    }

    @Test
    public void register_loginLength_7_registrationSuccessful() {
        User user = new User("NancyHa", "passwords-1", MIN_AGE);
        User actual = registrationService.register(user);

        assertEquals(user, actual);
    }

    @Test
    public void register_passwordLength_lessThan6_throwsException() {
        User user = new User("JonnyHale", "pas", MIN_AGE);

        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_passwordLength_5_throwsException() {
        User user = new User("DepHale", "123de", MIN_AGE);

        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_passwordLength_6_registrationSuccessful() {
        User user = new User("AlexHale", "paSSco", MIN_AGE);
        User actual = registrationService.register(user);

        assertEquals(user, actual);
    }

    @Test
    public void register_passwordLength_moreThan6_registrationSuccessful() {
        User user = new User("ScottHale", "pass_code", MIN_AGE);
        User actual = registrationService.register(user);

        assertEquals(user, actual);
    }

    @Test
    public void register_passwordLength_7_registrationSuccessful() {
        User user = new User("JenniferHale", "pass_co", MIN_AGE);
        User actual = registrationService.register(user);

        assertEquals(user, actual);
    }

    @Test
    public void register_age_lessThan18_throwsException() {
        User user = new User("TaliaHale", "password",
                MIN_AGE - random.nextInt(1, BOUND_NUMBER));

        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_age_17_throwsException() {
        User user = new User("AmandaHale", "password", MIN_AGE - 1);

        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_age_18_registrationSuccessful() {
        User user = new User("DerekHale", "password-10", MIN_AGE);
        User actual = registrationService.register(user);

        assertEquals(user, actual);
    }

    @Test
    public void register_age_over18_registrationSuccessful() {
        User user = new User("GaliHale", "password-1",
                MIN_AGE + random.nextInt(1, BOUND_NUMBER));
        User actual = registrationService.register(user);

        assertEquals(user, actual);

    }

    @Test
    public void register_age_19_registrationSuccessful() {
        User user = new User("DemiHale", "password-1", MIN_AGE + 1);
        User actual = registrationService.register(user);

        assertEquals(user, actual);
    }

    @Test
    public void register_age_negative_throwsException() {
        User user = new User("HarlanHale", "password-1", - MIN_AGE);

        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_loginIsNull_throwsException() {
        User user = new User(null, "password", MIN_AGE);

        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_passwordIsNull_throwsException() {
        User user = new User("LexCorp", null, MIN_AGE);

        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_ageIsNull_throwsException() {
        User user = new User("LexCorp", "Lex123", null);

        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_validUser_isOk() {
        User user = new User("user123", "password", MIN_AGE + random.nextInt(BOUND_NUMBER));
        User registeredUser = registrationService.register(user);

        assertEquals(user, registeredUser, "Validation of user was failed!");
    }
}

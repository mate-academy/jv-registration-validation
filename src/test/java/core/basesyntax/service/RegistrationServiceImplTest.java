package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_USERS_AGE = 18;
    private static final int MAX_USERS_AGE = 150;
    private static final int CORRECT_USERS_AGE = 25;
    private static final int UNDER_MIN_USERS_AGE = 16;
    private static final int OVER_MAX_USERS_AGE = 190;
    private static final int FIRST_INCORRECT_AGE = 3965496;
    private static final int SECOND_INCORRECT_AGE = -32;
    private static RegistrationService registrationService;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        User arnold = new User();
        arnold.setAge(CORRECT_USERS_AGE);
        arnold.setLogin("Arny");
        arnold.setPassword("1*hD#Sak");
        storageDao.add(arnold);
    }

    @Test
    void register_userWithCorrectData_Ok() {
        User sam = new User();
        sam.setAge(CORRECT_USERS_AGE);
        sam.setLogin("Sammy");
        sam.setPassword("password");
        User testUser = registrationService.register(sam);
        assertTrue(sam.equals(testUser));
    }

    @Test
    void register_userWithMinAge_Ok() {
        User sam = new User();
        sam.setAge(MIN_USERS_AGE);
        sam.setLogin("LittleSam");
        sam.setPassword("Sam9384");
        User testUser = registrationService.register(sam);
        assertTrue(sam.equals(testUser));
    }

    @Test
    void register_userWithMaxAge_Ok() {
        User sam = new User();
        sam.setAge(MAX_USERS_AGE);
        sam.setLogin("BigSam");
        sam.setPassword("Ro9v-*.al");
        User testUser = registrationService.register(sam);
        assertTrue(sam.equals(testUser));
    }

    @Test
    void register_ageLessThanMin_notOk() {
        User sam = new User();
        sam.setAge(UNDER_MIN_USERS_AGE);
        sam.setLogin("Samuel");
        sam.setPassword("SammySam");
        assertThrows(RuntimeException.class, () -> registrationService.register(sam));
    }

    @Test
    void register_ageMoreThanMax_notOk() {
        User sam = new User();
        sam.setAge(OVER_MAX_USERS_AGE);
        sam.setLogin("S");
        sam.setPassword("SammySam");
        assertThrows(RuntimeException.class, () -> registrationService.register(sam));
    }

    @Test
    void register_nullInput_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_sameLogin_notOk() {
        User mary = new User();
        mary.setAge(CORRECT_USERS_AGE);
        mary.setLogin("Arny");
        mary.setPassword("Mary*Love37");
        assertThrows(RuntimeException.class, () -> registrationService.register(mary));
    }

    @Test
    void register_passwordLessThenMin_notOk() {
        User rick = new User();
        rick.setAge(CORRECT_USERS_AGE);
        rick.setLogin("RichyRick");
        rick.setPassword("rick");
        assertThrows(RuntimeException.class, () -> registrationService.register(rick));
    }

    @Test
    void register_userWithoutLogin_notOk() {
        User rick = new User();
        rick.setAge(CORRECT_USERS_AGE);
        rick.setPassword("rickyTicky");
        assertThrows(RuntimeException.class, () -> registrationService.register(rick));
    }

    @Test
    void register_userWithoutAnyData_notOk() {
        User user = new User();
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userWithoutPassword_notOk() {
        User rick = new User();
        rick.setAge(CORRECT_USERS_AGE);
        rick.setLogin("RichyRick");
        assertThrows(RuntimeException.class, () -> registrationService.register(rick));
    }

    @Test
    void register_firstIncorrectUsersAge_notOk() {
        User rick = new User();
        rick.setAge(FIRST_INCORRECT_AGE);
        rick.setLogin("RichyRick");
        rick.setPassword("rickyTicky");
        assertThrows(RuntimeException.class, () -> registrationService.register(rick));
    }

    @Test
    void register_secondIncorrectUsersAge_notOk() {
        User rick = new User();
        rick.setAge(SECOND_INCORRECT_AGE);
        rick.setLogin("RichyRick");
        rick.setPassword("rickyTicky");
        assertThrows(RuntimeException.class, () -> registrationService.register(rick));
    }

    @Test
    void register_userWithoutAge_notOk() {
        User rick = new User();
        rick.setLogin("Richy");
        rick.setPassword("rickyTicky");
        assertThrows(RuntimeException.class, () -> registrationService.register(rick));
    }
}

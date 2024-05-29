package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.InvalidDataException;
import core.basesyntax.service.RegistrationServiceImpl;
import java.util.Objects;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final int DEFAULT_LENGTH_FOR_LOGIN = 6;
    private static final int DEFAULT_LENGTH_FOR_PASSWORD = 6;
    private static final int AGE_THRESHOLD = 18;
    private static final long ZERO = 0;
    private StorageDaoImpl storageDao;
    private RegistrationServiceImpl registrationService;
    private User validUser1;
    private User validUser2;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_userNull_notOk() {
        assertThrows(InvalidDataException.class, () -> registrationService.register(null));
    }

    @Test
    void check_userExistInDataBase_notOk() {
        validUser1 = new User(35624576L, "MadGeek", "qwer123", 25);
        validUser2 = new User(66357777L, "MadGeek", "vndjf55", 19);
        storageDao.add(validUser1);
        assertThrows(InvalidDataException.class, () -> registrationService.register(validUser2));
    }

    @Test
    void check_addedUserIsTheSame_ok() {
        validUser1 = new User(35624576L, "MadGeek", "qwer123", 25);
        User returnedUser = registrationService.register(validUser1);
        assertEquals(validUser1, returnedUser);
    }

    @Test
    void register_userLoginNull_notOk() {
        validUser1 = new User(35624576L, null, "qwer123", 25);
        assertThrows(InvalidDataException.class, () -> registrationService.register(validUser1));
    }

    @Test
    void register_userPasswordNull_notOk() {
        validUser1 = new User(35624576L, "MadGeek", null, 25);
        assertThrows(InvalidDataException.class, () -> registrationService.register(validUser1));
    }

    @Test
    void register_userIdNull_notOk() {
        validUser1 = new User(null, "MadGeek", "qwer123", 25);
        assertThrows(InvalidDataException.class, () -> registrationService.register(validUser1));
    }

    @Test
    void register_userAgeNull_notOk() {
        validUser1 = new User(35624576L, "MadGeek", "qwer123", null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(validUser1));
    }

    @Test
    void check_userPasswordEqualsLengthSix_ok() {
        validUser1 = new User(35624576L, "MadGeek", "123456", 25);
        validUser2 = new User(66354214L, "FlyBoys", "111111", 21);
        User current1 = registrationService.register(validUser1);
        User current2 = registrationService.register(validUser2);
        final boolean actual = current1.getPassword().length()
                == DEFAULT_LENGTH_FOR_PASSWORD
                && current2.getPassword().length() == DEFAULT_LENGTH_FOR_PASSWORD;
        assertEquals(current1, validUser1);
        assertEquals(current2, validUser2);
        assertTrue(actual);
    }

    @Test
    void check_userPasswordMoreThanSix_ok() {
        validUser1 = new User(35624576L, "MadGeek", "1234567", 25);
        User current1 = registrationService.register(validUser1);
        validUser2 = new User(66354214L, "FlyBoys", "111111635421", 21);
        User current2 = registrationService.register(validUser2);
        final boolean actual = current1.getPassword().length()
                > DEFAULT_LENGTH_FOR_PASSWORD
                && current2.getPassword().length() > DEFAULT_LENGTH_FOR_PASSWORD;
        assertEquals(current1, validUser1);
        assertEquals(current2, validUser2);
        assertTrue(actual);
    }

    @Test
    void check_userPasswordLessThanSix_notOk() {
        validUser1 = new User(35624576L, "MadGeek", "1111", 25);
        validUser2 = new User(19358745L, "FlyBoys", "0", 21);
        assertThrows(InvalidDataException.class, () -> registrationService.register(validUser1));
        assertThrows(InvalidDataException.class, () -> registrationService.register(validUser2));
    }

    @Test
    void check_userAgeLessThanEighteen_notOk() {
        validUser1 = new User(35624576L, "MadGeek", "11sds11", 0);
        validUser2 = new User(19358745L, "FlyBoys", "0dsdfsdf", 17);
        assertThrows(InvalidDataException.class, () -> registrationService.register(validUser1));
        assertThrows(InvalidDataException.class, () -> registrationService.register(validUser2));
    }

    @Test
    void check_userAgeIsNegative_notOk() {
        validUser1 = new User(35624576L, "MadGeek", "11sds11", -1);
        validUser2 = new User(19358745L, "FlyBoys", "0dsdfsdf", -999);
        assertThrows(InvalidDataException.class, () -> registrationService.register(validUser1));
        assertThrows(InvalidDataException.class, () -> registrationService.register(validUser2));
    }

    @Test
    void check_userAgeIsEighteen_ok() {
        validUser1 = new User(35624576L, "MadGeek", "11sds11", 18);
        validUser2 = new User(19358745L, "FlyBoys", "0dsdfsdf", 18);
        User currentUser1 = registrationService.register(validUser1);
        User currentUser2 = registrationService.register(validUser2);
        final boolean actual = Objects.equals(currentUser1.getAge(), validUser1.getAge())
                && Objects.equals(currentUser2.getAge(), validUser2.getAge());
        assertEquals(currentUser1, validUser1);
        assertEquals(currentUser2, validUser2);
        assertTrue(actual);
    }

    @Test
    void check_userAgeIsMoreThanEighteen_ok() {
        validUser1 = new User(35624576L, "MadGeek", "11sds11", 122);
        validUser2 = new User(19358745L, "FlyBoys", "0dsdfsdf", 28);
        User currentUser1 = registrationService.register(validUser1);
        User currentUser2 = registrationService.register(validUser2);
        final boolean actual = currentUser1.getAge() > AGE_THRESHOLD
                && currentUser2.getAge() > AGE_THRESHOLD;
        assertEquals(currentUser1, validUser1);
        assertEquals(currentUser2, validUser2);
        assertTrue(actual);
    }

    @Test
    void check_userLoginIsLessThanSix_notOk() {
        validUser1 = new User(35624576L, "Mad", "11sds11", 22);
        validUser2 = new User(19358745L, "F", "0dsdfsdf", 28);
        assertThrows(InvalidDataException.class, () -> registrationService.register(validUser1));
        assertThrows(InvalidDataException.class, () -> registrationService.register(validUser2));
    }

    @Test
    void check_userLoginIsSixLength_ok() {
        validUser1 = new User(35624576L, "Mad123", "11sds11", 22);
        validUser2 = new User(19358745L, "F12345", "0dsdfsdf", 28);
        User currentUser1 = registrationService.register(validUser1);
        User currentUser2 = registrationService.register(validUser2);
        boolean actual = currentUser1.getLogin().length() == DEFAULT_LENGTH_FOR_LOGIN
                && currentUser2.getLogin().length() == DEFAULT_LENGTH_FOR_LOGIN;
        assertEquals(currentUser1, validUser1);
        assertEquals(currentUser2, validUser2);
        assertTrue(actual);
    }

    @Test
    void check_userLoginIsMoreThanSixLength_ok() {
        validUser1 = new User(35624576L, "MadGeek1", "11sds11", 122);
        validUser2 = new User(19358745L, "FlyBoys123456", "0dsdfsdf", 28);
        User currentUser1 = registrationService.register(validUser1);
        User currentUser2 = registrationService.register(validUser2);
        final boolean actual = currentUser1.getLogin().length()
                > DEFAULT_LENGTH_FOR_LOGIN
                && currentUser2.getLogin().length() > DEFAULT_LENGTH_FOR_LOGIN;
        assertEquals(currentUser1, validUser1);
        assertEquals(currentUser2, validUser2);
        assertTrue(actual);
    }

    @Test
    void check_idIsNegative_notOk() {
        validUser1 = new User(-35624576L, "MadGeek1", "11sds11", 122);
        validUser2 = new User(-19358745L, "FlyBoys123456", "0dsdfsdf", 28);
        assertThrows(InvalidDataException.class, () -> registrationService.register(validUser1));
        assertThrows(InvalidDataException.class, () -> registrationService.register(validUser2));
    }

    @Test
    void check_idEqualsZero_notOk() {
        validUser1 = new User(ZERO, "MadGeek1", "11sds11", 122);
        assertThrows(InvalidDataException.class, () -> registrationService.register(validUser1));
    }
}

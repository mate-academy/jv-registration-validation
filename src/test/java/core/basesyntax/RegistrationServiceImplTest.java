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
    private static final int DEFAULT_LENGTH_FOR_LOGIN_AND_PASSWORD = 6;
    private static final int EIGHTEEN_YEARS_OLD = 18;
    private static final long ZERO = 0;
    private StorageDaoImpl storageDao;
    private RegistrationServiceImpl registrationService;
    private User user1;
    private User user2;
    private User user3;
    private User user4;
    private User user5;

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
        user1 = null;
        assertThrows(InvalidDataException.class, () -> registrationService.register(user1));
    }

    @Test
    void check_userExistInDataBase_notOk() {
        user1 = new User(35624576L, "MadGeek", "qwer123", 25);
        user2 = new User(66357777L, "MadGeek", "vndjf55", 19);
        storageDao.add(user1);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user2));

    }

    @Test
    void check_addedUserIsTheSame_Ok() {
        user1 = new User(35624576L, "MadGeek", "qwer123", 25);
        User returnedUser = registrationService.register(user1);
        assertEquals(user1, returnedUser);
    }

    @Test
    void register_userLoginNull_notOk() {
        user1 = new User(35624576L, null, "qwer123", 25);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user1));
    }

    @Test
    void register_userPasswordNull_notOk() {
        user1 = new User(35624576L, "MadGeek", null, 25);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user1));
    }

    @Test
    void register_userIdNull_notOk() {
        user1 = new User(null, "MadGeek", "qwer123", 25);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user1));
    }

    @Test
    void register_userAgeNull_notOk() {
        user1 = new User(35624576L, "MadGeek", "qwer123", null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user1));
    }

    @Test
    void check_userPasswordEqualsLengthSix_Ok() {
        user1 = new User(35624576L, "MadGeek", "123456", 25);
        user2 = new User(66354214L, "FlyBoys", "111111", 21);
        User current1 = registrationService.register(user1);
        User current2 = registrationService.register(user2);
        final boolean actual = current1.getPassword().length()
                == DEFAULT_LENGTH_FOR_LOGIN_AND_PASSWORD
                && current2.getPassword().length() == DEFAULT_LENGTH_FOR_LOGIN_AND_PASSWORD;
        assertEquals(current1, user1);
        assertEquals(current2, user2);
        assertTrue(actual);
    }

    @Test
    void check_userPasswordMoreThanSix_Ok() {
        user1 = new User(35624576L, "MadGeek", "1234567", 25);
        User current1 = registrationService.register(user1);
        user2 = new User(66354214L, "FlyBoys", "111111635421", 21);
        User current2 = registrationService.register(user2);
        user3 = new User(66354214L, "FlyBoy", "fjskfjowrfjd", 21);
        User current3 = registrationService.register(user3);
        final boolean actual = current1.getPassword().length()
                > DEFAULT_LENGTH_FOR_LOGIN_AND_PASSWORD
                && current2.getPassword().length() > DEFAULT_LENGTH_FOR_LOGIN_AND_PASSWORD
                && current3.getPassword().length() > DEFAULT_LENGTH_FOR_LOGIN_AND_PASSWORD;
        assertEquals(current1, user1);
        assertEquals(current2, user2);
        assertEquals(current3, user3);
        assertTrue(actual);
    }

    @Test
    void check_userPasswordLessThanSix_notOk() {
        user1 = new User(35624576L, "MadGeek", "1111", 25);
        user2 = new User(19358745L, "FlyBoys", "0", 21);
        user3 = new User(66354214L, "MilkyWilki", "abc", 28);
        user4 = new User(33576954L, "Pokemon", "qqqww", 35);
        user5 = new User(79865341L, "AngryDad", "//", 42);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user1));
        assertThrows(InvalidDataException.class, () -> registrationService.register(user2));
        assertThrows(InvalidDataException.class, () -> registrationService.register(user3));
        assertThrows(InvalidDataException.class, () -> registrationService.register(user4));
        assertThrows(InvalidDataException.class, () -> registrationService.register(user5));

    }

    @Test
    void check_userAgeLessThanEighteen_notOk() {
        user1 = new User(35624576L, "MadGeek", "11sds11", 0);
        user2 = new User(19358745L, "FlyBoys", "0dsdfsdf", 17);
        user3 = new User(66354214L, "MilkyWilki", "absssc", 4);
        user4 = new User(33576954L, "Pokemon", "qqqddww", 15);
        user5 = new User(79865341L, "AngryDad", "//vdfffd", 1);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user1));
        assertThrows(InvalidDataException.class, () -> registrationService.register(user2));
        assertThrows(InvalidDataException.class, () -> registrationService.register(user3));
        assertThrows(InvalidDataException.class, () -> registrationService.register(user4));
        assertThrows(InvalidDataException.class, () -> registrationService.register(user5));
    }

    @Test
    void check_userAgeIsNegative_notOk() {
        user1 = new User(35624576L, "MadGeek", "11sds11", -1);
        user2 = new User(19358745L, "FlyBoys", "0dsdfsdf", -999);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user1));
        assertThrows(InvalidDataException.class, () -> registrationService.register(user2));
    }

    @Test
    void check_userAgeIsEighteen_Ok() {
        user1 = new User(35624576L, "MadGeek", "11sds11", 18);
        user2 = new User(19358745L, "FlyBoys", "0dsdfsdf", 18);
        User currentUser1 = registrationService.register(user1);
        User currentUser2 = registrationService.register(user2);
        final boolean actual = Objects.equals(currentUser1.getAge(), user1.getAge())
                && Objects.equals(currentUser2.getAge(), user2.getAge());
        assertEquals(currentUser1, user1);
        assertEquals(currentUser2, user2);
        assertTrue(actual);

    }

    @Test
    void check_userAgeIsMoreThanEighteen_Ok() {
        user1 = new User(35624576L, "MadGeek", "11sds11", 122);
        user2 = new User(19358745L, "FlyBoys", "0dsdfsdf", 28);
        user3 = new User(66354214L, "MilkyWilki", "absssc", 45);
        user4 = new User(33576954L, "Pokemon", "qqqddww", 19);
        user5 = new User(79865341L, "AngryDad", "//vdfffd", 60);
        User currentUser1 = registrationService.register(user1);
        User currentUser2 = registrationService.register(user2);
        User currentUser3 = registrationService.register(user3);
        User currentUser4 = registrationService.register(user4);
        User currentUser5 = registrationService.register(user5);
        final boolean actual = currentUser1.getAge() > EIGHTEEN_YEARS_OLD
                && currentUser2.getAge() > EIGHTEEN_YEARS_OLD
                && currentUser3.getAge() > EIGHTEEN_YEARS_OLD
                && currentUser4.getAge() > EIGHTEEN_YEARS_OLD
                && currentUser5.getAge() > EIGHTEEN_YEARS_OLD;
        assertEquals(currentUser1, user1);
        assertEquals(currentUser2, user2);
        assertEquals(currentUser3, user3);
        assertEquals(currentUser4, user4);
        assertEquals(currentUser5, user5);
        assertTrue(actual);
    }

    @Test
    void check_userLoginIsLessThanSix_notOk() {
        user1 = new User(35624576L, "Mad", "11sds11", 22);
        user2 = new User(19358745L, "F", "0dsdfsdf", 28);
        user3 = new User(66354214L, "Milky", "absssc", 45);
        user4 = new User(33576954L, "Po", "qqqddww", 19);
        user5 = new User(79865341L, "Angr", "//vdfffd", 60);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user1));
        assertThrows(InvalidDataException.class, () -> registrationService.register(user2));
        assertThrows(InvalidDataException.class, () -> registrationService.register(user3));
        assertThrows(InvalidDataException.class, () -> registrationService.register(user4));
        assertThrows(InvalidDataException.class, () -> registrationService.register(user5));
    }

    @Test
    void check_userLoginIsSixLength_Ok() {
        user1 = new User(35624576L, "Mad123", "11sds11", 22);
        user2 = new User(19358745L, "F12345", "0dsdfsdf", 28);
        User currentUser1 = registrationService.register(user1);
        User currentUser2 = registrationService.register(user2);
        boolean actual = currentUser1.getLogin().length() == DEFAULT_LENGTH_FOR_LOGIN_AND_PASSWORD
                && currentUser2.getLogin().length() == DEFAULT_LENGTH_FOR_LOGIN_AND_PASSWORD;
        assertEquals(currentUser1, user1);
        assertEquals(currentUser2, user2);
        assertTrue(actual);

    }

    @Test
    void check_userLoginIsMoreThanSixLength_Ok() {
        user1 = new User(35624576L, "MadGeek1", "11sds11", 122);
        user2 = new User(19358745L, "FlyBoys123456", "0dsdfsdf", 28);
        user3 = new User(66354214L, "MilkyWilki5555555", "absssc", 45);
        user4 = new User(33576954L, "12345678910", "qqqddww", 19);
        user5 = new User(79865341L, "AngryD55ad", "//vdfffd", 60);
        User currentUser1 = registrationService.register(user1);
        User currentUser2 = registrationService.register(user2);
        User currentUser3 = registrationService.register(user3);
        User currentUser4 = registrationService.register(user4);
        User currentUser5 = registrationService.register(user5);
        final boolean actual = currentUser1.getLogin().length()
                > DEFAULT_LENGTH_FOR_LOGIN_AND_PASSWORD
                && currentUser2.getLogin().length() > DEFAULT_LENGTH_FOR_LOGIN_AND_PASSWORD
                && currentUser3.getLogin().length() > DEFAULT_LENGTH_FOR_LOGIN_AND_PASSWORD
                && currentUser4.getLogin().length() > DEFAULT_LENGTH_FOR_LOGIN_AND_PASSWORD
                && currentUser5.getLogin().length() > DEFAULT_LENGTH_FOR_LOGIN_AND_PASSWORD;
        assertEquals(currentUser1, user1);
        assertEquals(currentUser2, user2);
        assertEquals(currentUser3, user3);
        assertEquals(currentUser4, user4);
        assertEquals(currentUser5, user5);
        assertTrue(actual);
    }

    @Test
    void check_idIsNegative_notOk() {
        user1 = new User(-35624576L, "MadGeek1", "11sds11", 122);
        user2 = new User(-19358745L, "FlyBoys123456", "0dsdfsdf", 28);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user1));
        assertThrows(InvalidDataException.class, () -> registrationService.register(user2));
    }

    @Test
    void check_idEqualsZero_notOk() {
        user1 = new User(ZERO, "MadGeek1", "11sds11", 122);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user1));
    }
}

package core.basesyntax;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.InvalidDataException;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Feel free to remove this class and create your own.
 */
public class RegistrationServiceImplTest {
    private static final int DEFAULT_LENGTH_FOR_LOGIN_AND_PASSWORD = 6;
    private static final int EIGHTEEN_YEARS_OLD = 18;
    private StorageDaoImpl storageDao;
    private RegistrationServiceImpl registrationService;
    private static final long ZERO = 0;
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

    @Test //1 Перевірка користувача на null
    void register_userNull_notOk() {
        user1 = null;
        assertThrows(InvalidDataException.class, () -> registrationService.register(user1));
    }

    @Test //2 Перевірка користувача з таким же логіном на існування в базі
    void check_userExistInDataBase_notOk() {
        user1 = new User(35624576L, "MadGeek", "qwer123", 25);
        user2 = new User(66357777L, "MadGeek", "vndjf55", 19);
        storageDao.add(user1);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user2));

    }

    @Test //3 Перевірка чи повертається правильний користувач
    void check_addedUserIsTheSame_Ok() {
        user1 = new User(35624576L, "MadGeek", "qwer123", 25);
        User returnedUser = registrationService.register(user1);
        assertEquals(user1, returnedUser);
    }

    @Test //4 Перевірка на null логін.
    void register_userLoginNull_notOk() {
        user1 = new User(35624576L, null, "qwer123", 25);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user1));
    }

    @Test //5 Перевірка на null пароль.
    void register_userPasswordNull_notOk() {
        user1 = new User(35624576L, "MadGeek", null, 25);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user1));
    }

    @Test // 6 Перевірка на null ід.
    void register_userIdNull_notOk() {
        user1 = new User(null, "MadGeek", "qwer123", 25);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user1));
    }

    @Test //7 Перевірка на null вік.
    void register_userAgeNull_notOk() {
        user1 = new User(35624576L, "MadGeek", "qwer123", null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user1));
    }

    @Test //8 Перевірка на пароль який довірнює 6.
    void check_userPasswordEqualsLengthSix_Ok() {
        user1 = new User(35624576L, "MadGeek", "123456", 25);
        user2 = new User(66354214L, "FlyBoys", "111111", 21);
        User current1 = registrationService.register(user1);
        User current2 = registrationService.register(user2);
        boolean actual = current1.getPassword().length() == DEFAULT_LENGTH_FOR_LOGIN_AND_PASSWORD
                && current2.getPassword().length() == DEFAULT_LENGTH_FOR_LOGIN_AND_PASSWORD;
        assertEquals(current1, user1);
        assertEquals(current2, user2);
        assertTrue(actual);
    }

    @Test //9 Перевірка на пароль який більше 6.
    void check_userPasswordMoreThanSix_Ok() {
        user1 = new User(35624576L, "MadGeek", "1234567", 25);
        user2 = new User(66354214L, "FlyBoys", "111111635421", 21);
        user3 = new User(66354214L, "FlyBoy", "fjskfjowrfjd", 21);
        User current1 = registrationService.register(user1);
        User current2 = registrationService.register(user2);
        User current3 = registrationService.register(user3);
        boolean actual = current1.getPassword().length() > DEFAULT_LENGTH_FOR_LOGIN_AND_PASSWORD
                && current2.getPassword().length() > DEFAULT_LENGTH_FOR_LOGIN_AND_PASSWORD
                && current3.getPassword().length() > DEFAULT_LENGTH_FOR_LOGIN_AND_PASSWORD;
        assertEquals(current1, user1);
        assertEquals(current2, user2);
        assertEquals(current3, user3);
        assertTrue(actual);
    }

    @Test //10 Перевірка на пароль який менше 6.
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

    @Test //11. Перевірка на вік менше 18.
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

    @Test //12. Перевірка на негативний вік.
    void check_userAgeIsNegative_notOk() {
        user1 = new User(35624576L, "MadGeek", "11sds11", -1);
        user2 = new User(19358745L, "FlyBoys", "0dsdfsdf", -999);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user1));
        assertThrows(InvalidDataException.class, () -> registrationService.register(user2));
    }

    @Test //13. Перевірка на вік == 18.
    void check_userAgeIsEighteen_Ok() {
        user1 = new User(35624576L, "MadGeek", "11sds11", 18);
        user2 = new User(19358745L, "FlyBoys", "0dsdfsdf", 18);
        User currentUser1 = registrationService.register(user1);
        User currentUser2 = registrationService.register(user2);
        boolean actual = Objects.equals(currentUser1.getAge(), user1.getAge())
                && Objects.equals(currentUser2.getAge(), user2.getAge());
        assertEquals(currentUser1, user1);
        assertEquals(currentUser2, user2);
        assertTrue(true);

    }

    @Test //14. Перевірка на вік > 18;
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
        boolean actual = currentUser1.getAge() > EIGHTEEN_YEARS_OLD
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

    @Test //15Перевірка на логін менше 6 символів.
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

    @Test //16. Перевірка на логін дорівнює 6 символам.
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

    @Test //17 Перевірка на лоіг більше 6 символів.
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
        boolean actual = currentUser1.getLogin().length() > DEFAULT_LENGTH_FOR_LOGIN_AND_PASSWORD
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

    @Test //18Перевірка АЙДІ  негативний
    void check_idIsNegative_notOk() {
        user1 = new User(-35624576L, "MadGeek1", "11sds11", 122);
        user2 = new User(-19358745L, "FlyBoys123456", "0dsdfsdf", 28);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user1));
        assertThrows(InvalidDataException.class, () -> registrationService.register(user2));
    }


    @Test //20 Перевірка Ід на 0
    void check_idEqualsZero_notOk() {
        user1 = new User(ZERO, "MadGeek1", "11sds11", 122);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user1));
    }
}

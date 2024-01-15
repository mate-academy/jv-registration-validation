package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.DataCorrectnessException;
import core.basesyntax.exceptions.LoginDuplicateException;
import core.basesyntax.exceptions.NullUserDataException;
import core.basesyntax.exceptions.NullUserException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final StorageDao storageDao = new StorageDaoImpl();

    @BeforeEach
    void setUp() {
        User user1 = new User();
        user1.setId(1L);
        user1.setLogin("LordFar");
        user1.setPassword("34gsge&ws");
        user1.setAge(23);

        User user2 = new User();
        user2.setId(2L);
        user2.setLogin("Cramliren");
        user2.setPassword("233Fwaefs");
        user2.setAge(23);

        User user3 = new User();
        user3.setId(3L);
        user3.setLogin("Ignesco");
        user3.setPassword("123455fef");
        user3.setAge(23);

        Storage.people.add(user1);
        Storage.people.add(user2);
        Storage.people.add(user3);
    }

    @Test
    void register_correctUser_ok() {

        User actualUserFirst = new User();
        actualUserFirst.setLogin("Valdelkron");
        actualUserFirst.setPassword("233Fwa");
        actualUserFirst.setAge(23);

        registrationService.register(actualUserFirst);
        assertEquals(actualUserFirst, storageDao.get(actualUserFirst.getLogin()));

        User actualUserSecond = new User();
        actualUserSecond.setLogin("Kalibr");
        actualUserSecond.setPassword("564gfass");
        actualUserSecond.setAge(19);

        registrationService.register(actualUserSecond);
        assertEquals(actualUserSecond, storageDao.get(actualUserSecond.getLogin()));

        User actualUserThird = new User();
        actualUserThird.setLogin("Faradei");
        actualUserThird.setPassword("564gfass");
        actualUserThird.setAge(18);

        registrationService.register(actualUserThird);
        assertEquals(actualUserThird, storageDao.get(actualUserThird.getLogin()));

        User actualUserForth = new User();
        actualUserForth.setLogin("Ragnorer");
        actualUserForth.setPassword("564gfass");
        actualUserForth.setAge(21);

        registrationService.register(actualUserForth);
        assertEquals(actualUserForth, storageDao.get(actualUserForth.getLogin()));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(NullUserException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullUserData_notOk() {
        User actualUser1 = new User();
        actualUser1.setLogin(null);
        actualUser1.setPassword("344gs&ws");
        actualUser1.setAge(39);

        assertThrows(NullUserDataException.class, () -> registrationService.register(actualUser1));

        User actualUser2 = new User();
        actualUser2.setLogin("Aster");
        actualUser2.setPassword(null);
        actualUser2.setAge(39);

        assertThrows(NullUserDataException.class, () -> registrationService.register(actualUser2));

        User actualUser4 = new User();
        actualUser4.setLogin("");
        actualUser4.setPassword("344gs&ws");
        actualUser4.setAge(null);

        assertThrows(NullUserDataException.class, () -> registrationService.register(actualUser4));
    }

    @Test
    void register_loginDuplicateSameUsers_notOk() {
        User actualUser1 = new User();
        actualUser1.setLogin("LordFar");
        actualUser1.setPassword("34gsge&ws");
        actualUser1.setAge(23);

        assertThrows(LoginDuplicateException.class,
                () -> registrationService.register(actualUser1));

        User actualUser2 = new User();
        actualUser2.setLogin("Cramliren");
        actualUser2.setPassword("hkl4gs&ws");
        actualUser2.setAge(39);

        assertThrows(LoginDuplicateException.class,
                () -> registrationService.register(actualUser2));

        User actualUser3 = new User();
        actualUser3.setLogin("Ignesco");
        actualUser3.setPassword("hkl4gs&ws");
        actualUser3.setAge(39);

        assertThrows(LoginDuplicateException.class,
                () -> registrationService.register(actualUser3));
    }

    @Test
    void register_loginAllowableSize_notOk() {
        User actualUser1 = new User();
        actualUser1.setLogin("Asa");
        actualUser1.setPassword("344gs&ws");
        actualUser1.setAge(39);

        assertThrows(DataCorrectnessException.class,
                () -> registrationService.register(actualUser1));

        User actualUser2 = new User();
        actualUser2.setLogin("Aster");
        actualUser2.setPassword("344gs&ws");
        actualUser2.setAge(39);

        assertThrows(DataCorrectnessException.class,
                () -> registrationService.register(actualUser2));

        User actualUser3 = new User();
        actualUser3.setLogin("A");
        actualUser3.setPassword("344gs&ws");
        actualUser3.setAge(39);

        assertThrows(DataCorrectnessException.class,
                () -> registrationService.register(actualUser3));

        User actualUser4 = new User();
        actualUser4.setLogin("");
        actualUser4.setPassword("344gs&ws");
        actualUser4.setAge(39);

        assertThrows(DataCorrectnessException.class,
                () -> registrationService.register(actualUser4));
    }

    @Test
    void register_passwordAllowableSize_notOk() {
        User actualUser1 = new User();
        actualUser1.setLogin("Asallre");
        actualUser1.setPassword("344gs");
        actualUser1.setAge(39);

        assertThrows(DataCorrectnessException.class,
                () -> registrationService.register(actualUser1));

        User actualUser2 = new User();
        actualUser2.setLogin("Dsterres");
        actualUser2.setPassword("34");
        actualUser2.setAge(39);

        assertThrows(DataCorrectnessException.class,
                () -> registrationService.register(actualUser2));

        User actualUser3 = new User();
        actualUser3.setLogin("Roparked");
        actualUser3.setPassword("3");
        actualUser3.setAge(39);

        assertThrows(DataCorrectnessException.class,
                () -> registrationService.register(actualUser3));

        User actualUser4 = new User();
        actualUser4.setLogin("Wlrensar");
        actualUser4.setPassword("");
        actualUser4.setAge(39);

        assertThrows(DataCorrectnessException.class,
                () -> registrationService.register(actualUser4));
    }

    @Test
    void register_AllowableAge_notOk() {
        User actualUser1 = new User();
        actualUser1.setLogin("Asallre");
        actualUser1.setPassword("3gd4h8@44gs");
        actualUser1.setAge(17);

        assertThrows(DataCorrectnessException.class,
                () -> registrationService.register(actualUser1));

        User actualUser2 = new User();
        actualUser2.setLogin("Dsterres");
        actualUser2.setPassword("3gd4h8@44gs");
        actualUser2.setAge(4);

        assertThrows(DataCorrectnessException.class,
                () -> registrationService.register(actualUser2));

        User actualUser3 = new User();
        actualUser3.setLogin("Roparked");
        actualUser3.setPassword("3gd4h8@44gs");
        actualUser3.setAge(0);

        assertThrows(DataCorrectnessException.class,
                () -> registrationService.register(actualUser3));

        User actualUser4 = new User();
        actualUser4.setLogin("Wlrensar");
        actualUser4.setPassword("3gd4h8@44gs");
        actualUser4.setAge(-5);

        assertThrows(DataCorrectnessException.class,
                () -> registrationService.register(actualUser4));

    }
}

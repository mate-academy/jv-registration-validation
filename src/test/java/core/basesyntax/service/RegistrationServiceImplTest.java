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
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final List<User> storageUsersList = new ArrayList<>();
    private static final List<User> correctUsersList = new ArrayList<>();
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final StorageDao storageDao = new StorageDaoImpl();

    @BeforeAll
    static void beforeAll() {
        User storageUser1 = new User();
        storageUser1.setId(1L);
        storageUser1.setLogin("LordFar");
        storageUser1.setPassword("34gsge&ws");
        storageUser1.setAge(23);

        User storageUser2 = new User();
        storageUser2.setId(2L);
        storageUser2.setLogin("Cramliren");
        storageUser2.setPassword("233Fwaefs");
        storageUser2.setAge(20);

        User storageUser3 = new User();
        storageUser3.setId(3L);
        storageUser3.setLogin("Ignesco");
        storageUser3.setPassword("123455fef");
        storageUser3.setAge(99);

        storageUsersList.add(storageUser1);
        storageUsersList.add(storageUser2);
        storageUsersList.add(storageUser3);

        User correctUser1 = new User();
        correctUser1.setId(4L);
        correctUser1.setLogin("Dsterr");
        correctUser1.setPassword("34gsge&ws");
        correctUser1.setAge(23);

        User correctUser2 = new User();
        correctUser2.setId(5L);
        correctUser2.setLogin("Roparked");
        correctUser2.setPassword("waefs5");
        correctUser2.setAge(23);

        User correctUser3 = new User();
        correctUser3.setId(6L);
        correctUser3.setLogin("Limilon");
        correctUser3.setPassword("123455fef");
        correctUser3.setAge(18);

        User correctUser4 = new User();
        correctUser4.setId(7L);
        correctUser4.setLogin("Astaros");
        correctUser4.setPassword("f455fef");
        correctUser4.setAge(39);

        correctUsersList.add(correctUser1);
        correctUsersList.add(correctUser2);
        correctUsersList.add(correctUser3);
        correctUsersList.add(correctUser4);
    }

    @BeforeEach
    void setUp() {
        Storage.people.addAll(storageUsersList);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_correctUser_ok() {
        for (User user : correctUsersList) {
            registrationService.register(user);
            assertEquals(user, storageDao.get(user.getLogin()));
        }

    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(NullUserException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullUserData_notOk() {
        User user1 = copeUser(correctUsersList.get(0));
        user1.setLogin(null);
        assertThrows(NullUserDataException.class, () -> registrationService.register(user1));

        User user2 = copeUser(correctUsersList.get(1));
        user2.setPassword(null);
        assertThrows(NullUserDataException.class, () -> registrationService.register(user2));

        User user3 = copeUser(correctUsersList.get(2));
        user3.setAge(null);
        assertThrows(NullUserDataException.class, () -> registrationService.register(user3));
    }

    @Test
    void register_loginDuplicateSameUsers_notOk() {
        User user1 = copeUser(storageUsersList.get(0));
        assertThrows(LoginDuplicateException.class, () -> registrationService.register(user1));

        User user2 = copeUser(storageUsersList.get(1));
        user2.setPassword("1234566wwf4");
        assertThrows(LoginDuplicateException.class, () -> registrationService.register(user2));

        User user3 = copeUser(storageUsersList.get(2));
        user3.setAge(23);
        assertThrows(LoginDuplicateException.class, () -> registrationService.register(user3));
    }

    @Test
    void register_loginWrongSize_notOk() {
        User user1 = copeUser(correctUsersList.get(0));
        user1.setLogin("Aster");
        assertThrows(DataCorrectnessException.class, () -> registrationService.register(user1));

        User user2 = copeUser(correctUsersList.get(1));
        user2.setLogin("Asa");
        assertThrows(DataCorrectnessException.class, () -> registrationService.register(user2));

        User user3 = copeUser(correctUsersList.get(2));
        user3.setLogin("A");
        assertThrows(DataCorrectnessException.class, () -> registrationService.register(user3));

        User user4 = copeUser(correctUsersList.get(3));
        user4.setLogin("");
        assertThrows(DataCorrectnessException.class, () -> registrationService.register(user4));
    }

    @Test
    void register_passwordWrongSize_notOk() {
        User user1 = copeUser(correctUsersList.get(0));
        user1.setPassword("1ster");
        assertThrows(DataCorrectnessException.class, () -> registrationService.register(user1));

        User user2 = copeUser(correctUsersList.get(1));
        user2.setPassword("f23");
        assertThrows(DataCorrectnessException.class, () -> registrationService.register(user2));

        User user3 = copeUser(correctUsersList.get(2));
        user3.setPassword("f");
        assertThrows(DataCorrectnessException.class, () -> registrationService.register(user3));

        User user4 = copeUser(correctUsersList.get(3));
        user4.setPassword("");
        assertThrows(DataCorrectnessException.class, () -> registrationService.register(user4));
    }

    @Test
    void register_underage_notOk() {
        User user1 = copeUser(correctUsersList.get(0));
        user1.setAge(17);
        assertThrows(DataCorrectnessException.class, () -> registrationService.register(user1));

        User user2 = copeUser(correctUsersList.get(1));
        user2.setAge(6);
        assertThrows(DataCorrectnessException.class, () -> registrationService.register(user2));

        User user3 = copeUser(correctUsersList.get(2));
        user3.setAge(0);
        assertThrows(DataCorrectnessException.class, () -> registrationService.register(user3));

        User user4 = copeUser(correctUsersList.get(3));
        user4.setAge(-10);
        assertThrows(DataCorrectnessException.class, () -> registrationService.register(user4));

    }

    private User copeUser(User user) {
        User newUser = new User();
        newUser.setId(user.getId());
        newUser.setLogin(user.getLogin());
        newUser.setPassword(user.getPassword());
        newUser.setAge(user.getAge());
        return newUser;
    }
}

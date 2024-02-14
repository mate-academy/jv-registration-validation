package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StorageDaoImplTest {
    private StorageDao storageDao;

    @BeforeAll
    public void setUp() {
        storageDao = new StorageDaoImpl();
    }

    @ParameterizedTest
    @MethodSource("userProviderForAdding")
    void addUserToStorage_success_ok(List<User> users) {
        for (User user : users) {
            assertEquals(user,storageDao.add(user));
        }
    }

    @ParameterizedTest
    @MethodSource("userProviderForGetting")
    void getUserFromStorage_success_ok(List<User> users) {
        for (User user : users) {
            User actualUser = storageDao.get(user.getLogin());
            assertEquals(user,actualUser);
        }
    }

    private Stream<List<User>> userProviderForAdding() {
        User user1 = new User();
        user1.setAge(18);
        user1.setLogin("Vasiliy");
        user1.setPassword("012345");
        User user2 = new User();
        user2.setAge(56);
        user2.setLogin("Svitlana");
        user2.setPassword("qwerty");
        User user3 = new User();
        user3.setAge(33);
        user3.setLogin("TarasShev");
        user3.setPassword("asdfghDar");
        User user4 = new User();
        user4.setAge(41);
        user4.setLogin("Larisa");
        user4.setPassword("54321042");
        User user5 = new User();
        user5.setAge(18);
        user5.setLogin("Svyatoslav");
        user5.setPassword("ytrewqgfD");
        User user6 = new User();
        user6.setAge(57);
        user6.setLogin("Zinaida");
        user6.setPassword("gfdsah");
        User user7 = new User();
        user7.setAge(35);
        user7.setLogin("Mykola");
        user7.setPassword("banka_ogirkiv");
        User user8 = new User();
        user8.setAge(29);
        user8.setLogin("Hanna777");
        user8.setPassword("poiuytr");
        User user9 = new User();
        user9.setAge(46);
        user9.setLogin("Vanentyn");
        user9.setPassword("11223344");
        User user10 = new User();
        user10.setAge(23);
        user10.setLogin("Anatoliy");
        user10.setPassword("zxcvbn");
        List<User> users = new ArrayList<>(List.of(
                user1, user2, user3, user4, user5, user6, user7, user8, user9, user10));
        return Stream.of(users);
    }

    private Stream<List<User>> userProviderForGetting() {
        User user1 = new User();
        user1.setAge(18);
        user1.setLogin("Vasiliy");
        user1.setPassword("012345");
        User user2 = new User();
        user2.setAge(56);
        user2.setLogin("Svitlana");
        user2.setPassword("qwerty");
        User user3 = new User();
        user3.setAge(33);
        user3.setLogin("TarasShev");
        user3.setPassword("asdfghDar");
        User user4 = new User();
        user4.setAge(41);
        user4.setLogin("Larisa");
        user4.setPassword("54321042");
        User user5 = new User();
        user5.setAge(18);
        user5.setLogin("Svyatoslav");
        user5.setPassword("ytrewqgfD");
        User user6 = new User();
        user6.setAge(57);
        user6.setLogin("Zinaida");
        user6.setPassword("gfdsah");
        User user7 = new User();
        user7.setAge(35);
        user7.setLogin("Mykola");
        user7.setPassword("banka_ogirkiv");
        User user8 = new User();
        user8.setAge(29);
        user8.setLogin("Hanna777");
        user8.setPassword("poiuytr");
        User user9 = new User();
        user9.setAge(46);
        user9.setLogin("Vanentyn");
        user9.setPassword("11223344");
        User user10 = new User();
        user10.setAge(23);
        user10.setLogin("Anatoliy");
        user10.setPassword("zxcvbn");
        List<User> users = new ArrayList<>(List.of(user1, user2,
                user3, user4, user5, user6, user7, user8, user9, user10));
        Storage.people.addAll(users);
        return Stream.of(users);
    }
}

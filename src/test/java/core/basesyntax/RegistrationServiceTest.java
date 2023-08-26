package core.basesyntax;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.InvalidDataException;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    static User user1;
    static User user2;
    static RegistrationServiceImpl registrationServiceTest;
    static StorageDaoImpl storageDao;

    @BeforeEach
    void setUp() {
        user1 = new User();
        user2 = new User();
        registrationServiceTest = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();

        user1.setLogin("happytroya");
        user1.setPassword("litachok");
        user1.setAge(18);
        user2.setLogin("happytroya");
        user2.setPassword("Max");
        user2.setAge(22);
    }

    @Test
    void register_userEqualsNull_notOK() {
        user1 = null;
        Assertions.assertThrows(InvalidDataException.class, ()-> registrationServiceTest.register(user2));
    }

    @Test
    void register_userAlreadyExist_notOK() {
        registrationServiceTest.register(user1);
        Assertions.assertThrows(InvalidDataException.class, ()-> registrationServiceTest.register(user2));
    }

    @Test
    void register_nullPassword_notOK() {
        user1.setPassword(null);
        Assertions.assertThrows(InvalidDataException.class, () -> registrationServiceTest.register(user1));
    }

    @Test
    void register_passwordIsToShort_notOk() {
        user1.setPassword("abs");
        Assertions.assertThrows(InvalidDataException.class, () -> registrationServiceTest.register(user1));
    }

    @Test()
    void register_nullLogin_notOK() {
        user1.setLogin(null);
        Assertions.assertThrows(InvalidDataException.class, () -> registrationServiceTest.register(user1));
    }

    @Test
    void register_loginIsShort_notOK() {
        user1.setLogin("abs");
        Assertions.assertThrows(InvalidDataException.class, () -> registrationServiceTest.register(user1));
    }

    @Test
    void register_nullAge_notOK() {
        user1.setAge(null);
        Assertions.assertThrows(InvalidDataException.class, () -> registrationServiceTest.register(user1));
    }

    @Test
    void register_ageIsLessThanMin_Ok() {
        user1.setAge(17);
        Assertions.assertThrows(InvalidDataException.class, () -> registrationServiceTest.register(user1));
    }
}

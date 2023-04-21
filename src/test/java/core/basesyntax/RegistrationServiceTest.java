package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static final String VALID_STRING = "string";
    private static final String ANOTHER_VALID_STRING = "another";
    private static final String INVALID_STRING_WITH_LENGTH_ZERO = "";
    private static final String INVALID_STRING_WITH_LENGTH_THREE = "qwe";
    private static final String INVALID_STRING_WITH_LENGTH_FIVE = "asdfg";
    private static final int VALID_AGE = 18;
    private static final int ANOTHER_VALID_AGE = 22;
    private static final int INVALID_AGE_SIX = 6;
    private static final int INVALID_AGE_NINE = 9;
    private static final int INVALID_AGE_SIXTEEN = 16;

    private static StorageDao storage;
    private static RegistrationService registrationService;
    private static User user;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        storage = new StorageDaoImpl();
        user = new User();
        user.setLogin(VALID_STRING);
        user.setPassword(VALID_STRING);
        user.setAge(VALID_AGE);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginLessThanSix_notOk() {
        user.setLogin(INVALID_STRING_WITH_LENGTH_ZERO);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setLogin(INVALID_STRING_WITH_LENGTH_THREE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setLogin(INVALID_STRING_WITH_LENGTH_FIVE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordLessThanSix_notOk() {
        user.setPassword(INVALID_STRING_WITH_LENGTH_ZERO);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setPassword(INVALID_STRING_WITH_LENGTH_THREE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setPassword(INVALID_STRING_WITH_LENGTH_FIVE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageLessThanEighteen_notOk() {
        user.setAge(INVALID_AGE_SIX);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setAge(INVALID_AGE_NINE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setAge(INVALID_AGE_SIXTEEN);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_theSameLoginExists_notOk() {
        User userWithTheSameLogin = user;
        storage.add(userWithTheSameLogin);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithTheSameLogin));
    }

    @Test
    void register_validUserData_ok() {
        User expectedUser = new User();
        expectedUser.setLogin(ANOTHER_VALID_STRING);
        expectedUser.setPassword(ANOTHER_VALID_STRING);
        expectedUser.setAge(ANOTHER_VALID_AGE);
        User actualUser = registrationService.register(expectedUser);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void equals_null_notOk() {
        assertNotEquals(user, null);
    }

    @Test
    void equals_similarUser_ok() {
        User anotherUser = new User();
        anotherUser.setLogin(user.getLogin());
        anotherUser.setPassword(user.getPassword());
        anotherUser.setAge(user.getAge());
        boolean isEquals = Objects.equals(user, anotherUser);
        assertTrue(isEquals);
    }

    @Test
    void hashCode_validUserData_ok() {
        int expectedHashCode = user.hashCode();
        assertEquals(expectedHashCode, user.hashCode());
    }
}

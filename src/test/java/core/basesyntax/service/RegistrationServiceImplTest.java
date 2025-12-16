package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService service;
    private User validUser1;
    private User validUser2;
    private User nullLoginUser;
    private User nullPasswordUser;
    private User nullAgeUser;
    private User negativeAgeUser;
    private User edgeLoginLengthUser;
    private User edgePasswordLengthUser;
    private User edgeAgeUser;
    private User shortLoginUser;
    private User shortPasswordUser;
    private User youngUser;
    private final StorageDao storageDao = new StorageDaoImpl();

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        service = new RegistrationServiceImpl();
        validUser1 = new User();
        validUser1.setId(1L);
        validUser1.setLogin("kastashenkova");
        validUser1.setPassword("123456");
        validUser1.setAge(18);

        validUser2 = new User();
        validUser2.setId(2L);
        validUser2.setLogin("k.astashenkova123");
        validUser2.setPassword("secure");
        validUser2.setAge(40);

        nullLoginUser = new User();
        nullLoginUser.setId(3L);
        nullLoginUser.setLogin(null);
        nullLoginUser.setPassword("validPass");
        nullLoginUser.setAge(25);

        nullPasswordUser = new User();
        nullPasswordUser.setId(4L);
        nullPasswordUser.setLogin("validLogin");
        nullPasswordUser.setPassword(null);
        nullPasswordUser.setAge(25);

        nullAgeUser = new User();
        nullAgeUser.setId(5L);
        nullAgeUser.setLogin("validLogin2");
        nullAgeUser.setPassword("validPass2");
        nullAgeUser.setAge(null);

        negativeAgeUser = new User();
        negativeAgeUser.setId(6L);
        negativeAgeUser.setLogin("negAgeUser");
        negativeAgeUser.setPassword("validPass3");
        negativeAgeUser.setAge(-5);

        edgeLoginLengthUser = new User();
        edgeLoginLengthUser.setId(7L);
        edgeLoginLengthUser.setLogin("12345");
        edgeLoginLengthUser.setPassword("validPass4");
        edgeLoginLengthUser.setAge(25);

        edgePasswordLengthUser = new User();
        edgePasswordLengthUser.setId(8L);
        edgePasswordLengthUser.setLogin("validLogin5");
        edgePasswordLengthUser.setPassword("12345");
        edgePasswordLengthUser.setAge(25);

        edgeAgeUser = new User();
        edgeAgeUser.setId(9L);
        edgeAgeUser.setLogin("validLogin6");
        edgeAgeUser.setPassword("validPass6");
        edgeAgeUser.setAge(17);

        shortLoginUser = new User();
        shortLoginUser.setId(10L);
        shortLoginUser.setLogin("f");
        shortLoginUser.setPassword("sdffjcdjvdjfn");
        shortLoginUser.setAge(22);

        shortPasswordUser = new User();
        shortPasswordUser.setId(11L);
        shortPasswordUser.setLogin("sdffjcdjvdjfn");
        shortPasswordUser.setPassword("a");
        shortPasswordUser.setAge(22);

        youngUser = new User();
        youngUser.setId(12L);
        youngUser.setLogin("ivanenko");
        youngUser.setPassword("password1");
        youngUser.setAge(10);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class,
                () -> service.register(null));
    }

    @Test
    void register_nullLoginUser_notOk() {
        assertThrows(RegistrationException.class,
                () -> service.register(nullLoginUser));
    }

    @Test
    void register_nullPasswordUser_notOk() {
        assertThrows(RegistrationException.class,
                () -> service.register(nullPasswordUser));
    }

    @Test
    void register_nullAgeUser_notOk() {
        assertThrows(RegistrationException.class,
                () -> service.register(nullAgeUser));
    }

    @Test
    void register_negativeAgeUser_notOk() {
        assertThrows(RegistrationException.class,
                () -> service.register(negativeAgeUser));
    }

    @Test
    void register_edgeLoginLengthUser_notOk() {
        assertThrows(RegistrationException.class,
                () -> service.register(edgeLoginLengthUser));
    }

    @Test
    void register_edgePasswordLengthUser_notOk() {
        assertThrows(RegistrationException.class,
                () -> service.register(edgePasswordLengthUser));
    }

    @Test
    void register_edgeAgeUser_notOk() {
        assertThrows(RegistrationException.class,
                () -> service.register(edgeAgeUser));
    }

    @Test
    void register_existingUser_notOk() {
        storageDao.add(validUser1);
        assertThrows(RegistrationException.class,
                () -> service.register(validUser1));
    }

    @Test
    void register_shortLogin_notOk() {
        assertThrows(RegistrationException.class,
                () -> service.register(shortLoginUser));
    }

    @Test
    void register_shortPassword_notOk() {
        assertThrows(RegistrationException.class,
                () -> service.register(shortPasswordUser));
    }

    @Test
    void register_underAge_notOk() {
        assertThrows(RegistrationException.class,
                () -> service.register(youngUser));
    }

    @Test
    void register_normalUser_Ok() {
        User registeredUser = service.register(validUser2);
        assertNotNull(registeredUser);
        assertNotNull(storageDao.get(validUser2.getLogin()));
        assertEquals(validUser2, registeredUser);
    }
}

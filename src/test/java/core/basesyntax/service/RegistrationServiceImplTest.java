package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();
    private StorageDao storageDao = new StorageDaoImpl();
    private User jac;
    private User serhii;
    private User kat;
    private User daniel;

    @BeforeEach
    void setUp() {
        jac = new User();
        jac.setLogin("Jac");
        jac.setAge(19);
        jac.setPassword("1245787");
        serhii = new User();
        serhii.setLogin("serhii");
        serhii.setAge(17);
        serhii.setPassword("1245787");
        kat = new User();
        kat.setLogin("Kat");
        kat.setAge(18);
        kat.setPassword("1245787");
        daniel = new User();
        daniel.setLogin("Daniel");
        daniel.setAge(22);
        daniel.setPassword("1245787");
    }

    @Test
    void register_nullAge_notOk() {
        jac.setAge(null);
        RegistrationException thrown = assertThrows(RegistrationException.class, () -> {
            registrationService.register(jac);
        });
        assertEquals("Login can't be null", thrown.getMessage());
    }

    @Test
    void register_18Age_itsOk() {
        boolean actual = registrationService.register(kat).equals(kat);
        assertTrue(actual);
    }

    @Test
    void register_moreAge_itsOk() {
        boolean actual = registrationService.register(jac).equals(jac);
        assertTrue(actual);
    }

    @Test
    void register_minAge_itsOk() {
        RegistrationException thrown = assertThrows(RegistrationException.class, () -> {
            registrationService.register(serhii);
        });
        assertEquals("Login cannot be less than 18", thrown.getMessage());
    }

    @Test
    void register_negativeAge_itsOk() {
        jac.setAge(-1);
        RegistrationException thrown = assertThrows(RegistrationException.class, () -> {
            registrationService.register(jac);
        });
        assertEquals("Login cannot be less than 18", thrown.getMessage());
    }

    @Test
    void register_nullLogin_notOk() {
        jac.setLogin(null);
        RegistrationException thrown = assertThrows(RegistrationException.class, () -> {
            registrationService.register(jac);
        });
        assertEquals("Login can't be null", thrown.getMessage());
    }

    @Test
    void register_repeatedLogin_notOk() {
        serhii.setAge(18);
        storageDao.add(serhii);
        RegistrationException thrown = assertThrows(RegistrationException.class, () -> {
            registrationService.register(serhii);
        });
        assertEquals("there is user with such login in the Storage", thrown.getMessage());
    }

    @Test
    void register_nullPassword_notOk() {
        daniel.setPassword(null);
        RegistrationException thrown = assertThrows(RegistrationException.class, () -> {
            registrationService.register(daniel);
        });
        assertEquals("Password can't be null", thrown.getMessage());
    }

    @Test
    void register_minPassword_notOk() {
        daniel.setPassword("1425");
        RegistrationException thrown = assertThrows(RegistrationException.class, () -> {
            registrationService.register(daniel);
        });
        assertEquals("Password must be at least six characters long", thrown.getMessage());
    }
}

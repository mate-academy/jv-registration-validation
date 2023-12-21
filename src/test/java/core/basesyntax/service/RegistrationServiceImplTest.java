package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.*;

class RegistrationServiceImplTest {
  private static StorageDao storageDao;
  private static RegistrationService registrationService;
  @BeforeAll
  static void initDB() {
    storageDao = new StorageDaoImpl();
    storageDao.add(new User("User01", "password01", 18));
    storageDao.add(new User("User02", "password02", 19));
    storageDao.add(new User("User03", "password03", 20));
    registrationService = new RegistrationServiceImpl();
  }
  @AfterAll
  static void disconnectFromDB() {
    storageDao = null;
    registrationService = null;
  }

  @Test
  void isNullLoginValid() {

  }
}
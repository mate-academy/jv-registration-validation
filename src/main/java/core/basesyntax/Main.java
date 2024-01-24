package core.basesyntax;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;


public class Main {
    public static void main(String[] args) {

        StorageDao storageDao = new StorageDaoImpl();
        RegistrationService registrationService =
                new RegistrationServiceImpl(storageDao);

        User user = new User();
        user.setLogin("user123");
        user.setPassword("user123");
        user.setAge(20);

        try {
            registrationService.register(user);
            System.out.println("new user is successfully registered");
        } catch (RegistrationException e) {
            throw new RuntimeException("registration failed for new user", e);
        }


    }
}

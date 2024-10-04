package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Feel free to remove this class and create your own.
 */
public class RegistrationServiceTests {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;

    @BeforeAll
    public static void set_up() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @Test
    public void register_validUser_success() {
        User newUser = new User();
        newUser.setLogin("Bob679");
        newUser.setAge(18);
        newUser.setPassword("0987654321");
        registrationService.register(newUser);
        User expectedUser = storageDao.get(newUser.getLogin());
        assertEquals(expectedUser.getLogin(), newUser.getLogin());
        assertEquals(expectedUser.getPassword(), newUser.getPassword());
        assertEquals(expectedUser.getAge(), newUser.getAge());
        assertEquals(storageDao.size(), 1);
    }

    @Test
    public void register_incorrectLogin_throwsException() {
        User newUser = new User();
        newUser.setLogin("Mary");
        newUser.setAge(18);
        newUser.setPassword("666666666");
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(newUser);
        });
        assertEquals("The length of login is less than 6 characters", exception.getMessage());
    }

    @Test
    public void register_existingLogin_throwsException() {
        User newUser = new User();
        newUser.setLogin("Bob679");
        newUser.setAge(22);
        newUser.setPassword("1230988686");
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(newUser);
        });
        assertEquals("The user with the same login already exists", exception.getMessage());
    }

    @Test
    public void register_nullLogin_throwsException() {
        User newUser = new User();
        newUser.setLogin(null);
        newUser.setAge(35);
        newUser.setPassword("853895378");
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(newUser);
        });
        assertEquals("The login cannot be null", exception.getMessage());
    }

    @Test
    public void register_emptyLogin_throwsException() {
        User newUser = new User();
        newUser.setLogin("");
        newUser.setAge(35);
        newUser.setPassword("59494866489640");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    public void register_incorrectPassword_throwsException() {
        User newUser = new User();
        newUser.setLogin("Alice12");
        newUser.setAge(44);
        newUser.setPassword("12345");
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(newUser);
        });
        assertEquals("The length of password is less than 6 characters", exception.getMessage());
    }

    @Test
    public void register_nullPassword_throwsException() {
        User newUser = new User();
        newUser.setLogin("Olha906784");
        newUser.setAge(35);
        newUser.setPassword(null);
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(newUser);
        });
        assertEquals("The password cannot be null", exception.getMessage());
    }

    @Test
    public void register_emptyPassword_throwsException() {
        User newUser = new User();
        newUser.setLogin("Anton69504");
        newUser.setAge(35);
        newUser.setPassword("");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    public void register_incorrectAge_throwsException() {
        User newUser = new User();
        newUser.setLogin("Mary67890");
        newUser.setAge(17);
        newUser.setPassword("098765543211111");
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(newUser);
        });
        assertEquals("The age is less than 18 years", exception.getMessage());
    }

    @Test
    public void register_negativeAge_throwsException() {
        User newUser = new User();
        newUser.setLogin("Mary67890");
        newUser.setAge(-10);
        newUser.setPassword("098765543211111");
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(newUser);
        });
        assertEquals("The age is less than 18 years", exception.getMessage());
    }
}

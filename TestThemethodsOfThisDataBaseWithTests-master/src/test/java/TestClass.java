import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestClass {



    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService = new UserService();


    @Test
    public void testPasWordHasher(){
        String hashedpassWord = userService.scramblePassword("test");

        Assert.assertEquals("uftu", hashedpassWord);

    }

    @Test
    public  void testLogin(){
        String hashedPassWord = userService.scramblePassword("test");
        when(userRepository.findOneById("test")).thenReturn(new User("test", hashedPassWord));

        User user = userService.login("test", "test");

        //Don't do this because you won't be able to overwrite equals methods in all classes in the real world
        //Assert.assertEquals(new User("test", "test"), user);
        Assert.assertEquals("test", user.getUserName());
        Assert.assertEquals("uftu", user.getPassWordHashed());

    }

    @Test
    public void testBadLogin(){
        String hashedPassWord = userService.scramblePassword("test");
        when(userRepository.findOneById("test")).thenReturn(new User("test", hashedPassWord));
        User user = userService.login("test", "badPassword");
        Assert.assertNull(user);

    }

    @Test
    public void testUserCreator(){
        String hashedPassWord = userService.scramblePassword("test");

        userRepository.createOne(new User("test", "test"));
        verify(userRepository,times(1)).createOne(any(User.class));
        when(userRepository.findOneById("test")).thenReturn(new User("test", hashedPassWord));

        User user = userService.createUserWithHasher("test", "test");
        Assert.assertEquals("test", user.getUserName());
        Assert.assertEquals("uftu", user.getPassWordHashed());

    }


}

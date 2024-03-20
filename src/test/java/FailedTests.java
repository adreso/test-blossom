import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class FailedTests {


    @Test
    public void testFail() {
        System.out.println("This test should fail");
        assertEquals(0, 1);
    }
}

import implementations.DefaultConstructorImpl;
import implementations.ManyInjectConstructorsImpl;
import implementations.NoInjectAndDefaultConstructorImpl;
import implementations.SingleInjectConstructorImpl;
import interfaces.DefaultConstructorIntf;
import interfaces.ManyInjectConstructorsIntf;
import interfaces.NoInjectAndDefaultConstructorIntf;
import interfaces.SingleInjectConstructorIntf;
import org.example.container.Injector;
import org.example.container.implementations.InjectorImpl;
import org.example.exceptions.BindingNotFoundException;
import org.example.exceptions.ConstructorNotFoundException;
import org.example.exceptions.TooManyConstructorsException;
import org.junit.Assert;
import org.junit.Test;

public class ExceptionsTest {

    @Test
    public void testTooManyConstructorsException() {
        try {
            Injector injector = new InjectorImpl();
            injector.bind(ManyInjectConstructorsIntf.class, ManyInjectConstructorsImpl.class);
            injector.checkBindings();
            Assert.fail("Should be throwned instance of TooManyConstructorsException");
        } catch (TooManyConstructorsException tooMany) {
            tooMany.printStackTrace(System.out);
        }
    }

    @Test
    public void testConstructorNotFoundException() {
        try {
            Injector injector = new InjectorImpl();
            injector.bind(NoInjectAndDefaultConstructorIntf.class, NoInjectAndDefaultConstructorImpl.class);
            injector.checkBindings();
            Assert.fail("Should be throwned instance of ConstructorNotFoundException");
        } catch (ConstructorNotFoundException constNotfound) {
            constNotfound.printStackTrace(System.out);
        }
    }

    @Test
    public void testBindingNotFoundException() {
        try {
            Injector injector = new InjectorImpl();
            injector.bind(SingleInjectConstructorIntf.class, SingleInjectConstructorImpl.class);
            injector.checkBindings();
            Assert.fail("Should be throwned instance of BindingNotFoundException");
        } catch (BindingNotFoundException bindNotFound) {
            bindNotFound.printStackTrace(System.out);
        }
    }
}

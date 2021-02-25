import implementations.DefaultConstructorImpl;
import implementations.DoubleInjectConstructorImpl;
import implementations.SingleInjectConstructorImpl;
import interfaces.DefaultConstructorIntf;
import interfaces.DoubleInjectConstructorIntf;
import interfaces.SingleInjectConstructorIntf;
import org.example.container.Injector;
import org.example.container.Provider;
import org.example.container.implementations.InjectorImpl;
import org.junit.Test;

import static org.junit.Assert.*;

public class GettingProvidersTest {

    @Test
    public void testExistingBinding() throws Exception {
        Injector injector = new InjectorImpl();
        injector.bind(SingleInjectConstructorIntf.class, SingleInjectConstructorImpl.class);
        injector.bind(DefaultConstructorIntf.class, DefaultConstructorImpl.class);
        injector.bind(DoubleInjectConstructorIntf.class, DoubleInjectConstructorImpl.class);
        injector.checkBindings();
        Provider<DoubleInjectConstructorIntf> provider = injector.getProvider(DoubleInjectConstructorIntf.class);
        assertNotNull(provider);
        assertNotNull(provider.getInstance());
        assertSame(DoubleInjectConstructorImpl.class, provider.getInstance().getClass());
    }

    @Test
    public void testNullProvider() throws Exception {
        Injector injector = new InjectorImpl();
        Provider<Object> provider = injector.getProvider(Object.class);
        assertNull(provider);
    }

}

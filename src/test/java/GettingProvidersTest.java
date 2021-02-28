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
        injector.bindSingleton(DefaultConstructorIntf.class, DefaultConstructorImpl.class);
        injector.bind(DoubleInjectConstructorIntf.class, DoubleInjectConstructorImpl.class);
        injector.checkBindings();

        Provider<DoubleInjectConstructorIntf> providerForDouble = injector.getProvider(DoubleInjectConstructorIntf.class);
        assertNotNull(providerForDouble);
        assertNotNull(providerForDouble.getInstance());
        assertSame(DoubleInjectConstructorImpl.class, providerForDouble.getInstance().getClass());

        DoubleInjectConstructorImpl doubleImpl = (DoubleInjectConstructorImpl) providerForDouble.getInstance();
        SingleInjectConstructorImpl singleImpl = (SingleInjectConstructorImpl) doubleImpl.getSingleInjectConstructorIntf();

        Provider<DefaultConstructorIntf> providerForDefault = injector.getProvider(DefaultConstructorIntf.class);
        assertSame(doubleImpl.getDefaultConstructorIntf(), providerForDefault.getInstance());
        assertSame(singleImpl.getDefaultConstructorIntf(), providerForDefault.getInstance());

        Provider<SingleInjectConstructorIntf> providerForSingle = injector.getProvider(SingleInjectConstructorIntf.class);
        SingleInjectConstructorImpl newestSingle = (SingleInjectConstructorImpl) providerForSingle.getInstance();
        assertNotSame(singleImpl, newestSingle);
        assertSame(singleImpl.getDefaultConstructorIntf(), newestSingle.getDefaultConstructorIntf());
    }

    @Test
    public void testSingletonInstanceCreating() throws Exception {
        Injector injector = new InjectorImpl();
        injector.bindSingleton(DefaultConstructorIntf.class, DefaultConstructorImpl.class);
        injector.checkBindings();

        Provider<DefaultConstructorIntf> provider1 = injector.getProvider(DefaultConstructorIntf.class);
        assertNotNull(provider1);
        assertNotNull(provider1.getInstance());

        Provider<DefaultConstructorIntf> provider2 = injector.getProvider(DefaultConstructorIntf.class);
        assertNotNull(provider2);
        assertNotNull(provider2.getInstance());

        assertSame(provider1.getInstance(), provider2.getInstance());
    }

    @Test
    public void testPrototypeInstanceCreating() throws Exception {
        Injector injector = new InjectorImpl();
        injector.bind(DefaultConstructorIntf.class, DefaultConstructorImpl.class);
        injector.checkBindings();

        Provider<DefaultConstructorIntf> provider1 = injector.getProvider(DefaultConstructorIntf.class);
        assertNotNull(provider1);
        assertNotNull(provider1.getInstance());

        Provider<DefaultConstructorIntf> provider2 = injector.getProvider(DefaultConstructorIntf.class);
        assertNotNull(provider2);
        assertNotNull(provider2.getInstance());

        assertNotSame(provider1.getInstance(), provider2.getInstance());
    }

    @Test
    public void testNullProvider() throws Exception {
        Injector injector = new InjectorImpl();
        Provider<Object> provider = injector.getProvider(Object.class);
        assertNull(provider);
    }

}

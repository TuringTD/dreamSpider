
package conf;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

@Singleton
public class Module extends AbstractModule {


    protected void configure() {
        bind(StartupActions.class);

        // bind your injections here!

    }

}

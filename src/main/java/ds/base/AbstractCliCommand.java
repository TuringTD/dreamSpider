package ds.base;

import com.google.inject.Injector;
import ninja.utils.NinjaMode;
import ninja.utils.NinjaPropertiesImpl;
import ninja.Bootstrap;

public abstract class AbstractCliCommand {

    private Bootstrap nb = null;

    protected abstract void command(Injector injector);

    public void run(){
        try {
            this.nb = this.getNinjaBootstrap();
            nb.boot();
            this.command(nb.getInjector());
        }catch (Exception ex){
            throw ex;
        }finally {
            this.shutdown();
            System.out.println("---------------------------------------");
            System.out.println("--------    DONE         ---------------");
            System.out.println("---------------------------------------");
        }
    }

    protected Bootstrap getNinjaBootstrap(){
        return new Bootstrap(new NinjaPropertiesImpl(NinjaMode.dev));
    }

    protected void shutdown(){
        if(this.nb!=null){
            nb.shutdown();
        }
    }
}

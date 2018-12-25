package test.revolut.config;

import org.glassfish.jersey.server.ResourceConfig;
 

public class ApplicationResourceConfig extends ResourceConfig {
    public ApplicationResourceConfig() {
            packages("test.revolut");
            register(new ApplicationBinder());
         
        }
}
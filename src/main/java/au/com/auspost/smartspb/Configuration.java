package au.com.auspost.smartspb;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.springframework.context.annotation.Bean;

public class Configuration {

    @Bean
    public Module registerJodaModule() {
        return new JodaModule();
    }
}

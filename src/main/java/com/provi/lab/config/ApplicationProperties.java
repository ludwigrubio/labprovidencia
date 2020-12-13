package com.provi.lab.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Labprovidencia.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
    String lactoescanToread;
    String lactoescanReaded;

    public String getLactoescanToread() {
        return this.lactoescanToread;
    }

    public void setLactoescanToread(String lactoescanToread) {
        this.lactoescanToread = lactoescanToread;
    }

    public String getLactoescanReaded() {
        return this.lactoescanReaded;
    }

    public void setLactoescanReaded(String lactoescanReaded) {
        this.lactoescanReaded = lactoescanReaded;
    }

}

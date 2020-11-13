package com.provi.lab.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.provi.lab.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.provi.lab.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.provi.lab.domain.User.class.getName());
            createCache(cm, com.provi.lab.domain.Authority.class.getName());
            createCache(cm, com.provi.lab.domain.User.class.getName() + ".authorities");
            createCache(cm, com.provi.lab.domain.Personal.class.getName());
            createCache(cm, com.provi.lab.domain.Area.class.getName());
            createCache(cm, com.provi.lab.domain.Area.class.getName() + ".personals");
            createCache(cm, com.provi.lab.domain.Dummy.class.getName());
            createCache(cm, com.provi.lab.domain.Dummy.class.getName() + ".areas");
            createCache(cm, com.provi.lab.domain.EntityAuditEvent.class.getName());
            createCache(cm, com.provi.lab.domain.Area.class.getName() + ".userExtras");
            createCache(cm, com.provi.lab.domain.Area.class.getName() + ".fQLeches");
            createCache(cm, com.provi.lab.domain.Area.class.getName() + ".fQMantequillas");
            createCache(cm, com.provi.lab.domain.Area.class.getName() + ".fQQuesos");
            createCache(cm, com.provi.lab.domain.Area.class.getName() + ".fQCremas");
            createCache(cm, com.provi.lab.domain.Area.class.getName() + ".fQSueros");
            createCache(cm, com.provi.lab.domain.Area.class.getName() + ".pruebaMicros");
            createCache(cm, com.provi.lab.domain.Relacion.class.getName());
            createCache(cm, com.provi.lab.domain.Relacion.class.getName() + ".personals");
            createCache(cm, com.provi.lab.domain.Cultivo.class.getName());
            createCache(cm, com.provi.lab.domain.Superficie.class.getName());
            createCache(cm, com.provi.lab.domain.Recepcion.class.getName());
            createCache(cm, com.provi.lab.domain.TipoProducto.class.getName());
            createCache(cm, com.provi.lab.domain.Producto.class.getName());
            createCache(cm, com.provi.lab.domain.UserExtra.class.getName());
            createCache(cm, com.provi.lab.domain.PruebaMicro.class.getName());
            createCache(cm, com.provi.lab.domain.FQCrema.class.getName());
            createCache(cm, com.provi.lab.domain.FQLeche.class.getName());
            createCache(cm, com.provi.lab.domain.FQMantequilla.class.getName());
            createCache(cm, com.provi.lab.domain.FQQueso.class.getName());
            createCache(cm, com.provi.lab.domain.FQSuero.class.getName());
            createCache(cm, com.provi.lab.domain.Contenedor.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}

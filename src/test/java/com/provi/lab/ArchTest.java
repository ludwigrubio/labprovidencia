package com.provi.lab;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.provi.lab");

        noClasses()
            .that()
                .resideInAnyPackage("com.provi.lab.service..")
            .or()
                .resideInAnyPackage("com.provi.lab.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.provi.lab.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}

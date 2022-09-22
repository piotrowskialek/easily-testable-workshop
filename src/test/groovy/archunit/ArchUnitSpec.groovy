package archunit

import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption
import spock.lang.Specification

class ArchUnitSpec extends Specification {

    static final String APP_PACKAGE = 'com.workshop.easilytestable'
    static final String API_PACKAGE = APP_PACKAGE + '.api..'
    static final String ADAPTERS_PACKAGE = APP_PACKAGE + '.adapters..'
    static final String DOMAIN_PACKAGE = APP_PACKAGE + '.domain..'
    static final String INFRASTRUCTURE_PACKAGE = APP_PACKAGE + '.infrastructure..'
    static final String SPRING_PACKAGE = APP_PACKAGE + 'org.springframework..'

    static final classesToCheck = new ClassFileImporter()
        .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
        .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
        .importPackages(APP_PACKAGE)
}

package archunit

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.web.bind.annotation.RestController

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses

class ClassLocationSpec extends ArchUnitSpec {

    def "controllers should reside in the api package"() {
        given:
        def rule = classes()
            .that().areAnnotatedWith(RestController.class)
            .should().resideInAPackage(API_PACKAGE)

        expect:
        rule.check(classesToCheck)
    }

    def "repositories should reside in the adapters package"() {
        given:
        def rule = classes()
            .that().areAssignableTo(MongoRepository.class)
            .should().resideInAPackage(ADAPTERS_PACKAGE)

        expect:
        rule.check(classesToCheck)
    }

    def "configurations should reside in the infrastructure package"() {
        given:
        def rule = classes()
            .that().areAnnotatedWith(Configuration.class)
            .should().resideInAPackage(INFRASTRUCTURE_PACKAGE)

        expect:
        rule.check(classesToCheck)
    }

    def "there should be no spring dependent classes in the domain package"() {
        given:
        def rule = noClasses()
            .that().resideInAPackage(DOMAIN_PACKAGE)
            .should().dependOnClassesThat().resideInAPackage(SPRING_PACKAGE)

        expect:
        rule.check(classesToCheck)
    }
}

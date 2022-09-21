package archunit

import spock.lang.Ignore

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses

class LayerAccessSpec extends ArchUnitSpec {

    @Ignore("intentionally ignored")
    def "classes in api package should not depend on classes from adapters package"() {
        given:
        def rule = noClasses()
            .that().resideInAPackage(API_PACKAGE)
            .should().dependOnClassesThat().resideInAPackage(ADAPTERS_PACKAGE)

        expect:
        rule.check(classesToCheck)
    }

    def "classes in domain package should not depend on classes from api or adapters packages"() {
        given:
        def rule = noClasses()
            .that().resideInAPackage(DOMAIN_PACKAGE)
            .should().dependOnClassesThat().resideInAPackage(API_PACKAGE)
            .andShould().dependOnClassesThat().resideInAPackage(ADAPTERS_PACKAGE)

        expect:
        rule.check(classesToCheck)
    }

    def "classes in adapters package should not depend on classes in api package"() {
        given:
        def rule = noClasses()
            .that().resideInAPackage(ADAPTERS_PACKAGE)
            .should().dependOnClassesThat().resideInAPackage(API_PACKAGE)

        expect:
        rule.check(classesToCheck)
    }
}

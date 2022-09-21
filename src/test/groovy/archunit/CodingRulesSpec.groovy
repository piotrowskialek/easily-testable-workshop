package archunit

import com.tngtech.archunit.library.GeneralCodingRules
import org.springframework.cache.annotation.Cacheable
import org.springframework.transaction.annotation.Transactional

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods

class CodingRulesSpec extends ArchUnitSpec {

    def "timed annotation can not be applied on private method"() {
        given:
        def rule = methods()
            .that().areAnnotatedWith(Transactional)
            .or().areAnnotatedWith(Cacheable)
            .should().notBePrivate().allowEmptyShould(true)

        expect:
        rule.check(classesToCheck)
    }

    def "general coding rules"() {
        expect:
        GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS.because("You should use a logger").check(classesToCheck)
        GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS.because("You should name your exceptions").check(classesToCheck)
        GeneralCodingRules.NO_CLASSES_SHOULD_USE_JODATIME.because("You should not use the jodatime").check(classesToCheck)
        GeneralCodingRules.NO_CLASSES_SHOULD_USE_FIELD_INJECTION.because("You should use constructor/setter injection").check(classesToCheck)
    }
}

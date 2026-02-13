package com.genealogy.tree

import jakarta.validation.Validation
import jakarta.validation.Validator
import jakarta.validation.ValidatorFactory
import spock.lang.Shared
import spock.lang.Specification
import java.time.OffsetDateTime
import java.util.UUID

class GenealogyTreeSpec extends Specification {

    @Shared
    Validator validator

    def setupSpec() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory()
        validator = factory.getValidator()
    }

    def "should create a valid GenealogyTree"() {
        given:
        def uuid = UUID.randomUUID()
        def now = OffsetDateTime.now()
        def creator = TreeCreator.builder()
                .firstName("Stéphane")
                .lastName("Kermabon")
                .email("stephane@example.com")
                .build()

        when:
        def tree = GenealogyTree.builder()
                .uuid(uuid)
                .creationDateTime(now)
                .lastModificationDateTime(now)
                .creator(creator)
                .build()
        def violations = validator.validate(tree)

        then:
        violations.isEmpty()
        tree.uuid() == uuid
        tree.creationDateTime() == now
        tree.lastModificationDateTime() == now
        tree.creator().firstName() == "Stéphane"
        tree.creator().lastName() == "Kermabon"
        tree.creator().email() == "stephane@example.com"
    }

    def "should detect violations in TreeCreator"() {
        given:
        def creator = TreeCreator.builder()
                .firstName("")
                .lastName("  ")
                .email("invalid-email")
                .build()

        when:
        def violations = validator.validate(creator)

        then:
        violations.size() == 3
        violations.any { it.propertyPath.toString() == "firstName" }
        violations.any { it.propertyPath.toString() == "lastName" }
        violations.any { it.propertyPath.toString() == "email" }
    }

    def "should detect cascaded violations in GenealogyTree"() {
        given:
        def invalidCreator = TreeCreator.builder().build()
        def uuid = UUID.randomUUID()
        def now = OffsetDateTime.now()

        when:
        def tree = GenealogyTree.builder()
                .uuid(uuid)
                .creationDateTime(now)
                .lastModificationDateTime(now)
                .creator(invalidCreator)
                .build()
        def violations = validator.validate(tree)

        then:
        !violations.isEmpty()
    }

    def "should detect null attributes using validator"() {
        given:
        def tree = GenealogyTree.builder().build()

        when:
        def violations = validator.validate(tree)

        then:
        !violations.isEmpty()
        violations.size() == 2
        violations.any { it.propertyPath.toString() == "creationDateTime" }
        violations.any { it.propertyPath.toString() == "creator" }
    }

    def "should support toBuilder copy functionality"() {
        given:
        def original = GenealogyTree.builder()
                .uuid(UUID.randomUUID())
                .creationDateTime(OffsetDateTime.now())
                .lastModificationDateTime(OffsetDateTime.now())
                .creator(TreeCreator.builder()
                    .firstName("Stéphane")
                    .lastName("Kermabon")
                    .email("stephane@example.com")
                    .build())
                .build()

        when:
        def newTime = OffsetDateTime.now().plusHours(1)
        def updated = original.toBuilder()
                .lastModificationDateTime(newTime)
                .build()

        then:
        updated.uuid() == original.uuid()
        updated.creationDateTime() == original.creationDateTime()
        updated.lastModificationDateTime() == newTime
        updated.creator() == original.creator()
    }
}

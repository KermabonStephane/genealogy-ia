package com.genealogy.tree;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Represents a modern genealogy tree with audit information.
 */
@Builder(toBuilder = true)
@Valid
public record GenealogyTree(
    UUID uuid,
    @NotNull OffsetDateTime creationDateTime,
    OffsetDateTime lastModificationDateTime,
    @NotNull @Valid TreeCreator creator
) {
}

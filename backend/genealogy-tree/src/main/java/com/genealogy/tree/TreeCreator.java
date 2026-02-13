package com.genealogy.tree;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

/**
 * Information about the creator of a genealogy tree.
 */
@Builder(toBuilder = true)
public record TreeCreator(
    @NotBlank String firstName,
    @NotBlank String lastName,
    @Email @NotBlank String email
) {}

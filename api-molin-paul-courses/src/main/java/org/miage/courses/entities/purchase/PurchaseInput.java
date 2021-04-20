package org.miage.courses.entities.purchase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseInput {

    @NotBlank
    @NotNull
    private String userId;
    @NotBlank
    @NotNull
    private String courseId;
}

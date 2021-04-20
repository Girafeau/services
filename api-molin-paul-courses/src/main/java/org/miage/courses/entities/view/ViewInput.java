package org.miage.courses.entities.view;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewInput {

    @NotBlank
    @NotNull
    private String userId;
    @NotBlank
    @NotNull
    private String episodeId;

}

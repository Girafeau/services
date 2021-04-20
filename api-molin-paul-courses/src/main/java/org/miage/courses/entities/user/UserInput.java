package org.miage.courses.entities.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInput {

    @NotNull
    @NotBlank
    private String id;

    public User transform() {
        return new User(this.id);
    }
}

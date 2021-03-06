package org.miage.users.entities.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInput {

    @NotBlank
    @NotNull
    @Email
    private String email;
    @NotBlank
    @NotNull
    @Size(min=2)
    private String firstname;
    @NotBlank
    @NotNull
    @Size(min=2)
    private String lastname;

    public User transform() {
        return new User(this.email, this.firstname, this.lastname);
    }
}

package com.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class UserObjDto {


    @NotNull(message = "{user.firstname.notnull}")
    @NotBlank(message = "{user.notblank}")
    @Size(min=3,max = 15,message = "{user.firstname.length}")
    private String firstName;
    @NotNull(message = "{user.lastname.notnull}")
    @NotBlank(message = "{user.notblank}")
    @Size(min=3,max = 15,message = "{user.lastname.length}")
    private String lastName;
    @NotNull(message = "{user.lastname.notnull}")
    @Email(message = "{user.email.emailP}")
    @NotBlank(message = "{user.notblank}")
    private String email;
    @NotNull(message = "Field can't be null.")
    @NotBlank(message = "{user.notblank}")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "{user.password.pattern}"
    )
    private String password;

}

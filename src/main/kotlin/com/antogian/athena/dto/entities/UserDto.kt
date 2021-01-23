package com.antogian.athena.dto.entities

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class UserDto {
    @NotNull
    @NotEmpty
    var firstName: String? = null

    @NotNull
    @NotEmpty
    var lastName: String? = null

    @NotNull
    @NotEmpty
    var password: String? = null
    var matchingPassword: String? = null

    @NotNull
    @NotEmpty
    var email: String? = null // standard getters and setters
}
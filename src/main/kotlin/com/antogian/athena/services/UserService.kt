package com.antogian.athena.services

import com.antogian.athena.entities.User
import org.springframework.stereotype.Service

import java.util.ArrayList

@Service
class UserService {

    val currentUser: User
        get() = User()

}

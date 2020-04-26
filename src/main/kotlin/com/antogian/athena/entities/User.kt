package com.antogian.athena.entities

class User {
    var firstName: String? = null
    var lastName: String? = null
    var email: String? = null
    var phoneNumber: String? = null
    var city: String? = null
    var state: String? = null
    var zip: String? = null
    var address: String? = null
    var floor: Int = 0

    init {
        firstName = ""
        lastName = ""
        email = ""
        phoneNumber = ""
        city = ""
        state = ""
        zip = ""
        address = ""
        floor = 0
    }

}

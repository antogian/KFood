package com.antogian.athena.model

class ChargeRequest {
    var description: String? = null
    var amount: Int = 0
    var currency: Currency? = null
    var stripeEmail: String? = null
    var stripeToken: String? = null

    enum class Currency {
        EUR, USD
    }
}
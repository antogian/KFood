package com.antogian.athena.services

import com.antogian.athena.model.ChargeRequest
import com.stripe.Stripe
import com.stripe.exception.*
import com.stripe.model.Charge
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.HashMap
import javax.annotation.PostConstruct

@Service
class StripeService {

    //    @Autowired
    //    private Environment env;

    @Value("\${stripe.private.key}")
    private val secretKey: String? = null

    @PostConstruct
    fun init() {
        Stripe.apiKey = secretKey
    }

    @Throws(AuthenticationException::class, InvalidRequestException::class, APIConnectionException::class,
            CardException::class, APIException::class)
    fun charge(chargeRequest: ChargeRequest): Charge {
        val chargeParams = HashMap<String, Any?>()
        chargeParams["amount"] = chargeRequest.amount
        chargeParams["currency"] = chargeRequest.currency
        chargeParams["description"] = chargeRequest.description
        chargeParams["source"] = chargeRequest.stripeToken
        return Charge.create(chargeParams)
    }
}

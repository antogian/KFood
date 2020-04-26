package com.antogian.athena.controllers

import com.antogian.athena.entities.Order
import com.antogian.athena.entities.User
import com.antogian.athena.model.ChargeRequest
import com.antogian.athena.model.ShoppingCart
import com.antogian.athena.services.OrderService
import com.antogian.athena.services.StripeService
import com.antogian.athena.services.UserService
import com.stripe.exception.StripeException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.servlet.http.HttpServletRequest

@Controller
@Scope("session")
class CheckoutController

@Autowired
constructor(private val userService: UserService,
            private val orderService: OrderService,
            private val stripeService: StripeService) {

    private var currentUser: User? = null
    private var cart: ShoppingCart? = null

    //    @Autowired
    //    private Environment env;

    @Value("\${stripe.public.key}")
    private val stripePublicKey: String? = null

    private fun initialize() {
        currentUser = userService.currentUser
        cart = ShoppingCart()
    }

    @GetMapping(value = ["/checkout"])
    fun checkout(model: Model, request: HttpServletRequest): String {
        initialize()
        cart = request.session.getAttribute("shoppingCart") as ShoppingCart
        if (cart == null)
            return "redirect:/menu"

        //        //----------------------------------------------------------------------------------------------------
        //        //TODO: Must be removed
        //        Order newOrder = new Order();
        //        newOrder.setCart(cart);
        //        orderService.insertOrder(newOrder);
        //        //----------------------------------------------------------------------------------------------------

        val amount = cart!!.getTotalCost() * 100

        model.addAttribute("bucket", cart)
        model.addAttribute("user", User())
        model.addAttribute("amount", amount.toInt()) // in cents
        model.addAttribute("stripePublicKey", stripePublicKey)
        model.addAttribute("currency", ChargeRequest.Currency.USD)

        return "checkout"
    }

    @PostMapping("/charge")
    @Throws(StripeException::class)
    fun charge(chargeRequest: ChargeRequest, model: Model, redirectAttributes: RedirectAttributes,
               request: HttpServletRequest,
               @RequestParam(value = "firstName", required = false) firstName: String,
               @RequestParam(value = "lastName", required = false) lastName: String,
               @RequestParam(value = "email", required = false) email: String,
               @RequestParam(value = "phone", required = false) phoneNumber: String,
               @RequestParam(value = "city", required = false) city: String,
               @RequestParam(value = "state", required = false) state: String,
               @RequestParam(value = "zip", required = false) zip: String,
               @RequestParam(value = "address", required = false) address: String,
               @RequestParam(value = "floor", required = false) floor: Int): String {
        //chargeRequest.setDescription("Example charge");
        chargeRequest.currency = ChargeRequest.Currency.USD
        //chargeRequest.setAmount((int)cart.getTotalCost() * 100);
        val charge = stripeService.charge(chargeRequest)
        //        model.addAttribute("id", charge.getId());
        //        model.addAttribute("status", charge.getStatus());
        //        model.addAttribute("chargeId", charge.getId());
        //        model.addAttribute("balance_transaction", charge.getBalanceTransaction());

        currentUser!!.firstName = firstName
        currentUser!!.lastName = lastName
        currentUser!!.email = email
        currentUser!!.phoneNumber = phoneNumber
        currentUser!!.city = city
        currentUser!!.state = state
        currentUser!!.zip = zip
        currentUser!!.address = address
        currentUser!!.floor = floor

        val order = Order()
        order.cart = cart
        order.user = currentUser
        order.isPaid = charge.getPaid()

        orderService.insertOrder(order)

        redirectAttributes.addFlashAttribute("message", "Order Completed Successfully.")

        return "redirect:/menu"
    }

    @ExceptionHandler(StripeException::class)
    fun handleError(model: Model, ex: StripeException, redirectAttributes: RedirectAttributes): String {
        redirectAttributes.addFlashAttribute("message", ex.message)
        return "redirect:/checkout"
    }

    @RequestMapping(value = ["/processOrder"], method = [RequestMethod.POST])
    fun processOrder(@RequestParam(value = "firstName") firstName: String,
                     @RequestParam(value = "lastName") lastName: String, @RequestParam(value = "email") email: String,
                     @RequestParam(value = "phone") phoneNumber: String, @RequestParam(value = "city") city: String,
                     @RequestParam(value = "state") state: String, @RequestParam(value = "zip") zip: String,
                     @RequestParam(value = "address") address: String, @RequestParam(value = "floor") floor: Int): String {

        currentUser!!.firstName = firstName
        currentUser!!.lastName = lastName
        //TODO: Made them comments for the purpose of MongoDB training
        //        currentUser.setEmail(email);
        //        currentUser.setPhoneNumber(phoneNumber);
        //        currentUser.setCity(city);
        //        currentUser.setState(state);
        //        currentUser.setZip(zip);
        //        currentUser.setAddress(address);
        //        currentUser.setFloor(floor);

        return "redirect:/home"
    }

}
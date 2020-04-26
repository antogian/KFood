package com.antogian.athena.controllers

import com.antogian.athena.model.CartEntry
import com.antogian.athena.dto.entities.CategoryDTO
import com.antogian.athena.dto.entities.ItemDTO
import com.antogian.athena.model.ShoppingCart
import com.antogian.athena.dto.CategoryCoverter
import com.antogian.athena.dto.ItemConverter
import com.antogian.athena.services.MenuService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.util.*
import javax.servlet.http.HttpServletRequest

@Controller
@Scope("session")
class CategoryController

@Autowired
constructor(private val categoryCoverter: CategoryCoverter, private val itemConverter: ItemConverter,
            private val menuService: MenuService) {
    private var allCats: List<CategoryDTO>? = null
    private var shoppingCart: ShoppingCart? = null
    private var currentItem = ItemDTO()

    private fun initialize() {
        shoppingCart = ShoppingCart()
        allCats = ArrayList<CategoryDTO>()
        try {
            allCats = categoryCoverter.allCats
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun checkSelectedItem(id: String?) {
        if (id == null || id.equals("", ignoreCase = true))
            currentItem = ItemDTO()
        else
            currentItem = allCats?.let { menuService.getItemById(it, id) }!!
    }

    @GetMapping("/menu")
    fun menu(model: Model, redirectAttributes: RedirectAttributes): String {
        if (allCats == null) {
            initialize()
        }

        if (model.containsAttribute("message"))
            shoppingCart = ShoppingCart()

        model.addAttribute("allCats", allCats)
        model.addAttribute("bucket", shoppingCart)
        model.addAttribute("totalItems", shoppingCart!!.getEntries()?.size)

        if (allCats!!.isEmpty())
            model.addAttribute("message", "Cannot parse menu")

        return "menu"
    }

    @ExceptionHandler(Exception::class)
    fun handleError(model: Model, ex: Exception, redirectAttributes: RedirectAttributes): String {
        redirectAttributes.addFlashAttribute("message", ex.message)
        return "redirect:/menu"
    }

    @RequestMapping("/menu/item/{id}")
    fun item(@PathVariable("id") id: String, model: Model): String {
        checkSelectedItem(id)
        model.addAttribute("bucket", shoppingCart)
        model.addAttribute("totalItems", shoppingCart!!.getEntries()?.size)
        model.addAttribute("currentItem", currentItem)
        model.addAttribute("modifiers", currentItem.modifiers)

        return "item"
    }

    @RequestMapping(value = ["/add"], method = [RequestMethod.POST])
    fun addItemToCart(@RequestParam(value = "itemId") id: String,
                      @ModelAttribute("currentItem") item: ItemDTO,
                      @RequestParam(value = "itemQuantity") quantity: Int,
                      @RequestParam(value = "itemSize", required = false) sizeName: String?): String {
        val newItem = allCats?.let { menuService.getItemById(it, id) }
        val cartItem = newItem?.let { itemConverter.getItemByValue(it) }
        if (cartItem != null) {
            cartItem.id = UUID.randomUUID().toString()
        }

        val newEntry = CartEntry()

        //TODO: Correct no toppings scenario
        if (cartItem != null) {
            item.modifiers?.let { menuService.checkModifiers(cartItem, it) }
        }

        if (cartItem != null) {
            menuService.checkSizes(cartItem, sizeName)
        }
        cartItem?.calculateTotalCost()

        newEntry.item = cartItem
        newEntry.quantity = quantity
        shoppingCart!!.addEntry(newEntry)

        return "redirect:/menu"
    }

    @RequestMapping(value = ["/edit"], method = [RequestMethod.POST])
    fun editITem(@RequestParam(value = "itemId") id: String,
                 @ModelAttribute("currentItem") item: ItemDTO,
                 @RequestParam(value = "itemQuantity") quantity: Int,
                 @RequestParam(value = "itemSize", required = false) sizeName: String?): String {
        val bucketEntry = shoppingCart?.let { menuService.getEntryFromCart(it, id) }
        val newItem = bucketEntry?.item
        val cartItem = newItem?.let { itemConverter.getItemByValue(it) }
        cartItem?.id = UUID.randomUUID().toString()

        val newEntry = CartEntry()

        //TODO: Correct no toppings scenario
        menuService.checkModifiers(cartItem!!, item.modifiers!!)

        menuService.checkSizes(cartItem, sizeName)
        cartItem.calculateTotalCost()

        newEntry.item = cartItem
        newEntry.quantity = quantity

        shoppingCart!!.removeEntryById(id)
        shoppingCart!!.addEntry(newEntry)

        return "redirect:/menu"
    }

    @RequestMapping("/menu/item/edit/{id}")
    fun edit(@PathVariable("id") id: String, model: Model): String {
        val newEntry = shoppingCart?.let { menuService.getEntryFromCart(it, id) }
        currentItem = newEntry?.item!!
        //TODO: Quantity isn't initiated.
        model.addAttribute("bucket", shoppingCart)
        model.addAttribute("totalItems", shoppingCart!!.getEntries()?.size)
        model.addAttribute("currentItem", currentItem)
        model.addAttribute("modifiers", currentItem.modifiers)

        return "edit"
    }

    @RequestMapping("/delete")
    fun removeItem(@RequestParam(value = "item") id: String): String {
        shoppingCart?.let { menuService.removeItemById(it, id) }

        return "redirect:/menu"
    }

    @RequestMapping(value = ["/proceed"])
    fun checkout(request: HttpServletRequest): String {
        request.session.setAttribute("shoppingCart", shoppingCart)

        return "redirect:/checkout"
    }

    @RequestMapping(value = ["/proceed"], method = [RequestMethod.POST])
    fun proceedToCheckout(httpServletRequest: HttpServletRequest): String {
        httpServletRequest.session.setAttribute("shoppingCart", shoppingCart)

        return "redirect:/checkout"
    }
}

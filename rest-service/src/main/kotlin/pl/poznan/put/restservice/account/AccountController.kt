package pl.poznan.put.restservice.account

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("accounts")
class AccountController(
    private val accountService: AccountService
) {
    @GetMapping
    fun getAll(): List<AccountDTO> =
        accountService.getAll()

    @PostMapping
    fun create(@RequestBody account: AccountDTO): AccountDTO =
        accountService.create(account)

    @DeleteMapping("/{accountId}")
    fun delete(@PathVariable accountId: String) {
        accountService.delete(accountId)
    }
}
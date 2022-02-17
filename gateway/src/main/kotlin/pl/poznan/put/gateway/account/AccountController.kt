package pl.poznan.put.gateway.account

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import pl.poznan.put.gateway.account.dto.AccountDTO

@RequestMapping("/accounts")
@RestController
class AccountController(
    private val accountServiceClient: AccountServiceClient
) {
    @GetMapping
    fun getAll(): List<AccountDTO> =
        accountServiceClient.getAllAccounts()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody account: AccountDTO): AccountDTO =
        accountServiceClient.createAccount(account)

    @DeleteMapping("/{accountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable accountId: String) =
        accountServiceClient.deleteAccount(accountId)
}
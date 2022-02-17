package pl.poznan.put.gateway.account

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import pl.poznan.put.gateway.account.dto.AccountDTO

@FeignClient(
    name = "accounts",
    url = "\${services.account.url}",
    path = "accounts"
)
interface AccountServiceClient {
    @GetMapping
    fun getAllAccounts(): List<AccountDTO>

    @PostMapping
    fun createAccount(@RequestBody account: AccountDTO): AccountDTO

    @DeleteMapping("/{accountId}")
    fun deleteAccount(@PathVariable accountId: String)
}
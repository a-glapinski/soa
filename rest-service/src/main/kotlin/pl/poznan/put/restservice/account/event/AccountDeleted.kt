package pl.poznan.put.restservice.account.event

import pl.poznan.put.restservice.account.model.Account

data class AccountDeleted(
    val username: String,
    val firstName: String,
    val lastName: String
) {
    constructor(account: Account) : this(
        username = account.username,
        firstName = account.firstName,
        lastName = account.lastName
    )
}

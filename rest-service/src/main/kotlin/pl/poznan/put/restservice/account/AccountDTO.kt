package pl.poznan.put.restservice.account

import pl.poznan.put.restservice.account.model.Account

data class AccountDTO(
    val username: String,
    val firstName: String,
    val lastName: String
) {
    constructor(account: Account) : this(
        username = account.username,
        firstName = account.firstName,
        lastName = account.lastName
    )

    fun toEntity() = Account(
        username = username,
        firstName = firstName,
        lastName = lastName
    )
}

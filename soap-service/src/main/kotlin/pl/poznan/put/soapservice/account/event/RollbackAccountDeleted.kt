package pl.poznan.put.soapservice.account.event

data class RollbackAccountDeleted(
    val username: String,
    val firstName: String,
    val lastName: String
) {
    constructor(accountDeleted: AccountDeleted) : this(
        username = accountDeleted.username,
        firstName = accountDeleted.firstName,
        lastName = accountDeleted.lastName
    )
}
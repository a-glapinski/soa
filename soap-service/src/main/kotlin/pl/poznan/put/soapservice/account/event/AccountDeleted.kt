package pl.poznan.put.soapservice.account.event

data class AccountDeleted(
    val username: String,
    val firstName: String,
    val lastName: String
)

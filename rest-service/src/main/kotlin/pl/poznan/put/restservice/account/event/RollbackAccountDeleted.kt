package pl.poznan.put.restservice.account.event

data class RollbackAccountDeleted(
    val username: String,
    val firstName: String,
    val lastName: String
)
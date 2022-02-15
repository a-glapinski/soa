package pl.poznan.put.restservice.account.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Account(
    @Id
    val username: String,
    val firstName: String,
    val lastName: String
)
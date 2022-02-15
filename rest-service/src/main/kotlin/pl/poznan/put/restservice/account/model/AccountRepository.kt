package pl.poznan.put.restservice.account.model

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pl.poznan.put.restservice.account.model.Account

@Repository
interface AccountRepository : JpaRepository<Account, String>
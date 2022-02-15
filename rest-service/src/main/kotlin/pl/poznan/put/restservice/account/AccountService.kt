package pl.poznan.put.restservice.account

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pl.poznan.put.restservice.account.event.AccountDeleted
import pl.poznan.put.restservice.account.event.RollbackAccountDeleted
import pl.poznan.put.restservice.account.model.Account
import pl.poznan.put.restservice.account.model.AccountRepository
import pl.poznan.put.restservice.rabbit.EventType
import pl.poznan.put.restservice.rabbit.RabbitEvent
import pl.poznan.put.restservice.rabbit.RabbitEventPublisher

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val eventPublisher: RabbitEventPublisher
) {
    fun getAll(): List<AccountDTO> =
        accountRepository
            .findAll()
            .map(::AccountDTO)

    fun create(account: AccountDTO): AccountDTO =
        accountRepository
            .save(account.toEntity())
            .let(::AccountDTO)

    fun delete(accountId: String) {
        accountRepository
            .findByIdOrNull(accountId)
            ?.let { account ->
                accountRepository.delete(account)
                eventPublisher.publish(
                    RabbitEvent(
                        payload = AccountDeleted(account),
                        type = EventType.AccountDeleted
                    )
                )
            }
    }

    fun handle(rollbackAccountDeleted: RollbackAccountDeleted) {
        accountRepository.save(
            Account(
                username = rollbackAccountDeleted.username,
                firstName = rollbackAccountDeleted.firstName,
                lastName = rollbackAccountDeleted.lastName
            )
        )
    }
}
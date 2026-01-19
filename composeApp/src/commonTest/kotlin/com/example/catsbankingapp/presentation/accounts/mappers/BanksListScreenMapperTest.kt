package com.example.catsbankingapp.presentation.accounts.mappers

import com.example.catsbankingapp.domain.models.Bank
import com.example.catsbankingapp.domain.models.BanksList
import com.example.catsbankingapp.presentation.accounts.AccountsPresenterActions
import com.example.catsbankingapp.presentation.accounts.models.BankSectionUIModel
import com.example.catsbankingapp.utils.StringProvider
import org.jetbrains.compose.resources.StringResource
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BanksListScreenMapperTest {

    private class FakeStringProvider : StringProvider {
        override suspend fun getString(resource: StringResource): String {
            return "Mapped String"
        }
    }

    private class FakeBankSectionMapper : BankSectionMapper {
        override fun toUIModel(
            title: String,
            banks: List<Bank>,
            accountsPresenterActions: AccountsPresenterActions
        ): BankSectionUIModel {
            return BankSectionUIModel(title = title, banks = emptyList())
        }
    }

    private class FakeAccountsPresenterActions : AccountsPresenterActions {
        override fun onAccountClicked(accountId: String) {}
        override fun onRetryClicked() {}
    }

    @Test
    fun mapToUIModel_maps_sections_correctly() = runTest {
        // Arrange
        val bankSectionMapper = FakeBankSectionMapper()
        val stringProvider = FakeStringProvider()
        val mapper = BanksListScreenMapperImpl(bankSectionMapper, stringProvider)
        val banksList = BanksList(CABanks = arrayListOf(), otherBanks = arrayListOf())
        val actions = FakeAccountsPresenterActions()

        // Act
        val result = mapper.mapToUIModel(banksList, actions)

        // Assert
        assertEquals("Mapped String", result.CABankSection.title)
        assertEquals("Mapped String", result.otherBanksSection.title)
    }
}

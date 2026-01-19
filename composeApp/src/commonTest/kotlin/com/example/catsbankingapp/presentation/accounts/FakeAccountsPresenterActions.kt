package com.example.catsbankingapp.presentation.accounts

class FakeAccountsPresenterActions : AccountsPresenterActions {
    var lastClickedAccountId: String? = null
    var retryClicked: Boolean = false

    override fun onAccountClicked(accountId: String) {
        lastClickedAccountId = accountId
    }

    override fun onRetryClicked() {
        retryClicked = true
    }
}

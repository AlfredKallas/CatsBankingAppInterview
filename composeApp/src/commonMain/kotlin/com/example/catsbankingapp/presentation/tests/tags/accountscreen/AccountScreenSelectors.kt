package com.example.catsbankingapp.presentation.tests.tags.accountscreen

object AccountScreenSelectors {
    val AccountScreenTag = "AccountScreen"
    val CABanksSectionHeaderTag = "Account_Screen_CA_Banks_Section_Header"
    fun CABankTag(index: Int) = "Account_Screen_CA_Bank_$index"
    fun CABanksAccountTag(index: Int) = "Account_Screen_CA_Banks_Account_$index"
    val OtherBanksSectionHeaderTag = "Account_Screen_Other_Banks_Section_Header"
    fun OtherBankTag(index: Int) = "Account_Screen_Other_Bank_$index"
    fun OtherBanksAccountTag(index: Int) = "Account_Screen_Other_Banks_Account_$index"
}
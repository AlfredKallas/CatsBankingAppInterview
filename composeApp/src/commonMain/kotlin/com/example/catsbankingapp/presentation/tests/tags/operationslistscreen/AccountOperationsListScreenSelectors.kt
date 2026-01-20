package com.example.catsbankingapp.presentation.tests.tags.operationslistscreen

object AccountOperationsListScreenSelectors {
    val AccountOperationsListScreenTag = "Account_Operations_List_Screen"
    val AccountTitleTag = "Account_Title"
    val TotalBalanceTag = "Total_Balance"
    fun OperationCardTag(index: Int) = "Operation_Card_$index"
    fun OperationTitleTag(index: Int) = "Operation_Title_$index"
    fun OperationDateTag(index: Int) = "Operation_Date_$index"
    fun OperationBalanceTag(index: Int) = "Operation_Balance_$index"
    val AccountOperationsListBackNavigationTag = "Account_Operations_List_Back_Navigation"
}
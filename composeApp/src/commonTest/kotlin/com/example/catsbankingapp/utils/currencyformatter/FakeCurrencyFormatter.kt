package com.example.catsbankingapp.utils.currencyformatter

class FakeCurrencyFormatter : CurrencyFormatter {
    override fun format(
        amount: Double,
        currencyCode: String,
        withCurrencySymbol: Boolean,
        minimumFractionDigits: Int,
        maximumFractionDigits: Int
    ): String {
        return "$amount Formatted"
    }
}

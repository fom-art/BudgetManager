package com.wf.bm.core.model

enum class Currency(val sign: String, val currencyNameRes: Int) {
    CZK("Kč", R.string.czk),
    UAH("₴", R.string.uah),
    USD("$", R.string.usd),
    EUR("€", R.string.eur);

    fun exchangeCurrency(
        amount: Double,
        toCurrency: Currency,
        rates: Map<Currency, Map<Currency, Double>> = defaultExchangeRates
    ): Double {
        val ratesForFromCurrency = rates[this]
        if (ratesForFromCurrency == null) {
            println("No rates defined for ${this.name}. Returning the original amount.")
            return amount // Return the original amount if no rates are available
        }

        val rate = ratesForFromCurrency[toCurrency]
        if (rate == null) {
            println("No conversion rate from ${this.name} to ${toCurrency.name}. Returning the original amount.")
            return amount // Return the original amount if no specific rate is available
        }

        return amount * rate
    }

    companion object {
        fun findCurrencyNameRes(resId: Int): Currency? {
            return Currency.entries.find { it.currencyNameRes == resId }
        }
    }
}

// Default Exchange Rates (can be updated dynamically elsewhere)
val defaultExchangeRates = mapOf(
    Currency.USD to mapOf(Currency.CZK to 22.0, Currency.UAH to 36.0, Currency.EUR to 0.85),
    Currency.CZK to mapOf(Currency.USD to 0.045, Currency.UAH to 1.64, Currency.EUR to 0.038),
    Currency.UAH to mapOf(Currency.USD to 0.028, Currency.CZK to 0.61, Currency.EUR to 0.024),
    Currency.EUR to mapOf(Currency.USD to 1.18, Currency.CZK to 26.0, Currency.UAH to 41.0)
)
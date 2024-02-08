package com.example.countries.entities

data class Nation(
    val capitalCity: String,
    val countryCode: String,
    val nationCurrency: Currency,
    val flagImageUrl: String,
    val primaryLanguage: Language,
    val nationName: String,
    val geographicalRegion: String
) {
    data class Currency(
        val currencyCode: String,
        val currencyName: String,
        val currencySymbol: String
    )

    data class Language(
        val languageCode: String,
        val languageName: String
    )
}

typealias Nations = List<Nation>

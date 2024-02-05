package com.example.countries.usecases

import com.example.countries.Interactor
import com.example.countries.api.CountryApi
import com.example.countries.entities.Countries
import com.example.countries.repository.CountryRepository
import com.example.countries.repository.CountryRepositoryImpl

fun interface GetCountriesInteractor : Interactor<Result<Countries>>

fun getCountriesInteractor(repository: CountryRepository = CountryRepositoryImpl(CountryApi.service)) =
    GetCountriesInteractor(repository::getCountries)

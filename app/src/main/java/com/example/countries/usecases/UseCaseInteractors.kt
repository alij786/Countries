package com.example.countries.usecases

import com.example.countries.Interactor
import com.example.countries.api.NationsApi
import com.example.countries.entities.Nations
import com.example.countries.repository.NationsRepository
import com.example.countries.repository.NationsRepositoryImpl

fun interface GetNationsInteractor : Interactor<Result<Nations>>

fun getNationsInteractor(repository: NationsRepository = NationsRepositoryImpl(NationsApi.instance)) =
    GetNationsInteractor(repository::getNations)

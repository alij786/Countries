package com.example.countries.repository

import com.example.countries.api.NationsApi
import com.example.countries.entities.Nations
import java.io.IOException

interface NationsRepository {
    suspend fun getNations(): Result<Nations>
}

class NationsRepositoryImpl(private val nationsApi: NationsApi) : NationsRepository {
    override suspend fun getNations(): Result<Nations> {
        return runCatching {
            nationsApi.getNations().run {
                if (isSuccessful) body() ?: emptyList()
                else throw IOException(errorBody()?.string())
            }
        }
    }
}

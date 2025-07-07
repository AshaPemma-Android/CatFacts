package com.example.catfacts.data.repository

import com.example.catfacts.data.remote.api.CatFactApi
import com.example.catfacts.domain.model.CatFact
import com.example.catfacts.domain.repository.CatFactRepository
import javax.inject.Inject

class CatFactRepositoryImpl @Inject constructor(
    private val api: CatFactApi
) : CatFactRepository {
    override suspend fun getCatFact(): CatFact = api.getCatFact().toDomain()
}
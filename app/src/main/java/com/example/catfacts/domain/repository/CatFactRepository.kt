package com.example.catfacts.domain.repository

import com.example.catfacts.domain.model.CatFact

interface CatFactRepository {
    suspend fun getCatFact(): CatFact
}
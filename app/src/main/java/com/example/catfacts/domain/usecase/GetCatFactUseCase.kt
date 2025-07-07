package com.example.catfacts.domain.usecase

import com.example.catfacts.domain.model.CatFact
import com.example.catfacts.domain.repository.CatFactRepository
import javax.inject.Inject

class GetCatFactUseCase @Inject constructor(
    private val repository: CatFactRepository
) {
    suspend operator fun invoke(): CatFact = repository.getCatFact()
}
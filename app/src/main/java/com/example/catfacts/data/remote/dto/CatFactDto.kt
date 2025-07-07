package com.example.catfacts.data.remote.dto

import com.example.catfacts.domain.model.CatFact

data class CatFactDto(val fact: String, val length: Int) {
    fun toDomain(): CatFact = CatFact(fact, length)
}
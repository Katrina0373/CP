package com.mocalovak.cp.data.repository

import com.mocalovak.cp.data.local.dao.CharacterDao
import com.mocalovak.cp.data.local.entity.toDomain
import com.mocalovak.cp.domain.model.Character
import com.mocalovak.cp.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val dao:CharacterDao
): CharacterRepository{
    override fun getCharacters(): Flow<List<Character>> {
        var listic = dao.getAll()
        return listic.map { list -> list.map { it.toDomain() } }
    }

    override fun getCharacter(id: String): Flow<Character?> {
        return dao.getCharacterId(id).map { it?.toDomain() }
    }
}
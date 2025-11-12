package com.mocalovak.cp.data.repository

import com.mocalovak.cp.data.local.dao.SkillCharacterRefDao
import com.mocalovak.cp.data.local.dao.SkillDao
import com.mocalovak.cp.data.local.entity.SkillCharacterCrossRef
import com.mocalovak.cp.data.local.entity.toDomain
import com.mocalovak.cp.domain.model.Skill
import com.mocalovak.cp.domain.repository.SkillRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SkillRepositoryImpl @Inject constructor(
    private val dao:SkillDao,
    private val refDao: SkillCharacterRefDao
): SkillRepository {
    override fun getSkills(): Flow<List<Skill>> {
        return dao.getAll().map { list -> list.map { it.toDomain() } }
    }

    override fun getCharactersSkills(characterId:Int): Flow<List<Skill>> {
        return dao.getCharactersSkills(characterId).map { list -> list.map { it.toDomain() }}
    }

    override fun addSkillToCharacter(characterId: Int, skillId: String){
        refDao.insertOne(SkillCharacterCrossRef(characterId, skillId))
    }

    override fun deleteSkill(characterId: Int, skillId: String) {
        refDao.deleteSkillCharacterCrossRef(SkillCharacterCrossRef(characterId, skillId))
    }
}
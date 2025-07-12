package com.mocalovak.cp.data.repository

import com.mocalovak.cp.data.local.dao.SkillDao
import com.mocalovak.cp.data.local.entity.toDomain
import com.mocalovak.cp.domain.model.Skill
import com.mocalovak.cp.domain.repository.SkillRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SkillRepositoryImpl @Inject constructor(
    private val dao:SkillDao
): SkillRepository {
    override fun getSkills(): Flow<List<Skill>> {
        return dao.getAll().map { list -> list.map { it.toDomain() } }
    }
}
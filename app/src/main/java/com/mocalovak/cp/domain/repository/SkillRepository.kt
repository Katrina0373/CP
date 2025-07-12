package com.mocalovak.cp.domain.repository

import com.mocalovak.cp.domain.model.Skill
import kotlinx.coroutines.flow.Flow

interface SkillRepository {
    fun getSkills(): Flow<List<Skill>>
}
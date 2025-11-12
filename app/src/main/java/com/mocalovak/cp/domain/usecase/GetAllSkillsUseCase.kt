package com.mocalovak.cp.domain.usecase

import com.mocalovak.cp.domain.model.Skill
import com.mocalovak.cp.domain.repository.SkillRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllSkillsUseCase @Inject constructor(
    private val skillRepository: SkillRepository
) {
    operator fun invoke(): Flow<List<Skill>>{
        return skillRepository.getSkills()
    }
}
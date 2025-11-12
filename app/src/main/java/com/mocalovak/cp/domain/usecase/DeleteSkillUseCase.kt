package com.mocalovak.cp.domain.usecase

import com.mocalovak.cp.domain.repository.SkillRepository
import javax.inject.Inject

class DeleteSkillUseCase @Inject constructor(
    private val skillRepository: SkillRepository
) {
    operator fun invoke(skillId:String, characterId:Int) {
        skillRepository.deleteSkill(skillId = skillId, characterId = characterId)
    }
}
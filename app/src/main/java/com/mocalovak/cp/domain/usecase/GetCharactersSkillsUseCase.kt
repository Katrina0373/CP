package com.mocalovak.cp.domain.usecase

import com.mocalovak.cp.domain.model.Skill
import com.mocalovak.cp.domain.repository.SkillRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersSkillsUseCase @Inject constructor(
    private val repository: SkillRepository
) {

    operator fun invoke(characterId:Int): Flow<List<Skill>> {
        return repository.getCharactersSkills(characterId)
    }
}
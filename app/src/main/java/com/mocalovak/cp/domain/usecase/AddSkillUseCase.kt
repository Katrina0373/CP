package com.mocalovak.cp.domain.usecase

import com.mocalovak.cp.domain.repository.SkillRepository
import javax.inject.Inject

class AddSkillUseCase @Inject constructor(
    private val repo: SkillRepository
){
    operator fun invoke(characterId:Int, skillId:String){
        repo.addSkillToCharacter(characterId, skillId)
    }
}
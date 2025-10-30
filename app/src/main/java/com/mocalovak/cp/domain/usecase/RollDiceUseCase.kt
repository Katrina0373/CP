package com.mocalovak.cp.domain.usecase

import javax.inject.Inject
import kotlin.random.Random

class RollDiceUseCase @Inject constructor() {
    operator fun invoke(diceType: UByte, count: UShort):Int {

        var result = 0
        for (i in 0..count.toInt()) {
            result += Random.nextInt(1, diceType.toInt() + 1)
        }
        return result
    }
}
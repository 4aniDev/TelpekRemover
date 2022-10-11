package ru.chani.telpekremover.domain.usecase

import kotlin.random.Random

class GetRandomPersonPosition {
    operator fun invoke(): Int {
        return Random.nextInt(STARTING_POSITION_OF_PERSON, FINAL_POSITION_OF_PERSON)
    }

    companion object {
        private const val STARTING_POSITION_OF_PERSON = 1
        private const val FINAL_POSITION_OF_PERSON = 9
    }
}
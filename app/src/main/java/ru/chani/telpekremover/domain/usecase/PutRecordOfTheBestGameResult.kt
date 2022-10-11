package ru.chani.telpekremover.domain.usecase

import ru.chani.telpekremover.domain.Repository
import ru.chani.telpekremover.domain.entity.GameResult


class PutRecordOfTheBestGameResult(private val repository: Repository) {
    operator fun invoke(gameResult: GameResult) {
        repository.putRecordOfTheBestScore(gameResult)
    }
}
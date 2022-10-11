package ru.chani.telpekremover.domain.usecase

import ru.chani.telpekremover.domain.Repository
import ru.chani.telpekremover.domain.entity.GameResult


class GetRecordOfTheBestGameResult(private val repository: Repository) {
    operator fun invoke(): GameResult {
        return repository.getRecordOfTerBestScore()
    }
}
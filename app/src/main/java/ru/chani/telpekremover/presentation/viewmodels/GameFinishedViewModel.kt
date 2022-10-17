package ru.chani.telpekremover.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ru.chani.telpekremover.data.RepositoryImp
import ru.chani.telpekremover.domain.usecase.GetRecordOfTheBestGameResult

class GameFinishedViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RepositoryImp(context = application)
    private val getRecordOfTheBestGameResult = GetRecordOfTheBestGameResult(repository)

    fun getBestScore(): String {
        return getRecordOfTheBestGameResult().scoreString
    }

}
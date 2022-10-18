package ru.chani.telpekremover.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ru.chani.telpekremover.data.RepositoryImp
import ru.chani.telpekremover.domain.usecase.GetRecordOfTheBestGameResult

class GameFinishedViewModel(application: Application) : MenuViewModel(application = application)
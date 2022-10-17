package ru.chani.telpekremover.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import ru.chani.telpekremover.databinding.FragmentGameFinishedBinding
import ru.chani.telpekremover.domain.entity.GameResult
import ru.chani.telpekremover.presentation.contract.navigator
import ru.chani.telpekremover.presentation.viewmodels.GameFinishedViewModel

class GameFinishedFragment : Fragment() {

    private lateinit var gameResult: GameResult

    private lateinit var viewModel: GameFinishedViewModel

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
        viewModel = ViewModelProvider(this)[GameFinishedViewModel::class.java]
        viewModel.init()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.gameBestResult.observe(viewLifecycleOwner) {
            binding.tvBestResultValue.text = it.scoreString
        }

        binding.tvYourScoredValue.text = gameResult.scoreString

        setOnClickListeners()
    }

    private fun parseArgs() {
        requireArguments().getParcelable<GameResult>(KEY_GAME_RESULT)?.let {
            gameResult = it
        }
    }

    private fun setOnClickListeners() {
        binding.btPlayAgain.setOnClickListener { navigator().launchGame() }
        binding.btMenu.setOnClickListener { navigator().launchStartMenu() }
    }

    companion object {
        private const val KEY_GAME_RESULT = "KEY_GAME_RESULT"

        fun newInstance(gameResult: GameResult) =
            GameFinishedFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_GAME_RESULT, gameResult)
                }
            }
    }
}
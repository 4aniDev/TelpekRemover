package ru.chani.telpekremover.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import ru.chani.telpekremover.databinding.FragmentStartMenuBinding
import ru.chani.telpekremover.presentation.contract.navigator
import ru.chani.telpekremover.presentation.viewmodels.StartMenuViewModel

class StartMenuFragment : Fragment() {

    private var _binding: FragmentStartMenuBinding? = null
    private val binding: FragmentStartMenuBinding
        get() = _binding ?: throw RuntimeException("FragmentStartMenuBinding == null")

    private lateinit var viewModel: StartMenuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[StartMenuViewModel::class.java]
        viewModel.init()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartMenuBinding.inflate(inflater, container, false)

        viewModel.gameResult.observe(viewLifecycleOwner){
            binding.tvBestResultValue.text = it.scoreString
        }
        binding.btPlay.setOnClickListener { navigator().launchGame() }

        return binding.root
    }

    override fun onPause() {
        viewModel.pauseMusic()
        super.onPause()
    }

    override fun onResume() {
        viewModel.playMusic()
        super.onResume()
    }

    companion object {
        fun newInstance() = StartMenuFragment()
    }
}
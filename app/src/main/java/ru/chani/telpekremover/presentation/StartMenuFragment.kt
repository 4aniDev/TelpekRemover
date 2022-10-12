package ru.chani.telpekremover.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import ru.chani.telpekremover.R
import ru.chani.telpekremover.databinding.FragmentStartMenuBinding
import ru.chani.telpekremover.presentation.contract.navigator

class StartMenuFragment : Fragment() {

    private var _binding: FragmentStartMenuBinding? = null
    private val binding: FragmentStartMenuBinding
        get() = _binding ?: throw RuntimeException("FragmentStartMenuBinding == null")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartMenuBinding.inflate(inflater, container, false)

        binding.btPlay.setOnClickListener { navigator().launchGame() }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    companion object {
        fun newInstance() = StartMenuFragment()
    }
}
package com.example.countries.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy
import com.example.countries.R
import com.example.countries.databinding.FragmentNationsBinding
import com.example.countries.ui.NationsViewModel.Effect
import com.example.countries.usecases.getNationsInteractor
import com.example.countries.withFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class NationsFragment : Fragment(R.layout.fragment_nations) {
    private var _binding: FragmentNationsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NationsViewModel by viewModels {
        withFactory { NationsViewModel(getNationsInteractor()) }
    }

    private val adapter: NationsAdapter by lazy {
        NationsAdapter().apply {
            stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }
    }

    private val layoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentNationsBinding.inflate(inflater).apply {
            nationsList.layoutManager = layoutManager
            nationsList.adapter = adapter
            _binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collect { uiState ->
                        with(binding) {
                            progressBar.visibility =
                                if (uiState.loading) View.VISIBLE else View.GONE
                            adapter.setData(uiState.nations)
                        }
                    }
                }
                launch {
                    viewModel.effects.collect { effect ->
                        when (effect) {
                            is Effect.CompleteMessage -> showMessage("Loading complete")
                            is Effect.ErrorMessage -> showMessage("Error: ${effect.message}")
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun showMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
}

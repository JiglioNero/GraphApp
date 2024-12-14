package com.example.graphapp.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.graphapp.MyApplication
import com.example.graphapp.R
import com.example.graphapp.fragment.viewModel.MainViewModel
import com.example.graphapp.fragment.viewModel.UIState
import com.example.graphapp.fragment.viewModel.factory.MainViewModelFactory
import javax.inject.Inject

class MainFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, this.viewModelFactory)[MainViewModel::class.java]

        val etCount = view.findViewById<EditText>(R.id.etCount)
        val btnGo = view.findViewById<Button>(R.id.btnGo)
        val tvInfo = view.findViewById<TextView>(R.id.tvInfo)

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                UIState.Pending -> {
                    tvInfo.text = resources.getString(R.string.input_points_count)
                }
                UIState.Loading -> {
                    tvInfo.text = resources.getString(R.string.loading)
                }
                is UIState.Success -> {
                    tvInfo.text = ""
                    val action = MainFragmentDirections.actionMainFragmentToResultFragment(state.points.toTypedArray())
                    findNavController().navigate(action)
                    viewModel.clearState()
                }
                is UIState.Error -> {
                    tvInfo.text = "${resources.getString(R.string.error_message)}: ${state.error.message}"
                }
            }
        }

        btnGo.setOnClickListener {
            val countStr = etCount.text.toString()
            val count = countStr.toIntOrNull()
            if (count != null && count > 0) {
                viewModel.loadPoints(count)
            } else {
                Toast.makeText(requireContext(), resources.getString(R.string.input_correct_points_count_msg), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
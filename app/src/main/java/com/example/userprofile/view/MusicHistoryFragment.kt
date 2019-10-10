package com.example.userprofile.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.userprofile.R

class MusicHistoryFragment : Fragment() {

    companion object {
        fun newInstance() = MusicHistoryFragment()
    }

    private lateinit var viewModel: MusicHistoryViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.music_history_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MusicHistoryViewModel::class.java)
        // TODO: Use the ViewModel
    }

}

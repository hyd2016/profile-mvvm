package com.example.userprofile.view

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.userprofile.Interface.OnItemClickListener
import com.example.userprofile.R
import com.example.userprofile.Utils.AppConstants
import com.example.userprofile.Utils.IPCByMessenger
import com.example.userprofile.adapter.MusicHistoryAdapter
import com.example.userprofile.di.DaggerUserProfileComponent
import com.example.userprofile.viewmodel.MusicHistoryViewModel
import kotlinx.android.synthetic.main.music_history_fragment.*
import javax.inject.Inject


/*
*这个fragment和MusicHistoryFragment一毛一样，实际没有任何作用，只是进行
fragment切换
 */

class SearchFragment : Fragment() {

    private var mAdapter: MusicHistoryAdapter? = null

    private var musicHistorryViewModel: MusicHistoryViewModel? = null

    @Inject
    lateinit var iPCByMessenger:IPCByMessenger


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        DaggerUserProfileComponent.create().inject(this)
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        musicHistorryViewModel = ViewModelProviders.of(this).get(MusicHistoryViewModel::class.java)

        musicHistorryViewModel?.queryData(arguments?.getString(AppConstants.START_SEARCH_TITLE))

        initData()
        mAdapter?.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemLongClick(postion: Int) {
                showConfirmDialog(postion)
            }
            override fun onItemClick(view: View?, position: Int) {
                //播放音乐
                iPCByMessenger.sendToService(context, mAdapter?.currentList?.get(position))
            }
        })

        mAdapter?.setViewModel(musicHistorryViewModel)

        Log.d("musicviewmodelId2", musicHistorryViewModel.hashCode().toString())

    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun initData(){

        mAdapter = MusicHistoryAdapter()

        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        music_history_list?.layoutManager = linearLayoutManager
        music_history_list?.adapter = mAdapter
    }

    private fun showConfirmDialog(position: Int) {
        val normalDialog = AlertDialog.Builder(context)

        normalDialog.setMessage(getString(R.string.deleteConfirm))
        normalDialog.setPositiveButton(getString(R.string.yes)) { dialog, which ->

            musicHistorryViewModel?.deleteData(position,mAdapter?.currentList!!.size)
            mAdapter?.setViewModel(musicHistorryViewModel)

        }
        normalDialog.setNegativeButton(getString(R.string.no)) { _, _ -> }

        normalDialog.show()

    }

    override fun onDestroy() {

        //解绑服务，防止内存泄漏
        iPCByMessenger.unBindService()
        super.onDestroy()
    }
}

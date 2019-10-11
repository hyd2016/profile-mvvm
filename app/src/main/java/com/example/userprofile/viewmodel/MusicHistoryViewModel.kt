package com.example.userprofile.viewmodel

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.userprofile.BaseApplication
import com.example.userprofile.Utils.AppConstants
import com.example.userprofile.model.MusicParcelable
import com.example.userprofile.repository.music.MusicHistoryFactory

/**
 * @descriptioon:
 * @author: dinghaoyu
 * @date: 2019-10-05 16:00
 */
class MusicHistoryViewModel : ViewModel() {

    var musicHistoryList : LiveData<PagedList<MusicParcelable>>?=null
    private var musicHistoryFactory:MusicHistoryFactory? = null
    private val config = PagedList.Config.Builder()
            .setPageSize(3)
            .setEnablePlaceholders(false)
            .build()
    var name:String?=null

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    fun queryData(name:String?){
        musicHistoryFactory = MusicHistoryFactory(name,AppConstants.PAGING_NUM)
        musicHistoryList = LivePagedListBuilder(musicHistoryFactory!!,config).build()
        this.name = name
    }

    fun deleteData(position: Int,loadSize:Int){
        val id = musicHistoryList?.value?.get(position)?.id
        if (id != -1) {
            BaseApplication.getContext().contentResolver.delete(Uri.parse(CONTENT_URI), "id = ?", arrayOf(id.toString()))

            musicHistoryFactory = MusicHistoryFactory(name,loadSize)

            musicHistoryList = LivePagedListBuilder(musicHistoryFactory!!,config).build()
        }
    }



    companion object {
        private val CONTENT_URI = "content://com.example.hotsoon.provider/music"
    }
}

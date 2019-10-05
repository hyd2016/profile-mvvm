package com.example.userprofile.view

import android.app.AlertDialog
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.example.userprofile.R
import com.example.userprofile.adapter.MusicHistoryAdapter
import com.example.userprofile.model.MusicParcelable
import com.example.userprofile.viewmodel.MusicHistorryViewModel
import kotlinx.android.synthetic.main.activity_music_history.*
import java.util.*

class MusicHistoryActivity : AppCompatActivity() {

    private val CONTENT_URI = "content://com.example.hotsoon.provider/music"

    private val TAG = "MusicHistoryActivity"

    private val musicParcelableList = ArrayList<MusicParcelable>()

    private var mAdapter: MusicHistoryAdapter? = null

    private var musicHistorryViewModel:MusicHistorryViewModel? = null


    @BindView(R.id.music_history_list)
    internal var recyclerViewHistory: RecyclerView? = null

    @BindView(R.id.music_title_back)
    internal var musicBack: Button? = null

    @BindView(R.id.music_title_search)
    internal var musicSearch: EditText? = null

    @BindView(R.id.music_title_btn)
    internal var btnSearch: Button? = null

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_history)
        ButterKnife.bind(this)
        musicHistorryViewModel = ViewModelProviders.of(this).get(MusicHistorryViewModel::class.java)


        queryDate(null)

        initHistoryList()

    }


    @OnClick(R.id.music_title_back)
    fun setMusicBack() {
        finish()

    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    @OnClick(R.id.music_title_btn)
    fun setMusicSearch() {
        val title = musicSearch!!.text.toString()

        val name = "title like '%$title%'"

        queryDate(name)

    }


    private fun initHistoryList() {
        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        mAdapter = MusicHistoryAdapter(musicParcelableList)
        music_history_list?.layoutManager = linearLayoutManager
        music_history_list?.adapter = mAdapter

        //长按删除。为了重用，未重新定义接口
        mAdapter!!.setOnItemClickListener { view, position -> showConfirmDialog(position) }
    }

    private fun showConfirmDialog(position: Int) {
        val normalDialog = AlertDialog.Builder(this@MusicHistoryActivity)

        normalDialog.setMessage(getString(R.string.deleteConfirm))
        normalDialog.setPositiveButton(getString(R.string.yes)) { dialog, which ->
            val id = musicParcelableList[position].id
            if (id != -1) {
                Toast.makeText(this@MusicHistoryActivity, "长按位置$position", Toast.LENGTH_LONG).show()
                contentResolver.delete(Uri.parse(CONTENT_URI), "id = ?", arrayOf(id.toString()))
                musicParcelableList.removeAt(position)
                mAdapter!!.notifyItemRemoved(position)
                mAdapter!!.notifyDataSetChanged()
            }
        }
        normalDialog.setNegativeButton(getString(R.string.no)) { dialog, which -> }

        normalDialog.show()

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    fun queryDate(name: String?) {
        var cursor: Cursor? = null
        //数据清零
        musicParcelableList.clear()
        try {
            cursor = contentResolver.query(Uri.parse(CONTENT_URI), null, name, null, null, null)
            if (cursor != null) {
                Log.d(TAG, "queryDate: ")
                while (cursor.moveToNext()) {
                    val title = cursor.getString(cursor.getColumnIndex("title"))
                    val url = cursor.getString(cursor.getColumnIndex("url"))
                    val cover = cursor.getString(cursor.getColumnIndex("cover"))
                    val position = cursor.getInt(cursor.getColumnIndex("position"))
                    val id = cursor.getInt(cursor.getColumnIndex("id"))
                    val musicParcelable = MusicParcelable(url, title, cover, position, id)
                    musicParcelableList.add(musicParcelable)
                    Log.d(TAG,title)
                }
                cursor.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        //通知数据更新
        if (mAdapter != null) {
            mAdapter!!.notifyDataSetChanged()

        }

    }

}

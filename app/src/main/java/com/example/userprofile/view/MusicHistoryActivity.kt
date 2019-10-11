package com.example.userprofile.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.userprofile.R
import com.example.userprofile.Utils.AppConstants
import kotlinx.android.synthetic.main.music_history_title.*


class MusicHistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_history)
        val musicHistoryFragment = MusicHistoryFragment()
        supportFragmentManager.beginTransaction()
                .add(R.id.music_activity_fragment,musicHistoryFragment)
                .commit()

        music_title_back.setOnClickListener{ finish() }
        music_title_btn.setOnClickListener {
            val search = music_title_search.text.toString()
            val name = "title like '%$search%'"

            val searchFragment = SearchFragment()
            val args = Bundle()
            args.putString(AppConstants.START_SEARCH_TITLE,name)
            searchFragment.arguments = args
            supportFragmentManager.beginTransaction()
                    .replace(R.id.music_activity_fragment,searchFragment)
                    .commit()
        }
    }
}

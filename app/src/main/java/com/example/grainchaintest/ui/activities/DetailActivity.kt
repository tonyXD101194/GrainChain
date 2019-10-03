package com.example.grainchaintest.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.grainchaintest.R
import com.example.grainchaintest.ui.fragments.DetailFragment

class DetailActivity : AppCompatActivity() {

    private val detailFragment: DetailFragment by lazy {

        DetailFragment.newInstance()
    }

    companion object {

        fun startActivity(context: Context) {

            val listActivityIntent = Intent(context, DetailActivity::class.java)

            context.startActivity(listActivityIntent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.setContentView(R.layout.activity_detail)

        this.setContentFragment(detailFragment)

    }


    private fun setContentFragment(fragment: Fragment) {

        this.supportFragmentManager.commit {
            replace(R.id.activityDetailListContainer,fragment)
        }
    }

}
package com.koroden.test_task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val fragment : Fragment = MainFragment.newInstance()

            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment, "main_fragment")
            transaction.commit()
        }
    }
}
package com.sunfusheng.github.kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var flag: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vAccount.setOnClickListener {
            if (!flag) {
                vAccount.setImageResource(R.drawable.ic_account)
            } else {
                vAccount.setImageResource(R.drawable.ic_account_outline)
            }
            flag = !flag
        }

    }

}

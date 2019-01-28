package com.sunfusheng.github.kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var user: User
    @Inject
    lateinit var repo: Repo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DaggerMainComponent.builder()
            .userModule(UserModule("sunfusheng"))
            .repoModule(RepoModule("MarqueeView", User("fusheng")))
            .build()
            .inject(this);

        user.info()
        repo.info()
    }

}

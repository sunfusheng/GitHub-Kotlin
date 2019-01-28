package com.sunfusheng.github.kotlin

import dagger.Component

/**
 * @author by sunfusheng on 2019/1/28
 */
@Component(modules = arrayOf(RepoModule::class, UserModule::class))
interface MainComponent {

    fun inject(activity: MainActivity)
}
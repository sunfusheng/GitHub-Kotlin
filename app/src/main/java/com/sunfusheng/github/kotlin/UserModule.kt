package com.sunfusheng.github.kotlin

import dagger.Module
import dagger.Provides

/**
 * @author by sunfusheng on 2019/1/28
 */
@Module
class UserModule(private val userName: String) {

    @Provides
    fun userName() = userName

    @Provides
    fun user() = User(userName)
}
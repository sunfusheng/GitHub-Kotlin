package com.sunfusheng.github.kotlin

import dagger.Module
import dagger.Provides

/**
 * @author by sunfusheng on 2019/1/28
 */
@Module
class UserModule(val userName: String) {

    @Provides
    fun userName(): String {
        return userName
    }

    @Provides
    fun user(): User {
        return User(userName)
    }
}
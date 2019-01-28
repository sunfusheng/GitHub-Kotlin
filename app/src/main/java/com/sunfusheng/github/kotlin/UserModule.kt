package com.sunfusheng.github.kotlin

import dagger.Module
import dagger.Provides

/**
 * @author by sunfusheng on 2019/1/28
 */
@Module
class UserModule(var userName: String) {

    @Provides
    fun provideUserName(): String {
        return userName
    }

    @Provides
    fun provideUser(): User {
        return User(userName)
    }
}
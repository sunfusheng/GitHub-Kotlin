package com.sunfusheng.github.kotlin

import dagger.Module
import dagger.Provides

/**
 * @author by sunfusheng on 2019/1/28
 */
@Module
class UserModule {

    @Provides
    fun provideUser(userName: String): User {
        return User(userName)
    }
}
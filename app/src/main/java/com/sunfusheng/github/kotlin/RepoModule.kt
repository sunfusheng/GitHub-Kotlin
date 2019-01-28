package com.sunfusheng.github.kotlin

import dagger.Module
import dagger.Provides

/**
 * @author by sunfusheng on 2019/1/28
 */
@Module(includes = arrayOf(User::class))
class RepoModule(val user: User) {

    @Provides
    fun provideRepo(repoName: String): Repo {
        return Repo(repoName, user)
    }
}
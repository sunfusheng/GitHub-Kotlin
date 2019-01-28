package com.sunfusheng.github.kotlin

import dagger.Module
import dagger.Provides

/**
 * @author by sunfusheng on 2019/1/28
 */
@Module
class RepoModule(var repoName: String, val user: User) {

    @Provides
    fun provideRepoName(): String {
        return repoName
    }

//    @Provides
//    fun provideUser(): User {
//        return user
//    }

    @Provides
    fun provideRepo(): Repo {
        return Repo(repoName, user)
    }
}
package com.sunfusheng.github.kotlin

import dagger.Module
import dagger.Provides

/**
 * @author by sunfusheng on 2019/1/28
 */
@Module
class RepoModule(val repoName: String, val user: User) {

    @Provides
    fun repoName(): String {
        return repoName
    }

    @Provides
    fun repo(): Repo {
        return Repo(repoName, user)
    }
}
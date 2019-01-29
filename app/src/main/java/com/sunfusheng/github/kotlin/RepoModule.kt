package com.sunfusheng.github.kotlin

import dagger.Module
import dagger.Provides

/**
 * @author by sunfusheng on 2019/1/28
 */
@Module(includes = [UserModule::class])
class RepoModule(private val repoName: String) {

    @Provides
    fun repoName() = repoName

    @Provides
    fun repo(user: User) = Repo(repoName, user)
}
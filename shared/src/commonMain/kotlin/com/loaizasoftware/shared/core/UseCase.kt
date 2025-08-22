package com.loaizasoftware.shared.core

abstract class UseCase<out Type, in Params> {

    abstract suspend fun run(params: Params): Type

}
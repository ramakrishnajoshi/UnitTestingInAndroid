package com.example.unittestinginandroid.playground

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        println("main started")

        joinAll(
            async { coroutine(1, 500) },
            async { coroutine(2, 300) },
            async {
                repeat(10) {
                    println("Other task ${it+1} executed")
                    delay(100)
                }
            }
        )


        println("main ended")
    }
}

suspend fun coroutine(number: Int, time: Long) {
    println("Coroutine $number started")
    delay(time)
    println("Coroutine $number ended")
}
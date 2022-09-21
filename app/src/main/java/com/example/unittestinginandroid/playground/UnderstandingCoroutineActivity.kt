package com.example.unittestinginandroid.playground

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.unittestinginandroid.R
import kotlinx.android.synthetic.main.activity_understanding_coroutine.*
import kotlinx.coroutines.*

class UnderstandingCoroutineActivity : AppCompatActivity() {
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_understanding_coroutine)

        buttonLaunchCoroutine.setOnClickListener {
            logInfo("Button Clicked")

            GlobalScope.launch(Dispatchers.IO) {
                logInfo("Coroutine Running in Thread ${Thread.currentThread()}")

                val resultA = function1()
                val resultB = function2()
                logInfo("After Network Call")
                val result = resultA + resultB
                logInfo("result is $result")
            }
            logInfo("Button Click Executed")
        }

        buttonRunBlocking.setOnClickListener {
            logInfo("Button Clicked")
            runBlocking {
                logInfo("Coroutine Running in Thread ${Thread.currentThread()}")
                async {
                    function1()
                }
                async {
                    function2()
                }
                logInfo("Control after calling both functions")
            }
            logInfo("Button Click Executed")
        }
    }

    //One important thumb rule is Suspend function should be called only from a coroutine or
    // another suspend function
    suspend fun function1(): Int {
        logInfo("function1 : Running in Thread ${Thread.currentThread()}")
        logInfo("function1 : Delay function called")
        delay(3000)
        logInfo("function1 : Delay function executed")
        return 10
    }

    suspend fun function2(): Int {
        logInfo("function2 : Running in Thread ${Thread.currentThread()}")
        logInfo("function2 : Delay function called")
        delay(10000)
        logInfo("function2 : Delay function executed")
        return 20
    }

    private fun logInfo(message: String) {
        Log.d(TAG, message)
    }
}
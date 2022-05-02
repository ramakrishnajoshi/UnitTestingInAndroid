package com.example.unittestinginandroid

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TestActivity : AppCompatActivity() {

    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val job = GlobalScope.launch {
            repeat(5) {
                val response = doNetworkCall()
                Log.d(TAG, "response: $response")
            }
        }

        runBlocking {
            job.join()
            Log.d(TAG, "Coroutine Finished")
        }
    }

    private suspend fun doNetworkCall(): String {
        delay(3000)
        return "This is result!"
    }
}

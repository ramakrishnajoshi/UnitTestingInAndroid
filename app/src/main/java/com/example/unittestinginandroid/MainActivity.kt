package com.example.unittestinginandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.room.Room
import com.example.unittestinginandroid.data.local.ShoppingItem
import com.example.unittestinginandroid.data.local.ShoppingItemDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private val TAG = this::class.java.simpleName

    private val roomDatabase by lazy {
        Room.databaseBuilder(this, ShoppingItemDatabase::class.java, "shopping_item_db").build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logInfo("Main Thread : ${Thread.currentThread()}")

        insertButton.setOnClickListener {
            Intent(this@MainActivity, TestActivity::class.java).also {
                startActivity(it)
            }
            //runBlocking {
            insertShoppingItem()
            //}
        }

        roomDatabase.shoppingDao().observeTotalPrice().observe(this@MainActivity, Observer {
            logInfo("Total Price: $it")
        })

        // Every Coroutine need to be started in a Coroutine scope
        // GlobalScope means that this Coroutine will live as long as our application does
        // Of course if Coroutine finishes its job, it will be destroyed and not kept alive until the
        // application dies.
        // GlobalScope launches Coroutine in a new thread. So whatever we write inside CoroutineScope
        // will execute in a async thread.
        // Coroutines are suspendable -> They can be paused and resumed.
        // Coroutines have their own sleep function called delay() but with few differences.
        // sleep() function of Thread class blocks/pauses the thread for particular amount of time but
        // delay() function pauses a particular coroutine for particular amount of time,
        // delay() does not block the thread unlike sleep() function
        GlobalScope.launch(Dispatchers.IO) {
            logInfo("Coroutine Running in Thread ${Thread.currentThread()}")

            insertButton.text = "New thread"

            networkCall()
            logInfo("After Network Call")
        }
    }

    // suspend function can be called for either another suspend function
    // or from a coroutine context
    private suspend fun networkCall() {
        delay(5000)
        logInfo("network call executed in thread : ${Thread.currentThread()}")
    }

    private fun insertShoppingItem() {
        GlobalScope.launch {
            roomDatabase.shoppingDao().insertShoppingItem(
                ShoppingItem(
                    "Banana", 20, 1f, "imageUrl"
                )
            )
        }
    }

    private fun logInfo(message: String) {
        Log.d(TAG, message)
    }

    override fun onDestroy() {
        roomDatabase.close()
        super.onDestroy()
    }
}

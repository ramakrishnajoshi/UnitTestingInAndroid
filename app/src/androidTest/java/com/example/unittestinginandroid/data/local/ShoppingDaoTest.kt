package com.example.unittestinginandroid.data.local

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.unittestinginandroid.util.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class) //JUnit is basically used in JVM Environment,but here we are not in
// such an environment. We want this test class to run on an emulator(Android Environment) as we
// want to use Room version of our emulator/device, rather than local IDE version.
// Note: We can still test SQLite/Room using JUnit but in that case we would be using local IDE
// machine SQLite version instead of emulator/device version.
@SmallTest
class ShoppingDaoTest {

    private lateinit var shoppingDatabase: ShoppingItemDatabase

    private lateinit var shoppingDao: ShoppingDao

    // InstantTaskExecutorRule swaps the background executor used by the Architecture Components
    // with a different one which executes each task synchronously. Needed to test Architecture
    // Components like LiveData
    @get:Rule
    val ss = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        // For Tests, we have to create a temporary database - for that we need to use the
        // inMemoryDatabaseBuilder method. Information stored in an in memory database disappears
        // when the process is killed.

        // allowMainThreadQueries method: Room ensures that Database is never accessed on the main
        // thread because it may lock the main thread and trigger an ANR. So if we try to
        // access Database from main thread then we would get IllegalStateException. For testing
        // we turn this check off using allowMainThreadQueries method which Disables the main
        // thread query check for Room.
        shoppingDatabase = Room
            .inMemoryDatabaseBuilder(context, ShoppingItemDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        shoppingDao = shoppingDatabase.shoppingDao()
    }

    @After
    fun tearDown() {
        shoppingDatabase.close()
    }

    @Test
    fun shouldSaveShoppingItemInDatabaseWhenInsertCommandIsExecuted() {
        // runBlockingTest executes a [testBody] inside an immediate execution dispatcher.
        // This is similar to [runBlocking] but it will immediately progress past delays and into
        // [launch] and [async] blocks.
        // You can use this to write tests that execute in the presence of calls to [delay] without
        // causing your test to take extra time.
        runBlockingTest {

            val shoppingItem = ShoppingItem("name", 10, 5f, "url", 1)

            shoppingDao.insertShoppingItem(shoppingItem)

            // shoppingDao.observeAllShoppingItems() returns LiveData and LiveData runs asynchronously
            // and we do not want that here in our test case. So we need to use getOrAwaitValue()
            val shoppingItemList: List<ShoppingItem> = shoppingDao.observeAllShoppingItems().getOrAwaitValue()

            assertThat(shoppingItemList).contains(shoppingItem)
        }
    }

    @Test
    fun shouldDeleteShoppingItemInDatabaseWhenDeleteQueryIsExecuted() {
        runBlockingTest {
            val shoppingItem1 = ShoppingItem("name", 10, 5f, "url", 1)
            val shoppingItem2 = ShoppingItem("name", 10, 5f, "url", 2)

            shoppingDao.insertShoppingItem(shoppingItem1)
            shoppingDao.insertShoppingItem(shoppingItem2)
            shoppingDao.deleteShoppingItem(shoppingItem1)

            val shoppingItemList: List<ShoppingItem> = shoppingDao.observeAllShoppingItems().getOrAwaitValue()

            assertThat(shoppingItemList).doesNotContain(shoppingItem1)
        }
    }

    @Test
    fun shouldReturnCorrectTotalPrice() {
        runBlockingTest {
            val shoppingItem1 = ShoppingItem("name", 10, 5f, "url", 1)
            val shoppingItem2 = ShoppingItem("name", 10, 15f, "url", 2)

            shoppingDao.insertShoppingItem(shoppingItem1)
            shoppingDao.insertShoppingItem(shoppingItem2)

            val totalPrice: Float = shoppingDao.observeTotalPrice().getOrAwaitValue()

            assertThat(totalPrice).isEqualTo(
                shoppingItem1.price * shoppingItem1.quantity + shoppingItem2.price * shoppingItem2.quantity
            )
        }
    }
}

//FootNotes:
// Test Doubles : A test double is an object which we use in place of a real object during a test.
// Different Types Of Test Doubles are
// 1. Dummy 2. Fake 3. Stubs 4. Mocks
// Fake: Fake objects actually have a complete working implementation in them. But the
// implementation provided in them is some kind of shortcut which helps us in our task of unit
// testing, and this shortcut renders it incapable in production. A great example of this is the
// in-memory database object which we can use just for our testing purposes, while we use the
// real database object in production.
// Its important to note that a single object might act as multiple types of test double at once.
// So, a single object can act as a stub as well as a mock in the same unit test.
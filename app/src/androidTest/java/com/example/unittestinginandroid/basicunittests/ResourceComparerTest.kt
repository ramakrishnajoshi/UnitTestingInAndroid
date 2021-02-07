package com.example.unittestinginandroid.basicunittests

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.unittestinginandroid.R
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class ResourceComparerTest {

    lateinit var resourceComparer: ResourceComparer

    @Before
    fun setUp() {
        resourceComparer = ResourceComparer()
    }

    @After
    fun tearDown() {
        //no need to do anything here as Garbage Collector will clean up resourceComparer automatically
        //but in case if there is DatabaseConection object that that needs to be closed here
    }

    @Test
    fun shouldReturnFalseIfStringsDontMatch() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isEqual(
            context,
            R.string.app_name,
            "SomeRandomString"
        )

        assertThat(result).isFalse()
    }

    @Test
    fun shouldReturnTrueIfStringsMatch() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isEqual(
            context,
            R.string.app_name,
            "UnitTestingInAndroid"
        )

        assertThat(result).isTrue()
    }
}
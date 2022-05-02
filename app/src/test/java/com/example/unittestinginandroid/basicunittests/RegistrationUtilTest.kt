package com.example.unittestinginandroid.basicunittests

import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class RegistrationUtilTest {

    lateinit var sut: RegistrationUtil

    @Before
    fun setUp() {
        sut = RegistrationUtil
    }

    @Test
    fun `empty userName returns false`() {
        val result = sut.validateRegistrationInput(
            "",
            "1234",
            "1234"
        )

        assertThat(result).isFalse()
    }

    @Test
    fun `should return false if userName is taken`() {
        val result = sut.validateRegistrationInput(
            "Ram",
            "1234",
            "1234"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `should return false if password or confirmedPassword is empty`() {
        val result = sut.validateRegistrationInput(
            "Sunil",
            "",
            ""
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `should return false if confirmedPassword is not same as password`() {
        val result = sut.validateRegistrationInput(
            "Sunil",
            "Password@123",
            "Password"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `should return false if password does not contain at least 2 digits`() {
        val result = sut.validateRegistrationInput(
            "Sunil",
            "Password",
            "Password"
        )
        assertThat(result).isFalse()
    }

    @After
    fun tearDown() {
        //no need to do anything here as Garbage Collector will clean up resourceComparer automatically
        //but in case if there is DatabaseConnection object that that needs to be closed here
    }
}
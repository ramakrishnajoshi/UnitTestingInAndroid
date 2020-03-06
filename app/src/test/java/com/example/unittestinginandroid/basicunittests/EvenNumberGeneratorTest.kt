package com.example.unittestinginandroid.basicunittests

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class EvenNumberGeneratorTest {

    lateinit var SUT: EvenNumberGenerator

    /**
     * I want the system under test to be initialized to a clean state before each test.
     * To achieve that I'm going to add below method that sets SUT up and annotate it with @Before annotation.
     * @Before annotation tells JUnit that this method must be run before each single test case.
     * Therefore, I'll have a new instance of system under test for each test.
     */
    @Before
    fun setUp() {
        SUT = EvenNumberGenerator()
    }

    /**
     * Tests in JUnit are methods annotated with @Test annotation.
     * How to write tests? Well, I need to call methods on SUT and make sure that it produces the expected outputs.
     */
    @Test
    fun checkIfNumberIsEven() {
        assertThat(SUT.checkIfNumberIsEven(4), `is`(true))
    }
}
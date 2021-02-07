package com.example.unittestinginandroid.basicunittests

object RegistrationUtil {

    private val existingUsers = listOf<String>("Ram", "Krishna")

    /*
    * input is not valid if...
    * ...the userName is empty
    * ...the userName is already taken
    * ...the password or confirmedPassword is empty
    * ...the confirmedPassword is not same as the real password
    * ...the password does not contain at least 2 digits
    * */
    fun validateRegistrationInput(
        userName: String,
        password: String,
        confirmedPassword: String
    ): Boolean {
        if (userName.isEmpty() || password.isEmpty() || confirmedPassword.isEmpty()) {
            return false
        }
        if (existingUsers.contains(userName)) {
            return false
        }
        if (password != confirmedPassword) {
            return false
        }
        if (password.count { it.isDigit() } < 2) {
            return false
        }
        return true
    }
}
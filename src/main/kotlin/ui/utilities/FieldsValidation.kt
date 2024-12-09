package ui.utilities

import models.BaseValues


object FieldsValidation {

    fun String.isValid(): Boolean {
        return this.isNotEmpty()
    }

    fun String.isValidCode(): Boolean {
        return this.isNotEmpty() && this.length <= 10 && !this.hasBadCommands()
    }

    fun String.isValidLevel(): Boolean {
        return this.isNotEmpty() && this.length < 8 && !this.hasBadCommands()
    }

    fun String.isValidName(): Boolean {
        return this.isNotEmpty() && this.length < 20 && !this.hasBadCommands()
    }

    fun String.isValidPhone(): Boolean {
        if(BaseValues.PhoneRegex.isNotEmpty())
            return BaseValues.PhoneRegex.toRegex().matches(this) && !this.hasBadCommands()
                    && this.length == BaseValues.PhoneSample.length
                    && this.startsWith("+")

        return false
    }

    fun String.isValidEmail(): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        return emailRegex.toRegex().matches(this) && !this.hasBadCommands()
        //return this.isNotEmpty()
    }

    fun String.isValidPassword(): Boolean {
        //Minimum eight and maximum 10 characters, at least one uppercase letter, one lowercase letter,
        //one number and one special character
        val passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,10}$"
        return passwordRegex.toRegex().matches(this) && !hasBadCharacters()
        //return this.isNotEmpty()
    }


    private fun String.hasBadCommands() : Boolean {

        val prohibitedCharacters = listOf("&", "|", ";", ">", "<", "`", "$", "(", ")")
        if(prohibitedCharacters.any{ this.contains(it)}) {
            return true
        }
        val prohibitedCommands = listOf(
            "ls", "cd", "rm", "cat", "sudo", "chmod", "chown", "echo",
            "mv", "cp", "mkdir", "rmdir", "find", "grep", "touch", "head",
            "tail", "tar", "curl", "wget", "kill", "ps", "nc", "&&", "curl",
            "||", "sh", "bash"
        )

        return prohibitedCommands.any {  this.contains(it) }
    }

    fun String.hasBadCharacters() : Boolean {

        val prohibitedCharacters = listOf("&", "|", ";", ">", "<", "`", "$", "(", ")")
        if (prohibitedCharacters.any { this.contains(it) }) {
            return true
        }

        return false
    }
}
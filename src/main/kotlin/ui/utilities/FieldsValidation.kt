package ui.utilities

object FieldsValidation {

    fun String.isValid(): Boolean {
        return this.isNotEmpty()
    }
}
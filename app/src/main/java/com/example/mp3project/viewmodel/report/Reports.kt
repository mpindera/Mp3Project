package com.example.mp3project.viewmodel.report

import android.content.Context
import android.widget.Toast
import com.example.mp3project.R

class Reports (val context: Context){

  /** Login **/
  fun errorFetchFromDatabase() {
    Toast.makeText(context, context.getString(R.string.error_fetching_data), Toast.LENGTH_LONG)
      .show()
  }

  /** Login **/
  fun loggedSuccess(name: String) {
    Toast.makeText(context, "Hello, $name.", Toast.LENGTH_SHORT).show()
  }

  /** Register **/
  fun errorForSavingToDatabase() {
    Toast.makeText(
      context, context.getText(R.string.error_for_saving_to_database), Toast.LENGTH_SHORT
    ).show()
  }

  /** Register **/
  fun savedInDatabase() {
    Toast.makeText(context, context.getText(R.string.saved_in_database), Toast.LENGTH_SHORT)
      .show()
  }

  /** Register **/
  fun registerPerson() {
    Toast.makeText(context, context.getText(R.string.register_person), Toast.LENGTH_SHORT).show()
  }

  /** Register **/
  fun errorRegisterPersonValidation() {
    Toast.makeText(context, context.getText(R.string.error_register_person_validation), Toast.LENGTH_SHORT).show()
  }

  /** Register **/
  fun errorRegisterPerson() {
    Toast.makeText(context, context.getText(R.string.error_register_person), Toast.LENGTH_SHORT)
      .show()
  }


  fun errorEmailDoesNotExists() {
    Toast.makeText(context, context.getString(R.string.email_not_exists), Toast.LENGTH_LONG)
      .show()
  }

  fun errorPasswordInvalid() {
    Toast.makeText(context, context.getString(R.string.error_password_invalid), Toast.LENGTH_LONG)
      .show()
  }

}
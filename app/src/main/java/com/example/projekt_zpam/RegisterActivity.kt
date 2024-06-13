package com.example.projekt_zpam

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.projekt_zpam.firebase.FirestoreClass
import com.example.projekt_zpam.firebase.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class RegisterActivity: BaseActivity() {

    private var registerButton: Button? = null
    private var inputEmail: EditText? = null
    private var inputName: EditText? = null
    private var inputPassword: EditText? = null
    private var inputRepPass: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Inicjalizacja pól wejściowych i przycisku rejestracji
        registerButton = findViewById(R.id.registerButton)
        inputEmail = findViewById(R.id.inputLEmaill)
        inputName = findViewById(R.id.inputName)
        inputPassword = findViewById(R.id.inputPassword2)
        inputRepPass = findViewById(R.id.inputPassword2repeat)

        // Ustawienie nasłuchiwania kliknięć przycisku rejestracji
        registerButton?.setOnClickListener{
            registerUser()
        }
    }


    private fun validateRegisterDetails(): Boolean {
        /**
         * Metoda walidująca wprowadzone dane rejestracji.
         * @return True, jeśli dane są poprawne jeśli nie to False.
         */
        return when{
            TextUtils.isEmpty(inputEmail?.text.toString().trim{ it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email),true)
                false
            }
            TextUtils.isEmpty(inputName?.text.toString().trim{ it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_name),true)
                false
            }
            TextUtils.isEmpty(inputPassword?.text.toString().trim{ it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password),true)
                false
            }
            TextUtils.isEmpty(inputRepPass?.text.toString().trim{ it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_reppassword),true)
                false
            }
            inputPassword?.text.toString().trim {it <= ' '} != inputRepPass?.text.toString().trim{it <= ' '} -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_password_mismatch),true)
                false
            }
            else -> true
        }
    }


    fun goToLogin(view: View) {
        /**
         * Metoda przechodzenia do aktywności logowania.
         */
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }


    private fun registerUser(){
        /**
         * Metoda rejestracji użytkownika za pomocą Firebase Authentication.
         */
        if (validateRegisterDetails()){
            val login: String = inputEmail?.text.toString().trim() {it <= ' '}
            val password: String = inputPassword?.text.toString().trim() {it <= ' '}
            val name: String = inputName?.text.toString().trim() {it <= ' '}

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(login,password).addOnCompleteListener(
                OnCompleteListener <AuthResult>{ task ->
                    if(task.isSuccessful){
                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        showErrorSnackBar("You are registered successfully." +
                                " Your user id is ${firebaseUser.uid}",false)

                        val user = User(firebaseUser.uid,
                            name,
                            true,
                            login,
                        )
                        FirestoreClass().registerUserFS(this@RegisterActivity, user)

                        FirebaseAuth.getInstance().signOut()
                        finish()

                    } else{
                        showErrorSnackBar(task.exception!!.message.toString(),true)
                    }
                }
            )
        }
    }

    fun  userRegistrationSuccess(){
        /**
         * Metoda wywoływana po udanej rejestracji użytkownika wyświetlająca wiadomość Toast.
         */
        Toast.makeText(this@RegisterActivity,
            resources.getString(R.string.register_success), Toast.LENGTH_LONG).show()
    }
}


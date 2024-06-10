package com.example.projekt_zpam.firebase

    import com.example.projekt_zpam.RegisterActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

open class FirestoreClass {

        private val mFireStore = FirebaseFirestore.getInstance()

        fun registerUserFS(activity: RegisterActivity, userInfo: User){
            /**
             * Rejestruje użytkownika w kolekcji users
             */
            mFireStore.collection("users")
                .document(userInfo.id)
                .set(userInfo, SetOptions.merge())
                .addOnSuccessListener {
                    activity.userRegistrationSuccess()
                }
                .addOnFailureListener{

                }

        }

    }
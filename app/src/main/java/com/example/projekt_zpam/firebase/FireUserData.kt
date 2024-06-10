package com.example.projekt_zpam.firebase

import com.google.firebase.firestore.PropertyName

data class FireUserData (@get:PropertyName("name") @set:PropertyName("name") var name: String = "",
                         @get:PropertyName("surname") @set:PropertyName("surname") var surname: String = "",
                         @get:PropertyName("age") @set:PropertyName("age") var age: Int = 0,
                         @get:PropertyName("studentId") @set:PropertyName("studentId") var studentId: String = "",
)
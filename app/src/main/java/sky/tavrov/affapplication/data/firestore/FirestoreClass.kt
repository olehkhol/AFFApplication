package sky.tavrov.affapplication.data.firestore

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import sky.tavrov.affapplication.data.models.User

class FirestoreClass {

    private val firestore = FirebaseFirestore.getInstance()

    fun registerUser(userInfo: User, onSuccess: () -> Unit, onFailure: () -> Unit) {
        firestore.collection("users")
            .add(userInfo)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure()
                Log.e("FirestoreClass", "Error while registering the user.", it)
            }
    }
}
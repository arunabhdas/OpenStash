package app.niftyapp.mobile.android.niftyapp.firestore

import android.util.Log
import app.niftyapp.mobile.android.niftyapp.activities.LoginActivity
import app.niftyapp.mobile.android.niftyapp.activities.RegisterActivity
import app.niftyapp.mobile.android.niftyapp.models.User
import app.niftyapp.mobile.android.niftyapp.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

/**
 * Created by Das on 4/27/21.
 */
/**
 * A custom class where we will add the operation performed for the FireStore database.
 */
class FirestoreUtility {
    // Access a Cloud Firestore instance.
    private val mFireStore = FirebaseFirestore.getInstance()
    /**
     * A function to make an entry of the registered user in the FireStore database.
     */
    fun registerUser(activity: RegisterActivity, userInfo: User) {

        // The "users" is collection name. If the collection is already created then it will not create the same one again.
        mFireStore.collection(Constants.USERS)
                // Document ID for users fields. Here the document it is the User ID.
                .document(userInfo.id)
                // Here the userInfo are Field and the SetOption is set to merge. It is for if we wants to merge later on instead of replacing the fields.
                .set(userInfo, SetOptions.merge())
                .addOnSuccessListener {

                    // Here call a function of base activity for transferring the result to it.
                    activity.userRegistrationSuccess()
                }
                .addOnFailureListener { e ->
                    activity.hideProgressDialog()
                    Log.e(
                            activity.javaClass.simpleName,
                            "Error while registering the user.", e
                    )
                }
    }

    fun getCurrentUserId(): String {
        // An instance of currentUser using FirebaseAuth
        val currentUser = FirebaseAuth.getInstance().currentUser

        // A variable to assign the currentUserId if is not null else it will be blank
        var currentUserId = ""
        if (currentUser != null) {
            currentUserId = currentUser.uid
        }
        return currentUserId
    }

    /**
     * A function to get the logged user details from from FireStore Database.
     */
    fun getUserDetails() {

        // Here we pass the collection name from which we wants the data.
        mFireStore.collection(Constants.USERS)
                // The document id to get the Fields of user.
                .document(getCurrentUserId())
                .get()
                .addOnSuccessListener { document ->

                    Log.i(this.javaClass.simpleName, document.toString())

                    // Here we have received the document snapshot which is converted into the User Data model object.
                    val user = document.toObject(User::class.java)!!
                    Log.i(this.javaClass.simpleName, user.firstName)
                    Log.i(this.javaClass.simpleName, user.lastName)
                    Log.i(this.javaClass.simpleName, user.email)

                }
                .addOnFailureListener { e ->
                    // Hide the progress dialog if there is any error. And print the error in log.
                    Log.e(
                            this.javaClass.simpleName,
                            "Error while getting user details.",
                            e
                    )
                }

    }
}
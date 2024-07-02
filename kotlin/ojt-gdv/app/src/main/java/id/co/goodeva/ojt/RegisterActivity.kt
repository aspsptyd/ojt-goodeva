package id.co.goodeva.ojt

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth
import id.co.goodeva.ojt.databinding.ActivityRegisterBinding


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var progressDialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        progressDialog = initView(this, this)
        initView(this,this)
        auth = Firebase.auth
        binding.tvSignin.setOnClickListener{
            finish()
        }
        binding.button.setOnClickListener {
            if (binding.edtEmail.text.isNullOrEmpty() || binding.edtPassword.text.isNullOrEmpty()){
                Snackbar.make(binding.root, "Silahkan isi field yang masih kosong", Snackbar.LENGTH_SHORT).show()
            }else{
                val email = binding.edtEmail.text.toString()
                val password = binding.edtPassword.text.toString()
                val name = binding.edtName.text.toString()
                register(email,name,password)
            }
        }
    }
    private fun register(email: String, name: String,password:String){
        progressDialog.show()
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful && task.result!=null) {
                    // Sign in success, update UI with the signed-in user's information
                    progressDialog.dismiss()
                    val user = Firebase.auth.currentUser
                    if(user!=null){
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build()

                        user.updateProfile(profileUpdates)
                            .addOnCompleteListener(OnCompleteListener<Void?> { task ->
                                if (task.isSuccessful) {
                                    Log.d("RESPONSE", "User profile updated.")
                                    Log.d("RESPONSE", "createUserWithEmail:success")
                                    startActivity(Intent(this,MainActivity::class.java))
                                }
                            })
                    }else{
                        Snackbar.make(binding.root, "Register Failed", Snackbar.LENGTH_SHORT).show()
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    progressDialog.dismiss()
                    Log.w("RESPONSE", "createUserWithEmail:failure", task.exception)
                    Snackbar.make(binding.root, "Authentication Failed", Snackbar.LENGTH_SHORT).show()
                }
            }
    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}
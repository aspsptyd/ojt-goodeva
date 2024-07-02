package id.co.goodeva.ojt

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import id.co.goodeva.ojt.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var progressDialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView(this,this)
        auth = Firebase.auth
        binding.tvSignup.setOnClickListener{
            startActivity(Intent(this,RegisterActivity::class.java))
        }
        binding.button.setOnClickListener {
            if (binding.edtEmail.text.isNullOrEmpty() || binding.edtPassword.text.isNullOrEmpty()){
                Snackbar.make(binding.root, "Silahkan isi field yang masih kosong", Snackbar.LENGTH_SHORT).show()
            }else{
                val email = binding.edtEmail.text.toString()
                val password = binding.edtPassword.text.toString()
                login(email,password)
            }
        }
    }
    private fun login(email:String,password:String){
        progressDialog.show()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    progressDialog.dismiss()
                    Log.d("RESPONSE", "signInWithEmail:success")
                    startActivity(Intent(this,MainActivity::class.java))
                } else {
                    // If sign in fails, display a message to the user.
                    progressDialog.dismiss()
                    Log.w("RESPONSE", "signInWithEmail:failure", task.exception)
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
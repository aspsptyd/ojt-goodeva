package id.co.goodeva.ojt

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.co.goodeva.ojt.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvSignup.setOnClickListener{
            startActivity(Intent(this,RegisterActivity::class.java))
        }
        binding.button.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}
package id.co.goodeva.ojt.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import id.co.goodeva.ojt.R
import id.co.goodeva.ojt.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        auth = Firebase.auth
        val user = auth.currentUser
        if (user!=null){
            binding.tvName.text = "Welcome, ${user.displayName}! \uD83D\uDE03"
        }


        return binding.root
    }
}
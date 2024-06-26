package eu.nexanet.tictactoe.activity

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import eu.nexanet.tictactoe.R

class SignInActivity : AppCompatActivity() {

    private lateinit var btnSignUp: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        init()
    }

    private fun init() {
        btnSignUp = findViewById(R.id.btnSignUp)

        btnSignUp.setOnClickListener { btnSignUpAction() }
    }

    private fun btnSignUpAction() {
        showSignUp()
    }

    private fun showSignUp() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }
}
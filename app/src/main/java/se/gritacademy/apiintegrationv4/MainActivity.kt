package se.gritacademy.apiintegrationv4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

open class MainActivity : AppCompatActivity() {

    var loggedOn: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        var btnLogIn: Button = findViewById(R.id.button)
        val txtUser: EditText = findViewById<EditText?>(R.id.editTextText)
        var txtPassword: EditText = findViewById<EditText>(R.id.editTextTextPassword)

        btnLogIn.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            if (txtUser.text.toString() == "admin" && txtPassword.text.toString() == "password") {
                startActivity(intent)
                loggedOn = true
                Log.i("Andreas", "Inloggad = " + loggedOn)
            } else {
                Toast.makeText(this, "FEL VID INLOGG", Toast.LENGTH_SHORT).show()

            }

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}


/* backstack
fragmentManager.beginTransaction().replace(R.id.frame, BlankFragment1(),"fragment1")
.addToBackStack("1")
.commit()
*/

//logga varje backstack
/*override fun onBackPressed() {
    Log.i("alrik",fragmentManager.backStackEntryCount.toString())
}*/



//supportFragmentManager.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
//ska ligga på på logga ut knappen sen för att rensa backstack
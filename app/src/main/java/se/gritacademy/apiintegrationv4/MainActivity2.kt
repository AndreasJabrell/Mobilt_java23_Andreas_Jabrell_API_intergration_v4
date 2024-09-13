package se.gritacademy.apiintegrationv4

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.core.app.NotificationCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

class MainActivity2 : MainActivity() {

    val navHostFragment =
        supportFragmentManager

    private val CHANNEL_ID = "channel_Andreas"

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logOut -> {

                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish() // Stänger nuvarande aktivitet
                return true
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

            //building the notification
        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("notifikation")
            .setContentText("du pillar för mycket i appen")
            .setPriority(NotificationCompat.PRIORITY_HIGH)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "general notifications"
            val descriptionText = "test notifikation"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system.
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            Log.d("Notification", "Notification channel created")

            notificationManager.notify(1, builder.build())//push the notification

            val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
            setSupportActionBar(toolbar)//menu

            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            //navController = navHostFragment.navController

                //making sure fragments is not null
            if (navHostFragment != null) {
                navController = navHostFragment.navController
            } else {
                Log.e("Andreas", "NavHostFragment är null")
            }


                //button to hop between fragments + sends notification everytime
            findViewById<Button>(R.id.button2).setOnClickListener {
                Log.i("Andreas", "onCreate: " + navController.currentDestination!!.toString())
                notificationManager.notify(1, builder.build())
                Log.i(
                    "Andreas",
                    "onCreate: " + navController.currentDestination!!.toString() + navController.currentDestination!!.id
                )
                if (navController.currentDestination!!.id == R.id.blankFragment2)
                    navController.navigate(R.id.action_blankFragment2_to_blankFragment3)
                else
                    navController.navigate(R.id.action_blankFragment_to_blankFragment22)

            }


            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }
    }
}
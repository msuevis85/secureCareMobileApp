package com.project.centennial.securecaremobileapp.view.shared

import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.project.centennial.securecaremobileapp.R
import com.project.centennial.securecaremobileapp.utils.SharedPreferencesHelper
import com.project.centennial.securecaremobileapp.utils.UserType
import com.project.centennial.securecaremobileapp.view.user.LoginActivity
import com.project.centennial.securecaremobileapp.view.MainActivity
import com.project.centennial.securecaremobileapp.view.patient.BookSpecialistAppointmentActivity
import com.project.centennial.securecaremobileapp.view.patient.MedicalHistoryActivity
import com.project.centennial.securecaremobileapp.view.patient.PatientRegisterActivity
import com.project.centennial.securecaremobileapp.view.specialist.SpecialistRegisterActivity
import com.project.centennial.securecaremobileapp.view.patient.PatientProfileActivity
import com.project.centennial.securecaremobileapp.view.patient.RequestAppointmentActivity
import com.project.centennial.securecaremobileapp.view.specialist.CheckingWaitingAppointmentsActivity
import com.project.centennial.securecaremobileapp.view.specialist.SpecialistProfileActivity


open class DrawerBaseActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private var token: String = ""
    private var usertypeid: Int = -1
    private lateinit var user: Map<String, Any?>

    private lateinit var includeLayout: ConstraintLayout
    private lateinit var groupHeaderAuthorization: LinearLayout
    private lateinit var usernameHeaderText: TextView

    override fun setContentView(view: View?) {
        drawerLayout = LayoutInflater.from(this).inflate(R.layout.activity_drawer_base, null) as DrawerLayout
        var container: FrameLayout = drawerLayout.findViewById(R.id.activity_container)
        container.addView(view)
        super.setContentView(drawerLayout)

        sharedPreferencesHelper = SharedPreferencesHelper(this)



        val toolbar= findViewById<Toolbar>(R.id.toolbar_2)
        setSupportActionBar(toolbar)

        val navigationView = findViewById<NavigationView>(R.id.nav_view_2)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.nav_open,
            R.string.nav_close
        )
        toggle.isDrawerIndicatorEnabled = false
        drawerLayout.addDrawerListener(toggle)
        var drawable: Drawable? = ResourcesCompat.getDrawable(resources,R.drawable.icons_menu, theme)
        toggle.setHomeAsUpIndicator(drawable)
        toggle.setToolbarNavigationClickListener {
            if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        }
        toggle.syncState()

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(
                view, "Replace with your own action",
                Snackbar.LENGTH_LONG
            )
                .setAction("Action", null).show()
        }

        setMenuOptions()
    }




    private fun setMenuOptions(){
        // find nav menu
        val navMenu = drawerLayout.findViewById<NavigationView>(R.id.nav_view_2)

        // find content-layout, header_username
        includeLayout = drawerLayout.findViewById<ConstraintLayout>(R.id.content_layout)
        groupHeaderAuthorization = includeLayout.findViewById<LinearLayout>(R.id.group_header_authorization)
        usernameHeaderText = includeLayout.findViewById<TextView>(R.id.header_username)
        var registerText = includeLayout.findViewById<TextView>(R.id.header_register_textview)
        var loginText = includeLayout.findViewById<TextView>(R.id.header_login_textview)
        var logo_img = includeLayout.findViewById<ImageView>(R.id.header_logo)

        logo_img.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

        // find drawer header
        val headerView = navMenu.getHeaderView(0)
        val textView = headerView.findViewById<TextView>(R.id.main_drawer_header_username)

        user = sharedPreferencesHelper.getUserInfo()
        token = sharedPreferencesHelper.getUserToken().toString()
        //Toast.makeText(this, "Drawer Base: $token", Toast.LENGTH_LONG).show()
        //Toast.makeText(this, "Drawer Base: $userStr", Toast.LENGTH_LONG).show()
        //Log.d("MAIN PAGE: ","hello token $token ")
       if(token != "" && user != null){
            // already login
            groupHeaderAuthorization.visibility = View.GONE
            usernameHeaderText.visibility = View.VISIBLE
            navMenu.menu.removeGroup(R.id.group_nav_authorization)

            if(user != null){
                usernameHeaderText.text = "${user!!["firstname"]} ${user!!["lastname"]}"
                textView.text = "${user!!["firstname"]} ${user!!["lastname"]}"
                usertypeid = (user!!["usertypeid"] as Double).toInt()



                if(usertypeid == UserType.Specialist.id){
                    // specialist
                    navMenu.menu.removeItem(R.id.nav_medical_history)
                    navMenu.menu.removeItem(R.id.nav_user_appointment)
                    navMenu.menu.removeItem(R.id.group_nav_patient_features)
                } else {
                    navMenu.menu.removeItem(R.id.group_nav_specialist_features)

                }
            }

        } else {
            // not login/register yet
           groupHeaderAuthorization.visibility = View.VISIBLE
           usernameHeaderText.visibility = View.GONE
            navMenu.menu.findItem(R.id.group_nav_logout)?.isVisible = false
            navMenu.menu.removeGroup(R.id.group_nav_login_user)
           navMenu.menu.removeItem(R.id.group_nav_specialist_features)
           registerText.setOnClickListener{
               true
               showPopupMenu(registerText)

           }
           loginText.setOnClickListener {

               startActivity(Intent(this, LoginActivity::class.java))

           }
        }


    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.

        drawerLayout.closeDrawer(GravityCompat.START)
        when (item.itemId) {
            R.id.nav_register_patient -> {
                startActivity(Intent(this, PatientRegisterActivity::class.java))
            }

            R.id.nav_register_specialist -> {
                startActivity(Intent(this, SpecialistRegisterActivity::class.java))

            }
            R.id.nav_login -> {
                //Toast.makeText(this,"Login", Toast.LENGTH_LONG).show()

                startActivity(Intent(this, LoginActivity::class.java))

            }
            R.id.nav_logout -> {
                sharedPreferencesHelper.clearUserLogin();
                startActivity(Intent(this, MainActivity::class.java))
            }

            R.id.nav_profile -> {
                if(usertypeid == UserType.Patient.id)
                    startActivity(Intent(this, PatientProfileActivity::class.java))
                else if(usertypeid == UserType.Specialist.id)
                    startActivity(Intent(this, SpecialistProfileActivity::class.java))

            }

            R.id.nav_meet_specialist -> {
                startActivity(Intent(this, BookSpecialistAppointmentActivity::class.java))
            }

            R.id.nav_request_appointment -> {
                startActivity(Intent(this, RequestAppointmentActivity::class.java))
            }

            R.id.nav_medical_history -> {
                startActivity(Intent(this, MedicalHistoryActivity::class.java))
            }

            R.id.nav_specialist_waiting_room -> {
                startActivity(Intent(this, CheckingWaitingAppointmentsActivity::class.java))

            }
        }
        return true
    }

    private fun showPopupMenu(anchor: View) {

        val popupMenu = PopupMenu(this, anchor)
        popupMenu.inflate(R.menu.register_popup_menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            drawerLayout.closeDrawer(GravityCompat.START)
            when (menuItem.itemId) {
                R.id.nav_popup_patient -> {
                    // Handle popup item 1 click
                    startActivity(Intent(this, PatientRegisterActivity::class.java))
                    true
                }
                R.id.nav_popup_specialist -> {
                    // Handle popup item 2 click
                    startActivity(Intent(this, SpecialistRegisterActivity::class.java))
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }


    protected fun allocateActivityTitle(titleString: String){
        supportActionBar?.apply {
            title = titleString
        }
    }

    protected fun hideGroupHeaderAuthorization(){

        groupHeaderAuthorization.visibility = View.GONE
        usernameHeaderText.visibility = View.VISIBLE
        usernameHeaderText.text = "Welcome"
    }

}

package com.oky2abbas.sms2html.main

import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.widget.ViewFlipper
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.oky2abbas.sms2html.R
import com.oky2abbas.sms2html.extention.isPermission
import kotlinx.android.synthetic.main.main_nav.*
import kotlinx.android.synthetic.main.main_view.*

class MainView : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {
    private var actionBarDrawerToggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_view)

        initView()
    }

    private fun initView() {
        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        if (actionBarDrawerToggle == null)
            actionBarDrawerToggle = ActionBarDrawerToggle(
                this, drawer_layout, mToolbar,
                R.string.strName, R.string.strName
            )

        configView()
    }

    private fun configView() {
        this.isPermission()

        drawer_layout.addDrawerListener(actionBarDrawerToggle!!)
        actionBarDrawerToggle?.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        nav_view.bringToFront()

        bottom_nav.selectedItemId = R.id.menu_backup
        bottom_nav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_backup -> flipper_main.goTo(0)
                R.id.menu_list -> flipper_main.goTo(1)
                R.id.menu_about -> flipper_main.goTo(2)
            }

            return@setOnNavigationItemSelectedListener true
        }

        txt_menu_backup.setOnClickListener { flipper_main.goTo(0) }
        txt_menu_list.setOnClickListener { flipper_main.goTo(1) }
        txt_menu_about.setOnClickListener { flipper_main.goTo(2) }
    }

    fun ViewFlipper.goTo(index: Int) {
        displayedChild = index

        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == android.view.KeyEvent.KEYCODE_MENU) {
            if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                drawer_layout.closeDrawer(GravityCompat.START)
            } else {
                drawer_layout.openDrawer(GravityCompat.START)
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}
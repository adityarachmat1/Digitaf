package com.drife.digitaf

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import com.drife.digitaf.Module.Login.Activity.LoginActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class LoginActivityTest {
    @get:Rule
    val loginActivityTest: ActivityTestRule<LoginActivity> = ActivityTestRule(LoginActivity::class.java)

    @Test
    fun login() {
        Espresso.onView(ViewMatchers.withId(R.id.etUsername))
                .perform(ViewActions.typeText("yudha.satryawan@taf.co.id"))

        Espresso.onView(ViewMatchers.withId(R.id.etPassword))
                .perform(ViewActions.typeText("Ys@123456"))

        Espresso.onView(ViewMatchers.withId(R.id.bt_login))
                .perform(ViewActions.click())
    }
}
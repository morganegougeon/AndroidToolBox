package fr.isen.gougeon.androidtoolbox

import android.app.Activity
import junit.framework.Assert.assertEquals
import org.junit.Test

class TestAge {

    @Test
    fun testYear() {

        val testActivity = FormActivity()
        val age = testActivity.getAge(2000, 1, 1)
        assertEquals(20, age)
    }

    @Test
    fun testMonth(){
        val testActivity = FormActivity()
        val age = testActivity.getAge(2000, 3,1)
        assertEquals(19, age)
    }

    @Test
    fun testDay(){
        val testActivity = FormActivity()
        val age = testActivity.getAge(2000, 3,30)
        assertEquals(19, age)
    }
}


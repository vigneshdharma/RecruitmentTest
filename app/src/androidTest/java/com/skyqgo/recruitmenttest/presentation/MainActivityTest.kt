package com.skyqgo.recruitmenttest.presentation

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.skyqgo.recruitmenttest.R
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.endsWith
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        val textView = onView(
            allOf(
                withText("SkyQGo"),
                withParent(
                    allOf(
                        withId(androidx.appcompat.R.id.action_bar),
                        withParent(withId(androidx.appcompat.R.id.action_bar_container))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("SkyQGo")))

        val viewGroup = onView(
            allOf(
                withId(R.id.fragment_container),
                withParent(
                    allOf(
                        withId(android.R.id.content),
                        withParent(withId(androidx.appcompat.R.id.decor_content_parent))
                    )
                ),
                isDisplayed()
            )
        )
        viewGroup.check(matches(isDisplayed()))

        val textView2 = onView(
            allOf(
                withId(R.id.companyLogo), withText("SkyQGo"),
                withParent(withParent(withId(R.id.nav_host_fragment))),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("SkyQGo")))

        val editText = onView(
            allOf(
                withId(R.id.searchMovie), withText(endsWith("Search Movies")),
                withParent(withParent(withId(R.id.nav_host_fragment))),
                isDisplayed()
            )
        )
        editText.check(matches(withText(endsWith("Search Movies"))))

        val recyclerView = onView(
            allOf(
                withId(R.id.movieList),
                withParent(withParent(withId(R.id.nav_host_fragment))),
                isDisplayed()
            )
        )
        recyclerView.check(matches(isDisplayed()))

        val imageView = onView(
            allOf(
                withId(R.id.poster), ViewMatchers.withContentDescription("SkyQGo"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))

        val genretextView = onView(
            allOf(
                withId(R.id.genre), withText("ACTION"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))),
                isDisplayed()
            )
        )
        genretextView.check(matches(withText("ACTION")))

        val textView3 = onView(
            allOf(
                withId(R.id.genre), withText("ACTION"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("ACTION")))
    }
}

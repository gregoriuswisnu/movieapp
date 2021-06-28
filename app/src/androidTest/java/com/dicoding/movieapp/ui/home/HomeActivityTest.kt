package com.dicoding.movieapp.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.dicoding.movieapp.R
import com.dicoding.movieapp.utils.DataDummy
import com.dicoding.movieapp.utils.EspressoIdlingResources
import org.junit.After
import org.junit.Before
import org.junit.Test

@Suppress("DEPRECATION")
class   HomeActivityTest{

    private val dummyMovie = DataDummy.generateDummyMoviews()
    private val dummySeries = DataDummy.generateDummySeries()

    @Before
    fun setUp(){
        ActivityScenario.launch(HomeActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResources.idlingResource)
    }

    @After
    fun tearDown(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResources.idlingResource)
    }
    @Test
    fun loadMovies(){
        delayTwoSecond()
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMovie.size))
    }

    @Test
    fun loadDetailMovies(){
        delayTwoSecond()
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,click()))
        delayTwoSecond()
        onView(withId(R.id.text_title_desc)).check(matches(isDisplayed()))
        onView(withId(R.id.text_title_desc)).check(matches(withText(dummyMovie[0].title)))
        onView(withId(R.id.text_year_desc)).check(matches(isDisplayed()))
        onView(withId(R.id.text_year_desc)).check(matches(withText(dummyMovie[0].year)))
        onView(withId(R.id.text_cast_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.text_cast_detail)).check(matches(withText(dummyMovie[0].cast)))
        onView(withId(R.id.text_description)).check(matches(isDisplayed()))
        onView(withId(R.id.text_description)).check(matches(withText(dummyMovie[0].description)))
        onView(withId(R.id.image_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.text_genre_desc)).check(matches(isDisplayed()))
        onView(withId(R.id.text_genre_desc)).check(matches(withText(dummyMovie[0].genre)))
    }

    @Test
    fun loadSeries(){
        onView(withText("TV Series")).perform(click())
        delayTwoSecond()
        onView(withId(R.id.rv_series)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_series)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummySeries.size))
    }



    @Test
    fun loadDetailSeries(){
        onView(withText("TV Series")).perform(click())
        delayTwoSecond()
        onView(withId(R.id.rv_series)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,click()))
        delayTwoSecond()
        onView(withId(R.id.text_title_desc)).check(matches(isDisplayed()))
        onView(withId(R.id.text_title_desc)).check(matches(withText(dummySeries[0].title)))
        onView(withId(R.id.text_year_desc)).check(matches(isDisplayed()))
        onView(withId(R.id.text_year_desc)).check(matches(withText(dummySeries[0].year)))
        onView(withId(R.id.text_cast_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.text_cast_detail)).check(matches(withText(dummySeries[0].cast)))
        onView(withId(R.id.text_description)).check(matches(isDisplayed()))
        onView(withId(R.id.text_description)).check(matches(withText(dummySeries[0].description)))
        onView(withId(R.id.image_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.text_genre_desc)).check(matches(isDisplayed()))
        onView(withId(R.id.text_genre_desc)).check(matches(withText(dummySeries[0].genre)))

    }

    @Test
    fun insertUpdateFavMovieAndSeries(){
        delayTwoSecond()
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        delayTwoSecond()
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        delayTwoSecond()
        onView(withId(R.id.fab_favorite)).perform(click())
        delayTwoSecond()
        pressBack()
        delayTwoSecond()
        onView(withText("TV Series")).perform(click())
        delayTwoSecond()
        onView(withId(R.id.rv_series)).check(matches(isDisplayed()))
        delayTwoSecond()
        onView(withId(R.id.rv_series)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        delayTwoSecond()
        onView(withId(R.id.fab_favorite)).perform(click())
        delayTwoSecond()
        pressBack()
        delayTwoSecond()
        onView(withId(R.id.favorite_list)).perform(click())
        delayTwoSecond()
        onView(withId(R.id.rv_fav_movie)).check(matches(isDisplayed()))
        delayTwoSecond()
        onView(withText("TV Series")).perform(click())
        onView(withId(R.id.rv_fav_serie)).check(matches(isDisplayed()))

    }

    private fun delayTwoSecond(){
        try {
            Thread.sleep(2000)
        }catch (e: InterruptedException){
            e.printStackTrace()
        }
    }
}
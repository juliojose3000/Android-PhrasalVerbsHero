package com.loaizasoftware.phrasalverbshero.presentation.ui.verbs


import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavType
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.loaizasoftware.shared.core.None
import com.loaizasoftware.phrasalverbshero.data.api.ApiService
import com.loaizasoftware.phrasalverbshero.data.repository.VerbRepositoryImpl
import com.loaizasoftware.phrasalverbshero.domain.model.Definition
import com.loaizasoftware.phrasalverbshero.domain.model.PhrasalVerb
import com.loaizasoftware.phrasalverbshero.domain.model.Question
import com.loaizasoftware.phrasalverbshero.domain.model.Verb
import com.loaizasoftware.phrasalverbshero.domain.usecase.GetPrepsAdverbsUseCase
import com.loaizasoftware.phrasalverbshero.domain.usecase.GetVerbsUseCase
import com.loaizasoftware.phrasalverbshero.presentation.ui.screens.MainScreen
import com.loaizasoftware.phrasalverbshero.presentation.viewmodel.MainViewModel
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Call


@RunWith(AndroidJUnit4::class)
class VerbsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var fakeViewModel: FakeMainViewModel

    private lateinit var navController: TestNavHostController

    @Before
    fun setup() {
        fakeViewModel = FakeMainViewModel()
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.navigatorProvider.addNavigator(ComposeNavigator())
    }

    @Test
    fun verbs_are_displayed_correctly() {
        composeTestRule.setContent {
            navController.setLifecycleOwner(LocalLifecycleOwner.current)
            MainScreen(viewModel = fakeViewModel, navController = navController)
        }

        composeTestRule.onAllNodesWithText("Out").assertCountEquals(1)
        composeTestRule.onAllNodesWithText("In").assertCountEquals(1)
    }

    @Test
    fun navigate_to_phrasal_verbs_screen() {
        composeTestRule.setContent {
            navController.setLifecycleOwner(LocalLifecycleOwner.current)

            NavHost(navController = navController, startDestination = "verbs_screen") {
                composable("verbs_screen") {
                    MainScreen(viewModel = fakeViewModel, navController = navController)
                }
                composable(
                    "phrasal_verbs/{phrasalVerbPart}",
                    arguments = listOf(navArgument("phrasalVerbPart") { type = NavType.StringType })
                ) {
                    // No-op fake destination
                }
            }
        }

        composeTestRule.onNodeWithText("Out").performClick()
        composeTestRule.waitForIdle()

        // ✅ Fix: assert the destination pattern AND check actual value passed
        assertEquals("phrasal_verbs/{phrasalVerbPart}", navController.currentBackStackEntry?.destination?.route)
        assertEquals("Out", navController.currentBackStackEntry?.arguments?.getString("phrasalVerbPart"))
    }
}


class FakeApiService : ApiService {
    /*override fun getVerbs(): Call<List<Verb>> {
        throw UnsupportedOperationException("Not used in UI test")
    }*/

    override fun getVerbsSingle(): List<Verb> {
        throw UnsupportedOperationException("Not used in UI test")
    }

    override fun getPhrasalVerbs(verbId: Long): List<PhrasalVerb> {
        throw UnsupportedOperationException("Not used in this test")
    }

    override fun getPhrasalVerbs(prepositionAdverb: String): List<PhrasalVerb> {
        throw UnsupportedOperationException("Not used in this test")
    }

    override fun getPhrasalVerbDefinitions(phrasalVerbId: Long): List<Definition> {
        throw UnsupportedOperationException("Not used in this test")
    }

    override fun getQuestions(phrasalVerbPart: String): List<Question> {
        throw UnsupportedOperationException("Not used in this test")
    }

    override fun getPrepsAdverbs(): List<String> {
        throw UnsupportedOperationException("Not used in this test")
    }
}

class FakeVerbRepositoryImpl : VerbRepositoryImpl(FakeApiService())

class FakeGetVerbsUseCase : GetVerbsUseCase(FakeVerbRepositoryImpl()) {
    override fun run(params: None): List<Verb> {
        return Single.just(emptyList()) // Not used in UI test
    }
}

class FakeGetPrepsAdverbsUseCase : GetPrepsAdverbsUseCase(FakeVerbRepositoryImpl()) {
    override fun run(params: None): List<String> {
        return super.run(params)
    }
}

class FakeMainViewModel : MainViewModel(FakeGetVerbsUseCase(), FakeGetPrepsAdverbsUseCase()) {
    init {
        isLoading.value = false

        /*filteredVerbs.value = listOf(
            Verb(id = 1L, name = "Go", phrasalVerbs = emptyList()),
            Verb(id = 2L, name = "Come", phrasalVerbs = emptyList())
        )*/

        filteredVerbs.value = listOf("Out", "In")

    }
}
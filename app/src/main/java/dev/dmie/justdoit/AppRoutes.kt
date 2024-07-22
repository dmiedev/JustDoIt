package dev.dmie.justdoit

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import dev.dmie.justdoit.screen.add_edit_task.AddEditTaskScreen
import dev.dmie.justdoit.screen.add_edit_task_list.AddEditTaskListScreen
import dev.dmie.justdoit.screen.profile.ProfileScreen
import dev.dmie.justdoit.screen.search.SearchScreen
import dev.dmie.justdoit.screen.sign_in.SignInScreen
import dev.dmie.justdoit.screen.sign_up.SignUpScreen
import dev.dmie.justdoit.screen.splash.SplashScreen
import dev.dmie.justdoit.screen.task_lists.TaskListsScreen
import dev.dmie.justdoit.screen.tasks.TasksScreen
import dev.dmie.justdoit.screen.today_tasks.TodayTasksScreen

const val SPLASH_SCREEN = "splash"
const val TASK_LISTS_SCREEN = "taskLists"
const val TASKS_SCREEN = "tasks"
const val TODAY_TASKS_SCREEN = "todayTasks"
const val PROFILE_SCREEN = "profile"
const val SIGN_IN_SCREEN = "signIn"
const val SIGN_UP_SCREEN = "signUp"
const val ADD_EDIT_TASK_SCREEN = "addEditTask"
const val ADD_EDIT_TASK_LIST_SCREEN = "addEditTaskList"
const val SEARCH_SCREEN = "search"

const val TASK_LIST_ID = "taskListId"
const val TASK_ID = "taskId"

const val DEEP_LINK_BASE_URL = "https://justdoit.dmie.dev"


fun NavGraphBuilder.makeNavGraph(appState: JustDoItAppState) {
    composable(SPLASH_SCREEN) {
        SplashScreen(openAndPopUp = appState::navigateAndPopUp)
    }

    composable(TASK_LISTS_SCREEN) {
        TaskListsScreen(openScreen = appState::navigate)
    }

    composable(
        route = "$ADD_EDIT_TASK_LIST_SCREEN?$TASK_LIST_ID={$TASK_LIST_ID}",
        arguments = listOf(navArgument(TASK_LIST_ID) { nullable = true })
    ) {
        AddEditTaskListScreen(popUp = appState::popUp)
    }

    composable(PROFILE_SCREEN) {
        ProfileScreen(
            openScreen = appState::navigate,
            restartApp = appState::clearAndNavigate,
            popUp = appState::popUp
        )
    }

    composable(SIGN_UP_SCREEN) {
        SignUpScreen(
            openAndPopUp = appState::navigateAndPopUp,
            popUp = appState::popUp
        )
    }

    composable(SIGN_IN_SCREEN) {
        SignInScreen(
            openAndPopUp = appState::navigateAndPopUp,
            popUp = appState::popUp
        )
    }

    composable(
        route = "$TASKS_SCREEN/{$TASK_LIST_ID}",
        arguments = listOf(navArgument(TASK_LIST_ID) { })
    ) {
        TasksScreen(
            openScreen = appState::navigate,
            popUp = appState::popUp
        )
    }

    composable(
        route = TODAY_TASKS_SCREEN,
        deepLinks = listOf(navDeepLink { uriPattern = "$DEEP_LINK_BASE_URL/$TODAY_TASKS_SCREEN" })
    ) {
        TodayTasksScreen(
            openScreen = appState::navigate,
            popUp = appState::popUp
        )
    }

    composable(
        route = "$ADD_EDIT_TASK_SCREEN?$TASK_LIST_ID={$TASK_LIST_ID}&$TASK_ID={$TASK_ID}",
        arguments = listOf(
            navArgument(TASK_LIST_ID) { nullable = true },
            navArgument(TASK_ID) { nullable = true }
        )
    ) {
        AddEditTaskScreen(popUp = appState::popUp)
    }

    composable(SEARCH_SCREEN) {
        SearchScreen(
            popUp = appState::popUp,
            openScreen = appState::navigate
        )
    }
}

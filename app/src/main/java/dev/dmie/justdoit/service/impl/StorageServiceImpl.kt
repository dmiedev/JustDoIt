package dev.dmie.justdoit.service.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import dev.dmie.justdoit.model.Task
import dev.dmie.justdoit.model.TaskList
import dev.dmie.justdoit.service.AuthenticationService
import dev.dmie.justdoit.service.StorageService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class StorageServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AuthenticationService
) : StorageService {
    @OptIn(ExperimentalCoroutinesApi::class)
    override val taskLists: Flow<List<TaskList>>
        get() = auth.currentUser.flatMapLatest { user -> firestore
            .collection(TASK_LIST_COLLECTION)
            .whereEqualTo(USER_ID_FIELD, user.id)
            .orderBy(NAME_FIELD, Query.Direction.ASCENDING)
            .dataObjects()
        }

    override suspend fun getTaskList(taskListId: String): TaskList? {
        return firestore.collection(TASK_LIST_COLLECTION).document(taskListId).get().await().toObject()
    }

    override suspend fun saveTaskList(taskList: TaskList): String {
        val taskListWithUserId = taskList.copy(userId = auth.currentUserId)
        return firestore.collection(TASK_LIST_COLLECTION).add(taskListWithUserId).await().id
    }

    override suspend fun updateTaskList(taskList: TaskList) {
        firestore.collection(TASK_LIST_COLLECTION).document(taskList.id).set(taskList).await()
    }

    override suspend fun deleteTaskList(taskListId: String) {
        firestore.collection(TASK_LIST_COLLECTION).document(taskListId).delete().await()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getTasks(taskListId: String, sort: Task.Sort): Flow<List<Task>> {
        return auth.currentUser.flatMapLatest { user -> firestore
            .collection(TASK_COLLECTION)
            .whereEqualTo(USER_ID_FIELD, user.id)
            .whereEqualTo(TASK_LIST_ID_FIELD, taskListId)
            .taskOrderBy(sort)
            .dataObjects()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getTodayTasks(sort: Task.Sort): Flow<List<Task>> {
        return auth.currentUser.flatMapLatest { user -> firestore
            .collection(TASK_COLLECTION)
            .whereEqualTo(USER_ID_FIELD, user.id)
            .whereLessThan(DUE_DATE_FIELD, getTomorrowMidnight())
            .whereEqualTo(COMPLETED_FIELD, false)
            .taskOrderBy(sort)
            .dataObjects()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun findTasks(query: String, sort: Task.Sort): Flow<List<Task>> {
        if (query.isBlank()) {
            return flowOf(emptyList())
        }
        val trimmedQuery = query.trim()
        return auth.currentUser.flatMapLatest { user -> firestore
            .collection(TASK_COLLECTION)
            .whereEqualTo(USER_ID_FIELD, user.id)
            .whereGreaterThanOrEqualTo(TITLE_FIELD, trimmedQuery)
            .whereLessThanOrEqualTo(TITLE_FIELD, trimmedQuery + '\uf8ff')
            .taskOrderBy(sort)
            .dataObjects()
        }
    }

    override suspend fun getTask(taskId: String): Task? {
        return firestore.collection(TASK_COLLECTION).document(taskId).get().await().toObject()
    }

    override suspend fun saveTask(taskListId: String, task: Task): String {
        val taskWithIds = task.copy(userId = auth.currentUserId, taskListId = taskListId)
        return firestore.collection(TASK_COLLECTION).add(taskWithIds).await().id
    }

    override suspend fun updateTask(task: Task) {
        firestore.collection(TASK_COLLECTION).document(task.id).set(task).await()
    }

    override suspend fun deleteTask(taskId: String) {
        firestore.collection(TASK_COLLECTION).document(taskId).delete().await()
    }

    private fun Query.taskOrderBy(sort: Task.Sort): Query {
        val query = this.orderBy(COMPLETED_FIELD, Query.Direction.ASCENDING)
        if (sort == Task.Sort.None) {
            return query
        }
        return query.orderBy(
            sort.toField(),
            if (sort == Task.Sort.Priority)
                Query.Direction.DESCENDING
            else
                Query.Direction.ASCENDING
        )
    }

    private fun Task.Sort.toField() = this.name.replaceFirstChar { it.lowercase(Locale.getDefault()) }

    companion object {
        private const val USER_ID_FIELD = "userId"
        private const val COMPLETED_FIELD = "completed"
        private const val DUE_DATE_FIELD = "dueDate"
        private const val TASK_LIST_ID_FIELD = "taskListId"
        private const val NAME_FIELD = "name"
        private const val TITLE_FIELD = "title"

        private const val TASK_LIST_COLLECTION = "taskLists"
        private const val TASK_COLLECTION = "tasks"
    }
}

private fun getTomorrowMidnight(): Date {
    val tomorrowMidnight = LocalDate.now()
        .plusDays(1)
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
    return Date.from(tomorrowMidnight)
}

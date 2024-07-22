package dev.dmie.justdoit.service

import dev.dmie.justdoit.model.Task
import dev.dmie.justdoit.model.TaskList
import kotlinx.coroutines.flow.Flow

interface StorageService {
    val taskLists: Flow<List<TaskList>>
    suspend fun getTaskList(taskListId: String): TaskList?
    suspend fun saveTaskList(taskList: TaskList): String
    suspend fun updateTaskList(taskList: TaskList)
    suspend fun deleteTaskList(taskListId: String)

    fun getTasks(taskListId: String, sort: Task.Sort): Flow<List<Task>>
    fun getTodayTasks(sort: Task.Sort): Flow<List<Task>>
    fun findTasks(query: String, sort: Task.Sort): Flow<List<Task>>
    suspend fun getTask(taskId: String): Task?
    suspend fun saveTask(taskListId: String, task: Task): String
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(taskId: String)
}

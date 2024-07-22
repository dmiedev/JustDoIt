package dev.dmie.justdoit.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import dev.dmie.justdoit.R.string as AppText
import java.util.Date

data class Task(
    @DocumentId val id: String = "",
    val title: String = "",
    val description: String = "",
    val dueDate: Date? = null,
    val priority: Int = 0,
    val completed: Boolean = false,
    val userId: String = "",
    val taskListId: String = ""
) {
    enum class Priority { None, Low, Medium, High }

    enum class Sort {
        None,
        Priority,
        DueDate,
        Title;

        fun toAppText() = when (this) {
            Priority -> AppText.sort_priority_label
            DueDate -> AppText.sort_due_date_label
            Title -> AppText.sort_title_label
            None -> AppText.sort_none_label
        }
    }

    @Exclude
    fun isNew() = id.isBlank()
}

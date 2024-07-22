package dev.dmie.justdoit.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import dev.dmie.justdoit.R.drawable as AppIcon

data class TaskList(
    @DocumentId val id: String = "",
    val name: String = "",
    val icon: Icon = Icon.entries.first(),
    val userId: String = "",
) {
    enum class Icon(val drawableId: Int) {
        Home(AppIcon.ic_home),
        School(AppIcon.ic_school),
        Shopping(AppIcon.ic_shopping_cart),
        Gaming(AppIcon.ic_videogame),
        People(AppIcon.ic_people),
        Music(AppIcon.ic_music_note),
        Star(AppIcon.ic_star),
        Day(AppIcon.ic_sunny),
        Night(AppIcon.ic_nightlight);
    }

    @Exclude
    fun isNew() = id.isBlank()
}

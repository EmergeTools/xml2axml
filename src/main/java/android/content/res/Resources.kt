package android.content.res

import android.R.attr
import java.lang.reflect.Field

class Resources(
    private val attrMap: Map<String, Int> = attr::class.java.fields.associateBy(Field::getName) { it.getInt(null) }
) : Map<String, Int> by attrMap {

    fun getId(
        name: String,
        type: String,
        pkg: String,
    ): Int {
        if (pkg == PACKAGE_ANDROID && type == ATTRIBUTE_TYPE) {
            val attr = this[name]
            if (attr != null) {
                return attr
            }
        }
        return 0
    }

    companion object {
        const val PACKAGE_ANDROID = "android"
        const val ATTRIBUTE_TYPE = "attr"

        val instance = Resources()
    }
}

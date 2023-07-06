import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import android.webkit.JavascriptInterface
import android.widget.Toast
import androidx.fragment.app.findFragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import kotlinx.serialization.*
import kotlinx.serialization.json.Json


class AndroidJSInterface(private val mView: View) {

    @JavascriptInterface
    fun redirectPage(route: String) {
        val navController: NavController = findNavController(mView.findFragment())

        when(route) {
            "InputFragment" -> {
                navController.navigate(com.example.webviewtesting.R.id.action_homeFragment_to_inputFragment)
            }
        }
    }

    @JavascriptInterface
    fun saveData(name: String, age:Int) {
        val mContext: Context = mView.context
        val sharedPreferences:SharedPreferences = mContext.getSharedPreferences("DEFAULT_PREFERENCES",Context.MODE_PRIVATE)
        val sharedEditor = sharedPreferences.edit()
        sharedEditor.putString("name",name)
        sharedEditor.putInt("age",age)
        sharedEditor.apply()
        Log.d("Debug","Input Saving Success!")
    }

    @JavascriptInterface
    fun loadSavedData():String {
        val mContext: Context = mView.context
        val sharedPreferences:SharedPreferences = mContext.getSharedPreferences("DEFAULT_PREFERENCES",Context.MODE_PRIVATE)
        val name:String = sharedPreferences.getString("name","")!!
        val age = sharedPreferences.getInt("age",0)
        return if(name == "") {
            ""
        } else {
            val mapData:Map<String,String> = mapOf(
                "name" to name,
                "age" to age.toString(),
            )
            Json.encodeToString(mapData)
        }

    }

    @JavascriptInterface
    fun showToast() {
        Toast.makeText(mView.context,"TOAST FROM ANDROID", Toast.LENGTH_SHORT).show()
    }
}
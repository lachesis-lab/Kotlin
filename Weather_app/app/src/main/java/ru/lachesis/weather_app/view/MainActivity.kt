package ru.lachesis.weather_app.view


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import ru.lachesis.weather_app.R

class MainActivity :  AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.main_container, MainFragment.newInstance(null))
/*
            transaction.replace(R.id.day_fragment_container,DayFragment.newInstance())
*/
            transaction.commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_content_provider -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.main_container,ContactsFragment.newInstance(),null)
                    .addToBackStack("")
                    .commitAllowingStateLoss()

            }
            R.id.history_menu_item -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.main_container,HistoryFragment.newInstance(),null)
                    .addToBackStack("")
                    .commitAllowingStateLoss()

            }

        }

 /*       if(item.itemId == R.id.history_menu_item){
            supportFragmentManager.beginTransaction()
                .add(R.id.main_container,HistoryFragment.newInstance(),null)
                .addToBackStack("")
                .commitAllowingStateLoss()
        }
 */       return true
    }
    override fun onBackPressed() {
        val fManager = supportFragmentManager
        super.onBackPressed()
    }
}
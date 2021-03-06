package com.ayeshaazeema.githubapp.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayeshaazeema.githubapp.R
import com.ayeshaazeema.githubapp.adapter.UserAdapter
import com.ayeshaazeema.githubapp.model.User
import com.ayeshaazeema.githubapp.databinding.ActivityMainBinding
import com.ayeshaazeema.githubapp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var userAdapter: UserAdapter
    private lateinit var mainViewModel: MainViewModel
    private lateinit var listUser: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        supportActionBar?.title = getString(R.string.github_user_list)

        setAdapter()
        showProgressBar(true)
        setViewModel()
    }

    private fun setAdapter() {
        userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()

        mainBinding.rvUsers.apply {
            setHasFixedSize(true)
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun setViewModel() {
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java
        )
        if (mainViewModel.getListUser().value == null) {
            mainViewModel.setListUser(this, "")
        }
        mainViewModel.getListUser().observe(this, { users ->
            if (users != null) {
                listUser = users
                userAdapter.setData(listUser)
                showProgressBar(false)
            }
        })
    }

    private fun showProgressBar(state: Boolean) {
        if (state) {
            mainBinding.pbMain.visibility = View.VISIBLE
        } else {
            mainBinding.pbMain.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val searchMenu = menu?.findItem(R.id.search)
        searchMenu?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                showProgressBar(true)
                mainViewModel.setListUser(this@MainActivity, "")
                return true
            }

            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                return true
            }
        })

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                showProgressBar(true)
                mainViewModel.setListUser(this@MainActivity, query)
                closeKeyboard()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.settings) {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun closeKeyboard() {
        val view: View? = this.currentFocus
        if (view != null) {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}
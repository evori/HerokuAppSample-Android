package example.aleks.com.herokuapp.presentation

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import dagger.android.AndroidInjection
import example.aleks.com.herokuapp.R
import example.aleks.com.herokuapp.databinding.ActivityMainBinding
import example.aleks.com.herokuapp.presentation.adapter.MoviesAdapter
import example.aleks.com.herokuapp.presentation.model.MoviesViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var dataBinding: ActivityMainBinding

    private val moviesAdapter by lazy {

        MoviesAdapter(moviesViewModel)
    }

    private val moviesViewModel by lazy {

        ViewModelProviders.of(this, viewModelFactory).get(MoviesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        val itemMargin = resources.getDimensionPixelSize(R.dimen.dimen_10dp)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        dataBinding.moviesViewModel = moviesViewModel

        dataBinding.moviesRecyclerView.addItemDecoration(RecyclerViewItemsSpaceDecoration(itemMargin, itemMargin, itemMargin, itemMargin))
        dataBinding.moviesRecyclerView.layoutManager = GridLayoutManager(this, resources.getInteger(R.integer.movies_span_count))
        dataBinding.moviesRecyclerView.adapter = moviesAdapter

        dataBinding.searchView.isActivated = true
        dataBinding.searchView.queryHint = getString(R.string.search)
        dataBinding.searchView.onActionViewExpanded()
        dataBinding.searchView.isIconified = false
        dataBinding.searchView.clearFocus()

        dataBinding.searchView.setOnQueryTextListener(this)
    }

    override fun onResume() {
        super.onResume()

        if (dataBinding.moviesViewModel?.moviesViewState?.movieItems?.isEmpty() == true) {

            dataBinding.moviesViewModel?.start()
        }
    }

    override fun onPause() {
        super.onPause()
        dataBinding.moviesViewModel?.dispose()
    }

    //region SearchView.OnQueryTextListener
    override fun onQueryTextChange(query: String?): Boolean {

        if (query.isNullOrBlank()) {
            moviesAdapter.filter.filter("")
        } else {
            moviesAdapter.filter.filter(query)
        }

        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean = false
    //endregion
}

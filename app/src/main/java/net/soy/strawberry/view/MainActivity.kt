package net.soy.strawberry.view

import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import net.soy.strawberry.R
import net.soy.strawberry.databinding.ActivityMainBinding
import net.soy.strawberry.adapter.MainSearchRecyclerViewAdapter
import net.soy.strawberry.view.base.BaseKtActivity
import net.soy.strawberry.viewmodel.MainViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

//View
class MainActivity :
    BaseKtActivity<ActivityMainBinding, MainViewModel>() {

    override val layoutResId: Int
        get() = R.layout.activity_main

    override val viewModel: MainViewModel by viewModel()

    private val mainSearchRecyclerViewAdapter: MainSearchRecyclerViewAdapter by inject()

    override fun initStartView() {
        Log.w(TAG, "initStartView()")
        main_activity_search_recycler_view.run {
            adapter = mainSearchRecyclerViewAdapter
            layoutManager = StaggeredGridLayoutManager(3, 1).apply {
                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
                orientation = StaggeredGridLayoutManager.VERTICAL
            }
            setHasFixedSize(true)
        }
    }

    override fun initDataBinding() {
        Log.w(TAG, "initDataBinding()")
        viewModel.imageSearchResponseLiveData.observe(this, Observer {
            it.documents.forEach{document ->
                mainSearchRecyclerViewAdapter.addImageItem(document.image_url, document.doc_url)
            }
            mainSearchRecyclerViewAdapter.notifyDataSetChanged()
        })
    }

    override fun initAfterBinding() {
        Log.w(TAG, "initAfterBinding()")
        main_activity_search_recycler_view.setOnClickListener {
            viewModel.getImageSearch(main_activity_search_text_view.text.toString(), 1, 80)
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}


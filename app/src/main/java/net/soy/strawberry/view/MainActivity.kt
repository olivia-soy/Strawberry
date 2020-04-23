package net.soy.strawberry.view

import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import net.soy.strawberry.R
import net.soy.strawberry.adapter.MainSearchRecyclerViewAdapter
import net.soy.strawberry.base.BaseKtActivity
import net.soy.strawberry.databinding.ActivityMainBinding
import net.soy.strawberry.viewmodel.MainViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

//View
class MainActivity :
    BaseKtActivity<ActivityMainBinding, MainViewModel>() {

    override val layoutResId: Int
        get() = R.layout.activity_main

    override val viewModel: MainViewModel by viewModel()

    var pastVisibleItems = 0
    var visibleItemCount:Int = 0
    var totalItemCount:Int = 0
    var page:Int = 1

    private val mainSearchRecyclerViewAdapter: MainSearchRecyclerViewAdapter by inject()

    override fun initStartView() {
        Log.w(TAG, "initStartView()")
        main_activity_search_recycler_view.run {
            adapter = mainSearchRecyclerViewAdapter
            layoutManager = GridLayoutManager(context, 3).apply {
                orientation = GridLayoutManager.VERTICAL
            }
            setHasFixedSize(true)

            addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if(dy > 0){
                        visibleItemCount = (layoutManager as GridLayoutManager).childCount
                        totalItemCount = (layoutManager as GridLayoutManager).itemCount
                        pastVisibleItems = (layoutManager as GridLayoutManager).findFirstVisibleItemPosition()

                        if(visibleItemCount + pastVisibleItems >= totalItemCount){
                            page++
                            viewModel.getImageSearch(text_input_edit_text_search.text.toString(), page, 20)
                        }
                    }
                }
            })
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
        btn_search.setOnClickListener {
            mainSearchRecyclerViewAdapter.clearAddImageItem()
            viewModel.getImageSearch(text_input_edit_text_search.text.toString(), page, 20)
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}


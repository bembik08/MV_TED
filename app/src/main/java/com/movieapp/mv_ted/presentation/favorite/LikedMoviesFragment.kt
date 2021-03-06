package com.movieapp.mv_ted.presentation.favorite

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.movieapp.mv_ted.R
import com.movieapp.mv_ted.databinding.FragmentLikedMoviesBinding
import com.movieapp.mv_ted.domain.AppState
import com.movieapp.mv_ted.presentation.core.BaseFragment
import com.movieapp.mv_ted.presentation.favorite.adapter.LikedMovieAdapter
import org.koin.android.ext.android.getKoin
import org.koin.core.scope.Scope

class LikedMoviesFragment :
    BaseFragment<FragmentLikedMoviesBinding>(R.layout.fragment_liked_movies) {
    override val scope : Scope = getKoin().createScope<LikedMoviesFragment>()
    override val viewBinding: FragmentLikedMoviesBinding by viewBinding()
    private val adapter: LikedMovieAdapter by lazy { LikedMovieAdapter() }
    override val viewModel: LikedMovieViewModel = scope.get()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
        viewModel.getData()
    }

    override fun renderData(appState: AppState) {
        when (appState) {
            is AppState.SuccessLike -> {
                adapter.setData(appState.likedMoviesList)
                initRecycleView()
            }
        }
    }

    private fun initRecycleView() = with(viewBinding) {
        val recyclerView = likesMoviesRecView
        recyclerView.adapter = adapter
    }

    companion object {
        fun newInstance() = LikedMoviesFragment()
    }
}
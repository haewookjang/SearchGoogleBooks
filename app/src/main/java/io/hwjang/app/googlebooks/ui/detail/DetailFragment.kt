package io.hwjang.app.googlebooks.ui.detail

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail.*
import io.hwjang.app.googlebooks.R
import io.hwjang.app.googlebooks.base.BaseDataBindingFragment
import io.hwjang.app.googlebooks.databinding.FragmentDetailBinding

@AndroidEntryPoint
class DetailFragment :
    BaseDataBindingFragment<FragmentDetailBinding, DetailViewModel>(R.layout.fragment_detail) {
    override val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val transition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = transition
        viewModel.load(args.id)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }

    override fun initDataBinding() {
        binding.imageUri = args.uri
        binding.title = args.title
        binding.bookTitle.transitionName = args.title
        binding.bookImage.transitionName = args.id
    }

}
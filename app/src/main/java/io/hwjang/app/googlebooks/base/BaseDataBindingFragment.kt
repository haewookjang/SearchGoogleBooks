package io.hwjang.app.googlebooks.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.hwjang.app.googlebooks.BR

abstract class BaseDataBindingFragment<DB : ViewDataBinding, VM : BaseViewModel>(@LayoutRes val layoutResourceId: Int) :
    BaseFragment<VM>() {

    private var dataBinding: DB? = null
    protected val binding get() = dataBinding ?: throw IllegalStateException()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<DB>(inflater, layoutResourceId, container, false).also {
            dataBinding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.setVariable(BR.vm, viewModel)
        initDataBinding()
    }

    abstract fun initDataBinding()

    override fun onDestroyView() {
        super.onDestroyView()
        dataBinding = null
    }
}
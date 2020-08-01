package com.vipul.materialmotions

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.google.android.material.animation.AnimationUtils
import com.google.android.material.transition.MaterialContainerTransform
import com.vipul.materialmotions.databinding.FragmentSecondBinding

class SecondFragment : BaseFragment<FragmentSecondBinding>() {

    private val args: SecondFragmentArgs by navArgs()

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?, bundle: Bundle?) =
        FragmentSecondBinding.inflate(inflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val transformation = MaterialContainerTransform()
        transformation.interpolator = AnimationUtils.LINEAR_INTERPOLATOR
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment
            duration = 1000
            scrimColor = Color.TRANSPARENT
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val key = args.color.first as String
        val color = args.color.second as String

        with(binding.textView) {
            text = key
        }

        with(binding.cardView) {
            setBackgroundColor(Color.parseColor(color))
            transitionName = color
        }
    }
}
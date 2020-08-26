package com.vipul.materialmotions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.MaterialElevationScale
import com.vipul.materialmotions.databinding.FragmentFirstBinding
import com.vipul.materialmotions.databinding.RowEvenBinding

class FirstFragment : BaseFragment<FragmentFirstBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        binding.cardsRecyclerView.adapter =
            CardsAdapter(event = { rowEvenBinding ->
                val extras: FragmentNavigator.Extras = FragmentNavigatorExtras(
                    rowEvenBinding.cardView to rowEvenBinding.cardView.transitionName
                )
                findNavController().navigate(
                    FirstFragmentDirections.actionListToDetails(rowEvenBinding.cardView.transitionName),
                    extras
                )

                exitTransition = MaterialElevationScale(false).apply {
                    duration = 100
                }
                reenterTransition = MaterialElevationScale(true).apply {
                    duration = 100
                }
            })
    }

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?, bundle: Bundle?) =
        FragmentFirstBinding.inflate(inflater, container, false)
}

class CardsAdapter(
    private val event: (RowEvenBinding) -> Unit
) :
    RecyclerView.Adapter<CardsViewHolder>() {

    override fun getItemCount(): Int {
        return 5
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsViewHolder {
        val binding = RowEvenBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return CardsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardsViewHolder, position: Int) {

        with(holder.binding.cardView) {
            transitionName = "Card $position"
        }

        holder.itemView.setOnClickListener {
            event(holder.binding)
        }
    }
}

class CardsViewHolder(val binding: RowEvenBinding) : RecyclerView.ViewHolder(binding.root)
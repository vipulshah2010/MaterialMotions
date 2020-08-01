package com.vipul.materialmotions

import android.graphics.Color
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
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.vipul.materialmotions.databinding.FragmentFirstBinding
import com.vipul.materialmotions.databinding.RowCardBinding
import java.io.IOException

class FirstFragment : BaseFragment<FragmentFirstBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        val map = Types.newParameterizedType(
            MutableMap::class.java,
            String::class.java,
            Any::class.java
        )

        val json =
            requireContext().assets.open("colors.json")
                .bufferedReader().use { it.readText() }


        try {
            Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                .adapter<Map<String, String>>(map).fromJson(json)?.let { it ->
                    binding.cardsRecyclerView.adapter =
                        CardsAdapter(it) {
                            val extras: FragmentNavigator.Extras = FragmentNavigatorExtras(
                                it.cardView to it.cardView.transitionName
                            )
                            findNavController().navigate(
                                FirstFragmentDirections.actionListToDetails(
                                    Pair(
                                        it.textView.transitionName,
                                        it.cardView.transitionName
                                    )
                                ), extras
                            )

                            exitTransition = MaterialElevationScale(false).apply {
                                duration = 300
                            }
                            reenterTransition = MaterialElevationScale(true).apply {
                                duration = 300
                            }
                        }
                }
        } catch (exception: IOException) {

        }
    }

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?, bundle: Bundle?) =
        FragmentFirstBinding.inflate(inflater, container, false)
}

class CardsAdapter(
    private val colors: Map<String, String>,
    private val event: (RowCardBinding) -> Unit
) :
    RecyclerView.Adapter<CardsViewHolder>() {

    private val keys: List<String> by lazy {
        ArrayList<String>(colors.keys)
    }

    override fun getItemCount(): Int {
        return colors.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsViewHolder {
        val binding = RowCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return CardsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardsViewHolder, position: Int) {

        with(holder.binding.textView) {
            text = keys[position]
            transitionName = keys[position]
        }

        with(holder.binding.cardView) {
            setBackgroundColor(Color.parseColor(colors[keys[position]]))
            transitionName = colors[keys[position]]
        }

        holder.itemView.setOnClickListener {
            event(holder.binding)
        }
    }
}

class CardsViewHolder(val binding: RowCardBinding) : RecyclerView.ViewHolder(binding.root)
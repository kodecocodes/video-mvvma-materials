/*
 * Copyright (c) 2022 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * These materials have been reviewed and are updated as of 7/2022.
 */

package com.yourcompany.android.creaturemon.view.allcreatures

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.yourcompany.android.creaturemon.databinding.ListItemCreatureBinding
import com.yourcompany.android.creaturemon.model.Creature

class CreatureAdapter(private val creatures: MutableList<Creature>)
  : RecyclerView.Adapter<CreatureAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val binding = ListItemCreatureBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
    )
    return ViewHolder(binding)
  }

  override fun onBindViewHolder(holder: CreatureAdapter.ViewHolder, position: Int) {
    holder.bind(creatures[position])
  }

  override fun getItemCount() = creatures.size

  fun updateCreatures(creatures: List<Creature>) {
    this.creatures.clear()
    this.creatures.addAll(creatures)
    notifyDataSetChanged()
  }

  // The constructor accepts a binding instance instead of a View
  class ViewHolder(private val binding: ListItemCreatureBinding) :
      RecyclerView.ViewHolder(binding.root) {

    private lateinit var creature: Creature

    fun bind(creature: Creature) {
      this.creature = creature
      // AppCompatResources is the recommended way to access the drawables
      // as context.getDrawable() tells you to use this in this case.
      binding.avatarListItem.setImageDrawable(
          AppCompatResources.getDrawable(
              binding.avatarListItem.context,
              creature.drawable
          )
      )
      binding.name.text = creature.name
      binding.hitPoints.text = creature.hitPoints.toString()
    }
  }
}
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

package com.yourcompany.android.creaturemon.view.creature

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yourcompany.android.creaturemon.R
import com.yourcompany.android.creaturemon.databinding.ActivityCreatureBinding
import com.yourcompany.android.creaturemon.model.AttributeStore
import com.yourcompany.android.creaturemon.model.AttributeType
import com.yourcompany.android.creaturemon.model.AttributeValue
import com.yourcompany.android.creaturemon.model.Avatar
import com.yourcompany.android.creaturemon.view.avatars.AvatarAdapter
import com.yourcompany.android.creaturemon.view.avatars.AvatarBottomDialogFragment
import com.yourcompany.android.creaturemon.viewmodel.CreatureViewModel

class CreatureActivity : AppCompatActivity(), AvatarAdapter.AvatarListener {

  private lateinit var viewModel: CreatureViewModel

  // Update Note: Views would be accessed using the binding instance that is set in the onCreate
  // method below
  private lateinit var binding: ActivityCreatureBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityCreatureBinding.inflate(layoutInflater)
    val view = binding.root
    // Update Note: This sets the content of the Activity to the root view which is the
    // ConstraintLayout
    setContentView(view)

    viewModel = ViewModelProvider(this).get(CreatureViewModel::class.java)

    configureUI()
    configureSpinnerAdapters()
    configureSpinnerListeners()
    configureEditText()
    configureClickListeners()
    configureLiveDataObservers()
  }

  private fun configureUI() {
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    title = getString(R.string.add_creature)
    if (viewModel.drawable != 0) hideTapLabel()
  }

  private fun configureSpinnerAdapters() {
    binding.intelligence.adapter = ArrayAdapter<AttributeValue>(this,
        android.R.layout.simple_spinner_dropdown_item, AttributeStore.INTELLIGENCE)
    binding.strength.adapter = ArrayAdapter<AttributeValue>(this,
        android.R.layout.simple_spinner_dropdown_item, AttributeStore.STRENGTH)
    binding.endurance.adapter = ArrayAdapter<AttributeValue>(this,
        android.R.layout.simple_spinner_dropdown_item, AttributeStore.ENDURANCE)
  }

  private fun configureSpinnerListeners() {
    binding.intelligence.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel.attributeSelected(AttributeType.INTELLIGENCE, position)
      }
      override fun onNothingSelected(parent: AdapterView<*>?) {}
    }
    binding.strength.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel.attributeSelected(AttributeType.STRENGTH, position)
      }
      override fun onNothingSelected(parent: AdapterView<*>?) {}
    }
    binding.endurance.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel.attributeSelected(AttributeType.ENDURANCE, position)
      }
      override fun onNothingSelected(parent: AdapterView<*>?) {}
    }
  }

  private fun configureEditText() {
    binding.nameEditText.addTextChangedListener(object : TextWatcher {
      override fun afterTextChanged(s: Editable?) {}
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        viewModel.name = s.toString()
      }
    })
  }

  private fun configureClickListeners() {
    binding.avatarImageView.setOnClickListener {
      val bottomDialogFragment = AvatarBottomDialogFragment.newInstance()
      bottomDialogFragment.show(supportFragmentManager, "AvatarBottomDialogFragment")
    }

    binding.saveButton.setOnClickListener {
      // Update Note: canSaveCreature() method is used here in the if statement this time around to
      // perform
      // the check. Then saveCreature() is called if the data is valid.
      if (viewModel.canSaveCreature()) {
        viewModel.saveCreature()
        Toast.makeText(this, getString(R.string.creature_saved), Toast.LENGTH_SHORT).show()
        finish()
      } else {
        Toast.makeText(this, getString(R.string.error_saving_creature), Toast.LENGTH_SHORT).show()
      }
    }
  }

  private fun configureLiveDataObservers() {
    viewModel.getCreatureLiveData().observe(this, Observer { creature ->
      // Update Note: Remember, we're accessing the views using view binding
      creature?.let {
        binding.hitPoints.text = creature.hitPoints.toString()
        binding.avatarImageView.setImageResource(creature.drawable)
        binding.nameEditText.setText(creature.name)
      }
    })
  }

  override fun avatarClicked(avatar: Avatar) {
    viewModel.drawableSelected(avatar.drawable)
    hideTapLabel()
  }

  private fun hideTapLabel() {
    binding.tapLabel.visibility = View.INVISIBLE
  }
}

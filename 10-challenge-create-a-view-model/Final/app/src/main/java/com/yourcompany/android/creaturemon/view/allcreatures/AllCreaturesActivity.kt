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

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yourcompany.android.creaturemon.R
import com.yourcompany.android.creaturemon.databinding.ActivityAllCreaturesBinding
import com.yourcompany.android.creaturemon.view.creature.CreatureActivity
import com.yourcompany.android.creaturemon.viewmodel.AllCreaturesViewModel

class AllCreaturesActivity : AppCompatActivity() {

  private lateinit var viewModel: AllCreaturesViewModel

  private val adapter = CreatureAdapter(mutableListOf())

  // The recording used Kotlin synthetics
  // but the materials have been updated to use viewBinding.
  // This line creates a binding variable of the generated binding class.
  // If you hold down Cmd + Click on the class name, it would take you to the layout file
  private lateinit var binding: ActivityAllCreaturesBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    installSplashScreen() // Initializes the new SplashScreen API
    super.onCreate(savedInstanceState)

    // The binding variable is set to the inflated layout which just creates an instance of
    // the layout file to be accessible by Kotlin.
    binding = ActivityAllCreaturesBinding.inflate(layoutInflater)
    val view = binding.root
    // This sets the content of the Activity to the root view which is the CoordinatorLayout
    setContentView(view)
    setSupportActionBar(binding.toolbar)

    viewModel = ViewModelProvider(this).get(AllCreaturesViewModel::class.java)

    // We use binding to access the views in the layout file.
    binding.rcvWrapper.creaturesRecyclerView.layoutManager = LinearLayoutManager(this)
    binding.rcvWrapper.creaturesRecyclerView.adapter = adapter

    viewModel.getAllCreaturesLiveData().observe(this, Observer { creatures ->
      creatures?.let {
        adapter.updateCreatures(creatures)
      }
    })

    binding.fab.setOnClickListener {
      startActivity(Intent(this, CreatureActivity::class.java))
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    // Inflate the menu; this adds items to the action bar if it is present.
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.action_clear_all -> {
        viewModel.clearAllCreatures()
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }
}

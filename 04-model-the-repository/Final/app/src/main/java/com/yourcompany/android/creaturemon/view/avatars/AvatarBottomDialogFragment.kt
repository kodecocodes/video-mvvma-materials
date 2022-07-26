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

package com.yourcompany.android.creaturemon.view.avatars

import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yourcompany.android.creaturemon.databinding.LayoutAvatarBottomSheetBinding
import com.yourcompany.android.creaturemon.model.Avatar

class AvatarBottomDialogFragment : BottomSheetDialogFragment(), AvatarAdapter.AvatarListener {

  private lateinit var callback: AvatarAdapter.AvatarListener

  // This property is only valid between onCreateView and
  // onDestroyView and it is used to inflate and destroy the view
  private var _binding: LayoutAvatarBottomSheetBinding? = null

  // Used throughout the class
  private val binding get() = _binding!!

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    _binding = LayoutAvatarBottomSheetBinding.inflate(inflater, container, false)
    val view = binding.root
    return view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    try {
      callback = activity as AvatarAdapter.AvatarListener
    } catch (e: ClassCastException) {
      throw ClassCastException(activity.toString() + " must implement AvatarAdapter.AvatarListener")
    }

    binding.avatarRecyclerView.layoutManager = GridLayoutManager(context, 3)
    binding.avatarRecyclerView.adapter = AvatarAdapter(AvatarStore.AVATARS, this)

  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  override fun avatarClicked(avatar: Avatar) {
    callback.avatarClicked(avatar)
  }

  companion object {
    fun newInstance(): AvatarBottomDialogFragment {
      return AvatarBottomDialogFragment()
    }
  }
}
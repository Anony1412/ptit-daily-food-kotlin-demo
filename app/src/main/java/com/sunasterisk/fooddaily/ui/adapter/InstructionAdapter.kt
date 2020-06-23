package com.sunasterisk.fooddaily.ui.adapter

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sunasterisk.fooddaily.ui.fragment.instruction.InstructionFragment

class InstructionAdapter(
    fragmentManager: FragmentManager
): FragmentPagerAdapter(
    fragmentManager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {

    private var instructionFragments = mutableListOf<InstructionFragment>()

    override fun getItem(position: Int): InstructionFragment = instructionFragments[position]

    override fun getCount(): Int = instructionFragments.size

    fun addFragment(fragments: List<InstructionFragment>) {
        instructionFragments = fragments.toMutableList()
    }
}

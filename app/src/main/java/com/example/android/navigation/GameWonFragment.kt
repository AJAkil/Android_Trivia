/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.navigation.databinding.FragmentGameWonBinding


class GameWonFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentGameWonBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game_won, container, false)

        binding.nextMatchButton.setOnClickListener { view: View ->
            //view.findNavController().navigate(R.id.action_gameWonFragment_to_gameFragment)

            // We would use Direction classes to navigate back to the game fragment
            view.findNavController().navigate(GameWonFragmentDirections.actionGameWonFragmentToGameFragment())
        }

        setHasOptionsMenu(true)


        /*if (args != null) {
            Toast.makeText(context, "NumCorrect : ${args.numCorrect}, NumQuestions : ${args.numQuestions}", Toast.LENGTH_SHORT).show()
        }*/


        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.try_again)

        return binding.root
    }

    // This method will be used to share the values of the game in the form of an intent.
    private fun getShareIntent(): Intent {
        val args = arguments?.let { GameWonFragmentArgs.fromBundle(it) }
        /*val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        if (args != null) {
            shareIntent.putExtra(Intent.EXTRA_TEXT,getString(R.string.share_success_text,args.numCorrect, args.numQuestions))
        }
        return shareIntent*/

        // we can actually do this in a much easier way with a builtinMethod that builds an intent

            return ShareCompat.IntentBuilder.from(activity)
                    .setText(getString(R.string.share_success_text, args?.numCorrect, args?.numQuestions))
                    .setType("text/plain")
                    .intent

    }

    // This share success method, which get's the intent from the getShareIntent() method and then
    // calls an startActivity to start an activity so that we can share our intent with the required
    // values
    private fun shareSuccess(){
        startActivity(getShareIntent())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.winner_menu, menu)

        // check to see if the intent we are trying to share resolves to an activity
        if (null == getShareIntent().resolveActivity(activity!!.packageManager)){

            // we ask the menu to find the shareItem by the ID, so that we can set it's visibility
            // to false and hide the share icon if there is no activity that can resolve to the
            // intent we are trying to create
            menu.findItem(R.id.share).setVisible(false)
        }
    }

    // What this method actually does is that, when the options item is selected on the menu,
    // in this case the share icon, then if the id of the share view matches with the menuItem Id,
    // we call the shareSuccess() method that creates or starts an activity of sharing our intent.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.share -> shareSuccess()
        }
        return super.onOptionsItemSelected(item)
    }
}

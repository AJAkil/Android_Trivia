package com.example.android.navigation

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.android.navigation.databinding.FragmentTitleBinding


/**
 * A simple [Fragment] subclass.
 * Use the [TitleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TitleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflating the layout by ourselves rather than letting the activity handle this
        val binding: FragmentTitleBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_title, container, false
        )

        // binding object is used to navigate to the next screen with the click of a button.
        binding.playButton.setOnClickListener { view: View ->
            //Navigation.findNavController(view).navigate(R.id.gameFragment)

            // we can actually do it with the Navigation class itself
            //Navigation.createNavigateOnClickListener(R.id.gameFragment)

            // below is the extension function for the android view class
            //view.findNavController().navigate(R.id.action_titleFragment_to_gameFragment)

            // Lastly we would use Direction classes to navigate to the start of the game, the ga,e fragment
            view.findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToGameFragment())
        }

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_about_trivia)
        // We tell android we are going to have a menu associated with our title fragment by setting setHasOptionsMenu = true
        setHasOptionsMenu(true)


        return binding.root

    }

    // This method creates the menu by inflating the menu layout
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    // This function decides what happens if we select an item of the menu item
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
                view!!.findNavController()
        ) || super.onOptionsItemSelected(item)

    }
}
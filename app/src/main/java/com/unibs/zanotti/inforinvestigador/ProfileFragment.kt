package com.unibs.zanotti.inforinvestigador


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.unibs.zanotti.inforinvestigador.auth.LoginActivity
import com.unibs.zanotti.inforinvestigador.utils.FirebaseUtils


/**
 * A simple [Fragment] subclass.
 *
 */
class ProfileFragment : androidx.fragment.app.Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val logoutButton = view.findViewById<Button>(R.id.logout_button)
        logoutButton.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            intent.putExtra(LoginActivity.BOOLEAN_EXTRA_DO_LOGOUT, true)
            startActivity(intent)
            activity?.finish()
        }

        val createPaperButton = view.findViewById<Button>(R.id.create_paper_button)
        createPaperButton.setOnClickListener {
            FirebaseUtils.populatePapersCollection()
        }

        return view
    }
}

package com.cacttuseducation.lorikshabani_project_2.ui.login

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cacttuseducation.lorikshabani_project_2.R
import com.cacttuseducation.lorikshabani_project_2.`object`.Singleton
import com.cacttuseducation.lorikshabani_project_2.databinding.FragmentLoginBinding
import com.cacttuseducation.lorikshabani_project_2.models.LoginAttemptRequest
import com.cacttuseducation.lorikshabani_project_2.models.LoginCodeRequest
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = activity?.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPref?.getBoolean("is_logged_in", false)

        if (isLoggedIn == true) {
            val action = LoginFragmentDirections.actionNavLoginToNavBlog()
            findNavController().navigate(action)
        }
        goToRegister()
        apiLoginAttemptRequest()
    }

    private fun goToRegister() {
        binding.btnGoToRegister.setOnClickListener {
            findNavController().navigate(R.id.action_nav_login_to_nav_register)
        }
    }

    private fun apiLoginAttemptRequest() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val user = LoginAttemptRequest(email, password)

            val loader = binding.progressBar
            loader.visibility = View.GONE

            if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
                Snackbar.make(requireView(), "Please Fill Out The Fields", Snackbar.LENGTH_LONG).show()
                binding.etEmail.setHintTextColor(Color.RED)
                binding.etPassword.setHintTextColor(Color.RED)
                loader.visibility = View.GONE
            } else {
                loader.visibility = View.VISIBLE
                val loginAttempt = Singleton.provideRetrofitInstance().loginAttempt(user)
                loginAttempt.enqueue(object : Callback<LoginCodeRequest?> {
                    override fun onResponse(
                        call: Call<LoginCodeRequest?>, response: Response<LoginCodeRequest?>
                    ) {
                        if (response.isSuccessful && response.code() == 200) {
                            Log.d("response", response.body().toString())
                            Snackbar.make(requireView(), "Code Sent", Snackbar.LENGTH_LONG).show()
                            val action = LoginFragmentDirections.actionNavLoginToLoginCodeFragment(email)
                            loader.visibility = View.GONE
                            findNavController().navigate(action)
                        } else {
                            Snackbar.make(requireView(), "Wrong Credentials", Snackbar.LENGTH_LONG).show()
                            loader.visibility = View.GONE
                        }
                    }

                    override fun onFailure(call: Call<LoginCodeRequest?>, t: Throwable) {
                        Log.w("error", "Failed API")
                        loader.visibility = View.GONE

                    }

                })
            }


        }
    }


}
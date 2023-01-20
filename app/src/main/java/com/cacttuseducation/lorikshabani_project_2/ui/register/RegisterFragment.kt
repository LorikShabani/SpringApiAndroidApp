package com.cacttuseducation.lorikshabani_project_2.ui.register

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cacttuseducation.lorikshabani_project_2.R
import com.cacttuseducation.lorikshabani_project_2.`object`.Singleton.provideRetrofitInstance
import com.cacttuseducation.lorikshabani_project_2.databinding.FragmentRegisterBinding
import com.cacttuseducation.lorikshabani_project_2.models.RegisterRequest
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goToRegister()
        apiRegisterCall()
    }

    private fun goToRegister() {
        binding.btnGoToLogin.setOnClickListener {
            findNavController().navigate(R.id.action_nav_register_to_nav_login)
        }
    }

    private fun apiRegisterCall() {
        binding.btnRegister.setOnClickListener {
            val loader = binding.progressBar
            loader.visibility = View.GONE
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val newUser = RegisterRequest(email, password)
            if (email.isNullOrEmpty() || password.isNullOrEmpty()){
                Snackbar.make(requireView(), "Please Fill Out The Fields", Snackbar.LENGTH_LONG).show()
                binding.etEmail.setHintTextColor(Color.RED)
                binding.etPassword.setHintTextColor(Color.RED)
                loader.visibility = View.GONE
            }else{
                loader.visibility = View.VISIBLE
                val registerUser = provideRetrofitInstance().register(newUser)
                registerUser.enqueue(object : Callback<RegisterRequest?>{
                    override fun onResponse(
                        call: Call<RegisterRequest?>, response: Response<RegisterRequest?>) {
                        if(response.isSuccessful && response.code()==200){
                            Log.d("response", response.body().toString())
                            Snackbar.make(requireView(), "New User Registered", Snackbar.LENGTH_LONG).show()
                            loader.visibility = View.GONE
                            findNavController().navigate(R.id.action_nav_register_to_nav_login)
                        }else{
                            Snackbar.make(requireView(), "Email In-Use!", Snackbar.LENGTH_LONG).show()
                        }
                    }
                    override fun onFailure(call: Call<RegisterRequest?>, t: Throwable) {
                        Log.w("error", "Failed API ${t.message}")
                        loader.visibility = View.GONE
                    }

                })
            }


        }
    }


}



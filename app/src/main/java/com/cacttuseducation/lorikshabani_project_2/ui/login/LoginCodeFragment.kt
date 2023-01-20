package com.cacttuseducation.lorikshabani_project_2.ui.login

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cacttuseducation.lorikshabani_project_2.R
import com.cacttuseducation.lorikshabani_project_2.`object`.Singleton.provideRetrofitInstance
import com.cacttuseducation.lorikshabani_project_2.databinding.FragmentLoginCodeBinding
import com.cacttuseducation.lorikshabani_project_2.models.LoginCodeRequest
import com.cacttuseducation.lorikshabani_project_2.models.LoginCodeResponse
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginCodeFragment : Fragment() {

    private lateinit var binding: FragmentLoginCodeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginCodeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        apiLoginCodeRequest()
        val email = arguments?.getString("email")
        binding.tvConfirm.text = "Code Sent at $email"

    }

    private fun apiLoginCodeRequest() {
        binding.btnLogin.setOnClickListener {

            val loader = binding.progressBar
            loader.visibility = View.GONE
            val code = binding.etCode.text.toString()

            val user = LoginCodeRequest(code)
            if (code.isNullOrEmpty()){
                binding.etCode.setHintTextColor(Color.RED)
                Toast.makeText(context, "Please Fill Out The Field", Toast.LENGTH_LONG).show()
                loader.visibility = View.GONE
            }else{
                loader.visibility = View.VISIBLE
                val loginCode = provideRetrofitInstance().loginCode(user)
                loginCode.enqueue(object : Callback<LoginCodeResponse?>{
                    override fun onResponse(
                        call: Call<LoginCodeResponse?>,
                        response: Response<LoginCodeResponse?>
                    ) {
                        if (response.isSuccessful && response.code()==200){
                            Snackbar.make(requireView(), "Successful Login", Snackbar.LENGTH_LONG).show()
                            val token = response.body()?.token
                            val sharedPref = activity?.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
                            sharedPref?.edit()?.putBoolean("is_logged_in", true)?.apply()
                            sharedPref?.edit()?.putString("token", token)?.commit()
                            Log.d("token", "$token")
                            loader.visibility = View.GONE
                            findNavController().navigate(R.id.action_loginCodeFragment_to_nav_blog)
                        }else{
                            Snackbar.make(requireView(), "Invalid Code", Snackbar.LENGTH_LONG).show()
                            binding.etCode.setHintTextColor(Color.RED)
                            loader.visibility = View.GONE
                        }
                    }

                    override fun onFailure(call: Call<LoginCodeResponse?>, t: Throwable) {
                        Log.w("error", "${t.message}")
                        loader.visibility = View.GONE
                    }

                })
            }

        }
    }


}
package edu.vlu.week07.productroom.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import edu.vlu.week07.productroom.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: ProductViewModel by viewModels()
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ProductAdapter(
            onFavoriteClick = { p, fav -> viewModel.setFavorite(p, fav) },
            onDeleteClick = { p -> viewModel.deleteProduct(p) }
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Observe LiveData -> UI tự cập nhật
        viewModel.allProducts.observe(this) { list ->
            adapter.submitList(list)
        }

        binding.btnAdd.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val price = binding.etPrice.text.toString().toDoubleOrNull() ?: 0.0
            val qty = binding.etQuantity.text.toString().toIntOrNull() ?: 0

            if (name.isBlank()) {
                Toast.makeText(this, "Names must not be blank", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.addProduct(name, price, qty)
            binding.etName.text?.clear()
            binding.etPrice.text?.clear()
            binding.etQuantity.text?.clear()
        }

        binding.btnClearAll.setOnClickListener {
            viewModel.clearAll()
        }
    }
}

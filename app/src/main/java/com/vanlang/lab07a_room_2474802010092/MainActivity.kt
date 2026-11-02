package com.vanlang.lab07a_room_2474802010092

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.vanlang.lab07a_room_2474802010092.data.local.AppDatabase
import com.vanlang.lab07a_room_2474802010092.data.local.ProductDao
import com.vanlang.lab07a_room_2474802010092.data.local.ProductEntity
import com.vanlang.lab07a_room_2474802010092.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var productDao: ProductDao
    private var currentList: List<ProductEntity> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = AppDatabase.getDatabase(this)
        productDao = database.productDao()

        // Observe LiveData stream: getAllProducts()
        productDao.getAllProducts().observe(this) { products ->
            currentList = products
            if (products.isEmpty()) {
                binding.tvProductList.text = "Database đang trống. Hãy thêm sản phẩm mới!"
            } else {
                val sb = StringBuilder()
                products.forEach { p ->
                    sb.append("ID: ${p.id} | Tên: ${p.name} | Giá: $${p.price} | Tồn kho: ${p.quantity}\n-------------------------\n")
                }
                binding.tvProductList.text = sb.toString()
            }
        }

        // Insert product
        binding.btnInsert.setOnClickListener {
            val name = binding.etProductName.text.toString().trim()
            val priceStr = binding.etProductPrice.text.toString().trim()
            val qtyStr = binding.etProductQuantity.text.toString().trim()

            if (name.isEmpty() || priceStr.isEmpty() || qtyStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val price = priceStr.toDoubleOrNull() ?: 0.0
            val quantity = qtyStr.toIntOrNull() ?: 0
            val product = ProductEntity(
                name = name,
                price = price,
                quantity = quantity
            )

            lifecycleScope.launch(Dispatchers.IO) {
                productDao.insertProduct(product)
                withContext(Dispatchers.Main) {
                    binding.etProductName.text.clear()
                    binding.etProductPrice.text.clear()
                    binding.etProductQuantity.text.clear()
                    Toast.makeText(this@MainActivity, "Đã thêm sản phẩm thành công!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Delete latest product
        binding.btnDelete.setOnClickListener {
            if (currentList.isNotEmpty()) {
                val latest = currentList.first()
                lifecycleScope.launch(Dispatchers.IO) {
                    productDao.deleteProduct(latest)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, "Đã xóa SP ID: ${latest.id}", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Không có sản phẩm nào để xóa!", Toast.LENGTH_SHORT).show()
            }
        }

        // Search product by ID
        binding.btnSearch.setOnClickListener {
            val idStr = binding.etSearchId.text.toString().trim()
            val id = idStr.toIntOrNull()
            if (id == null) {
                Toast.makeText(this, "Vui lòng nhập ID hợp lệ!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch(Dispatchers.IO) {
                val found = productDao.getProductById(id)
                withContext(Dispatchers.Main) {
                    if (found != null) {
                        Toast.makeText(
                            this@MainActivity,
                            "Tìm thấy: ${found.name} (Giá: ${found.price}, Kho: ${found.quantity})",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(this@MainActivity, "Không tìm thấy SP với ID: $id", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}

package edu.vlu.week07.productroom.data.repository

import androidx.lifecycle.LiveData
import edu.vlu.week07.productroom.data.local.ProductDao
import edu.vlu.week07.productroom.data.local.ProductEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository(
    private val productDao: ProductDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    val allProducts: LiveData<List<ProductEntity>> = productDao.observeAll()

    suspend fun insert(product: ProductEntity): Long = withContext(ioDispatcher) {
        productDao.insert(product)
    }

    suspend fun update(product: ProductEntity) = withContext(ioDispatcher) {
        productDao.update(product)
    }

    suspend fun delete(product: ProductEntity) = withContext(ioDispatcher) {
        productDao.delete(product)
    }

    suspend fun setFavorite(productId: Long, isFavorite: Boolean) = withContext(ioDispatcher) {
        productDao.setFavorite(productId, isFavorite)
    }

    suspend fun deleteAll() = withContext(ioDispatcher) {
        productDao.deleteAll()
    }
}

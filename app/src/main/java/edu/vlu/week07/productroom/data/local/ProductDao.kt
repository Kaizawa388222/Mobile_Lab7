package edu.vlu.week07.productroom.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: ProductEntity): Long

    @Update
    suspend fun update(product: ProductEntity)

    @Delete
    suspend fun delete(product: ProductEntity)

    @Query("SELECT * FROM products ORDER BY created_at DESC")
    fun observeAll(): LiveData<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE id = :productId")
    suspend fun getById(productId: Long): ProductEntity?

    @Query("UPDATE products SET is_favorite = :favorite WHERE id = :productId")
    suspend fun setFavorite(productId: Long, favorite: Boolean)

    @Query("SELECT COUNT(*) FROM products")
    suspend fun count(): Int

    @Query("DELETE FROM products")
    suspend fun deleteAll()
}

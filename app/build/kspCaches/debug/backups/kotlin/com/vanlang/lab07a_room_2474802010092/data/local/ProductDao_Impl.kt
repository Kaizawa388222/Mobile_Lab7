package com.vanlang.lab07a_room_2474802010092.`data`.local

import androidx.lifecycle.LiveData
import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import javax.`annotation`.processing.Generated
import kotlin.Double
import kotlin.Int
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class ProductDao_Impl(
  __db: RoomDatabase,
) : ProductDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfProductEntity: EntityInsertAdapter<ProductEntity>

  private val __deleteAdapterOfProductEntity: EntityDeleteOrUpdateAdapter<ProductEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfProductEntity = object : EntityInsertAdapter<ProductEntity>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `products` (`id`,`product_name`,`product_price`,`stock_quantity`) VALUES (nullif(?, 0),?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ProductEntity) {
        statement.bindLong(1, entity.id.toLong())
        statement.bindText(2, entity.name)
        statement.bindDouble(3, entity.price)
        statement.bindLong(4, entity.quantity.toLong())
      }
    }
    this.__deleteAdapterOfProductEntity = object : EntityDeleteOrUpdateAdapter<ProductEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `products` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: ProductEntity) {
        statement.bindLong(1, entity.id.toLong())
      }
    }
  }

  public override suspend fun insertProduct(product: ProductEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfProductEntity.insert(_connection, product)
  }

  public override suspend fun deleteProduct(product: ProductEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfProductEntity.handle(_connection, product)
  }

  public override fun getAllProducts(): LiveData<List<ProductEntity>> {
    val _sql: String = "SELECT * FROM products ORDER BY id DESC"
    return __db.invalidationTracker.createLiveData(arrayOf("products"), false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "product_name")
        val _columnIndexOfPrice: Int = getColumnIndexOrThrow(_stmt, "product_price")
        val _columnIndexOfQuantity: Int = getColumnIndexOrThrow(_stmt, "stock_quantity")
        val _result: MutableList<ProductEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: ProductEntity
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpPrice: Double
          _tmpPrice = _stmt.getDouble(_columnIndexOfPrice)
          val _tmpQuantity: Int
          _tmpQuantity = _stmt.getLong(_columnIndexOfQuantity).toInt()
          _item = ProductEntity(_tmpId,_tmpName,_tmpPrice,_tmpQuantity)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getProductById(productId: Int): ProductEntity? {
    val _sql: String = "SELECT * FROM products WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, productId.toLong())
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "product_name")
        val _columnIndexOfPrice: Int = getColumnIndexOrThrow(_stmt, "product_price")
        val _columnIndexOfQuantity: Int = getColumnIndexOrThrow(_stmt, "stock_quantity")
        val _result: ProductEntity?
        if (_stmt.step()) {
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpPrice: Double
          _tmpPrice = _stmt.getDouble(_columnIndexOfPrice)
          val _tmpQuantity: Int
          _tmpQuantity = _stmt.getLong(_columnIndexOfQuantity).toInt()
          _result = ProductEntity(_tmpId,_tmpName,_tmpPrice,_tmpQuantity)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}

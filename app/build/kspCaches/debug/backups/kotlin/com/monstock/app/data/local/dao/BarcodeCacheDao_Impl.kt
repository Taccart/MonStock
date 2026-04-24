package com.monstock.app.`data`.local.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.monstock.app.`data`.local.entity.BarcodeCacheEntity
import javax.`annotation`.processing.Generated
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class BarcodeCacheDao_Impl(
  __db: RoomDatabase,
) : BarcodeCacheDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfBarcodeCacheEntity: EntityInsertAdapter<BarcodeCacheEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfBarcodeCacheEntity = object : EntityInsertAdapter<BarcodeCacheEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `barcode_cache` (`barcode`,`name`,`brand`,`category`,`unit`,`imageUrl`,`cachedAt`) VALUES (?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: BarcodeCacheEntity) {
        statement.bindText(1, entity.barcode)
        statement.bindText(2, entity.name)
        val _tmpBrand: String? = entity.brand
        if (_tmpBrand == null) {
          statement.bindNull(3)
        } else {
          statement.bindText(3, _tmpBrand)
        }
        val _tmpCategory: String? = entity.category
        if (_tmpCategory == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmpCategory)
        }
        val _tmpUnit: String? = entity.unit
        if (_tmpUnit == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmpUnit)
        }
        val _tmpImageUrl: String? = entity.imageUrl
        if (_tmpImageUrl == null) {
          statement.bindNull(6)
        } else {
          statement.bindText(6, _tmpImageUrl)
        }
        statement.bindLong(7, entity.cachedAt)
      }
    }
  }

  public override suspend fun insert(entry: BarcodeCacheEntity): Unit = performSuspending(__db,
      false, true) { _connection ->
    __insertAdapterOfBarcodeCacheEntity.insert(_connection, entry)
  }

  public override suspend fun getByBarcode(barcode: String): BarcodeCacheEntity? {
    val _sql: String = "SELECT * FROM barcode_cache WHERE barcode = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, barcode)
        val _columnIndexOfBarcode: Int = getColumnIndexOrThrow(_stmt, "barcode")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfBrand: Int = getColumnIndexOrThrow(_stmt, "brand")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfUnit: Int = getColumnIndexOrThrow(_stmt, "unit")
        val _columnIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _columnIndexOfCachedAt: Int = getColumnIndexOrThrow(_stmt, "cachedAt")
        val _result: BarcodeCacheEntity?
        if (_stmt.step()) {
          val _tmpBarcode: String
          _tmpBarcode = _stmt.getText(_columnIndexOfBarcode)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpBrand: String?
          if (_stmt.isNull(_columnIndexOfBrand)) {
            _tmpBrand = null
          } else {
            _tmpBrand = _stmt.getText(_columnIndexOfBrand)
          }
          val _tmpCategory: String?
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          }
          val _tmpUnit: String?
          if (_stmt.isNull(_columnIndexOfUnit)) {
            _tmpUnit = null
          } else {
            _tmpUnit = _stmt.getText(_columnIndexOfUnit)
          }
          val _tmpImageUrl: String?
          if (_stmt.isNull(_columnIndexOfImageUrl)) {
            _tmpImageUrl = null
          } else {
            _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl)
          }
          val _tmpCachedAt: Long
          _tmpCachedAt = _stmt.getLong(_columnIndexOfCachedAt)
          _result =
              BarcodeCacheEntity(_tmpBarcode,_tmpName,_tmpBrand,_tmpCategory,_tmpUnit,_tmpImageUrl,_tmpCachedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteOlderThan(threshold: Long) {
    val _sql: String = "DELETE FROM barcode_cache WHERE cachedAt < ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, threshold)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}

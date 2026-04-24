package com.monstock.app.`data`.local.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.monstock.app.`data`.local.entity.ShelfEntity
import javax.`annotation`.processing.Generated
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class ShelfDao_Impl(
  __db: RoomDatabase,
) : ShelfDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfShelfEntity: EntityInsertAdapter<ShelfEntity>

  private val __deleteAdapterOfShelfEntity: EntityDeleteOrUpdateAdapter<ShelfEntity>

  private val __updateAdapterOfShelfEntity: EntityDeleteOrUpdateAdapter<ShelfEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfShelfEntity = object : EntityInsertAdapter<ShelfEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `shelves` (`id`,`pantryId`,`name`,`capacity`) VALUES (nullif(?, 0),?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ShelfEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.pantryId)
        statement.bindText(3, entity.name)
        val _tmpCapacity: Int? = entity.capacity
        if (_tmpCapacity == null) {
          statement.bindNull(4)
        } else {
          statement.bindLong(4, _tmpCapacity.toLong())
        }
      }
    }
    this.__deleteAdapterOfShelfEntity = object : EntityDeleteOrUpdateAdapter<ShelfEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `shelves` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: ShelfEntity) {
        statement.bindLong(1, entity.id)
      }
    }
    this.__updateAdapterOfShelfEntity = object : EntityDeleteOrUpdateAdapter<ShelfEntity>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `shelves` SET `id` = ?,`pantryId` = ?,`name` = ?,`capacity` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: ShelfEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.pantryId)
        statement.bindText(3, entity.name)
        val _tmpCapacity: Int? = entity.capacity
        if (_tmpCapacity == null) {
          statement.bindNull(4)
        } else {
          statement.bindLong(4, _tmpCapacity.toLong())
        }
        statement.bindLong(5, entity.id)
      }
    }
  }

  public override suspend fun insertShelf(shelf: ShelfEntity): Long = performSuspending(__db, false,
      true) { _connection ->
    val _result: Long = __insertAdapterOfShelfEntity.insertAndReturnId(_connection, shelf)
    _result
  }

  public override suspend fun deleteShelf(shelf: ShelfEntity): Unit = performSuspending(__db, false,
      true) { _connection ->
    __deleteAdapterOfShelfEntity.handle(_connection, shelf)
  }

  public override suspend fun updateShelf(shelf: ShelfEntity): Unit = performSuspending(__db, false,
      true) { _connection ->
    __updateAdapterOfShelfEntity.handle(_connection, shelf)
  }

  public override fun getShelvesByPantry(pantryId: Long): Flow<List<ShelfEntity>> {
    val _sql: String = "SELECT * FROM shelves WHERE pantryId = ? ORDER BY name ASC"
    return createFlow(__db, false, arrayOf("shelves")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, pantryId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfPantryId: Int = getColumnIndexOrThrow(_stmt, "pantryId")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfCapacity: Int = getColumnIndexOrThrow(_stmt, "capacity")
        val _result: MutableList<ShelfEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: ShelfEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpPantryId: Long
          _tmpPantryId = _stmt.getLong(_columnIndexOfPantryId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpCapacity: Int?
          if (_stmt.isNull(_columnIndexOfCapacity)) {
            _tmpCapacity = null
          } else {
            _tmpCapacity = _stmt.getLong(_columnIndexOfCapacity).toInt()
          }
          _item = ShelfEntity(_tmpId,_tmpPantryId,_tmpName,_tmpCapacity)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getShelfById(id: Long): ShelfEntity? {
    val _sql: String = "SELECT * FROM shelves WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfPantryId: Int = getColumnIndexOrThrow(_stmt, "pantryId")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfCapacity: Int = getColumnIndexOrThrow(_stmt, "capacity")
        val _result: ShelfEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpPantryId: Long
          _tmpPantryId = _stmt.getLong(_columnIndexOfPantryId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpCapacity: Int?
          if (_stmt.isNull(_columnIndexOfCapacity)) {
            _tmpCapacity = null
          } else {
            _tmpCapacity = _stmt.getLong(_columnIndexOfCapacity).toInt()
          }
          _result = ShelfEntity(_tmpId,_tmpPantryId,_tmpName,_tmpCapacity)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteShelfById(id: Long) {
    val _sql: String = "DELETE FROM shelves WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
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

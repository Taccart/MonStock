package com.monstock.app.`data`.local.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.monstock.app.`data`.local.entity.PantryEntity
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
public class PantryDao_Impl(
  __db: RoomDatabase,
) : PantryDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfPantryEntity: EntityInsertAdapter<PantryEntity>

  private val __deleteAdapterOfPantryEntity: EntityDeleteOrUpdateAdapter<PantryEntity>

  private val __updateAdapterOfPantryEntity: EntityDeleteOrUpdateAdapter<PantryEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfPantryEntity = object : EntityInsertAdapter<PantryEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `pantries` (`id`,`name`) VALUES (nullif(?, 0),?)"

      protected override fun bind(statement: SQLiteStatement, entity: PantryEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.name)
      }
    }
    this.__deleteAdapterOfPantryEntity = object : EntityDeleteOrUpdateAdapter<PantryEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `pantries` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: PantryEntity) {
        statement.bindLong(1, entity.id)
      }
    }
    this.__updateAdapterOfPantryEntity = object : EntityDeleteOrUpdateAdapter<PantryEntity>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `pantries` SET `id` = ?,`name` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: PantryEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.name)
        statement.bindLong(3, entity.id)
      }
    }
  }

  public override suspend fun insertPantry(pantry: PantryEntity): Long = performSuspending(__db,
      false, true) { _connection ->
    val _result: Long = __insertAdapterOfPantryEntity.insertAndReturnId(_connection, pantry)
    _result
  }

  public override suspend fun deletePantry(pantry: PantryEntity): Unit = performSuspending(__db,
      false, true) { _connection ->
    __deleteAdapterOfPantryEntity.handle(_connection, pantry)
  }

  public override suspend fun updatePantry(pantry: PantryEntity): Unit = performSuspending(__db,
      false, true) { _connection ->
    __updateAdapterOfPantryEntity.handle(_connection, pantry)
  }

  public override fun getAllPantries(): Flow<List<PantryEntity>> {
    val _sql: String = "SELECT * FROM pantries ORDER BY name ASC"
    return createFlow(__db, false, arrayOf("pantries")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _result: MutableList<PantryEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: PantryEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          _item = PantryEntity(_tmpId,_tmpName)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getPantryById(id: Long): PantryEntity? {
    val _sql: String = "SELECT * FROM pantries WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _result: PantryEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          _result = PantryEntity(_tmpId,_tmpName)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deletePantryById(id: Long) {
    val _sql: String = "DELETE FROM pantries WHERE id = ?"
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

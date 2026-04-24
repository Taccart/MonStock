package com.monstock.app.`data`.local.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.monstock.app.`data`.local.database.Converters
import com.monstock.app.`data`.local.entity.ItemEntity
import com.monstock.app.`data`.model.ItemCategory
import com.monstock.app.`data`.model.ItemUnit
import java.time.LocalDate
import javax.`annotation`.processing.Generated
import kotlin.Double
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
public class ItemDao_Impl(
  __db: RoomDatabase,
) : ItemDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfItemEntity: EntityInsertAdapter<ItemEntity>

  private val __converters: Converters = Converters()

  private val __deleteAdapterOfItemEntity: EntityDeleteOrUpdateAdapter<ItemEntity>

  private val __updateAdapterOfItemEntity: EntityDeleteOrUpdateAdapter<ItemEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfItemEntity = object : EntityInsertAdapter<ItemEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `items` (`id`,`shelfId`,`name`,`brand`,`category`,`quantity`,`unit`,`purchaseDate`,`expiryDate`,`minimumStockThreshold`,`barcode`,`photoUri`,`notes`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ItemEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.shelfId)
        statement.bindText(3, entity.name)
        val _tmpBrand: String? = entity.brand
        if (_tmpBrand == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmpBrand)
        }
        val _tmp: String? = __converters.itemCategoryToString(entity.category)
        if (_tmp == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmp)
        }
        statement.bindDouble(6, entity.quantity)
        val _tmp_1: String? = __converters.itemUnitToString(entity.unit)
        if (_tmp_1 == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmp_1)
        }
        val _tmpPurchaseDate: LocalDate? = entity.purchaseDate
        val _tmp_2: Long? = __converters.localDateToEpochDay(_tmpPurchaseDate)
        if (_tmp_2 == null) {
          statement.bindNull(8)
        } else {
          statement.bindLong(8, _tmp_2)
        }
        val _tmpExpiryDate: LocalDate? = entity.expiryDate
        val _tmp_3: Long? = __converters.localDateToEpochDay(_tmpExpiryDate)
        if (_tmp_3 == null) {
          statement.bindNull(9)
        } else {
          statement.bindLong(9, _tmp_3)
        }
        val _tmpMinimumStockThreshold: Double? = entity.minimumStockThreshold
        if (_tmpMinimumStockThreshold == null) {
          statement.bindNull(10)
        } else {
          statement.bindDouble(10, _tmpMinimumStockThreshold)
        }
        val _tmpBarcode: String? = entity.barcode
        if (_tmpBarcode == null) {
          statement.bindNull(11)
        } else {
          statement.bindText(11, _tmpBarcode)
        }
        val _tmpPhotoUri: String? = entity.photoUri
        if (_tmpPhotoUri == null) {
          statement.bindNull(12)
        } else {
          statement.bindText(12, _tmpPhotoUri)
        }
        val _tmpNotes: String? = entity.notes
        if (_tmpNotes == null) {
          statement.bindNull(13)
        } else {
          statement.bindText(13, _tmpNotes)
        }
      }
    }
    this.__deleteAdapterOfItemEntity = object : EntityDeleteOrUpdateAdapter<ItemEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `items` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: ItemEntity) {
        statement.bindLong(1, entity.id)
      }
    }
    this.__updateAdapterOfItemEntity = object : EntityDeleteOrUpdateAdapter<ItemEntity>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `items` SET `id` = ?,`shelfId` = ?,`name` = ?,`brand` = ?,`category` = ?,`quantity` = ?,`unit` = ?,`purchaseDate` = ?,`expiryDate` = ?,`minimumStockThreshold` = ?,`barcode` = ?,`photoUri` = ?,`notes` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: ItemEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.shelfId)
        statement.bindText(3, entity.name)
        val _tmpBrand: String? = entity.brand
        if (_tmpBrand == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmpBrand)
        }
        val _tmp: String? = __converters.itemCategoryToString(entity.category)
        if (_tmp == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmp)
        }
        statement.bindDouble(6, entity.quantity)
        val _tmp_1: String? = __converters.itemUnitToString(entity.unit)
        if (_tmp_1 == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmp_1)
        }
        val _tmpPurchaseDate: LocalDate? = entity.purchaseDate
        val _tmp_2: Long? = __converters.localDateToEpochDay(_tmpPurchaseDate)
        if (_tmp_2 == null) {
          statement.bindNull(8)
        } else {
          statement.bindLong(8, _tmp_2)
        }
        val _tmpExpiryDate: LocalDate? = entity.expiryDate
        val _tmp_3: Long? = __converters.localDateToEpochDay(_tmpExpiryDate)
        if (_tmp_3 == null) {
          statement.bindNull(9)
        } else {
          statement.bindLong(9, _tmp_3)
        }
        val _tmpMinimumStockThreshold: Double? = entity.minimumStockThreshold
        if (_tmpMinimumStockThreshold == null) {
          statement.bindNull(10)
        } else {
          statement.bindDouble(10, _tmpMinimumStockThreshold)
        }
        val _tmpBarcode: String? = entity.barcode
        if (_tmpBarcode == null) {
          statement.bindNull(11)
        } else {
          statement.bindText(11, _tmpBarcode)
        }
        val _tmpPhotoUri: String? = entity.photoUri
        if (_tmpPhotoUri == null) {
          statement.bindNull(12)
        } else {
          statement.bindText(12, _tmpPhotoUri)
        }
        val _tmpNotes: String? = entity.notes
        if (_tmpNotes == null) {
          statement.bindNull(13)
        } else {
          statement.bindText(13, _tmpNotes)
        }
        statement.bindLong(14, entity.id)
      }
    }
  }

  public override suspend fun insertItem(item: ItemEntity): Long = performSuspending(__db, false,
      true) { _connection ->
    val _result: Long = __insertAdapterOfItemEntity.insertAndReturnId(_connection, item)
    _result
  }

  public override suspend fun deleteItem(item: ItemEntity): Unit = performSuspending(__db, false,
      true) { _connection ->
    __deleteAdapterOfItemEntity.handle(_connection, item)
  }

  public override suspend fun updateItem(item: ItemEntity): Unit = performSuspending(__db, false,
      true) { _connection ->
    __updateAdapterOfItemEntity.handle(_connection, item)
  }

  public override fun getItemsByShelf(shelfId: Long): Flow<List<ItemEntity>> {
    val _sql: String = "SELECT * FROM items WHERE shelfId = ? ORDER BY expiryDate ASC"
    return createFlow(__db, false, arrayOf("items")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, shelfId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfShelfId: Int = getColumnIndexOrThrow(_stmt, "shelfId")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfBrand: Int = getColumnIndexOrThrow(_stmt, "brand")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfQuantity: Int = getColumnIndexOrThrow(_stmt, "quantity")
        val _columnIndexOfUnit: Int = getColumnIndexOrThrow(_stmt, "unit")
        val _columnIndexOfPurchaseDate: Int = getColumnIndexOrThrow(_stmt, "purchaseDate")
        val _columnIndexOfExpiryDate: Int = getColumnIndexOrThrow(_stmt, "expiryDate")
        val _columnIndexOfMinimumStockThreshold: Int = getColumnIndexOrThrow(_stmt,
            "minimumStockThreshold")
        val _columnIndexOfBarcode: Int = getColumnIndexOrThrow(_stmt, "barcode")
        val _columnIndexOfPhotoUri: Int = getColumnIndexOrThrow(_stmt, "photoUri")
        val _columnIndexOfNotes: Int = getColumnIndexOrThrow(_stmt, "notes")
        val _result: MutableList<ItemEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: ItemEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpShelfId: Long
          _tmpShelfId = _stmt.getLong(_columnIndexOfShelfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpBrand: String?
          if (_stmt.isNull(_columnIndexOfBrand)) {
            _tmpBrand = null
          } else {
            _tmpBrand = _stmt.getText(_columnIndexOfBrand)
          }
          val _tmpCategory: ItemCategory
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfCategory)
          }
          val _tmp_1: ItemCategory? = __converters.fromItemCategory(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'com.monstock.app.`data`.model.ItemCategory', but it was NULL.")
          } else {
            _tmpCategory = _tmp_1
          }
          val _tmpQuantity: Double
          _tmpQuantity = _stmt.getDouble(_columnIndexOfQuantity)
          val _tmpUnit: ItemUnit
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfUnit)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfUnit)
          }
          val _tmp_3: ItemUnit? = __converters.fromItemUnit(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'com.monstock.app.`data`.model.ItemUnit', but it was NULL.")
          } else {
            _tmpUnit = _tmp_3
          }
          val _tmpPurchaseDate: LocalDate?
          val _tmp_4: Long?
          if (_stmt.isNull(_columnIndexOfPurchaseDate)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getLong(_columnIndexOfPurchaseDate)
          }
          _tmpPurchaseDate = __converters.fromLocalDate(_tmp_4)
          val _tmpExpiryDate: LocalDate?
          val _tmp_5: Long?
          if (_stmt.isNull(_columnIndexOfExpiryDate)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getLong(_columnIndexOfExpiryDate)
          }
          _tmpExpiryDate = __converters.fromLocalDate(_tmp_5)
          val _tmpMinimumStockThreshold: Double?
          if (_stmt.isNull(_columnIndexOfMinimumStockThreshold)) {
            _tmpMinimumStockThreshold = null
          } else {
            _tmpMinimumStockThreshold = _stmt.getDouble(_columnIndexOfMinimumStockThreshold)
          }
          val _tmpBarcode: String?
          if (_stmt.isNull(_columnIndexOfBarcode)) {
            _tmpBarcode = null
          } else {
            _tmpBarcode = _stmt.getText(_columnIndexOfBarcode)
          }
          val _tmpPhotoUri: String?
          if (_stmt.isNull(_columnIndexOfPhotoUri)) {
            _tmpPhotoUri = null
          } else {
            _tmpPhotoUri = _stmt.getText(_columnIndexOfPhotoUri)
          }
          val _tmpNotes: String?
          if (_stmt.isNull(_columnIndexOfNotes)) {
            _tmpNotes = null
          } else {
            _tmpNotes = _stmt.getText(_columnIndexOfNotes)
          }
          _item =
              ItemEntity(_tmpId,_tmpShelfId,_tmpName,_tmpBrand,_tmpCategory,_tmpQuantity,_tmpUnit,_tmpPurchaseDate,_tmpExpiryDate,_tmpMinimumStockThreshold,_tmpBarcode,_tmpPhotoUri,_tmpNotes)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAllItems(): Flow<List<ItemEntity>> {
    val _sql: String = "SELECT * FROM items ORDER BY expiryDate ASC"
    return createFlow(__db, false, arrayOf("items")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfShelfId: Int = getColumnIndexOrThrow(_stmt, "shelfId")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfBrand: Int = getColumnIndexOrThrow(_stmt, "brand")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfQuantity: Int = getColumnIndexOrThrow(_stmt, "quantity")
        val _columnIndexOfUnit: Int = getColumnIndexOrThrow(_stmt, "unit")
        val _columnIndexOfPurchaseDate: Int = getColumnIndexOrThrow(_stmt, "purchaseDate")
        val _columnIndexOfExpiryDate: Int = getColumnIndexOrThrow(_stmt, "expiryDate")
        val _columnIndexOfMinimumStockThreshold: Int = getColumnIndexOrThrow(_stmt,
            "minimumStockThreshold")
        val _columnIndexOfBarcode: Int = getColumnIndexOrThrow(_stmt, "barcode")
        val _columnIndexOfPhotoUri: Int = getColumnIndexOrThrow(_stmt, "photoUri")
        val _columnIndexOfNotes: Int = getColumnIndexOrThrow(_stmt, "notes")
        val _result: MutableList<ItemEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: ItemEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpShelfId: Long
          _tmpShelfId = _stmt.getLong(_columnIndexOfShelfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpBrand: String?
          if (_stmt.isNull(_columnIndexOfBrand)) {
            _tmpBrand = null
          } else {
            _tmpBrand = _stmt.getText(_columnIndexOfBrand)
          }
          val _tmpCategory: ItemCategory
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfCategory)
          }
          val _tmp_1: ItemCategory? = __converters.fromItemCategory(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'com.monstock.app.`data`.model.ItemCategory', but it was NULL.")
          } else {
            _tmpCategory = _tmp_1
          }
          val _tmpQuantity: Double
          _tmpQuantity = _stmt.getDouble(_columnIndexOfQuantity)
          val _tmpUnit: ItemUnit
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfUnit)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfUnit)
          }
          val _tmp_3: ItemUnit? = __converters.fromItemUnit(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'com.monstock.app.`data`.model.ItemUnit', but it was NULL.")
          } else {
            _tmpUnit = _tmp_3
          }
          val _tmpPurchaseDate: LocalDate?
          val _tmp_4: Long?
          if (_stmt.isNull(_columnIndexOfPurchaseDate)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getLong(_columnIndexOfPurchaseDate)
          }
          _tmpPurchaseDate = __converters.fromLocalDate(_tmp_4)
          val _tmpExpiryDate: LocalDate?
          val _tmp_5: Long?
          if (_stmt.isNull(_columnIndexOfExpiryDate)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getLong(_columnIndexOfExpiryDate)
          }
          _tmpExpiryDate = __converters.fromLocalDate(_tmp_5)
          val _tmpMinimumStockThreshold: Double?
          if (_stmt.isNull(_columnIndexOfMinimumStockThreshold)) {
            _tmpMinimumStockThreshold = null
          } else {
            _tmpMinimumStockThreshold = _stmt.getDouble(_columnIndexOfMinimumStockThreshold)
          }
          val _tmpBarcode: String?
          if (_stmt.isNull(_columnIndexOfBarcode)) {
            _tmpBarcode = null
          } else {
            _tmpBarcode = _stmt.getText(_columnIndexOfBarcode)
          }
          val _tmpPhotoUri: String?
          if (_stmt.isNull(_columnIndexOfPhotoUri)) {
            _tmpPhotoUri = null
          } else {
            _tmpPhotoUri = _stmt.getText(_columnIndexOfPhotoUri)
          }
          val _tmpNotes: String?
          if (_stmt.isNull(_columnIndexOfNotes)) {
            _tmpNotes = null
          } else {
            _tmpNotes = _stmt.getText(_columnIndexOfNotes)
          }
          _item =
              ItemEntity(_tmpId,_tmpShelfId,_tmpName,_tmpBrand,_tmpCategory,_tmpQuantity,_tmpUnit,_tmpPurchaseDate,_tmpExpiryDate,_tmpMinimumStockThreshold,_tmpBarcode,_tmpPhotoUri,_tmpNotes)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getItemsExpiringSoon(todayEpochDay: Long, epochDayLimit: Long):
      Flow<List<ItemEntity>> {
    val _sql: String = """
        |
        |        SELECT * FROM items
        |        WHERE expiryDate IS NOT NULL
        |          AND expiryDate <= ?
        |          AND expiryDate >= ?
        |        ORDER BY expiryDate ASC
        |    
        """.trimMargin()
    return createFlow(__db, false, arrayOf("items")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, epochDayLimit)
        _argIndex = 2
        _stmt.bindLong(_argIndex, todayEpochDay)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfShelfId: Int = getColumnIndexOrThrow(_stmt, "shelfId")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfBrand: Int = getColumnIndexOrThrow(_stmt, "brand")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfQuantity: Int = getColumnIndexOrThrow(_stmt, "quantity")
        val _columnIndexOfUnit: Int = getColumnIndexOrThrow(_stmt, "unit")
        val _columnIndexOfPurchaseDate: Int = getColumnIndexOrThrow(_stmt, "purchaseDate")
        val _columnIndexOfExpiryDate: Int = getColumnIndexOrThrow(_stmt, "expiryDate")
        val _columnIndexOfMinimumStockThreshold: Int = getColumnIndexOrThrow(_stmt,
            "minimumStockThreshold")
        val _columnIndexOfBarcode: Int = getColumnIndexOrThrow(_stmt, "barcode")
        val _columnIndexOfPhotoUri: Int = getColumnIndexOrThrow(_stmt, "photoUri")
        val _columnIndexOfNotes: Int = getColumnIndexOrThrow(_stmt, "notes")
        val _result: MutableList<ItemEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: ItemEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpShelfId: Long
          _tmpShelfId = _stmt.getLong(_columnIndexOfShelfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpBrand: String?
          if (_stmt.isNull(_columnIndexOfBrand)) {
            _tmpBrand = null
          } else {
            _tmpBrand = _stmt.getText(_columnIndexOfBrand)
          }
          val _tmpCategory: ItemCategory
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfCategory)
          }
          val _tmp_1: ItemCategory? = __converters.fromItemCategory(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'com.monstock.app.`data`.model.ItemCategory', but it was NULL.")
          } else {
            _tmpCategory = _tmp_1
          }
          val _tmpQuantity: Double
          _tmpQuantity = _stmt.getDouble(_columnIndexOfQuantity)
          val _tmpUnit: ItemUnit
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfUnit)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfUnit)
          }
          val _tmp_3: ItemUnit? = __converters.fromItemUnit(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'com.monstock.app.`data`.model.ItemUnit', but it was NULL.")
          } else {
            _tmpUnit = _tmp_3
          }
          val _tmpPurchaseDate: LocalDate?
          val _tmp_4: Long?
          if (_stmt.isNull(_columnIndexOfPurchaseDate)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getLong(_columnIndexOfPurchaseDate)
          }
          _tmpPurchaseDate = __converters.fromLocalDate(_tmp_4)
          val _tmpExpiryDate: LocalDate?
          val _tmp_5: Long?
          if (_stmt.isNull(_columnIndexOfExpiryDate)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getLong(_columnIndexOfExpiryDate)
          }
          _tmpExpiryDate = __converters.fromLocalDate(_tmp_5)
          val _tmpMinimumStockThreshold: Double?
          if (_stmt.isNull(_columnIndexOfMinimumStockThreshold)) {
            _tmpMinimumStockThreshold = null
          } else {
            _tmpMinimumStockThreshold = _stmt.getDouble(_columnIndexOfMinimumStockThreshold)
          }
          val _tmpBarcode: String?
          if (_stmt.isNull(_columnIndexOfBarcode)) {
            _tmpBarcode = null
          } else {
            _tmpBarcode = _stmt.getText(_columnIndexOfBarcode)
          }
          val _tmpPhotoUri: String?
          if (_stmt.isNull(_columnIndexOfPhotoUri)) {
            _tmpPhotoUri = null
          } else {
            _tmpPhotoUri = _stmt.getText(_columnIndexOfPhotoUri)
          }
          val _tmpNotes: String?
          if (_stmt.isNull(_columnIndexOfNotes)) {
            _tmpNotes = null
          } else {
            _tmpNotes = _stmt.getText(_columnIndexOfNotes)
          }
          _item =
              ItemEntity(_tmpId,_tmpShelfId,_tmpName,_tmpBrand,_tmpCategory,_tmpQuantity,_tmpUnit,_tmpPurchaseDate,_tmpExpiryDate,_tmpMinimumStockThreshold,_tmpBarcode,_tmpPhotoUri,_tmpNotes)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getExpiredItems(todayEpochDay: Long): Flow<List<ItemEntity>> {
    val _sql: String = """
        |
        |        SELECT * FROM items
        |        WHERE expiryDate IS NOT NULL
        |          AND expiryDate < ?
        |        ORDER BY expiryDate ASC
        |    
        """.trimMargin()
    return createFlow(__db, false, arrayOf("items")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, todayEpochDay)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfShelfId: Int = getColumnIndexOrThrow(_stmt, "shelfId")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfBrand: Int = getColumnIndexOrThrow(_stmt, "brand")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfQuantity: Int = getColumnIndexOrThrow(_stmt, "quantity")
        val _columnIndexOfUnit: Int = getColumnIndexOrThrow(_stmt, "unit")
        val _columnIndexOfPurchaseDate: Int = getColumnIndexOrThrow(_stmt, "purchaseDate")
        val _columnIndexOfExpiryDate: Int = getColumnIndexOrThrow(_stmt, "expiryDate")
        val _columnIndexOfMinimumStockThreshold: Int = getColumnIndexOrThrow(_stmt,
            "minimumStockThreshold")
        val _columnIndexOfBarcode: Int = getColumnIndexOrThrow(_stmt, "barcode")
        val _columnIndexOfPhotoUri: Int = getColumnIndexOrThrow(_stmt, "photoUri")
        val _columnIndexOfNotes: Int = getColumnIndexOrThrow(_stmt, "notes")
        val _result: MutableList<ItemEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: ItemEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpShelfId: Long
          _tmpShelfId = _stmt.getLong(_columnIndexOfShelfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpBrand: String?
          if (_stmt.isNull(_columnIndexOfBrand)) {
            _tmpBrand = null
          } else {
            _tmpBrand = _stmt.getText(_columnIndexOfBrand)
          }
          val _tmpCategory: ItemCategory
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfCategory)
          }
          val _tmp_1: ItemCategory? = __converters.fromItemCategory(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'com.monstock.app.`data`.model.ItemCategory', but it was NULL.")
          } else {
            _tmpCategory = _tmp_1
          }
          val _tmpQuantity: Double
          _tmpQuantity = _stmt.getDouble(_columnIndexOfQuantity)
          val _tmpUnit: ItemUnit
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfUnit)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfUnit)
          }
          val _tmp_3: ItemUnit? = __converters.fromItemUnit(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'com.monstock.app.`data`.model.ItemUnit', but it was NULL.")
          } else {
            _tmpUnit = _tmp_3
          }
          val _tmpPurchaseDate: LocalDate?
          val _tmp_4: Long?
          if (_stmt.isNull(_columnIndexOfPurchaseDate)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getLong(_columnIndexOfPurchaseDate)
          }
          _tmpPurchaseDate = __converters.fromLocalDate(_tmp_4)
          val _tmpExpiryDate: LocalDate?
          val _tmp_5: Long?
          if (_stmt.isNull(_columnIndexOfExpiryDate)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getLong(_columnIndexOfExpiryDate)
          }
          _tmpExpiryDate = __converters.fromLocalDate(_tmp_5)
          val _tmpMinimumStockThreshold: Double?
          if (_stmt.isNull(_columnIndexOfMinimumStockThreshold)) {
            _tmpMinimumStockThreshold = null
          } else {
            _tmpMinimumStockThreshold = _stmt.getDouble(_columnIndexOfMinimumStockThreshold)
          }
          val _tmpBarcode: String?
          if (_stmt.isNull(_columnIndexOfBarcode)) {
            _tmpBarcode = null
          } else {
            _tmpBarcode = _stmt.getText(_columnIndexOfBarcode)
          }
          val _tmpPhotoUri: String?
          if (_stmt.isNull(_columnIndexOfPhotoUri)) {
            _tmpPhotoUri = null
          } else {
            _tmpPhotoUri = _stmt.getText(_columnIndexOfPhotoUri)
          }
          val _tmpNotes: String?
          if (_stmt.isNull(_columnIndexOfNotes)) {
            _tmpNotes = null
          } else {
            _tmpNotes = _stmt.getText(_columnIndexOfNotes)
          }
          _item =
              ItemEntity(_tmpId,_tmpShelfId,_tmpName,_tmpBrand,_tmpCategory,_tmpQuantity,_tmpUnit,_tmpPurchaseDate,_tmpExpiryDate,_tmpMinimumStockThreshold,_tmpBarcode,_tmpPhotoUri,_tmpNotes)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getLowStockItems(): Flow<List<ItemEntity>> {
    val _sql: String = """
        |
        |        SELECT * FROM items
        |        WHERE minimumStockThreshold IS NOT NULL
        |          AND quantity <= minimumStockThreshold
        |        ORDER BY name ASC
        |    
        """.trimMargin()
    return createFlow(__db, false, arrayOf("items")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfShelfId: Int = getColumnIndexOrThrow(_stmt, "shelfId")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfBrand: Int = getColumnIndexOrThrow(_stmt, "brand")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfQuantity: Int = getColumnIndexOrThrow(_stmt, "quantity")
        val _columnIndexOfUnit: Int = getColumnIndexOrThrow(_stmt, "unit")
        val _columnIndexOfPurchaseDate: Int = getColumnIndexOrThrow(_stmt, "purchaseDate")
        val _columnIndexOfExpiryDate: Int = getColumnIndexOrThrow(_stmt, "expiryDate")
        val _columnIndexOfMinimumStockThreshold: Int = getColumnIndexOrThrow(_stmt,
            "minimumStockThreshold")
        val _columnIndexOfBarcode: Int = getColumnIndexOrThrow(_stmt, "barcode")
        val _columnIndexOfPhotoUri: Int = getColumnIndexOrThrow(_stmt, "photoUri")
        val _columnIndexOfNotes: Int = getColumnIndexOrThrow(_stmt, "notes")
        val _result: MutableList<ItemEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: ItemEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpShelfId: Long
          _tmpShelfId = _stmt.getLong(_columnIndexOfShelfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpBrand: String?
          if (_stmt.isNull(_columnIndexOfBrand)) {
            _tmpBrand = null
          } else {
            _tmpBrand = _stmt.getText(_columnIndexOfBrand)
          }
          val _tmpCategory: ItemCategory
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfCategory)
          }
          val _tmp_1: ItemCategory? = __converters.fromItemCategory(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'com.monstock.app.`data`.model.ItemCategory', but it was NULL.")
          } else {
            _tmpCategory = _tmp_1
          }
          val _tmpQuantity: Double
          _tmpQuantity = _stmt.getDouble(_columnIndexOfQuantity)
          val _tmpUnit: ItemUnit
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfUnit)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfUnit)
          }
          val _tmp_3: ItemUnit? = __converters.fromItemUnit(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'com.monstock.app.`data`.model.ItemUnit', but it was NULL.")
          } else {
            _tmpUnit = _tmp_3
          }
          val _tmpPurchaseDate: LocalDate?
          val _tmp_4: Long?
          if (_stmt.isNull(_columnIndexOfPurchaseDate)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getLong(_columnIndexOfPurchaseDate)
          }
          _tmpPurchaseDate = __converters.fromLocalDate(_tmp_4)
          val _tmpExpiryDate: LocalDate?
          val _tmp_5: Long?
          if (_stmt.isNull(_columnIndexOfExpiryDate)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getLong(_columnIndexOfExpiryDate)
          }
          _tmpExpiryDate = __converters.fromLocalDate(_tmp_5)
          val _tmpMinimumStockThreshold: Double?
          if (_stmt.isNull(_columnIndexOfMinimumStockThreshold)) {
            _tmpMinimumStockThreshold = null
          } else {
            _tmpMinimumStockThreshold = _stmt.getDouble(_columnIndexOfMinimumStockThreshold)
          }
          val _tmpBarcode: String?
          if (_stmt.isNull(_columnIndexOfBarcode)) {
            _tmpBarcode = null
          } else {
            _tmpBarcode = _stmt.getText(_columnIndexOfBarcode)
          }
          val _tmpPhotoUri: String?
          if (_stmt.isNull(_columnIndexOfPhotoUri)) {
            _tmpPhotoUri = null
          } else {
            _tmpPhotoUri = _stmt.getText(_columnIndexOfPhotoUri)
          }
          val _tmpNotes: String?
          if (_stmt.isNull(_columnIndexOfNotes)) {
            _tmpNotes = null
          } else {
            _tmpNotes = _stmt.getText(_columnIndexOfNotes)
          }
          _item =
              ItemEntity(_tmpId,_tmpShelfId,_tmpName,_tmpBrand,_tmpCategory,_tmpQuantity,_tmpUnit,_tmpPurchaseDate,_tmpExpiryDate,_tmpMinimumStockThreshold,_tmpBarcode,_tmpPhotoUri,_tmpNotes)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun searchItems(query: String): Flow<List<ItemEntity>> {
    val _sql: String = """
        |
        |        SELECT * FROM items
        |        WHERE name LIKE '%' || ? || '%'
        |           OR brand LIKE '%' || ? || '%'
        |        ORDER BY expiryDate ASC
        |    
        """.trimMargin()
    return createFlow(__db, false, arrayOf("items")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, query)
        _argIndex = 2
        _stmt.bindText(_argIndex, query)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfShelfId: Int = getColumnIndexOrThrow(_stmt, "shelfId")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfBrand: Int = getColumnIndexOrThrow(_stmt, "brand")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfQuantity: Int = getColumnIndexOrThrow(_stmt, "quantity")
        val _columnIndexOfUnit: Int = getColumnIndexOrThrow(_stmt, "unit")
        val _columnIndexOfPurchaseDate: Int = getColumnIndexOrThrow(_stmt, "purchaseDate")
        val _columnIndexOfExpiryDate: Int = getColumnIndexOrThrow(_stmt, "expiryDate")
        val _columnIndexOfMinimumStockThreshold: Int = getColumnIndexOrThrow(_stmt,
            "minimumStockThreshold")
        val _columnIndexOfBarcode: Int = getColumnIndexOrThrow(_stmt, "barcode")
        val _columnIndexOfPhotoUri: Int = getColumnIndexOrThrow(_stmt, "photoUri")
        val _columnIndexOfNotes: Int = getColumnIndexOrThrow(_stmt, "notes")
        val _result: MutableList<ItemEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: ItemEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpShelfId: Long
          _tmpShelfId = _stmt.getLong(_columnIndexOfShelfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpBrand: String?
          if (_stmt.isNull(_columnIndexOfBrand)) {
            _tmpBrand = null
          } else {
            _tmpBrand = _stmt.getText(_columnIndexOfBrand)
          }
          val _tmpCategory: ItemCategory
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfCategory)
          }
          val _tmp_1: ItemCategory? = __converters.fromItemCategory(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'com.monstock.app.`data`.model.ItemCategory', but it was NULL.")
          } else {
            _tmpCategory = _tmp_1
          }
          val _tmpQuantity: Double
          _tmpQuantity = _stmt.getDouble(_columnIndexOfQuantity)
          val _tmpUnit: ItemUnit
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfUnit)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfUnit)
          }
          val _tmp_3: ItemUnit? = __converters.fromItemUnit(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'com.monstock.app.`data`.model.ItemUnit', but it was NULL.")
          } else {
            _tmpUnit = _tmp_3
          }
          val _tmpPurchaseDate: LocalDate?
          val _tmp_4: Long?
          if (_stmt.isNull(_columnIndexOfPurchaseDate)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getLong(_columnIndexOfPurchaseDate)
          }
          _tmpPurchaseDate = __converters.fromLocalDate(_tmp_4)
          val _tmpExpiryDate: LocalDate?
          val _tmp_5: Long?
          if (_stmt.isNull(_columnIndexOfExpiryDate)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getLong(_columnIndexOfExpiryDate)
          }
          _tmpExpiryDate = __converters.fromLocalDate(_tmp_5)
          val _tmpMinimumStockThreshold: Double?
          if (_stmt.isNull(_columnIndexOfMinimumStockThreshold)) {
            _tmpMinimumStockThreshold = null
          } else {
            _tmpMinimumStockThreshold = _stmt.getDouble(_columnIndexOfMinimumStockThreshold)
          }
          val _tmpBarcode: String?
          if (_stmt.isNull(_columnIndexOfBarcode)) {
            _tmpBarcode = null
          } else {
            _tmpBarcode = _stmt.getText(_columnIndexOfBarcode)
          }
          val _tmpPhotoUri: String?
          if (_stmt.isNull(_columnIndexOfPhotoUri)) {
            _tmpPhotoUri = null
          } else {
            _tmpPhotoUri = _stmt.getText(_columnIndexOfPhotoUri)
          }
          val _tmpNotes: String?
          if (_stmt.isNull(_columnIndexOfNotes)) {
            _tmpNotes = null
          } else {
            _tmpNotes = _stmt.getText(_columnIndexOfNotes)
          }
          _item =
              ItemEntity(_tmpId,_tmpShelfId,_tmpName,_tmpBrand,_tmpCategory,_tmpQuantity,_tmpUnit,_tmpPurchaseDate,_tmpExpiryDate,_tmpMinimumStockThreshold,_tmpBarcode,_tmpPhotoUri,_tmpNotes)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getItemsByCategory(category: ItemCategory): Flow<List<ItemEntity>> {
    val _sql: String = "SELECT * FROM items WHERE category = ? ORDER BY expiryDate ASC"
    return createFlow(__db, false, arrayOf("items")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: String? = __converters.itemCategoryToString(category)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, _tmp)
        }
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfShelfId: Int = getColumnIndexOrThrow(_stmt, "shelfId")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfBrand: Int = getColumnIndexOrThrow(_stmt, "brand")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfQuantity: Int = getColumnIndexOrThrow(_stmt, "quantity")
        val _columnIndexOfUnit: Int = getColumnIndexOrThrow(_stmt, "unit")
        val _columnIndexOfPurchaseDate: Int = getColumnIndexOrThrow(_stmt, "purchaseDate")
        val _columnIndexOfExpiryDate: Int = getColumnIndexOrThrow(_stmt, "expiryDate")
        val _columnIndexOfMinimumStockThreshold: Int = getColumnIndexOrThrow(_stmt,
            "minimumStockThreshold")
        val _columnIndexOfBarcode: Int = getColumnIndexOrThrow(_stmt, "barcode")
        val _columnIndexOfPhotoUri: Int = getColumnIndexOrThrow(_stmt, "photoUri")
        val _columnIndexOfNotes: Int = getColumnIndexOrThrow(_stmt, "notes")
        val _result: MutableList<ItemEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: ItemEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpShelfId: Long
          _tmpShelfId = _stmt.getLong(_columnIndexOfShelfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpBrand: String?
          if (_stmt.isNull(_columnIndexOfBrand)) {
            _tmpBrand = null
          } else {
            _tmpBrand = _stmt.getText(_columnIndexOfBrand)
          }
          val _tmpCategory: ItemCategory
          val _tmp_1: String?
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getText(_columnIndexOfCategory)
          }
          val _tmp_2: ItemCategory? = __converters.fromItemCategory(_tmp_1)
          if (_tmp_2 == null) {
            error("Expected NON-NULL 'com.monstock.app.`data`.model.ItemCategory', but it was NULL.")
          } else {
            _tmpCategory = _tmp_2
          }
          val _tmpQuantity: Double
          _tmpQuantity = _stmt.getDouble(_columnIndexOfQuantity)
          val _tmpUnit: ItemUnit
          val _tmp_3: String?
          if (_stmt.isNull(_columnIndexOfUnit)) {
            _tmp_3 = null
          } else {
            _tmp_3 = _stmt.getText(_columnIndexOfUnit)
          }
          val _tmp_4: ItemUnit? = __converters.fromItemUnit(_tmp_3)
          if (_tmp_4 == null) {
            error("Expected NON-NULL 'com.monstock.app.`data`.model.ItemUnit', but it was NULL.")
          } else {
            _tmpUnit = _tmp_4
          }
          val _tmpPurchaseDate: LocalDate?
          val _tmp_5: Long?
          if (_stmt.isNull(_columnIndexOfPurchaseDate)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getLong(_columnIndexOfPurchaseDate)
          }
          _tmpPurchaseDate = __converters.fromLocalDate(_tmp_5)
          val _tmpExpiryDate: LocalDate?
          val _tmp_6: Long?
          if (_stmt.isNull(_columnIndexOfExpiryDate)) {
            _tmp_6 = null
          } else {
            _tmp_6 = _stmt.getLong(_columnIndexOfExpiryDate)
          }
          _tmpExpiryDate = __converters.fromLocalDate(_tmp_6)
          val _tmpMinimumStockThreshold: Double?
          if (_stmt.isNull(_columnIndexOfMinimumStockThreshold)) {
            _tmpMinimumStockThreshold = null
          } else {
            _tmpMinimumStockThreshold = _stmt.getDouble(_columnIndexOfMinimumStockThreshold)
          }
          val _tmpBarcode: String?
          if (_stmt.isNull(_columnIndexOfBarcode)) {
            _tmpBarcode = null
          } else {
            _tmpBarcode = _stmt.getText(_columnIndexOfBarcode)
          }
          val _tmpPhotoUri: String?
          if (_stmt.isNull(_columnIndexOfPhotoUri)) {
            _tmpPhotoUri = null
          } else {
            _tmpPhotoUri = _stmt.getText(_columnIndexOfPhotoUri)
          }
          val _tmpNotes: String?
          if (_stmt.isNull(_columnIndexOfNotes)) {
            _tmpNotes = null
          } else {
            _tmpNotes = _stmt.getText(_columnIndexOfNotes)
          }
          _item =
              ItemEntity(_tmpId,_tmpShelfId,_tmpName,_tmpBrand,_tmpCategory,_tmpQuantity,_tmpUnit,_tmpPurchaseDate,_tmpExpiryDate,_tmpMinimumStockThreshold,_tmpBarcode,_tmpPhotoUri,_tmpNotes)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getItemByBarcode(barcode: String): ItemEntity? {
    val _sql: String = "SELECT * FROM items WHERE barcode = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, barcode)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfShelfId: Int = getColumnIndexOrThrow(_stmt, "shelfId")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfBrand: Int = getColumnIndexOrThrow(_stmt, "brand")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfQuantity: Int = getColumnIndexOrThrow(_stmt, "quantity")
        val _columnIndexOfUnit: Int = getColumnIndexOrThrow(_stmt, "unit")
        val _columnIndexOfPurchaseDate: Int = getColumnIndexOrThrow(_stmt, "purchaseDate")
        val _columnIndexOfExpiryDate: Int = getColumnIndexOrThrow(_stmt, "expiryDate")
        val _columnIndexOfMinimumStockThreshold: Int = getColumnIndexOrThrow(_stmt,
            "minimumStockThreshold")
        val _columnIndexOfBarcode: Int = getColumnIndexOrThrow(_stmt, "barcode")
        val _columnIndexOfPhotoUri: Int = getColumnIndexOrThrow(_stmt, "photoUri")
        val _columnIndexOfNotes: Int = getColumnIndexOrThrow(_stmt, "notes")
        val _result: ItemEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpShelfId: Long
          _tmpShelfId = _stmt.getLong(_columnIndexOfShelfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpBrand: String?
          if (_stmt.isNull(_columnIndexOfBrand)) {
            _tmpBrand = null
          } else {
            _tmpBrand = _stmt.getText(_columnIndexOfBrand)
          }
          val _tmpCategory: ItemCategory
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfCategory)
          }
          val _tmp_1: ItemCategory? = __converters.fromItemCategory(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'com.monstock.app.`data`.model.ItemCategory', but it was NULL.")
          } else {
            _tmpCategory = _tmp_1
          }
          val _tmpQuantity: Double
          _tmpQuantity = _stmt.getDouble(_columnIndexOfQuantity)
          val _tmpUnit: ItemUnit
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfUnit)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfUnit)
          }
          val _tmp_3: ItemUnit? = __converters.fromItemUnit(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'com.monstock.app.`data`.model.ItemUnit', but it was NULL.")
          } else {
            _tmpUnit = _tmp_3
          }
          val _tmpPurchaseDate: LocalDate?
          val _tmp_4: Long?
          if (_stmt.isNull(_columnIndexOfPurchaseDate)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getLong(_columnIndexOfPurchaseDate)
          }
          _tmpPurchaseDate = __converters.fromLocalDate(_tmp_4)
          val _tmpExpiryDate: LocalDate?
          val _tmp_5: Long?
          if (_stmt.isNull(_columnIndexOfExpiryDate)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getLong(_columnIndexOfExpiryDate)
          }
          _tmpExpiryDate = __converters.fromLocalDate(_tmp_5)
          val _tmpMinimumStockThreshold: Double?
          if (_stmt.isNull(_columnIndexOfMinimumStockThreshold)) {
            _tmpMinimumStockThreshold = null
          } else {
            _tmpMinimumStockThreshold = _stmt.getDouble(_columnIndexOfMinimumStockThreshold)
          }
          val _tmpBarcode: String?
          if (_stmt.isNull(_columnIndexOfBarcode)) {
            _tmpBarcode = null
          } else {
            _tmpBarcode = _stmt.getText(_columnIndexOfBarcode)
          }
          val _tmpPhotoUri: String?
          if (_stmt.isNull(_columnIndexOfPhotoUri)) {
            _tmpPhotoUri = null
          } else {
            _tmpPhotoUri = _stmt.getText(_columnIndexOfPhotoUri)
          }
          val _tmpNotes: String?
          if (_stmt.isNull(_columnIndexOfNotes)) {
            _tmpNotes = null
          } else {
            _tmpNotes = _stmt.getText(_columnIndexOfNotes)
          }
          _result =
              ItemEntity(_tmpId,_tmpShelfId,_tmpName,_tmpBrand,_tmpCategory,_tmpQuantity,_tmpUnit,_tmpPurchaseDate,_tmpExpiryDate,_tmpMinimumStockThreshold,_tmpBarcode,_tmpPhotoUri,_tmpNotes)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getItemById(id: Long): ItemEntity? {
    val _sql: String = "SELECT * FROM items WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfShelfId: Int = getColumnIndexOrThrow(_stmt, "shelfId")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfBrand: Int = getColumnIndexOrThrow(_stmt, "brand")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfQuantity: Int = getColumnIndexOrThrow(_stmt, "quantity")
        val _columnIndexOfUnit: Int = getColumnIndexOrThrow(_stmt, "unit")
        val _columnIndexOfPurchaseDate: Int = getColumnIndexOrThrow(_stmt, "purchaseDate")
        val _columnIndexOfExpiryDate: Int = getColumnIndexOrThrow(_stmt, "expiryDate")
        val _columnIndexOfMinimumStockThreshold: Int = getColumnIndexOrThrow(_stmt,
            "minimumStockThreshold")
        val _columnIndexOfBarcode: Int = getColumnIndexOrThrow(_stmt, "barcode")
        val _columnIndexOfPhotoUri: Int = getColumnIndexOrThrow(_stmt, "photoUri")
        val _columnIndexOfNotes: Int = getColumnIndexOrThrow(_stmt, "notes")
        val _result: ItemEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpShelfId: Long
          _tmpShelfId = _stmt.getLong(_columnIndexOfShelfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpBrand: String?
          if (_stmt.isNull(_columnIndexOfBrand)) {
            _tmpBrand = null
          } else {
            _tmpBrand = _stmt.getText(_columnIndexOfBrand)
          }
          val _tmpCategory: ItemCategory
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfCategory)
          }
          val _tmp_1: ItemCategory? = __converters.fromItemCategory(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'com.monstock.app.`data`.model.ItemCategory', but it was NULL.")
          } else {
            _tmpCategory = _tmp_1
          }
          val _tmpQuantity: Double
          _tmpQuantity = _stmt.getDouble(_columnIndexOfQuantity)
          val _tmpUnit: ItemUnit
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfUnit)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfUnit)
          }
          val _tmp_3: ItemUnit? = __converters.fromItemUnit(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'com.monstock.app.`data`.model.ItemUnit', but it was NULL.")
          } else {
            _tmpUnit = _tmp_3
          }
          val _tmpPurchaseDate: LocalDate?
          val _tmp_4: Long?
          if (_stmt.isNull(_columnIndexOfPurchaseDate)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getLong(_columnIndexOfPurchaseDate)
          }
          _tmpPurchaseDate = __converters.fromLocalDate(_tmp_4)
          val _tmpExpiryDate: LocalDate?
          val _tmp_5: Long?
          if (_stmt.isNull(_columnIndexOfExpiryDate)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getLong(_columnIndexOfExpiryDate)
          }
          _tmpExpiryDate = __converters.fromLocalDate(_tmp_5)
          val _tmpMinimumStockThreshold: Double?
          if (_stmt.isNull(_columnIndexOfMinimumStockThreshold)) {
            _tmpMinimumStockThreshold = null
          } else {
            _tmpMinimumStockThreshold = _stmt.getDouble(_columnIndexOfMinimumStockThreshold)
          }
          val _tmpBarcode: String?
          if (_stmt.isNull(_columnIndexOfBarcode)) {
            _tmpBarcode = null
          } else {
            _tmpBarcode = _stmt.getText(_columnIndexOfBarcode)
          }
          val _tmpPhotoUri: String?
          if (_stmt.isNull(_columnIndexOfPhotoUri)) {
            _tmpPhotoUri = null
          } else {
            _tmpPhotoUri = _stmt.getText(_columnIndexOfPhotoUri)
          }
          val _tmpNotes: String?
          if (_stmt.isNull(_columnIndexOfNotes)) {
            _tmpNotes = null
          } else {
            _tmpNotes = _stmt.getText(_columnIndexOfNotes)
          }
          _result =
              ItemEntity(_tmpId,_tmpShelfId,_tmpName,_tmpBrand,_tmpCategory,_tmpQuantity,_tmpUnit,_tmpPurchaseDate,_tmpExpiryDate,_tmpMinimumStockThreshold,_tmpBarcode,_tmpPhotoUri,_tmpNotes)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getTotalItemCount(): Flow<Int> {
    val _sql: String = "SELECT COUNT(*) FROM items"
    return createFlow(__db, false, arrayOf("items")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: Int
        if (_stmt.step()) {
          val _tmp: Int
          _tmp = _stmt.getLong(0).toInt()
          _result = _tmp
        } else {
          _result = 0
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteItemById(id: Long) {
    val _sql: String = "DELETE FROM items WHERE id = ?"
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

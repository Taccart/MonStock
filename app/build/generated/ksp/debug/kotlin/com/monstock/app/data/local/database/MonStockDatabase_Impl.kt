package com.monstock.app.`data`.local.database

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.monstock.app.`data`.local.dao.ItemDao
import com.monstock.app.`data`.local.dao.ItemDao_Impl
import com.monstock.app.`data`.local.dao.PantryDao
import com.monstock.app.`data`.local.dao.PantryDao_Impl
import com.monstock.app.`data`.local.dao.ShelfDao
import com.monstock.app.`data`.local.dao.ShelfDao_Impl
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class MonStockDatabase_Impl : MonStockDatabase() {
  private val _pantryDao: Lazy<PantryDao> = lazy {
    PantryDao_Impl(this)
  }

  private val _shelfDao: Lazy<ShelfDao> = lazy {
    ShelfDao_Impl(this)
  }

  private val _itemDao: Lazy<ItemDao> = lazy {
    ItemDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(2,
        "dc2807cedf2642f094a6c7b8f5508a2b", "c7fbe25dc980b8b63a9c579456ce6ee3") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `pantries` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `shelves` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `pantryId` INTEGER NOT NULL, `name` TEXT NOT NULL, `capacity` INTEGER, FOREIGN KEY(`pantryId`) REFERENCES `pantries`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_shelves_pantryId` ON `shelves` (`pantryId`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `items` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `shelfId` INTEGER NOT NULL, `name` TEXT NOT NULL, `brand` TEXT, `category` TEXT NOT NULL, `quantity` REAL NOT NULL, `unit` TEXT NOT NULL, `purchaseDate` INTEGER, `expiryDate` INTEGER, `minimumStockThreshold` REAL, `barcode` TEXT, `photoUri` TEXT, `notes` TEXT, FOREIGN KEY(`shelfId`) REFERENCES `shelves`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_items_shelfId` ON `items` (`shelfId`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'dc2807cedf2642f094a6c7b8f5508a2b')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `pantries`")
        connection.execSQL("DROP TABLE IF EXISTS `shelves`")
        connection.execSQL("DROP TABLE IF EXISTS `items`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        connection.execSQL("PRAGMA foreign_keys = ON")
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection):
          RoomOpenDelegate.ValidationResult {
        val _columnsPantries: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsPantries.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsPantries.put("name", TableInfo.Column("name", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysPantries: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesPantries: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoPantries: TableInfo = TableInfo("pantries", _columnsPantries, _foreignKeysPantries,
            _indicesPantries)
        val _existingPantries: TableInfo = read(connection, "pantries")
        if (!_infoPantries.equals(_existingPantries)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |pantries(com.monstock.app.data.local.entity.PantryEntity).
              | Expected:
              |""".trimMargin() + _infoPantries + """
              |
              | Found:
              |""".trimMargin() + _existingPantries)
        }
        val _columnsShelves: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsShelves.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsShelves.put("pantryId", TableInfo.Column("pantryId", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsShelves.put("name", TableInfo.Column("name", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsShelves.put("capacity", TableInfo.Column("capacity", "INTEGER", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysShelves: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysShelves.add(TableInfo.ForeignKey("pantries", "CASCADE", "NO ACTION",
            listOf("pantryId"), listOf("id")))
        val _indicesShelves: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesShelves.add(TableInfo.Index("index_shelves_pantryId", false, listOf("pantryId"),
            listOf("ASC")))
        val _infoShelves: TableInfo = TableInfo("shelves", _columnsShelves, _foreignKeysShelves,
            _indicesShelves)
        val _existingShelves: TableInfo = read(connection, "shelves")
        if (!_infoShelves.equals(_existingShelves)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |shelves(com.monstock.app.data.local.entity.ShelfEntity).
              | Expected:
              |""".trimMargin() + _infoShelves + """
              |
              | Found:
              |""".trimMargin() + _existingShelves)
        }
        val _columnsItems: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsItems.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsItems.put("shelfId", TableInfo.Column("shelfId", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsItems.put("name", TableInfo.Column("name", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsItems.put("brand", TableInfo.Column("brand", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsItems.put("category", TableInfo.Column("category", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsItems.put("quantity", TableInfo.Column("quantity", "REAL", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsItems.put("unit", TableInfo.Column("unit", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsItems.put("purchaseDate", TableInfo.Column("purchaseDate", "INTEGER", false, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsItems.put("expiryDate", TableInfo.Column("expiryDate", "INTEGER", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsItems.put("minimumStockThreshold", TableInfo.Column("minimumStockThreshold", "REAL",
            false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsItems.put("barcode", TableInfo.Column("barcode", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsItems.put("photoUri", TableInfo.Column("photoUri", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsItems.put("notes", TableInfo.Column("notes", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysItems: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysItems.add(TableInfo.ForeignKey("shelves", "CASCADE", "NO ACTION",
            listOf("shelfId"), listOf("id")))
        val _indicesItems: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesItems.add(TableInfo.Index("index_items_shelfId", false, listOf("shelfId"),
            listOf("ASC")))
        val _infoItems: TableInfo = TableInfo("items", _columnsItems, _foreignKeysItems,
            _indicesItems)
        val _existingItems: TableInfo = read(connection, "items")
        if (!_infoItems.equals(_existingItems)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |items(com.monstock.app.data.local.entity.ItemEntity).
              | Expected:
              |""".trimMargin() + _infoItems + """
              |
              | Found:
              |""".trimMargin() + _existingItems)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "pantries", "shelves", "items")
  }

  public override fun clearAllTables() {
    super.performClear(true, "pantries", "shelves", "items")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(PantryDao::class, PantryDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(ShelfDao::class, ShelfDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(ItemDao::class, ItemDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override
      fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>):
      List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun pantryDao(): PantryDao = _pantryDao.value

  public override fun shelfDao(): ShelfDao = _shelfDao.value

  public override fun itemDao(): ItemDao = _itemDao.value
}

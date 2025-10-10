package br.com.naitzel.hertz.data.local.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.naitzel.hertz.data.local.dao.FinancialDao
import br.com.naitzel.hertz.data.local.dao.ServiceOrderDao
import br.com.naitzel.hertz.data.local.entity.FinancialEntity
import br.com.naitzel.hertz.data.local.entity.ServiceOrderEntity

@Database(
    entities = [
        ServiceOrderEntity::class,
        FinancialEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class HertzDatabase : RoomDatabase() {

    abstract fun budgetDao(): ServiceOrderDao

    abstract fun financialDao(): FinancialDao

    companion object {
        @Volatile
        private var INSTANCE: HertzDatabase? = null

        fun getDatabase(context: android.content.Context): HertzDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HertzDatabase::class.java,
                    "hertz_db"
                )
                    .fallbackToDestructiveMigration(true)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
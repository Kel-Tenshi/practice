package ci.nsu.mobile.main.data.repository

import ci.nsu.mobile.main.data.dao.DepositDao
import ci.nsu.mobile.main.data.entity.DepositCalculation
import kotlinx.coroutines.flow.Flow

class DepositRepository(private val depositDao: DepositDao) {

    val allCalculations: Flow<List<DepositCalculation>> = depositDao.getAllCalculations()

    suspend fun insert(calculation: DepositCalculation) {
        depositDao.insertCalculation(calculation)
    }
}
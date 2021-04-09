package tads.eaj.ufrn.swipedragdroprecyclerview

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Fruta(var nome: String, var img: Int) {
    var bitten: Boolean = false
    @PrimaryKey(autoGenerate = true) var id:Long = 0L
}
package tads.eaj.ufrn.swipedragdroprecyclerview

import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import tads.eaj.ufrn.swipedragdroprecyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val db: AppDatabase by lazy {
        Room.databaseBuilder(this, AppDatabase::class.java, "frutas.sqlite")
            .allowMainThreadQueries()
            .build()
    }

    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        preparabanco()

        val adapter = FrutaAdapter(db.frutaDao().listAll())
        binding.recyclerView.adapter = adapter

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(  UP or DOWN, START or END){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                Log.i("AULA17", "OnMove")
                val fromPosition = viewHolder.adapterPosition
                val toPosition = target.adapterPosition
                adapter.mover(fromPosition, toPosition)
                return true// true se moveu, se não moveu, retorne falso
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val posicao = viewHolder.adapterPosition
                adapter.removerComTempo(posicao)
            }

            override fun onChildDraw(c: Canvas,recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,  dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                val itemView = viewHolder.itemView
                val background = ColorDrawable(resources.getColor(R.color.red))
                // not sure why, but this method get's called for viewholder that are already swiped away
                if (viewHolder.adapterPosition == -1) {
                    // not interested in those
                    return
                }
                Log.i("AULA17", "dx = $dX")
                // Here, if dX > 0 then swiping right.
                // If dX < 0 then swiping left.
                // If dX == 0 then at at start position.
                // draw red background
                if (dX < 0) {
                    Log.i("AULA17", "dX < 0")
                    background.setBounds(
                        (itemView.right + dX).toInt(),
                        itemView.top,
                        itemView.right,
                        itemView.bottom
                    )
                } else if (dX > 0) {
                    Log.i("AULA17", "dX > 0")
                    background.setBounds(
                        itemView.left,
                        itemView.top,
                        (dX).toInt(),
                        itemView.bottom
                    )
                }
                background.draw(c)
                super.onChildDraw(c,recyclerView,viewHolder,dX, dY,actionState,isCurrentlyActive)
            }

        })

        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    fun preparabanco() {
        db.frutaDao().deleteAll()

        val f1 = Fruta("Pera", R.drawable.fruit)
        val f2 = Fruta("Uva", R.drawable.fruit)
        val f3 = Fruta("Goiaba", R.drawable.fruit)
        val f4 = Fruta("Maça", R.drawable.fruit)
        val f5 = Fruta("Tomate", R.drawable.fruit)
        val f6 = Fruta("Banana", R.drawable.fruit)
        val f7 = Fruta("Caju", R.drawable.fruit)
        val f8 = Fruta("Maracuja", R.drawable.fruit)
        val f9 = Fruta("Melancia", R.drawable.fruit)
        val f10 = Fruta("Melão", R.drawable.fruit)
        val f11 = Fruta("Mamão", R.drawable.fruit)
        val f12 = Fruta("Tamarindo", R.drawable.fruit)
        val f13 = Fruta("Acerola", R.drawable.fruit)
        val f14 = Fruta("Jambo", R.drawable.fruit)
        val f15 = Fruta("Graviola", R.drawable.fruit)
        val f16 = Fruta("Pinha", R.drawable.fruit)

        db.frutaDao().inserirAll(
            f1,
            f2,
            f3,
            f4,
            f5,
            f6,
            f7,
            f8,
            f9,
            f10,
            f11,
            f12,
            f13,
            f14,
            f15,
            f16
        )
    }
}
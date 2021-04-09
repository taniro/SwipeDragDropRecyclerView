package tads.eaj.ufrn.swipedragdroprecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import java.util.Collections.swap

class FrutaAdapter(var frutas:MutableList<Fruta>) : RecyclerView.Adapter<FrutaAdapter.FrutaViewHolder>() {

    var listaFrutasRemover = ArrayList<Fruta>()
    var removerJobs: HashMap<Fruta, Job> = HashMap()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FrutaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.novo_fruta_inflater, parent, false);
        return FrutaViewHolder(view)
    }

    override fun getItemCount(): Int {
        return frutas.size
    }

    override fun onBindViewHolder(holder: FrutaViewHolder, position: Int) {

        val frutaescolhida = frutas[position]

        holder.textViewNome.text = frutaescolhida.nome
        holder.img.setImageResource(frutaescolhida.img)
        if (frutaescolhida.bitten) {
            holder.img.setImageResource(R.drawable.bitten)
        } else {
            holder.img.setImageResource(R.drawable.fruit)
        }

        if (listaFrutasRemover.contains(frutaescolhida)) {
            //view do swipe/delete
            holder.layoutNormal.setVisibility(View.GONE)

            holder.layoutGone.setVisibility(View.VISIBLE)
            holder.undoButton.setVisibility(View.VISIBLE)

            holder.undoButton.setOnClickListener {
                // usou o undo, remover a pendingRennable
                val job = removerJobs[frutaescolhida]
                job?.cancel()
                removerJobs.remove(frutaescolhida)
                listaFrutasRemover.remove(frutaescolhida)
                notifyItemChanged(position)
            }
        } else {
            //mostra o padrão
            holder.textViewNome.setText(frutaescolhida.nome)
            holder.layoutNormal.setVisibility(View.VISIBLE)
            holder.layoutGone.setVisibility(View.GONE)
            holder.undoButton.setVisibility(View.GONE)
            holder.undoButton.setOnClickListener(null)
            if (frutaescolhida.bitten) {
                holder.img.setImageResource(R.drawable.bitten)
            } else {
                holder.img.setImageResource(R.drawable.fruit)
            }

            holder.img.setOnClickListener{
                frutaescolhida.bitten = true
                notifyItemChanged(position)
            }
        }
    }


    fun remover (position: Int){
        val fruta = frutas[position]
        if (frutas.contains(fruta)){
            frutas.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun removerComTempo(position: Int) {

        val fruta = frutas[position]
        if (!listaFrutasRemover.contains(fruta)) {
            listaFrutasRemover.add(fruta)
            notifyItemChanged(position)

            val job = GlobalScope.launch(Dispatchers.Main) {
                delay(3000)
                remover(position)
            }
            removerJobs[fruta] = job
        }
    }

    fun mover(fromPosition: Int, toPosition: Int) {

        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                swap(frutas, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                swap(frutas, i, i - 1)
            }
        }

        notifyItemMoved(fromPosition, toPosition)
        notifyItemChanged(toPosition)
        notifyItemChanged(fromPosition)
    }



    class FrutaViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val textViewNome: TextView = v.findViewById(R.id.nomeFruta)
        val img: ImageView = v.findViewById(R.id.imgFruta)
        val layoutNormal: LinearLayout = v.findViewById(R.id.layout_normal)
        val layoutGone:LinearLayout = v.findViewById(R.id.layout_gone)
        val undoButton: Button = v.findViewById(R.id.undo_button)
    }
}
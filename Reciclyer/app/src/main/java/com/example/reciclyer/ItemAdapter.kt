import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.reciclyer.Item
import com.example.reciclyer.R

class ItemAdapter(
    private val itemList: List<Item>, // Lista de elementos (puede ser cualquier objeto)
    private val onItemClick: (Int) -> Unit // Lambda para manejar el clic y pasar la posición
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {


    // ViewHolder que representa cada elemento
    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView)
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val button: Button = view.findViewById(R.id.button)

        init {
            itemView.setOnClickListener {
                onItemClick(adapterPosition) // Llama a la lambda con la posición del elemento
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.textView.text = item.text
        holder.imageView.setImageResource(item.imageResId)
        holder.button.setOnClickListener {
            // Aquí puedes definir la acción del botón
        }
    }

    override fun getItemCount() = itemList.size



}

public class VistaReyclerView extends lista_item_contactos.Adapter<VistaReyclerView.CustomViewHolder> {
    private List<Contactos> itemList; // Lista de datos para los elementos de la lista

    public VistaReyclerView(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el archivo de diseño para los elementos individuales de la lista
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Item item = itemList.get(position);
        // Configurar los datos en la vista
        holder.viewNombre.setText(item.getNombre());
        holder.viewApellido.setText(item.getApellido());
        holder.viewPin.setText(item.getPin());

        // Configurar el evento del botón
        holder.btnUbicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acciones a realizar cuando se hace clic en el botón "Ubicar"
                // Puedes acceder a los datos del elemento en esta posición utilizando item
                // Ejemplo: String nombre = item.getNombre();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        // Vistas dentro del elemento individual de la lista
        public TextView viewNombre;
        public TextView viewApellido;
        public TextView viewPin;
        public Button btnUbicar;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inicializar las vistas
            viewNombre = itemView.findViewById(R.id.viewNombre);
            viewApellido = itemView.findViewById(R.id.viewApellido);
            viewPin = itemView.findViewById(R.id.viewPin);
            btnUbicar = itemView.findViewById(R.id.btnUbicar);
        }
    }
}
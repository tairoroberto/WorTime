package br.com.trmasolucoes.worktime.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.List;

import br.com.trmasolucoes.worktime.R;
import br.com.trmasolucoes.worktime.domain.Registro;
import br.com.trmasolucoes.worktime.interfaces.RecyclerViewOnClickListenerHack;
import br.com.trmasolucoes.worktime.util.DateUtil;

public class RegistroAdapter extends RecyclerView.Adapter<RegistroAdapter.ViewHolder> {
    private Context mContext;
    private List<Registro> mList;
    private LayoutInflater mLayoutInflater;
    private static RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private static final String TAG = "Script";

    public RegistroAdapter(Context context, List<Registro> list){
        mContext = context;
        mList = list;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view;

        view = mLayoutInflater.inflate(R.layout.item_registro_card, viewGroup, false);
        ViewHolder mvh = new ViewHolder(view);
        return mvh;
    }

    @Override
    public void onBindViewHolder(ViewHolder myViewHolder, int position) {

        Registro registro = mList.get(position);
        String[] data = DateUtil.getDateToString(registro.getData()).split(" ");
        myViewHolder.txtHorario.setText(data[1]);

        try{
            YoYo.with(Techniques.BounceIn)
                    .duration(700)
                    .playOn(myViewHolder.itemView);
        }
        catch(Exception e){}

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        mRecyclerViewOnClickListenerHack = r;
    }

    public void addListItem(Registro registro, int position){
        mList.add(registro);
        notifyItemInserted(position);
    }

    public void removeListItem(int position){
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imgEditar;
        public ImageView imgFoto;
        public TextView txtHorario;
        public ImageView imgObservacoes;
        public ImageView imgDeletar;

        public ViewHolder(View itemView) {
            super(itemView);

            imgEditar = (ImageView) itemView.findViewById(R.id.imgEditar);
            imgFoto = (ImageView) itemView.findViewById(R.id.imgFoto);
            txtHorario = (TextView) itemView.findViewById(R.id.txtHorario);
            imgObservacoes = (ImageView) itemView.findViewById(R.id.imgObservacoes);
            imgDeletar = (ImageView) itemView.findViewById(R.id.imgDeletar);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mRecyclerViewOnClickListenerHack != null){
                mRecyclerViewOnClickListenerHack.onClickListener(v, getAdapterPosition());
            }
        }
    }
}

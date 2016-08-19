package br.com.trmasolucoes.worktime.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.trmasolucoes.worktime.R;
import br.com.trmasolucoes.worktime.adapters.RegistroAdapter;
import br.com.trmasolucoes.worktime.database.HorarioDAO;
import br.com.trmasolucoes.worktime.database.RegistroDAO;
import br.com.trmasolucoes.worktime.domain.Registro;
import br.com.trmasolucoes.worktime.interfaces.RecyclerViewOnClickListenerHack;
import br.com.trmasolucoes.worktime.util.DateUtil;


public class RegistroFragment extends Fragment implements RecyclerViewOnClickListenerHack {
    protected static final String TAG = "Script";
    private static final String KEY_DATE = "date";

    protected RecyclerView mRecyclerView;
    protected List<Registro> registros = new ArrayList<>();
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    private RegistroDAO registroDAO;
    private RegistroAdapter adapter;
    private String data;
    private Registro registro;
    private TextView txtSaldoHorasDia;
    private TextView txtPrevisaoSaida;
    private HorarioDAO horarioDAO;

    public static RegistroFragment newInstance(long date) {
        RegistroFragment fragment = new RegistroFragment();
        Bundle args = new Bundle();
        args.putLong(KEY_DATE, date);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){
            registros = savedInstanceState.getParcelableArrayList("registros");
        }

        final long millis = getArguments().getLong(KEY_DATE);
        if (millis > 0) {
            final Context context = getActivity();
            if (context != null) {
                registroDAO = new RegistroDAO(context);
                registros = registroDAO.getByDate(DateUtil.getFormattedDate(context, millis, null));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_registro, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        txtPrevisaoSaida = (TextView) view.findViewById(R.id.txtPrevisao);
        txtSaldoHorasDia = (TextView) view.findViewById(R.id.txtSaldoDia);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        /** Atualiza a lista de registros */
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_swipe);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                registroDAO = new RegistroDAO(getActivity());
                horarioDAO = new HorarioDAO(getActivity());
                final long millis = getArguments().getLong(KEY_DATE);
                registros = registroDAO.getByDate(DateUtil.getFormattedDate(getActivity(), millis,null));
                adapter = new RegistroAdapter(getActivity(), registros, getFragmentManager());
                mRecyclerView.setAdapter(adapter);
                mSwipeRefreshLayout.setRefreshing(false);
                ajustarHorarioSaida(registros, registroDAO, horarioDAO);
            }
        });
        mRecyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity(), mRecyclerView, this));

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        /** Busca os dados dos registros */
        registroDAO = new RegistroDAO(getActivity());
        adapter = new RegistroAdapter(getActivity(), registros, getFragmentManager());
        mRecyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onClickListener(View view, int position) {
        Toast.makeText(getActivity(), "Posição: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLongPressClickListener(View view, int position) {
        //Toast.makeText(getActivity(), "onLongPressClickListener(): " + position, Toast.LENGTH_SHORT).show();
    }


    private static class RecyclerViewTouchListener implements RecyclerView.OnItemTouchListener {
        private Context mContext;
        private GestureDetector mGestureDetector;
        private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

        public RecyclerViewTouchListener(Context c, final RecyclerView rv, RecyclerViewOnClickListenerHack rvoclh){
            mContext = c;
            mRecyclerViewOnClickListenerHack = rvoclh;

            mGestureDetector = new GestureDetector(mContext, new GestureDetector.SimpleOnGestureListener(){
                @Override
                public void onLongPress(MotionEvent e) {
                    super.onLongPress(e);

                    View cv = rv.findChildViewUnder(e.getX(), e.getY());

                    if(cv != null && mRecyclerViewOnClickListenerHack != null){
                        mRecyclerViewOnClickListenerHack.onLongPressClickListener(cv,
                                rv.getChildAdapterPosition(cv) );
                    }
                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    View cv = rv.findChildViewUnder(e.getX(), e.getY());

                    if(cv != null && mRecyclerViewOnClickListenerHack != null){
                        mRecyclerViewOnClickListenerHack.onClickListener(cv,
                                rv.getChildAdapterPosition(cv) );
                    }

                    return(true);
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            mGestureDetector.onTouchEvent(e);
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {}

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("registros", (ArrayList<Registro>) registros);
    }

    private void ajustarHorarioSaida(List<Registro> registros, RegistroDAO registroDAO, HorarioDAO horarioDAO) {

        float entrada = 0;
        float almoco = 0;
        float almocoRetorno = 0;
        float saida = 0;
        float total = 0;


        for (Registro registro : registros) {
            /** Separa a string "-" da data e coloca no textView */
            String[] data = DateUtil.getDateToString(registro.getData()).split(" ");
            String[] horario = data[1].split(":");

            if (registro.getTipo().equalsIgnoreCase("entrada")){
                entrada = getHorarioFloat(horario[0] +":"+ horario[1]);

            }else if (registro.getTipo().equalsIgnoreCase("almoco")){
                almoco = getHorarioFloat(horario[0] +":"+ horario[1]);

            }else if (registro.getTipo().equalsIgnoreCase("almoco_retorno")){
                almocoRetorno = getHorarioFloat(horario[0] +":"+ horario[1]);

            }else if (registro.getTipo().equalsIgnoreCase("saida")){
                saida = getHorarioFloat(horario[0] +":"+ horario[1]);
            }

        }

        if (entrada > 0 && almoco > 0 && almocoRetorno > 0 && saida > 0){
            total = (saida - almocoRetorno) + (almoco - entrada);

        }else if (entrada > 0 && almoco > 0 && almocoRetorno > 0){
            total = almocoRetorno + (almoco - entrada);

        }else if (entrada > 0 && almoco > 0){
            total = almoco - entrada;

        }else {
            total = 0;
        }
        DecimalFormat df = new DecimalFormat("0.00");
        String.valueOf(df.format(total));

    }

    private static float getHorarioFloat(String horario){
        if (horario == null){
            return 0;
        }
        return Float.parseFloat(horario.replace(":","."));
    }
}

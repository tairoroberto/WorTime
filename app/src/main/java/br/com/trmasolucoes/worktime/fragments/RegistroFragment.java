package br.com.trmasolucoes.worktime.fragments;

import android.content.Context;
import android.content.Intent;
import android.location.SettingInjectorService;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

import br.com.trmasolucoes.worktime.R;
import br.com.trmasolucoes.worktime.RelatorioActivity;
import br.com.trmasolucoes.worktime.SettingsActivity;
import br.com.trmasolucoes.worktime.adapters.RegistroAdapter;
import br.com.trmasolucoes.worktime.database.RegistroDAO;
import br.com.trmasolucoes.worktime.domain.Registro;
import br.com.trmasolucoes.worktime.interfaces.RecyclerViewOnClickListenerHack;
import br.com.trmasolucoes.worktime.util.DateUtil;


public class RegistroFragment extends Fragment implements RecyclerViewOnClickListenerHack, View.OnClickListener {
    protected static final String TAG = "Script";
    protected RecyclerView mRecyclerView;
    protected List<Registro> mList = new ArrayList<>();
    protected FloatingActionMenu fab;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    private RegistroDAO registroDAO;
    private RegistroAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){
            mList = savedInstanceState.getParcelableArrayList("mList");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_registro, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    fab.hideMenuButton(true);
                } else {
                    fab.showMenuButton(true);
                }
            }
        });

        /** Atualiza a lista de registros */
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_swipe);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                registroDAO = new RegistroDAO(getActivity());
                mList = registroDAO.getByDate(DateUtil.getDateToString(DateUtil.getDataHoje()));
                adapter = new RegistroAdapter(getActivity(), mList);
                mRecyclerView.setAdapter(adapter);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        mRecyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity(), mRecyclerView, this));

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        /** Busca os dados dos registros */
        registroDAO = new RegistroDAO(getActivity());
        mList = registroDAO.getByDate(DateUtil.getDateToString(DateUtil.getDataHoje()));
        adapter = new RegistroAdapter(getActivity(), mList);
        mRecyclerView.setAdapter(adapter);
        setFloatingActionButton(view);
        return view;
    }


    public void setFloatingActionButton(View view){
        //fab = (FloatingActionMenu) getActivity().findViewById(R.id.fab);
        fab = (FloatingActionMenu) view.findViewById(R.id.fab);
        fab.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean b) {
                //Toast.makeText(getActivity(), "Is menu opened? " + (b ? "true" : "false"), Toast.LENGTH_SHORT).show();
            }
        });
        fab.showMenuButton(true);
        fab.setClosedOnTouchOutside(true);

        FloatingActionButton fabSoundCloud = (FloatingActionButton) view.findViewById(R.id.fab_registrar);
        FloatingActionButton fabYoutube = (FloatingActionButton) view.findViewById(R.id.fab_relatorios);
        FloatingActionButton fabGoogle_plus = (FloatingActionButton) view.findViewById(R.id.fab_settings);
        //FloatingActionButton fabFacebook = (FloatingActionButton) view.findViewById(R.id.fab_hoje);

        fabSoundCloud.setOnClickListener(this);
        fabYoutube.setOnClickListener(this);
        fabGoogle_plus.setOnClickListener(this);
        //fabFacebook.setOnClickListener(this);
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
    public void onClick(View v) {
        Intent intent;

        switch( v.getId() ){
            case R.id.fab_registrar:
                Toast.makeText(getActivity(), "Registrar", Toast.LENGTH_SHORT).show();

                Registro registro = new Registro(1, DateUtil.getDataHoje(),DateUtil.getDataHoje(),"almoço","2016-05-21-almoco","Observações teste tairo");
                registroDAO = new RegistroDAO(getActivity());
                registroDAO.insert(registro);
                mList = registroDAO.getAll();
                adapter = new RegistroAdapter(getActivity(), mList);
                mRecyclerView.setAdapter(adapter);

                break;
            case R.id.fab_relatorios:
                Toast.makeText(getActivity(), "Relatórios", Toast.LENGTH_SHORT).show();
                intent = new Intent(getActivity(), RelatorioActivity.class);
                startActivity(intent);
                break;
            case R.id.fab_settings:
                Toast.makeText(getActivity(), "Configurações", Toast.LENGTH_SHORT).show();
                intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);

                break;
            //case R.id.fab_hoje:
            //    Toast.makeText(getActivity(), "Hoje", Toast.LENGTH_SHORT).show();
            //    break;
        }

        fab.close(true);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("mList", (ArrayList<Registro>) mList);
    }
}
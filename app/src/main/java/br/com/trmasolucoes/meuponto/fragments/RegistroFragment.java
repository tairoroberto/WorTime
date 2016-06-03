package br.com.trmasolucoes.meuponto.fragments;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

import br.com.trmasolucoes.meuponto.R;
import br.com.trmasolucoes.meuponto.adapters.RegistroAdapter;
import br.com.trmasolucoes.meuponto.database.RegistroDAO;
import br.com.trmasolucoes.meuponto.domain.Registro;
import br.com.trmasolucoes.meuponto.interfaces.RecyclerViewOnClickListenerHack;
import br.com.trmasolucoes.meuponto.util.DateUtil;


public class RegistroFragment extends Fragment implements RecyclerViewOnClickListenerHack {
    protected static final String TAG = "Script";
    private static final String ARG_DATA_REGISTRO = "data_registro";
    protected RecyclerView mRecyclerView;
    protected List<Registro> mList = new ArrayList<>();
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    private RegistroDAO registroDAO;
    private RegistroAdapter adapter;
    private int data_registro_page;

    public static RegistroFragment newInstance(int dataRegitro) {
        RegistroFragment fragment = new RegistroFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_DATA_REGISTRO, dataRegitro);
        fragment.setArguments(args);
        return fragment;
    }

    public static RegistroFragment newInstance(int dataRegitro, String data) {
        RegistroFragment fragment = new RegistroFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_DATA_REGISTRO, dataRegitro);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){
            mList = savedInstanceState.getParcelableArrayList("mList");
        }
        if (getArguments() != null) {
            this.data_registro_page = getArguments().getInt(ARG_DATA_REGISTRO);
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

       // Toast.makeText(getActivity(), "" + getArguments().getInt(ARG_DATA_REGISTRO), Toast.LENGTH_SHORT).show();
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
        outState.putParcelableArrayList("mList", (ArrayList<Registro>) mList);
        if (getArguments() != null) {
            this.data_registro_page = getArguments().getInt(ARG_DATA_REGISTRO);
        }
    }
}

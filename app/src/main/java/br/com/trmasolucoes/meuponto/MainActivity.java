package br.com.trmasolucoes.meuponto;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import br.com.trmasolucoes.meuponto.database.RegistroDAO;
import br.com.trmasolucoes.meuponto.domain.Registro;
import br.com.trmasolucoes.meuponto.fragments.RegistroFragment;
import br.com.trmasolucoes.meuponto.util.DateUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "Script";
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    protected FloatingActionMenu fab;
    private RegistroDAO registroDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        setFloatingActionButton();
        int[] dia = DateUtil.getDayMonthYear();
        Log.i(TAG, "Data: " + dia[0] +"/"+dia[1]+"/"+dia[2]);
        mViewPager.setCurrentItem(dia[0]);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends android.support.v4.app.FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return RegistroFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show total pages.
            return DateUtil.getLastDayMonth();
        }
    }

    public void setFloatingActionButton(){
        //fab = (FloatingActionMenu) getActivity().findViewById(R.id.fab);
        fab = (FloatingActionMenu) findViewById(R.id.fab);
        fab.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean b) {
                //Toast.makeText(getActivity(), "Is menu opened? " + (b ? "true" : "false"), Toast.LENGTH_SHORT).show();
            }
        });
        fab.showMenuButton(true);
        fab.setClosedOnTouchOutside(true);

        FloatingActionButton fabSoundCloud = (FloatingActionButton) findViewById(R.id.fab_registrar);
        FloatingActionButton fabYoutube = (FloatingActionButton) findViewById(R.id.fab_relatorios);
        FloatingActionButton fabGoogle_plus = (FloatingActionButton) findViewById(R.id.fab_settings);
        //FloatingActionButton fabFacebook = (FloatingActionButton) view.findViewById(R.id.fab_hoje);

        assert fabSoundCloud != null;
        fabSoundCloud.setOnClickListener(this);
        assert fabYoutube != null;
        fabYoutube.setOnClickListener(this);
        assert fabGoogle_plus != null;
        fabGoogle_plus.setOnClickListener(this);
        //fabFacebook.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch( v.getId() ){
            case R.id.fab_registrar:
                Toast.makeText(MainActivity.this, "Registrar", Toast.LENGTH_SHORT).show();

                Registro registro = new Registro(1, DateUtil.getDataHoje(),DateUtil.getDataHoje(),"almoço","2016-05-21-almoco","Observações teste tairo");
                registroDAO = new RegistroDAO(MainActivity.this);
                registroDAO.insert(registro);

                break;
            case R.id.fab_relatorios:
                Toast.makeText(MainActivity.this, "Relatórios", Toast.LENGTH_SHORT).show();
                intent = new Intent(MainActivity.this, RelatorioActivity.class);
                startActivity(intent);
                break;
            case R.id.fab_settings:
                Toast.makeText(MainActivity.this, "Configurações", Toast.LENGTH_SHORT).show();
                intent = new Intent(MainActivity.this, ConfiguracaoActivity.class);
                startActivity(intent);

                break;
            //case R.id.fab_hoje:
            //    Toast.makeText(MainActivity.this, "Hoje", Toast.LENGTH_SHORT).show();
            //    break;
        }

        fab.close(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

    }
}

package br.com.cadastrodepessoas.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import br.com.cadastrodepessoas.R;
import br.com.cadastrodepessoas.adapters.ViewPageAdapter;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class MainActivity extends AppCompatActivity {

    public static TabLayout tabLayout;
    Toolbar toolbar;
    private int[] icones = {R.drawable.ic_add_pessoa, R.drawable.ic_lista_pessoa};
    private ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolBar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPageAdapter.setarViewPager(viewPager);

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        setarIconestabLayout();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Toast.makeText(getBaseContext(), "TAB "+ tab.getPosition(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setarIconestabLayout() {
        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(icones[0]);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(icones[1]);
    }


}
package br.com.cadastrodepessoas.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import br.com.cadastrodepessoas.fragments.FragmentCadastroDePessoas;
import br.com.cadastrodepessoas.fragments.FragmentListaPessoas;

public class ViewPageAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentTitleList = new ArrayList<>();

    public ViewPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public void setarViewPager(ViewPager viewPager) {
        this.adicionarFragment(new FragmentCadastroDePessoas(), "Cadastro");
        this.adicionarFragment(new FragmentListaPessoas(), "Lista");
        viewPager.setAdapter(this);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }

    public void adicionarFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
    }

}

package com.bookservice.ui.activity.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.bookservice.R;
import com.bookservice.adapter.BestDealsAdapter;
import com.bookservice.adapter.CategoryAdapter;
import com.bookservice.data.model.category.Category;
import com.bookservice.data.model.category.CategoryResponse;
import com.bookservice.data.model.offers.GetOffersResponse;
import com.bookservice.data.model.offers.Offer;
import com.bookservice.preference.BsPreference;
import com.bookservice.ui.activity.user.UserDetailActivity;
import com.bookservice.ui.activity.vehicle.VehicleDetailActivity;
import com.bookservice.ui.base.BsBaseActivity;
import com.bookservice.data.pojos.SingleVertical;
import com.bookservice.utils.view.BsToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BsBaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, IHomeView {

    @BindView(R.id.floating_search_view)
    FloatingSearchView mSearchView;
    @BindView(R.id.recycler_view_best_deals)
    RecyclerView rvBestDeals;
    @BindView(R.id.recycler_view_category)
    RecyclerView rvCategories;
    @BindView(R.id.recycler_view_discounts)
    RecyclerView rvDiscounts;
    HomePresenter presenter;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        activity = HomeActivity.this;
        presenter = new HomePresenter(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mSearchView.attachNavigationDrawerToMenuButton(drawer);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu m = navigationView.getMenu();

        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);
            applyFontToMenuItem(mi);
        }

        presenter.getOffers(activity);
        presenter.getCategories(activity);



    }

    @Override
    public void onSuccessOffers(GetOffersResponse getOffersResponse) {
        final String url = getOffersResponse.getUrl();
        final List<Offer> offers = getOffersResponse.getOffers();

        BestDealsAdapter adapter = new BestDealsAdapter(offers,url, this);
        rvBestDeals.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvBestDeals.setAdapter(adapter);

        rvDiscounts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvDiscounts.setAdapter(adapter);
    }

    @Override
    public void onSuccessCategory(CategoryResponse categoryResponse) {
        final List<Category> categories = categoryResponse.getCategories();
        GridLayoutManager gridLayoutManagerCategories = new GridLayoutManager(this, 2);
        rvCategories.setHasFixedSize(true);
        rvCategories.setLayoutManager(gridLayoutManagerCategories);
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, categories);
        rvCategories.setAdapter(categoryAdapter);
    }

    @Override
    public void onError(String result) {
        BsToast.showLong(activity, result);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {

        } else if (id == R.id.offers) {

        } else if (id == R.id.account) {
            Intent intent = new Intent(this, UserDetailActivity.class);
            startActivity(intent);
        } else if (id == R.id.vehicle) {
            Intent intent = new Intent(this, VehicleDetailActivity.class);
            startActivity(intent);
        } else if (id == R.id.orders) {

        } else if (id == R.id.logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/avenirLTStd-Light.otf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }*/

}

package com.ntubus.ntubus;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.ntubus.ntubus.ui.BaseScrollableFragment;
import com.ntubus.ntubus.ui.BusStopListFragment;
import com.ntubus.ntubus.ui.HomeFragment;
import com.ntubus.ntubus.ui.MapFragment;
import com.ntubus.ntubus.widget.TintableImageView;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends AppCompatActivity {
    private static final int PAGE_POSITION_HOME = 0;
    private static final int PAGE_POSITION_ROUTES = 1;
    private static final int PAGE_POSITION_2 = 2;
    private static final int PAGE_POSITION_3 = 3;

    @InjectView(R.id.toolbarContainer)
    LinearLayout mToolbarContainer;

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.spinner)
    Spinner mToolbarSpinner;

    @InjectView(R.id.viewpager)
    ViewPager mViewPager;

    @InjectView(R.id.viewpagertab)
    SmartTabLayout mViewPagerTab;

    private FragmentPagerItemAdapter mPagerAdapter;
    private ArrayAdapter mToolbarSpinnerAdapter;
    private Drawer.Result result = null;
    private static int currentPageColor;

    boolean isClicked = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.inject(this);
        setSupportActionBar(mToolbar);

        currentPageColor = Utils.fetchPrimaryColor(this);

        mToolbarSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.routes, R.layout.toolbar_spinner_item);
        mToolbarSpinnerAdapter.setDropDownViewResource(R.layout.toolbar_spinner_dropdown_item);
        mToolbarSpinner.setAdapter(mToolbarSpinnerAdapter);
        mToolbarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setToolbarColor(getPageColor(PAGE_POSITION_ROUTES), true);
                BusStopListFragment f = (BusStopListFragment) mPagerAdapter.getPage(PAGE_POSITION_ROUTES);
                if (f != null) {
                    switch (position) {
                        case 0:
                            f.loadData(null);
                        default:
                            f.loadData(Integer.toString(position));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        AccountHeader.Result headerResult = new AccountHeader()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        new ProfileDrawerItem().withName("NTU Bus")
                )
                .build();

        result = new Drawer()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.home).withIcon(GoogleMaterial.Icon.gmd_home).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.routes).withIcon(GoogleMaterial.Icon.gmd_directions_bus).withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.bus_stops).withIcon(GoogleMaterial.Icon.gmd_place).withIdentifier(3),
                        new PrimaryDrawerItem().withName(R.string.map).withIcon(GoogleMaterial.Icon.gmd_map).withIdentifier(4)

                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                        Fragment f;
                        switch (iDrawerItem.getIdentifier()) {
                            case 1:
                                break;
                            case 2:
                                break;
                            case 3:
                                f = new BusStopListFragment();
                                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f).commit();
                                break;
                            case 4:
                                f = new MapFragment();
                                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f).commit();
                                break;
                        }

                    }
                })
                .build();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);

//        result.setSelectionByIdentifier(3);

        mPagerAdapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(R.string.home, HomeFragment.class)
                .add(R.string.bus_stops, BusStopListFragment.class)
                .add(R.string.bus_stops, BusStopListFragment.class)
                .add(R.string.map, HomeFragment.class)
                .create());


        final LayoutInflater inflater = LayoutInflater.from(this);
        mViewPagerTab.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter pagerAdapter) {
                ImageView icon = (ImageView) inflater.inflate(R.layout.custom_tab_icon, container, false);
                switch (position) {
                    case PAGE_POSITION_HOME:
                        icon.setImageDrawable(new IconicsDrawable(MainActivity.this, GoogleMaterial.Icon.gmd_home).sizeDp(22));
                        break;
                    case PAGE_POSITION_ROUTES:
                        icon.setImageDrawable(new IconicsDrawable(MainActivity.this, GoogleMaterial.Icon.gmd_directions_bus).sizeDp(22));
                        break;
                    case PAGE_POSITION_2:
                        icon.setImageDrawable(new IconicsDrawable(MainActivity.this, GoogleMaterial.Icon.gmd_place).sizeDp(22));
                        break;
                    case PAGE_POSITION_3:
                        icon.setImageDrawable(new IconicsDrawable(MainActivity.this, GoogleMaterial.Icon.gmd_map).sizeDp(22));
                        break;
                    default:
                        throw new IllegalStateException("Invalid position: " + position);
                }
                int primaryColor = Utils.fetchPrimaryColor(MainActivity.this);
                int disabledColor = getResources().getColor(R.color.disabled);
                icon.setColorFilter(ColorUtils.compositeColors(disabledColor, primaryColor));
                return icon;
            }
        });

        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mViewPagerTab.setViewPager(mViewPager);

        mViewPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                                  boolean userScrollChange = true;
                                                  int previousState;
                                                  int selected = mViewPager.getCurrentItem();
                                                  int disabledColor = getResources().getColor(R.color.disabled);
                                                  int enabledColor = getResources().getColor(R.color.enabled);

                                                  @Override
                                                  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                                      if (!userScrollChange)
                                                          return;

                                                      float actualPosition = position + positionOffset;
                                                      int position1 = (int) Math.ceil(actualPosition);
                                                      int position2 = (int) Math.floor(actualPosition);

                                                      ArgbEvaluator toolbarBgEvaluator = new ArgbEvaluator();
                                                      int fromBgColor = getPageColor(position1);
                                                      int toBgColor = getPageColor(position2);
                                                      int bgColor = (int) toolbarBgEvaluator.evaluate(position1 - actualPosition, fromBgColor, toBgColor);
                                                      setToolbarColor(bgColor, false);

                                                      ArgbEvaluator evaluator = new ArgbEvaluator();

                                                      int total = mViewPager.getAdapter().getCount();
                                                      for (int i = 0; i < total; i++) {
                                                          int color;
                                                          TintableImageView icon = (TintableImageView) mViewPagerTab.getTabAt(i);
                                                          if (i == position1) {
                                                              color = (int) evaluator.evaluate(position1 - actualPosition, enabledColor, disabledColor);
                                                              icon.setColorFilter(ColorUtils.compositeColors(color, bgColor));
                                                          } else if (position1 != position2 && i == position2) {
                                                              color = (int) evaluator.evaluate(actualPosition - position2, enabledColor, disabledColor);
                                                              icon.setColorFilter(ColorUtils.compositeColors(color, bgColor));
                                                          } else if (fromBgColor != toBgColor) {
                                                              icon.setColorFilter(ColorUtils.compositeColors(disabledColor, bgColor));
                                                          }
                                                      }
                                                  }

                                                  @Override
                                                  public void onPageSelected(int position) {
                                                      int bgColor = getPageColor(position);
                                                      setToolbarColor(bgColor, false);
                                                      if (selected != position && !userScrollChange) {
                                                          int total = mViewPager.getAdapter().getCount();
                                                          for (int i = 0; i < total; i++) {
                                                              TintableImageView icon = (TintableImageView) mViewPagerTab.getTabAt(i);
                                                              if (i == position) {
                                                                  icon.setColorFilter(ColorUtils.compositeColors(enabledColor, bgColor));
                                                              } else {
                                                                  icon.setColorFilter(ColorUtils.compositeColors(disabledColor, bgColor));
                                                              }
                                                          }
                                                      }

                                                      if (mPagerAdapter.getPage(position) instanceof BaseScrollableFragment) {
                                                          BaseScrollableFragment fragment = (BaseScrollableFragment) mPagerAdapter.getPage(position);
                                                          if (fragment != null && fragment.getRecylerView().isAtTop())
                                                              fragment.getScrollListener().onShow();
                                                      }

                                                      selected = position;

                                                      switch (position) {
                                                          case 1:
                                                              getSupportActionBar().setDisplayShowTitleEnabled(false);
                                                              mToolbarSpinner.setVisibility(View.VISIBLE);
                                                              break;
                                                          default:
                                                              mToolbarSpinner.setVisibility(View.GONE);
                                                              getSupportActionBar().setDisplayShowTitleEnabled(true);
                                                              mToolbar.setTitle("NTU Bus");
                                                              break;

                                                      }
                                                  }

                                                  @Override
                                                  public void onPageScrollStateChanged(int state) {
                                                      if (state == ViewPager.SCROLL_STATE_DRAGGING || (previousState == ViewPager.SCROLL_STATE_DRAGGING && state == ViewPager.SCROLL_STATE_SETTLING))
                                                          userScrollChange = true;

                                                      else if (previousState == ViewPager.SCROLL_STATE_SETTLING
                                                              && state == ViewPager.SCROLL_STATE_IDLE)
                                                          userScrollChange = false;

                                                      previousState = state;

                                                      System.out.println("Current state: " + state);

                                                  }
                                              }

        );

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    public LinearLayout getToolbarContainer() {
        return mToolbarContainer;
    }

    private void setToolbarColor(final int color, boolean animate) {
        final ColorDrawable currentColorDrawable = (ColorDrawable) mToolbarContainer.getBackground();

        int currentColor = currentColorDrawable.getColor();
        if (animate) {
            ValueAnimator animator = ValueAnimator.ofObject(new ArgbEvaluator(), currentColor, color);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int newColor = (Integer) animation.getAnimatedValue();
                    mToolbarContainer.setBackgroundColor(newColor);
                    recolorTabs(newColor);
                }
            });

            animator.setDuration(200);
            animator.start();
        } else {
            mToolbarContainer.setBackgroundColor(color);
        }

        currentPageColor = color;
    }

    private void recolorTabs(int color) {
        int l = mViewPager.getChildCount();
        int disabledColor = getResources().getColor(R.color.disabled);
        int enabledColor = getResources().getColor(R.color.enabled);
        int selectedPosition = mViewPager.getCurrentItem();
        for (int i = 0; i < l; i++) {
            TintableImageView imageView = (TintableImageView) mViewPagerTab.getTabAt(i);
            imageView.setColorFilter(ColorUtils.compositeColors(selectedPosition == i ? enabledColor : disabledColor, color));

        }
    }

    private int getPageColor(int position) {
        int primaryColor = Utils.fetchPrimaryColor(MainActivity.this);
        switch (position) {
            case PAGE_POSITION_ROUTES:
                int spinnerPosition = mToolbarSpinner.getSelectedItemPosition();
                int color;
                switch (spinnerPosition) {
                    case 1:
                        color = getResources().getColor(R.color.md_red_700);
                        break;
                    case 2:
                        color = getResources().getColor(R.color.md_blue_700);
                        break;
                    case 3:
                        color = getResources().getColor(R.color.md_green_700);
                        break;
                    default:
                        color = Utils.fetchPrimaryColor(MainActivity.this);
                }

                return color;
            default:
                return primaryColor;
        }


    }
}

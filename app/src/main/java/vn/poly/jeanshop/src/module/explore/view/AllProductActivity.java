package vn.poly.jeanshop.src.module.explore.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import vn.poly.jeanshop.R;
import vn.poly.jeanshop.src.model.Product;
import vn.poly.jeanshop.src.module.explore.IOnClickProduct;
import vn.poly.jeanshop.src.module.explore.adapter.NewFoodAdapter;
import vn.poly.jeanshop.src.module.explore.presenter.IProduct;
import vn.poly.jeanshop.src.module.explore.presenter.PresenterProduct;
import vn.poly.jeanshop.src.utils.DialogLoading;
import vn.poly.jeanshop.src.utils.ItemOffsetDecoration;

public class AllProductActivity extends AppCompatActivity implements IProduct.IViewProduct, IOnClickProduct {

    private RecyclerView recyclerViewAllFood;
    private int page = 1;
    private PresenterProduct presenterProduct;
    private List<Product> products;
    private NewFoodAdapter allFoodAdapter;
    int findLastItem, totalItemCount;
    private GridLayoutManager layoutManager;
    private GoogleProgressBar progress_productAll;
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_product);
        initView();

        listenerScrollRecyclerAllFood();
        DialogLoading.LoadingGoogle(true, progress_productAll);
        presenterProduct.getListProductMore(page);

    }

    private void initView() {
        recyclerViewAllFood = findViewById(R.id.recyclerViewAllFoodPaging);
        progress_productAll = findViewById(R.id.progress_productAll);
        imgBack = findViewById(R.id.imgBack);

        imgBack.setOnClickListener(v -> finish());

        products = new ArrayList<>();
        presenterProduct = new PresenterProduct(this);
        layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerViewAllFood.setLayoutManager(layoutManager);

        allFoodAdapter = new NewFoodAdapter(getApplicationContext(), R.layout.custom_layout_new_food, products, this, "All");
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.margin10);
        recyclerViewAllFood.addItemDecoration(new ItemOffsetDecoration(spacingInPixels));
        recyclerViewAllFood.setAdapter(allFoodAdapter);

    }

    private void listenerScrollRecyclerAllFood() {
        recyclerViewAllFood.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = layoutManager.getItemCount();
                findLastItem = layoutManager.findLastCompletelyVisibleItemPosition();

                if (findLastItem == (totalItemCount -1)) {
                    DialogLoading.LoadingGoogle(true, progress_productAll);
                    presenterProduct.getListProductMore(page);
                }

            }
        });
    }

    @Override
    public void onGetListProductSuccess(List<Product> productList) {
        DialogLoading.LoadingGoogle(false, progress_productAll);

        if (productList.size() == 0) {
            Toasty.info(AllProductActivity.this, "???? h???t s???n ph???m", Toasty.LENGTH_LONG).show();
            return;
        }
        products.addAll(productList);
        allFoodAdapter.notifyDataSetChanged();
        page++;

    }

    @Override
    public void onGetListProductFailed(String msg) {
        DialogLoading.LoadingGoogle(false, progress_productAll);


    }

    @Override
    public void onGetListNewFoodSuccess(List<Product> productList) {
        //null
    }

    @Override
    public void onGetListNewFoodFailed(String msg) {
        //null
    }

    @Override
    public void OnClickProductDetails(Product product) {

        Intent intent = new Intent(AllProductActivity.this, ProductDetailsActivity.class);
        intent.putExtra("product", product);
        startActivity(intent);

    }

    @Override
    public void OnClickBadge() {

    }
}

package com.project.agroworld.ui.shopping.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.agroworld.R;
import com.project.agroworld.databinding.ActivityAddToCartBinding;
import com.project.agroworld.ui.payment.PaymentDetailsActivity;
import com.project.agroworld.ui.shopping.adapter.ProductCartAdapter;
import com.project.agroworld.ui.shopping.listener.ItemCartActionListener;
import com.project.agroworld.ui.shopping.model.ProductModel;
import com.project.agroworld.utils.Constants;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddToCartActivity extends AppCompatActivity implements ItemCartActionListener {

    private ActivityAddToCartBinding binding;
    private final ArrayList<ProductModel> productCartList = new ArrayList<>();
    private DatabaseReference databaseReference;
    private ProductCartAdapter productAdapter;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    ActionBar actionBar;
    private String addressLine;
    private double totalItemAmount = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_to_cart);
        actionBar = getSupportActionBar();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        actionBar.hide();
        getProductListFromFirebase();
        binding.tvAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((ContextCompat.checkSelfPermission(AddToCartActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
                    askPermission();
                } else {
                    getLastLocation();
                }
            }
        });

        binding.btnProceedToPayment.setOnClickListener(v -> {
            String value = binding.tvTotalAmount.getText().toString();
            Log.d("valueInit", "size " + productCartList.size() + "value " + value.toString());
            if (addressLine != null && !value.contains("₹0.0") && !productCartList.isEmpty()) {
                Intent intent = new Intent(this, PaymentDetailsActivity.class);
                intent.putExtra("productItemList", productCartList);
                intent.putExtra("address", addressLine);
                intent.putExtra("totalAmount", binding.tvTotalAmount.getText().toString());
                startActivity(intent);
            } else {
                Constants.showToast(this, "Something is missing");
            }
        });
    }

    private void getProductListFromFirebase() {
        databaseReference = FirebaseDatabase.getInstance().getReference("CartItemList");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                totalItemAmount = 0.0;
                if (snapshot.exists()) {
                    productCartList.clear();
                    for (DataSnapshot product : snapshot.getChildren()) {
                        ProductModel productItem = product.getValue(ProductModel.class);
                        productCartList.add(productItem);
                        totalItemAmount += productItem.getPrice();
                    }
                    binding.tvTotalAmount.setText("₹ " + df.format(totalItemAmount));
                    setRecyclerView();
                } else {
                    totalItemAmount = 0.0;
                    productCartList.clear();
                    binding.recyclerViewCrtList.setVisibility(View.GONE);
                    binding.tvNoCartDataFound.setVisibility(View.VISIBLE);
                    binding.tvTotalAmount.setText("₹ " + totalItemAmount);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Constants.showToast(AddToCartActivity.this, error.toString());
            }
        });
    }

    private void setRecyclerView() {
        productAdapter = new ProductCartAdapter(productCartList, AddToCartActivity.this);
        binding.recyclerViewCrtList.setAdapter(productAdapter);
        binding.recyclerViewCrtList.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewCrtList.setHasFixedSize(true);
    }

    @Override
    public void onIncreaseItemClick(ProductModel productModel, int quantity) {
        productModel.setQuantity(quantity);
        String[] currentValue = binding.tvTotalAmount.getText().toString().split("₹");
        double currentDoubleValue = Double.valueOf(currentValue[1]) + productModel.getPrice();
        binding.tvTotalAmount.setText("₹ " + df.format(currentDoubleValue));
    }

    @Override
    public void onDecreaseItemClick(ProductModel productModel, int quantity) {
        productModel.setQuantity(quantity);
        String[] currentValue = binding.tvTotalAmount.getText().toString().split("₹");
        double currentDoubleValue = Double.valueOf(currentValue[1]) - productModel.getPrice();
        binding.tvTotalAmount.setText("₹ " + df.format(currentDoubleValue));
    }

    private void getLastLocation() {
        if (ContextCompat.checkSelfPermission(AddToCartActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        Geocoder geocoder = new Geocoder(AddToCartActivity.this, Locale.getDefault());
                        List<Address> addresses = null;
                        try {
                            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            addressLine = addresses.get(0).getAddressLine(0);
                            binding.tvAddAddress.setText(addressLine);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {

                    }
                }
            });
        } else {
            askPermission();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Constants.showToast(AddToCartActivity.this, getString(R.string.provide_permission));
            }
        }
    }

    private void askPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.REQUEST_CODE);
        }
    }

    @Override
    public void onRemovedItemClick(ProductModel productModel, int position) {
        databaseReference.child(productModel.getTitle()).removeValue().addOnSuccessListener(unused -> {
            totalItemAmount -= productModel.getPrice();
            productAdapter.notifyDataSetChanged();
            Constants.showToast(AddToCartActivity.this, "Item removed successfully price " + productModel.getPrice());
        }).addOnFailureListener(e -> Constants.showToast(AddToCartActivity.this, "Failed to  removed item"));
    }
}
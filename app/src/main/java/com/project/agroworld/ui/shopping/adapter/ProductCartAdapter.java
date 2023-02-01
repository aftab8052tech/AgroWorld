package com.project.agroworld.ui.shopping.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.agroworld.databinding.CartItemLayoutBinding;
import com.project.agroworld.ui.shopping.listener.ItemCartActionListener;
import com.project.agroworld.ui.shopping.model.ProductModel;
import com.project.agroworld.ui.shopping.viewholder.CartViewHolder;

import java.util.List;

public class ProductCartAdapter extends RecyclerView.Adapter<CartViewHolder> {
    private List<ProductModel> productModelArrayList;
    private ItemCartActionListener listener;
    public ProductCartAdapter(List<ProductModel> productModelList, ItemCartActionListener listener){
        this.productModelArrayList = productModelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder(CartItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        ProductModel productModel = productModelArrayList.get(position);
        holder.bindItemData(productModel, listener);
    }

    @Override
    public int getItemCount() {
        return productModelArrayList.size();
    }
}
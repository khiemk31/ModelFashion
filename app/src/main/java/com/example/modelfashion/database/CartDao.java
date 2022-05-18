package com.example.modelfashion.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface CartDao {
    @Insert
    Single<Long> insertProductToCart(MyProductCart myProductCart);

    @Delete
    Completable removeProductFromCart(MyProductCart myProductCart);

    @Query("delete from product_cart")
    Completable deleteAll();

    @Update
    Completable updateProductInCart(MyProductCart myProductCart);

    @Query("select count(*) from product_cart where product_id = :id and product_size = :size")
    int countProduct(String id, String size);

    @Query("select * from product_cart")
    Single<List<MyProductCart>> getAllProductInCart();
}

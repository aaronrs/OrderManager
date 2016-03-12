package net.astechdesign.cms.repo;

import net.astechdesign.cms.database.DBHelper;

public class Repository {

    private final ProductsRepo productsRepo;

    private Repository(DBHelper dbHelper) {
        productsRepo = new ProductsRepo(dbHelper);
    }

    public ProductsRepo getProductRepo() {
        return productsRepo;
    }
}

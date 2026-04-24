package com.monstock.app.domain.model;

import com.monstock.app.data.model.ItemCategory;
import com.monstock.app.data.model.ItemUnit;
import java.time.LocalDate;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b \n\u0002\u0010\b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001B\u0091\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u000f\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u000b\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\u0015J\t\u0010/\u001a\u00020\u0003H\u00c6\u0003J\u0010\u00100\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003\u00a2\u0006\u0002\u0010$J\u000b\u00101\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\u000b\u00102\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\u000b\u00103\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\t\u00104\u001a\u00020\u0003H\u00c6\u0003J\t\u00105\u001a\u00020\u0006H\u00c6\u0003J\u000b\u00106\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\t\u00107\u001a\u00020\tH\u00c6\u0003J\t\u00108\u001a\u00020\u000bH\u00c6\u0003J\t\u00109\u001a\u00020\rH\u00c6\u0003J\u000b\u0010:\u001a\u0004\u0018\u00010\u000fH\u00c6\u0003J\u000b\u0010;\u001a\u0004\u0018\u00010\u000fH\u00c6\u0003J\u009e\u0001\u0010<\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\r2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u000f2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u000b2\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0006H\u00c6\u0001\u00a2\u0006\u0002\u0010=J\u0013\u0010>\u001a\u00020 2\b\u0010?\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010@\u001a\u00020AH\u00d6\u0001J\u0010\u0010B\u001a\u00020 2\b\b\u0002\u0010C\u001a\u00020\u0003J\t\u0010D\u001a\u00020\u0006H\u00d6\u0001R\u0013\u0010\u0012\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0017R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u001f\u001a\u00020 8F\u00a2\u0006\u0006\u001a\u0004\b\u001f\u0010!R\u0011\u0010\"\u001a\u00020 8F\u00a2\u0006\u0006\u001a\u0004\b\"\u0010!R\u0015\u0010\u0011\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\n\n\u0002\u0010%\u001a\u0004\b#\u0010$R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0017R\u0013\u0010\u0014\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u0017R\u0013\u0010\u0013\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u0017R\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\u001cR\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010\u001eR\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010.\u00a8\u0006E"}, d2 = {"Lcom/monstock/app/domain/model/Item;", "", "id", "", "shelfId", "name", "", "brand", "category", "Lcom/monstock/app/data/model/ItemCategory;", "quantity", "", "unit", "Lcom/monstock/app/data/model/ItemUnit;", "purchaseDate", "Ljava/time/LocalDate;", "expiryDate", "minimumStockThreshold", "barcode", "photoUri", "notes", "(JJLjava/lang/String;Ljava/lang/String;Lcom/monstock/app/data/model/ItemCategory;DLcom/monstock/app/data/model/ItemUnit;Ljava/time/LocalDate;Ljava/time/LocalDate;Ljava/lang/Double;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getBarcode", "()Ljava/lang/String;", "getBrand", "getCategory", "()Lcom/monstock/app/data/model/ItemCategory;", "getExpiryDate", "()Ljava/time/LocalDate;", "getId", "()J", "isExpired", "", "()Z", "isLowStock", "getMinimumStockThreshold", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getName", "getNotes", "getPhotoUri", "getPurchaseDate", "getQuantity", "()D", "getShelfId", "getUnit", "()Lcom/monstock/app/data/model/ItemUnit;", "component1", "component10", "component11", "component12", "component13", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(JJLjava/lang/String;Ljava/lang/String;Lcom/monstock/app/data/model/ItemCategory;DLcom/monstock/app/data/model/ItemUnit;Ljava/time/LocalDate;Ljava/time/LocalDate;Ljava/lang/Double;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lcom/monstock/app/domain/model/Item;", "equals", "other", "hashCode", "", "isExpiringSoon", "withinDays", "toString", "app_debug"})
public final class Item {
    private final long id = 0L;
    private final long shelfId = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String name = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String brand = null;
    @org.jetbrains.annotations.NotNull()
    private final com.monstock.app.data.model.ItemCategory category = null;
    private final double quantity = 0.0;
    @org.jetbrains.annotations.NotNull()
    private final com.monstock.app.data.model.ItemUnit unit = null;
    @org.jetbrains.annotations.Nullable()
    private final java.time.LocalDate purchaseDate = null;
    @org.jetbrains.annotations.Nullable()
    private final java.time.LocalDate expiryDate = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double minimumStockThreshold = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String barcode = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String photoUri = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String notes = null;
    
    public final long component1() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component12() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component13() {
        return null;
    }
    
    public final long component2() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.monstock.app.data.model.ItemCategory component5() {
        return null;
    }
    
    public final double component6() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.monstock.app.data.model.ItemUnit component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.time.LocalDate component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.time.LocalDate component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.monstock.app.domain.model.Item copy(long id, long shelfId, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.Nullable()
    java.lang.String brand, @org.jetbrains.annotations.NotNull()
    com.monstock.app.data.model.ItemCategory category, double quantity, @org.jetbrains.annotations.NotNull()
    com.monstock.app.data.model.ItemUnit unit, @org.jetbrains.annotations.Nullable()
    java.time.LocalDate purchaseDate, @org.jetbrains.annotations.Nullable()
    java.time.LocalDate expiryDate, @org.jetbrains.annotations.Nullable()
    java.lang.Double minimumStockThreshold, @org.jetbrains.annotations.Nullable()
    java.lang.String barcode, @org.jetbrains.annotations.Nullable()
    java.lang.String photoUri, @org.jetbrains.annotations.Nullable()
    java.lang.String notes) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
    
    public Item(long id, long shelfId, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.Nullable()
    java.lang.String brand, @org.jetbrains.annotations.NotNull()
    com.monstock.app.data.model.ItemCategory category, double quantity, @org.jetbrains.annotations.NotNull()
    com.monstock.app.data.model.ItemUnit unit, @org.jetbrains.annotations.Nullable()
    java.time.LocalDate purchaseDate, @org.jetbrains.annotations.Nullable()
    java.time.LocalDate expiryDate, @org.jetbrains.annotations.Nullable()
    java.lang.Double minimumStockThreshold, @org.jetbrains.annotations.Nullable()
    java.lang.String barcode, @org.jetbrains.annotations.Nullable()
    java.lang.String photoUri, @org.jetbrains.annotations.Nullable()
    java.lang.String notes) {
        super();
    }
    
    public final long getId() {
        return 0L;
    }
    
    public final long getShelfId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getName() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getBrand() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.monstock.app.data.model.ItemCategory getCategory() {
        return null;
    }
    
    public final double getQuantity() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.monstock.app.data.model.ItemUnit getUnit() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.time.LocalDate getPurchaseDate() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.time.LocalDate getExpiryDate() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getMinimumStockThreshold() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getBarcode() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getPhotoUri() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getNotes() {
        return null;
    }
    
    public final boolean isExpired() {
        return false;
    }
    
    /**
     * True if expiry date is within the next [withinDays] days.
     */
    public final boolean isExpiringSoon(long withinDays) {
        return false;
    }
    
    public final boolean isLowStock() {
        return false;
    }
}
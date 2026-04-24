package com.monstock.app.ui.screens.itemlist;

import androidx.lifecycle.ViewModel;
import com.monstock.app.data.model.ItemCategory;
import com.monstock.app.data.repository.ItemRepository;
import com.monstock.app.data.repository.ShelfRepository;
import com.monstock.app.domain.model.Item;
import com.monstock.app.domain.model.Shelf;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.ExperimentalCoroutinesApi;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/monstock/app/ui/screens/itemlist/SortOrder;", "", "(Ljava/lang/String;I)V", "EXPIRY", "NAME", "CATEGORY", "QUANTITY", "app_debug"})
public enum SortOrder {
    /*public static final*/ EXPIRY /* = new EXPIRY() */,
    /*public static final*/ NAME /* = new NAME() */,
    /*public static final*/ CATEGORY /* = new CATEGORY() */,
    /*public static final*/ QUANTITY /* = new QUANTITY() */;
    
    SortOrder() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.monstock.app.ui.screens.itemlist.SortOrder> getEntries() {
        return null;
    }
}
package ua.org.goservice.cashdesk.model.trade.transaction;

import javafx.collections.ObservableList;
import ua.org.goservice.cashdesk.model.draft.DraftEntry;
import ua.org.goservice.cashdesk.model.warehouse.IDGoodSearcher;

import java.util.ArrayList;
import java.util.List;

final class ProductPacker {

    List<SaleUnit> convertToSaleUnits(IDGoodSearcher goodSearcher, ObservableList<DraftEntry> draftList) {
        List<SaleUnit> saleUnits = new ArrayList<>();
        for (DraftEntry draftEntry : draftList) {
            Integer goodID = goodSearcher.findGoodID(draftEntry.getBarcode());
            saleUnits.add(new SaleUnit(goodID, draftEntry.getCount(), draftEntry.getPrice()));
        }
        return saleUnits;
    }
}

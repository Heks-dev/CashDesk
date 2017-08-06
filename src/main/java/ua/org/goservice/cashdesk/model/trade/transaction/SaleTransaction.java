package ua.org.goservice.cashdesk.model.trade.transaction;

import javafx.collections.ObservableList;
import ua.org.goservice.cashdesk.model.draft.Draft;
import ua.org.goservice.cashdesk.model.draft.DraftEntry;
import ua.org.goservice.cashdesk.model.organization.Organization;
import ua.org.goservice.cashdesk.model.util.json.JsonAgent;
import ua.org.goservice.cashdesk.model.util.json.JsonFormat;
import ua.org.goservice.cashdesk.model.util.validator.fund.ComplexFundValidator;
import ua.org.goservice.cashdesk.model.warehouse.IDGoodSearcher;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SaleTransaction implements Transaction<SaleContent> {

    private final SaleContent content;

    public SaleTransaction(BigDecimal amountInCash, IDGoodSearcher goodSearcher, Draft draft, Organization our, Organization counterparty) {
        List<SaleUnit> saleUnits = convertToSaleUnits(goodSearcher, draft.getDraftList());
        Long discountNum = draft.getDiscountCard() == null ? null : draft.getDiscountCard().getBonuscardnum();
        this.content = new SaleContent(our.getId(), counterparty.getId(), counterparty.getPriceid(),
                amountInCash, draft.getTerminalFund(), draft.getBonusFund(),
                discountNum, saleUnits);
    }

    @Override
    public SaleContent getTransactionContent() {
        return content;
    }

    @Override
    public String serialize() {
        String json = JsonAgent.serialize(content, JsonFormat.SINGLE_OBJECT);
        // todo debug
        System.out.println(json);
        return json;
    }

    @Override
    public void activateTransaction(boolean yes) {
        if (yes) content.setActive(Transaction.ACTIVE);
    }

    private List<SaleUnit> convertToSaleUnits(IDGoodSearcher goodSearcher, ObservableList<DraftEntry> draftList) {
        List<SaleUnit> saleUnits = new ArrayList<>();
        for (DraftEntry draftEntry : draftList) {
            Integer goodID = goodSearcher.findGoodID(draftEntry.getBarcode());
            saleUnits.add(new SaleUnit(goodID, draftEntry.getCount(), draftEntry.getPrice()));
        }
        return saleUnits;
    }
}

package ua.org.goservice.cashdesk.model.trade.transaction;

import ua.org.goservice.cashdesk.model.draft.RefundDraft;
import ua.org.goservice.cashdesk.model.draft.RefundDraftEntry;
import ua.org.goservice.cashdesk.model.draft.RefundReason;
import ua.org.goservice.cashdesk.model.util.json.JsonAgent;
import ua.org.goservice.cashdesk.model.util.json.JsonFormat;
import ua.org.goservice.cashdesk.model.warehouse.IDGoodSearcher;

import java.util.ArrayList;
import java.util.List;

public class RefundTransaction implements Transaction {

    private RefundContent content;

    public RefundTransaction(RefundDraft draft, IDGoodSearcher goodSearcher, RefundReason reason) {
        List<SaleUnit> saleUnits = convertToSaleUnits(draft, goodSearcher);
        SaleContent saleContent = draft.getSaleContent();
        content = new RefundContent(saleContent.getId(), saleContent.getIdfor(), saleContent.getIdfrom(),
                draft.getCashFund(), draft.getBonusFund(), saleUnits, reason.getId());
    }

    @Override
    public TransactionContent getTransactionContent() {
        return content;
    }

    @Override
    public String serialize() {
        String json = JsonAgent.serialize(content, JsonFormat.SINGLE_OBJECT);
        // todo debug
        System.out.println(json);
        json = json.replace("\"id\"", "\"checkid\"");
        System.out.println(json);
        return json;
    }

    @Override
    public void activateTransaction(boolean yes) {
        if (yes) content.setActive(Transaction.ACTIVE);
    }

    private List<SaleUnit> convertToSaleUnits(RefundDraft draft, IDGoodSearcher goodSearcher) {
        List<SaleUnit> saleUnits = new ArrayList<>();
        for (RefundDraftEntry draftEntry : draft.getSaleList()) {
            if (draftEntry.getRefundCount() != null) {
                Integer goodID = goodSearcher.findGoodID(draftEntry.getBarcode());
                saleUnits.add(new SaleUnit(goodID, draftEntry.getRefundCount(), draftEntry.getPrice()));
            }
        }
        return saleUnits;
    }
}

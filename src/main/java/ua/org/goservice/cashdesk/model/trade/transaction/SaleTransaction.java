package ua.org.goservice.cashdesk.model.trade.transaction;

import ua.org.goservice.cashdesk.model.draft.Draft;
import ua.org.goservice.cashdesk.model.organization.Organization;
import ua.org.goservice.cashdesk.model.util.json.JsonAgent;
import ua.org.goservice.cashdesk.model.util.json.JsonFormat;
import ua.org.goservice.cashdesk.model.warehouse.IDGoodSearcher;

import java.math.BigDecimal;
import java.util.List;

public class SaleTransaction implements Transaction<SaleContent> {

    private final SaleContent content;
    private static final ProductPacker productPacker = new ProductPacker();

    public SaleTransaction(BigDecimal amountInCash, IDGoodSearcher goodSearcher, Draft draft, Organization our,
                           Organization counterparty) {
        List<SaleUnit> saleUnits = productPacker.convertToSaleUnits(goodSearcher, draft.getDraftList());
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
}

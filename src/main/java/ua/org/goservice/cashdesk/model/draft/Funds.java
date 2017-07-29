package ua.org.goservice.cashdesk.model.draft;

import java.math.BigDecimal;

public class Funds {

    private BigDecimal cash;
    private BigDecimal terminal;
    private BigDecimal bonuses;

    public void payInCash(BigDecimal cash) {
        this.cash = cash;
    }

    public void payInTerminal(BigDecimal terminal) {
        this.terminal = terminal;
    }

    public void payInBonuses(BigDecimal bonuses) {
        this.bonuses = bonuses;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public BigDecimal getTerminal() {
        return terminal;
    }

    public BigDecimal getBonuses() {
        return bonuses;
    }

    public BigDecimal getTotalContributed() {
        return cash.add(terminal.add(bonuses));
    }
}

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
        BigDecimal result = new BigDecimal(0);
        if (cash != null) {
            result = result.add(cash);
        }
        if (terminal != null) {
            result = result.add(terminal);
        }
        if (bonuses != null) {
            result = result.add(bonuses);
        }
        return result.compareTo(BigDecimal.ZERO) == 0 ? null : result;
    }
}

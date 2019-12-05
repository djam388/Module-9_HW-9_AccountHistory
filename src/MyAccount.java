import java.util.Comparator;
import java.util.Date;

public class MyAccount implements Comparable<MyAccount>
{
    private Date operationDate;
    private String operationDescription;
    private Double income;
    private Double expense;

    public MyAccount(Date operationDate, String operationDescription, Double income, Double expense) {
        this.operationDate = operationDate;
        this.operationDescription = operationDescription;
        this.income = income;
        this.expense = expense;
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    public String getOperationDescription() {
        return operationDescription;
    }

    public void setOperationDescription(String operationDescription) {
        this.operationDescription = operationDescription;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public Double getExpense() {
        return expense;
    }

    public void setExpense(Double expense) {
        this.expense = expense;
    }


    @Override
    public int compareTo(MyAccount o) {
        int last = this.operationDescription.compareTo(o.operationDescription);
        return last == 0 ? 0 : last;
    }
}

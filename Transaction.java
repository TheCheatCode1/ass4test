import java.io.Serializable;

public class Transaction implements Serializable{
private int transNumber;
private int transId;
private double transAmt;
public Transaction(int number, int id, double amount) {
transNumber = number;
transId = id;
transAmt = amount;
}

public int getTransNumber() {
return transNumber;
}
public String getTransId() {

String type = "";

if (transId == 1) {
type = "Check";
}
if (transId == 2) {
type = "Deposit";
}
if (transId == 3) {
type = "Service. Charg.";
}
return type;
}
public double getTransAmt() {
return transAmt;
}
@Override
public String toString() {
return String.format("%2d\t%-10s\t\t$%8.2f", getTransNumber(), getTransId(), getTransAmt());
}
public String toStringDeposit() {
return "";
}
public String toStringCheck() {
return "";
}
}
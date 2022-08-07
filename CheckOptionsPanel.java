import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DecimalFormat;

public class CheckOptionsPanel extends JPanel implements Serializable {
public static String filename = "file.dat";
double totalDeposit;
JFrame frameForDeposit;
JTextField cashTextField;
JTextField checkTextField;
JButton OkButton;
JButton cancel;
double cash;
double check;
JRadioButton entertransaction;
JRadioButton listTrans;
JRadioButton checks;
JRadioButton deposits;
JRadioButton readfile;
JRadioButton writefile;
JLabel lable;
double amount = 0;
String transaction;
double currentServiceCharge;
int transactionCode;

static String name = Main.getBalanceName();
static double initBalance = Main.getInitialBalance();
CheckingAccount checkingAccountObj = new CheckingAccount(initBalance);
CheckingAccount newObj = new CheckingAccount(initBalance);
JTextArea text = new JTextArea();
String message = "";
DecimalFormat form;

public CheckOptionsPanel() {

form = new DecimalFormat("$##,##0.00;($##,##0.00)");
text.setFont(new Font("Monospaced", Font.PLAIN, 14));
entertransaction = new JRadioButton("Enter Transaction");
listTrans = new JRadioButton("List All Transactions");
checks = new JRadioButton("List All Checks ");
deposits = new JRadioButton("List All Deposits");
readfile = new JRadioButton("Read From File");
writefile = new JRadioButton("Write To The File");
ButtonGroup group = new ButtonGroup();
group.add(entertransaction);
group.add(listTrans);
group.add(checks);
group.add(deposits);
group.add(readfile);
group.add(writefile);

EOptionListener1 listener = new EOptionListener1();
entertransaction.addActionListener(listener);
listTrans.addActionListener(listener);
checks.addActionListener(listener);
deposits.addActionListener(listener);
readfile.addActionListener(listener);
writefile.addActionListener(listener);
lable = new JLabel("Checking Account Actions");
lable.setFont(new Font("Helvetica", Font.BOLD, 24));
add(lable);
add(entertransaction);
add(listTrans);
add(checks);
add(deposits);
add(readfile);
add(writefile);

// create Grid Layout with 7 row and 1 column
setLayout(new GridLayout(7,1));



  
setBackground(Color.YELLOW);
setPreferredSize(new Dimension(350, 200));

}


public void panelDeposit() {

int WINDOW_WIDTH = 220;
int WINDOW_HEIGHT = 180;

frameForDeposit = new JFrame("Deposit");
frameForDeposit.setLocationRelativeTo(null);
frameForDeposit.setTitle("Deposit");
frameForDeposit.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frameForDeposit.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
JLabel messageCash = new JLabel("Cash");
JLabel messageCheck = new JLabel("Check");
cashTextField = new JTextField(15);
checkTextField = new JTextField(15);
OkButton = new JButton("OK");
cancel = new JButton("Cancel");
JPanel panelB = new JPanel();
panelB.add(messageCash);
panelB.add(cashTextField);
panelB.add(messageCheck);
panelB.add(checkTextField);
panelB.add(OkButton);
panelB.add(cancel);
EOptionListener2 listener2 = new EOptionListener2();
OkButton.addActionListener(listener2);
cancel.addActionListener(listener2);
frameForDeposit.add(panelB);
frameForDeposit.setVisible(true);
}

// listener for deposit panel
private class EOptionListener2 implements ActionListener {

public void actionPerformed(ActionEvent event) {

Object source = event.getSource();
if (source == OkButton) {
String input1, input2;
input1 = cashTextField.getText();
cash = Double.parseDouble(input1);
input2 = checkTextField.getText();
check = Double.parseDouble(input2);

totalDeposit = cash + check;

Deposit newDeposit = new Deposit(checkingAccountObj.transCount, 2, check, cash);

checkingAccountObj.addTrans(newDeposit);

frameForDeposit.dispose();

checkingAccountObj.setBalance(transactionCode, totalDeposit);
checkingAccountObj.setTotalServiceCharge(currentServiceCharge);

JOptionPane.showMessageDialog(null, Main.name + " account.\n"
+ "Transaction: " + transaction + " in amount of " + form.format(totalDeposit) + "\n"
+ "Current Balance: " + form.format(checkingAccountObj.getBalance()) + "\n"
+ "Service charge: " + transaction + " Charge--- " + form.format(currentServiceCharge) + "\n" + checkingAccountObj.message
+ "\nTotal Service Charge: " + form.format(checkingAccountObj.getTotalServiceCharge()) + "\n"
+ "\n Date: " + Main.showDate());
}
if (source == cancel) {

frameForDeposit.dispose();
}
}
}

private class EOptionListener1 implements ActionListener {

public void actionPerformed(ActionEvent event) {

Object source = event.getSource();

// check the radio from here:
if (source == entertransaction) {
// get tansaction code
transactionCode = Main.getTransCode();

if (transactionCode != 0) {
if (transactionCode == 1) {
String input = JOptionPane.showInputDialog("Enter the check number:");
int checkNumber = Integer.parseInt(input);
amount = Main.checkAmount();
transaction = "Check";
currentServiceCharge = 0.15;
Check tr = new Check(checkingAccountObj.transCount, 1, amount, checkNumber);
checkingAccountObj.addTrans(tr);

checkingAccountObj.setBalance(transactionCode, amount);
checkingAccountObj.setTotalServiceCharge(currentServiceCharge);

JOptionPane.showMessageDialog(null, Main.name + " account.\n"
+ "Transaction: " + transaction + "#" + checkNumber + " in amount of " + form.format(amount) + "\n"
+ "Current Balance: " + form.format(checkingAccountObj.getBalance()) + "\n"
+ "Service charge: " + transaction + " Charge--- " + form.format(currentServiceCharge) + "\n" + checkingAccountObj.message
+ "\nTotal Service Charge: " + form.format(checkingAccountObj.getTotalServiceCharge()) + "\n"
+ "\n Date: " + Main.showDate());
}

if (transactionCode == 2) {

panelDeposit();
amount = totalDeposit;

transaction = "Deposit";
currentServiceCharge = 0.10;
}

} else {
transaction = "End";
JOptionPane.showMessageDialog(null, " Transaction: " + transaction
+ "\n Current Balance: " + form.format(checkingAccountObj.getBalance())
+ "\n Total Service Charge: " + form.format(checkingAccountObj.getTotalServiceCharge())
+ "\n Final Balance: " + form.format(checkingAccountObj.finalBalance())
+ "\n\n Date: " + Main.showDate());
}
}

if (source == listTrans) {
text.setOpaque(false);
message = "Transactions List for \n"+ Main.name
+ "\n\nID \t Type \t\t\t Amount\n\n";

for (int i = 0; i < checkingAccountObj.getTransCount(); i++) {
message += checkingAccountObj.getTrans(i).toString() + "\n";
}
text.setText(message);

JOptionPane.showMessageDialog(null, text);
}
if (source == checks) {

text.setOpaque(false);
message = "List All Checks for "+ Main.name + "\n\n"
+ "ID \t Type \t Check Number \t Amount\n\n";
for (int i = 0; i < checkingAccountObj.getTransCount(); i++) {
if (checkingAccountObj.getTrans(i).getTransId().equals("Check")) {
message += checkingAccountObj.getTrans(i).toStringCheck() + "\n";
}
}
text.setText(message);
JOptionPane.showMessageDialog(null, text);
}
if (source == deposits) {
text.setOpaque(false);
message = "List All Deposits for " + Main.name + "\n\n"
+ "ID \t Type \t\t Checks \t Cash \t Amount\n\n";
for (int i = 0; i < checkingAccountObj.getTransCount(); i++) {
if (checkingAccountObj.getTrans(i).getTransId().equals("Deposit")) {
message += checkingAccountObj.getTrans(i).toStringDeposit() + "\n";
}
}
text.setText(message);
JOptionPane.showMessageDialog(null, text);
}

if (source == readfile){
// read  goes here:

}

if (source == writefile){

writeElements();


}
}
}

public int getTransCode() {
String tranC = JOptionPane.showInputDialog("Enter The Transaction Code "
+ "\n 1 for Check \n 2 for deposit \n 0 for exit");
transactionCode = Integer.parseInt(tranC);

return transactionCode;
}




public static void chooseFile(int ioOption)
{
int status, confirm;

String message = "Would you like to use the current default file: \n" + filename;
confirm = JOptionPane.showConfirmDialog (null, message);
if (confirm == JOptionPane.YES_OPTION)
return;
JFileChooser chooser = new JFileChooser();
if (ioOption == 1)
status = chooser.showOpenDialog (null);
else
status = chooser.showSaveDialog (null);
if (status == JFileChooser.APPROVE_OPTION)
{
File file = chooser.getSelectedFile();
filename = file.getPath();
}
}

public static void readElements()
{
chooseFile(1);
try

{
FileInputStream fis = new FileInputStream(filename);
ObjectInputStream in = new ObjectInputStream(fis);

String eCountObj = (String)in.readObject();

System.out.println("count of objects in file = "+ eCountObj.length());
for (int i = 0; i < eCountObj.length(); i++)
{
System.out.println("reading object "+ i);

}
in.close();
}
catch(ClassNotFoundException | IOException e)
{
System.out.println(e);
}
}

public void writeElements()
{
chooseFile(2);

try
{
FileOutputStream fos = new FileOutputStream(filename);
ObjectOutputStream out = new ObjectOutputStream(fos);

for (int i=0; i<newObj.transCount;i++ ){

}

out.close();
}
catch(IOException e)
{
System.out.println(e);
}
}

}
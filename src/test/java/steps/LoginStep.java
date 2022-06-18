package steps;


import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import helper.fileMapping;
import pages.LoginPage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LoginStep {
    LoginPage lpOb = new LoginPage();

    String workingDir = System.getProperty("user.dir");      //relative path
    fileMapping datafile = new fileMapping(workingDir + "\\config\\Configuration.properties");

    String clubNumbr = null;
    String staticClubNumber = "";
    String fname = "";
    String scndname = "";
    String transDate = "";
    String memId = "";
    String expectdAmount = "";
    String finalPgData = "";
    String memberType = "";
    String memStatus = "";
    String AmountCorrect="";
    String DuplicateInvoices="";
    public void openPaymentGatewayUrl(){
        String pgUrl = datafile.getData("paymentGatewayUrl");
        lpOb.navigateToUrl(pgUrl);
    }

    public void openRCMUrl(){
        String rcmUrl = datafile.getData("uiPageUrl");
        lpOb.navigateToUrl(rcmUrl);
    }
    public void validLogin(){
        String usrName = datafile.getData("usrName");
        String usrPaswd = datafile.getData("usrpaswd");
        lpOb.enterUsrNameAndpasswdPG(usrName,usrPaswd);
    }
    public void validLoginRCM(){
        String usrName = datafile.getData("usrName");
        String usrPaswd = datafile.getData("usrpaswd");
        lpOb.enterUsrNameAndpasswdRCM(usrName,usrPaswd);
    }
    @Given("^user navigates to ABC product page$")
    public void goToProductPage() throws IOException, InterruptedException {

        String paymentGateway = datafile.getData("checkAccountData");
        String getBatchData = datafile.getData("getBatchData");
        String lstFourDigit = "";
        String bnkOrCC = "";
        String pgAmount = "";
        String pgAcntHolderName = "";
        String pgStatus = "";
        String pgDate = "";
        String pgDataHeader ="";
        String isAmountSame="";

        // Code to get the batch data WIP
        if(getBatchData.equalsIgnoreCase("true")){

            openPaymentGatewayUrl();
            validLogin();
            lpOb.clickBatchModule();
            lpOb.sleep(10000);
            //    lpOb.explicitlyWaitOnElement(10);
            Map<Integer, List<String>> batchFileData = lpOb.getTableData("fileMonitorRow");
            int noOfBatches = batchFileData.size();
            lpOb.fileAppendData("\n Number of batches is "+noOfBatches);
            String merchantName = "";
            String batchName="";
            String fileName="";
            for(int i=1; i<=noOfBatches; i++){
                lpOb.clickBatch(i);
                lpOb.sleep(3000);
                Map<Integer, List<String>> paymentBatchFileData = lpOb.getTableData("paymentBatchMonitorsTableRow");//paymentBatchMonitorsTableTable
                int noOfClubsInBatch = paymentBatchFileData.size();
                lpOb.fileAppendData("\n\n Number of clubs in batch is "+noOfClubsInBatch);
                for(int j=1; j<= noOfClubsInBatch ;j++){
                    lpOb.batchClickClub(j);
                    lpOb.sleep(3000);
                    merchantName = lpOb.getClubName();
                    batchName=lpOb.getBatchName();
                    fileName= lpOb.getFileName();
                    lpOb.sleep(2000);
                    Map<Integer, List<String>> paymentTransactionsBatchData = lpOb.getTableData("paymentBatchTransactionsMonitorsTableRow"); //paymentBatchTransactionsMonitorsTableTable          //size-2

                    lpOb.fileAppendData("\n\n"+" Number of transactions in batch "+batchName+" is "+paymentTransactionsBatchData.size()+"\n");
                    pgDataHeader = "FileName,BatchId,ClubName,ActHolderName,PgAmount,Status";
                    lpOb.fileAppendData(pgDataHeader);
                     int s=0;
                    for (Map.Entry<Integer,List<String>> entry1 : paymentTransactionsBatchData.entrySet()) {
                            String accountHolderName = entry1.getValue().get(s);
                            String accountHolderAmount = entry1.getValue().get(s+2).split("\\$")[1].trim();
                            String transStatus = entry1.getValue().get(s+7);
                            System.out.println("account holder name frm batch data is "+fileName+","+batchName+","+merchantName+","+accountHolderName+","+accountHolderAmount+","+transStatus);
                            lpOb.fileAppendData("\n"+fileName+","+batchName+","+merchantName+","+accountHolderName+","+accountHolderAmount+","+transStatus);

                             s=s+8;
                    }
                    lpOb.getDriver().navigate().back();
                    lpOb.sleep(3000);

                }
                lpOb.sleep(4000);
                lpOb.getDriver().navigate().back();
            }    finalPgData = pgAmount + "," + pgStatus + "," + pgDate + "," + pgAcntHolderName + "," + isAmountSame + "," + "\n";
        }


        // Code to verify the 4 digit payment amount on PG url
        if(paymentGateway.equalsIgnoreCase("true")){

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            System.out.println(dtf.format(now));

            pgDataHeader = "\n\n" + dtf.format(now) + "\nAcnt Num,rcmAmount,pgAmount,pgStatus,pgDate,pgAccountName,isAmountSame \n";
            lpOb.fileAppendData(pgDataHeader);

            finalPgData = "";
            String acntDetail = "";
            String pastDue="";
            String rcmAmount="";
            String pgUrl = datafile.getData("paymentGatewayUrl");
            lpOb.navigateToUrl(pgUrl);
            String usrName = datafile.getData("usrName");
            String usrPaswd = datafile.getData("usrpaswd");
            lpOb.enterUsrNameAndpasswdPG(usrName,usrPaswd);
            lpOb.sleep(2000);
            lpOb.clickTransactionModule();
//            lpOb.enterBeginDate(datafile.getData("beginDate"));
//            lpOb.enterEndDate(datafile.getData("endDate"));

            LinkedHashMap<String,List<String>> getAllPaymentData = lpOb.readExcelFile(datafile.getData("paymentSheet"));
            for (Map.Entry<String,List<String>> entry : getAllPaymentData.entrySet()) {

                memId = entry.getKey();
                acntDetail = entry.getValue().get(8);
                if (acntDetail == null) {
                    pastDue = entry.getValue().get(9);
                    if(pastDue.equalsIgnoreCase("Past Due")){
                        pgAmount = "pastDue";
//                        pgAcntHolderName = "";
//                        pgStatus = "";
//                        pgDate = "";
                    }
                    else {
                        pgAmount = "TBV";
//                        pgAcntHolderName = "";
//                        pgStatus = "";
//                        pgDate = "";
                    }
                } else

                    if(!acntDetail.contains("test")) {
                       bnkOrCC = acntDetail.split("\\(")[0].trim();

                           int i = 0;
                           if (i < 1) {


                               if (bnkOrCC.contains("Bank Account")) {
                                   lpOb.clickBankButton();
                               } else {
                                   lpOb.clickCreditCardButton();
                               }
                           }
                           lpOb.clickAddFilter();

                           lpOb.sleep(3000);
                       //    lpOb.explicitlyWaitOnElement(10,);
                           lpOb.clickLastFourDigit();
                                lstFourDigit = entry.getValue().get(8).split("\\(")[1].trim();
                                rcmAmount = entry.getValue().get(12);
                                lpOb.payment4Nums(lstFourDigit);
                                lpOb.sleep(10000);
                               //  lpOb.explicitlyWaitOnElement(60);
                                Map<Integer, List<String>> transactionData = lpOb.getTableData("transactionsRow");
                                if (transactionData.size() == 1) {
                                   lpOb.sleep(3000);
                                   pgAmount = lpOb.getPgAmount().split("\\$")[1].trim();
                                   pgAcntHolderName = lpOb.getPgAcntHolderName();
                                   pgStatus = lpOb.getPgStatus();
                                   pgDate = lpOb.getPgDAte();
                               } else {
                               //   pgAmount = "";  // To Be Verified
                                   pgAcntHolderName = "TBV";
                                   pgStatus = "TBV";
                                   pgDate = "TBV";
                               }
                               if (Double.parseDouble(rcmAmount) == Double.parseDouble(pgAmount)) {                     /////////////////
                                     isAmountSame = "True";
                                } else {
                             isAmountSame = "False";
                           }
                     //   }// isAmountSame// compare;
//                        catch (NumberFormatException ex){
//                               ex.printStackTrace();
//            }
                               finalPgData = acntDetail + "," + rcmAmount + "," + pgAmount + "," + pgStatus + "," + pgDate + "," + pgAcntHolderName + "," + isAmountSame + "," + "\n";   // pastDue +
                           }
//                       }
                else{
                           finalPgData = "TBV \n";
                       }
                       lpOb.fileAppendData(finalPgData);
                   }
            now = LocalDateTime.now();
            lpOb.fileAppendData("payment validation ended at : "+dtf.format(now));
        }
        else{
            String productUrl = datafile.getData("uiPageUrl");
            lpOb.navigateToUrl(productUrl);
        }

    }

    @When("^user search for a product$")
    public void searchProduct() throws InterruptedException, IOException {
        String checkRCMData=datafile.getData("checkRCMData");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
        String finalData = dtf.format(now) + "\n\nMemId,MemType,ActiveStatus,Expected Invoice amount,clubNum,Club,Member Name,Due Date,Payment Type(Last Four),Invoice Status (PROD),Comments,Amount billed as expected,Expected invoice amount,Payment amount billed on Payment Gateway,Amount Correct,Duplicate Invoices \n";
        lpOb.fileAppendData(finalData);

        if(checkRCMData.equalsIgnoreCase("true")){
            validLoginRCM();

        String x = "";
        String clubName = "";

        LinkedHashMap<String,List<String>> getAllMemberData = lpOb.readExcelFile(datafile.getData("uiTestDataSheet"));
        for (Map.Entry<String,List<String>> entry : getAllMemberData.entrySet()) {
            finalData = "";
           x = entry.getValue().get(0).split("\\.")[0];
           if(clubNumbr == null || !clubNumbr.equalsIgnoreCase(x)){
                clubNumbr = entry.getValue().get(0).split("\\.")[0];
                lpOb.clearClub();
                lpOb.clickBusiness();
                lpOb.searchClub(clubNumbr);
                lpOb.clickClub(clubNumbr);
                lpOb.clickMemberButton();
            }

                memId = entry.getKey().toString().replace(".0","");
                // clubNumbr = entry.getValue().get(0).split("\\.")[0];
                fname = entry.getValue().get(1).split(" ")[0];
                scndname = entry.getValue().get(2);
                transDate = entry.getValue().get(4);
                expectdAmount = entry.getValue().get(6);                                      ////------------
                memberType = lpOb.searchMemberAndGetMemType(fname, scndname,clubNumbr, memId);
//                if(lpOb.getMemberStatus().equalsIgnoreCase("ACTIVE")){
//                lpOb.clickPaymentsTab();
//                System.out.println(lpOb.getRCMAmount(datafile.getData("beginDate")));
//                String rcmAmnt=lpOb.getRCMAmount(datafile.getData("beginDate")).split("\\$")[1].trim();
//                System.out.println(rcmAmnt);
//                System.out.println(expectdAmount);
//
//            if (Double.parseDouble(rcmAmnt) == Double.parseDouble(expectdAmount)) {                     /////////////////
//                AmountCorrect = "True";
//            }else{
//                AmountCorrect="False";
//            }}else{
//                    AmountCorrect="TBV";
//                }
                if(memberType=="Pass") {
                    memStatus = lpOb.getMemberStatus();
                    clubName = lpOb.getClubName(clubNumbr);
                    finalData = finalData + memId + "," + memberType + "," + memStatus + "," + expectdAmount + "," + clubNumbr + "," + clubName + "," + fname + " " + scndname + "," + transDate;//+ AmountCorrect  + "," +DuplicateInvoices
                    if (memStatus.equalsIgnoreCase("ACTIVE") || memStatus.equalsIgnoreCase("memStatusTBUpdated")) {
                        lpOb.clickPaymentsTab();
                        finalData = finalData + "," + lpOb.getFirstPaymentType(datafile.getData("beginDate"), expectdAmount,AmountCorrect) + "\n";
                    } else {
                        finalData = finalData + "\n";
                    }
                    lpOb.fileAppendData(finalData);
                }else {
                    finalData = finalData + memId + "," + memberType + "," + memStatus + "," + expectdAmount + "," + clubNumbr + "," + clubName + "," + fname + " " + scndname + "," + transDate;
                    lpOb.fileAppendData(finalData+"\n");//   "/n"
                    continue;
                   }
        }
        lpOb.fileAppendData("UI VAlidation end at : "+ dtf.format(now) );
    }
    }

    @Then("^right category is shown$")
    public void checkCategory() {
//        String expectedProductCategory = datafile.getData("productCategory");
//        String actualProductCategory = lpOb.getCategory();
//        Assert.assertEquals(actualProductCategory,expectedProductCategory, "Validation for check of searched product category" );
    }
}

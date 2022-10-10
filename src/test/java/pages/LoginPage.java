package pages;

import Base.BaseUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.time.Duration;
import java.util.*;

public class LoginPage extends BaseUtil {
    By pgDate = By.xpath("//div[@class='styles--MHthb']//span[@data-abc-id='createdDate']");
    By pgStatus = By.xpath("//span[@data-abc-id='status']");
    By pgAccountHolderName = By.xpath("//div[@class='styles--MHthb']//span[@data-abc-id='accountHolderName']");
    By pgAmount = By.xpath("//div[@class='styles--MHthb']//span[@data-abc-id='amount']");
    By batchDataStartDate = By.xpath("//*[@data-abc-id='filesModifiedDateRangeStart']//input");
    By searchText = By.xpath("//input[@placeholder='Search for products, brands and more']");
    By searchButton = By.xpath("//button[@class='vh79eN']");
    By category = By.xpath("//a[@class='_3XS1AH _32ZSYo']");
    By usrname = By.xpath("//*[@id=\"username\"]");
    By usrpaswd = By.xpath("//*[@id=\"password\"]");
    By signInButton = By.xpath("//button/div[contains(text(),'Sign In')]");
    By continueBT = By.xpath("//button[@type='submit']");
    By signInButtonPG = By.xpath("//*[@id=\"idSIButton9\"]");
    By usrpaswdPG = By.xpath("//*[@name=\"passwd\"]");
    By yesBT=By.xpath("//input[contains(@id,'idSIButton')]");
    By bsnesButton = By.xpath("//li[@role='listitem']/descendant::h2[contains(text(),\"Business\")]");
    By clubSearchBox = By.xpath("//input[@id='tableViewSearchInput']") ;

    By srchButton = By.xpath("//div[@data-abc-id='tableViewSearchInputTooltip']/descendant::input");
    By memberButton = By.xpath("//a[@data-abc-id=\"navBarLink\" and contains(text(),'Members')]");
    By backButtonMemberSearch = By.xpath("//*[@class=\"ui-icon icon-arrow-left-thin\"]");
    //By srchClearButton = By.xpath("//*[@data-abc-id='memberListSearchInputTooltip']//*[@data-abc-id='iconClose']");
    By srchClearButton = By.xpath("//*[@data-abc-id='clearIcon']");
    By memList = By.xpath("//*[@data-abc-id='memberList']");

    By subscriptiontab = By.xpath("//div[contains(text(),'Subscriptions')]");
    By MemberAccountTab = By.xpath("//div[contains(text(),'Member Account')]");
    By paymentsTab = By.xpath("//*[@data-text='Payments']");
    By memStatus = By.xpath("//*[@data-abc-id='memberStatus']");

    By clubClearButton = By.xpath("//*[@data-abc-id='iconClose']");
    By status = By.xpath("(//td[@data-abc-id='status'])[1]");
    By paymentId = By.xpath("(//td[@data-abc-id='paymentMethodId'])[1]");
    By amount = By.xpath("(//td[@data-abc-id='amountDue'])[1]");
    By statementAmount = By.xpath("(//table[@data-abc-id='accountStatementsTable']//td[@data-abc-id='amountDue'])[1]");
    By statementCreatedDate = By.xpath("(//table[@data-abc-id=\"accountStatementsTable\"]//td[@data-abc-id='createdDate'])[1]");
    By statementPostedDate = By.xpath("(//table[@data-abc-id=\"accountStatementsTable\"]//td[@data-abc-id='postingDate'])[1]");
    By memButton = By.xpath("//*[@data-abc-id='navBarLink' and contains(text(),'Members')]");

    public void enterUsrNameAndpasswdRCM(String usrName, String passwd) {
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        getDriver().findElement(usrname).clear();
        getDriver().findElement(usrname).sendKeys(usrName);
        getDriver().findElement(usrpaswd).clear();
        getDriver().findElement(usrpaswd).sendKeys(passwd);
        getDriver().findElement(signInButton).click();
    }

    public void enterUsrNameAndpasswdPG(String usrName, String passwd) {
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        getDriver().findElement(usrname).clear();
        getDriver().findElement(usrname).sendKeys(usrName);
        getDriver().findElement(continueBT).click();
        getDriver().findElement(usrpaswdPG).clear();
        getDriver().findElement(usrpaswdPG).sendKeys(passwd);
        getDriver().findElement(signInButtonPG).click();
        getDriver().findElement(yesBT).click();
    }

    public void navigateToUrl(String url) {
        getDriver().manage().window().maximize();
        getDriver().navigate().to(url);
    }

    public void clickBusiness() throws InterruptedException {
        //Thread.sleep(2000);
          explicitlyWaitOnElement(bsnesButton,10);             //////     1
        getDriver().findElement(bsnesButton).click();
    }

    public void searchClub(String clubNumr){
        getDriver().findElement(clubSearchBox).sendKeys(clubNumr);
    }

    public void clickClub(String clubNumbr) throws InterruptedException {
//       Thread.sleep(2000);
        explicitlyWaitOnElement(clubSearchBox,10);               ////   2
        String xpathVal = "//span[@data-abc-id='extraContent' and contains(text(),'"+clubNumbr+"')]";
        By club = By.xpath(xpathVal);
        getDriver().findElement(club).click();
    }

    public void clickMemberButton() throws InterruptedException {
//        Thread.sleep(2000);
        explicitlyWaitOnElement(memButton,10);             ////   3
        getDriver().findElement(memButton).click();
    }

    public String searchMemberAndGetMemType(String fName,String lName, String clubNum, String agrementNum) throws InterruptedException {
        int count = getDriver().findElements(memList).size();

        if(count==1){
            getDriver().findElement(memList).click();
            sleep(2000);
            //           explicitlyWaitOnElement(srchClearButton,10);
                if(1==getDriver().findElements(srchClearButton).size())
                    getDriver().findElement(srchClearButton).click();
        }

        sleep(2000);
        getDriver().findElement(memberButton).click();
        getDriver().findElement(srchButton).click();

        if(1==getDriver().findElements(memList).size()){
            getDriver().findElement(srchClearButton).click();
        }

        // if agrementNum i.e. length of memberId is less than 3, search does not give expected result
        if(agrementNum.length() <=3){
            getDriver().findElement(srchButton).sendKeys(lName);
        }else{
            getDriver().findElement(srchButton).sendKeys(agrementNum);
        }
        String agrNumXpath = "";
        if(clubNum.length()==3){
            clubNum = "0"+clubNum;
        }

//        if(lName.equalsIgnoreCase("'"+"+lName+")){
//
//        }

        agrementNum = agrementNum.replace(".0","");
        if(agrementNum.startsWith("B")){
            agrNumXpath = "//td[@data-abc-id='agreementNumber']//span[contains(text(),\"0" +clubNum+""+agrementNum+"\")]";
        }
        else if(agrementNum.startsWith("0000")){
          agrNumXpath= "//*[@data-abc-id='memberName' and contains(text(),'"+fName+"') and contains(text(),'"+lName+"')]";
        }else if(agrementNum.startsWith("000")) {
            agrNumXpath= "//*[@data-abc-id='memberName' and contains(text(),'"+fName+"') and contains(text(),'"+lName+"')]";
        }
        else if(agrementNum.length()==4){
            agrNumXpath = "//td[@data-abc-id='agreementNumber']//span[contains(text(),\"00" +clubNum+"00"+agrementNum+"\")]";
        }else if(agrementNum.endsWith("B")){
            agrNumXpath = "//td[@data-abc-id='agreementNumber']//span[contains(text(),\"00" +clubNum+""+agrementNum+"\")]";
        }else if(agrementNum.length()<4){
            if(agrementNum.length()==1){
                agrNumXpath= "//*[@data-abc-id='memberName' and contains(text(),'"+fName+"') and contains(text(),'"+lName+"')]";
            }
            else if(agrementNum.length()==2){
                agrNumXpath= "//*[@data-abc-id='memberName' and contains(text(),'"+fName+"') and contains(text(),'"+lName+"')]";
            }
            else if(agrementNum.length()==3){
                agrNumXpath= "//*[@data-abc-id='memberName' and contains(text(),'"+fName+"') and contains(text(),'"+lName+"')]";
            }

        }else if(agrementNum.length()==6){
            agrNumXpath = "//td[@data-abc-id='agreementNumber']//span[contains(text(),\"00" +clubNum+""+agrementNum+"\")]";
        }
        else
            {
            agrNumXpath = "//td[@data-abc-id='agreementNumber']//span[contains(text(),\"00" +clubNum+"0"+agrementNum+"\")]";
        }

        By agrementNumber = By.xpath(agrNumXpath);
        if(getDriver().findElements(agrementNumber).size()>0){
            getDriver().findElement(agrementNumber).click();
            return "Pass";
        }else{
            return "Fail";              // finalMemType;
        }
    }
        public Boolean searchMemberByFirstAndSecondName(String fName, String sName) throws InterruptedException {
        boolean findMem = false;
        int count = getDriver().findElements(backButtonMemberSearch).size();
        if(count==1){
            getDriver().findElement(backButtonMemberSearch).click();
            getDriver().findElement(srchClearButton).click();
        }
        getDriver().findElement(srchButton).clear();
        getDriver().findElement(srchButton).sendKeys(fName);


        By membersearchXPath = By.xpath("//*[starts-with(text(), '"+sName +"')  and contains(text(), '"+fName +"')]");
        int memberCount = getDriver().findElements(membersearchXPath).size();
        if(memberCount==1){
            findMem = true;
            getDriver().findElement(membersearchXPath).click();
        }
        return findMem;
    }

    public void searchProduct(String product) {
        getDriver().findElement(searchText).clear();
        getDriver().findElement(searchText).sendKeys(product);
        getDriver().findElement(searchButton).click();
    }


    public void clickSubscriptionTab() {
        getDriver().findElement(subscriptiontab).click();
    }

    public void clickMemberAccountTab() {
        getDriver().findElement(MemberAccountTab).click();
    }


    public void clickPaymentsTab() {
        getDriver().findElement(paymentsTab).click();
    }


    public String getMemberStatus() throws InterruptedException {
        Thread.sleep(3000);
         return getDriver().findElement(memStatus).getText();
    }


    // Get Complete Table Data
    public Map<Integer,List<String>> getTableData(String tableName) {

        List<WebElement> rowsList = getDriver().findElements(By.xpath("//tr[@data-abc-id='"+tableName +"']"));
//        System.out.println(rowsList.size());
        List<WebElement> columnsList = null;

        List<String> rowData = new ArrayList<String>();
        Map<Integer,List<String>> tableData = new HashMap<Integer,List<String>>();

        System.out.println();
        sleep(2000);
        try {
    int i = 0;
    for (WebElement row : rowsList) {
        try {
          columnsList = row.findElements(By.tagName("td"));
            }catch(StaleElementReferenceException e){
            }
////columnsList.size();
//        System.out.println(columnsList.size());
        String data = "";
        for (WebElement column : columnsList) {
            // System.out.print(column.getText() + ", ");
            data = column.getText();
            rowData.add(data);
        }
        tableData.put(i, rowData);
        i++;
    }
}catch (Exception e){
    e.printStackTrace();
}
return tableData;
    }


    public LinkedHashMap<String,List<String>> readExcelFile(String filePath) throws IOException {
        FileInputStream file = new FileInputStream(new File(filePath));

        // Create Workbook instance holding reference to .xlsx file
        XSSFWorkbook workbook = new XSSFWorkbook(file);

        // Get first/desired sheet from the workbook
        XSSFSheet sheet = workbook.getSheetAt(0);

        Map<String,List<String>> memberDetails = new LinkedHashMap<String,List<String>>();
        int rowcount = sheet.getLastRowNum();
        String data = "";
        System.out.println("Total row number: " + rowcount);

        for (int i = 1; i < rowcount + 1; i++) {
            //Create a loop to get the cell values of a row for one iteration
            Row row = sheet.getRow(i);
            List<String> arrName = new ArrayList<String>();
            for (int j = 0; j < row.getLastCellNum(); j++) {
                // Create an object reference of 'Cell' class
            Cell cell = row.getCell(j);

            try{
                // Add all the cell values of a particular row
                switch (cell.getCellType()) {
                    case NUMERIC:
                        // System.out.print(cell.getNumericCellValue() + "\t");
                        data = String.valueOf(cell.getNumericCellValue());
                        arrName.add(data);
                        break;
                    case STRING:
                        // System.out.print(cell.getStringCellValue() + "\t");
                        data = cell.getStringCellValue();
                        arrName.add(data);
                        break;
                    case _NONE:
                        arrName.add(null);
                        break;
                    case BLANK:
                        arrName.add(null);
                        break;
                 }}catch (NullPointerException e){
                e.printStackTrace();
            }}
            System.out.println();
            if(filePath.contains("paymentTestData")){
                memberDetails.put(arrName.get(0),arrName);
            }else{
                memberDetails.put(arrName.get(3),arrName);
            }
        }
        System.out.println();
        return (LinkedHashMap<String, List<String>>) memberDetails;
    }

    public void clearClub(){
        int isClubSelected = getDriver().findElements(clubClearButton).size();
        if(isClubSelected==1)
            getDriver().findElement(clubClearButton).click();
    }

    public String getFirstPaymentType(String usrDate, String expectedInvAmnt,String amountCorrect){
        By dataTest = By.xpath("//*[@data-abc-id='createdDate' and contains(text(),'"+usrDate+"')]//ancestor::tr//*[@data-abc-id='content' and contains(text(),'Recurring Payment')]");
        By status = By.xpath("//*[@data-abc-id='createdDate' and contains(text(),'"+usrDate+"')]//ancestor::tr//descendant::td[@data-abc-id='status']/span[@data-abc-id='content']");
        By paymentMethod = By.xpath("//*[@data-abc-id='createdDate' and contains(text(),'"+usrDate+"')]//ancestor::tr//*[@data-abc-id='paymentMethod']//span[@data-abc-id='content']");
        By amount = By.xpath("//*[@data-abc-id='createdDate' and contains(text(),'"+usrDate+"')]//ancestor::tr//*[@data-abc-id='amount']//span[@data-abc-id='content']");
        By postDate = By.xpath("//*[@data-abc-id='createdDate' and contains(text(),'"+usrDate+"')]/ancestor::span/../*[@data-abc-id='extraContent']");
        By invoiceStatus = By.xpath("//*[@data-abc-id='content' and contains(text(),'"+usrDate+"')]/ancestor::td[@data-abc-id='date']/preceding-sibling::td[@data-abc-id='transaction']/span[text()='Invoice']/../following-sibling::td[@data-abc-id='status']/span");  //////

        int recurringPayment = getDriver().findElements(dataTest).size();

        String finalDataTobeVerified = "";

        String getStatus = "", getPaymntMethod = "", getAmount = "", createdDate = "", postedDate = "";

        if(recurringPayment == 1 ){
            // From Transaction History Table
             getStatus = getDriver().findElement(status).getText();
            getPaymntMethod = getDriver().findElement(paymentMethod).getText();
             getAmount = getDriver().findElement(amount).getText().replace("$","").replace(",","");
             createdDate = usrDate;
             postedDate = getDriver().findElement(postDate).getText();
        }
        else
        {
            getDriver().navigate().refresh();
            int invoiceStatusSize = getDriver().findElements(invoiceStatus).size();
            if(invoiceStatusSize == 1){
                getStatus = getDriver().findElement(invoiceStatus).getText();
            }
        }
        finalDataTobeVerified = getPaymntMethod +","+ getStatus +","+ ","+ "," + expectedInvAmnt + ","+  getAmount +"," + createdDate+","+ postedDate +"," +amountCorrect;  // getStatementAmount +","+ statusInfo
        return finalDataTobeVerified;
    }

    public String getRCMAmount(String usrDate){
        String rcmAmnt="";
         return rcmAmnt=getDriver().findElement(By.xpath("//*[@data-abc-id='createdDate' and contains(text(),'"+usrDate+"')]//ancestor::tr//*[@data-abc-id='amount']//span[@data-abc-id='content']")).getText();
    }

     File file = new File("C:\\Users\\yrajendra\\OneDrive - Bhavna Software India Pvt.Ltd\\Desktop\\VerifiedData.csv");

        public void explicitlyWaitOnElement(By xp,int timeout){
            WebDriverWait wait = new WebDriverWait(getDriver(),Duration.ofSeconds(timeout));
           wait.until(ExpectedConditions.visibilityOfElementLocated(xp));//visibilityOfElementLocated((xpathForElement)
        }
    public void fileAppendData(String data) throws IOException {
        FileWriter fr = new FileWriter(file, true);
        BufferedWriter br = new BufferedWriter(fr);
        br.write(data);
        br.close();
        fr.close();
    }
    By startDate = By.xpath("//*[@data-abc-id=\"transactionsDateRangeStart\"]//input");
    By batchTransaction = By.xpath("//*[@data-abc-id='navCardSkeletonTitle' and contains(text(),'Batch Monitor')]");
    By pgTransaction = By.xpath("//*[@data-abc-id='navCardSkeletonTitle' and contains(text(),'Transactions')]");
    By endDate = By.xpath("//*[@data-abc-id=\"transactionsDateRangeEnd\"]//input");
    By bnkButton = By.xpath("//*[@data-abc-id=\"/app/transactionsTab\"]");

    public void clickTransactionModule(){
        getDriver().findElement(pgTransaction).click();
    }

    public void clickBatchModule(){
        getDriver().findElement(batchTransaction).click();
    }

    public void enterBeginDate(String date){
        getDriver().findElement(startDate).click();
        getDriver().findElement(startDate).sendKeys(Keys.BACK_SPACE);
        getDriver().findElement(startDate).sendKeys(Keys.BACK_SPACE);
        getDriver().findElement(startDate).sendKeys(Keys.BACK_SPACE);
        getDriver().findElement(startDate).sendKeys(Keys.BACK_SPACE);
        getDriver().findElement(startDate).sendKeys(Keys.BACK_SPACE);
        getDriver().findElement(startDate).sendKeys(Keys.BACK_SPACE);
        getDriver().findElement(startDate).sendKeys(Keys.BACK_SPACE);
        getDriver().findElement(startDate).sendKeys(Keys.BACK_SPACE);
        getDriver().findElement(startDate).sendKeys(date);
    }

    public void enterEndDate(String date){
        getDriver().findElement(endDate).click();
        getDriver().findElement(endDate).sendKeys(Keys.BACK_SPACE);
        getDriver().findElement(endDate).sendKeys(Keys.BACK_SPACE);
        getDriver().findElement(endDate).sendKeys(Keys.BACK_SPACE);
        getDriver().findElement(endDate).sendKeys(Keys.BACK_SPACE);
        getDriver().findElement(endDate).sendKeys(Keys.BACK_SPACE);
        getDriver().findElement(endDate).sendKeys(Keys.BACK_SPACE);
        getDriver().findElement(endDate).sendKeys(Keys.BACK_SPACE);
        getDriver().findElement(endDate).sendKeys(Keys.BACK_SPACE);
        getDriver().findElement(endDate).sendKeys(date);
    }

    public void clickBankButton(){
        getDriver().findElement(bnkButton).click();
    }
    By addFilter=By.xpath("//button[@data-abc-id=\"addFilterMenu\"]");
    By creditCardButton = By.xpath("//*[@data-abc-id=\"/app/transactions/credit-cardTab\"]");
    By lastFourDigit=By.xpath("//div[@data-valuetext='Last Four']");
    By fourNums = By.xpath("//input[@id='lastFourInput']");
    public void clickCreditCardButton(){
        getDriver().findElement(creditCardButton).click();
    }

    public void clickAddFilter(){
        getDriver().findElement(addFilter).click();
    }


    public void clickLastFourDigit(){
        getDriver().findElement(lastFourDigit).click();
    }


    public void  payment4Nums(String num) throws InterruptedException {
        getDriver().findElement(fourNums).sendKeys(Keys.BACK_SPACE);
        getDriver().findElement(fourNums).sendKeys(Keys.BACK_SPACE);
        getDriver().findElement(fourNums).sendKeys(Keys.BACK_SPACE);
        getDriver().findElement(fourNums).sendKeys(Keys.BACK_SPACE);
        getDriver().findElement(fourNums).sendKeys(num);
       Thread.sleep(3000);                                                       ////   6
        //    explicitlyWaitOnElement(fourNums,10);
        getDriver().findElement(fourNums).sendKeys(Keys.ENTER);
    }

    public String getPgAmount(){
        return getDriver().findElement(pgAmount).getText();
    }


    public String getPgAcntHolderName(){
        return getDriver().findElement(pgAccountHolderName).getText();
    }


    public String getPgStatus(){
        return getDriver().findElement(pgStatus).getText();
    }


    public String getPgDAte(){
        return getDriver().findElement(pgDate).getText().replace(","," - ");
    }

   public String getClubName(String clubNumber){
        switch(clubNumber){
            case "520": return "TAN 24-7 FREMONT";
            case "2000": return    "JAMES A. BOTTIN";
            case "2002": return    "BOTTIN FAMILY REAL ESTATE LLC";
            case "2908": return    "HARVEY'S GYM OF FRANKLIN";
            case "3683": return    "MG SPORTS AND FITNESS";
            case "3706": return    "New Life Fitness Center";
            case "3726": return    "JUNGLE GYM FITNESS";
            case "3948": return    "NEW ENGLAND FIT AND MMA";
            case "3959": return "LIFE FITNESS PROS";
            case "4150": return    "Pilgers Womens Bootcamp";
            case "4572": return    "THE FITNESS ZONE";
            case "4613": return    "BAM ADVENTURE BOOT CAMP";
            case "5319": return    "ROCK CITY MMA";
            case "5342": return    "CATOCTIN CROSSFIT PURCELLVILLE";
            case "5552": return    "CONFLUENCE CLUB WORKS MXMETRIC";
            case "5810": return    "ELITE COMBAT ACADEMY";
            case "5855": return    "PAGELAND FITNESS AND WELLNESS";
            case "6060": return    "THE PUMPHOUSE HENDERSONVILLE";
            case "7095": return    "UNITED KARATE STUDIO";
            case "7722": return    "MUSCLES AND CURVES GYM";
            case "7875": return    "TKI FAMILY MARTIAL ARTS";
            case "4099": return "Old Skool Fight Sports Fitness";
            case "7541": return "Titus Strength";
            case "6019" : return "LADIES FITNESS AND WELLNESS MB";
            case "8730" : return "PLAINFIELD GYM AND TAN";
            case "9971" : return "Evendo LLC";
            case "4300" : return "XTREME PHYSIQUE";
            case "7997" : return "CONTOURS EXPRESS";
            case "2928"    : return "REVOLUTION MIXED MARTIAL ARTS";
            case "8205"    : return "CABARRUS PROF FIREFIGHTER ASSN";
            case "3204" : return "BRUTAL IRON GYM";
            case "3505" : return "TEXAS ROWING CENTER";
            case "8429" : return  "LELIA PATTERSON CENTER";
            case "5206" : return  "MAXD OUT FITNESS";
            case "7504" : return  "TAMPA SPORTS ACADEMY";
            case "9533" : return  "E'VILLE FITNESS";
            case "2558" : return  "121 FITNESS";
            case "3250" : return  "FOUST FAMILY FITNESS";
            case "3770" : return  "ACERO GYM";
            case "8609" : return  "NEW YORK SPORT AND FITNESS";
            case "8743" : return  "I AM FITNESS";
            case "7941" : return  "RAINIER HEALTH AND FITNESS";
            case "7218" : return  "THREE RIVERS ATHLETIC CLUB";
            case "30234" : return  "GRAND VALLEY STRENGTH AND FIT";
            case "534" : return  "RAGE FITNESS NIXA";
            case "5204" : return  "SCULPT";
            case "40063" : return  "RAGE FITNESS SPRINGFIELD";
            case "243" : return  "OMEGA FITNESS AND TRAINING";
            case "30361" : return  "REPS FITNESS AND NUTRITION CTR";
            case "3595" : return  "CONNIE WOLD WELLNESS CENTER";
            case "5827": return "RESULTS PERSONAL TRAINING";
            case "30288": return "NEW U FITNESS";
            case "10017": return "HIGHPOINT FITNESS";
            case "1844": return "PREMIERE FITNESS";
            case "30356": return "CHICO SPORTS CLUB";
            case "30511": return "C12 ATHLETICS";
            case "6571": return "FLOW FITNESS LAWRENCE MA";
            case "4594": return "HEALTH AND FITNESS HQ";
            case "7553": return "STATE STREET FITNESS";
            case "99963": return "PIM TRAINING AND DEV";
            case "7463": return "CABOT JIU JITSU AND MARTIALART";
            case "7679": return "ERLANGER FITPLUS";
            case "201": return "TYLER'S FIT FACTORY";
            case "30351": return "PERRY STRENGTH AND FITNESS CTR";
            case "30325": return "SUDA FIT GYM";
            case "7167": return "METROFLEX GYM FT WORTH";
            case "8294": return "BALDWIN CITY FITNESS";
            case "30162": return "WESTERN WISCONSIN HEALTH-RSO";
            case "30313": return "CLUB EVEXIA";
            case "6630": return "DETERMINED FITNESS";
            case "30512": return "EDGEWATER FITNESS CENTER";
            case "6845": return "CHAMPIONS GYM AND FITNESS";
            case "7989": return "SEARCY SWIM CENTER";
            case "7131": return "PREFERRED FITNESS MUSKEGO";
            case "7165": return "WILLOW BEND FITNESS CLUB";
            case "7720": return "SOUTH TEXAS ATHLETIC CENTER";
            case "40085": return "UNLIMITED AUTO WASH LIGHTHOUSE";
            case "6236": return "GARDENS AUTO WASH PALM BEACH";
            case "6237": return "UNLIMITED AUTO WASH CLUB";
            case "6243": return "UNLIMITED AUTO WASH W PALM BCH";
            case "6255": return "TEQUESTA AUTO WASH";
            case "6256": return "UNLIMITED AUTO WASH WELLINGTON";
            case "6266": return "JUPITER AUTO WASH";
            case "6281": return "UNLIMITED AUTO WASH FRENCHMANS";
            case "7021": return "EVERGREEN FITNESS";
            case "7677": return "84 FITNESS CENTER";
            case "8557": return "METRO FITNESS SUNBURY";
            case "30264": return "JM4 DIMENSIONS ABILENE";
            case "2909": return "FITNESS1";
            case "30265": return "SUN CITY ATHLETIC CLUB";
            case "30548": return "MODERN FITNESS";
            case "3488": return "PERFORMANCE HEALTH CORALVILLE";
            case "4477": return "ADVENTHEALTH WELLNESS CENTER";
            case "5272": return "EXTREME STUDIO PERFORMANCE";
            case "5354": return "THE GYM 247 LEBANON";
            case "9175": return "RUSH FITNESS";
            case "1315": return "SC BARBELL AND HEAVY ATHLETICS";
            case "30092": return "FIT ST JAMES";
            case "30318": return "PROJECT VETERAN MUSCLE";
            case "30544": return "IRON GYM";
            case "3387": return "SOUTH CAMPUS MOSES LAKE";
            case "376": return "HERITAGE HILLS ATHLETIC CLUB";
            case "7449": return "HUDSON'S GYM";
            case "7806": return "SMART FITNESS LEWISTON";
            case "8423": return "TURNING POINT AT SONRISE";
            case "8742": return "B-FIT LAKE BLACKSHEAR";
            case "8945": return "IRON REVOLUTION";
            case "9770": return "OLYMPIA ATHLETIC CLUB";
            case "9878": return "BEEBE ATHLETIC CLUB";
            case "30140": return "EVERY HOUR FITNESS";
            case "30257": return "METROFLEX RUSTON";
            case "30315": return "DESTINATION DALLAS";
            case "30643": return "PAC GYM KENNEWICK";
            case "3407": return "CLUB 7 FITNESS KEARNEY";
            case "5368": return "EVOLUTION STRENGTH CENTER";
            case "8525": return "MCALESTER REGIONAL WELLNESS";
            case "9776": return "FOX FITNESS";
            case "1892": return "ULTIMATE FITNESS GASTONIA";
            case "30214": return "STEEL YARD GYM";
            case "3823": return "CORE HEALTH AND FITNESS";
            case "8444": return "RIDGEWELL FITNESS";
            case "9721": return "SIGNAL MOUNTAIN ATHLETIC CLUB";
            case "136": return "THE PLATFORM GYM CONCORD";
            case "30522": return "STUDIO 925";
            case "4468": return "ULTIMATE SPORTS INSTITUTE";
            case "5904": return "US FITNESS";
            case "7509": return "LIFEQUEST WRIGHTSVILLE";
            case "7848": return "BODYFIT EVOLUTION";
            case "8396": return "CORE 24 LLANO";
            case "8424": return "THE DALY WORKOUT";
            case "3908": return "FIT NATION BOSSIER CITY";
            case "5202": return "BELLAIRE FITNESS CENTER";
            case "5277": return "LAWNDALE CHRISTIAN FITNESS";
            case "9261": return "WAYNE COUNTY FITNESS";
            case "7229": return "WELLNESS TODAY";
            case "1975": return "PANTHER FIELD HOUSE";
            case "5735": return "HIVE THERAPY";
            case "6513": return "XTREME FITNESS AND RACQUETBALL";
            case "7423": return "GOLD'S GYM CARMEL";
            case "7565": return "TX HEALTH FITNESS CTR HUGULEY";
            case "7829": return "DIVIDE FITNESS";
            case "1240": return "LEGENDS GYM";
            case "30141": return "THE DRAGON'S LAIR GYM";
            case "8547": return "PEAK FITNESS PARAGOULD";
            case "9708": return "MARSHFIELD FITNESS AND TANNING";
            case "30093": return "XD POWER FITNESS";
            case "30105": return "PILSEN'S GYM";
            case "30195": return "FITNESS ODYSSEY";
            case "4443": return "BEREA FITNESS";
            case "5373": return "ATHENS 247 FAMILY FITNESS";
            case "5867": return "RAW IRON DAINGERFIELD";
            case "7056": return "STRICKLANDS BOXING AND FITNESS";
            case "7058": return "STAR SPORTS AND FITNESS";
            case "9284": return "MOTION AND FITNESS";
            case "9581": return "PRO FITNESS";
            case "30222": return "THE NEXT ELEMENT ACADEMY";
            case "4527": return "QUEST FITNESS CLUB";
            case "4528": return "QUEST FITNESS CLUB";
            case "4538": return "QUEST FITNESS CLUB";
            case "5677": return "HARDBODIES FITNESS TRAINING";
            case "5791": return "DESTINY FITNESS ASHBY";
            case "9622": return "EMPORIA FITNESS";
            default:
                return	"Update the club Name in CodeBase";
        }
    }

    // Batch Data fetch Code
    public void batchBeginDate(String date){
        getDriver().findElement(batchDataStartDate).click();
        getDriver().findElement(batchDataStartDate).sendKeys(Keys.BACK_SPACE);
        getDriver().findElement(batchDataStartDate).sendKeys(Keys.BACK_SPACE);
        getDriver().findElement(batchDataStartDate).sendKeys(Keys.BACK_SPACE);
        getDriver().findElement(batchDataStartDate).sendKeys(Keys.BACK_SPACE);
        getDriver().findElement(batchDataStartDate).sendKeys(Keys.BACK_SPACE);
        getDriver().findElement(batchDataStartDate).sendKeys(Keys.BACK_SPACE);
        getDriver().findElement(batchDataStartDate).sendKeys(Keys.BACK_SPACE);
        getDriver().findElement(batchDataStartDate).sendKeys(Keys.BACK_SPACE);
        getDriver().findElement(batchDataStartDate).sendKeys(date);
    }

    By batchDataEndDate = By.xpath("//*[@data-abc-id='filesModifiedDateRangeEnd']//input");
    public void batchEndDate(String date){
        getDriver().findElement(batchDataEndDate).click();
        getDriver().findElement(batchDataEndDate).sendKeys(Keys.BACK_SPACE);
        getDriver().findElement(batchDataEndDate).sendKeys(Keys.BACK_SPACE);
        getDriver().findElement(batchDataEndDate).sendKeys(Keys.BACK_SPACE);
        getDriver().findElement(batchDataEndDate).sendKeys(Keys.BACK_SPACE);
        getDriver().findElement(batchDataEndDate).sendKeys(Keys.BACK_SPACE);
        getDriver().findElement(batchDataEndDate).sendKeys(Keys.BACK_SPACE);
        getDriver().findElement(batchDataEndDate).sendKeys(Keys.BACK_SPACE);
        getDriver().findElement(batchDataEndDate).sendKeys(Keys.BACK_SPACE);
        getDriver().findElement(batchDataEndDate).sendKeys(date);
    }

    public void clickBatch(int batchNo){
        By batchNoXpath = By.xpath("//tr[@data-abc-id='fileMonitorRow']["+ batchNo +"]");
        getDriver().findElement(batchNoXpath).click();
    }

    public void batchClickClub(int clubPos){
        By clubNoXpath = By.xpath("//tr[@data-abc-id='paymentBatchMonitorsTableRow']["+ clubPos +"]");
        explicitlyWaitOnElement(clubNoXpath,10);
        getDriver().findElement(clubNoXpath).click();
    }

    public void clickMainBatch(){
        By mainBatch = By.xpath("//a[@data-abc-id='batchFilesMonitoringBreadcrumb']");  //what is the use of this
        getDriver().findElement(mainBatch).click();
    }

    By merchantName = By.xpath("//*[@data-abc-id='batchInfo']//p");
    public String getClubName(){
     //   explicitlyWaitOnElement(10);
        return getDriver().findElement(merchantName).getText();
    }

    By batchName = By.xpath("//*[@data-abc-id='transactionDetailsBreadcrumb']");
    public String getBatchName(){
        return getDriver().findElement(batchName).getText();
    }


    By fileName = By.xpath("//*[@data-abc-id='primaryBatchDetailsBreadcrumb']");
    public String getFileName(){
        return getDriver().findElement(fileName).getText();
    }
    //
//    public String getCategory() {
//        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(4));
//        wait.until(ExpectedConditions.visibilityOfElementLocated(category));
//        return getDriver().findElement(category).getText();
//    }

public void sleep(int sleepTime){
    try {
        Thread.sleep(sleepTime);
    }catch(Exception e){
    }
}

    By name_inp_xpath = By.xpath("//div[@class='_13NKt copyable-text selectable-text' and @data-tab='3']");
    public void sendDataToCustomer(String target, String msg) throws InterruptedException {
        By searchButton = By.xpath("//div[@class='_13NKt copyable-text selectable-text']") ;
        By targetPerson = By.xpath("//span[contains(@title,'"+ target +"')]");
        getDriver().findElement(searchButton).click();
        getDriver().findElement(searchButton).sendKeys(target);
        getDriver().findElement(targetPerson).click();
        getDriver().findElement(name_inp_xpath).click();
        By sendButton = By.xpath("//button[@class='_4sWnG']");
        By msgTextBox = By.xpath("//*[@class='_13NKt copyable-text selectable-text' and @data-tab='6']");
        getDriver().findElement(msgTextBox).sendKeys(msg);
        getDriver().findElement(sendButton).click();
        Thread.sleep(2000);
    }


    public void clearMemSearchBox(){
        try{
            findElement(srchClearButton).click();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public WebElement findElement(By xp){
        int timeout = Integer.valueOf(datafile.getData("Timeout"));
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        // wait.until(ExpectedConditions.visibilityOfElementLocated(xp));
        return getDriver().findElement(xp);
    }
}
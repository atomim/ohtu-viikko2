import ohtu.*
import ohtu.authentication.*
import org.openqa.selenium.*
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

description 'A new user account can be created if a proper unused username and a proper password are given'

scenario "user can create a new account with valid input", {
    given 'create account selected', {    
        driver = new HtmlUnitDriver();
        driver.get("http://localhost:8080");
        element = driver.findElement(By.linkText("register new user"));       
        element.click();     
    }

    when 'a valid username and password are given', {
        element = driver.findElement(By.name("username"));
        element.sendKeys("pekka2");
        element = driver.findElement(By.name("password"));
        element.sendKeys("akkepakk2");
        element = driver.findElement(By.name("passwordConfirmation"));
        element.sendKeys("akkepakk2");
        element = driver.findElement(By.name("add"));
        element.submit();
    }
 
    then 'user will be logged in to system', {
        driver.getPageSource().contains("Welcome to Ohtu App!").shouldBe true
    }
}

scenario "user cannot create a new account with a name of an existing account", {
    given 'there is an account of the same name', {    
        driver = new HtmlUnitDriver();
        driver.get("http://localhost:8080");
        element = driver.findElement(By.linkText("register new user"));       
        element.click(); 
        element = driver.findElement(By.name("username"));
        element.sendKeys("pekka2");
        element = driver.findElement(By.name("password"));
        element.sendKeys("akkepakkep2");
        element = driver.findElement(By.name("passwordConfirmation"));
        element.sendKeys("akkepakkep2");
        element = driver.findElement(By.name("add"));
        element.submit();
    }
    and 'create new user selected', {
        driver = new HtmlUnitDriver();
        driver.get("http://localhost:8080");
        element = driver.findElement(By.linkText("register new user"));       
        element.click(); 
    }
    when 'a new account is made with the same name', {
        element = driver.findElement(By.name("username"));
        element.sendKeys("pekka2");
        element = driver.findElement(By.name("password"));
        element.sendKeys("akkepakkep2");
        element = driver.findElement(By.name("passwordConfirmation"));
        element.sendKeys("akkepakkep2");
        element = driver.findElement(By.name("add"));
        element.submit();
    }
    then 'user creation should fail', {
        driver.getPageSource().contains("Welcome to Ohtu Application!").shouldBe false
    }
    
scenario "user cannot create a new account with too short username", {
    given 'create new user selected', {
        driver = new HtmlUnitDriver();
        driver.get("http://localhost:8080");
        element = driver.findElement(By.linkText("register new user"));       
        element.click(); 
    }
    when 'a new account is made with thoo short user name', {
        element = driver.findElement(By.name("username"));
        element.sendKeys("pekk");
        element = driver.findElement(By.name("password"));
        element.sendKeys("akkepakkep2");
        element = driver.findElement(By.name("passwordConfirmation"));
        element.sendKeys("akkepakkep2");
        element = driver.findElement(By.name("add"));
        element.submit();
    }
    then 'user creation should fail', {
        driver.getPageSource().contains("Welcome to Ohtu Application!").shouldBe false
    }
}

scenario "user cannot create a new account with too long user name", {
    given 'create new user selected', {
        driver = new HtmlUnitDriver();
        driver.get("http://localhost:8080");
        element = driver.findElement(By.linkText("register new user"));       
        element.click(); 
    }
    when 'a new account is made with too long user name', {
        element = driver.findElement(By.name("username"));
        element.sendKeys("pekkapekkapekkapekka");
        element = driver.findElement(By.name("password"));
        element.sendKeys("akkepakkep2");
        element = driver.findElement(By.name("passwordConfirmation"));
        element.sendKeys("akkepakkep2");
        element = driver.findElement(By.name("add"));
        element.submit();
    }
    then 'user creation should fail', {
        driver.getPageSource().contains("Welcome to Ohtu Application!").shouldBe false
    }

scenario "user cannot create a new account with a password without a noncharacter", {
    given 'create new user selected', {
        driver = new HtmlUnitDriver();
        driver.get("http://localhost:8080");
        element = driver.findElement(By.linkText("register new user"));       
        element.click(); 
    }
    when 'a new account is made withinvalid password', {
        element = driver.findElement(By.name("username"));
        element.sendKeys("pekka2");
        element = driver.findElement(By.name("password"));
        element.sendKeys("akkepakkep");
        element = driver.findElement(By.name("passwordConfirmation"));
        element.sendKeys("akkepakkep");
        element = driver.findElement(By.name("add"));
        element.submit();
    }
    then 'user creation should fail', {
        driver.getPageSource().contains("Welcome to Ohtu Application!").shouldBe false
    }
}

scenario "user cannot create a new account with a password too short", {
    given 'create new user selected', {
        driver = new HtmlUnitDriver();
        driver.get("http://localhost:8080");
        element = driver.findElement(By.linkText("register new user"));       
        element.click(); 
    }
    when 'a new account is made with too short password', {
        element = driver.findElement(By.name("username"));
        element.sendKeys("pekka2");
        element = driver.findElement(By.name("password"));
        element.sendKeys("akk2");
        element = driver.findElement(By.name("passwordConfirmation"));
        element.sendKeys("akk2");
        element = driver.findElement(By.name("add"));
        element.submit();
    }
    then 'user creation should fail', {
        driver.getPageSource().contains("Welcome to Ohtu Application!").shouldBe false
    }
}

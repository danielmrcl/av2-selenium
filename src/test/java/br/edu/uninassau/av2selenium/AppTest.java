package br.edu.uninassau.av2selenium;

import java.time.Duration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
	private final String URL   = "http://automationpractice.com";
	private final String EMAIL = "sopitis854@servergem.com";
	private final String PASS  = "senha123";
	
	private static FirefoxDriver driver = null;
	private static WebDriverWait wait = null;
	
	@BeforeAll
	public static void setUp() {
		driver = new FirefoxDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	}

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
    	driver.navigate().to(URL);
   
    	// 1. Realizar Login.
    	driver.findElement(By.className("login")).click();
    	driver.findElement(By.id("email")).sendKeys(EMAIL);
    	driver.findElement(By.id("passwd")).sendKeys(PASS);
    	driver.findElement(By.name("SubmitLogin")).click();
    	
    	assertThrows(NoSuchElementException.class, 
			() -> driver.findElement(By.className("login")));

    	// 2. Pesquisar por um item.
    	driver.findElement(By.id("search_query_top")).sendKeys("Summer");
    	driver.findElement(By.name("submit_search")).click();
    	driver.findElement(By.xpath("//*[@id='center_column']/ul/li[2]")).click();
    	
    	// 3. Adicionar ao Carrinho.
    	var ourPriceDisplay = driver.findElement(By.id("our_price_display")).getText();
    	assertTrue(ourPriceDisplay.equals("$30.50"));
    	
    	driver.findElement(By.xpath("//*[@id='add_to_cart']/*[@type='submit']")).click();
    	wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("layer_cart"))));
    	driver.findElement(By.xpath("//*[@title='Proceed to checkout']")).click();
    	driver.findElement(By.xpath("//*[@id='center_column']/p/a")).click();
    	
    	// 4. Informar opções de entrega.
    	var pageHeadingAddresses = driver.findElement(By.className("page-heading")).getText();
    	assertTrue(pageHeadingAddresses.equals("ADDRESSES"));
    	
    	driver.findElement(By.xpath("//*[@class='address_update']/*[@title='Update']")).click();
    	driver.findElement(By.id("phone")).clear();
    	driver.findElement(By.id("phone")).sendKeys("0100000000");
    	driver.findElement(By.id("submitAddress")).click();
    	driver.findElement(By.name("processAddress")).click();
    	driver.findElement(By.id("cgv")).click();
    	driver.findElement(By.name("processCarrier")).click();
    	
    	// 5. Informar opções de pagamento.
    	var pageHeadingPayment = driver.findElement(By.className("page-heading")).getText();
    	assertTrue(pageHeadingPayment.equals("PLEASE CHOOSE YOUR PAYMENT METHOD"));
    	
    	driver.findElement(By.className("bankwire")).click();
    	
    	// 6. Finalizar Compra.
    	driver.findElement(By.xpath("//*[@id='cart_navigation']/button")).click();
    	
    	var orderComplete = driver.findElement(By.xpath("//*[@id='center_column']/div/p/strong")).getText();
    	assertTrue(orderComplete.equals("Your order on My Store is complete."));
    }
}

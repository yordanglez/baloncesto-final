import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class PruebasPhantomjsIT
{
    private static WebDriver driver=null;
    @Test
    public void tituloIndexTest()
    {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,"/usr/bin/phantomjs");
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new
                String[] {"--web-security=no", "--ignore-ssl-errors=yes"});
        driver = new PhantomJSDriver(caps);
        driver.navigate().to("http://localhost:8080/Baloncesto/");
        assertEquals("Votacion mejor jugador liga ACB", driver.getTitle(),
                "El titulo no es correcto");
        System.out.println(driver.getTitle());
        driver.close();
        driver.quit();
    }

    @Test
    public void votosACeroTest()
    {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,"/usr/bin/phantomjs");
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new
                String[] {"--web-security=no", "--ignore-ssl-errors=yes"});
        driver = new PhantomJSDriver(caps);
        driver.navigate().to("http://localhost:8080/Baloncesto/");
        WebElement btnPonerVotosACero = driver.findElement(By.name("B3"));
        btnPonerVotosACero.click();

        WebElement btnVerVotos = driver.findElement(By.name("B4"));
        btnVerVotos.click();

        driver.manage().timeouts().implicitlyWait(10, java.util.concurrent.TimeUnit.SECONDS);

        List<WebElement> filas = driver.findElements(By.xpath("//table[@border='1']//tr"));

        for (int i = 1; i < filas.size(); i++) {
            List<WebElement> columnas = filas.get(i).findElements(By.tagName("td"));
            String nombre = columnas.get(0).getText();
            String votos = columnas.get(1).getText();
            assertEquals("0", votos);
        }

        System.out.println("Todos los votos están a cero.");
        driver.close();
        driver.quit();
    }

    @Test
    public void votarOtroTest()
    {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,"/usr/bin/phantomjs");
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new
                String[] {"--web-security=no", "--ignore-ssl-errors=yes"});
        driver = new PhantomJSDriver(caps);
        driver.navigate().to("http://localhost:8080/Baloncesto/");

        String nuevoJugador = "NuevoJugador";
        WebElement nombreOtros = driver.findElement(By.name("txtOtros"));
        nombreOtros.sendKeys(nuevoJugador);

        WebElement opcionOtro = driver.findElement(By.cssSelector("input[type='radio'][value='Otros']"));
        opcionOtro.click();

        WebElement botonVotar = driver.findElement(By.name("B1"));
        botonVotar.click();

        WebElement enlaceIrAlComienzo = driver.findElement(By.linkText("Ir al comienzo"));
        enlaceIrAlComienzo.click();

        WebElement botonVerVotos = driver.findElement(By.name("B4"));
        botonVerVotos.click();

        List<WebElement> filas = driver.findElements(By.xpath("//table[@border='1']//tr"));
        boolean jugadorEncontrado = false;

        for (int i = 1; i < filas.size(); i++) {
            List<WebElement> columnas = filas.get(i).findElements(By.tagName("td"));
            String nombre = columnas.get(0).getText();
            String votos = columnas.get(1).getText();

            if (nombre.equals(nuevoJugador) && votos.equals("1")) {
                jugadorEncontrado = true;
                break;
            }
        }
        assertEquals(true, jugadorEncontrado);

        System.out.println("Jugador creado con voto 1");
        driver.close();
        driver.quit();
    }
}
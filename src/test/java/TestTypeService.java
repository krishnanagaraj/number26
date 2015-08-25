import javax.ws.rs.core.Response;

import com.persistence.beans.Transaction;
import com.persistence.service.TransactionService;
import com.resource.TransactionResource;
import com.resource.TypeResource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TestTypeService {

    private TransactionService transactionServiceMock;
    private TypeResource typeResource;
    private TransactionResource transactionResource;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        typeResource = new TypeResource();
        transactionResource = new TransactionResource();
        transactionServiceMock = mock(TransactionService.class);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testGetTransactionIdsBasedOnTypeNotFound() throws Exception {
        String type = "retail";
        Response result = typeResource.getDocument(type);
        assertEquals(404, result.getStatus());
    }

    @Test
    public void testGetTransactionIdsBasedOnTypeBadRequest() throws Exception {
        Response result = typeResource.getDocument(null);
        assertEquals(400, result.getStatus());
    }

    @Test
    public void testGetTransactionIdsBasedOnTypeSuccess() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setAmount(1000);
        transaction.setType("cars");

        transactionResource.addDocument(transaction);

        transaction = new Transaction();
        transaction.setAmount(2000);
        transaction.setType("shopping");

        transactionResource.addDocument(transaction);

        transaction = new Transaction();
        transaction.setAmount(3000);
        transaction.setType("cars");

        transactionResource.addDocument(transaction);

        String type = "cars";
        Response result = typeResource.getDocument(type);
        transactionResource.deleteDocument(Long.valueOf(1));
        transactionResource.deleteDocument(Long.valueOf(2));
        transactionResource.deleteDocument(Long.valueOf(3));
        assertEquals(200, result.getStatus());
    }
}

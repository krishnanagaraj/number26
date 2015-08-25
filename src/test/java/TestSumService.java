import javax.ws.rs.core.Response;

import com.persistence.beans.Transaction;
import com.persistence.service.TransactionService;
import com.resource.SumResource;
import com.resource.TransactionResource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


/**
 * Created by nkrishna on 8/25/15.
 */
public class TestSumService {

    private TransactionService transactionServiceMock;
    private SumResource sumResource;
    private TransactionResource transactionResource;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        sumResource = new SumResource();
        transactionResource = new TransactionResource();
        transactionServiceMock = mock(TransactionService.class);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testGetTotalAmountBasedOnTransactionIdNotFound() throws Exception {
        long i = 321123;
        Response result = sumResource.getTotalAmount(i);
        assertEquals(404, result.getStatus());
    }

    @Test
    public void testGetTotalAmountBasedOnTransactionIdBadRequest() throws Exception {
        long i = 0;
        Response result = sumResource.getTotalAmount(i);
        assertEquals(400, result.getStatus());
    }

    @Test
    public void testGetTotalAmountBasedOnTransactionIdSuccess() throws Exception {
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
        transaction.setParentId(1);

        long i = 2;
        transactionResource.updateDocument(i, transaction);

        Response result = sumResource.getTotalAmount(i);
        transactionResource.deleteDocument(Long.valueOf(1));
        transactionResource.deleteDocument(Long.valueOf(2));
        transactionResource.deleteDocument(Long.valueOf(3));
        assertEquals(200, result.getStatus());
    }
}


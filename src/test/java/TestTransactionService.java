import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;

import com.persistence.beans.Transaction;
import com.persistence.service.TransactionService;
import com.resource.TransactionResource;
import org.junit.Test;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by nkrishna on 8/25/15.
 */

public class TestTransactionService {

    private TransactionService transactionServiceMock;

    private TransactionResource transactionResource;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        transactionResource = new TransactionResource();
        transactionServiceMock = mock(TransactionService.class);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testAddTransactionBadRequest() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setAmount(0);
        transaction.setParentId(0);
        transaction.setType("cars");

        Response result = transactionResource.addDocument(transaction);

        assertEquals(400, result.getStatus());
    }


    @Test
    public void testAddTransaction() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setAmount(1000);
        transaction.setType("cars");

        Response result = transactionResource.addDocument(transaction);

        assertEquals(200, result.getStatus());
    }


    @Test
    public void testGetTransactionNotFound() throws Exception {
        long i = 1000;
        Response result = transactionResource.getDocument(i);
        assertEquals(404, result.getStatus());
    }

    @Test
    public void testGetTransactionBadRequest() throws Exception {
        long i = 0;
        Response result = transactionResource.getDocument(i);
        assertEquals(400, result.getStatus());
    }

    @Test
    public void testGetTransaction() throws Exception {
        long i = 4;
        Response result = transactionResource.getDocument(i);
        assertEquals(200, result.getStatus());
    }


    @Test
    public void testUpdateTransactionBadRequest() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setAmount(0);
        transaction.setParentId(0);
        transaction.setType("cars");

        long i = 1;
        Response result = transactionResource.updateDocument(i, transaction);

        assertEquals(400, result.getStatus());
    }

    @Test
    public void testUpdateTransactionNotFound() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setAmount(1000);
        transaction.setParentId(1);
        transaction.setType("cars");
        long i = 1000;
        Response result = transactionResource.updateDocument(i, transaction);
        assertEquals(404, result.getStatus());
    }

    @Test
    public void testUpdateTransaction() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setAmount(1000);
        transaction.setParentId(4);
        transaction.setType("cars");
        long i = 5;
        Response result = transactionResource.updateDocument(i, transaction);
        assertEquals(200, result.getStatus());
    }

    @Test
    public void testDeleteTransactionBadRequest() throws Exception {
        long i = 0;
        Response result = transactionResource.deleteDocument(i);
        assertEquals(400, result.getStatus());
    }

    @Test
    public void testDeleteTransactionNotFound() throws Exception {
        long i = 100;
        Response result = transactionResource.deleteDocument(i);
        assertEquals(404, result.getStatus());
    }

    @Test
    public void testDeleteTransaction() throws Exception {
        long i = 6;
        Response result = transactionResource.deleteDocument(i);
        assertEquals(200, result.getStatus());
    }


}

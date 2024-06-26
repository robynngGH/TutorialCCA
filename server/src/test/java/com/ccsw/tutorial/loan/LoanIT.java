package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.common.pagination.PageableRequest;
import com.ccsw.tutorial.config.ResponsePage;
import com.ccsw.tutorial.customer.model.CustomerDTO;
import com.ccsw.tutorial.game.model.GameDTO;
import com.ccsw.tutorial.loan.model.LoanDTO;
import com.ccsw.tutorial.loan.model.LoanSearchDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LoanIT {

  public static final String LOCALHOST = "http://localhost:";
  public static final String SERVICE_PATH = "/loan";

  @LocalServerPort
  private int port;
  @Autowired
  private TestRestTemplate restTemplate;

  ParameterizedTypeReference<ResponsePage<LoanDTO>> responseTypePage = new ParameterizedTypeReference<ResponsePage<LoanDTO>>() {
  };

  private static final int PAGE_SIZE = 5;
  private static final int TOTAL_LOANS = 7;
  private static final String GAME_ID_PARAM = "idGame";
  private static final String CUSTOMER_ID_PARAM = "idCustomer";
  private static final String DATE_PARAM = "date";

  private static final Date START_DATE = new Date(2019, Calendar.MAY, 21);
  private static final Date END_DATE = new Date(2019, Calendar.MAY, 29);
  private static final Long NEW_LOAN_ID = 8L;
  private static final Long EXISTING_LOAN_ID = 2L;
  private static final Long EXISTING_CUSTOMER_ID = 2L;
  private static final Long EXISTING_GAME_ID = 3L;
  private static final Date TOO_MANY_DAYS_DATE = new Date(2019, Calendar.JUNE, 10);
  private static final Long GAME_ID_FOR_CONFLICTING_DATE = 1L; //On Mars

  /**
   * Sets up the URL with its parameters for game, customer and date filtering
   *
   * @return url from UriComponentsBuilder
   */
  private String getUrlWithParams() {
    return UriComponentsBuilder.fromHttpUrl(LOCALHOST + port + SERVICE_PATH).queryParam(GAME_ID_PARAM, "{" + GAME_ID_PARAM + "}").queryParam(CUSTOMER_ID_PARAM, "{" + CUSTOMER_ID_PARAM + "}").queryParam(DATE_PARAM, "{" + DATE_PARAM + "}")
        .encode().toUriString();
  }

  /**
   * Testing the first page without filters shows the proper page size
   */
  @Test
  public void findFirstPageWithSizeOfFiveAndNoFiltersShouldReturnFirstFiveResults() {
    LoanSearchDTO searchDTO = new LoanSearchDTO();
    searchDTO.setPageable(new PageableRequest(0, PAGE_SIZE));

    ResponseEntity<ResponsePage<LoanDTO>> responseEntity = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDTO), responseTypePage);

    assertNotNull(responseEntity);
    assertEquals(TOTAL_LOANS, responseEntity.getBody().getTotalElements());
    assertEquals(PAGE_SIZE, responseEntity.getBody().getContent().size());
  }

  /**
   * Testing the second page without filters shows the remaining results
   */
  @Test
  public void findSecondPageWithSizeOfFiveAndNoFiltersShouldReturnLastResults() {

    int elementsCount = TOTAL_LOANS - PAGE_SIZE; //2

    LoanSearchDTO searchDTO = new LoanSearchDTO();
    searchDTO.setPageable(new PageableRequest(1, PAGE_SIZE)); //pageNumber 1 equals the second page

    ResponseEntity<ResponsePage<LoanDTO>> responseEntity = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDTO), responseTypePage);

    assertNotNull(responseEntity);
    assertEquals(TOTAL_LOANS, responseEntity.getBody().getTotalElements());
    assertEquals(elementsCount, responseEntity.getBody().getContent().size());
  }

  /**
   * Creating a new loan with proper values
   */
  @Test
  public void saveShouldCreateNewLoan() {

    long newLoanId = TOTAL_LOANS + 1;
    long newLoanSize = TOTAL_LOANS + 1;

    GameDTO gameDto = new GameDTO();
    CustomerDTO customerDto = new CustomerDTO();
    gameDto.setId(1L);
    customerDto.setId(1L);

    LoanDTO dto = new LoanDTO();
    dto.setGame(gameDto);
    dto.setCustomer(customerDto);
    dto.setStart_date(START_DATE);
    dto.setEnd_date(END_DATE);

    restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

    LoanSearchDTO searchDto = new LoanSearchDTO();
    searchDto.setPageable(new PageableRequest(0, (int) newLoanSize));

    //setting up the params for the URL
    //Map<String, Object> params = new HashMap<>();
    //params.put(GAME_ID_PARAM, null);
    //params.put(CUSTOMER_ID_PARAM, null);
    //params.put(DATE_PARAM, null);

    ResponseEntity<ResponsePage<LoanDTO>> responseEntity = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

    assertNotNull(responseEntity);
    assertEquals(newLoanSize, responseEntity.getBody().getTotalElements());

    LoanDTO loan = responseEntity.getBody().getContent().stream().filter(item -> item.getId().equals(newLoanId)).findFirst().orElse(null);
    assertNotNull(loan);
    assertEquals(NEW_LOAN_ID, loan.getId());
  }

  //Saving exceptions

  /**
   * Saving a loan with return date prior to loan date
   */
  @Test
  public void saveWithReturnDatePriorToLoanDateShouldThrowNotAcceptable() {
    LoanDTO dto = new LoanDTO();
    GameDTO gameDto = new GameDTO();
    CustomerDTO customerDto = new CustomerDTO();

    customerDto.setId(EXISTING_CUSTOMER_ID);
    gameDto.setId(EXISTING_GAME_ID);
    dto.setGame(gameDto);
    dto.setCustomer(customerDto);
    dto.setStart_date(END_DATE);
    dto.setEnd_date(START_DATE);
    ResponseEntity<?> responseEntity = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

    assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
  }

  /**
   * Saving a loan valid for more than 14 days
   */
  @Test
  public void saveWithMoreThan14DaysOfLoanShouldThrowMethodNotAllowed() {
    LoanDTO dto = new LoanDTO();
    GameDTO gameDto = new GameDTO();
    CustomerDTO customerDto = new CustomerDTO();

    customerDto.setId(EXISTING_CUSTOMER_ID);
    gameDto.setId(EXISTING_GAME_ID);
    dto.setGame(gameDto);
    dto.setCustomer(customerDto);
    dto.setStart_date(START_DATE);
    dto.setEnd_date(TOO_MANY_DAYS_DATE);
    ResponseEntity<?> responseEntity = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

    assertEquals(HttpStatus.METHOD_NOT_ALLOWED, responseEntity.getStatusCode());
  }

  /**
   * Saving a loan for a game with an already existing loan during an overlapping date range
   */
  @Test
  public void saveWithGameAndDatesThatOverlapWithAnAlreadyExistingLoanShouldThrowConflict() {
    LoanDTO dto = new LoanDTO();
    GameDTO gameDto = new GameDTO();
    CustomerDTO customerDto = new CustomerDTO();

    LoanDTO dto2 = new LoanDTO();
    CustomerDTO customerDto2 = new CustomerDTO();
    customerDto2.setId(EXISTING_CUSTOMER_ID + 1);

    customerDto.setId(EXISTING_CUSTOMER_ID);
    gameDto.setId(GAME_ID_FOR_CONFLICTING_DATE);

    dto2.setGame(gameDto);
    dto2.setCustomer(customerDto2);
    dto2.setStart_date(START_DATE);
    dto2.setEnd_date(END_DATE);
    restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto2), Void.class);

    dto.setGame(gameDto);
    dto.setCustomer(customerDto);
    dto.setStart_date(START_DATE);
    dto.setEnd_date(END_DATE); //these dates are already conflicting
    ResponseEntity<?> responseEntity = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

    assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
  }

  /**
   * Saving a loan for a customer that already has 2 games loaned in a date that overlaps
   */
  @Test
  public void saveLoanForACustomerWithAlready2GamesLoanedForThatDateRangeShouldThrowPreconditionFailed() {
    LoanDTO dto = new LoanDTO();
    GameDTO gameDto = new GameDTO();
    CustomerDTO customerDto = new CustomerDTO();
    customerDto.setId(EXISTING_CUSTOMER_ID);
    gameDto.setId(EXISTING_GAME_ID);

    LoanDTO dto2 = new LoanDTO();
    GameDTO gameDto2 = new GameDTO();
    CustomerDTO customerDto2 = new CustomerDTO();
    customerDto2.setId(EXISTING_CUSTOMER_ID);
    gameDto2.setId(EXISTING_GAME_ID + 1);

    LoanDTO dto3 = new LoanDTO();
    GameDTO gameDto3 = new GameDTO();
    CustomerDTO customerDto3 = new CustomerDTO();
    customerDto3.setId(EXISTING_CUSTOMER_ID);
    gameDto3.setId(EXISTING_GAME_ID + 2);

    dto2.setGame(gameDto2);
    dto2.setCustomer(customerDto2);
    dto2.setStart_date(START_DATE);
    dto2.setEnd_date(END_DATE);
    restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto2), Void.class);

    dto3.setGame(gameDto3);
    dto3.setCustomer(customerDto3);
    dto3.setStart_date(START_DATE);
    dto3.setEnd_date(END_DATE);
    restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto3), Void.class);

    dto.setGame(gameDto);
    dto.setCustomer(customerDto);
    dto.setStart_date(START_DATE);
    dto.setEnd_date(END_DATE); //these dates are already conflicting
    ResponseEntity<?> responseEntity = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

    assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());
  }

  /**
   * Delete test with existing ID
   */
  @Test
  public void deleteWithExistingIdShouldDeleteLoan() {

    long newLoanSize = TOTAL_LOANS - 1;

    restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + EXISTING_LOAN_ID, HttpMethod.DELETE, null, Void.class);

    LoanSearchDTO searchDTO = new LoanSearchDTO();
    searchDTO.setPageable(new PageableRequest(0, TOTAL_LOANS));

    ResponseEntity<ResponsePage<LoanDTO>> responseEntity = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDTO), responseTypePage);

    assertNotNull(responseEntity);
    assertEquals(newLoanSize, responseEntity.getBody().getTotalElements());
  }

  @Test
  public void deleteWithNonExistingIdShouldThrowException() {

    ResponseEntity<?> responseEntity = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + NEW_LOAN_ID, HttpMethod.DELETE, null, Void.class);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
  }
}

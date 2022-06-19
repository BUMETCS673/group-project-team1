package edu.bu.metcs673.trackr.user;

import edu.bu.metcs673.trackr.common.TrackrInputValidationException;
import edu.bu.metcs673.trackr.security.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests the methods in the TrackrUserServiceImpl. Uses Mockito to fake
 * repository calls, so that business logic can be tested.
 * 
 * @author Tim Flucker
 *
 */
@ExtendWith(MockitoExtension.class)
public class TrackrUserServiceImplTest {

	private static final String TEST_USERNAME = "testUser";
	private static final String TEST_EMAIL = "testEmail@email.com";
	private TrackrUser mockUser;

	@Mock
	private TrackrUserRepository userRepository;

	@Mock
	private JWTUtil jwtUtil;

	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@InjectMocks
	private TrackrUserServiceImpl serviceImpl;

	// creates valid TrackrUser object before each test to allow for modification or
	// to use as verification
	@BeforeEach
	public void beforeEach() {
		mockUser = new TrackrUser(0L, "test", "mcTesterson", TEST_USERNAME, "myCoolPassword", TEST_EMAIL);
	}

	public void setUpAuthMock() {
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		Mockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(TEST_USERNAME);
	}

	@Test
	public void testFindById_success() {
		Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

		TrackrUser user = serviceImpl.findUserById(1L);

		assertNotNull(user);
		assertEquals(TEST_USERNAME, user.getUsername());
	}

	@Test
	public void testFindById_failure() {
		Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(NoSuchElementException.class, () -> serviceImpl.findUserById(1L));
	}

	@Test
	public void testFindByUsername_success() {
		setUpAuthMock();
		Mockito.when(userRepository.findByUsername(TEST_USERNAME)).thenReturn(mockUser);

		TrackrUser user = serviceImpl.getCurrentUser();

		assertNotNull(user);
		assertEquals(TEST_USERNAME, user.getUsername());
	}

	@Test
	public void testFindByUsername_failure() {
		setUpAuthMock();
		Mockito.when(userRepository.findByUsername(TEST_USERNAME)).thenReturn(null);

		TrackrUser user = serviceImpl.getCurrentUser();

		assertNull(user);
	}

	@Test
	public void testLoadByUsername_success() {
		Mockito.when(userRepository.findByUsername(TEST_USERNAME)).thenReturn(mockUser);

		UserDetails user = serviceImpl.loadUserByUsername(TEST_USERNAME);

		assertNotNull(user);
		assertEquals(TEST_USERNAME, user.getUsername());
	}

	@Test
	public void testLoadByUsername_failure() {
		Mockito.when(userRepository.findByUsername(TEST_USERNAME)).thenReturn(null);

		assertThrows(UsernameNotFoundException.class, () -> serviceImpl.loadUserByUsername(TEST_USERNAME));
	}

	@Test
	public void testCreateUser_success() {

		// define input
		TrackrUserDTO userInput = new TrackrUserDTO("test", "mcTesterson", TEST_USERNAME, "myCoolPassword", TEST_EMAIL);
		mockUser.setPassword("ENCODED_PASSWORD");
		// mock repository calls
		Mockito.when(userRepository.existsByUsername(TEST_USERNAME)).thenReturn(false);
		Mockito.when(bCryptPasswordEncoder.encode("myCoolPassword")).thenReturn("ENCODED_PASSWORD");
		Mockito.when(userRepository.save(mockUser)).thenReturn(mockUser);
		Mockito.when(jwtUtil.generateToken(TEST_USERNAME)).thenReturn("FAKE.JWT.TOKEN");

		String jwtToken = serviceImpl.createUser(userInput);

		assertNotNull(jwtToken);
		assertEquals("FAKE.JWT.TOKEN", jwtToken);

	}

	@Test
	public void testDuplicateUsername() {
		Mockito.when(userRepository.existsByUsername(TEST_USERNAME)).thenReturn(true);
		TrackrUserDTO testUser = new TrackrUserDTO("testy", "mcTesterson", TEST_USERNAME, "myCoolPassword", TEST_EMAIL);
		assertThrows(TrackrInputValidationException.class, () -> serviceImpl.validateParameters(testUser));
	}

	@Test
	public void testDuplicateEmail() {
		Mockito.when(userRepository.existsByUsername(TEST_USERNAME)).thenReturn(false);
		Mockito.when(userRepository.existsByEmail(TEST_EMAIL)).thenReturn(true);
		TrackrUserDTO testUser = new TrackrUserDTO("testy", "mcTesterson", TEST_USERNAME, "myCoolPassword", TEST_EMAIL);
		assertThrows(TrackrInputValidationException.class, () -> serviceImpl.validateParameters(testUser));
	}

	@ParameterizedTest
	@ValueSource(strings = { "Tim", "Jean", "Xioabing", "Weijie", "Test-Name", "Tim123" })
	public void testNameRegex_success(String name) {
		assertDoesNotThrow(() -> serviceImpl.regexNameValidation(name));
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "Tim@$#@", "Jea@$T@$an", "Xioabi1/-+15$ng", "Wei LKJDSF_ *Y(*RYjie" })
	public void testNameRegex_failure(String name) {
		assertThrows(TrackrInputValidationException.class, () -> serviceImpl.regexNameValidation(name));
	}

	@ParameterizedTest
	@ValueSource(strings = { "Tim", "Jean!", "Xiaobing?", "Weijie_", "Test-Name", "Tim123" })
	public void testPasswordRegex_success(String password) {
		assertDoesNotThrow(() -> serviceImpl.regexPasswordValidation(password));
	}

	@ParameterizedTest
	@ValueSource(strings = { "Tim的", "Jeaالعربيةan", "Xiaobiにほんごng", "Wei한국어jie" })
	public void testPasswordRegex_failure(String password) {
		assertThrows(TrackrInputValidationException.class, () -> serviceImpl.regexPasswordValidation(password));
	}
}

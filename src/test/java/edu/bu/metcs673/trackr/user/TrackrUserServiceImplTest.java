package edu.bu.metcs673.trackr.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import edu.bu.metcs673.trackr.common.TrackrInputValidationException;
import edu.bu.metcs673.trackr.security.JWTUtil;

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
		mockUser = new TrackrUser(0L, "test", "mcTesterson", "testUser", "myCoolPassword", "testEmail@email.com");
	}

	@Test
	public void testFindById_success() {
		Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

		TrackrUser user = serviceImpl.findUserById(1L);

		assertNotNull(user);
		assertEquals("testUser", user.getUsername());
	}

	@Test
	public void testFindById_failure() {
		Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(NoSuchElementException.class, () -> serviceImpl.findUserById(1L));
	}

	@Test
	public void testFindByUsername_success() {
		Mockito.when(userRepository.findByUsername(TEST_USERNAME)).thenReturn(mockUser);

		TrackrUser user = serviceImpl.findByUsername(TEST_USERNAME);

		assertNotNull(user);
		assertEquals(TEST_USERNAME, user.getUsername());
	}

	@Test
	public void testFindByUsername_failure() {
		Mockito.when(userRepository.findByUsername(TEST_USERNAME)).thenReturn(null);

		TrackrUser user = serviceImpl.findByUsername(TEST_USERNAME);

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
		TrackrUserDTO userInput = new TrackrUserDTO("test", "mcTesterson", TEST_USERNAME, "myCoolPassword",
				"testEmail@email.com");
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
		TrackrUserDTO testUser = new TrackrUserDTO("testy", "mcTesterson", TEST_USERNAME, "myCoolPassword",
				"testEmail@email.com");
		assertThrows(TrackrInputValidationException.class, () -> serviceImpl.validateParameters(testUser));
	}
}

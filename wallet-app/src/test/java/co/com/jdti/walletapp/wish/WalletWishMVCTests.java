package co.com.jdti.walletapp.wish;

import co.com.jdti.walletapp.TestMYSQLContainerConnection;
import co.com.jdti.walletapp.wishlist.model.WishDTO;
import co.com.jdti.walletapp.wishlist.services.IWishService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WalletWishMVCTests extends TestMYSQLContainerConnection {

	private MockMvc mockMvc;

	@Autowired
	private IWishService wishService;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
			.build();
	}

	@Test
	@Order(0)
	void contextLoads() {
		assertTrue(mysqlDB.isCreated());
		assertTrue(mysqlDB.isRunning());
	}

	@Test
	@Order(1)
	void saveNewWishTest() throws Exception {
		mockMvc.perform(post("/wishes/save")
				.param("description", "Test 1")
				.param("price", "1000"))
			// .with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/wishes"));
	}

	@Test
	@Order(2)
	void findAllPageableTest() throws Exception {
		int page = 1;
		Page<WishDTO> wishes = wishService.findAll(page);

		mockMvc.perform(get("/wishes/"))
			.andExpect(status().isOk())
			 .andExpect(view().name("wishes/index"))
			.andExpect(model().attribute("title", "Wallet App - Deseos"))
			.andExpect(model().attribute("currentPage", page))
			.andExpect(model().attribute("totalPages", wishes.getTotalPages()))
			.andExpect(model().attribute("totalItems", wishes.getTotalElements()))
			.andExpect(model().attribute("wishes", wishes.getContent()))
			.andExpect(model().attribute("currentView", "wishes"));
	}

	@Test
	@Order(3)
	void findAllPageableTest_Empty() throws Exception {
		mockMvc.perform(get("/wishes/3")
				.param("page", "3"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/wishes"));
	}

	@Test
	@Order(4)
	void testDeleteWish() throws Exception {
		mockMvc.perform(post("/wishes/save")
				.param("id", "someId")
				.param("description", "Test 1")
				.param("price", "1000"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/wishes"));

		mockMvc.perform(get("/wishes/delete/{id}", "someId"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/wishes"));
	}
}

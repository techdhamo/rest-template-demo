/**
 * 
 */
package in.otomate.adminloginservice.service.implementation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest; 
import in.otomate.adminloginservice.model.Admin;
import in.otomate.adminloginservice.service.IAdminLoginService;
import in.otomate.common.Exceptions.DataViolationException;
import in.otomate.common.Exceptions.InvalidDataException;
import in.otomate.common.Exceptions.NullDataException;

/**
 * @author dhamo
 *
 */
@SpringBootTest
class AdminLoginServiceImplTest {

	@Autowired
	IAdminLoginService service;

	@Test
	void testRegisterNull() {
		Assertions.assertThrows(NullDataException.class, ()->{
		service.saveInfo(null);
		});
	}
	@Test
	void testRegisterInvalid() {
		Assertions.assertThrows(InvalidDataException.class, ()->{
		service.saveInfo(new Admin());
		});
	}
	
	@Test
	void testRegister() {
Assertions.assertThrows(DataViolationException.class, ()->{
		Admin a = Admin.builder().fname("Dhamodaran").lname("N").email("dhamodaran@outlok.in").mobile("9655606683")
				.password("12345678").build();
		System.out.println(service.saveInfo(a));
	});
	}

	@Test
	void testLogin() {
		System.out.println(service.findByEmail("dhamodaran@outlook.in"));
	}

	@Test
	void testLoginNull() {
		Assertions.assertThrows(NullDataException.class, ()->{
			service.findByEmail(null);
		});
	}

}

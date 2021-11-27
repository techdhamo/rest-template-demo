/*
 * package in.otomate.adminloginservice.service.implementation;
 * 
 * import static org.junit.jupiter.api.Assertions.assertEquals;
 * 
 * import org.junit.jupiter.api.Test; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.boot.test.context.SpringBootTest;
 * 
 * import in.otomate.adminloginservice.model.OTPRequest; import
 * in.otomate.adminloginservice.model.ValidationResponse; import
 * in.otomate.adminloginservice.model.Verification; import
 * in.otomate.adminloginservice.model.VerifyContact; import
 * in.otomate.adminloginservice.service.IAdminLoginService;
 * 
 * @SpringBootTest public class OTPSenderTest {
 * 
 * @Autowired IAdminLoginService service;
 * 
 * void testSendMailOtp() { OTPRequest otpRequest =
 * OTPRequest.builder().fname("Dhamodaran").lname("N").contact(
 * "dhamodaran@outlook.in") .build(); ValidationResponse sent =
 * service.sendOtp(otpRequest); assertEquals(true, sent); }
 * 
 * void testSendMobileOtp() { OTPRequest otpRequest =
 * OTPRequest.builder().fname("Dhamodaran").lname("N").contact("+919655606681")
 * .build(); ValidationResponse sent = service.sendOtp(otpRequest);
 * assertEquals(true, sent); }
 * 
 * @Test void testVerifyMail() {
 * 
 * VerifyContact contact=VerifyContact.builder()
 * .contact("dhamodaran@outlook.in") .otp("089432") .build(); assertEquals(true,
 * service.verify(contact)); }
 * 
 * @Test void testVerifyMobile() {
 * 
 * VerifyContact contact=VerifyContact.builder() .contact("+919655606681")
 * .otp("083451") .build(); assertEquals(true, service.verify(contact)); } }
 */
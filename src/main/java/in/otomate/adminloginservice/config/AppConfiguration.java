package in.otomate.adminloginservice.config; 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration; 
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;   
import in.otomate.adminloginservice.service.implementation.AdminDetailServiceImpl; 
import in.otomate.common.filter.CORSFilter;
import in.otomate.common.util.JwtUtil;

@Configuration 
public class AppConfiguration {
	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public UserDetailsService service() {
		return new AdminDetailServiceImpl();
	} 
	@Bean
	public JwtUtil util() {
		return new JwtUtil();
	} 
	@Bean
	public CORSFilter cors() {
		return new CORSFilter();
	} 
	
	/*
	 * @Bean public RestHighLevelClient client() { try { ClientConfiguration
	 * clientConfiguration = ClientConfiguration.builder()
	 * .connectedTo("localhost:9200") .build(); return
	 * RestClients.create(clientConfiguration).rest(); } catch (Exception e) { throw
	 * new SystemException(null, this, e.getMessage()); }
	 * 
	 * 
	 * 
	 * }
	 * 
	 * @Bean public ElasticsearchOperations elasticsearchTemplate() { try {
	 * 
	 * return new ElasticsearchRestTemplate(client()); } catch (Exception e) { throw
	 * new SystemException(null, this, e.getMessage()); }
	 * 
	 * }
	 */
}
